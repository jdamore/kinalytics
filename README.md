# Kinalytics

A Kotlin/Spring Boot web application for displaying and analyzing financial transactions.

## Requirements

- Java 21
- Gradle 8.x (wrapper included)

## Quick Start

```bash
# Build the project
./go.sh build

# Run tests
./go.sh test

# Start locally (http://localhost:5000)
./go.sh local
```

## Commands

Use `./go.sh [command]` as the main entry point:

| Command | Description |
|---------|-------------|
| `./go.sh help` | Show available commands |
| `./go.sh build` | Build the project |
| `./go.sh test` | Run all tests |
| `./go.sh local` | Start application locally (port 5000) |
| `./go.sh clean` | Clean build artifacts |
| `./go.sh hooks` | Install git hooks (pre-commit, pre-push) |

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/` | Returns "Hello, World!" |

## Configuration

Uses Spring Boot profiles for environment-specific configuration:
- `local` (default) - Uses local CSV file for transactions
- `prod` - Uses GCP Cloud Storage

Set profile via `SPRING_PROFILES_ACTIVE` environment variable.

## Architecture

Spring Boot application using DDD structure with BDD-style testing.

**DDD Layers** (`src/main/kotlin/com/finalytics/`):
- `config/` - Environment-based configuration
- `domain/` - Entity definitions (Transaction)
- `infrastructure/` - Data access (TransactionRepository, CsvTransactionRepository)
- `application/` - Business logic services (TransactionService)
- `web/` - REST controllers

**Test Structure:**
- Feature files (Gherkin): `src/test/resources/features/*.feature`
- Step definitions: `src/test/kotlin/com/finalytics/bdd/steps/`
- Unit tests: `src/test/kotlin/com/finalytics/unit/`

## Tech Stack

- Kotlin 2.1
- Spring Boot 3.4
- Gradle (Kotlin DSL)
- Java 21
- JUnit 5 + MockK
- Cucumber-JVM (BDD)
