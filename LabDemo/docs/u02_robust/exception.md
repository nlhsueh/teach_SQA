# 實習 02：例外處理 (Exception Handling)

> 把「例外」視為「常態」是專業工程師必備的態度。

你不能掌控的事都可能出錯、發生例外。要寫一個穩健（robust）的系統，必須非常細心，考慮到各種可能的例外情況、各種可能出錯的環境變數，並且做出應對。

Java 的例外處理機制（Exception Handling）是一種用來管理程式在執行期間所發生異常狀況的機制，使得程式能夠在面對錯誤時繼續執行或優雅地中止。Java 使用 `try-catch-finally` 結構來捕捉並處理例外。

本單元**全程以「氣泡排序（BubbleSort）」演算法與實務資料處理情境**為核心範例，帶領大家一次通透 Java 例外架構、受檢與未檢例外、語法關鍵字、現代資源管理以及自訂業務例外。

---

## 1. 氣泡排序 (BubbleSort) 與例外？

在實作與呼叫排序演算法時，我們常會遇到以下典型異常情境：
1. **輸入參數非法 (Unchecked Exception: `IllegalArgumentException` / `NullPointerException`)**：
   傳入 `null` 陣列。若未進行防禦性檢查，將直接拋出 `NullPointerException`；若有做好公開 API 前置條件檢查，則會主動拋出 `IllegalArgumentException`。
2. **演算法迴圈邊界越界 (Unchecked Exception: `ArrayIndexOutOfBoundsException`)**：
   雙層迴圈邊界失誤（如將內層寫成 `i < length` 但裡面存取 `data[i + 1]`），導致陣列索引超出界線。
3. **外部資料來源失效 (Checked Exception: `FileNotFoundException` / `IOException`)**：
   待排序資料往往來自外部檔案或串流，當指定的檔案不存在或硬體讀取失敗時，必須遵循捕捉或宣告原則（CDR）。
4. **外部資料格式損毀 (Unchecked Exception: `NumberFormatException`)**：
   從文字檔案逐行讀取數字轉成整數陣列時，遇到非數字字元或格式異常。
5. **演算法驗證失敗 (自訂受檢例外: `SortingException`)**：
   當排序演算法執行完成後進行後置條件驗證，若未符合非遞減順序，可主動拋出自訂受檢例外，封裝違規索引與陣列快照。
6. **系統致命錯誤 (Error: `StackOverflowError` / `OutOfMemoryError`)**：
   若採用遞迴版氣泡排序，當終止條件失效或遞迴規模未正確縮小，將耗盡呼叫堆疊引發 `StackOverflowError`；若嘗試配置超大陣列則會耗盡 JVM 記憶體引發 `OutOfMemoryError`。

---

## 2. 例外處理核心語法

### 2.1 `try-catch-finally`
- `try`：包含可能會拋出例外的程式碼區塊。
- `catch`：捕捉特定例外並進行修復或記錄日誌。
- `finally`：**無論是否發生例外、是否被 catch 捕捉，`finally` 區塊內的程式碼保證一定會執行**（通常用來記錄統計資訊或釋放系統資源）。

```java
int[] sample = { 5, 2, 9, 1 };
try {
    // 呼叫帶有邊界缺陷的排序方法
    buggyBoundarySort(sample);
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("捕捉到未檢例外：陣列索引越界 - " + e.getMessage());
} finally {
    System.out.println("【Finally 保證執行】排序作業嘗試完畢，進行統計日誌記錄與環境清理。");
}
```

### 2.2 `throw` 關鍵字（主動拋出例外）
在方法內部，當發現前置條件被破壞或業務規則不符時，使用 `throw` 關鍵字主動建立並拋出例外物件：

```java
public static void bubbleSort(int[] data) {
    // 公開 API 前置防禦：若傳入 null，主動拋出非受檢例外
    if (data == null) {
        throw new IllegalArgumentException("待排序陣列不可為 null (前置條件防禦)");
    }
    // ... 排序主邏輯
}
```

### 2.3 `throws` 關鍵字與捕捉或宣告原則 (CDR)
**捕捉或宣告原則（Catch or Declare Rule - CDR）**：
> 對於受檢例外（Checked Exception），你只有兩個選擇：
> 1. 在內部使用 `try-catch` 捕捉並處理它。
> 2. 在方法簽名使用 `throws` 宣告該例外，交給上一層呼叫者處理。

```java
// 方法無法在內部決定檔案遺失時如何因應，因此使用 throws 宣告向上傳遞
public static int[] loadAndSort(File file) throws IOException, SortingException {
    if (!file.exists()) {
        throw new FileNotFoundException("找不到指定的排序輸入檔案: " + file.getAbsolutePath());
    }
    // ... 讀檔與排序邏輯
}
```

### 2.4 多重 catch (Multi-catch) 與捕捉順序
在從外部檔案載入資料並進行氣泡排序時，可能引發多種不同型態的例外。此時可以使用多個 `catch` 區塊分別處理：

```java
try {
    loadAndSort(dataFile);
} catch (FileNotFoundException e) {
    System.out.println("檔案未找到，請檢查路徑: " + e.getMessage());
} catch (IOException e) {
    System.out.println("檔案讀取發生 IO 錯誤: " + e.getMessage());
} catch (SortingException e) {
    System.out.println("演算法排序結果檢驗失敗: " + e.getMessage());
} catch (Exception e) {
    System.out.println("其他未預期異常: " + e.getMessage());
}
```
> ⚠️ **重要捕捉順序規則：子類別例外必須排在父類別例外之前**！若將 `Exception` 放在 `FileNotFoundException` 之前，會導致編譯錯誤（Unreachable catch block）。

### 2.5 現代資源管理：`try-with-resources`
當排序資料需要由檔案（如 `FileReader`、`BufferedReader`）讀入時，使用 `try-with-resources` 可確保資源在離開區塊後**自動關閉**，無需手動於 `finally` 呼叫 `close()`：

```java
// BufferedReader 實作了 AutoCloseable 介面
try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
    String line;
    while ((line = reader.readLine()) != null) {
        list.add(Integer.parseInt(line.trim()));
    }
} // 離開此區塊時，reader 保證自動關閉，即使發生例外也不會外洩檔案控制代碼
```

---

## 3. Java 例外架構圖與 Throwable 體系深入剖析

在 Java 中，所有例外與錯誤皆源自 `java.lang.Throwable` 類別：

```
Throwable (所有異常與錯誤的根類別)
├── Exception (程式可預期且應處理的異常)
│   ├── IOException / FileNotFoundException (受檢例外 Checked Exception - 必須 catch 或 declare)
│   ├── SortingException (自訂受檢例外 - 業務驗證失敗)
│   │
│   └── RuntimeException (未檢例外 Unchecked Exception - 通常代表程式碼邏輯缺陷)
│       ├── NullPointerException (未檢查 null 直接存取 length)
│       ├── ArrayIndexOutOfBoundsException (雙層迴圈邊界越界)
│       ├── IllegalArgumentException (前置參數防禦性檢查失敗)
│       └── NumberFormatException (文字轉數字解析失敗)
│
└── Error (JVM 層級致命錯誤 - 不應由應用程式 try-catch)
    ├── StackOverflowError (遞迴版氣泡排序無窮遞迴耗盡呼叫堆疊)
    ├── OutOfMemoryError (嘗試配置超大陣列導致記憶體枯竭)
    └── AssertionError (斷言失敗，表示內部狀態不變量被打破)
```

### 3.1 Checked Exception（受檢例外）
* **特點**：編譯時期強制檢查，必須使用 `try-catch` 或 `throws` 處理，否則**無法通過編譯**。
* **代表意義**：屬於可預期、常態下受外部環境（如檔案系統、網路連線）影響的異常。
* **氣泡排序情境**：從檔案載入欲排序的陣列（`FileNotFoundException`、`IOException`）。

### 3.2 Unchecked Exception（未檢例外 / RuntimeException）
* **特點**：編譯時期不強制宣告或捕捉，通常反映了**程式設計師的邏輯缺陷**。
* **防禦原則**：最佳策略是**撰寫正確的程式碼邏輯與前置防禦**，而不是在錯誤發生後用 `try-catch` 掩飾。
* **氣泡排序情境**：
  * `NullPointerException`：未防禦傳入的待排序陣列是否為 `null`，直接存取 `data.length`。
  * `ArrayIndexOutOfBoundsException`：內層迴圈邊界寫錯，在比較 `data[i] > data[i + 1]` 時最後一項超出陣列長度。
  * `IllegalArgumentException`：公開 API 主動防禦參數，若傳入不合規陣列則拋出。

### 3.3 Error（致命錯誤）
* **特點**：代表 JVM 底層發生嚴重崩潰或資源耗盡狀態，應用程式**絕不應該使用 `try-catch` 捕捉或嘗試恢復**。
* **氣泡排序情境**：
  * `StackOverflowError`：將氣泡排序寫成遞迴版時，因終止條件或參數傳遞錯誤（如誤傳 `n` 而非 `n - 1`）導致無窮遞迴耗盡堆疊。
  * `OutOfMemoryError`：嘗試建立超過 JVM Heap 容量的超大陣列（例如 `new int[Integer.MAX_VALUE - 1]`）準備排序。

---

## 4. 自訂受檢例外 (Custom Exception)：`SortingException`

在高品質軟體工程中，當標準例外無法完整表達領域情境時，我們會自訂例外類別。
以氣泡排序為例，當排序演算法執行完畢但後置檢驗發現順序不正確時，拋出 `SortingException` 並封裝出錯時的陣列現況與違規索引：

```java
public static class SortingException extends Exception {
    private final int[] rawData;
    private final int failedIndex;

    public SortingException(String message, int[] rawData, int failedIndex) {
        super(String.format("%s (在索引 [%d] 處違反遞增順序: %d > %d, 現況: %s)",
                message, failedIndex, rawData[failedIndex], rawData[failedIndex + 1],
                Arrays.toString(rawData)));
        this.rawData = rawData.clone();
        this.failedIndex = failedIndex;
    }

    public int[] getRawData() { return rawData.clone(); }
    public int getFailedIndex() { return failedIndex; }
}
```

---

## 5. 完整教學示範程式碼 (Complete Runnable Code)

本教學展示程式已完整實作於專案中的 [`src/main/java/u02_robust/exception/BubbleSortExceptionDemo.java`](../../src/main/java/u02_robust/exception/BubbleSortExceptionDemo.java)，可直接編譯並執行：

```bash
# 編譯並執行展示程式
mvn compile -q
java -cp target/classes u02_robust.exception.BubbleSortExceptionDemo
```

### 完整原始程式碼：

```java
package u02_robust.exception;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 氣泡排序例外處理教學展示範例：BubbleSortExceptionDemo
 *
 * 【核心學習目標】：
 * 1. 未檢例外 (Unchecked Exception / RuntimeException)：
 *    - NullPointerException：傳入 null 陣列時引發。
 *    - ArrayIndexOutOfBoundsException：雙層迴圈邊界錯誤引發索引越界。
 *    - IllegalArgumentException：公開 API 主動防禦非法參數。
 * 2. 受檢例外 (Checked Exception)：
 *    - IOException / FileNotFoundException：從檔案讀取待排序陣列，示範 CDR 原則與 try-with-resources。
 *    - SortingException (自訂例外)：繼承 Exception，封裝排序演算法後置驗證失敗的詳細情境。
 * 3. 致命錯誤 (Error)：
 *    - StackOverflowError：遞迴版氣泡排序在規模過大時耗盡 JVM 堆疊。
 * 4. 現代例外處理結構：
 *    - try-catch-finally 保證資源釋放。
 *    - try-with-resources 自動關閉檔案串流。
 *    - Multi-catch 多重捕捉處理不同失敗情境。
 */
public class BubbleSortExceptionDemo {

    // =========================================================================
    // 1. 自訂受檢例外 (Custom Checked Exception)
    // =========================================================================

    /**
     * 自訂排序例外：當排序後的陣列經檢驗未符合排序規則時拋出
     */
    public static class SortingException extends Exception {
        private final int[] rawData;
        private final int failedIndex;

        public SortingException(String message, int[] rawData, int failedIndex) {
            super(String.format("%s (在索引 [%d] 處違反遞增順序: %d > %d, 現況: %s)",
                    message, failedIndex, rawData[failedIndex], rawData[failedIndex + 1],
                    Arrays.toString(rawData)));
            this.rawData = rawData.clone();
            this.failedIndex = failedIndex;
        }

        public int[] getRawData() {
            return rawData.clone();
        }

        public int getFailedIndex() {
            return failedIndex;
        }
    }

    // =========================================================================
    // 2. 核心排序方法與各類例外示範
    // =========================================================================

    /**
     * 標準穩健氣泡排序：包含 IllegalArgumentException 防禦與自訂受檢例外檢驗
     *
     * @param data 待排序陣列
     * @throws IllegalArgumentException 若傳入 null
     * @throws SortingException         若排序演算法未正確完成升冪排序
     */
    public static void bubbleSort(int[] data) throws SortingException {
        // [Unchecked Exception: IllegalArgumentException 防禦]
        if (data == null) {
            throw new IllegalArgumentException("待排序陣列不可為 null (前置條件防禦)");
        }

        int length = data.length;
        if (length <= 1) {
            return;
        }

        for (int pass = 0; pass < length - 1; pass++) {
            boolean swapped = false;
            for (int i = 0; i < length - pass - 1; i++) {
                if (data[i] > data[i + 1]) {
                    int temp = data[i];
                    data[i] = data[i + 1];
                    data[i + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }

        // 驗證排序結果，若失敗則拋出自訂受檢例外
        for (int i = 0; i < length - 1; i++) {
            if (data[i] > data[i + 1]) {
                throw new SortingException("氣泡排序後置檢驗失敗", data, i);
            }
        }
    }

    /**
     * 刻意帶有邊界缺陷的氣泡排序：示範未檢例外 (ArrayIndexOutOfBoundsException)
     *
     * @param data 待排序陣列
     */
    public static void buggyBoundarySort(int[] data) {
        if (data == null) {
            throw new IllegalArgumentException("待排序陣列不可為 null");
        }
        int length = data.length;
        // 刻意將迴圈寫成 i < length，當存取 data[i + 1] 時在最後一個元素必定超出邊界
        for (int i = 0; i < length; i++) {
            if (data[i] > data[i + 1]) { // 💥 引發 ArrayIndexOutOfBoundsException
                int temp = data[i];
                data[i] = data[i + 1];
                data[i + 1] = temp;
            }
        }
    }

    /**
     * 未防禦 null 的氣泡排序：示範未檢例外 (NullPointerException)
     */
    public static void unshieldedSort(int[] data) {
        // 未檢查 data 是否為 null，直接呼叫 data.length
        int length = data.length; // 💥 若 data 為 null 則引發 NullPointerException
        System.out.println("陣列長度: " + length);
    }

    /**
     * 從檔案讀取整數陣列並排序：示範受檢例外 (Checked Exception)、CDR 原則與 try-with-resources
     *
     * @param file 檔案物件
     * @return 排序後的陣列
     * @throws IOException 當檔案讀取失敗時向上宣告
     */
    public static int[] loadAndSort(File file) throws IOException, SortingException {
        if (!file.exists()) {
            throw new FileNotFoundException("找不到指定的排序輸入檔案: " + file.getAbsolutePath());
        }

        List<Integer> list = new ArrayList<>();
        // try-with-resources：保證 reader 在離開區塊時自動關閉
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    list.add(Integer.parseInt(line)); // 可能拋出 NumberFormatException
                }
            }
        }

        int[] result = list.stream().mapToInt(Integer::intValue).toArray();
        bubbleSort(result);
        return result;
    }

    /**
     * 帶有無窮遞迴缺陷的氣泡排序：示範致命錯誤 (StackOverflowError)
     * 若忘記將遞迴規模減 1 (如誤寫為 n)，將導致無窮遞迴耗盡呼叫堆疊
     *
     * @param data 陣列
     * @param n    待排序範圍長度
     */
    public static void recursiveBubbleSortWithBug(int[] data, int n) {
        if (n <= 1) {
            return;
        }
        for (int i = 0; i < n - 1; i++) {
            if (data[i] > data[i + 1]) {
                int temp = data[i];
                data[i] = data[i + 1];
                data[i + 1] = temp;
            }
        }
        // 💥 缺陷：誤將規模傳為 n 而非 n - 1，導致無窮遞迴
        recursiveBubbleSortWithBug(data, n);
    }

    // =========================================================================
    // 3. 展示主程式：涵蓋 try-catch-finally、多重 catch 與錯誤觀摩
    // =========================================================================

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" 軟體品質保證 (SQA) 實習示範：BubbleSort 例外處理展示");
        System.out.println("==================================================");

        // ---------------------------------------------------------------------
        // 場景 1: NullPointerException 與 IllegalArgumentException 防禦
        // ---------------------------------------------------------------------
        System.out.println("\n[場景 1] 參數防禦 (IllegalArgumentException vs NullPointerException)");
        try {
            System.out.println(">> 傳入 null 呼叫 bubbleSort...");
            bubbleSort(null);
        } catch (IllegalArgumentException e) {
            System.out.println(">> 成功捕捉主動防禦之例外: " + e.getMessage());
        } catch (SortingException e) {
            System.out.println(">> 排序例外: " + e.getMessage());
        }

        // ---------------------------------------------------------------------
        // 場景 2: ArrayIndexOutOfBoundsException 與 try-catch-finally
        // ---------------------------------------------------------------------
        System.out.println("\n[場景 2] 迴圈邊界越界 (ArrayIndexOutOfBoundsException) 與 finally 保證");
        int[] sample = { 5, 2, 9, 1 };
        try {
            System.out.println(">> 呼叫具有邊界缺陷的 buggyBoundarySort...");
            buggyBoundarySort(sample);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(">> 成功捕捉陣列越界例外: 索引超出範圍 (" + e.getMessage() + ")");
        } finally {
            System.out.println(">> [finally 區塊] 無論是否越界，資源釋放與統計記錄保證執行！");
        }

        // ---------------------------------------------------------------------
        // 場景 3: Checked Exception (FileNotFoundException) 與多重 catch
        // ---------------------------------------------------------------------
        System.out.println("\n[場景 3] 讀取檔案排序之多重 catch (Multi-catch)");
        File nonExistentFile = new File("non_existent_data.txt");
        try {
            System.out.println(">> 嘗試載入不存在的檔案: " + nonExistentFile.getName());
            loadAndSort(nonExistentFile);
        } catch (FileNotFoundException e) {
            System.out.println(">> [專屬捕捉 1] 檔案未找到: " + e.getMessage());
        } catch (IOException e) {
            System.out.println(">> [專屬捕捉 2] 一般 IO 例外: " + e.getMessage());
        } catch (SortingException e) {
            System.out.println(">> [專屬捕捉 3] 排序後置條件異常: " + e.getMessage());
        }

        // ---------------------------------------------------------------------
        // 場景 4: 自訂受檢例外 (SortingException) 示範
        // ---------------------------------------------------------------------
        System.out.println("\n[場景 4] 自訂受檢例外 (SortingException) 拋出與欄位提取");
        try {
            int[] testData = { 10, 20, 15, 30 };
            // 模擬演算法缺陷未排序完成
            throw new SortingException("模擬演算法缺陷，部分數值未正確排序", testData, 1);
        } catch (SortingException e) {
            System.out.println(">> 捕捉到自訂例外訊息: " + e.getMessage());
            System.out.println(">> 錯誤發生索引位置: " + e.getFailedIndex());
        }

        // ---------------------------------------------------------------------
        // 場景 5: 致命錯誤 (StackOverflowError) 觀摩
        // ---------------------------------------------------------------------
        System.out.println("\n[場景 5] 致命錯誤觀摩 (StackOverflowError - 遞迴缺陷)");
        try {
            int[] testArray = { 5, 2, 9, 1 };
            System.out.println(">> 呼叫遞迴氣泡排序 (因忘記 n-1 造成無窮遞迴)...");
            recursiveBubbleSortWithBug(testArray, testArray.length);
        } catch (StackOverflowError err) {
            System.out.println(">> 捕捉到 JVM 致命錯誤 (Error): 堆疊空間溢位 (StackOverflowError)");
            System.out.println(">> 說明：Error 代表系統底層無法恢復之嚴重狀態，實務上不應使用 try-catch 掩蓋！");
        }

        System.out.println("\n==================================================");
        System.out.println(" 展示結束：BubbleSort 例外處理生命週期示範完畢");
        System.out.println("==================================================");
    }
}
```

### 執行輸出結果：
```text
==================================================
 軟體品質保證 (SQA) 實習示範：BubbleSort 例外處理展示
==================================================

[場景 1] 參數防禦 (IllegalArgumentException vs NullPointerException)
>> 傳入 null 呼叫 bubbleSort...
>> 成功捕捉主動防禦之例外: 待排序陣列不可為 null (前置條件防禦)

[場景 2] 迴圈邊界越界 (ArrayIndexOutOfBoundsException) 與 finally 保證
>> 呼叫具有邊界缺陷的 buggyBoundarySort...
>> 成功捕捉陣列越界例外: 索引超出範圍 (Index 4 out of bounds for length 4)
>> [finally 區塊] 無論是否越界，資源釋放與統計記錄保證執行！

[場景 3] 讀取檔案排序之多重 catch (Multi-catch)
>> 嘗試載入不存在的檔案: non_existent_data.txt
>> [專屬捕捉 1] 檔案未找到: 找不到指定的排序輸入檔案: .../non_existent_data.txt

[場景 4] 自訂受檢例外 (SortingException) 拋出與欄位提取
>> 捕捉到自訂例外訊息: 模擬演算法缺陷，部分數值未正確排序 (在索引 [1] 處違反遞增順序: 20 > 15, 現況: [10, 20, 15, 30])
>> 錯誤發生索引位置: 1

[場景 5] 致命錯誤觀摩 (StackOverflowError - 遞迴缺陷)
>> 呼叫遞迴氣泡排序 (因忘記 n-1 造成無窮遞迴)...
>> 捕捉到 JVM 致命錯誤 (Error): 堆疊空間溢位 (StackOverflowError)
>> 說明：Error 代表系統底層無法恢復之嚴重狀態，實務上不應使用 try-catch 掩蓋！

==================================================
 展示結束：BubbleSort 例外處理生命週期示範完畢
==================================================
```

---

## 6. 概念檢核 (Quiz)

### Quiz 01: 受檢例外與宣告原則
以下程式能否通過編譯？若不行，原因為何？
```java
import java.io.FileNotFoundException;

public class SortFileLoaderQuiz {
    public static void loadFile(String filename) {
        if (filename == null) {
            throw new FileNotFoundException("檔案不存在");
        }
    }

    public static void main(String[] args) {
        loadFile("data.txt");
    }
}
```
<details>
<summary>💡 點擊展開答案與解析</summary>

**答案：無法通過編譯。**
* **解析**：`FileNotFoundException` 繼承自 `IOException`，屬於 **受檢例外 (Checked Exception)**。根據 CDR 原則，方法若拋出受檢例外，必須在方法簽名宣告 `throws FileNotFoundException`，或在內部以 `try-catch` 包裹處理。
</details>

---

### Quiz 02: `try-catch-finally` 執行流程分析
說明以下氣泡排序測試程式碼執行時的螢幕印出結果與終止狀態：
```java
public class BubbleSortFinallyQuiz {
    public static void main(String[] args) {
        int[] data = { 5, 2, 8 };
        try {
            System.out.println("開始執行排序");
            int val = data[10]; // 💥 索引越界
            System.out.println("排序完成");
        } catch (NullPointerException e) {
            System.out.println("捕捉到：NullPointerException");
        } finally {
            System.out.println("Finally 區塊保證執行");
        }
        System.out.println("主程式順利結束");
    }
}
```
<details>
<summary>💡 點擊展開答案與解析</summary>

**輸出結果**：
```text
開始執行排序
Finally 區塊保證執行
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 10 out of bounds for length 3
```
* **解析**：
  1. `data[10]` 引發 `ArrayIndexOutOfBoundsException`。
  2. `catch` 區塊宣告的是 `NullPointerException`，型態不相符，無法被捕捉。
  3. 跳出前**必定先執行 `finally` 區塊**，印出 `"Finally 區塊保證執行"`。
  4. 隨後未捕獲之例外向外拋出至 JVM，程式異常終止，因此 `"主程式順利結束"` 絕不會被印出。
</details>

---

### Quiz 03: 多重 catch 的捕捉繼承順序
以下程式碼在編譯時期會發生什麼問題？該如何修正？
```java
public class MultiCatchSortQuiz {
    public static void main(String[] args) {
        try {
            int[] data = null;
            System.out.println(data.length);
        } catch (Exception e) {
            System.out.println("捕捉到通用 Exception: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("捕捉到 NullPointerException");
        }
    }
}
```
<details>
<summary>💡 點擊展開答案與解析</summary>

**答案：編譯錯誤 (Unreachable catch block for NullPointerException)**
* **解析**：`NullPointerException` 是 `Exception` 的子類別。若先宣告 `catch (Exception e)`，所有例外（包含 NPE）都會被第一個 catch 攔截，導致後方的 `catch (NullPointerException e)` 永遠不可能被執行到。
* **修正方式**：必須遵循**「先子類別、後父類別」**的原則，將 `NullPointerException` 放上方。
</details>

---

### Quiz 04: `finally` 區塊與回傳值 (Return Value) 行為
執行以下程式，終端機印出的回傳數值為何？
```java
public class FinallyReturnDemo {
    public static int getFirstElement() {
        int x = 10;
        try {
            System.out.println("Try 執行");
            return x;
        } finally {
            x = 99;
            System.out.println("Finally 執行，x 改為 " + x);
        }
    }

    public static void main(String[] args) {
        System.out.println("方法回傳值: " + getFirstElement());
    }
}
```
<details>
<summary>💡 點擊展開答案與解析</summary>

**答案**：
```text
Try 執行
Finally 執行，x 改為 99
方法回傳值: 10
```
* **解析**：基本資料型態（primitive type）在 `try` 區塊執行 `return x;` 時，會先將 `x` 當前的值 `10` 複製暫存到回傳保留區。隨後執行 `finally` 修改 `x = 99`，僅影響區域變數，不會覆寫已確定的暫存回傳值。
</details>

---

## 7. Lab & Practice (實習展示與動手練習)

本單元包含展示程式碼與實習練習題，皆位於 [`src/main/java/u02_robust/exception/`](../../src/main/java/u02_robust/exception/)：

### 示範 01: [BubbleSortExceptionDemo.java](../../src/main/java/u02_robust/exception/BubbleSortExceptionDemo.java)
* **核心展示**：以氣泡排序貫穿各類例外（Checked/Unchecked/Error）、CDR 原則、`try-with-resources` 與自訂受檢例外 `SortingException`。

### 示範 02: [ExceptionBasicsDemo.java](../../src/main/java/u02_robust/exception/ExceptionBasicsDemo.java)
* 涵蓋例外層次結構、CDR 原則與 AutoCloseable 連線管理示範。

### 示範 03: [TriangleExceptionDemo.java](../../src/main/java/u02_robust/exception/TriangleExceptionDemo.java)
* 自訂受檢例外 `TriangleException`：當邊長小於等於零或不符合三角形不等式定理時拋出。

---

## 8. Exercise (學生自主練習)

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
