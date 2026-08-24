Feature: Add new item to the cart
  Scenario: Add new item to the cart
    Given User navigate to the Phones&PDAs page
    When I click on the iPhone link
    And  I enter 1 in the quantity field
    And  I click on the Add to cart button
    Then I should see the Item added to cart message