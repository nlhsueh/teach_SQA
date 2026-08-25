# White Box Testing Lab (白箱測試與覆蓋度分析實作手冊)

本實驗手冊引導學生進行**白箱測試（結構化測試）**與**覆蓋度分析（Code Coverage）**的實作。內容包含傳統白箱測試的工具使用、基本覆蓋度分析、複雜度指標度量，以及在 2026 AI 時代如何人機協同進行高效率的白箱測試「補彈（覆蓋度提升）」。

---

# PART 1: 傳統白箱測試 (Traditional White Box Testing)

## 📖 1.1 教學與核心觀念 (Tutorial)

### 1.1.1 什麼是白箱測試？
白箱測試（White Box Testing）又稱結構測試或邏輯驅動測試。測試人員在完全了解程式內部邏輯、控制流與程式碼結構的前提下，設計測試案例來驗證程式碼的執行路徑是否符合預期。其核心指標是**程式碼覆蓋率 (Code Coverage)**，用以量化「程式碼被測試了多少」。

### 1.1.2 常見的覆蓋度指標
1.  **敘述涵蓋度 (Statement Coverage, SC)**：程式中每一條可執行指令敘述至少被執行一次。最弱的白箱指標。
2.  **分支涵蓋度 (Branch Coverage, BC)**：又稱決策涵蓋度 (Decision Coverage)。確保程式中每個判斷（如 `if` 語句）的 True 和 False 分支都至少被執行一次。
3.  **條件涵蓋度 (Condition Coverage, CC)**：使程式中每個判斷內部的每一個個別布林條件（如 `A` 和 `B`）的 True 和 False 都至少執行過一次。
4.  **修改條件/決策涵蓋度 (MC/DC)**：航空級軟體（如 DO-178B）的覆蓋率標準。要求每個個別條件都能獨立影響整個決策的輸出結果，所需測資數呈線性關係 ($n+1$ 到 $2n$)。

### 1.1.3 實務工具 (IntelliJ vs. JaCoCo) 的分支定義差異
在學術上，`if (A && B)` 是一個單一的分支決策點。但在實務工具中：
*   **JaCoCo** 是在 **Bytecode（位元組碼）** 層級進行分析的。
*   Java 編譯器為了實現**短路求值 (Short-circuit evaluation)**，會將 `&&` 與 `||` 拆解為多個跳轉指令。
*   因此，在 JaCoCo 報告中，一個 `if (A && B)` 會被計為 **4 個位元組碼分支**（`A` 的 T/F，與 `B` 的 T/F）。
*   若你的測試案例只涵蓋了 `A=False`（短路）與 `A=True, B=True`，JaCoCo 會回報僅有 75% 的分支覆蓋度（4 個分支中只覆蓋了 3 個），並在 IDE 中顯示**黃色鑽石 (Yellow Diamond)**，提示有未覆蓋的分支。

---

## 💻 1.2 範例展示與操作 (Demo)

### 1.2.1 課堂基本案例分析
針對以下課堂程式片段：
```java
// 輸入 A, B, X
if (A > 1 && B == 0) {
    Y = A;
}
if (A == 2 || X > 1) {
    Y = X;
}
// 輸出 Y
```

#### 測試案例設計分析表：
*   **敘述涵蓋度 (SC 100%)**：只需設計一組測資 `(A=2, B=0, X=3)`，此時兩個 `if` 條件皆成立，所有敘述皆會執行。
*   **缺陷**：若工程師將 `A > 1 && B == 0` 誤寫為 `A > 1 || B == 0`，上述測資 `(2,0,3)` 執行結果依然正確，無法找出 Bug。這證明了 **SC 100% 依然非常薄弱**。

### 1.2.2 IntelliJ Coverage 工具操作步驟
1.  開啟 `Lab/DemoSQA` 專案。
2.  在測試檔案上按右鍵，選擇 **Run 'xxxTest' with Coverage**。
3.  在 IDE 右側的 Coverage 視窗中，可檢視 Class、Method、Line、Branch 的覆蓋率。
4.  在程式碼編輯器左側的側邊欄中：
    *   **綠色**：表示該行已被完全執行。
    *   **紅色**：表示該行未被執行。
    *   **黃色（黃色鑽石）**：表示分支未被完全執行（部分覆蓋）。

---

## 🧪 1.3 實作練習 (Lab)

### 📋 Lab 1: Triangle 三角形測試與覆蓋度分析
1.  開啟待測檔案 [Triangle.java](../DemoSQA/src/main/java/u04_utest/Triangle.java)。
2.  在 `src/test/java/u04_utest/` 目錄下建立 `TriangleTest.java` 測試類別。
3.  使用 IntelliJ 的 **Run with Coverage** 檢驗測試涵蓋度：
    *   說明 Class、Method、Line、Branch 涵蓋率的實質意義。
    *   實務工具（IntelliJ/JaCoCo）中顯示的 Branch 覆蓋率，和講義中學術定義的「條件涵蓋度」與「分支涵蓋度」有何差異？
4.  補充測試案例，直到達到 **Branch Coverage 100%**。
5.  若發現某些分支無論如何都無法達到 100% 覆蓋，請分析其原因（是否為等價邏輯或死碼），並嘗試改善。

### 📋 Lab 2: Binary Search 二元搜尋
1.  開啟待測檔案 [BinarySearch.java](../DemoSQA/src/main/java/u04_utest/BinarySearch.java)。
2.  在 `src/test/java/u04_utest/` 目錄下建立 `BinarySearchTest.java`。
3.  設計測試案例，並使用 Coverage 工具檢驗。逐步添加測資直到 **Line 與 Branch 覆蓋率達到 100%**。

### 📋 Lab 3: Loan Calculator 貸款利率計算
考慮以下貸款利率計算規則：
> 基礎利率為 5%。
> *   貸款年限超過 10 年折扣 0.5%，超過 20 年折扣 1%。
> *   若貸款金額超過 500 萬，每多 100 萬減少 0.1%。
> *   若申請者為青年則減少 1%。
> *   若已婚則再減少 0.5%。
> *   利率最低為 2%。

1.  開啟待測檔案 [LoanCalculator.java](../DemoSQA/src/main/java/u04_utest/LoanCalculator.java)。
2.  分別設計三個測試案例集：
    *   **集合 A**：滿足 Weak Coverage (SC100)。
    *   **集合 B**：滿足 All-Pair Coverage（全成對測試）。
    *   **集合 C**：滿足 Strong Coverage (BC100)。
3.  執行並記錄這三個測試案例集在 IntelliJ/JaCoCo 中所得到的實際 Branch Coverage，並分析其效益差異。

---

# PART 2: AI 輔助白箱測試 (AI-Assisted White Box Testing)

## 📖 2.1 教學與核心觀念 (AI-Assisted Tutorial)

在大型系統中，為了提升幾趴的覆蓋率，工程師往往要花費數小時去追蹤複雜的控制流與輸入參數關係。AI 在此處能扮演強大的「補彈助手」：
1.  **控制流反推輸入**：當 JaCoCo 指出某個深層的分支（例如紅色或黃色區塊）未被覆蓋時，可將該方法程式碼交給 AI，並詢問：「要執行到第 X 行的 `else` 分支，輸入參數應滿足什麼條件？」
2.  **變異測試與等價變異體識別**：當 PIT 變異測試框架回報有 Mutant 存活時，AI 能協助比對變異後程式碼與原程式碼的語意，判斷其是否為「等價變異體 (Equivalent Mutant)」。

> [!IMPORTANT]
> **安全防線（Oracle 審查）**：
> AI 生成測試案例時，只會根據目前的程式碼「合理化現有行為」。如果原始程式碼有錯誤邏輯，AI 寫出來的 Assert 預期值也會是錯的。工程師必須手動校對斷言的正確性。

---

## 💻 2.2 AI 輔助範例展示 (AI-Assisted Demo)

### 2.2.1 案例背景
我們有一段成績計算程式碼，覆蓋率工具回報第 8 行的 `else` 分支未執行：
```java
1: public double computeAverage(int[] grade) {
2:     int sum = 0, valid = 0, index = 0;
3:     while (index < grade.length) {
4:         if (grade[index] >= 0 && grade[index] <= 100) {
5:             sum += grade[index];
6:             valid++;
7:         } else {
8:             System.out.println("成績範圍錯誤"); // 未覆蓋！
9:         }
10:        index++;
11:    }
12:    return valid > 0 ? (double) sum / valid : -1;
13: }
```

### 2.2.2 AI 補彈 Prompt 範本
我們向 AI 送出以下 Prompt：
> **Prompt**：
> 「你是單元測試與白箱測試專家。以下是我的待測 Java 程式碼，目前測試覆蓋度工具回報**第 8 行的 else 分支**未執行。請幫我分析控制流，並寫出一個 JUnit 5 測試方法，提供精確的輸入參數以觸發第 8 行的分支。
> 
> [貼上 computeAverage 程式碼]」

### 2.2.3 AI 回傳的單元測試
AI 成功分析出只要 `grade` 陣列中包含小於 0 或大於 100 的數值即可觸發：
```java
@Test
@DisplayName("測試非 0-100 成績以觸發異常分支")
void testInvalidGradeTriggersElseBranch() {
    double result = computeAverage(new int[]{90, -5, 80}); // 包含負數
    assertEquals(85.0, result); // 90+80 / 2 = 85.0
}
```

---

## 🧪 2.3 AI 輔助實作練習 (AI-Assisted Lab)

### 📋 Lab 4: AI 輔助覆蓋度補彈實戰
1.  執行 [LoanCalculator.java](../DemoSQA/src/main/java/u04_utest/LoanCalculator.java) 的測試套件。
2.  若發現分支覆蓋率未達 100%（例如利率下限 2% 的防禦分支未執行，或特定複合條件短路分支未覆蓋）：
    *   **不要手動推導輸入**。請撰寫一個結構化的 **AI Prompt**，將程式碼與未覆蓋的分支資訊提供給 AI，請它產出能觸發該分支的測試案例。
    *   **審查與驗證**：將 AI 產出的測試案例加入測試套件，確認覆蓋率是否成功提升至 100%。人工檢查 AI 生成的斷言是否符合貸款規格書（而非盲目合理化錯誤）。
    *   **繳交要求**：提交你所撰寫的 **AI Prompt**、**AI 生成的原始測試碼**，以及**你審查後修正的最終測試碼**。

---

# 🛠️ 附錄：JaCoCo Maven 配置與生命週期指引

## ⚙️ A. POM.xml 設定 (`jacoco-maven-plugin`)
若要自動化產生覆蓋度 HTML 報告，請在專案的 `pom.xml` 中加入以下插件配置：

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.12</version>
    <executions>
        <execution>
            <id>prepare-agent</id>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>verify</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### 執行步驟：
1.  在專案根目錄下，開啟終端機執行：
    ```bash
    mvn clean test verify
    ```
2.  建置成功後，開啟 `target/site/jacoco/index.html` 即可檢視精美的覆蓋度報告。

---

## 🔄 B. Maven 核心生命週期說明

*   **clean**：清理先前建置產生的所有檔案（如整個 `target` 目錄）。
*   **compile**：將專案的 `.java` 原始碼編譯成 `.class` 位元組碼。
*   **test**：使用 JUnit 測試框架執行所有單元測試。
*   **package**：將編譯後的 class 檔案打包成發布格式（如 `.jar`）。
*   **verify**：執行整合測試與品質檢查。由於我們的 JaCoCo `report` 綁定在 `verify` 階段，因此在此階段會產出覆蓋度報告。
*   **install**：將打包好的 JAR 檔安裝至本地 Maven 本地倉庫中，供其他專案引用。