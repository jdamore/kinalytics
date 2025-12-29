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

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/` | Returns "Hello, World!" |
| GET | `/transactions` | Returns JSON array of all transactions |

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

## Architecture

This project follows a DDD (Domain-Driven Design) layered architecture:

**Layers** (`src/main/kotlin/com/finalytics/`):
- `domain/` - Core business entities (Transaction)
- `infrastructure/` - Data access implementations (TransactionRepository, CsvTransactionRepository, RepositoryFactory)
- `service/` - Business logic services (TransactionService)
- `config/` - Application configuration (AppConfig, DataSource enum)
- `controller/` - REST API endpoints (HelloController, TransactionController)

**Data Flow:**
1. Controller receives HTTP request
2. Service orchestrates business logic
3. Repository fetches/persists data
4. Domain entities represent business concepts

## Data Format

Transaction CSV files use **UTF-16 LE encoding** with columns:
- Booking Date (DD.MM.YYYY format)
- Partner Name
- Partner IBAN
- BIC/SWIFT
- Partner Account Number
- Bank code
- Amount (comma as thousands separator, negative = outgoing)
- Currency
- Booking details
- Payment Reference

## Unit Tests

Unit tests use JUnit 5 with MockK for mocking.

**Structure:**
- Unit tests: `src/test/kotlin/com/finalytics/unit/`
- Use `@Test` annotation from JUnit 5
- Use MockK for mocking dependencies

**Running tests:**
- `./go.sh test` - Run all tests (BDD + unit)
- `./gradlew test --rerun-tasks` - Force re-run all tests

## BDD Tests

BDD tests use Cucumber-JVM with Gherkin feature files.

**Structure:**
- Feature files: `src/test/resources/features/*.feature`
- Step definitions: `src/test/kotlin/com/finalytics/bdd/steps/`
- Config: `src/test/kotlin/com/finalytics/bdd/CucumberConfig.kt`
- Runner: `src/test/kotlin/com/finalytics/bdd/RunCucumberTest.kt`

**Adding a new BDD test:**
1. Create `.feature` file in `src/test/resources/features/`
2. Create step definitions in `src/test/kotlin/com/finalytics/bdd/steps/`
3. Use `@Given`, `@When`, `@Then` annotations from Cucumber

## Tech Stack

- Kotlin 2.1
- Spring Boot 3.4
- Gradle (Kotlin DSL)
- Java 21
- JUnit 5 + MockK
- Cucumber-JVM (BDD)
