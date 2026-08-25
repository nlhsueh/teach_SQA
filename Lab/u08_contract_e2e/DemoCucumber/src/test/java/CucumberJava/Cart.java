package CucumberJava;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class Cart {
    WebDriver driver;

    @Before
    public void beforeScenario() {
        System.out.println("This will run before the Scenario");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(4000));
        driver.get("https://tutorialsninja.com/demo/");
    }

    @Given("User navigate to the Phones&PDAs page")
    public void userNavigateToThePhonesPDAsPage() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath(
                "//a[text()='Phones & PDAs']"));
        element.click();
    }

    @When("I click on the iPhone link")
    public void iClickOnTheIPhoneLink() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//a[text()='iPhone']"));
        element.click();
    }

    @And("I enter {int} in the quantity field")
    public void iEnterInTheQuantityField(int quantity) throws InterruptedException {
        WebElement element = driver.findElement(By.id("input-quantity"));
        element.clear();
        element.sendKeys(String.valueOf(quantity));
    }

    @And("I click on the Add to cart button")
    public void iClickOnTheAddToCartButton() throws InterruptedException {
        WebElement element = driver.findElement(By.id("button-cart"));
        element.click();
    }

    @Then("I should see the Item added to cart message")
    public void iShouldSeeTheItemAddedToCartMessage() {
        Assert.assertTrue(driver.findElement(By.xpath(
                        "//div[@class= 'alert alert-success" + " alert-dismissible']")).
                getText().contains("Success: You have added iPhone to your shopping cart!"));
    }

    @And("I click cart button")
    public void iClickCartButton() {
        WebElement element = driver.findElement(By.xpath(
                "//button[@class='btn " +
                        "btn-inverse btn-block btn-lg dropdown-toggle']"));
        element.click();
    }

    @Then("I should see the item in the cart")
    public void iShouldSeeTheItemInTheCart() {
        Assert.assertTrue(driver.findElement(By.xpath("//a[text()='iPhone']")).
                getText().contains("iPhone"));
    }

    @And("the total price is 123.20")
    public void thePriceIs() {
        Assert.assertTrue(driver.findElement(By.xpath(
                "//td[text()='$123.20']")).getText().contains("$123.20"));
    }

    @And("I will see the quantity field is x1")
    public void iWillSeeTheQuantityFieldIsX() {
        Assert.assertTrue(driver.findElement(By.xpath(
                "//td[text()='x1']")).getText().contains("x1"));
    }

    @When("I click on cart button")
    public void iClickOnCartButton() {
        WebElement element = driver.findElement(By.xpath(
                "//button[@class='btn " +
                        "btn-inverse btn-block btn-lg dropdown-toggle']"));
        element.click();
    }

    @Then("I should see the message that Your shopping cart is empty!")
    public void iShouldSeeTheMessageThatYourShoppingCartIsEmpty() {
        Assert.assertTrue(driver.findElement(By.xpath(
                        "//p[text()='Your shopping cart is empty!']")).
                getText().contains("Your shopping cart is empty!"));
    }

    @When("I click the cancel button")
    public void iClickTheCancelButton() {
        WebElement element = driver.findElement(
                By.xpath("//button[@title='Remove']"));
        element.click();
    }
}
