
Feature: BMI Calculator
  To help users calculate their BMI and understand their health status

Scenario Outline: Calculate BMI with outline successfully
Given I am on the BMI calculator page
When I enter "<name>" in the name field
And I enter "<height>" in the height field
And I enter "<weight>" in the weight field
And I click the calculate button
Then I should see the greeting "你好，<name>！"
And I should see the BMI value "你的 BMI 值為：<bmiValue>"
And I should see the BMI status "你的健康狀態：<bmiStatus>"

Examples:
  | name | height | weight | bmiValue | bmiStatus |
  | Nick | 172    | 67     | 22.65    | 適中        |
  | Anna | 160    | 45     | 17.58    | 過輕        |
  | John | 180    | 85     | 26.23    | 過重        |