# 實習 01：防禦性程式設計與斷言 (Defensive Programming & Assertions)

> 就像排骨牌一樣，我們會設立許多安全防線與斷點，阻止錯誤擴散並破壞系統狀態。

---

## 1. 斷言 (Java Assertions) 的核心概念

斷言是 Java 語言內建用來檢驗**「程式設計師的內部假設與狀態不變量」**的強大工具。

```java
// 基本語法 1：條件若為 false，拋出 java.lang.AssertionError
assert grade <= 100;

// 基本語法 2：附帶自訂錯誤訊息字串
assert grade <= 100 : "成績計算異常：超過滿分 100，實際值 = " + grade;
```

---

## 2. 斷言的最佳使用時機

### 2.1 內部狀態不變量 (Internal Invariants)
```java
if (i % 3 == 0) {
    handleZero();
} else if (i % 3 == 1) {
    handleOne();
} else {
    // 邏輯上如果 i 是正數，這裡只可能是 2；但如果 i 是負數，結果可能是 -1 或 -2
    assert i % 3 == 2 : "非預期的餘數狀態: " + (i % 3);
    handleTwo();
}
```

### 2.2 類別不變量 (Class Invariants)
類別不變量是物件在任何公開方法執行前後**必須恆為真**的黃金法則：

```java
public class BoundedStack {
    private int[] elements;
    private int size = 0;
    private final int capacity;

    private boolean invariant() {
        return size >= 0 && size <= capacity && elements != null;
    }

    public void push(int val) {
        if (size >= capacity) throw new IllegalStateException("Stack Full");
        elements[size++] = val;
        assert invariant() : "Push 後違反 Stack 類別不變量！";
    }
}
```

### 2.3 控制流程不變量 (Control-Flow Invariants)
```java
void processStatus(Status status) {
    switch (status) {
        case PENDING: ... return;
        case SUCCESS: ... return;
        case FAILED:  ... return;
    }
    // 理論上所有列舉值都已涵蓋，絕對不可能執行到這裡
    assert false : "未知的 Status 狀態: " + status;
}
```

---

## 3. 何時「絕不該」使用斷言？

| ❌ 錯誤用法 | 為什麼不行？ | ✅ 正確做法 |
| :--- | :--- | :--- |
| 用 `assert` 檢查**公開方法 (Public API) 的參數** | 生產環境可能關閉斷言 (`-da`)，導致非法參數直接穿透攻擊系統 | 使用 `IllegalArgumentException` 或 Google Guava `Preconditions.checkArgument` |
| 在 `assert` 內部執行**具副作用的商業邏輯** | 關閉斷言後該邏輯將完全不被執行（例如 `assert list.remove(item);`） | 先執行運算取回結果，再對結果進行斷言 |

---

## 4. 如何在 IDE 與 Maven 中啟用斷言 (`-ea`)

Java 預設在執行時期是**關閉斷言**的。要啟用斷言：

### 4.1 CLI 命令列執行
```bash
# -ea 代表 enableassertions
java -ea -cp target/classes xdemo.BubbleSort
```

### 4.2 IntelliJ IDEA 設定
1. 點擊頂部選單 **Run $\rightarrow$ Edit Configurations...**
2. 選擇你的 Application 執行設定。
3. 點擊 **Modify options $\rightarrow$ Add VM options**。
4. 在 VM options 欄位中輸入 **`-ea`** 並儲存。

### 4.3 Maven 測試設定 (`pom.xml`)
在 Maven 的 `maven-surefire-plugin` 中啟用斷言：
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.2.5</version>
    <configuration>
        <enableAssertions>true</enableAssertions>
    </configuration>
</plugin>
```

---

## 5. 實習動手演練 (Lab Exercises)

請開啟本模組內的範例專案 [`DemoPreventive`](DemoPreventive/) 進行除錯與防禦防線強化：

* **Lab 01: [BubbleSort.java](DemoPreventive/src/main/java/xdemo/BubbleSort.java)**
  * 為氣泡排序加入後置條件斷言：確認陣列長度不變且元素嚴格遞增 `assert isSorted(arr);`。
* **Lab 02: [Sin.java](DemoPreventive/src/main/java/xdemo/Sin.java)**
  * 泰勒展開式計算 $\sin(x)$：加入數值範圍斷言 `assert result >= -1.0 && result <= 1.0;`。
* **Lab 03: [People.java](DemoPreventive/src/main/java/xdemo/People.java)**
  * 為人物年齡、身高與 BMI 計算加入不變量防護。
* **Lab 04: [Triangle.java](DemoPreventive/src/main/java/xdemo/Triangle.java)**
  * 區分公開 API 參數驗證（丟出例外）與內部判斷邏輯（斷言）。
* **Lab 05: [MaxHeap.java](DemoPreventive/src/main/java/xdemo/MaxHeap.java)**
  * 為二元最大堆積樹加入 `assert isMaxHeap();` 類別不變量檢查。
