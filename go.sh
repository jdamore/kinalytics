#!/bin/bash

# Kinalytics - Helper Script
# Usage: ./go.sh [command]

set -euo pipefail

show_help() {
    echo "Usage: ./go.sh [command]"
    echo ""
    echo "Commands:"
    echo "  help     Show this help message"
    echo "  build    Build the project"
    echo "  test     Run all tests"
    echo "  local    Run the application locally"
    echo "  clean    Clean build artifacts"
    echo "  hooks    Install git hooks"
}

run_build() {
    echo "Building project..."
    ./gradlew build -x test
}

run_tests() {
    echo "Running tests..."
    ./gradlew test "$@"
}

run_local() {
    echo "Starting application locally..."
    SPRING_PROFILES_ACTIVE=local ./gradlew bootRun
}

run_clean() {
    echo "Cleaning build artifacts..."
    ./gradlew clean
}

run_hooks() {
    echo "Installing git hooks..."
    cp hooks/pre-commit .git/hooks/pre-commit
    cp hooks/pre-push .git/hooks/pre-push
    chmod +x .git/hooks/pre-commit .git/hooks/pre-push
    echo "Git hooks installed."
}

case "${1:-help}" in
    help)
        show_help
        ;;
    build)
        run_build
        ;;
    test)
        shift
        run_tests "$@"
        ;;
    local)
        run_local
        ;;
    clean)
        run_clean
        ;;
    hooks)
        run_hooks
        ;;
    *)
        echo "Unknown command: $1"
        echo ""
        show_help
        exit 1
        ;;
esac
