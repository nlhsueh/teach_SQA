# 實習 11：微服務契約測試 (Pact) ＆ 現代 Playwright E2E 自動化

> 🎯 **實習目標**：
> 1. 掌握微服務與前後端分離架構下的防破壞神兵——**消費者驅動契約測試 (Consumer-Driven Contract Testing with Pact)**。
> 2. 淘汰脆弱、緩慢的老舊 Selenium，全面升級為微軟現代化 **Playwright (Java)** 端到端 (E2E) 測試。
> 3. 體驗 Playwright 的自動等待 (Auto-waiting)、錄影回放 (Video Tracing) 與全自動截圖能力。

---

## Part 1：微服務契約測試 (Contract Testing with Pact)

### 1. 為什麼前後端/微服務需要契約測試？
在微服務架構中，後端修改了一個 API 欄位名稱（例如 `user_id` 改成 `userId`），傳統整合測試很難即刻抓到，直到前端或下游服務崩潰才發現。
* **Pact 哲學**：由前端（消費者 Consumer）定義「我需要的 API 格式與預期回應」，生成 `pact.json` 契約檔案。
* 後端（提供者 Provider）在 CI 建構時自動驗證自己是否打破了這份契約！

### 2. Consumer 端定義契約範例 (Java JUnit 5)
```java
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "UserService")
public class UserConsumerContractTest {

    @Pact(consumer = "FrontendApp")
    public V4Pact createContract(PactDslWithProvider builder) {
        return builder
            .given("使用者 ID 123 存在")
            .uponReceiving("查詢使用者 123 的請求")
                .path("/api/v1/users/123")
                .method("GET")
            .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                    .integerType("id", 123)
                    .stringType("username", "alice")
                    .stringMatcher("email", ".*@.*", "alice@example.com"))
            .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createContract")
    void testUserApiConsumer(MockServer mockServer) {
        // 使用 Pact 自動建立的 MockServer 進行前端 API 呼叫驗證
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(mockServer.getUrl() + "/api/v1/users/123", String.class);
        assertEquals(200, response.getStatusCodeValue());
    }
}
```

---

## Part 2：現代 Playwright Web E2E 自動化測試

### 1. 為什麼選擇 Playwright 而非 Selenium？
* **零 Flaky 測試**：Playwright 內建「智慧自動等待（Auto-waiting）」，在點擊元素前自動等待元素可見、啟用且動畫停止，不再需要手寫 `Thread.sleep` 或脆弱的顯式等待！
* **多瀏覽器原生支援**：一行代碼切換 Chromium (Chrome/Edge)、Firefox 與 WebKit (Safari)。
* **測試軌跡與錄影 (Trace Viewer)**：出錯時可查看毫秒級的 DOM 快照、網路請求瀑布圖與螢幕截圖。

### 2. Maven 依賴 (`pom.xml`)
```xml
<dependency>
    <groupId>com.microsoft.playwright</groupId>
    <artifactId>playwright</artifactId>
    <version>1.43.0</version>
    <scope>test</scope>
</dependency>
```

### 3. Playwright 實戰測試範例
```java
package lab.sqa.e2e;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class WebCheckoutE2ETest {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        // headless = false 可在除錯時看到真實瀏覽器視窗彈出
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        // 啟動錄影與軌跡記錄
        context = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(java.nio.file.Paths.get("target/videos/")));
        page = context.newPage();
    }

    @Test
    void testUserLoginAndPurchaseFlow() {
        page.navigate("https://demo.playwright.dev/todomvc/");

        // 新增待辦事項
        Locator newTodo = page.locator(".new-todo");
        newTodo.fill("完成 SQA 契約測試實習");
        newTodo.press("Enter");

        newTodo.fill("打造紅藍攻防專題");
        newTodo.press("Enter");

        // 斷言待辦清單項目數為 2
        Locator todoItems = page.locator(".todo-list li");
        assertEquals(2, todoItems.count());

        // 勾選第一個項目完成
        todoItems.first().locator(".toggle").check();
        
        // 驗證第一個項目已被劃上刪除線
        assertTrue(todoItems.first().getAttribute("class").contains("completed"));
    }
}
```

---

## 📋 實習成果驗收標準
1. [ ] 撰寫一組 Pact Consumer 測試，成功生成 `target/pacts/FrontendApp-UserService.json` 契約檔案。
2. [ ] 撰寫 Playwright 自動化測試腳本，涵蓋一個完整的 Web 表單提交與驗證流程。
3. [ ] 啟用 Playwright Video/Trace 錄影功能，輸出測試運行影片或 Trace 檔案。
