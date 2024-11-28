import org.danilopianini.gradle.mavencentral.JavadocJar
import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
    library = "multiplatform"
    groupId = project.group.toString()
    id = project.name
    configOptions.put("dateLibrary", "kotlinx-datetime")
}

val openApiOutputDir = tasks.openApiGenerate.flatMap { it.outputDir }

val copyDocs by tasks.registering(DefaultTask::class) {
    doLast {
        file(openApiOutputDir.map { "$it/README.md" })
            .copyTo(rootProject.rootDir.resolve("README.md"), overwrite = true)
        rootProject.rootDir.resolve("docs").mkdirs()
        file(openApiOutputDir.map { "$it/docs" })
            .copyRecursively(rootProject.rootDir.resolve("docs"), overwrite = true)
    }
    dependsOn(tasks.openApiGenerate)
}

val fixFormData by tasks.registering {
    val target = openApiOutputDir.map { "$it/src/commonMain/kotlin/org/danilopianini/centralpublisher/api/PublishingApi.kt" }
    inputs.file(target)
    outputs.file(target)
    dependsOn(tasks.openApiGenerate)
    doLast {
        val regex = Regex(
            """^(\s*)formData\s*\{\s*bundle\?\.\s*apply\s*\{\s*append\("bundle",\s*bundle\s*\)\s*\}\s*\}""",
            setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.MULTILINE),
        )
        val file = File(target.get())
        check(file.exists()) { "File ${file.absolutePath} does not exist" }
        val content = file.readText()
        check(regex.containsMatchIn(content)) { "Regex does not match" }
        println("Regex matches")
        println(regex.find(content)?.groupValues)
        val newContent = regex.replace(content) { matchResult ->
            val (_, indent) = matchResult.groupValues
            """
            |${indent}formData {
            |$indent    bundle?.apply {
            |$indent        append(
            |$indent            "bundle",
            |$indent            bundle,
            |$indent            io.ktor.http.Headers.build {
            |$indent                append(io.ktor.http.HttpHeaders.ContentDisposition, "filename=\"${'$'}name\"")
            |$indent            }
            |$indent        )
            |$indent    }
            |$indent}
            """.trimMargin()
        }
        file.writeText(newContent)
    }
}

tasks.openApiGenerate.configure {
    finalizedBy(copyDocs)
    finalizedBy(fixFormData)
}

tasks.withType<KotlinCompilationTask<*>>().configureEach {
    dependsOn(fixFormData)
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

    sourceSets.configureEach {
        kotlin.srcDir(openApiOutputDir.map { "$it/src/$name" })
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.kotlinx.datetime)
                api(libs.bundles.ktor.client)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.ktor.client.mock)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk7"))
                implementation(libs.ktor.client.cio.jvm)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
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


tasks.dokkaJavadoc {
    enabled = false
}

tasks.withType<JavadocJar>().configureEach {
    val dokka = tasks.dokkaHtml.get()
    dependsOn(dokka)
    from(dokka.outputDirectory)
}

signing {
    if (System.getenv("CI") == "true") {
        val signingKey: String? by project
        val signingPassword: String? by project
        useInMemoryPgpKeys(signingKey, signingPassword)
    }
}

publishOnCentral {
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

publishing {
    publications {
        publications.withType<MavenPublication>().configureEach {
            if ("OSSRH" !in name) {
                artifact(tasks.javadocJar)
            }
        }
    }
}
