@ignore
Feature: Registering a new user

  Scenario Outline: User registers with valid data
    Given user navigate to the register page
    When user enters "<First name>" into the first name field
    And user enters "<Lastname>" into the last name field
    And user enters "<Email>" into the email field
    And user enters "<Telephone>" into the telephone field
    And user enters "<Password>"  into the password field
    And user enters "<Password confirm>" into the password confirm field
    And user clicks on the privacy policy checkbox
    And user clicks on the continue button
    Then user should be registered fail
    Examples:
      | First name | Lastname | Email             | Telephone | Password | Password confirm |
      | John       | Doe      | johndoe@gmail.com | 123456789 | 123456   | 123456           |
      | John       | Doe      | johndoe@gmail.com | 123456789 | 123456   | 145689           |

  Scenario: User registers with valid data
    Given user navigate to the register page
    When user enters "John" into the first name field
    And user enters "Doe" into the last name field
    And user enters "johndoe@gmail.com" into the email field
    And user enters "123456789" into the telephone field
    And user enters "123456"  into the password field
    And user enters "145689" into the password confirm field
    And user clicks on the privacy policy checkbox
    And user clicks on the continue button
    Then user should be registered fail
    And The message of Password confirmation does not match password! will display

  Scenario: User registers with valid data
    Given user navigate to the register page
    When user enters "John" into the first name field
    And user enters "Doe" into the last name field
    And user enters "johndoe@gmail.com" into the email field
    And user enters "123456789" into the telephone field
    And user enters "123456"  into the password field
    And user enters "123456" into the password confirm field
    And user clicks on the continue button
    Then the message of You must agree to the Privacy Policy! will display

  Scenario: User registers with valid data
    Given user navigate to the register page
    When user enters "John" into the first name field
    And user enters "Doe" into the last name field
    And user enters "johndoe@gmail.com" into the email field
    And user enters " " into the telephone field
    And user enters "123456"  into the password field
    And user enters "123456" into the password confirm field
    And user clicks on the privacy policy checkbox
    And user clicks on the continue button
    Then user should be registered fail
    And the message of Telephone must be between 3 and 32 characters! will display

  Scenario: User registers with valid data
    Given user navigate to the register page
    When user enters "John" into the first name field
    And user enters "Doe" into the last name field
    And user enters "johndoe@gmail.com" into the email field
    And user enters "123456789" into the telephone field
    And user enters " "  into the password field
    And user enters " " into the password confirm field
    And user clicks on the privacy policy checkbox
    And user clicks on the continue button
    Then user should be registered fail
    And the message of Password must be between 4 and 20 characters!
