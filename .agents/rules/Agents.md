# Repository Guidelines

This project is for creating UI components that will be used in other projects
MainActivity is a scrollable screen showcasing this UI components

## Project Structure

Single-module Android project (`settings.gradle.kts` includes only `:app`). App code lives in `app/src/main/java/com/example/androidui`, with `MainActivity.kt` as the entry point.
- Architecture follows MVVM: Screens act as Views, ViewModels own UI state and intents.
- `ui/shared/`: reusable Compose components. Avoid duplicating components across features.
- pull styling from `ui/theme/`
## Verification and Deploy

Always use the committed Gradle wrapper, not a system Gradle.

1. Compile-check: `./gradlew :app:compileDebugKotlin` then Deploy: `./scripts/deploy_waydroid.sh`

`deploy_waydroid.sh` builds, installs, and launches the app inside a local Waydroid container. It ensures the Waydroid session is running, discovers the Waydroid IP on the `waydroid0` bridge, connects ADB to it, runs `./gradlew installDebug`, and launches `com.example.atamaproject.MainActivity`. It uses the ADB binary at `/home/saita/Android/Sdk/platform-tools/adb`, so `adb` need not be on your `PATH`.

## Testing

Do not write or add tests unless the user explicitly requests them.

## Coding Style

- Kotlin + Jetpack Compose + Material 3. Four-space indent; small, feature-scoped files.
- Pull colors/shapes/typography/Dimensions from `MaterialTheme.colorScheme/shapes/typography/Dimens` — never hardcode `Color` or `dp`.
- Kotlin code style is `official` (see `gradle.properties`).