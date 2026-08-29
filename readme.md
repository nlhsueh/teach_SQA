# 軟體測試與軟體品質保證
### Software Testing & Software Quality Assurance (2026 AI Era Edition)
 
## 課程目標：

1. **建立系統化的測試思維**：
    * 擺脫「想到哪測到哪」的直覺式除錯，建立黑箱測試（等價類、邊界值）與白箱測試（邏輯覆蓋、圈複雜度）的系統化思維。
    * 掌握將抽象的 ISO 25010 品質要求，轉化為具體可執行的測試策略與驗證指標。

2. **掌握現代測試工程的技術**：
    * **單元測試**：JUnit 5 現代架構與參數化/動態測試。
    * **進階測試**：`jqwik` 屬性測試（告別手寫測資）、PITest 變異測試（殺死弱測試）。
    * **整合測試**：Testcontainers 容器化測試、Pact 契約測試（微服務對接）。
    * **端到端與效能**：Playwright E2E 測試、k6 負載測試。
    * **安全與可靠**：模糊測試 (Fuzzing)、混沌工程 (Chaos Engineering)。
    * **CI/CD 集成**：SonarQube 靜態分析、GitHub Actions 自動化流水線。

3. **理解 AI 時代的軟體工程範式轉移**：
    * 認識 AI 生成代碼（LLM/Copilot）的固有風險（幻覺、長時崩潰、精度誤差）。
    * 學習如何使用「防禦性架構（Design by Contract）」與「契約測試」來管理 LLM 生成代碼的不可信風險。

4. **培養實戰開發與交叉測試能力**：
    * **專題成果發表與交叉測試**：各組於第 14 週完成專題系統並交付他組進行真實情境交叉測試；期末報告時，每位同學不僅展示自家系統與測試成效，亦報告對他組系統的測試發現與品質回饋。
    * **AI 測試代理**：探索 AI 測試代理在生成測試案例、自動化測試、風險識別方面的應用與局限。

---

## 🗺️ ISO 25010 軟體品質模型與實作技術對照

> 每一種測試都在度量並守護品質模型的具體維度

| ISO 25010 品質特性 | 核心子特性 (Sub-characteristics) | 對應之測試與工程技術 |
| :--- | :--- | :--- |
| **功能適合性** (Functional Suitability) | 完備性、正確性、適切性 | 等價類分割 (EP)、邊界值分析 (BVA)、JUnit 5、BDD (Cucumber) |
| **可靠性** (Reliability) | 成熟度、容錯度 (Fault Tolerance)、可回復性 | 斷言 (Assertions)、**屬性測試 (jqwik Property-Based Testing)**、混沌工程 (Chaos) |
| **可維護性** (Maintainability) | 模組化、可分析性、可修改性、可測試性 | 靜態程式碼分析 (SonarQube/SpotBugs)、**變異測試 (PITest)**、依賴解耦 |
| **安全性** (Security) | 機密性、完整性、抗抵賴性、真實性 | 靜態安全掃描 (AST/SAST)、**模糊測試 (Fuzzing with Jazzer)** |
| **效能效率** (Performance Efficiency) | 時間行為 (延遲/回應時間)、資源利用率 | **k6 / Apache JMeter 高併發壓測**、GC 監控與記憶體洩漏分析 |
| **相容性** (Compatibility) | 共存性、互通性 (Interoperability) | **微服務契約測試 (Pact)**、跨版本相容性測試 |
| **可移植性** (Portability) | 適應性、易安裝性、易置換性 | **Testcontainers 容器化測試**、雲原生多環境測試 |
| **易用性** (Usability) | 易識別性、易學習性、易操作性、錯誤保護 | **Playwright E2E 驗收測試**、使用者流程自動化驗證 |

---

## 🗓️ 18 週教學大綱規劃

每週課程安排（大致）：**3 小時**（**1 小時一般教室理論授課** + **2 小時電腦教室實習演練**；第 17~18 週為**彈性自主學習與觀看影音單元**）：

| 週次 | 1 小時講授 (Lecture)<img width="40%" height="0"> | 2 小時實習 (Lab)<img width="40%" height="0"> | 教材與實習手冊連結<img width="15%" height="0"> |
| :---: | :--- | :--- | :--- |
| **01** | **【開局震撼】**：為什麼 AI 生成的程式碼會在第 101 小時崩潰？軟體危機、Garvin 五大品質觀點與軟體要素 | **AI 程式碼破壞實驗**：給定一個 AI 生成的交易系統，找出隱藏的並發、精度與崩潰漏洞 | 📖 [Ch01 導論](Lecture/source/ch01t_intro.md)<br>🛠️ [Lab 01 AI 破壞實驗](LabDemo/docs/u01_debug/ai_code_break.md) |
| **02** | **【核心理論】現代軟體品質模型 (ISO 9126 $\rightarrow$ ISO 25010)**：8 大產品品質特性、使用品質與品質成本 (CoQ 1:10:100 定律) | **除錯實務與科學假設檢驗**：條件斷點、例外斷點與日誌追蹤分析 | 📖 [Ch02 錯與除錯](Lecture/source/ch02_bug.md)<br>🛠️ [Lab 02 除錯實務](LabDemo/docs/u01_debug/debug.md) |
| **03** | **防禦性架構與合約設計 (Design by Contract)**：前置/後置條件、狀態不變量 (Class Invariants) 與自我診斷防線 | 使用 Java Assertions、Google Guava Preconditions 與結構化日誌（SLF4J/MDC）建立自我防護 | 📖 [Ch03 測試原則](Lecture/source/ch03_testing.md)<br>🛠️ [Lab 03 斷言防線](LabDemo/docs/u02_preventive/assertion.md) |
| **04** | **現代靜態分析與架構異味檢測**：從 AST 語法樹看程式碼異味 (Code Smells)、安全漏洞 (OWASP Top 10) 與架構腐化 | 打造 CI 靜態品質門檻：SonarQube / SpotBugs / PMD 規則設定與自訂檢測規則 | 📖 [Ch04 檢視與靜態分析](Lecture/source/ch04_inspection.md)<br>🛠️ [Lab 04 靜態分析](LabDemo/docs/u03_inspection/pmd.md) |
| **05** | **黑箱設計之魂**：等價分割、邊界分析、全成對測試 (Pairwise) 與正交表數學原理 | JUnit 5 現代架構：動態測試 (`@TestFactory`)、參數化測試 (`@ParameterizedTest`) 與自訂 DisplayName | 📖 [Ch05 黑箱測試](Lecture/source/ch05_blackbox.md)<br>🛠️ [Lab 05 JUnit 5](LabDemo/docs/u04_utest/junit.md) |
| **06** | **【典範轉移】屬性基礎測試 (Property-Based Testing)**：告別手寫測資，用數學屬性（Invariants）讓電腦自動生成萬組測資 | **`jqwik` / `Hypothesis` 實戰**：定義演算法不變量，體驗框架自動產生極端測資與縮小化 (Shrinking) | 📖 [Ch05 屬性測試](Lecture/source/ch05_blackbox.md)<br>🛠️ [Lab 06 jqwik 屬性測試](LabDemo/docs/u04_utest/jqwik_property_based.md) |
| **07** | **白箱測試與 MC/DC (Modified Condition/Decision Coverage)**：航空級高可靠度軟體的覆蓋率標準與圈複雜度推導 | JaCoCo 高級分析：分支與指令覆蓋率解讀、為未覆蓋路徑精準補彈 | 📖 [Ch06 白箱測試](Lecture/source/ch06_whitebox.md)<br>🛠️ [Lab 07 白箱測試](LabDemo/docs/u06_wbtesting/whitebox_test.md) |
| **08** | **【期中能力鑑定 (Midterm Exam)】**（涵蓋 ISO 25010 品質模型、規格推導、MC/DC 分析、屬性不變量與測試設計） | **【專題提案與品質架構審查 (Architecture & Spec Review)】**：各組發表專題規格、防護策略與品質指標目標 | - |
| **09** | **變異測試 (Mutation Testing) —— 測試品質的唯一真理**：誰來監督監督者？變異算子、殺死率與等價變異體難題 | **PIT (Pitest) 實戰**：注入故障變異體，計算 Mutation Score，揪出「高覆蓋率卻測不出 Bug」的假測試 | 📖 [Ch06 變異測試](Lecture/source/ch06_whitebox.md)<br>🛠️ [Lab 08 變異測試](LabDemo/docs/u07_mutation/mutation_test.md) |
| **10** | **隔離架構與 Test Double 原則**：何時用 Mock？何時不用 Mock？過度 Mock 的反模式與脆化測試 (Brittle Tests) | **Mockito 進階**：深入 `ArgumentCaptor`、Spy、嚴格 Stubbing (`Strictness.STRICT_STUBS`) 與架構解耦 | 📖 [Ch07 整合測試](Lecture/source/ch07_integration.md)<br>🛠️ [Lab 09 Mockito](LabDemo/docs/u08_integration/mokito.md) |
| **11** | **真實環境整合測試 (Testcontainers) 與 API 測試**：拒絕 H2 內存庫幻覺，使用真實 Docker 容器測試資料庫與中介軟體 | **Testcontainers + Spring Boot Test**：一鍵拉起真實 PostgreSQL / Redis 容器進行毫秒級資料庫整合測試 | 📖 [Ch07 整合測試 (7.8 容器化)](Lecture/source/ch07_integration.md)<br>🛠️ [Lab 10 Testcontainers](LabDemo/docs/u08_integration/testcontainers_spring.md) |
| **12** | **微服務契約測試 (Contract Testing with Pact) & 現代 E2E**：分散式系統中 API 契約防護；Playwright 現代化 Web 測試 | **Pact 實戰 + Playwright 自動化**：定義 Consumer-Driven Contracts；撰寫抗網路波動、具錄影回放的 Web 驗收測試 | 📖 [Ch08 系統測試 (契約與 E2E)](Lecture/source/ch08_system.md)<br>🛠️ [Lab 11 契約與 Playwright](LabDemo/docs/u09_cucumber_bdd/pact_and_playwright.md) |
| **13** | **高併發、效能與負載工程**：TPS、P99 延遲、資源洩漏、排隊理論與壓測模型設計 | **k6 / JMeter 現代壓測**：用程式碼定義負載情境（Load as Code），進行突波測試 (Spike) 與耐力測試 (Soak) | 📖 [Ch08 效能測試](Lecture/source/ch08_system.md)<br>🛠️ [Lab 12 k6 壓測](LabDemo/docs/u10_performance/k6_load_testing.md) |
| **14** | **【專題系統完成與交付凍結 (Code Freeze)】**：各組完成系統開發與自測防線，並**正式交付給對應組別**進行交叉測試 | **Chaos-Mesh / Fault Injection 實作**：隨機注入網路延遲、殺死 Pod、模擬磁碟滿載，驗證系統容錯自癒力 | 📖 [Ch08 混沌與安全測試](Lecture/source/ch08_system.md)<br>🛠️ [Lab 13 模糊與混沌測試](LabDemo/docs/u10_chaos_fuzzing/chaos_and_fuzzing.md) |
| **15** | **【AI in SQA 前沿】Agentic Testing 與自律測試機器人**：Prompt Engineering for QA、AI 輔助探索性測試、自我修復測試套件 | 打造 GitHub Actions 自動化 CI/CD 全防線；**持續進行他組系統交叉測試**並撰寫《測試發現與品質改善報告》 | 📖 [AI in QA 專題](Lecture/source/UX_and_AI.md)<br>🛠️ [Lab 14 CI 品質防線](LabDemo/docs/u11_devops/github_actions_quality_gate.md) |
| **16** | **【期末能力筆試 (Final Exam)】**（30% 觀念：ISO 25010 品質架構、微服務測試、混沌工程、AI 測試驗證準則） | **【期末專題成果發表會】**：每位同學進行雙向成果報告：(1) **自家系統架構與自測成果**；(2) **測試他人系統的發現與品質改善建議** | 🏆 成果發表 |
| **17** | **【自主學習 / 影音單元】現代測試文件標準與活文件工程 (Doc-as-Code)**：建議大方向：精簡版 IEEE 829/ISO 29119 架構、需求雙向追溯 (RTM) 與 Markdown/Gherkin 活文件實踐 | **影音自主演練**：自由探索現代化測試報表工具（如 Allure Report）或 Doc-as-Code 自動發布流程 | 🎞️ [影音單元自主學習] |
| **18** | **【自主學習 / 影音單元】品質成熟度模型 (CMMI / TMMi) 與品質治理前沿**：建議大方向：軟體組織測試成熟度評鑑 (TMMi Level 1~5)、AI 程式碼審查 (AI Code Review) 與技術債度量治理 | **影音自主演練**：自由選擇感興趣的開源專案或系統架構進行組織級品質度量與成熟度評估探索 | 🎞️ [前沿專題影音] |

---

## 📊 學期考核與評分比重 (Assessment Weights)

* **平時實習作業與課堂 CCQ (Lab & Quizzes)**：**25%**（每週上機即時驗收，重視動手能力）
* **期中能力鑑定筆試 (Midterm Exam)**：**25%**（第 08 週，著重於 ISO 品質模型、黑白箱設計推導、MC/DC 與屬性不變量）
* **期末能力鑑定筆試 (Final Exam)**：**20%**（第 16 週，著重於微服務測試架構、Test Double、混沌工程、AI 測試驗證）
* **期末專題成果發表與交叉測試 (Final Project & Cross-Testing)**：**30%**（含自家系統品質防線建置、交叉測試他人系統之缺陷與品質報告、期末雙向口頭成果發表）
