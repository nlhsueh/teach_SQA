@ignore
Feature: Add new item to the cart

  Scenario: Add new item to the cart
    Given User navigate to the Phones&PDAs page
    When I click on the iPhone link
    And  I enter 1 in the quantity field
    And  I click on the Add to cart button
    Then I should see the Item added to cart message
    And  I click cart button
    Then I should see the item in the cart
    And  I will see the quantity field is x1
    And  the total price is 123.20

  Scenario: Do not buy anything
    Given User navigate to the Phones&PDAs page
    When I click on cart button
    Then I should see the message that Your shopping cart is empty!

  Scenario: I want to delete  items
    Given User navigate to the Phones&PDAs page
    When I click on the iPhone link
    And  I enter 1 in the quantity field
    And  I click on the Add to cart button
    And  I click cart button
    Then I should see the item in the cart
    And  I will see the quantity field is x1
    And  the total price is 123.20
    When  I click the cancel button
    And  I click cart button
    Then I should see the message that Your shopping cart is empty!


