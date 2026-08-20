# **Selenium 介紹：以 BMI 測試範例為例**

---

## **什麼是 Selenium？**

**Selenium** 是一個開源的自動化測試框架，用於測試 Web 應用程式。  
它支援多種瀏覽器（如 Chrome、Firefox）與程式語言（如 Java、Python、C# 等），可以模擬使用者在瀏覽器中的操作，例如點擊、輸入文字、檢查元素等。

---

### **Selenium 的核心概念**

1. **WebDriver**  
   - Selenium 的核心組件，用於控制瀏覽器操作。

2. **定位元素（Element Locators）**  
   - 使用不同方法（如 ID、XPath、CSS Selector）來尋找網頁元素。

3. **操作元素**  
   - 透過 API 操作元素，例如輸入文字、點擊按鈕等。

4. **驗證測試結果**  
   - 使用斷言（assertions）檢查結果是否符合預期。

---

## **安裝與設定**

### **1. 建立 Maven 專案**
在 `pom.xml` 中新增以下依賴：

```xml
    <dependencies>
        <!-- Selenium WebDriver -->
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>4.12.0</version>
        </dependency>

        <!-- JUnit 5 -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.8.1</version>
            <scope>test</scope>
        </dependency>

    </dependencies>
```

---

### **2. 設定目錄結構**

```plaintext
src
├── main
│   └── java
├── test
    ├── java
    │   └── bmi
    │       └── BMICalculatorTest.java  # 測試類別
    └── resources
        └── bmi.html  # 測試目標 HTML 文件
```

---

## **範例測試：BMI 計算器**

### **1. 測試背景介紹**
BMI 計算器的功能：  
1. 使用者輸入名字、身高、體重後，點擊「計算」按鈕。  
2. 顯示計算結果，包括問候語、BMI 值與健康狀態。  
3. 點擊「清除」按鈕後，所有欄位與結果區域應清空或隱藏。

---

### **2. 實作測試類別**

以下是完整的測試範例程式碼，模擬使用者操作 BMI 計算器的流程。

```java
package bmi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Dimension;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class BMICalculatorVisibleTest {
    private WebDriver driver;
    String testedURL = "http://127.0.0.1:5500/lab/u09_web_testing/bmi.html";

    @BeforeEach
void setUp() {
    // 創建 Chrome 瀏覽器選項物件
    ChromeOptions options = new ChromeOptions();

    // 設置允許遠端跨來源的請求
    options.addArguments("--remote-allow-origins=*");
    // 啟用無頭模式 (headless mode)，不顯示瀏覽器 UI（適合自動化測試或無需互動的場景）
    options.addArguments("--headless"); 
    // 使用帶有設定選項的 ChromeDriver 實例來初始化 driver
    driver = new ChromeDriver(options);
    // 設置隱式等待時間，等待元素加載最多 4000 毫秒
    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(4000));
    // 設置瀏覽器視窗的大小為 1200 x 800 像素; headless 則不需要設置
    driver.manage().window().setSize(new Dimension(1200, 800));
}


    @AfterEach
    void tearDown() throws InterruptedException {
        if (driver != null) {
            Thread.sleep(4000); // 延遲 4 秒
            driver.quit();
        }
    }

    @Test
    void testBMICalculator() throws InterruptedException {
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
        Thread.sleep(2000);
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
    void testClearForm() throws InterruptedException {
        driver.get(testedURL);

        WebElement nameInput = driver.findElement(By.id("name"));
        WebElement heightInput = driver.findElement(By.id("height"));
        WebElement weightInput = driver.findElement(By.id("weight"));
        WebElement clearButton = driver.findElement(By.xpath("//button[text()='清除資料']"));

        // 填寫表單
        nameInput.clear();
        nameInput.sendKeys("Nick");
        heightInput.clear();
        heightInput.sendKeys("172");
        weightInput.clear();
        weightInput.sendKeys("67");

        // 按下清除
        Thread.sleep(2000);
        clearButton.click();

        // 驗證欄位清空
        assertEquals("", nameInput.getAttribute("value"));
        assertEquals("", heightInput.getAttribute("value"));
        assertEquals("", weightInput.getAttribute("value"));

        // 驗證結果隱藏
        WebElement result = driver.findElement(By.id("result"));
        assertEquals("hidden", result.getAttribute("class"));
    }
}
```

---

### **3. 測試說明**

- **設置與清理環境**  
  使用 `@BeforeEach` 啟動 ChromeDriver，並設定視窗大小。  
  使用 `@AfterEach` 在測試完成後關閉瀏覽器。

- **操作網頁元素**  
  - 定位元素使用 **`By.id`** 和 **`By.xpath`**。
  - 清空輸入框後填寫數值。
  - 點擊按鈕觸發事件。

- **驗證結果**  
  - 使用斷言（assertions）檢查：
    - 問候語、BMI 值與健康狀態是否正確。
    - 清除按鈕是否成功清空表單並隱藏結果。

---

## **執行測試**

1. 在 IDE 中右鍵測試類別，選擇 **Run 'BMICalculatorVisibleTest'**。
2. 或使用 Maven 執行：
   ```bash
   mvn test
   ```

---

## **常見問題**

1. **瀏覽器無法啟動？**  
   - 確保瀏覽器版本與驅動程式相符。
   - 使用最新版本的 ChromeDriver。

2. **找不到元素？**  
   - 檢查 HTML 結構，確認定位方法是否正確。
   - 加入適當的等待時間以處理網頁載入延遲。

3. **測試不穩定？**  
   - 使用 `driver.manage().timeouts().implicitlyWait()` 設定隱式等待。

---

## **總結**

透過 Selenium，您可以輕鬆模擬瀏覽器操作，驗證 Web 應用程式的功能與行為。本講義結合 BMI 計算器的範例，展示了如何使用 JUnit 5 與 Selenium 整合進行自動化測試。希望本教學對您有所幫助！

## Lab: More test on BMI
* 新增測試案例，使得涵蓋體重正常、過重、過輕的人
