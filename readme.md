# 軟體測試與軟體品質保證
### Software Testing & Software Quality Assurance (2026 AI Era Edition)

> 💡 **核心哲學**：在 AI 輔助編程普及的 2026 年，寫出一段程式碼只要問 AI 3 秒鐘；但要證明這段程式碼在生產環境不會造成數百萬美元的災難，必須仰賴系統化的品質保證工程。**軟體開發的瓶頸已從「撰寫代碼 (Writing)」全面轉向「驗證代碼 (Verification)」**。

---

## 📌 本課程目錄與章節全景 (Curriculum Modules)

本課程分為 9 大核心講義模組，完整涵蓋從缺陷本質、靜態檢視、黑白箱測試、隔離架構到微服務與混沌工程：

| 章節 | 模組主題 (Module Title) | 核心學習重點與前沿技術 (Key Highlights) | 講義資源 |
| :---: | :--- | :--- | :---: |
| **Ch01** | **軟體危機、品質模型與 AI 時代的可靠性工程** | 歷史四大慘劇（愛國者、火星軌道、名古屋空難、獅子王）➔ AI 時代新軟體危機（Slopsquatting 幻覺投毒、Vibe Coding 漏洞）➔ IEEE 軟體四要素 ➔ David Garvin 五大品質觀點 ➔ V&V 與品質成本 (CoQ 1:10:100 定律) ➔ DevOps 6 大連續品質門檻 ➔ ISO 25010 模型 | [📖 講義](Lecture/source/ch01t_intro.md) |
| **Ch02** | **錯與除錯 (Bugs, Faults, and Debugging)** | IEEE 610.12 臭蟲四階段因果鏈 (Mistake ➔ Fault ➔ Error ➔ Failure) ➔ Uncle Bob **Clean Code（命名自明、小函式、衛語句、Code Smells）** ➔ **Clean Code ≠ Bug-Free 迷思辨析** ➔ 科學除錯五步驟 ➔ 契約式設計 (DbC) ➔ 缺陷追蹤狀態機 (BTS) | [📖 講義](Lecture/source/ch02t_bug.md) |
| **Ch03** | **軟體測試原則、理論與架構模型** | Bertrand Meyer 契約三大要素（Pre/Post/Invariants）➔ ISTQB 7 大經典測試原則 ➔ 測試多維度分類 ➔ Martin Fowler 實戰測試金字塔 ➔ V 模型雙向追溯 ➔ 測試案例三要素 ➔ **Test Oracle（測試預言機）難題與突破** | [📖 講義](Lecture/source/ch03t_testing.md) |
| **Ch04** | **軟體檢視與靜態分析 (Static Inspection)** | 靜態檢視冰山哲學 ➔ IEEE 1028 / Fagan 檢視流程與角色 ➔ SRS 需求規格檢核 ➔ 架構設計審查與 AI 威脅建模 ➔ **團隊 Clean Code 查核清單** ➔ Code Smells 重構 ➔ OWASP Top 10 資安檢視 ➔ PMD / SonarQube | [📖 講義](Lecture/source/ch04t_inspection.md) |
| **Ch05** | **黑箱測試 (Black-Box Testing)** | JUnit 5 現代架構與斷言 ➔ **邊界值分析 (BVA: 4n+1, 6n+1)** ➔ **等價類分割 (EP: 弱/強涵蓋)** ➔ 全成對組合測試 (All-Pairs / Pairwise) ➔ 決策表測試 (Decision Table) ➔ 狀態轉換測試 ➔ **屬性基礎測試 (Property-Based Testing with jqwik)** | [📖 講義](Lecture/source/ch05t_blackbox.md) |
| **Ch06** | **白箱測試 (White-Box Testing)** | JaCoCo Bytecode 動態插樁原理 ➔ 邏輯涵蓋度階梯（敘述、分支、條件、多重條件、**DO-178C 航太級 MC/DC**）➔ McCabe 圈複雜度與基本路徑 ➔ **變異測試 (Mutation Testing with PITest)** ➔ AI 輔助未覆蓋路徑測資合成 | [📖 講義](Lecture/source/ch06t_whitebox.md) |
| **Ch07** | **整合測試 (Integration Testing)** | 測試層級與 Spring Boot 三層架構 ➔ 由下而上 (Drivers) / 由上而下 (Stubs) ➔ **Test Double 五大替身（Dummy, Stub, Spy, Mock, Fake）** ➔ Mockito 行為驗證 ➔ Spring Boot 整合測試 ➔ **Testcontainers 容器化資料庫測試** | [📖 講義](Lecture/source/ch07t_integration.md) |
| **Ch08** | **系統與驗收測試 (System Testing)** | 使用案例與端到端業務流 ➔ **行為驅動開發 (BDD with Cucumber & Gherkin)** ➔ 微服務 **Pact 消費者驅動契約測試** ➔ **Playwright 現代 E2E 自動化** ➔ Nielsen 10 大可用性 ➔ 高併發負載工程 (k6 壓測) ➔ 混沌工程與回復性 | [📖 講義](Lecture/source/ch08t_system.md) |
| **Ch09** | **測試文件與現代化實踐 (Doc-as-Code)** | 測試文件 4 大核心價值（對齊、追溯、重現、治理）➔ 精簡版 IEEE 829 三大支柱（計畫、規格、報告）➔ **Doc-as-Code 現代活文件** ➔ AI 輔助 PRD 需求矩陣自動萃取 ➔ 缺陷 RCA 分析 ➔ 品質檢核清單 | [📖 講義](Lecture/source/ch09t_doc.md) |

---

## 🎯 課程目標

1. **建立系統化的品質與防錯思維**：
    * 擺脫「想到哪測到哪」的直覺式除錯，建立黑箱測試（等價類、邊界值、決策表）與白箱測試（邏輯覆蓋、圈複雜度、MC/DC）的嚴謹工程思維。
    * 掌握將抽象的 **ISO 25010 軟體產品品質模型**，轉化為具體可執行的測試策略與自動化驗證指標。

2. **掌握現代全棧測試工程技術**：
    * **單元測試與防禦**：JUnit 5 現代架構、契約式設計 (DbC) 與斷言防線。
    * **進階測試前沿**：`jqwik` 屬性基礎測試（不變量驅動）、PITest 變異測試（測試你的測試套件）。
    * **整合與容器化**：Testcontainers（拉起真實 PostgreSQL/Redis 容器）、Pact 契約測試（微服務解耦）。
    * **端到端與高併發**：Playwright Web 自動化、k6 負載與壓力工程（Load as Code）。
    * **安全與混沌彈性**：Jazzer 模糊測試 (Fuzzing)、Resilience4j / Chaos Engineering（故障注入）。
    * **CI/CD 連續品質門檻**：SonarQube 靜態分析、GitHub Actions 自動化品質守門員。

3. **理解 AI 時代的軟體工程範式轉移**：
    * 辨析 AI 生成代碼（LLM / Copilot / Vibe Coding）的固有隱患（幻覺套件投毒、安全弱點、長時崩潰）。
    * 學習人機協同品質驗證 SOP，運用「防禦性架構」、「變異測試」與「AI 測試代理」管理生成代碼的不確定性。

4. **培養實戰開發與交叉測試能力**：
    * **專題成果發表與交叉測試**：各組於第 14 週交付專題系統供他組進行真實場景交叉測試；期末口頭發表時，雙向報告「自家系統防護」與「對他組系統之測試發現與品質改善建議」。

---

## 🗺️ ISO 25010 軟體品質模型與實作技術對照

> 每一種測試技術，都是在為品質模型的特定維度建立自動化守護防線：

| ISO 25010 品質特性 | 核心子特性 (Sub-characteristics) | 本課程對應之測試與工程技術 |
| :--- | :--- | :--- |
| **功能適合性** (Functional Suitability) | 完備性、正確性、適切性 | 等價類分割 (EP)、邊界值分析 (BVA)、JUnit 5、BDD (Cucumber) |
| **可靠性** (Reliability) | 成熟度、容錯度 (Fault Tolerance)、可回復性 | 斷言 (Assertions)、**屬性測試 (jqwik Property-Based Testing)**、混沌工程 (Chaos) |
| **可維護性** (Maintainability) | 模組化、可分析性、可修改性、可測試性 | 靜態程式碼分析 (SonarQube/SpotBugs/PMD)、**變異測試 (PITest)**、Clean Code 重構 |
| **安全性** (Security) | 機密性、完整性、抗抵賴性、真實性 | 靜態安全掃描 (SAST/SCA)、**模糊測試 (Fuzzing with Jazzer)**、OWASP Top 10 |
| **效能效率** (Performance Efficiency) | 時間行為 (P95/P99 延遲)、資源利用率、吞吐量 | **k6 / Apache JMeter 高併發壓測**、GC 監控與記憶體洩漏分析 |
| **相容性** (Compatibility) | 共存性、互通性 (Interoperability) | **微服務契約測試 (Pact)**、跨版本與跨環境相容性測試 |
| **可移植性** (Portability) | 適應性、易安裝性、易置換性 | **Testcontainers 容器化測試**、雲原生 Docker 多環境一致性 |
| **易用性** (Usability) | 易識別性、易學習性、易操作性、錯誤保護 | **Playwright E2E 驗收測試**、Nielsen 10 大啟發式原則、A/B 測試 |

---

## 🗓️ 18 週教學大綱規劃

每週課程安排：**3 小時**（**1 小時理論授課** + **2 小時電腦教室上機實習**；第 17~18 週為**彈性自主學習與影音單元**）：

| 週次 | 1 小時講授 (Lecture) | 2 小時實習 (Lab) | 教材與實習手冊連結 |
| :---: | :--- | :--- | :--- |
| **01** | **【開局震撼】**：軟體危機四大歷史慘劇、Garvin 五大品質觀點、CoQ 1:10:100 定律與 AI 時代新挑戰 | **AI 程式碼破壞實驗**：給定一個 AI 生成的交易系統，找出隱藏的並發、精度與崩潰漏洞 | 📖 [Ch01 導論](Lecture/source/ch01t_intro.md)<br>🛠️ [Lab 01 AI 破壞實驗](LabDemo/docs/u01_debug/ai_code_break.md) |
| **02** | **【錯與除錯】**：臭蟲因果鏈 (IEEE 610.12)、Clean Code 心法、除錯工具與科學除錯五步驟 | **除錯實務與科學假設檢驗**：條件斷點、例外斷點與日誌追蹤分析 | 📖 [Ch02 錯與除錯](Lecture/source/ch02t_bug.md)<br>🛠️ [Lab 02 除錯實務](LabDemo/docs/u01_debug/debug.md) |
| **03** | **【測試原則】**：契約式設計 (DbC: requires/ensures/maintains)、斷言機制與 ISTQB 7 大測試原則 | 使用 Java Assertions、Google Guava Preconditions 與結構化日誌建立自我防護 | 📖 [Ch03 測試原則](Lecture/source/ch03t_testing.md)<br>🛠️ [Lab 03 斷言防線](LabDemo/docs/u02_preventive/assertion.md) |
| **04** | **【靜態檢視】**：Fagan 檢視流程、團隊 Clean Code 清單、Code Smells 識別與 OWASP Top 10 安全分析 | 打造 CI 靜態品質門檻：SonarQube / SpotBugs / PMD 規則設定與自訂檢測規則 | 📖 [Ch04 檢視與靜態分析](Lecture/source/ch04t_inspection.md)<br>🛠️ [Lab 04 靜態分析](LabDemo/docs/u03_inspection/pmd.md) |
| **05** | **【黑箱設計】**：等價類分割 (EP)、邊界值分析 (BVA: 4n+1/6n+1)、全成對測試 (Pairwise) 與決策表 | JUnit 5 現代架構：動態測試 (`@TestFactory`)、參數化測試 (`@ParameterizedTest`) 與自訂 DisplayName | 📖 [Ch05 黑箱測試](Lecture/source/ch05t_blackbox.md)<br>🛠️ [Lab 05 JUnit 5](LabDemo/docs/u04_utest/junit.md) |
| **06** | **【前沿轉移】屬性基礎測試 (Property-Based Testing)**：告別手寫測資，用數學屬性（Invariants）讓電腦自動生成萬組測資 | **`jqwik` 實戰**：定義演算法不變量，體驗框架自動產生極端測資與縮小化 (Shrinking) | 📖 [Ch05 屬性測試](Lecture/source/ch05t_blackbox.md)<br>🛠️ [Lab 06 jqwik 屬性測試](LabDemo/docs/u04_utest/jqwik_property_based.md) |
| **07** | **【白箱分析】**：JaCoCo 插樁原理、涵蓋度階梯、McCabe 圈複雜度與 DO-178C 航空級 MC/DC 標準 | JaCoCo 高級分析：分支與指令覆蓋率解讀、為未覆蓋路徑精準補彈 | 📖 [Ch06 白箱測試](Lecture/source/ch06t_whitebox.md)<br>🛠️ [Lab 07 白箱測試](LabDemo/docs/u06_wbtesting/whitebox_test.md) |
| **08** | **【期中能力鑑定 (Midterm Exam)】**（涵蓋 ISO 25010、規格推導、MC/DC 分析、屬性不變量與黑白箱設計） | **【專題提案與品質架構審查 (Architecture & Spec Review)】**：各組發表專題規格、防護策略與品質指標 | - |
| **09** | **【質量驗證】變異測試 (Mutation Testing)**：測試測試套件的有效性；變異算子、殺死率與等價變異體難題 | **PIT (Pitest) 實戰**：注入故障變異體，計算 Mutation Score，揪出「高覆蓋率卻測不出 Bug」的假測試 | 📖 [Ch06 變異測試](Lecture/source/ch06t_whitebox.md)<br>🛠️ [Lab 08 變異測試](LabDemo/docs/u07_mutation/mutation_test.md) |
| **10** | **【隔離架構】Test Double 原則**：Dummy, Stub, Spy, Mock, Fake；過度 Mock 反模式與脆化測試 | **Mockito 進階**：深入 `ArgumentCaptor`、Spy、嚴格 Stubbing (`Strictness.STRICT_STUBS`) 與架構解耦 | 📖 [Ch07 整合測試](Lecture/source/ch07t_integration.md)<br>🛠️ [Lab 09 Mockito](LabDemo/docs/u08_integration/mokito.md) |
| **11** | **【真實整合】Testcontainers 容器化測試**：拒絕 H2 內存庫假綠燈，使用真實 Docker 容器測試資料庫與 API | **Testcontainers + Spring Boot Test**：一鍵拉起真實 PostgreSQL / Redis 容器進行毫秒級資料庫整合測試 | 📖 [Ch07 整合測試](Lecture/source/ch07t_integration.md)<br>🛠️ [Lab 10 Testcontainers](LabDemo/docs/u08_integration/testcontainers_spring.md) |
| **12** | **【微服務與 E2E】Pact 契約測試 & Playwright**：微服務 Consumer-Driven Contracts；Playwright 現代 Web 驗收 | **Pact 實戰 + Playwright 自動化**：定義 API 契約；撰寫抗網路波動、具錄影回放的 Web 驗收測試 | 📖 [Ch08 系統測試](Lecture/source/ch08t_system.md)<br>🛠️ [Lab 11 契約與 Playwright](LabDemo/docs/u09_cucumber_bdd/pact_and_playwright.md) |
| **13** | **【極限壓測】高併發、效能與 load 工程**：TPS、P95/P99 延遲、資源洩漏、排隊理論與壓測模型設計 | **k6 / JMeter 現代壓測**：用程式碼定義負載情境（Load as Code），進行突波 (Spike) 與耐力 (Soak) 測試 | 📖 [Ch08 效能測試](Lecture/source/ch08t_system.md)<br>🛠️ [Lab 12 k6 壓測](LabDemo/docs/u10_performance/k6_load_testing.md) |
| **14** | **【專題交付凍結 (Code Freeze)】**：各組完成系統開發與自測防線，**正式交付對應組別**進行交叉測試 | **混沌工程與模糊測試實作**：使用 Jazzer 進行 Fuzzing，並利用 Resilience4j 注入故障驗證自癒力 | 📖 [Ch08 混沌與安全測試](Lecture/source/ch08t_system.md)<br>🛠️ [Lab 13 模糊與混沌測試](LabDemo/docs/u10_chaos_fuzzing/chaos_and_fuzzing.md) |
| **15** | **【AI in SQA 前沿】Agentic Testing 與自律測試**：Prompt Engineering for QA、AI 自動修復測試套件 | 打造 GitHub Actions 自動化 CI/CD 全防線；**持續進行他組系統交叉測試**並撰寫《測試發現與品質改善報告》 | 📖 [AI in QA 專題](Lecture/source/UX_and_AI.md)<br>🛠️ [Lab 14 CI 品質防線](LabDemo/docs/u11_devops/github_actions_quality_gate.md) |
| **16** | **【期末能力筆試 (Final Exam)】**（涵蓋微服務測試架構、Test Double、契約防護、混沌工程、AI 驗證） | **【期末專題成果發表會】**：每位同學進行雙向口頭報告：(1) **自家系統架構與自測成果**；(2) **對他組系統之交叉測試報告** | 🏆 成果發表 |
| **17** | **【自主學習 / 影音單元】現代測試文件標準與活文件工程 (Doc-as-Code)**：精簡 IEEE 829 架構、雙向追溯 (RTM) 與 Markdown 活文件 | **影音自主演練**：探索現代化測試報表工具（如 Allure Report）或 Doc-as-Code 自動發布流程 | 📖 [Ch09 測試文件](Lecture/source/ch09t_doc.md)<br>🎞️ [影音單元自主學習] |
| **18** | **【自主學習 / 影音單元】品質成熟度模型 (CMMI / TMMi) 與品質治理前沿**：測試成熟度評鑑 (TMMi Level 1~5)、AI Code Review 與技術債度量 | **影音自主演練**：自由選擇開源專案進行組織級品質度量與成熟度評估探索 | 📖 [Ch10 CMMI](Lecture/source/ch10t_cmmi.md)<br>🎞️ [前沿專題影音] |

---

## 📊 學期考核與評分比重 (Assessment Weights)

* **平時實習作業與課堂 CCQ (Lab & Quizzes)**：**25%**（每週上機即時驗收，重視動手實作能力）
* **期中能力鑑定筆試 (Midterm Exam)**：**25%**（第 08 週，著重於 ISO 品質模型、黑白箱設計推導、MC/DC 與屬性不變量）
* **期末能力鑑定筆試 (Final Exam)**：**20%**（第 16 週，著重於微服務測試架構、Test Double、契約測試、混沌工程、AI 驗證）
* **期末專題成果發表與交叉測試 (Final Project & Cross-Testing)**：**30%**（含自家系統品質防線建置、交叉測試他人系統之缺陷與品質報告、期末雙向口頭成果發表）
