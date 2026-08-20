# 軟體品質確保與測試 (Software Quality Assurance and Testing)

歡迎來到本課程的學習資源庫。本課程旨在幫助學生建立扎實的軟體品質管理與測試技術觀念，並透過每週實作演練，掌握業界常用的測試與自動化工具。

---

## 📚 課程章節簡介

本課程內容涵蓋從軟體品質理論、靜態分析、單元測試、黑白箱測試技術，到系統整合測試與品質管理的完整流程：

* **[Ch01 軟體品質與軟體危機導論](Lecture/ch01_intro.md)**：藉由愛國者飛彈、火星氣候軌道器、名古屋空難等經典歷史災難，探討軟體危機與軟體品質的基本觀念，並介紹 **ISO 9126 品質模型**。
* **[Ch02 錯與除錯](Lecture/ch02_bug.md)**：深入定義 Bug、Fault、Failure 的差異，學習如何使用斷言 (Assertion) 確保前置/後置條件，以及如何使用 Debugger 進行單步除錯與缺陷生命週期管理。
* **[Ch03 軟體測試原則](Lecture/ch03_testing.md)**：探討軟體測試的核心原則（例如：窮盡測試不可行、殺蟲劑悖論），並引介 V-Model 軟體開發測試生命週期。
* **[Ch04 軟體檢視與靜態分析](Lecture/ch04_inspection.md)**：學習 Fagan Inspection 的流程與角色定義，並介紹如何利用靜態程式碼分析工具（如 **PMD**）在不執行程式的情況下預防潛在缺陷。
* **[Ch05 黑箱測試技術](Lecture/ch05_blackbox.md)**：介紹基於需求規格的黑箱測試案例設計方法，包含**等價類劃分 (EP)**、**邊界值分析 (BVA)**、**決策表 (Decision Tables)** 與**狀態轉換測試**。
* **[Ch06 白箱測試技術](Lecture/ch06_whitebox.md)**：深入學習基於程式內部結構的白箱測試，包含控制流覆蓋（陳述句、分支、條件、路徑覆蓋）與基準路徑測試 (Basis Path Testing)。
* **[Ch07 整合測試與 Mocking](Lecture/ch07_integration.md)**：學習由下而上、由上而下及三明治整合策略，並掌握使用 **Mockito** 與 Stub 隔離外部依賴進行 Mock 測試的技巧。
* **[Ch08 系統測試](Lecture/ch08_system.md)**：介紹完整系統整合後的測試方法，包含功能性測試與各式非功能性測試（如：效能、壓力、易用性、安全性及復原測試）。
* **[Ch09 測試文件與度量](Lecture/ch09_doc.md)**：以 **IEEE 829** 標準為藍本，介紹測試計畫 (Test Plan) 與測試案例文件的撰寫，並認識測試覆蓋率、缺陷密度等品質度量指標。
* **[Ch10 軟體品質管理](Lecture/ch10_cmmi.md)**：介紹能力成熟度整合模式 (**CMMI**) 的五個成熟度等級，並簡述現代 DevOps 軟體生命週期中的持續整合與部署 (CI/CD)。

---

## 🗓️ 16 週課程與實習排程建議

本課程設計為每週 **1 小時講授 (Lecture)** 與 **2 小時實習演練 (Lab/Hands-on)**，引導學生「學中做、做中學」：

| 週次 | 授課主題 (1 小時) | 實習演練 (2 小時) | 參考教材與實習手冊連結 |
| :---: | :--- | :--- | :--- |
| **01** | 課程介紹 & 軟體品質與危機導論 | 開發環境配置與 Maven 專案設定 | 📖 [Ch01 導論](Lecture/ch01_intro.md)<br>🛠️ [POM 配置手冊](Lab/u01_debug/POM.md) |
| **02** | 錯與除錯觀念 (Bug, Fault, Failure) | IntelliJ IDEA 調試器與單步執行實務 | 📖 [Ch02 錯與除錯](Lecture/ch02_bug.md)<br>🛠️ [調試實務手冊](Lab/u01_debug/debug.md) \| [IDE 調試手冊](Lab/u01_debug/Intellij.md) |
| **03** | 軟體測試原則與 V-Model | 防禦性程式設計：使用斷言 (Assertion) | 📖 [Ch03 測試原則](Lecture/ch03_testing.md)<br>🛠️ [斷言實作手冊](Lab/u02_preventive/assertion.md) |
| **04** | 軟體檢視 (Fagan Inspection) | 防禦性程式設計：例外處理與 Log 紀錄 | 📖 [Ch04 軟體檢視](Lecture/ch04_inspection.md)<br>🛠️ [例外手冊](Lab/u02_preventive/exception.md) \| [Log 手冊](Lab/u02_preventive/logging.md) |
| **05** | 靜態程式碼分析原理與規則設定 | 使用 PMD 工具進行自動化程式碼檢視 | 📖 [Ch04 軟體檢視](Lecture/ch04_inspection.md)<br>🛠️ [PMD 靜態分析手冊](Lab/u03_inspection/pmd.md) |
| **06** | 黑箱測試技術：等價類與邊界值 | 設計等價類與邊界值測試案例 | 📖 [Ch05 黑箱測試](Lecture/ch05_blackbox.md) |
| **07** | 黑箱測試技術：決策表與狀態轉換 | JUnit 5 單元測試基礎與斷言撰寫 | 📖 [Ch05 黑箱測試](Lecture/ch05_blackbox.md)<br>🛠️ [JUnit 5 單元測試手冊](Lab/u04_utest/junit.md) |
| **08** | **期中週**：期中報告或學期專案規劃 | 學期專案環境建置與測試規劃 | - |
| **09** | 白箱測試技術：控制流與資料流覆蓋率 | 白箱測試覆蓋率度量 (使用 JaCoCo) | 📖 [Ch06 白箱測試](Lecture/ch06_whitebox.md)<br>🛠️ [覆蓋率度量手冊](Lab/u04_utest/metrics.md) |
| **10** | 白箱測試技術：基準路徑測試 | 基準路徑測試案例設計與代碼覆蓋演練 | 📖 [Ch06 白箱測試](Lecture/ch06_whitebox.md)<br>🛠️ [基準路徑實作](Lab/u05_mutation/whitebox_test.md) |
| **11** | 變異測試與測試套件品質評估 | 使用 PIT 進行自動化變異測試實作 | 🛠️ [變異測試實作手冊](Lab/u05_mutation/mutation_test.md) |
| **12** | 整合測試策略與 Mock 物件概念 | 使用 Mockito 框架進行依賴隔離測試 | 📖 [Ch07 整合測試](Lecture/ch07_integration.md)<br>🛠️ [Mockito 使用手冊](Lab/u06_integration/mokito.md) |
| **13** | 整合測試與依賴注入框架 | Spring Boot 整合測試與 API 測試實務 | 📖 [Ch07 整合測試](Lecture/ch07_integration.md)<br>🛠️ [Spring 整合測試手冊](Lab/u06_integration/Spring.md) |
| **14** | 系統測試類型與測試文件 (IEEE 829) | 使用 Selenium 進行自動化 Web UI 測試 | 📖 [Ch08 系統測試](Lecture/ch08_system.md) \| [Ch09 文件](Lecture/ch09_doc.md)<br>🛠️ [Selenium 實作手冊](Lab/u09_web_testing/bmi_selenium.md) |
| **15** | 行為驅動開發 (BDD) 原理與應用 | 使用 Cucumber 撰寫 BDD 測試案例 | 🛠️ [BDD 導論](Lab/u09_web_testing/intro_BDD.md) \| [Cucumber 實作](Lab/u09_web_testing/bmi_cucumber.md) |
| **16** | 軟體流程改善 (CMMI) & DevOps CI/CD | 整合 GitHub Actions 進行自動化 CI 測試 | 📖 [Ch10 品質管理](Lecture/ch10_cmmi.md)<br>🛠️ [Git 與自動化測試建置](Lab/u10_devops/using_git.md) |

---

## 🚀 週 17–18：經典文獻與線上教材自習 (Self-Study Resources)

在完成 16 週的實體課程與實習演練後，推薦學生於第 17、18 週自主研讀以下精選的經典軟體工程文章、Google 開發手冊與 AI 生產力研究報告，深入理解軟體測試與 AI 輔助開發的實務脈絡：

### 1. 軟體測試策略與架構經典
* **[The Practical Test Pyramid - Martin Fowler](https://martinfowler.com/articles/practical-test-pyramid.html)**
  * **研讀重點**：軟體測試領域的必讀經典。深入探討如何合理配置單元測試 (Unit Tests)、整合測試 (Integration Tests) 與端到端測試 (End-to-End Tests) 的比例，建立高回饋效率且好維護的自動化測試套件。
* **[Software Engineering at Google: Testing Overview - Google SWE Book](https://abseil.io/resources/swe-book/html/ch11.html)**
  * **研讀重點**：Google 官方釋出的軟體工程專書測試章節。詳細剖析 Google 超大規模系統下的測試哲學，包含測試的「大小 (Size)」與「範圍 (Scope)」分類，以及預防測試碎裂的關鍵原則。

### 2. AI 輔助開發與軟體工程生產力研究
* **[Research: How GitHub Copilot helps developers work faster - GitHub Blog](https://github.blog/2022-09-07-research-how-github-copilot-helps-developers-work-faster/)**
  * **研讀重點**：GitHub 官方進行的里程碑式實證研究報告。量化呈現 AI 輔助程式開發如何使工作速度提升 55%、維持開發專注力（進入 Flow State），並將心力轉注於系統架構與設計等高價值工作。
* **[Develop Unit Tests using GitHub Copilot Tools - Microsoft Learn](https://aka.ms/AZ-2007)** (微軟官方實做單元)
  * **研讀重點**：微軟官方的互動式網頁教學文件。引導如何使用 GitHub Copilot 與 Copilot Chat 自動生成單元測試、例外測試，並有效率地處理各種測試邊界條件。


