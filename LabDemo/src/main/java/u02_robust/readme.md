# Unit 02: 穩健與防禦性程式設計 (Robust & Defensive Programming)

本模組彙整軟體品質保證 (SQA) 關鍵的三大防禦手段：**斷言 (Assertion)**、**例外處理 (Exception Handling)** 與 **日誌記錄 (Logging)**。
每個主題皆遵循「**先展示 (Demo) $\rightarrow$ 後動手練習 (Practice)**」的教學原則。

---

## 📂 目錄架構與主題對應

```
src/main/java/u02_robust/
│
├── assertion/                                # 1. 斷言機制與不變量防護
│   ├── BubbleSort.java                       # [展示] 氣泡排序之迴圈不變量與後置條件斷言
│   ├── PeopleDemo.java                       # [展示] 類別不變量 (Class Invariant) 與 BMI 範圍斷言
│   ├── SinPractice.java                      # [練習] 泰勒展開式計算 sin(x) 之數值收斂與範圍斷言
│   └── TriangleAssertionPractice.java       # [練習] 三角形分類：區分公開 API 例外 vs. 內部斷言
│
├── exception/                                # 2. 例外處理與捕捉/宣告原則 (CDR)
│   ├── ExceptionBasicsDemo.java              # [展示] Checked vs Unchecked、try-catch-finally、try-with-resources
│   ├── TriangleExceptionDemo.java            # [展示] 自訂受檢例外 TriangleException 封裝錯誤細節
│   ├── SafeCalculatorPractice.java           # [練習] 處理 ArithmeticException 與 NumberFormatException
│   └── BankAccountPractice.java              # [練習] 銀行提款：自訂受檢例外 InsufficientFundsException
│
└── log/                                      # 3. 企業級日誌記錄與追蹤
    ├── LoggingJulDemo.java                   # [展示] Java 內建日誌 (JUL) 等級、Handlers 與寫入 app.log
    ├── LoggingLog4jDemo.java                 # [展示] Log4j 2 / SLF4J 參數化日誌、分級策略與例外追蹤
    └── OrderDeliveryPractice.java            # [練習] 外送平台訂單日誌生命週期實戰 (INFO / WARN / ERROR)
```

---

## 🎯 執行與示範指令

### 1. 斷言展示 (務必加上 `-ea` 參數)
```bash
# 氣泡排序斷言展示
java -ea -cp target/classes u02_robust.assertion.BubbleSort

# People 類別不變量展示
java -ea -cp target/classes u02_robust.assertion.PeopleDemo

# sin(x) 泰勒級數練習
java -ea -cp target/classes u02_robust.assertion.SinPractice

# 三角形斷言練習
java -ea -cp target/classes u02_robust.assertion.TriangleAssertionPractice
```

### 2. 例外處理展示
```bash
# 例外基礎與 try-with-resources 展示
java -cp target/classes u02_robust.exception.ExceptionBasicsDemo

# 自訂 TriangleException 展示
java -cp target/classes u02_robust.exception.TriangleExceptionDemo

# 安全計算機練習
java -cp target/classes u02_robust.exception.SafeCalculatorPractice

# 銀行帳戶提款練習
java -cp target/classes u02_robust.exception.BankAccountPractice
```

### 3. 日誌記錄展示
```bash
# Java 內建 JUL 日誌展示
java -cp target/classes u02_robust.log.LoggingJulDemo

# Log4j 2 企業級日誌展示
java -cp target/classes u02_robust.log.LoggingLog4jDemo

# 外送平台日誌實戰練習
java -cp target/classes u02_robust.log.OrderDeliveryPractice
```

---

## 🧪 單元測試執行
```bash
# 執行所有 u02_robust 相關單元測試
mvn test -Dtest="u02_robust.**.*Test"
```