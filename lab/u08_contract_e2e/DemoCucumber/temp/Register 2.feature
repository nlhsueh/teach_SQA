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
    Then user should be registered successfully
    Examples:
      | First name | Lastname | Email             | Telephone | Password | Password confirm |
      | John       | Doe      | johndoe@gmail.com | 123456789 | 123456   | 123456           |