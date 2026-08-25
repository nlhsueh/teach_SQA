package CucumberJava;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

//import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class Login {
    // WebDriver driver = new  ChromeDriver() ;
    WebDriver driver;

    @Before
    public void beforeScenario() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");   // 允許遠端控制瀏覽器
        options.addArguments("--headless");                 // 不開啟瀏覽器
        driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();                 // 刪除瀏覽器所有 cookie
        driver.manage().window().maximize();                // 最大化瀏覽器
        // 設定 2 sec 的搜尋等待時間 (for driver.findElement)
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
        driver.get("https://tutorialsninja.com/demo/");     // 進入網址內容
    }


    @Given("user navigate to the login page")
    public void userAreOnTheLoginPage() throws InterruptedException {
        /*
            find element by xpath:
                //a: 找所有 a 標記
                @title='My Account' title 的屬性為 My Account
         */
        WebElement element = driver.findElement(By.xpath("//a[@title='My Account']"));
        element.click();        // 點開 My Account
        WebElement elementLogin = driver.findElement(By.linkText("Login"));
        elementLogin.click();   // 點下 Login
    }

    @When("User enters valid email address {string} into the email field")
    public void userEntersAnd(String email) throws InterruptedException {
        WebElement element = driver.findElement(By.id("input-email"));
        element.sendKeys(email);
    }

    @And("User enters valid password {string} into the password field")
    public void userEntersValidPasswordIntoThePasswordField(String password) throws InterruptedException {
        WebElement element = driver.findElement(By.id("input-password"));
        element.sendKeys(password);
    }

    @And("User clicks on the login button")
    public void userClicksOnTheLoginButton() throws InterruptedException {
        WebElement button = driver.findElement(By.xpath("//input[@value='Login']"));
        button.click();
    }

    @Then("user should be logged in")
    public void userShouldBeLoggedIn() {
        Assert.assertTrue(driver.findElement(By.linkText("Change your password")).isDisplayed());
        driver.quit();
    }

    @And("User enters invalid password {string} into the password field")
    public void userEntersInvalidPasswordIntoThePasswordField(String invalidPassword) throws InterruptedException {
        WebElement element = driver.findElement(By.id("input-password"));
        element.sendKeys(invalidPassword);
    }


    @Then("user should get an error message about invalid credentials")
    public void userShouldGetAnErrorMessageAboutInvalidCredentials() throws InterruptedException {
        //確認是否登入失敗
        Assert.assertTrue(driver.findElement(By.xpath(
                        "//div[@class='alert alert-danger alert-dismissible']")).
                getText().contains("Warning: No match for E-Mail Address and/or Password."));
        driver.quit();
    }
}
