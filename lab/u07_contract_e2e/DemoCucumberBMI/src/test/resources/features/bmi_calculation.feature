Feature: BMI Calculator
  To help users calculate their BMI and understand their health status

  Scenario: Calculate BMI successfully
    Given I am on the BMI calculator page
    When I enter "Nick" in the name field
    And I enter "172" in the height field
    And I enter "67" in the weight field
    And I click the calculate button
    Then I should see the greeting "你好，Nick！"
    And I should see the BMI value "你的 BMI 值為：22.65"
    And I should see the BMI status "你的健康狀態：適中"




  Scenario: Clear all form fields
    Given I am on the BMI calculator page
    When I enter "測試者" in the name field
    And I enter "170" in the height field
    And I enter "65" in the weight field
    And I click the calculate button
    Then I should see the BMI value "你的 BMI 值為：22.49"
    And I click the clear button
    Then the name field should be empty
    And the height field should be empty
    And the weight field should be empty
    And the result section should be hidden



Scenario: Clear form when incomplete input
  Given I am on the BMI calculator page
  When I enter "John" in the name field
  And I enter "172" in the height field
  And I click the clear button
  Then the name field should be empty
  And the height field should be empty
  And the weight field should be empty
  And the result section should be hidden






