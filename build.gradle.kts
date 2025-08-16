@file:OptIn(ExperimentalWasmDsl::class)

import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.gradle.jvm.tasks.Jar as AnyJar

plugins {
    alias(libs.plugins.dokka)
    alias(libs.plugins.gitSemVer)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.npm.publish)
    alias(libs.plugins.openapi.generator)
    alias(libs.plugins.publishOnCentral)
    alias(libs.plugins.taskTree)
}

group = "org.danilopianini"

repositories {
    google()
    mavenCentral()
}

openApiGenerate {
    inputSpec = rootProject.projectDir.resolve("openapi").resolve("central-publisher-api.json").absolutePath
    packageName = "org.danilopianini.centralpublisher.impl"
    apiPackage = "org.danilopianini.centralpublisher.api"
    generatorName = "kotlin"
    library.set("multiplatform")
    groupId = project.group.toString()
    id = project.name
    configOptions.put("dateLibrary", "kotlinx-datetime")
}

val openApiOutputDir: String =
    rootProject.layout.buildDirectory.dir("generated-sources/main").get().asFile.absolutePath

tasks.openApiGenerate.configure {
    outputDir = openApiOutputDir
}

val copyDocs by tasks.registering(DefaultTask::class) {
    doLast {
        file("$openApiOutputDir/README.md")
            .copyTo(rootProject.rootDir.resolve("README.md"), overwrite = true)
        rootProject.rootDir.resolve("docs").mkdirs()
        file("$openApiOutputDir/docs")
            .copyRecursively(rootProject.rootDir.resolve("docs"), overwrite = true)
    }
    dependsOn(tasks.openApiGenerate)
}

tasks.openApiGenerate.configure {
    finalizedBy(copyDocs)
}

tasks.withType<AnyJar>().configureEach {
    dependsOn(tasks.openApiGenerate)
}

tasks.compileKotlinMetadata.configure { dependsOn(tasks.openApiGenerate) }

tasks.withType<KotlinCompilationTask<*>>().configureEach {
    dependsOn(tasks.openApiGenerate)
}

kotlin {
    jvmToolchain(8)

    jvm {
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget = JvmTarget.JVM_1_8
                }
            }
        }
    }

    wasmWasi {
        binaries.library()
    }

    js(IR) {
        browser()
        nodejs()
        binaries.library()
    }

    wasmJs {
        browser()
        nodejs()
        d8()
        binaries.library()
    }

    val nativeSetup: KotlinNativeTarget.() -> Unit = {
        binaries {
            sharedLib()
            staticLib()
        }
    }

    applyDefaultHierarchyTemplate()
    /*
     * Linux 64
     */
    linuxX64(nativeSetup)
    linuxArm64(nativeSetup)
    /*
     * Win 64
     */
    mingwX64(nativeSetup)
    /*
     * Apple OSs
     */
    macosX64(nativeSetup)
    macosArm64(nativeSetup)
    iosArm64(nativeSetup)
    iosSimulatorArm64(nativeSetup)
    watchosArm32(nativeSetup)
    watchosArm64(nativeSetup)
    watchosSimulatorArm64(nativeSetup)
    tvosArm64(nativeSetup)
    tvosSimulatorArm64(nativeSetup)

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("$openApiOutputDir/src/commonMain/kotlin")
            dependencies {
                api(libs.bundles.ktor.client)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.ktor.client.mock)
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk7"))
                implementation(libs.ktor.client.cio.jvm)
            }
        }
        val jvmTest by getting { }
        val jsMain by getting {
            dependencies {
                api(libs.ktor.client.js)
            }
        }
        val iosMain by getting {
            dependencies {
                api(libs.ktor.client.ios)
            }
        }
        all {
            languageSettings.apply {
                optIn("kotlin.Experimental")
            }
        }
    }

    val os = OperatingSystem.current()
    val excludeTargets = when {
        os.isLinux -> kotlin.targets.filterNot { "linux" in it.name }
        os.isWindows -> kotlin.targets.filterNot { "mingw" in it.name }
        os.isMacOsX -> kotlin.targets.filter { "linux" in it.name || "mingw" in it.name }
        else -> emptyList()
    }.mapNotNull { it as? KotlinNativeTarget }

    configure(excludeTargets) {
        compilations.configureEach {
            cinterops.configureEach { tasks[interopProcessingTaskName].enabled = false }
            compileTaskProvider.get().enabled = false
            tasks[processResourcesTaskName].enabled = false
        }
        binaries.configureEach { linkTaskProvider.configure { enabled = false } }

        mavenPublication {
            tasks.withType<AbstractPublishToMaven>().configureEach {
                onlyIf { publication != this@mavenPublication }
            }
            tasks.withType<GenerateModuleMetadata>().configureEach {
                onlyIf { publication.get() != this@mavenPublication }
            }
        }
    }
}

val iosTest by tasks.registering(Exec::class) {
    val device = project.findProperty("device")?.toString() ?: "iPhone 8"
    dependsOn("linkDebugTestIosX64")
    group = JavaBasePlugin.VERIFICATION_GROUP
    description = "Execute unit tests on ${device} simulator"
    doFirst {
        val binary = kotlin.targets.getByName<KotlinNativeTarget>("iosX64").binaries.getTest("DEBUG")
        commandLine("xcrun", "simctl", "spawn", device, binary.outputFile)
    }
}
tasks.register("test") {
    dependsOn("allTests")
}

signing {
    if (System.getenv("CI") == "true") {
        val signingKey: String? by project
        val signingPassword: String? by project
        useInMemoryPgpKeys(signingKey, signingPassword)
    }
}

publishOnCentral {
    repoOwner = "DanySK"
    projectLongName.set("Template for Kotlin Multiplatform Project")
    projectDescription.set("A template repository for Kotlin Multiplatform projects")
    repository("https://maven.pkg.github.com/danysk/${rootProject.name}".lowercase()) {
        user.set("DanySK")
        password.set(System.getenv("GITHUB_TOKEN"))
    }
    publishing {
        publications {
            withType<MavenPublication> {
                pom {
                    developers {
                        developer {
                            name.set("Danilo Pianini")
                            email.set("danilo.pianini@gmail.com")
                            url.set("http://www.danilopianini.org/")
                        }
                    }
                }
            }
        }
    }
}

npmPublish {
    registries {
        register("npmjs") {
            uri.set("https://registry.npmjs.org")
            val npmToken: String? by project
            authToken.set(npmToken)
            dry.set(npmToken.isNullOrBlank())
        }
    }
}

tasks.named("kotlinStoreYarnLock").configure {
    dependsOn(tasks.named("kotlinUpgradeYarnLock"))
}
tasks.named("kotlinWasmStoreYarnLock").configure {
    dependsOn(tasks.named("kotlinWasmUpgradeYarnLock"))
}
