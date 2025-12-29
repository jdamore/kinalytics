# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

Use `./go.sh [command]` as the main entry point:

- `./go.sh help` - Show available commands
- `./go.sh build` - Build the project
- `./go.sh test` - Run all tests
- `./go.sh local` - Start application locally (http://localhost:5000)
- `./go.sh clean` - Clean build artifacts
- `./go.sh hooks` - Install git hooks (pre-commit, pre-push)

## Configuration

This project uses **Spring Boot profiles** for environment-specific configuration.

**Profile files:**
- `application.yml` - Base/common configuration
- `application-local.yml` - Local development (CSV file)

**Activation:**
- Default: `local` (set in application.yml)
- Override: Set `SPRING_PROFILES_ACTIVE` environment variable

**Adding environment-specific config:**
1. Create `application-{profile}.yml` in `src/main/resources/`
2. Use `@Value("${app.property}")` to inject values
3. Or use `@Profile("local")` / `@Profile("prod")` on beans for conditional loading

## Gradle Build Scans

Build Scans are enabled via the Develocity plugin in `settings.gradle.kts`.

- **In CI:** Scans are published automatically; links appear in GitHub Actions job summary
- **Locally:** Scans are not published (controlled by `CI` env var check)

Build Scan reports include: build performance, dependency resolution, test results, and failure details.

## Tech Stack

- Kotlin 2.1
- Spring Boot 3.4
- Gradle (Kotlin DSL)
- Java 21
- JUnit 5 + MockK
- Cucumber-JVM (BDD)
