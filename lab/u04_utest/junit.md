JUnit Automatic Testing
===

> [Junit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)

#### Test First Development

以前你是：設計 => 寫程式  => 測試。現在你應該：設計 => 寫測試碼 => 寫程式 => 測試

- 設計程式框架
- 設計測試案例：利用 JUnit 設計測試案例。
- 執行測試碼：執行 JUnit 測試碼，會跑出很多的「錯誤」。
- 撰寫程式
- 執行測試碼：執行 JUnit 測試碼，錯誤應該會逐漸減少。

#### 3A Principle

3A 指的是：安排 Arrange, 測試 Act, 驗證 Assert：

- Arrange：安排測試的環境或資料，
- Act：呼叫測試目標類別上要測試的方法。
- Assert：驗證預期結果與 Act 所得到的實際結果，是否符合。

## JUnit Introduction

JUnit 是一個用於 Java 程序的單元測試框架。它是開發人員用來測試他們的程式碼是否正確的一個重要工具。以下是一些有關 JUnit 的基本介紹：

1. 單元測試：JUnit 是一個用於單元測試的框架，這意味著它用於測試程式中的個別方法或函數，而不是整個應用程序。這有助於開發人員檢查他們的程式碼的特定部分是否正確運作。
2. 測試框架：JUnit 提供了一個測試框架，可以用來編寫測試用例。測試用例是測試單元的最小單位，它包含了一個或多個測試方法，用於確定程式碼的某個方面是否正確。
3. 測試注解：JUnit 使用注解來標記測試方法，這些注解告訴框架哪些方法是測試方法。一些常見的 JUnit 測試注解包括 `@Test`（用於標記測試方法）、`@Before`（在每個測試方法執行之前執行）、`@After`（在每個測試方法執行之後執行）等。
4. 斷言：JUnit 提供了許多斷言方法，用於檢查測試結果是否符合預期。這些斷言方法可以確保測試方法產生預期的結果。
5. 測試運行器：JUnit 提供了不同的測試運行器，可以用於不同的測試需求。例如，`JUnit4` 中的默認運行器是 `BlockJUnit4ClassRunner`，而 `JUnit5` 中引入了不同的運行器，例如 `Jupiter` 測試引擎。
6. 測試套件：JUnit 允許你將多個測試用例組合成一個測試套件，並一次運行所有測試用例。
7. 整合性：JUnit 可以與許多集成開發環境（IDE）和建置工具（如Maven、Gradle）無縫整合，使測試更加方便。
8. 支援參數化測試：JUnit 5 引入了對參數化測試的支援，允許你在不同的參數組合下運行相同的測試方法。


#### IntelliJ POM project

1. Maven project
2. POM 加入 dependency

```
<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.11.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```
ps. see [mvnrepository](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter/5.12.0)

3. 重新載入以下載 JUnit 套件
4. 前往要測試的檔案
5. 導覽 >> 測試 OR 按滑鼠右鍵，選擇「Go」，然後選擇「Test」
6. 選擇您想要測試的函數

**JUnit 5** 是 Java 生態系統中廣受歡迎的測試框架，它讓開發人員能夠為其 Java 應用程式編寫和執行單元測試。它是 JUnit 框架的最新版本，相較於其前身 JUnit 4 提供了顯著的改進。JUnit 5 具有模組化、可擴展性，並且專為支援現代測試需求而設計，使其成為 Java 開發人員的首選。

#### JUnit 5 modules

JUnit 5 由三個主要模組組成：

1. **JUnit Platform**：
   - **角色**：在 JVM 上啟動測試框架的基礎。
   - **特色**：
     - 啟動測試框架。
     - 定義用於開發在平台上運行的測試框架的 `TestEngine` API。
     - 與 Maven、Gradle 等建構工具以及 IntelliJ IDEA 和 Eclipse 等 IDE 整合。

2. **JUnit Jupiter**：
   - **角色**：提供用於編寫測試的新程式設計模型和擴展模型。
   - **特色**：
     - 包含用於編寫測試的 JUnit 5 API（例如，`@Test`、`@BeforeEach`）。
     - 引入新的註解和測試生命週期方法。
     - 支援參數化測試、巢狀測試和動態測試。

3. **JUnit Vintage**：
   - **角色**：提供與 JUnit 3 和 JUnit 4 測試的向後相容性。
   - **特色**：
     - 允許您與 JUnit 5 測試一起運行較舊的 JUnit 3 和 4 測試。
     - 對於從 JUnit 3/4 遷移到 JUnit 5 的專案很有用。


#### An Example

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    void testAddition() {
        Calculator calculator = new Calculator();
        int result = calculator.add(2, 3);
        assertEquals(5, result);
    }
}
```

使用了 JUnit 提供的方法 `assertEquals(5, result)` 來進行斷言。這個斷言的作用是比較實際的結果 `result` (執行 `add(2, 3)` 得到的值) 是否等於預期的結果 `5`。

如果實際的結果 `result` 確實是 `5`，那麼這個 `testAddition` 測試就會通過（Pass）；如果結果不是 5，那麼測試就會失敗（Fail），並提示錯誤訊息，這表明 `Calculator` 類別的 `add` 方法可能有問題。

### Template code

More tags in Junit
```java
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class StandardTests {

    @BeforeAll
    static void initAll() {
    }

    @BeforeEach
    void init() {
    }

    @Test
    void succeedingTest() {
    }

    @Test
    void failingTest() {
        fail("a failing test");
    }

    @Test
    @Disabled("for demonstration purposes")
    void skippedTest() {
        // not executed
    }

    @Test
    void abortedTest() {
        assumeTrue("abc".contains("Z"));
        fail("test should have been aborted");
    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
    }
}
```

## You Should Know

### Lazy Evaluating Message

在 JUnit 中，Lazily evaluating message 是一種技巧，用於在斷言（assertions）中延遲評估訊息（lazy evaluating message）的建立，僅在斷言失敗時才進行評估。這種技巧可以提高執行效能，避免不必要的訊息建立和字串連接操作，特別是當訊息建立的過程很耗時或者涉及複雜的計算時。

以下是一個使用 Lazily evaluating message 的例子：

```java
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CalculatorTest {
    
    @Test
    public void testAddition() {
        Calculator calculator = new Calculator();
        int result = calculator.add(2, 2);
        assertEquals(5, result, () -> "計算結果錯誤: " + calculator.getLastOperation());
    }
}
```

在上述範例中，我們使用了 JUnit 5 的 `assertEquals` 斷言方法，並傳遞了一個 lambda 運算式作為第三個參數。該 lambda 運算式將在斷言失敗時才執行，用於動態建立訊息。

假設 `Calculator` 類具有一個 `getLastOperation` 方法，該方法將返回上一個計算操作的字串描述。在此範例中，如果斷言失敗（即計算結果不等於 5），則會執行 lambda 運算式，並將計算器的最後一個操作訊息連接到錯誤訊息中。

這種方式可以避免在每次斷言時都執行 `getLastOperation` 方法，而僅在斷言失敗時才執行，從而節省了不必要的計算和訊息建立操作。

### `assertAll()`

在 JUnit 中，`assertAll()` 方法是一個非常有用的斷言方法，它用於同時執行多個斷言，並在所有斷言完成後報告所有失敗的斷言。這對於進行多個相關斷言的測試非常有用，因為它可以讓你一次性檢查多個條件，而不需要分開處理每個斷言。

以下程式碼，assertion 1 若沒有通過，不會中斷 assertion 2, 3 的執行。

```java
    assertAll("name of the assertion",
            ()-> assertEquals(exp, actural),        // assertion 1
            ()-> assertEquals(1, c.plus(-1, 1)),    // assertion 2
            ()-> assertEquals(5, c.plus(1, -1))     // assertion 3
    );
```

以下是 `assertAll()` 方法的應用範例：

```java
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CalculatorTest {
    
    void plusTest() {
        System.out.println("\t Here is plus test...");

        int actualValue = c.plus(1, 1);
        int expectedValue = 2;
        assertAll("plus",
                ()-> assertEquals(expectedValue, actualValue),
                ()-> assertEquals(1, c.plus(-1, 1)),
                ()-> assertEquals(5, c.plus(1, -1))
        );

    }
```

`assertAll()` 方法的第一個參數是一個描述性的名稱，用於識別這組相關的斷言。接下來的參數是多個 lambda 運算式，每個運算式都是一個斷言。

當執行 `assertAll()` 方法時，它將依次執行每個斷言。如果其中任何一個斷言失敗，將記錄錯誤訊息，並將所有失敗的斷言結果一起報告。這使得你可以一次檢查多個斷言，並且在所有斷言完成後一次性獲得所有失敗的結果。

這在以下情況下特別有用：
- 測試方法中有多個相關的斷言，並且你想一次檢查它們。
- 你想要避免在第一個斷言失敗後中止測試，並希望繼續檢查其他斷言。
- 你想要清晰地顯示所有失敗的斷言，而不是一個接一個地報告。

### Testing Exception

```java
@Test
void exceptionTesting() {
    Exception exception = assertThrows(ArithmeticException.class, () ->
    calculator.divide(1, 0));
    assertEquals("/ by zero", exception.getMessage());
}
```    


## JUnit- Basic 

以下是 JUnit 5 中常用的斷言方法及其簡單說明：

1. **`assertEquals(expected, actual)`**  
   用途：檢查預期值和實際值是否相等。如果兩者不相等，測試失敗。  
   範例：`assertEquals(10, calculator.add(7, 3));`

2. **`assertNotEquals(unexpected, actual)`**  
   用途：檢查不期望值與實際值是否不同。如果相等，測試失敗。  
   範例：`assertNotEquals(5, calculator.add(7, 3));`

3. **`assertTrue(condition)`**  
   用途：檢查條件是否為 `true`。如果條件為 `false`，測試失敗。  
   範例：`assertTrue(user.isActive());`

4. **`assertFalse(condition)`**  
   用途：檢查條件是否為 `false`。如果條件為 `true`，測試失敗。  
   範例：`assertFalse(user.isBlocked());`

5. **`assertNull(object)`**  
   用途：檢查對象是否為 `null`。如果對象不為 `null`，測試失敗。  
   範例：`assertNull(user.getMiddleName());`

6. **`assertNotNull(object)`**  
   用途：檢查對象是否不為 `null`。如果對象為 `null`，測試失敗。  
   範例：`assertNotNull(user.getFirstName());`

7. **`assertArrayEquals(expectedArray, actualArray)`**  
   用途：檢查兩個陣列的內容是否相等。如果不相等，測試失敗。  
   範例：`assertArrayEquals(new int[]{1, 2}, actualArray);`

8. **`assertThrows(exceptionClass, executable)`**  
   用途：檢查是否拋出指定類型的例外。如果沒有拋出或拋出不同類型的例外，測試失敗。  
   範例：`assertThrows(IllegalArgumentException.class, () -> someMethod());`

9. **`assertDoesNotThrow(executable)`**  
   用途：檢查執行某段程式碼時不會拋出任何例外。如果拋出例外，測試失敗。  
   範例：`assertDoesNotThrow(() -> someMethod());`

10. **`assertTimeout(duration, executable)`**  
    用途：檢查某段程式碼是否在給定的時間內執行完成。如果超過時間，測試失敗。  
    範例：`assertTimeout(Duration.ofMillis(100), () -> slowMethod());`

11. **`assertAll(executables...)`**  
    用途：允許同時驗證多個斷言並收集所有失敗訊息。這讓測試更加健全，不會因為第一個失敗而中斷。  
    範例：`assertAll(() -> assertTrue(x), () -> assertEquals(y, z));`

> Google `junit api` 來找出更多 Assertion 的方法。

### Demo01: Calculator
寫一個 Calculator 的類別，內有 
* add(int, int): int
* multiply(int, int): int
* divide(int, int): double 

See Demo [demo/Calculator](https://github.com/nlhsueh/sw-testing24/blob/main/Intellij/DemoJunit/src/main/java/demo/Calculator.java)

LAB: 
1. 修改 Calculator, 增加 subtract() 減法
2. 做一個會拋出例外的測試，例如 `5/0` （參考 [JUnit doc- assertThrows()](https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions)）
3. 參考 [JUnit doc- Assertion](https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions), 採用 assertAll() 進行多個測試。說明 assertAll() 的好處
4. 修改 Calculator, 增加 arrayAdd(), 針對兩個大小一樣的陣列進行相加，回傳結果。(`assertArrayEquals()`)

Read more about my [assertAll()](#assertall)

### Lab: Improve Testability (程式碼重構)

My [xdemo/Triangle](https://github.com/nlhsueh/sw-testing24/blob/main/Intellij/DemoJunit/src/main/java/xdemo/Triangle.java) is a code for checking type of Triangle. But it is not easy to test- I can't use JUnit to test it.

Lab
1. Please refactoring the code, make it easy to test
2. Use Juit to test your sort

### Demo02: Life Cycle (測試執行順序)

Learn
* JUnit testing life cycle (BeforeEach, AfterEach ,...)

為了讓每一次的測試可以在一個乾淨的環境，我們可以設定 @BeforeEach, @AfterEach。

![test life cycle](../img/junit_life_cycle.png)

See Demo [demo/Life cycle](https://github.com/nlhsueh/sw-testing24/blob/main/Intellij/DemoJunit/src/test/java/demo/LifeCycleTest.java)

### Demo03: Person (多個測試指令)

Person 類別，內有
* getFirstName(): String
* getLastName(): String

檢查這些方法是否正確。Read [JUnit doc- AssertionDemo](https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions). 

Learn:
* 字串比對測試
* 結構性的 `assertAll()`
* 例外測試
* 執行超時測試

### Lab: People (誤差與狀態改變)

My [xdemo/People](https://github.com/nlhsueh/sw-testing24/blob/main/Intellij/DemoJunit/src/main/java/xdemo/People.java) 封裝了姓名、身高、體重、BMI、還有父親的關係。這個程式可能有錯誤。

Learn
* 允許誤差的比對
* 物件改變狀態後的檢查

Lab:
1. 針對進行 BMI 的測試，注意小數點誤差的情況，可使用 delta 的參數。
2. 增加 addChild(People) 的功能，進行 getFather() 的測試。(father 的出生年應該比 child 小，如果沒有，應拋出例外)
3. 增加 isSibling(People) 的功能，並進行測試。


### Lab: Testing array (陣列檢核)

My [RobustBubbleSort](https://github.com/nlhsueh/sw-testing24/blob/main/Intellij/DemoJunit/src/main/java/xdemo/RobustBubbleSort.java) is a code for sorting data. But it is not easy to test- I can't use JUnit to test it.

Lab
1. Please refactoring the code, make it easy to test
2. Use assertArrayEquals() to test your sort

### Lab: Triangle (非獨立邊界測試)

三邊的長都接於 1-10 之間，寫一個程式判斷是何種三角形。
* 應用獨立非強固的邊界測試方法來進行測試
* 可先用 excel 規劃測試案例，再轉成 JUnit 進行測試
  * min, min+, max, max-
* 討論：
  * 如果邊界寫錯了 (<=10, 寫成 <10); 測試案例是否可以找出來？
  * 此問題是否適合採用「獨立非強固的邊界測試」方法測試？如果不合適，請說明
    * Hint: 需要兩個邊界都出錯，才會顯示呈現錯誤嗎？

### Lab: Binary Search (等價分割)

> 設計一個 `BinarySearch` 類別，包含 `search(int key, int[] array)` 方法，該方法接受一個目標數字 `key` 和已排序的整數陣列 `array`，並回傳一個 `Result` 物件；`Result` 包含布林值 `Found`，表示是否找到目標數字，及整數 `index`，為目標數字在陣列中的索引位置（若未找到則為 -1）。

ps. [Reference code](https://github.com/nlhsueh/sw-testing24/blob/main/Intellij/DemoJunit/src/main/java/demo/BinarySearch.java) 

> Write the test cases for testing the `BinarySearch` using Equivalence Partition method.

ps. [Reference code](https://github.com/nlhsueh/sw-testing24/blob/main/Intellij/DemoJunit/src/test/java/demo/BinarySearchTest.java) 

Discuss: is the test case enough?


## JUnit- Display

### Demo: DisplayName

See [JUnit doc- DisplayNameDemo](https://junit.org/junit5/docs/current/user-guide/#writing-tests-display-names)
* 可以讓測試的結果顯示更清楚，口語化
* 在 testing class 或 testing method 上面加上 `@DisplayName()` 的標記
* 需 `import org.junit.jupiter.api.DisplayName;`

```java
@DisplayName("A special test case")
class DisplayNameDemo {

    @Test
    @DisplayName("Custom test name containing spaces")
    void testWithDisplayNameContainingSpaces() {
    }
```

See my [LifeCycleTest](https://github.com/nlhsueh/sw-testing24/blob/main/Intellij/DemoJunit/src/test/java/demo/LifeCycleTest.java) to see how it works.

See my [DisplayNameTest](https://github.com/nlhsueh/sw-testing24/blob/main/Intellij/DemoJunit/src/test/java/demo/DisplayNameTest.java) to see how it works.


### Demo: DisplayName Generator

See [JUnit doc- DisplayNameGeneratorDemo](https://junit.org/junit5/docs/current/user-guide/#writing-tests-display-name-generator)
* DisplayNameGenerator 共有四種
    * `Standard`: 預設的生成器。它會將 CamelCase (駝峰命名法) 轉換為以空格分隔的單詞，首字母大寫，並保留方法參數。如果沒有參數，還是會呈現括號。
    * `Simple`: 以方法的名稱顯示，如果沒有參數，不會呈現括號
    * `ReplaceUnderscores`: 專門處理使用底線 _ 分隔單詞的命名方式。它會將名稱中的所有底線 _ 替換為空格。
    * `IndicativeSentences`: 為測試類別和方法生成完整的、有指示性的語句。對於巢狀類別 (Nested Classes) 特別有用，它可以將類別名稱組合成更具描述性的句子。

![](https://hackmd.io/_uploads/S1xiM7nZa.png)
* `A_year_is_not_supported` 是採用去除底線的方式，直接將方法名稱作為顯示，其內的 testing method 也都會採用此方法。
* `if_it_is_negative` 因為有特別宣告 DisplayName, 所以會採用其後的參數來描述。注意因為此方法用參數化測試, `-1`, `-4` 都會進去測試，每次測試的顯示會依據 name 的內容而定 `{0}` 表示第一個參數，此為 `year`。

```java
@DisplayName("A negative value for year is not supported by the leap year computation.")
@ParameterizedTest(name = "For example, year {0} is not supported.")
@ValueSource(ints = { -1, -4 })
void if_it_is_negative(int year) {
}
```

### Lab: Binary Search (DisplayName)

針對 BinarySearch 的程式，請加上 DisplayName 來美化測試結果的顯示

### Demo: Nested Test

See [JUnit doc- TestingAStackDemo](https://junit.org/junit5/docs/current/user-guide/#writing-tests-nested)

* class 內部有 class 形成結構，更方便組織測試
* 內部的 class name 上方加上 `@Nested`; 才會進行測試
* class `TestingAStackDemo` 下有 `WhenNew` class, `WhenNew` 下有 `AfterPushing` class, 他們下面有一些 testing method 會進行測試。
* 與 DisplayName 搭配，測試的報告更清楚。

class structure
![class hierarchy](https://hackmd.io/_uploads/r1rxcMh-p.png)

Result result:
![nest testing](https://hackmd.io/_uploads/r1_idz3W6.png)

### Lab: stack (巢狀測試)
* 依上面的例子，設計一個 Stack class, 設定最多放五個元素
* 應用 Nested test, 檢驗 stack isFull 的時候是否正確; 檢驗 isFull 時再 push() 是否會拋出 Exception

### Lab: Binary Search (Nested)
針對 BinarySearch 的程式，改以 Nested 的方式 來進行結構化測試。

## JUnit- Parameterized test

### Demo: Parameterized Test

See [JUnit doc- ParameterizedTest](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests)

`@ParameterizedTest` 是 JUnit 5 中的一個注解，用於執行參數化測試（Parameterized Tests）。它允許你在一個測試方法中使用不同的輸入參數運行多次測試，以驗證相同的測試邏輯對於不同的輸入產生正確的結果。

以下是 `@ParameterizedTest` 的應用範例：

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MathTest {

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 11})
    public void testPrime(int number) {
        Math math = new Math();
        assertTrue(math.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 100, 50, 99, 51})
    public void testNotPrime(int number) {
        Math math = new Math();
        assertFalse(math.isPrime(number));
    }
}
```


使用 `@ParameterizedTest` 可以有效地減少重複的測試程式碼，並使你能夠輕鬆地在一個測試方法中執行多次測試。這對於驗證一個邏輯對於不同輸入值的行為是否正確非常有用，同時保持測試程式碼的簡潔和可讀性。

參數化測試優點
1. 減少重複的測試程式碼：使用 `@ParameterizedTest` 可以將多次類似的測試結合到一個測試方法中，減少了重複編寫相似測試邏輯的需要。這樣一來，可以提高測試程式碼的可維護性和可讀性。
2. 一次性測試多個輸入值：通過參數化測試，你可以一次性測試多個輸入值。這使得可以在一個測試方法中檢查多個輸入組合下的預期行為，從而增加了測試的全面性和可靠性。
3. 提高測試覆蓋率：使用 `@ParameterizedTest` 可以為各種輸入條件和邊界情況設置不同的測試案例，從而增加測試覆蓋率。這有助於捕捉潛在的錯誤或不正確的行為，並提高程式碼的品質。
4. 測試報告和結果的可讀性：由於 `@ParameterizedTest` 將多個測試組合在一起，測試報告和結果將更清晰和易讀。它將以一個測試方法的形式呈現每個輸入組合的結果，使得報告更容易理解並追踪測試結果。
5. 擴展性和靈活性：JUnit 5 提供了多種參數提供者（Parameter Providers），如 @ValueSource、@CsvSource、@MethodSource 等。這使得可以靈活地指定不同的參數值來驗證測試邏輯，並根據需要擴展和定制參數化測試。

總之，`@ParameterizedTest` 提供了一種有效的方式來處理多個輸入組合下的測試情境，減少了重複的測試程式碼，提高了測試覆蓋率和測試結果的可讀性。它還提供了靈活的擴展性，以適應各種不同的參數化測試需求。

#### Lab: Prime (ValueSource)
* 使用 `@ValueSource` 來測試 `Math.isPrime()`

#### Lab: Swimming pool ticket (ValueSource)

一個游泳池標準收費為 100 元。 (1) 星期六日200元, 除會員以外不打折。(2) 清晨6:00 以前所有人打八折; 中午11:00-13:00 九折 ▫ (3) 會員一律打五折。各打折不可重複，以最低價為主。
* 使用 all-pair testing 的方式規劃測試案例
* 使用 `@ValueSource` 來測試票價計算是否正確

### Demo: CsvSource 

```java
    @ParameterizedTest
    @CsvSource({
            "apple,         1",
            "banana,        2",
            "'lemon, lime', 0xF1",
            "strawberry,    700_000"
    })
    void testWithCsvSource(String fruit, int rank) {
        assertNotNull(fruit);
        assertNotEquals(0, rank);
    }
```    

#### Lab: Triangle (CsvFile)

撰寫一個 `getTriangleType` 方法，根據三邊長 `a`、`b`、`c` 的值來判斷三角形的類型：
 * 若任一邊長小於等於 0 或兩邊之和小於等於第三邊，回傳 "Not a valid triangle"；
 * 若三邊相等，回傳 "Equilateral"；若兩邊相等，回傳 "Isosceles"；若三邊皆不相等，回傳 "Scalene"。
 * 參考程式碼：[demo/Triangle](https://github.com/nlhsueh/sw-testing24/blob/main/Intellij/DemoJunit/src/main/java/demo/Triangle.java)

撰寫以下測試案例：
 * 使用等價分割，對此問題進行測試案例設計
 * 使用 CsvSource 進行測試案例的安排，並進行測試

#### Lab: Tomorrow

以下範例是對 `tomorrow()` 進行測試， csv 內部前三個參數是輸入的日期，後三個數字是預期的輸出。輸出的結果我們都轉為 String 一次比較年月日是否相同。

```java
@ParameterizedTest
@CsvSource({
    "1901, 1, 1, 1901, 1, 2",
    "1901, 2, 28, 1901, 3, 1",
    "2000, 2, 28, 2000, 2, 29",
    "2000, 12, 31, 2001, 1, 1",
    "2000, 6, 30, 2000, 7, 1",
    "2000, 7, 30, 2000, 7, 31",
})
void testTomorrow(int y1, int m1, int d1, int y2, int m2, int d2) {
    MyDate today = new MyDate(y1, m1, d1);
    MyDate expected_tomorrow = new MyDate(y2, m2, d2);
    assertEquals(expected_tomorrow.toString(), today.tomorrow().toString());
}
```

* 參考程式碼：[xdemo/MyDate](https://github.com/nlhsueh/sw-testing24/blob/main/Intellij/DemoJunit/src/main/java/xdemo/MyDate.java)
*  變數 y1, m1, d1 分別對應到 1901, 1, 1,; y2, m2, d2 分別對應到 1901, 1, 2
*  逐行讀入進行測試

![](https://hackmd.io/_uploads/SkhM5AEza.png)

### Demo: CsvFileSource

```java
    @ParameterizedTest
    @CsvFileSource(resources = "/two-columns.csv", numLinesToSkip = 1)
    void testWithCsvFileSourceFromClasspath(String country, int reference) {
        assertNotNull(country);
        assertNotEquals(0, reference);
    }
```    
* .csv 檔案放在 /resource 下
* 跳過第一行: numLinesToSkip = 1

#### Lab: Swimming pool (全成對)
以下是一個游泳池收費系統的規則：

* 一般票價 200
* 星期六日250元, 除會員以外不打折
* 12（含）歲以下、60歲（含）以上打八折，限定 3-75 歲可入內游泳
* 七點以前八折
* 團體打七折
* 會員打五折
* 各打折不得重疊使用，以顧客最有利方案定價

請用 
1. 請用 All-pair testing 方法論，應用 excel 工具規劃測試案例
2. 將 excel 轉為 csv file, 使用 JUnit `CsvFileSource` 來進行測試

#### Lab: Which day

2021/1/1 是星期五，輸入月份和日期（一樣是2021年），輸出是星期幾。
1. 使用參數化測試進行測試 (CsvSource)
2. 使用 CsvSourceFile 進行測試

#### Lab: Currency
* 一個 Currency 的類別，內封裝金額與幣值的屬性，幣值可以有 NT 和 US 兩種幣值，其匯率為 30。如果是台幣+美金則回傳台幣，若美金+台幣則回傳為美金（由第一個決定），請設計程式與測試案例。
* 使用參數化測試進行測試

```java
class Currency {
    int amount;
    String symbol;
    public Currency(int a, String s) {
        ...
    }
    public Currency add(Currency other) {
        ...
    }    
}

Currency nt100 = new Currency(100, "NT");
Currency us100 = new Currency(100, "US");
...
```
### Demo: Condition

See [JUnit doc- Conditional Test Execution](https://junit.org/junit5/docs/current/user-guide/#writing-tests-conditional-execution)
* Some tests only execute on MAC environment
* `@TestOnMac`


### Demo: Assume

在JUnit 5中，Assumptions（前提條件）是一種用於定義測試前提條件的機制。它允許你在執行測試之前檢查某些條件，如果條件不滿足，則可以將測試視為已經通過，跳過執行測試。這在某些情況下非常有用，例如當測試需要特定的環境或資源時，如果條件不滿足，測試就沒有意義。

JUnit 5中Assumptions類提供了多個靜態方法來執行前提條件檢查，以下是其中一些重要的方法：

1. `assumeTrue(boolean condition)`：如果條件為true，則測試繼續執行；如果條件為false，則測試被視為已通過並跳過執行。

```java
@Test
void testSomething() {
    assumeTrue(isEnvironmentReady()); // 假設環境已準備好
    // 執行測試邏輯
}
```

2. `assumeFalse(boolean condition)`：如果條件為false，則測試繼續執行；如果條件為true，則測試被視為已通過並跳過執行。

```java
@Test
void testSomething() {
    assumeFalse(isEnvironmentInvalid()); // 假設環境無效
    // 執行測試邏輯
}
```

3. `assumingThat(boolean assumption, Executable executable)`：在滿足特定前提條件時執行測試邏輯，否則將測試視為已通過並跳過執行。

```java
@Test
void testSomething() {
    assumingThat(isInDevelopmentMode(), () -> {
        // 只有在開發模式下才執行這段測試邏輯
    });
    // 其他測試邏輯
}
```

在上述範例中，使用Assumptions可以在測試執行之前進行條件檢查。如果條件滿足，測試將繼續執行；如果條件不滿足，測試將被視為已通過並跳過執行。這有助於確保測試在正確的環境或條件下執行，並減少無效或不必要的測試執行。

Assumptions的使用方法能夠提供更靈活的控制，可以根據特定的前提條件來選擇是否執行測試，從而更好地管理和組織測試案例。

## Exercise

- 以下何者為真？（多選）	
	- @Test 用以標記一個測試碼;
	- @Before 用以標記測試執行前的設定與準備，多個測試僅會執行一次;
	- 僅針對有回傳值的方法進行測試，無回傳的方法則無從檢驗;
	- assertEquals() 針對預期值與真實質進行比對，對於有小數誤差的計算則無從檢驗;
	- @Exception 用以標記該測試預期會拋出例外，用以檢驗是否正確拋出例外。
	- 在 JUnit 中 @After 表示每一個@Test 測試後要執行的程式。		
	
- 以下 JUnit 測試碼執行後會輸出什麼？
```java
public class AdditionTest {
 @Test
 public void test1() {
    Addition a = new Addition();
    assertEquals(12, a.add(3,9));
    println("A");
 }	
 @Test
 public void test2() {
    Addition a = new Addition();
    assertEquals(24, a.add(12,12));
    println("B");
  }	
 @BeforeClass
  public static void beforeClass() {
    println("C");
  }	
 @Before
  public void before() {
    println("D");
  }	
 @After
 public void after () {
   println("E");
 }
 @AfterClass
 public static void afterclass() {
   println("F");
 }
}
```
- 全班分為兩大群：測試群與程式群，依據以下題目做平行開發與測試，採用測試先行的方式。
	- 加法 int add(int, int) 
	- 最大公因數 int gcd(int, int) 
	- 圓面積 double area(double) 	
	
- 寫一個程式來依據 BMI 檢驗體重是否過重，並用 JUnit 測試之。其分級如下： 
    - 體重過輕 BMI ＜ 18.5 
    - 正常範圍 18.5 <= BMI ＜24 
    - 過重 24 <= BMI ＜ 27 
    - 輕度肥胖 27 <= BMI ＜ 30 
    - 中度肥胖 30 <= BMI ＜ 35 
    - 重度肥胖 BMI >= 35 

- 寫一個程式判斷三角形。輸入的參數是三邊的長，若不符合三角形定義（任兩邊和大於第三邊）則會拋出例外。否則回傳 "一般三角形"、"等腰三角形"、"正三角形"等。開發之前，請先建立 JUnit 測試案例，並使用 @Test(expected=...) 的方式來測試例外。

- 針對一個排序程式設計測試案例 SortTest，每一次測試之前會先做一些初始化：從檔案中讀取資料，寫到陣列 data[]中，SortTest 中的 testSort() 再針對 data[] 中的資料做排序。請利用 @Before 來完成此工作。

- 寫一個無窮迴圈的程式，並使用 junit 來測試。利用 timeout 的參數來跳出迴圈。