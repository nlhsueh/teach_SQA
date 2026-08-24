## 🥒 Cucumber 與 BDD：行為驅動開發實戰

-----

### **一、行為驅動開發 (BDD) 與 Cucumber 簡介**

Behavior-Driven Development (BDD) 是一種敏捷軟體開發方法論，強調**跨職能團隊**（開發者、測試者、業務人員）之間的協作。

#### **什麼是 BDD？**

BDD 使用**共同語言（Ubiquitous Language）**，以自然語言和具體範例（Scenarios）來定義功能需求，確保所有成員對軟體行為有一致的理解。

#### **什麼是 Cucumber？**

Cucumber 是實現 BDD 的一個流行測試工具。它能夠解析用自然語言（**Gherkin 語法**）編寫的需求文件，並將其連結到實際的測試程式碼上執行。

-----

### **二、核心概念與三層結構**

Cucumber 的工作流程建立在一個清晰的**三層結構**上：

| 結構層次 | 文件/元件 | 說明 | 關鍵技術 |
| :--- | :--- | :--- | :--- |
| **1. 業務需求層** | **Feature File** (`.feature`) | 以 Gherkin 語法描述功能規格和測試場景。 | Gherkin |
| **2. 步驟定義層** | **Step Definitions** | 將 Gherkin 語法中的步驟映射到實際的程式碼執行邏輯。 | Java, Python, Ruby 等 |
| **3. 應用邏輯層** | **Runner/Application** | 完成功能實作，或透過 Runner 類別來觸發和執行測試。 | JUnit 5, Selenium 等 |

#### **Gherkin 語法**

這是 BDD 的核心，用於編寫 Feature File。

  * `Feature`: 描述一項功能。
  * `Scenario`: 描述一個具體的測試情境。
  * `Given`: 描述測試開始前的**前置條件**。
  * `When`: 描述測試過程中的**動作**或事件。
  * `Then`: 描述動作發生後的**預期結果**。

-----

### **三、Cucumber 實戰範例：BMI 計算器**

本範例展示如何整合 Cucumber、JUnit 5 與 Selenium，進行網頁的 BDD 測試。

#### **1. 專案設定與依賴 (`pom.xml`)**

專案需要 Cucumber 與測試框架（JUnit 5）的整合依賴，以及網頁自動化工具（Selenium）。

新增以下依賴至 `pom.xml`：  

```xml
<dependencies>
    <!-- Cucumber 與 JUnit 5 整合 -->
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-java</artifactId>
        <version>7.14.0</version>
    </dependency>
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-junit-platform-engine</artifactId>
        <version>7.14.0</version>
    </dependency>

    <!-- Selenium 驅動 -->
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.15.0</version>
    </dependency>
</dependencies>
```

#### **2. 目錄結構**

標準的 Maven/Cucumber 結構，將 Feature File 放在 `resources` 下，Java 程式碼放在 `java` 下。

```plaintext
src/
├── test/
    ├── java/
    │   └── bmi/
    │       ├── MyStepdefs.java   # 步驟定義
    │       └── RunCucumberTest.java # 測試入口 (Runner)
    └── resources/
        └── bmi/
            └── bmi.feature   # 測試場景
```

#### **3. 實作 Feature File (`bmi.feature`)**

定義測試場景（例如：成功計算 BMI、清除表單資料）。

```gherkin
Feature: BMI Calculator
  # ... 描述與目的

  Scenario: Calculate BMI successfully
    Given I am on the BMI calculator page
    When I enter "Nick" in the name field
    # ... 其他輸入步驟
    And I click the calculate button
    Then I should see the greeting "你好，Nick！"
    # ... 其他結果驗證步驟
```

#### **4. 實作步驟定義 (`MyStepdefs.java`)**

將 Feature File 中的 Gherkin 步驟連結到實際的 Selenium 網頁操作和 JUnit 斷言邏輯。

```java
// 使用 @Given, @When, @Then 標籤對應 Gherkin 步驟
public class MyStepdefs {
    private WebDriver driver;
    
    @Before /* 設定瀏覽器 */
    @After /* 關閉瀏覽器 */
    
    @Given("I am on the BMI calculator page")
    public void i_am_on_the_bmi_calculator_page() {
        driver.get(testedURL);
    }
    
    @When("I click the calculate button")
    public void i_click_the_calculate_button() {
        // ... 點擊計算按鈕的程式碼
    }

    @Then("I should see the greeting {string}")
    public void i_should_see_the_greeting(String expectedGreeting) {
        // ... 驗證結果的程式碼
    }
}
```

#### **5. 設置測試入口 (`RunCucumberTest.java`)**

使用 JUnit 5 的 `@Cucumber` 註解來啟動測試。

```java
package bmi;

import io.cucumber.junit.platform.engine.Cucumber;

@Cucumber
public class RunCucumberTest {
}
```

-----

### **四、BDD 的優勢與應用**

BDD 的主要優勢在於：

1.  **促進溝通：** 共同語言消除了技術與業務之間的隔閡。
2.  **可讀性高：** Feature File 成為清晰易懂的需求文檔。
3.  **提升覆蓋率：** 每個場景都會被測試，減少漏測。
4.  **減少返工：** 基於明確需求進行開發，減少誤解。

-----

### **五、延伸練習 (Lab)**

以下是原文件中提供的兩個實戰練習，建議您基於上述 Cucumber 框架進行實作：

#### **Lab 1: Child BMI ⚖️**

  * **任務：** 修改現有的 BMI 程式和測試，添加一個「是否為兒童」的選項。
  * **邏輯：** 如果是兒童，計算 BMI 但**不顯示**健康狀態（過重、過輕或正常）。
  * **行動：** 撰寫新的 Cucumber 情境 (`Scenario`) 來測試此兒童邏輯。

#### **Lab 2: Login in 🔑**

  * **任務：** 撰寫一個登入網頁（HTML/CSS/JS 即可，資料存於 JS）。
  * **功能：** 註冊、登入、忘記密碼（需提示）、錯誤三次鎖定。
  * **行動：** 撰寫 Cucumber 情境來測試上述所有登入、註冊和錯誤鎖定功能。

-----

您想讓我針對上述的任何一個 Lab 練習，為您撰寫一個初步的 **Feature File** 範本嗎？