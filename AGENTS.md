# Agent Instructions

## Scope

These instructions apply to the whole repository.

## Build And Validation

- Run `./gradlew build` before finishing substantial code changes.
- If you change OpenAPI inputs, generated sources, or documentation wiring, run `./gradlew copyDocs` and include the resulting updates to `README.md` and `docs/`.
- Do not invent alternate validation commands when Gradle already exposes the required task.

## Generated Sources

- Treat `openapi/central-publisher-api.json` as the source of truth for generated API code and docs.
- Do not hand-edit generated content under `docs/` or generated Kotlin sources when the change should come from OpenAPI generation instead.
- Keep `README.md` and `docs/` aligned with the generated output when API-facing changes affect them.

## Release Workflow

- Preserve the current semantic-release procedure unless the user explicitly asks to change it.
- Do not remove `@semantic-release/git`, the documentation commit flow, or the release publish steps as a workaround for CI failures.
- The `release` workflow must keep using SSH checkout with `secrets.DEPLOY_KEY`; do not replace it with token-based checkout unless the user explicitly requests a release-auth redesign.
- When adjusting release CI, preserve fork behavior: repository checkout must still work without secrets, and all refs in forks must exit successfully without publishing.

## Warning Suppressions

- Treat warning suppressions as a last resort.
- Add a short justification next to every suppression.
- Do not add blanket or unexplained suppressions.
