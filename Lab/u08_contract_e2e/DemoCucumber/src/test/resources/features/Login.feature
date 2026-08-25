Feature: Beer cans

  Scenario Outline: Logging
    Given user navigate to the login page
    When User enters valid email address <email> into the email field
    And User enters valid password <password> into the password field
    And User clicks on the login button
    Then user should be logged in

    Examples:
      | email                | password |
      | "wp900622@gmail.com" | "12345"  |

  Scenario: Logging in with invalid credentials
    Given user navigate to the login page
    When User enters valid email address "wp900622@gmail.com" into the email field
    And User enters invalid password " " into the password field
    And User clicks on the login button
    Then user should get an error message about invalid credentials


  Scenario: Logging in with invalid credentials
    Given user navigate to the login page
    When User enters valid email address " " into the email field
    And User enters invalid password " " into the password field
    And User clicks on the login button
    Then user should get an error message about invalid credentials

  Scenario: Logging in with invalid credentials
    Given user navigate to the login page
    When User enters valid email address " " into the email field
    And User enters invalid password "12345" into the password field
    And User clicks on the login button
    Then user should get an error message about invalid credentials

