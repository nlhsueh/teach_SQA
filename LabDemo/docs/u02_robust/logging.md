## Logging

Java 提供了多種機制來記錄應用程序執行過程中的日誌（log），這對於錯誤追蹤、除錯和系統監控至關重要。常用的日誌框架包括 Java 標準庫中的 `java.util.logging` 和第三方的 `Log4j`、`SLF4J` 等。以下介紹 Java 的日誌機制和如何在實際應用中使用。

### 1. **`java.util.logging` (JUL)**
Java 提供的內建日誌框架是 `java.util.logging`（JUL）。它是一個輕量級的日誌系統，包含了不同的日誌等級和靈活的處理器，可以記錄到不同的目標（如文件、控制台）。

#### 基本使用方式
```java
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingExample {
    // 一般都是用 class name 來作為 logger 的名字
    private static final Logger logger = Logger.getLogger(LoggingExample.class.getName());

    public static void main(String[] args) {
        logger.info("這是一條 INFO 等級的日誌訊息");
        logger.warning("這是一條 WARNING 等級的日誌訊息");

        try {
            int result = 10 / 0;  // 故意製造一個錯誤
        } catch (ArithmeticException e) {
            logger.log(Level.SEVERE, "發生了算術異常: " + e.getMessage(), e);
        }
    }
}
```

#### 日誌等級
- `SEVERE`：非常嚴重的錯誤，導致程式終止運行。
- `WARNING`：潛在問題或錯誤，但不一定會馬上影響程式。
- `INFO`：一般的運行訊息，用來記錄正常事件。
- `CONFIG`：用來記錄一些設定或配置訊息。
- `FINE`, `FINER`, `FINEST`：更詳細的除錯日誌，常用於開發和除錯階段。


-----

### **2. Log4j 2（功能較強大）**

使用 Maven 配置 Log4j 2 主要分為兩個部分：**添加 Maven 依賴（Dependencies）** 和 **撰寫 Log4j 2 配置文件**。

#### **第一步：配置 Maven 依賴 (pom.xml)**

要使用 Log4j 2，您的 `pom.xml` 檔案中必須包含兩個核心組件：`log4j-api`（介面）和 `log4j-core`（實作）。

建議將 Log4j 的版本號定義在 `<properties>` 區塊，方便管理。

在您的 `pom.xml` 文件中，新增以下內容：

```xml
<properties>
    <log4j2.version>2.20.0</log4j2.version> 
</properties>

<dependencies>
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-api</artifactId>
        <version>${log4j2.version}</version>
    </dependency>
    
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>${log4j2.version}</version>
    </dependency>

    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-slf4j2-impl</artifactId>
        <version>${log4j2.version}</version>
    </dependency>
</dependencies>
```

**小提示：** 雖然你可以直接使用 Log4j 2 的 API，但業界標準更推薦使用 **SLF4J (Simple Logging Facade for Java)** 作為介面，然後讓 Log4j 2 作為底層實作。這樣未來如果要切換日誌框架（例如換成 Logback），程式碼不需要修改。

#### **第二步：撰寫 Log4j 2 配置文件 (log4j2.xml)**

Log4j 2 啟動時會自動在 **Classpath** 中尋找名為 `log4j2.xml`、`log4j2.json`、`log4j2.yaml` 或 `log4j2.properties` 的文件。

在 Maven 專案中，通常是將配置文件放在 **`src/main/resources`** 目錄下。

以下是一個簡單的 **`log4j2.xml`** 範例，它會將 `INFO` 級別及以上的日誌輸出到控制台 (`Console`) 和一個日誌文件 (`File`)：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="ConsoleAppender" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
        
        <File name="FileAppender" fileName="logs/app.log">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </File>
    </Appenders>
    
    <Loggers>
        <Root level="info">
            <AppenderRef ref="ConsoleAppender"/>
            <AppenderRef ref="FileAppender"/>
        </Root>
        
        <Logger name="com.yourpackage.dao" level="debug" additivity="false">
             <AppenderRef ref="ConsoleAppender"/>
        </Logger>
    </Loggers>
</Configuration>
```

**配置說明**

1.  `Appenders` 設定所有輸出，`Loggers` 設定所有對象，透過 `AppenderRef` 來設定不同 `Logger` 採用不同的 `Appender`。
2.  **`<Configuration status="WARN">`**：設定 Log4j 內部日誌的級別。`WARN` 表示只輸出 Log4j 框架本身的警告及錯誤訊息。級別目的與用途:
    * `TRACE`: 最詳細的日誌，用於追蹤程式碼的細微流程。
    * `DEBUG`: 僅供開發人員調試，顯示變數狀態和流程細節。
    * `INFO`: 重要的里程碑事件，例如應用程式啟動、請求開始/結束。
    * `WARN`: 潛在的問題或非預期狀況，但應用程式可以從中恢復。
    * `ERROR`: 執行期錯誤，通常是例外被捕捉，影響到部分功能。
    * `FATAL`: 致命錯誤，導致應用程式無法繼續運作而必須關閉。
3.  **`<Appenders>`**：定義日誌輸出的目的地。
      * `ConsoleAppender`: 輸出到控制台 (`SYSTEM_OUT`)。
      * `FileAppender`: 輸出到指定路徑的檔案 (`logs/app.log`)。
      * `PatternLayout`: 定義日誌的格式。例如 `%d` 是日期，`%-5level` 是日誌級別，`%msg%n` 是日誌內容與換行。
4.  **`<Loggers>`**：定義日誌記錄器。
    * `Logger` 這一行: 「針對所有 `com.yourpackage.dao` 套件下的程式碼，請確保記錄所有 `DEBUG` 級別或更高的日誌，並將它們輸出到`控制台`。請不要將這些日誌事件傳遞給 Root Logger 或任何其他父級紀錄器，以避免重複記錄。(`additivity="false"`)」
    * **`<Root level="info">`**：這是預設的根紀錄器。將日誌級別設定為 `info`，表示 `info`、`warn`、`error`、`fatal` 級別的日誌都會被記錄。
    * **`<AppenderRef ref="...">`**：將 Appenders 綁定到 Logger 上。

#### **第三步：在程式碼中使用 Log4j**

在您的 Java 類別中，使用 Log4j 2 的 API 來實例化 Logger。

```java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyService {

    // 取得 Logger 實例
    private static final Logger logger = LogManager.getLogger(MyService.class);

    public void runLogic() {
        logger.trace("這是 Trace 等級的日誌"); // 級別太低，可能被忽略
        logger.debug("這是 Debug 等級的日誌"); // 級別太低，可能被忽略
        logger.info("應用程式啟動完成，執行中..."); // 會被 Root Logger 記錄 (level="info")
        
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            // 使用 error() 記錄例外，Log4j 會自動處理堆疊追蹤
            logger.error("發生數學錯誤", e); 
        }
    }
}
```

完成以上三個步驟後，執行您的 Maven 專案，Log4j 2 就會根據您的 `log4j2.xml` 配置文件開始記錄日誌了。

See [Here](../../src/main/java/demo/log4j/OrderProcessor.java) for more examples; [log4j.xml](../../src/main/resources/log4j.xml) is the setting file.

---

### **使用日誌的好處**
- **追蹤問題**：通過記錄系統錯誤和異常，方便日後調試和問題排查。
- **系統監控**：日誌可以幫助監控系統運行狀況，如記錄應用程式的性能問題。
- **持久保存**：將重要訊息記錄到文件，可以方便地查閱歷史運行狀況。

### **推薦做法**
- **正確選擇日誌等級**：依照不同嚴重程度選擇適當的等級（INFO、ERROR、DEBUG 等）。
- **避免過多日誌**：日誌過多會導致性能下降，應謹慎選擇日誌的輸出級別和頻率。
- **日誌輪轉**：當日誌文件過大時，使用日誌輪轉機制（如 Log4j 和 Logback 提供的功能）自動管理日誌文件。

通過有效的日誌機制，可以大大提升應用的可維護性和除錯效率。

## 和 err.print() 的差異

如果只是在控制台顯示錯誤訊息，使用 `Logger` 和直接使用 `System.err.println()` 的確都能達到相似的效果，但兩者在設計目的、靈活性和功能上有很大差異。以下是一些重要的區別：

### 1. **靈活性**
- **`Logger`**：`Logger` 是一個強大的日誌框架，具有更大的靈活性。你可以根據需要將日誌輸出到控制台、文件、遠端伺服器或其他輸出設備，甚至可以配置不同的日誌格式、日誌等級、以及不同的處理器（handlers）。此外，你可以通過設定日誌等級來控制不同嚴重程度的訊息是否要顯示。
- **`System.err.println()`**：這只是一個簡單的標準錯誤輸出，通常僅用來在控制台上印出錯誤訊息。它無法提供日誌等級、格式化、自定義輸出目標等功能。

### 2. **日誌等級**
- **`Logger`**：提供多種日誌等級（如 `INFO`、`DEBUG`、`WARNING`、`SEVERE` 等），這使得你可以根據不同的需求來過濾和記錄特定等級的日誌。這樣，在生產環境中，你可以只記錄 `SEVERE` 和 `WARNING` 級別的錯誤，而在開發過程中可以記錄更詳細的 `DEBUG` 或 `FINE` 級別的日誌。
- **`System.err.println()`**：沒有日誌等級概念，所有的輸出都是以同樣的方式處理，無法區分不同嚴重程度的錯誤或事件。

### 3. **格式化**
- **`Logger`**：可以配置不同的日誌格式器（formatter），比如時間戳、類名、執行緒名等資訊，這些訊息在調試和排錯時非常有用。你可以自定義輸出的格式以便更清晰地閱讀和分析。
- **`System.err.println()`**：輸出格式無法自定義，只能簡單地輸出文字，無法提供額外的上下文訊息如時間戳或錯誤來源。

### 4. **配置與控制**
- **`Logger`**：可以根據需要通過配置文件或程式設置來動態改變日誌行為，例如控制日誌是否寫入文件，改變輸出格式，調整日誌等級等。這使得系統可以在不修改程式碼的情況下進行日誌行為的調整。
- **`System.err.println()`**：完全無法配置，並且必須直接修改程式碼才能改變輸出內容。

### 5. **多目標日誌輸出**
- **`Logger`**：可以同時將日誌輸出到多個目標，例如控制台和文件，或甚至遠程伺服器等，這對於大規模應用的錯誤追蹤和系統監控非常有用。
- **`System.err.println()`**：只能輸出到控制台的標準錯誤流，無法擴展到其他目標。

### 6. **性能**
- **`Logger`**：在大規模應用中，日誌框架如 `java.util.logging`、`Log4j`、`Logback` 等，通過異步記錄或日誌輪轉等技術，能有效管理大量日誌的輸出，從而降低性能損耗。
- **`System.err.println()`**：每次都同步地直接輸出到控制台，當記錄大量訊息時，性能可能會變差，尤其是在生產環境中不需要那麼詳細的輸出時，這會消耗資源。

### 7. **錯誤處理**
- **`Logger`**：可以配置不同的處理器來應對不同情境的錯誤處理。例如，可以將錯誤記錄到一個單獨的錯誤日誌文件中，這樣可以更容易地追蹤問題。此外，還能配置備份、限制日誌文件大小等功能。
- **`System.err.println()`**：只能簡單地印出錯誤，並無法進行更細緻的錯誤處理或記錄。


## 寫到檔案

```java
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggingExample {
    private static final Logger logger = Logger.getLogger(LoggingExample.class.getName());

    public static void main(String[] args) {
        try {
            // 設定 FileHandler，將日誌寫入檔案 "app.log"
            FileHandler fileHandler = new FileHandler("app.log", true); // true 表示追加到文件中
            fileHandler.setFormatter(new SimpleFormatter()); // 設定格式為簡單格式
            logger.addHandler(fileHandler);

            logger.info("這是一條 INFO 等級的日誌訊息");
            logger.warning("這是一條 WARNING 等級的日誌訊息");

            // 故意產生一個錯誤
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            logger.log(Level.SEVERE, "發生了算術異常: " + e.getMessage(), e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "無法創建 FileHandler: " + e.getMessage(), e);
        }
    }
}
```

## Lab & Practice (實習展示與動手練習)

本單元包含展示程式碼與實習練習題，皆位於 [`src/main/java/u02_robust/log/`](../../src/main/java/u02_robust/log/)：

### 示範 01: [LoggingJulDemo.java](../../src/main/java/u02_robust/log/LoggingJulDemo.java)
* 涵蓋 Java 內建 `java.util.logging` (JUL)：
  - 日誌等級配置（`INFO`, `WARNING`, `SEVERE`）。
  - 同步輸出至控制台與檔案（`logs/jul_demo.log`）。
  - 例外發生時的堆疊資訊記錄。

### 示範 02: [LoggingLog4jDemo.java](../../src/main/java/u02_robust/log/LoggingLog4jDemo.java)
* 現代企業級 `Log4j 2` / `SLF4J` 實務：
  - 參數化日誌 (`logger.info("使用者 {} 付款", user)`)。
  - 多等級策略與連線異常記錄。

---

## Exercise (學生自主練習)

> 💡 **自主學習流程**：
> 1. 打開練習程式碼，依據 `TODO` 註解動手實作。
> 2. 執行對應的單元測試驗證是否全部通過。
> 3. 若卡關或想確認最佳寫法，再點開下方的摺疊區塊參考解答。

---

### Ex01 ~ Ex03: JUL 基本與檔案日誌
* 參考並執行 [LoggingJulDemo.java](../../src/main/java/u02_robust/log/LoggingJulDemo.java)，練習設定 `FileHandler` 與記錄 `SEVERE` 異常。

---

### Ex04: [OrderDeliveryPractice.java](../../src/main/java/u02_robust/log/OrderDeliveryPractice.java)
* **題目**：美食外送平台訂單日誌生命週期實戰。
* **練習任務**：
  1. `TODO 1`：在非法金額（`≤ 0`）時，使用 `logger.warn(...)` 記錄警訊。
  2. `TODO 2`：在訂單成功建立時，使用 `logger.info(...)` 記錄里程碑。
  3. `TODO 3`：庫存售罄拒單時，使用 `logger.error(...)` 記錄業務失敗。
  4. `TODO 4`：庫存偏低（`≤ 2`）時，使用 `logger.warn(...)` 記錄庫存預警。
  5. `TODO 5`：配送途中遭遇硬體或連線異常時，使用 `logger.error(msg, e)` 記錄錯誤訊息並保留完整調用棧。
* **單元測試指令**：
  ```bash
  mvn test -Dtest=OrderDeliveryPracticeTest
  ```

<details>
<summary>💡 點擊展開：Ex04 參考解答與解析</summary>

```java
// TODO 1: 記錄非法訂單金額警訊
logger.warn("訂單建立失敗：顧客 {} 提交的訂單金額不合法: {}", customer, amount);

// TODO 2: 記錄訂單成功建立之重要里程碑
logger.info("訂單 {} 成功建立，顧客: {}，總金額: {} 元", orderId, customer, amount);

// TODO 3: 庫存耗盡導致接單失敗，使用 ERROR 記錄業務失敗
logger.error("餐廳拒絕訂單 {}：庫存不足（當前庫存: 0）", order.getOrderId());

// TODO 4: 庫存偏低，使用 WARN 記錄存貨警訊
logger.warn("餐廳警訊：訂單 {} 接單後，剩餘庫存僅剩 {} 份！", order.getOrderId(), currentStock - 1);

// TODO 5: 捕捉未預期例外，使用 ERROR 級別並記錄堆疊追蹤
logger.error("訂單 {} 配送過程中遭遇系統異常: {}", order.getOrderId(), e.getMessage(), e);
```

**解析說明**：
- **避免字串拼接**：使用 SLF4J / Log4j 2 的 `{}` 佔位符，當日誌等級被關閉時（例如 DEBUG），不會產生額外的字串建立開銷。
- **例外記錄**：將 `Throwable` 物件置於最後一個參數，日誌框架會自動解析並打印 Stack Trace，切勿只記錄 `e.getMessage()`。
</details>