package bmi.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class BMICalculatorStepDefs {
    private WebDriver driver;
    private final String testedURL = "http://127.0.0.1:5500/lab/u09_web_testing/bmi.html";

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I am on the BMI calculator page")
    public void i_am_on_the_bmi_calculator_page() {
        driver.get(testedURL);
    }

    @When("I enter {string} in the name field")
    public void i_enter_in_the_name_field(String name) {
        WebElement nameInput = driver.findElement(By.id("name"));
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    @When("I enter {string} in the height field")
    public void i_enter_in_the_height_field(String height) {
        WebElement heightInput = driver.findElement(By.id("height"));
        heightInput.clear();
        heightInput.sendKeys(height);
    }

    @When("I enter {string} in the weight field")
    public void i_enter_in_the_weight_field(String weight) {
        WebElement weightInput = driver.findElement(By.id("weight"));
        weightInput.clear();
        weightInput.sendKeys(weight);
    }

    @When("I click the calculate button")
    public void i_click_the_calculate_button() {
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();
    }

    @Then("I should see the greeting {string}")
    public void i_should_see_the_greeting(String expectedGreeting) {
        WebElement greeting = driver.findElement(By.id("greeting"));
        assertEquals(expectedGreeting, greeting.getText());
    }

    @Then("I should see the BMI value {string}")
    public void i_should_see_the_bmi_value(String expectedBMI) {
        WebElement bmiValue = driver.findElement(By.id("bmiValue"));
        assertEquals(expectedBMI, bmiValue.getText());
    }

    @Then("I should see the BMI status {string}")
    public void i_should_see_the_bmi_status(String expectedStatus) {
        WebElement bmiStatus = driver.findElement(By.id("bmiStatus"));
        assertEquals(expectedStatus, bmiStatus.getText());
    }

    @When("I click the clear button")
    public void i_click_the_clear_button() {
        WebElement clearButton = driver.findElement(By.xpath("//button[text()='清除資料']"));
        clearButton.click();
    }

    @Then("the name field should be empty")
    public void the_name_field_should_be_empty() {
        WebElement nameInput = driver.findElement(By.id("name"));
        assertEquals("", nameInput.getAttribute("value"));
    }

    @Then("the height field should be empty")
    public void the_height_field_should_be_empty() {
        WebElement heightInput = driver.findElement(By.id("height"));
        assertEquals("", heightInput.getAttribute("value"));
    }

    @Then("the weight field should be empty")
    public void the_weight_field_should_be_empty() {
        WebElement weightInput = driver.findElement(By.id("weight"));
        assertEquals("", weightInput.getAttribute("value"));
    }

    @Then("the result section should be hidden")
    public void the_result_section_should_be_hidden() {
        WebElement resultSection = driver.findElement(By.id("result"));
        assertEquals("hidden", resultSection.getAttribute("class"));
    }
}
