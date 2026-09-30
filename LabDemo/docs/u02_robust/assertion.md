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
java -ea -cp target/classes u02_robust.assertion.BubbleSort
```

### 4.2 VS Code / Antigravity IDE 設定
VS Code 與 Antigravity IDE 執行 Java 程式碼時，可透過以下兩種方式啟用 `-ea`：

* **方式 A：設定 `.vscode/launch.json`（推薦）**
  1. 切換至側邊欄 **執行與偵錯 (Run and Debug)** $\rightarrow$ 點擊 **建立 launch.json 檔案**（或開啟既有的 `.vscode/launch.json`）。
  2. 在對應的 Java 配置區塊中加入 `"vmArgs": "-ea"`：
     ```json
     {
       "type": "java",
       "name": "Launch BubbleSort",
       "request": "launch",
       "mainClass": "u02_robust.assertion.BubbleSort",
       "vmArgs": "-ea"
     }
     ```
* **方式 B：工作區全域設定 (`.vscode/settings.json`)**
  若希望點擊 Java 程式碼上方的「Run | Debug」CodeLens 捷徑時一律預設啟用斷言，可在 `.vscode/settings.json` 加入：
  ```json
  {
    "java.debug.settings.vmArgs": "-ea"
  }
  ```

### 4.3 IntelliJ IDEA 設定
1. 點擊頂部選單 **Run $\rightarrow$ Edit Configurations...**
2. 選擇你的 Application 執行設定。
3. 點擊 **Modify options $\rightarrow$ Add VM options**。
4. 在 VM options 欄位中輸入 **`-ea`** 並儲存。

### 4.4 Maven 測試設定 (`pom.xml`)
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

## 5. 實習動手演練 (Lab & Practice)

本單元包含展示程式碼與實習練習題，皆位於 [`src/main/java/u02_robust/assertion/`](../../src/main/java/u02_robust/assertion/)：

### 示範 01: [BubbleSort.java](../../src/main/java/u02_robust/assertion/BubbleSort.java)
* **核心觀念**：
  - 公開 API 傳入 `null` 時拋出 `IllegalArgumentException`，嚴禁使用 `assert`。
  - 迴圈不變量：`assert isSuffixSorted(data, length - pass);` 確保每輪末端元素就定位。
  - 後置條件：`assert isSorted(data);` 確保陣列長度不變且完全遞增。
  - 內建 `buggyBubbleSort` 示範 off-by-one 演算法缺陷如何被斷言即時捕捉。

### 示範 02: [PeopleDemo.java](../../src/main/java/u02_robust/assertion/PeopleDemo.java)
* **核心觀念**：
  - 類別不變量 (Class Invariant)：身高、體重 > 0，出生年介於 1900 與今年之間。
  - 內部狀態計算斷言：BMI 範圍必須落在人體生理極限 [5.0, 150.0] 區間。

---

## 學生自主練習 (Exercises)

> 💡 **自主學習流程**：
> 1. 打開練習程式碼，依據 `TODO` 註解動手實作。
> 2. 執行對應的單元測試驗證是否全部通過。
> 3. 若卡關或想確認最佳寫法，再點開下方的摺疊區塊參考解答。

---

### 練習 01: [SinPractice.java](../../src/main/java/u02_robust/assertion/SinPractice.java)
* **題目**：泰勒展開式計算 $\sin(x)$ 的數值不變量防護。
* **練習任務**：
  1. `TODO 1`：前置狀態斷言，驗證正規化後的角度絕對值不超過 $2\pi$。
  2. `TODO 2`：迴圈不變量斷言，驗證級數展開項的絕對值逐漸遞減收斂。
  3. `TODO 3`：後置條件斷言，驗證數學定理 $\sin(x) \in [-1.0, 1.0]$。
* **單元測試指令**：
  ```bash
  mvn test -Dtest=SinPracticeTest
  ```

<details>
<summary>💡 點擊展開：練習 01 參考解答與解析</summary>

```java
// TODO 1: 前置狀態斷言
assert Math.abs(normalizedX) <= 2 * Math.PI + 1e-9 : "正規化角度超出範圍: " + normalizedX;

// TODO 2: 迴圈不變量斷言 (收斂性檢查)
assert Math.abs(term) <= Math.abs(prevTerm) || n <= 3 :
        String.format("級數未正常收斂！第 %d 項前一項 = %e, 目前項 = %e", n, prevTerm, term);

// TODO 3: 後置條件斷言 (結果範圍)
assert sum >= -1.0000001 && sum <= 1.0000001 :
        "後置條件失敗！sin(x) 計算結果超出 [-1.0, 1.0] 邊界: " + sum;
```

**解析說明**：
- 浮點數比對邊界時，應預留微小的容許誤差（如 `1e-9` 或 `1.0000001`），避免 IEEE 754 精度誤差造成斷言誤報。
- 輔助函式與斷言運算式內不可產生任何狀態修改（Pure Expression）。
</details>

---

### 練習 02: [TriangleAssertionPractice.java](../../src/main/java/u02_robust/assertion/TriangleAssertionPractice.java)
* **題目**：三角形分類的邊界防禦與幾何不變量。
* **練習任務**：
  1. `TODO 1`：正三角形不變量斷言（$a == b \land b == c$，且滿足幾何不等式）。
  2. `TODO 2`：等腰三角形不變量斷言（至少兩邊相等，且滿足幾何不等式）。
  3. `TODO 3`：非三角形不變量斷言（必定不滿足幾何不等式）。
* **單元測試指令**：
  ```bash
  mvn test -Dtest=TriangleAssertionPracticeTest
  ```

<details>
<summary>💡 點擊展開：練習 02 參考解答與解析</summary>

```java
if (type == TriangleType.EQUILATERAL) {
    // TODO 1: 正三角形不變量斷言
    assert (a == b && b == c) : "分類為正三角形，但三邊不相等: " + a + ", " + b + ", " + c;
    assert isValid : "正三角形必須滿足幾何不等式";

} else if (type == TriangleType.ISOSCELES) {
    // TODO 2: 等腰三角形不變量斷言
    assert (a == b || b == c || a == c) : "分類為等腰三角形，但不存在任兩邊相等";
    assert isValid : "等腰三角形必須滿足幾何不等式";

} else if (type == TriangleType.NOT_TRIANGLE) {
    // TODO 3: 非三角形不變量斷言
    assert !isValid : "分類為非三角形，但幾何不等式卻成立！";
}
```

**解析說明**：
- 外部傳入邊長小於等於 0 時，由公開 API 前端丟出 `IllegalArgumentException`，這屬於「輸入防禦」；
- 分類結束前用 `assert` 檢查三邊關係與不等式定理，這屬於「內部邏輯正確性不變量」，兩者職責分明。
</details>


