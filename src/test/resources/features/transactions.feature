Feature: Transactions
    As a user
    I want to view my financial transactions
    So that I can track my spending

    Scenario: User retrieves all transactions
        Given the application is running
        And transactions exist in the data file
        When I request the transactions endpoint
        Then I should receive a list of transactions
        And each transaction should have booking date, partner name, amount, and currency
