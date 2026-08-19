package bmi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class BMICalculatorTest {
    private WebDriver driver;
    String testedURL= "http://127.0.0.1:5500/lab/u09_web_testing/bmi.html";

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(4000));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testBMICalculator() {
        // 開啟 BMI 計算器頁面
        driver.get(testedURL);

        // 獲取表單元件
        WebElement nameInput = driver.findElement(By.id("name"));
        WebElement heightInput = driver.findElement(By.id("height"));
        WebElement weightInput = driver.findElement(By.id("weight"));
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));

        // 填寫表單
        nameInput.clear();
        nameInput.sendKeys("Nick");
        heightInput.clear();
        heightInput.sendKeys("172");
        weightInput.clear();
        weightInput.sendKeys("67");

        // 提交
        submitButton.click();

        // 驗證結果
        WebElement greeting = driver.findElement(By.id("greeting"));
        assertEquals("你好，Nick！", greeting.getText());

        WebElement bmiValue = driver.findElement(By.id("bmiValue"));
        assertEquals("你的 BMI 值為：22.65", bmiValue.getText());

        WebElement bmiStatus = driver.findElement(By.id("bmiStatus"));
        assertEquals("你的健康狀態：適中", bmiStatus.getText());
    }

    @Test
    void testClearForm() {
        // 開啟 BMI 計算器頁面
        driver.get(testedURL);

        // 獲取表單元件
        WebElement nameInput = driver.findElement(By.id("name"));
        WebElement heightInput = driver.findElement(By.id("height"));
        WebElement weightInput = driver.findElement(By.id("weight"));
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='計算 BMI']"));
        WebElement clearButton = driver.findElement(By.xpath("//button[text()='清除資料']"));

        // 填寫表單
        nameInput.sendKeys("測試者");
        heightInput.sendKeys("170");
        weightInput.sendKeys("65");

        // 按下提交
        submitButton.click();

        // 等待結果顯示
        WebElement resultSection = driver.findElement(By.id("result"));
        assertTrue(resultSection.isDisplayed(), "Error! 結果應顯示");

        // 驗證 BMI 計算結果
        WebElement bmiValueElement = driver.findElement(By.id("bmiValue"));
        String bmiText = bmiValueElement.getText();

        assertNotEquals("", bmiText);

        clearButton.click();

        // 驗證輸入欄位已清空
        assertEquals("", nameInput.getAttribute("value"));
        assertEquals("", heightInput.getAttribute("value"));
        assertEquals("", weightInput.getAttribute("value"));

        // 驗證結果隱藏
        WebElement result = driver.findElement(By.id("result"));
        assertEquals("hidden", result.getAttribute("class"));
    }
}
