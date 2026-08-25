package CucumberJava;

import io.cucumber.java.*;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class Register {

    WebDriver driver;

    @Before
    public void beforeScenario() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
        driver.get("https://tutorialsninja.com/demo/");
    }

    @Given("user navigate to the register page")
    public void userNavigateToTheRegisterPage() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//a[@title='My Account']"));
        element.click();
        WebElement elementLogin = driver.findElement(By.linkText("Register"));
        elementLogin.click();
    }

    @When("user enters {string} into the first name field")
    public void userEntersIntoTheFirstNameField(String FirstName) throws InterruptedException {
        WebElement element = driver.findElement(By.id("input-firstname"));
        element.sendKeys(FirstName);
    }

    @And("user enters {string} into the last name field")
    public void userEntersIntoTheLastNameField(String LastName) throws InterruptedException {
        WebElement element = driver.findElement(By.id("input-lastname"));
        element.sendKeys(LastName);
    }

    @And("user enters {string} into the email field")
    public void userEntersIntoTheEmailField(String email) throws InterruptedException {
        WebElement element = driver.findElement(By.id("input-email"));
        element.sendKeys(email);
    }

    @And("user enters {string} into the telephone field")
    public void userEntersIntoTheTelephoneField(String telephone) throws InterruptedException {
        WebElement element = driver.findElement(By.id("input-telephone"));
        element.sendKeys(telephone);
    }

    @And("user enters {string}  into the password field")
    public void userEntersIntoThePasswordField(String password) throws InterruptedException {
        WebElement element = driver.findElement(By.id("input-password"));
        element.sendKeys(password);
    }

    @And("user enters {string} into the password confirm field")
    public void userEntersIntoThePasswordConfirmField(String passwordConfirm) throws InterruptedException {
        WebElement element = driver.findElement(By.id("input-confirm"));
        element.sendKeys(passwordConfirm);
    }

    @And("user clicks on the privacy policy checkbox")
    public void userClicksOnThePrivacyPolicyCheckbox() throws InterruptedException {
        WebElement element = driver.findElement(By.name("agree"));
        element.click();
    }

    @And("user clicks on the continue button")
    public void userClicksOnTheContinueButton() throws InterruptedException {
        WebElement button = driver.findElement(By.xpath("//input[@value='Continue']"));
        button.click();
    }

    @Then("user should be registered fail")
    public void userShouldBeRegisteredSuccessfully() {
        Assert.assertTrue(driver.findElement(By.xpath(
                        "//div[contains(@class,'alert-dismissible')]")).getText().
                contains("Warning: E-Mail Address is already registered!"));
    }

    @And("The message of Password confirmation does not match password! will display")
    public void theMessageOfPasswordConfirmationDoesNotMatchPasswordWillDisplay() {
        Assert.assertTrue(driver.findElement(By.className("text-danger")).
                getText().contains("Password confirmation does not match password!"));
    }

    @Then("the message of You must agree to the Privacy Policy! will display")
    public void theMessageOfYouMustAgreeToThePrivacyPolicyWillDisplay() {
        Assert.assertTrue(driver.findElement(By.xpath(
                        "//div[contains(@class,'alert-dismissible')]")).getText().
                contains("Warning: You must agree to the Privacy Policy!"));
    }

    @And("the message of Telephone must be between 3 and 32 characters! will display")
    public void theMessageOfTelephoneMustBeBetweenAndCharactersWillDisplay() {
        Assert.assertTrue(driver.findElement(By.className("text-danger")).
                getText().contains("Telephone must be between 3 and 32 characters!"));
    }

    @And("the message of Password must be between 4 and 20 characters!")
    public void theMessageOfPasswordMustBeBetweenAndCharacters() {
        Assert.assertTrue(driver.findElement(By.className("text-danger")).
                getText().contains("Password must be between 4 and 20 characters!"));
    }
}
