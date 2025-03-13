Feature: User Management

  Scenario: Successfully creating a user
    Given a user with username "mohamed_medhat" and email "mmr973320@example.com"
    When the user is registered
    Then the response should contain the username "mohamed_medhat"

  Scenario: Creating a user that already exists
    Given a user with username "mohamed_medhat" and email "mmr973320@example.com" already exists
    When the user is registered
    Then an error "User with username 'mohamed_medhat' already exists" should be returned
