## PMD 介紹

**PMD** 是一個靜態程式碼分析工具，主要用於檢查 Java 程式碼中的潛在問題，例如壞味道（code smells）、未使用的變數、空的 `catch` 區塊、不良的程式結構等。它幫助開發者自動檢查程式碼，提供問題建議，從而提升程式碼品質並減少潛在的錯誤。

PMD 可以與多種構建工具集成，**Maven** 是其中之一。在 Maven 專案中，我們可以透過設定 `pom.xml` 文件，使用 **PMD Maven Plugin** 來自動檢查專案中的程式碼品質。

[Java check rule in PMD](https://docs.pmd-code.org/latest/pmd_rules_java.html)

### PMD 如何提升程式品質

PMD 能檢查程式碼中的許多常見問題，從而提升程式碼的可讀性、維護性及效能。其主要作用包括：

1. **檢測壞味道（Code Smells）**：PMD 可以發現不良的程式結構或不必要的複雜性，這些問題在後續維護過程中可能會導致錯誤或影響效能。
   
2. **強制遵循程式碼風格**：PMD 支援自定義規則，可以確保程式碼符合團隊的編碼標準。

3. **早期發現潛在錯誤**：透過靜態分析，PMD 能夠發現一些潛在的錯誤，避免在後期測試或執行時才發現。

4. **報告程式碼問題**：PMD 生成詳細的報告，指出程式碼中每個問題所在的具體行數及原因，方便開發者進行修正。

### Java rules
PMD（Programming Mistake Detector）是一款靜態程式碼分析工具，用於檢測源代碼中的潛在問題。它支持多種語言，其中 Java 規則最為豐富。PMD 提供了一組預定義的規則集，可幫助開發者維護高質量的程式碼，主要涵蓋以下幾個方面：

#### 1. **Best Practices（最佳實踐）**
建議使用更安全、高效的方法來編寫程式碼。

例如：
- **`UseEqualsToCompareStrings`** – 避免使用 `==` 比較字符串  
  *Bad Smell:* 直接用 `==` 比較字符串會導致錯誤的結果，應改用 `.equals()`。

- **`UseTryWithResources`** – 使用 `try-with-resources` 管理資源  
  *Bad Smell:* 手動管理資源時，若沒有正確關閉流，可能會導致記憶體洩漏。

- **`EmptyCatchBlock`** – 避免空的 `catch` 區塊  
  *Bad Smell:* 空的 `catch` 區塊會吞掉異常，導致錯誤難以發現和排查。

- **`ReturnFromFinallyBlock`** – 不要在 `finally` 區塊內使用 `return`  
  *Bad Smell:* 在 `finally` 區塊中 `return` 會覆蓋 `try` 或 `catch` 中的返回值或異常，導致不可預測的行為。

- **`FinalFieldCouldBeStatic`** – 使用 `final` 修飾不可變變數  
  *Bad Smell:* 若變數在初始化後不應變更，應使用 `final` 來提高可讀性和安全性。

- **`AvoidUsingStaticFields`** – 避免過度使用靜態變數  
  *Bad Smell:* 過多的靜態變數可能導致非預期的共享狀態問題，影響可維護性。

- **`UseIsEmptyOnCollections`** – 使用 `isEmpty()` 而非 `size() == 0` 來檢查集合是否為空  
  *Bad Smell:* `isEmpty()` 更具可讀性，且效能可能優於 `size() == 0`。

- **`DoNotIgnoreInterruptedException`** – 不要忽略 `InterruptedException`  
  *Bad Smell:* 在多執行緒環境下，應適當處理 `InterruptedException` 來確保執行緒可以正確中斷。

- **`AvoidInstantiatingObjectsInLoops`** – 避免在迴圈中創建不必要的物件  
  *Bad Smell:* 在迴圈內部頻繁創建物件會導致不必要的記憶體分配，應在迴圈外部初始化以提高效能。

- **`AbstractClassWithoutAbstractMethod`** – 抽象類中沒有抽象方法  
  *Bad Smell:* 若類沒有抽象方法，則應考慮是否應該是普通類，而非抽象類。

- **`AvoidDuplicateLiterals`** – 避免重複的字面值  
  *Bad Smell:* 多次重複相同的字符串或數值會降低可維護性，應將其提取為常數。

```java
public class OrderProcessor {
    private static final String PENDING = "PENDING";
    private static final String SHIPPED = "SHIPPED";
    private static final String DELIVERED = "DELIVERED";

    public void processOrder(String status) {
        if (status.equals(PENDING)) {
            System.out.println("Order is still pending.");
        } else if (status.equals(SHIPPED)) {
            System.out.println("Order has been shipped.");
        } else if (status.equals(DELIVERED)) {
            System.out.println("Order has been delivered.");
        }
    }
}
```

- **`GodClass`** – 避免「上帝類」  
  *Bad Smell:* 單個類擁有過多的責任，應拆分為多個類以提高可讀性與可維護性。

- **`TooManyMethods`** – 避免類別中方法數量過多  
  *Bad Smell:* 當類別的方法數量過多時，可能意味著職責過於複雜，應考慮拆分。

- **`ExcessiveParameterList`** – 避免過多的參數列表  
  *Bad Smell:* 方法參數過多（如超過 4-5 個）會降低可讀性，應考慮使用物件封裝。

- **`AvoidDeeplyNestedIfStmts`** – 避免過深的巢狀 `if` 語句  
  *Bad Smell:* 多層巢狀 `if` 會降低可讀性，應考慮早返回（early return）或重構成小方法。

- **`LawOfDemeter`** – 遵循迪米特法則（Law of Demeter, LoD）  
  *Bad Smell:* 物件不應直接訪問其他物件的屬性或方法鏈，應透過適當的封裝來降低耦合度。

- **`UnusedPrivateMethod`** – 移除未使用的私有方法  
  *Bad Smell:* 私有方法若未被使用，則應刪除以減少程式碼複雜度。

- **`CyclomaticComplexity`** – 避免過高的循環複雜度  
  *Bad Smell:* 當函式內的決策點（如 `if`、`switch`）過多時，應拆分成更小的函式以提高可讀性。

- **`AvoidReassigningParameters`** – 避免修改方法參數的值  
  *Bad Smell:* 方法參數應視為不可變的，若需變更應使用新的變數，以提高可讀性和可維護性。


#### 2. **Code Style（程式碼風格）**
   - 強制統一程式碼風格，提高可讀性。
   - 例如：
     - 禁止無用的 import
     - 確保類名、方法名符合 Java 命名規範
     - 禁止多行語句寫在同一行

- **`UnnecessarySemicolon`** – 避免不必要的分號  
  *Bad Smell:* 在類或方法內部出現額外的分號可能會影響可讀性，應移除它們。

- **`UnusedImports`** – 移除未使用的 `import`  
  *Bad Smell:* 未使用的 `import` 會讓程式碼顯得雜亂，影響可讀性，應清理它們。

- **`TooManyStaticImports`** – 避免過多的靜態匯入  
  *Bad Smell:* 使用過多 `import static` 可能會降低可讀性，應僅匯入必要的方法或變數。

- **`MethodNamingConventions`** – 方法命名應符合規範  
  *Bad Smell:* 方法名稱應使用駝峰命名法（camelCase），如 `calculateTotalAmount()`，避免 `Calculate_total_Amount()`。

- **`ClassNamingConventions`** – 類名應遵循 Pascal 命名法  
  *Bad Smell:* 類名應使用大寫開頭，如 `OrderProcessor`，避免 `orderprocessor` 或 `order_processor`。

- **`VariableNamingConventions`** – 變數命名應符合規範  
  *Bad Smell:* 變數名稱應使用小寫開頭的駝峰命名，如 `orderCount`，避免 `Order_Count` 或 `ORDERCOUNT`。

- **`FieldNamingConventions`** – 類別的成員變數應遵循駝峰命名法  
  *Bad Smell:* `private int TotalAmount;` 應改為 `private int totalAmount;`。

- **`ConstantNamingConventions`** – 常數應使用全大寫命名，並以 `_` 分隔  
  *Bad Smell:* `final int maxRetries = 5;` 應改為 `final int MAX_RETRIES = 5;`。

- **`AvoidTrailingWhitespace`** – 移除行尾空白  
  *Bad Smell:* 行尾的額外空白可能會增加程式碼的雜訊，應該移除。

- **`UseTabsOrSpaces`** – 使用統一的縮排方式（tab 或 space）  
  *Bad Smell:* 在同一專案中混用 `tab` 和 `space` 會導致格式錯亂，應統一選擇一種。

- **`LineLength`** – 避免行長度超過 80 或 120 個字元  
  *Bad Smell:* 過長的行降低可讀性，應適當換行或重構。

- **`ConsecutiveBlankLines`** – 避免過多的空行  
  *Bad Smell:* 連續的空行會讓程式碼變得難以閱讀，應限制最多 1-2 行。

- **`AtLeastOneConstructor`** – 類應該有至少一個明確的建構子  
  *Bad Smell:* 若類沒有明確建構子，應添加預設建構子以增加可讀性。

- **`AvoidLongParameterList`** – 避免方法參數過多  
  *Bad Smell:* 方法應該保持簡單，若參數過多應考慮封裝為物件。

- **`AvoidEmptyMethod`** – 避免空的方法  
  *Bad Smell:* 空的方法可能是未實作的功能，應提供實作或移除它。

- **`AvoidEmptyClass`** – 避免空的類  
  *Bad Smell:* 若類沒有任何屬性或方法，應移除或添加適當的功能。

- **`UseBraces`** – 使用 `{}` 來包裹單行的 `if`、`for`、`while` 語句  
  *Bad Smell:* `if (condition) doSomething();` 應改為：
  ```java
  if (condition) {
      doSomething();
  }
  ```

- **`SwitchStmtsShouldHaveDefault`** – `switch` 陳述式應該有 `default`  
  *Bad Smell:* 避免 `switch` 缺少 `default`，這可能導致意外的錯誤。

- **`AvoidMagicNumbers`** – 避免魔術數字  
  *Bad Smell:* `if (x > 1000)` 應改為：
  ```java
  final int MAX_LIMIT = 1000;
  if (x > MAX_LIMIT) { ... }
  ```

- **`NoFinalizer`** – 避免使用 `finalize()` 方法  
  *Bad Smell:* `finalize()` 已被認為是過時的，應使用 `try-with-resources` 或明確的 `close()` 方法來管理資源。

#### 3. **Design（設計）**
   - 幫助識別不良的程式設計模式，以提高可維護性和擴展性。
   - 例如：
     - 避免循環依賴（Cyclomatic Complexity 過高）
     - 禁止 Singleton 類的懶加載模式（Lazy Initialization）
     - 檢測是否過度使用靜態變數（Global Variables）

- **`GodClass`** – 避免「上帝類」  
  *Bad Smell:* 單個類擁有過多的責任，應拆分為多個類以提高可讀性與可維護性。

- **`LargeClass`** – 避免類過大  
  *Bad Smell:* 類別內部包含過多的屬性或方法，應考慮拆分成多個小類。

- **`TooManyMethods`** – 避免類的方法數量過多  
  *Bad Smell:* 方法數量過多可能意味著職責過於複雜，應拆分成多個類或模組。

- **`ExcessiveParameterList`** – 避免方法參數過多  
  *Bad Smell:* 方法參數應保持簡潔，若超過 4-5 個，應封裝為物件。

- **`FeatureEnvy`** – 避免過度訪問其他類的屬性  
  *Bad Smell:* 若一個類頻繁存取其他類的屬性，可能表示應該將該邏輯移到被訪問的類中。

- **`LawOfDemeter`** – 遵循迪米特法則（LoD, Least Knowledge Principle）  
  *Bad Smell:* 物件應只與直接相關的物件互動，避免深層方法鏈調用，如 `object.getA().getB().doSomething()`。

- **`DataClass`** – 避免過度使用資料類  
  *Bad Smell:* 若類別只包含 getter 和 setter，則應考慮是否應增加行為，或使用記錄型態（Record）。

- **`SwitchStatement`** – 避免使用 `switch` 替代多態  
  *Bad Smell:* `switch` 語句可透過策略模式（Strategy Pattern）或工廠模式（Factory Pattern）來取代，提高可擴充性。

- **`RefusedBequest`** – 避免子類拒絕繼承父類的行為  
  *Bad Smell:* 若子類繼承了父類，卻不使用或覆蓋大部分方法，可能代表應使用組合而非繼承。

- **`SpeculativeGenerality`** – 避免過度設計  
  *Bad Smell:* 不應過早為將來可能用不到的功能預留架構，應根據實際需求設計。

- **`CyclomaticComplexity`** – 控制循環複雜度  
  *Bad Smell:* 方法內的決策點（如 `if`、`for`、`while`）過多時，應拆分成小方法提高可讀性。

- **`DeepInheritanceTree`** – 避免過深的繼承層級  
  *Bad Smell:* 深層繼承（超過 3-4 層）會影響可維護性，應考慮使用組合（Composition）來取代。

- **`GodMethod`** – 避免「上帝方法」  
  *Bad Smell:* 單一方法過長，處理過多的邏輯，應拆分為多個小方法。

- **`UnusedPrivateMethod`** – 移除未使用的私有方法  
  *Bad Smell:* 如果私有方法未被任何地方使用，應刪除以減少程式碼複雜度。

- **`LongParameterList`** – 避免過長的參數列表  
  *Bad Smell:* 若方法參數過多，應封裝成物件來提升可讀性。

- **`CouplingBetweenObjects`** – 避免高耦合  
  *Bad Smell:* 類之間的相依性過高會降低模組的可重用性，應考慮降低耦合程度。

- **`HighFanOut`** – 避免過多的相依性  
  *Bad Smell:* 單一類依賴過多的其他類別，應考慮拆分模組。

- **`InappropriateIntimacy`** – 避免類之間過度依賴彼此的內部實作  
  *Bad Smell:* 若一個類頻繁訪問另一個類的內部細節，應考慮是否需要重構。

- **`SingletonPattern`** – 避免濫用單例模式  
  *Bad Smell:* 單例模式可能導致測試困難和全域狀態問題，應僅在必要時使用。

- **`AvoidStaticMethods`** – 避免過度使用靜態方法  
  *Bad Smell:* 靜態方法無法被覆寫，會影響擴展性，應考慮是否應將其改為實例方法。

#### 4. **Documentation（文件註解）**
   - 確保程式碼有適當的註解，特別是對於 API 方法。
   - 例如：
     - 檢查類和方法是否缺少 Javadoc 註解
     - 確保 Javadoc 格式正確，沒有缺失的標籤
     - 禁止使用 `TODO` 註解而不實現相關功能

- **`CommentRequired`** – 重要類和方法應提供註解  
  *Bad Smell:* 關鍵類別或方法缺少說明，影響可讀性和維護性，應添加適當的 Javadoc。

- **`MissingJavadocMethod`** – 方法應該有 Javadoc 註解  
  *Bad Smell:* 公開（public）方法應該有 Javadoc 說明其用途、參數及回傳值，例如：
  ```java
  /**
   * 計算總金額。
   * @param price 商品價格
   * @param quantity 商品數量
   * @return 總金額
   */
  public double calculateTotal(double price, int quantity) { ... }
  ```

- **`MissingJavadocType`** – 類別應該有 Javadoc  
  *Bad Smell:* 若類別沒有 Javadoc，開發人員可能難以理解其用途，例如：
  ```java
  /**
   * 處理客戶訂單的服務類別。
   */
  public class OrderService { ... }
  ```

- **`MissingJavadocVariable`** – 公共變數應有 Javadoc 說明  
  *Bad Smell:* 重要的公共變數應提供說明，例如：
  ```java
  /** 訂單的唯一識別碼 */
  public String orderId;
  ```

- **`MissingJavadocPackage`** – `package-info.java` 應該有 Javadoc  
  *Bad Smell:* `package-info.java` 文件應包含該 package 的簡要說明，例如：
  ```java
  /**
   * 此 package 包含與客戶管理相關的類別，例如客戶資訊、客戶訂單等。
   */
  package com.example.customer;
  ```

- **`JavadocStyle`** – Javadoc 格式應符合標準  
  *Bad Smell:* Javadoc 標籤（如 `@param`、`@return`）應保持統一格式，避免拼寫錯誤或缺失。

- **`UncommentedEmptyMethodBody`** – 空的方法應該添加註解說明原因  
  *Bad Smell:* 若方法尚未實作或特意保持空白，應註明原因，例如：
  ```java
  /**
   * 此方法目前尚未實作，將於下個版本補充。
   */
  public void processOrder() { }
  ```

- **`CommentSize`** – 註解應保持適當長度  
  *Bad Smell:* 註解應該簡潔明瞭，避免過度冗長或過於簡短，如：
  ```java
  // ❌ 過短
  // 訂單 ID

  // ✅ 清楚易懂
  // 訂單的唯一識別碼，系統自動生成
  ```

- **`DuplicateJavadoc`** – 避免重複的 Javadoc 註解  
  *Bad Smell:* 若相同的 Javadoc 註解出現在多個地方，應考慮將說明集中到類或介面中，減少重複。

- **`TodoComment`** – 避免使用 `TODO` 註解而未執行對應修改  
  *Bad Smell:* 若存在 `// TODO` 註解，應確保它被及時處理，或使用更清楚的標記，例如：
  ```java
  // TODO: 下個版本應加入錯誤處理機制
  ```

#### 5. **Error Prone（錯誤易發）**
   - 偵測容易導致程式錯誤的代碼結構，防止潛在 bug。
   - 例如：
     - 捕獲所有異常時，避免使用 `catch (Exception e)` 而不進行處理
     - 檢測未關閉的流、文件、Socket 等資源泄漏
     - 避免 `NullPointerException` 風險，例如不檢查 `null` 就調用方法

- **`EmptyCatchBlock`** – 避免空的 `catch` 區塊  
  *Bad Smell:* 空的 `catch` 會吞掉異常，導致錯誤難以偵測，應記錄日誌或適當處理，例如：
  ```java
  try {
      int result = 10 / 0;
  } catch (ArithmeticException e) {
      System.err.println("數學錯誤: " + e.getMessage());
  }
  ```

- **`DoNotIgnoreExceptions`** – 不要忽略異常  
  *Bad Smell:* 捕獲異常但不進行任何處理會導致難以排查問題，應至少記錄錯誤訊息。

- **`ReturnFromFinallyBlock`** – 避免在 `finally` 區塊內使用 `return`  
  *Bad Smell:* 在 `finally` 區塊中 `return` 可能會覆蓋 `try` 或 `catch` 的返回值或異常，導致不可預測的行為。

- **`AvoidCatchingGenericException`** – 避免過度使用 `catch (Exception e)`  
  *Bad Smell:* 捕獲所有異常類型可能會掩蓋特定錯誤，應捕獲特定異常類型，例如：
  ```java
  try {
      Files.readAllLines(Paths.get("file.txt"));
  } catch (IOException e) {
      System.err.println("檔案讀取錯誤: " + e.getMessage());
  }
  ```

- **`DoNotThrowExceptionInFinally`** – 避免在 `finally` 中拋出異常  
  *Bad Smell:* 若 `finally` 區塊內拋出異常，可能會覆蓋 `try` 或 `catch` 區塊中的異常，導致關鍵錯誤訊息遺失。

- **`NullAssignment`** – 避免明確賦值 `null`  
  *Bad Smell:* 直接將變數賦值為 `null` 可能導致 `NullPointerException`，應考慮使用 `Optional` 或其他替代方案。

- **`AvoidThrowingNullPointerException`** – 不要手動拋出 `NullPointerException`  
  *Bad Smell:* `NullPointerException` 應該是程式邏輯錯誤的結果，而非顯式拋出，應透過適當的 `null` 檢查來避免。

- **`DoNotIgnoreInterruptedException`** – 避免忽略 `InterruptedException`  
  *Bad Smell:* 在多執行緒環境下，應適當處理 `InterruptedException`，確保執行緒可以被正確中斷，例如：
  ```java
  try {
      Thread.sleep(1000);
  } catch (InterruptedException e) {
      Thread.currentThread().interrupt(); // 正確恢復中斷狀態
  }
  ```

- **`AvoidInstanceofChecksInCasts`** – 避免 `instanceof` 檢查後強制轉型  
  *Bad Smell:* 若使用 `instanceof` 檢查對象類型，應考慮更好的設計模式，如多態，避免不必要的類型轉換。

- **`AvoidHardCodedPasswords`** – 避免硬編碼密碼  
  *Bad Smell:* 密碼不應該直接寫在程式碼中，應該存放於環境變數或安全存儲機制，例如：
  ```java
  String password = System.getenv("DB_PASSWORD"); // 正確做法
  ```

### PMD 之應用  

要在 Maven 中使用 PMD，我們需要在 `pom.xml` 中加入 **PMD Maven Plugin**。以下是一個範例配置，展示如何使用 PMD 來檢查程式碼：

#### 1. `POM.xml` 配置

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-pmd-plugin</artifactId>
            <version>3.15.0</version>
            <configuration>
                <rulesets>
                    <!-- 指定使用的 PMD 規則集 -->
                    <ruleset>src/main/resources/pmd/ruleset.xml</ruleset>
                </rulesets>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <!-- 執行 PMD 的 check 工作 -->
                        <goal>check</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

#### 2. 設定規則集

```xml
<?xml version="1.0" encoding="UTF-8"?>

<ruleset name="All Java Rules"
         xmlns="http://pmd.sourceforge.net/ruleset/2.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://pmd.sourceforge.net/ruleset/2.0.0 https://pmd.sourceforge.io/ruleset_2_0_0.xsd">
    <description>Every Java Rule in PMD</description>
    
    <!-- the rules to be checked -->
    <rule ref="category/java/bestpractices.xml">
        <exclude name="SystemPrintln"/>
        <exclude name="AvoidReassigningParameters"/>
    </rule>
    <rule ref="category/java/codestyle.xml" />
    <rule ref="category/java/design.xml" />
    <rule ref="category/java/documentation.xml" />
    <rule ref="category/java/errorprone.xml" />
    <rule ref="category/java/multithreading.xml" />
    <rule ref="category/java/performance.xml" />
    <rule ref="category/java/security.xml" />

    <!--  override the rules  -->
    <rule ref="category/java/documentation.xml/CommentSize">
        <properties>
            <property name="maxLines" value="6" />
            <property name="maxLineLength" value="80" />
        </properties>
    </rule>
</ruleset>
```

這個規則集設定會檢查 Java 程式碼中的 `System.out.println` 語句，並在檢查到使用時產生警告。

#### 3. 執行規則檢查

在 Maven 中執行 PMD: 打開 Intellij 右方的 Maven 工具，執行 `verify` 進行檢查。

或是使用 console command: maven 中的 PMD 預設會在 `verify` 階段運行，也就是說，在執行以下指令時會自動觸發 PMD 檢查：

```bash
mvn verify
```

或者，你也可以直接執行 PMD 的檢查：

```bash
mvn pmd:check
```

這樣會檢查程式碼中的問題，並生成報告。

#### 4. 觀看報告、修正程式碼

打開 `target/report/pmd.html`, 觀看錯誤。在頁面上對於該違規如果不清楚，可以直接點擊觀看該違規，會引導到 PMD 的網站觀看說明。修正程式碼後可以再次執行 verify。

#### 5. 調整規則
如果有規則實在不符合你的需要， 例如 CommentSize 原先設定最大行數與長度分別為 6 與 80, 你可以修改：

* 修改規則參數
```xml
    <!--  override the rules  -->
    <rule ref="category/java/documentation.xml/CommentSize">
        <properties>
            <property name="maxLines" value="10" />
            <property name="maxLineLength" value="90" />
        </properties>
    </rule>
```

* 排除規格: 例如將 SystemPrintln 與 AvoidReassigningParameters 排除

```xml
    <rule ref="category/java/bestpractices.xml">
        <exclude name="SystemPrintln"/>
        <exclude name="AvoidReassigningParameters"/>
    </rule>
```

* 忽略警告
如果某些程式碼不適合檢查規則，可以透過 `@SuppressWarnings("PMD")`，讓警告不要出現在報告中。

```java
@SuppressWarnings("PMD")
public class Main {
    public static void main(String[] args) {
        // TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");
...        
```

詳細可以參考 [這裡](#忽略違規)

#### 管理違規

 ```xml
   <configuration>
       <rulesets>
           <ruleset>src/main/resources/pmd/ruleset.xml</ruleset>
       </rulesets>
       <failOnViolation>true</failOnViolation>
       <minimumPriority>3</minimumPriority> <!-- Set to 3 for "warning" and below -->
   </configuration>
```   

在 PMD 的 `configuration` 部分，這兩行設定的作用如下：

1. `<failOnViolation>true</failOnViolation>`
   這表示當 PMD 檢查程式碼時，如果發現任何違規（即不符合規則的程式碼），構建過程將失敗（例如 Maven build 會停止）。設定為 `true` 意味著發現的任何問題都會導致構建失敗，確保這些問題在繼續構建之前必須先行修復。

2. `<minimumPriority>3</minimumPriority>`
   這設定了 PMD 規則的最低優先級。PMD 中的優先級範圍是從 1 到 5，1 表示最高優先級，5 表示最低優先級。當設置為 `minimumPriority` 為 3 時，PMD 只會報告優先級為 3 或更高的違規行為，較低優先級的違規（4 和 5）將會被忽略，不會報告。

此配置的目的是控制 PMD 檢查的嚴格程度，以及哪些問題會導致構建失敗。

如果您想控制 PMD 對違規嚴重程度的行為，您可以調整「pom.xml」中的設定。以下是一些選項：

1. **Set Minimum Severity**: 您可以設定最低嚴重性級別，必須違反該級別 PMD 才能導致建置失敗。例如，您可以指定僅在「error」等級違規時失敗：


2. **Fail on Violations**: If you want to ensure that the build fails only if there are "critical" issues, you can manage this through configurations like:

   ```xml
   <configuration>
       <failOnViolation>true</failOnViolation>
       <minimumPriority>2</minimumPriority> <!-- Only fail on error and critical issues -->
   </configuration>
   ```

3. **Ignoring Certain Rules**: If some violations are not critical, consider excluding specific rules from your ruleset that generate excessive warnings or are not applicable to your project's context.

### 忽略違規

In PMD, to selectively ignore violations, such as one of the `AvoidArrayLoops` rule violations in your code, you can use **PMD suppression mechanisms**. There are three common approaches:

1. **Suppressing Violations with Annotations**
2. **Suppressing Violations with NOPMD Comments**
3. **Using Suppressions in a PMD Configuration File**

#### 1. 使用「標記」SuppressWarnings

局部方法忽略特定規則
```java
@SuppressWarnings("PMD.AvoidArrayLoops")
public void exampleMethod() {
    // Code that would normally trigger AvoidArrayLoops rule
}
```

整個 class 都忽略 PMD 的規則：
```java
@SuppressWarnings("PMD")
public class Main {
    public static void main(String[] args) {
        // TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");
...        
```

#### 2. 使用「註解」NOPMD

```java
for (int i = 0; i < array.length; i++) { //NOPMD - This loop is necessary for performance reasons
    // Your loop code here
}
```

The `NOPMD` comment will tell PMD to ignore the violation for that particular line. Make sure to include a reason after `//NOPMD` for better maintainability and clarity.

#### 3. 客製化規則

```xml
<rule ref="category/java/bestpractices.xml/AvoidArrayLoops">
    <exclude-pattern>.*SomeSpecificFile.java</exclude-pattern>
</rule>
```
or

```xml
<rule ref="category/java/bestpractices.xml">
    <exclude name="SystemPrintln">
        <exclude-pattern>fcu/.*\.java</exclude-pattern>
    </exclude>
    <exclude name="AvoidReassigningParameters" />
</rule>
```

### 改變 PMD 的檢查時程

PMD 預設是在 `verify` 時期執行，如果你想要讓 PMD 在其他階段（例如 `validate` 或 `compile`）運行，可以修改 `pom.xml` 中 `executions` 部分：

```xml
<execution>
    <phase>validate</phase>  <!-- 讓 PMD 在 validate 階段執行 -->
    <goals>
        <goal>check</goal>
    </goals>
</execution>
```

這樣每次執行 `mvn validate` 時就會執行 PMD 的檢查。

## Lab

### Lab01: install PMD

### Lab02: improve code
* check the report generated from PMD, improve at least 3 violations

### Lab03: customerize the rules

### Lab04: Suppress violation
* 有些部分的 violation 是被冤枉的，可以透過 NOPMD 來取消