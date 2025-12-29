# Migration Plan: finalytics → kinalytics

Migrate the Python/Flask finalytics application to Kotlin/SpringBoot with Gradle.

**Target Stack:** Kotlin 2.1 | Spring Boot 3.4 | Gradle (Kotlin DSL) | Java 21 | JUnit 5 + MockK | Cucumber-JVM

---

## Phase 1: Project Foundation & Build Infrastructure [Complete]

**Goal:** Set up the complete build system and helper scripts.

**Files:**
- `build.gradle.kts` - All dependencies
- `settings.gradle.kts` - Project settings + Develocity plugin for Build Scans
- `gradle/wrapper/*` - Gradle wrapper
- `go.sh` - Helper script (build, test, local, clean, hooks)
- `hooks/pre-commit`, `hooks/pre-push` - Git hooks
- `.github/workflows/test.yml` - GitHub Actions test workflow with Build Scans
- `src/main/kotlin/com/finalytics/FinalyticsApplication.kt` - Spring Boot main
- `src/main/resources/application.yml` - Base configuration
- `src/main/resources/application-local.yml` - Local profile config
- `README.md` - Project documentation
- `.claude/CLAUDE.md` - Claude Code guidance

**Gradle Build Scans:**
- Configured via Develocity plugin in `settings.gradle.kts`
- Only publishes in CI (when `CI` env var is set)
- Links appear in GitHub Actions job summary

**Verification:**
- [x] `./go.sh build` compiles successfully
- [x] `./go.sh test` runs (0 tests initially)
- [x] `./go.sh local` starts the application
- [x] GitHub Actions workflow passes with Build Scan link

---

## Phase 2: Hello World Endpoint + BDD Test [Complete]

**Goal:** Implement the `/` endpoint with its BDD test.

**Files:**
- `src/main/kotlin/com/finalytics/controller/HelloController.kt` - GET `/` returns "Hello, World!"
- `src/test/resources/features/hello.feature` - Copy from Python project
- `src/test/kotlin/com/finalytics/bdd/CucumberConfig.kt` - Cucumber-Spring integration
- `src/test/kotlin/com/finalytics/bdd/steps/HelloSteps.kt` - Step definitions

**Update CLAUDE.md:**
- Add API Endpoints section with `GET /`
- Add BDD test structure documentation

**Verification:**
- [x] `./go.sh test` passes hello.feature BDD test
- [x] `curl localhost:5000/` returns "Hello, World!"

---

## Phase 3: Domain & Infrastructure Layers + Unit Tests [Pending]

**Goal:** Implement Transaction entity and CSV repository with all unit tests.

**Files:**
- `src/main/kotlin/com/finalytics/domain/Transaction.kt` - Data class with `toMap()`
- `src/main/kotlin/com/finalytics/infrastructure/TransactionRepository.kt` - Interface
- `src/main/kotlin/com/finalytics/infrastructure/CsvTransactionRepository.kt` - UTF-16 LE CSV parsing
- `src/main/kotlin/com/finalytics/infrastructure/RepositoryFactory.kt` - Factory for LOCAL/PROD
- `src/main/kotlin/com/finalytics/config/AppConfig.kt` - AppMode enum
- `src/main/resources/data/TX_example.csv` - Copy from Python project
- `src/test/kotlin/com/finalytics/unit/CsvTransactionRepositoryTest.kt` - 8 tests
- `src/test/kotlin/com/finalytics/unit/RepositoryFactoryTest.kt` - 2 tests

**Update application-local.yml:**
- Add `app.data-source: csv`
- Add `app.csv-path: data/TX_example.csv`

**Update CLAUDE.md:**
- Add Architecture section with DDD layers (domain, infrastructure)
- Add Data Format section (CSV columns)
- Add unit test documentation

**Verification:**
- [x] `./go.sh test` passes all 10 unit tests
- [x] CSV parsing handles UTF-16 LE, DD.MM.YYYY dates, comma thousands separators

---

## Phase 4: Application Layer + Service Tests [Pending]

**Goal:** Implement TransactionService with unit tests.

**Files:**
- `src/main/kotlin/com/finalytics/application/TransactionService.kt` - Spring @Service
- `src/test/kotlin/com/finalytics/unit/TransactionServiceTest.kt` - 2 tests with mocked repository

**Update CLAUDE.md:**
- Add application layer to Architecture section

**Verification:**
- [ ] `./go.sh test` passes service unit tests

---

## Phase 5: Transactions Endpoint + BDD Test [Pending]

**Goal:** Complete the `/transactions` endpoint with its BDD test.

**Files:**
- `src/main/kotlin/com/finalytics/controller/TransactionController.kt` - GET `/transactions` returns JSON
- `src/test/resources/features/transactions.feature` - Copy from Python project
- `src/test/kotlin/com/finalytics/bdd/steps/TransactionsSteps.kt` - Step definitions

**Update CLAUDE.md:**
- Add `GET /transactions` to API Endpoints
- Add controller layer to Architecture section

**Update README.md:**
- Add `GET /transactions` to API Endpoints

**Verification:**
- [ ] `./go.sh test` passes ALL tests (BDD + unit)
- [ ] `curl localhost:5000/transactions` returns JSON array
- [ ] GitHub Actions workflow passes

---

## Phase 6: GCP Cloud Run Deployment [Pending]

**Goal:** Set up Docker containerization and GCP Cloud Run deployment.

**Files:**
- `Dockerfile` - Multi-stage build (Gradle → JRE runtime)
- `.dockerignore` - Exclude build artifacts
- `.github/workflows/deploy.yml` - GCP deploy workflow
- `src/main/resources/application-prod.yml` - Production profile config

**Update go.sh with Docker/GCP commands:**
- `dstart` - Start Docker daemon (Colima)
- `dstop` - Stop Docker daemon
- `dbuild` - Build Docker image
- `drun` - Run container locally (port 8080)
- `gcloudauth` - Authenticate with GCP
- `gcloudbuild` - Build and push to Artifact Registry
- `gcloudpush` - Push to Artifact Registry
- `gclouddeploy` - Deploy to Cloud Run

**Update CLAUDE.md:**
- Add Docker/GCP commands to Commands section
- Add `application-prod.yml` to Configuration section

**Verification:**
- [ ] `./go.sh dbuild` builds Docker image successfully
- [ ] `./go.sh drun` runs container locally on port 8080
- [ ] `curl localhost:8080/` returns "Hello, World!" from container
- [ ] GitHub Actions deploy workflow succeeds
- [ ] Cloud Run service responds correctly

---

## Final Project Structure

```
kinalytics/
├── build.gradle.kts
├── settings.gradle.kts
├── go.sh
├── README.md
├── Dockerfile                          # Phase 6
├── .dockerignore                       # Phase 6
├── .claude/
│   └── CLAUDE.md
├── hooks/
│   ├── pre-commit
│   └── pre-push
├── gradle/wrapper/
├── .github/workflows/
│   ├── test.yml
│   └── deploy.yml                      # Phase 6
└── src/
    ├── main/
    │   ├── kotlin/com/finalytics/
    │   │   ├── FinalyticsApplication.kt
    │   │   ├── config/AppConfig.kt                     # Phase 3
    │   │   ├── domain/Transaction.kt                   # Phase 3
    │   │   ├── infrastructure/
    │   │   │   ├── TransactionRepository.kt            # Phase 3
    │   │   │   ├── CsvTransactionRepository.kt         # Phase 3
    │   │   │   └── RepositoryFactory.kt                # Phase 3
    │   │   ├── application/TransactionService.kt       # Phase 4
    │   │   └── controller/
    │   │       ├── HelloController.kt                  # Phase 2
    │   │       └── TransactionController.kt            # Phase 5
    │   └── resources/
    │       ├── application.yml
    │       └── data/TX_example.csv                     # Phase 3
    └── test/
        ├── kotlin/com/finalytics/
        │   ├── unit/
        │   │   ├── CsvTransactionRepositoryTest.kt     # Phase 3
        │   │   ├── TransactionServiceTest.kt           # Phase 4
        │   │   └── RepositoryFactoryTest.kt            # Phase 3
        │   └── bdd/
        │       ├── CucumberConfig.kt                   # Phase 2
        │       └── steps/
        │           ├── HelloSteps.kt                   # Phase 2
        │           └── TransactionsSteps.kt            # Phase 5
        └── resources/features/
            ├── hello.feature                           # Phase 2
            └── transactions.feature                    # Phase 5
```
