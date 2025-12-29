Feature: Hello World
    As a user
    I want to visit the homepage
    So that I can see a greeting

    Scenario: User visits the homepage
        Given the application is running
        When I visit the homepage
        Then I should see "Hello, World!"
