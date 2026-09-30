## 例外處理

> 把「例外」視為「常態」是專業工程師必備的態度。

你不能掌控的事都可能出錯，發生例外。要寫一個穩健（robust）的系統，必須非常的細心，考慮到各種可能的例外情況，各種可能出錯的環境變數，並且做出應對。


Java 的例外處理機制（Exception Handling）是一種用來管理程式在執行期間所發生異常狀況的機制，使得程式能夠在面對錯誤時繼續執行或優雅地中止。Java 使用 `try-catch-finally` 這樣的結構來捕捉並處理例外情況。

以下是 Java 例外處理的關鍵概念與操作方式：

### 1. **例外類別層次結構**
Java 中的例外都是基於 `Throwable` 類別。它分為兩大類：
- **Checked Exception**（受檢例外）：這些是程式在編譯時就必須處理的例外，例如 `IOException`、`SQLException` 等。這類例外要求必須使用 `try-catch` 或在方法上宣告 `throws`。
    - 可能會發生，但程式設計師無法預防的。
    - 不處理則編譯錯誤。

- **Unchecked Exception**（未檢例外）：這些是繼承自 `RuntimeException` 的例外，如 `NullPointerException`、`ArrayIndexOutOfBoundsException` 等。
    - 這些例外通常是程式邏輯錯誤，編譯時不強制要求處理。
    - 若發生但沒有 catch, 則會中斷程式。

### 2. **例外處理的語法**
Java 例外處理主要使用以下三個關鍵字：

- `try`：包含可能會拋出例外的程式塊。
- `catch`：用來捕捉並處理特定的例外。
- `finally`：無論是否發生例外，`finally` 區塊內的程式碼都會被執行（通常用來釋放資源）。

範例：
```java
public class ExceptionExample {
    public static void main(String[] args) {
        try {
            int result = 10 / 0; // 這裡會引發 ArithmeticException
        } catch (ArithmeticException e) {
            System.out.println("捕捉到例外: " + e.getMessage());
        } finally {
            System.out.println("無論如何都會執行");
        }
    }
}
```
輸出：
```
捕捉到例外: / by zero
無論如何都會執行
```

### 3. **throws 關鍵字**
當一個方法內可能會拋出受檢例外，但該方法不打算處理時，可以使用 `throws` 關鍵字將例外拋給上層呼叫者處理。例如：
```java
public void readFile() throws IOException {
    // 這裡會拋出 IOException，並告訴呼叫者需要處理
}
```

### 4. **throw 關鍵字**
`throw` 關鍵字用來手動拋出例外。例如：
```java
public void checkAge(int age) throws IllegalArgumentException {
    if (age < 18) {
        throw new IllegalArgumentException("年齡必須大於18");
    }
}
```

### 5. **多重 catch 區塊**
你可以在一個 `try` 區塊後使用多個 `catch` 區塊來處理不同類型的例外：
```java
try {
    // 可能會拋出多種例外的程式碼
} catch (NullPointerException e) {
    System.out.println("NullPointerException 被捕捉到");
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("ArrayIndexOutOfBoundsException 被捕捉到");
} catch (Exception e) {
    System.out.println("其他例外: " + e.getMessage());
}
```

### 6. **try-with-resources**
這是一個自動管理資源的結構，適用於實現了 `AutoCloseable` 介面的資源（如文件流）。當使用這個結構時，資源會在結束後自動關閉，無需在 `finally` 中手動釋放資源。(`FileReader` 有實作 `AutoCloseable`)
```java
try (FileReader reader = new FileReader("test.txt")) {
    // 讀取文件
} catch (IOException e) {
    System.out.println("捕捉到 IO 例外");
}
```

Java 的例外處理機制提供了靈活性與可讀性，使得程式能夠在面對不預期情況時正確處理並維持穩定運行。


## 捕捉或宣告原則
捕捉或宣告原則 Catch or Declare Rule (CDR)：
> 對於例外你只有兩個選擇：一是處理它，二是宣告這個例外讓呼叫者處理。

> 寫一個模組程式和做一件事一樣，一方面你要把事情做好，一方面你要知道如何界定工作範圍 -- 對於你不能處理的事，你得上承給他人處理。


## 自訂例外

自定義例外類別在 Java 中非常簡單，你可以透過繼承 `Exception` 或 `RuntimeException` 類別來創建屬於你自己的例外類別。`TriangleException` 可以用來表示三角形相關的錯誤，例如當邊長不符合三角形不等式定理時拋出此例外。


```java
public class TriangleException extends Exception {
    private double a;
    private double b;
    private double c;

    public TriangleException(String message, double a, double b, double c) {
        super(message);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public String toString() {
        return super.getMessage() + ": (" + a + ", " + b + ", " + c + ")";
    }
}
```

![](https://media.geeksforgeeks.org/wp-content/uploads/20241218154434430661/Exception-Handling-768.png)

在 **Java 例外處理機制（Exception Handling）** 中，`Throwable` 是所有例外（Exception）和錯誤（Error）的根類別。Java 透過 `Throwable` 及其子類別來處理異常情況。以下是 Java 例外架構的層級關係：

---
## Throwable
### **1️⃣ `Throwable`（可拋出的物件）**
- **最頂層的類別**，所有可以被 `throw` 和 `catch` 的例外與錯誤都繼承自 `Throwable`。
- 主要有兩大子類別：
  1. `Exception`（異常）
  2. `Error`（錯誤）

```java
public class Throwable extends Object implements Serializable
```
---

### **2️⃣ `Exception`（異常，Checked Exception）**
- **表示程式可預期的異常情況**，通常是因為用戶輸入錯誤、文件未找到、網絡連線問題等。
- 需要 **顯式處理**（用 `try-catch` 或 `throws`）。
- 常見子類別：
  - `IOException`（輸入/輸出錯誤）
  - `SQLException`（資料庫操作錯誤）
  - `FileNotFoundException`（找不到檔案）
  - `InterruptedException`（執行緒中斷）

### **📌 Checked Exception 特點**
- **編譯時檢查**：如果方法可能拋出 `Exception`，必須 **顯式** 使用 `try-catch` 或 `throws` 來處理，否則會編譯錯誤。

**範例：IOException**
```java
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CheckedExceptionExample {
    public static void main(String[] args) {
        try {
            File file = new File("test.txt");
            FileReader reader = new FileReader(file);
        } catch (IOException e) {
            System.out.println("檔案讀取失敗：" + e.getMessage());
        }
    }
}
```

---

### **3️⃣ `RuntimeException`（執行期異常，Unchecked Exception）**
- `RuntimeException` 是 `Exception` 的 **子類別**，但它屬於 **非受檢異常（Unchecked Exception）**。
- **不需要強制處理**，即可以不用 `try-catch` 或 `throws`。
- 常見子類別：
  - `NullPointerException`（空指標異常）
  - `ArrayIndexOutOfBoundsException`（陣列索引超出範圍）
  - `ArithmeticException`（算術錯誤，如除以 0）
  - `ClassCastException`（類別轉型錯誤）

#### **📌 Unchecked Exception 特點**
- **執行期檢查**：編譯時不強制處理，但會在執行時發生錯誤。
- 代表 **程式邏輯錯誤**，通常應該透過修正程式碼來避免，而不是用 `try-catch` 掩蓋。

**範例：NullPointerException**
```java
public class UncheckedExceptionExample {
    public static void main(String[] args) {
        String str = null;
        System.out.println(str.length()); // 會拋出 NullPointerException
    }
}
```

---

### **4️⃣ `Error`（錯誤）**
- 代表 **JVM 無法恢復的嚴重錯誤**，如記憶體不足、系統崩潰等。
- **不應該在程式中捕獲或處理**，通常代表程式已進入無法修復的狀態。
- 常見子類別：
  - `OutOfMemoryError`（記憶體溢出）
  - `StackOverflowError`（堆疊溢出，通常是無窮遞迴）
  - `VirtualMachineError`（JVM 內部錯誤）
  - `AssertionError`（`assert` 斷言錯誤）

**範例：StackOverflowError（無窮遞迴）**
```java
public class ErrorExample {
    public static void recursiveMethod() {
        recursiveMethod(); // 無窮遞迴，會導致 StackOverflowError
    }

    public static void main(String[] args) {
        recursiveMethod();
    }
}
```

**🛑 注意：AssertionError 是一個不該發生的問題，所以不是一個 Exception**

**🛑 注意：Error 通常不應該用 `try-catch` 捕獲！**

---

### **🎯 總結：Java Exception 層級**
```
Throwable
├── Exception (受檢異常 Checked Exception)
│   ├── IOException
│   ├── SQLException
│   ├── InterruptedException
│   ├── RuntimeException (未受檢異常 Unchecked Exception)
│       ├── NullPointerException
│       ├── ArithmeticException
│       ├── ArrayIndexOutOfBoundsException
│       ├── ClassCastException
│       ├── IllegalArgumentException
│
└── Error (致命錯誤)
    ├── OutOfMemoryError
    ├── StackOverflowError
    ├── VirtualMachineError
    ├── AssertionError
```

---

### **🔹 何時使用哪種例外？**
| 類別               | 是否必須處理？          | 代表意義                              | 典型情境                                                                    |
| ------------------ | ----------------------- | ------------------------------------- | --------------------------------------------------------------------------- |
| `Exception`        | ✅ 必須處理（Checked）   | 可預期的異常，應該 **適當處理**       | 檔案不存在 (`FileNotFoundException`)、網路錯誤 (`IOException`)              |
| `RuntimeException` | ❌ 可不處理（Unchecked） | 代表程式邏輯錯誤，應該 **修正程式碼** | 陣列超界 (`ArrayIndexOutOfBoundsException`)、除以零 (`ArithmeticException`) |
| `Error`            | 🚨 不應處理              | JVM 無法恢復的錯誤，應該 **避免發生** | 記憶體溢出 (`OutOfMemoryError`)、遞迴太深 (`StackOverflowError`)            |

---

### **✨ 總結**
1. **`Throwable` 是所有異常和錯誤的根類別**。
2. **`Exception` 是受檢異常（Checked Exception）**，必須用 `try-catch` 或 `throws` 處理。
3. **`RuntimeException` 是未受檢異常（Unchecked Exception）**，通常代表程式設計錯誤，應修正程式碼。
4. **`Error` 是 JVM 層級的錯誤**，通常是系統問題，應避免發生而非捕獲。

這樣的架構讓 Java 具有強大的異常處理能力，同時保持程式的健壯性。希望這個解釋能幫助你清楚理解 Java 例外的設計原則！💡🚀

## Quiz

### Quiz01: 以下程式是否有錯誤？
```java
import java.io.FileNotFoundException;

public class ThrowKeywordDemo {
    public void someMethod() {
        throw new FileNotFoundException("檔案不存在");
    }

    public static void main(String[] args) {
        ThrowKeywordDemo demo = new ThrowKeywordDemo();
        demo.someMethod();
    }
}
```

### Quiz02: 說明以下程式執行的狀況

```java
public class RuntimeAndFinallyDemo {
    public static void main(String[] args) {
        int result = 0;
        try {
            int[] numbers = {10};
            System.out.println("嘗試執行除法");
            result = numbers[0] / 0; // 潛在錯誤
            System.out.println("除法完成");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("捕捉到：陣列索引越界");
        } finally {
            System.out.println("Finally 區塊執行");
        }
        System.out.println("Result: " + result);
    }
}
```

### Quiz03: 以下程式有何問題？該如何改善？

```java
public class MultiCatchDemo {
    public static void main(String[] args) {
        try {
            String s = null;
            s.length(); // 潛在錯誤
            System.out.println("執行成功");
        } catch (Exception e) {
            System.out.println("捕捉到：Exception");
        } catch (NullPointerException e) {
            System.out.println("捕捉到：NullPointerException");
        }
    }
}
```

### Quiz04: 以下程式會如何執行？

```java
public class FinallyReturnDemo {
    public static int getValue() {
        int x = 1;
        try {
            System.out.println("Try 開始");
            return x;
        } finally {
            x = 2;
            System.out.println("Finally 執行，x=" + x);
        }
    }

    public static void main(String[] args) {
        System.out.println("返回值: " + getValue());
    }
}
```

## Lab & Practice (實習展示與動手練習)

本單元包含展示程式碼與實習練習題，皆位於 [`src/main/java/u02_robust/exception/`](../../src/main/java/u02_robust/exception/)：

### 示範 01: [ExceptionBasicsDemo.java](../../src/main/java/u02_robust/exception/ExceptionBasicsDemo.java)
* 涵蓋 Checked vs Unchecked Exception、Catch or Declare Rule (CDR)、`try-catch-finally` 與 `try-with-resources`。

### 示範 02: [TriangleExceptionDemo.java](../../src/main/java/u02_robust/exception/TriangleExceptionDemo.java)
* 自訂受檢例外 `TriangleException`：當邊長小於等於零或不符合三角形不等式定理時拋出，並封裝邊長情境資料。

---

## Exercise (學生自主練習)

> 💡 **自主學習流程**：
> 1. 打開練習程式碼，依據 `TODO` 註解動手實作。
> 2. 執行對應的單元測試驗證是否全部通過。
> 3. 若卡關或想確認最佳寫法，再點開下方的摺疊區塊參考解答。

---

### Ex01 & Ex02: [SafeCalculatorPractice.java](../../src/main/java/u02_robust/exception/SafeCalculatorPractice.java)
* **題目**：安全計算機之字串解析與除法防禦。
* **練習任務**：
  - `TODO 1`：使用 `try-catch` 處理 `NumberFormatException`（使用者輸入非數字字串，如 `"abc"`）。
  - `TODO 2`：使用 `try-catch` 處理 `ArithmeticException`（除數為零時給予友善提示，避免系統崩潰）。
* **單元測試指令**：
  ```bash
  mvn test -Dtest=SafeCalculatorPracticeTest
  ```

<details>
<summary>💡 點擊展開：Ex01 & Ex02 參考解答與解析</summary>

```java
try {
    int numerator = Integer.parseInt(numeratorStr.trim());
    int denominator = Integer.parseInt(denominatorStr.trim());
    int quotient = numerator / denominator;
    return CalculationResult.ok(quotient);

} catch (NumberFormatException e) {
    // TODO 1: 處理數字格式異常
    return CalculationResult.error("輸入格式錯誤：請輸入合法的整數數值 (數字解析失敗)");
} catch (ArithmeticException e) {
    // TODO 2: 處理除零異常
    return CalculationResult.error("數學運算錯誤：除數不能為零！");
}
```

**解析說明**：
- `Integer.parseInt` 在遇到無法解析的字元時會丟出 `NumberFormatException`（屬於 `RuntimeException`）。
- 整數除法 `/ 0` 會拋出 `ArithmeticException`。若預期可能由外部輸入引發，應以 `try-catch` 捕捉並回傳領域結果物件，而不是直接讓未處理的例外中斷執行緒。
</details>

---

### Ex03: [BankAccountPractice.java](../../src/main/java/u02_robust/exception/BankAccountPractice.java)
* **題目**：銀行帳戶提款與自訂例外。
* **練習任務**：
  - 設計自訂受檢例外 `InsufficientFundsException extends Exception`，包含帳號、目前餘額、欲提領金額與短缺金額。
  - 在 `BankAccount.withdraw(amount)` 中檢驗餘額，不足時拋出該例外。
* **單元測試指令**：
  ```bash
  mvn test -Dtest=BankAccountPracticeTest
  ```

<details>
<summary>💡 點擊展開：Ex03 參考解答與解析</summary>

```java
// 1. 自訂受檢例外設計
public static class InsufficientFundsException extends Exception {
    private final String accountNumber;
    private final double currentBalance;
    private final double attemptedAmount;

    public InsufficientFundsException(String accountNumber, double currentBalance, double attemptedAmount) {
        super(String.format("帳號 %s 提款失敗：欲提領金額 %.2f 超過目前餘額 %.2f (短缺 %.2f 元)",
                accountNumber, attemptedAmount, currentBalance, (attemptedAmount - currentBalance)));
        this.accountNumber = accountNumber;
        this.currentBalance = currentBalance;
        this.attemptedAmount = attemptedAmount;
    }

    public double getDeficit() { return attemptedAmount - currentBalance; }
}

// 2. withdraw 方法中的檢查與拋出
public synchronized void withdraw(double amount) throws InsufficientFundsException {
    if (amount <= 0) {
        throw new IllegalArgumentException("提款金額必須大於 0");
    }

    // TODO: 練習 - 檢查餘額
    if (amount > this.balance) {
        throw new InsufficientFundsException(this.accountNumber, this.balance, amount);
    }

    this.balance -= amount;
}
```

**解析說明**：
- 自訂業務例外繼承 `Exception`（受檢例外），強制呼叫端面對業務失敗（如餘額不足）時必須採取因應作為（例如提示使用者補存或交易失敗），貫徹 CDR 原則。
</details>



