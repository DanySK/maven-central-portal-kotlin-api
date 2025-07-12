const publishCmd = `
./gradlew copyDocs || exit 1
if ! (git diff --quiet && git diff --staged --quiet); then
    git config user.name 'Danilo Pianini [bot]' || exit 10
    git config user.email 'danilo.pianini@gmail.com' || exit 11
    git add README.md docs/ || exit 12
    git commit -m "docs: update documentation" || exit 13
    git pull --rebase || exit 14
    git push || exit 15
fi
./gradlew publishAllPublicationsToProjectLocalRepository zipMavenCentralPortalPublication releaseMavenCentralPortalPublication || exit 2
./gradlew publishJsPackageToNpmjsRegistry || exit 4
`
import config from 'semantic-release-preconfigured-conventional-commits' with { type: "json" };
config.plugins.push(
    [
        "@semantic-release/exec",
        {
            "publishCmd": publishCmd,
        }
    ],
    "@semantic-release/github",
    "@semantic-release/git",
)
export default config
