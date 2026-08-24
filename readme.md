# 軟體品質保證與可靠性工程 (SQA 2.0)
### Software Quality Assurance & Reliability Engineering (2026 AI Era Edition)

> 歡迎來到資工系大三 **《軟體品質保證與可靠性工程》** 課程庫！
> 
> 在 2026 AI 時代，**「寫出程式碼（Coding）」的門檻已被 LLM 大幅抹平，而「定義好軟體的標準（Quality Models）」與「驗證程式碼的強固性（Verification & Reliability）」成為資工系學生最核心的工程競爭力。**
> 
> 本課程以 **現代軟體品質模型 (ISO 25010 / Garvin)** 為指北針，結合 **屬性測試 (Property-Based Testing)**、**變異測試 (PIT)**、**容器化整合 (Testcontainers)**、**契約測試 (Pact)**、**混沌工程 (Chaos Engineering)** 與 **AI 測試代理 (Agentic QA)**，引導學生從傳統的「手動抓蟲」躍升為具備全局視野的「軟體品質與可靠性架構師」。

---

## 🧭 核心教學理念與四大典範轉移

```
【傳統思維：1990~2010 年代】              【大破大立思維：2026 AI 時代】
手動抓蟲、填寫紙本測試表格      ➡️    不變量思考 (Invariants) & 規格工程
手敲大量重複的單點 assertEquals  ➡️    屬性測試 (Property-Based Testing, 自動萬組極端測資)
純 Mock 虛擬環境單元測試        ➡️    Testcontainers 真實容器化整合測試 & 契約測試 (Pact)
傳統 Selenium 易碎 UI 測試      ➡️    現代 Playwright E2E + 混沌工程 (Chaos Engineering)
各自做普通 CRUD App 寫報告       ➡️    ⚔️ 紅藍軍軟體品質攻防擂台賽 (Red vs. Blue Arena)
```

---

## 🗺️ ISO 25010 軟體品質模型與實戰技術對照地圖

本課程強調「測試不是盲目寫 code，每一種測試都在度量並守護品質模型的具體維度」：

| ISO 25010 品質特性 | 核心子特性 (Sub-characteristics) | 本課程對應之測試與工程技術 |
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

## 🗓️ 16 週精準教學大綱規劃

本課程每週 **3 小時**（**1 小時一般教室理論授課** + **2 小時電腦教室實習演練**），學中做、做中學：

| 週次 | 核心模組 | 1 小時講授 (Lecture - 理論/前沿思維) | 2 小時實習 (Lab - 現代工具實戰) | 品質模型對應維度 / 產出 | 教材與實習手冊連結 |
| :---: | :--- | :--- | :--- | :--- | :--- |
| **01** | **第一階段：品質模型與 AI 信任危機** | **【開局震撼】**：為什麼 AI 生成的代碼會在第 101 小時崩潰？軟體危機、Garvin 五大品質觀點與軟體要素 | **AI 代碼破壞實驗**：給定一個 AI 生成的交易系統，找出隱藏的並發、精度與崩潰漏洞 | 認識品質的代價與重要性 | 📖 [Ch01 導論](Lecture/ch01_intro.md)<br>🛠️ [Lab 01 AI 破壞實驗](Lab/u02_debug/ai_code_break.md) |
| **02** | 第一階段 | **【核心理論】現代軟體品質模型 (ISO 9126 $\rightarrow$ ISO 25010)**：8 大產品品質特性、使用品質與品質成本 (CoQ 1:10:100 定律) | **除錯實務與科學假設檢驗**：條件斷點、例外斷點與日誌追蹤分析 | **建立「好軟體」的評鑑指北針** | 📖 [Ch02 錯與除錯](Lecture/ch02_bug.md)<br>🛠️ [Lab 02 除錯實務](Lab/u02_debug/debug.md) |
| **03** | 第一階段 | **防禦性架構與合約設計 (Design by Contract)**：前置/後置條件、狀態不變量 (Class Invariants) 與自我診斷防線 | 使用 Java Assertions、Google Guava Preconditions 與結構化日誌（SLF4J/MDC）建立自我防護 | **守護：可靠性 (容錯度)**<br>Lab 03：防禦防線建置 | 📖 [Ch03 測試原則](Lecture/ch03_testing.md)<br>🛠️ [Lab 03 斷言防線](Lab/u03_preventive/assertion.md) |
| **04** | 第一階段 | **現代靜態分析與架構異味檢測**：從 AST 語法樹看程式碼異味 (Code Smells)、安全漏洞 (OWASP Top 10) 與架構腐化 | 打造 CI 靜態品質門檻：SonarQube / SpotBugs / PMD 規則設定與自訂檢測規則 | **守護：可維護性、安全性**<br>Lab 04：Code Smell 攔截 | 🛠️ [Lab 04 靜態分析](Lab/u04_inspection/pmd.md) |
| **05** | **第二階段：測試理論的極致與現代化** | **黑箱設計之魂**：等價分割、邊界分析、全成對測試 (Pairwise) 與正交表數學原理 | JUnit 5 現代架構：動態測試 (`@TestFactory`)、參數化測試 (`@ParameterizedTest`) 與自訂 DisplayName | **守護：功能適合性**<br>專題分組與賽制發布 | 📖 [Ch05 黑箱測試](Lecture/ch05_blackbox.md)<br>🛠️ [Lab 05 JUnit 5](Lab/u05_bbtest/junit.md) |
| **06** | 第二階段 | **【典範轉移】屬性基礎測試 (Property-Based Testing)**：告別手寫測資，用數學屬性（Invariants）讓電腦自動生成萬組測資 | **`jqwik` / `Hypothesis` 實戰**：定義演算法不變量，體驗框架自動產生極端測資與縮小化 (Shrinking) | **守護：可靠性 (極限邊界)**<br>顛覆傳統測試思維 | 📖 [Ch05 屬性測試](Lecture/ch05_blackbox.md)<br>🛠️ [Lab 06 jqwik 屬性測試](Lab/u05_bbtest/jqwik_property_based.md) |
| **07** | 第二階段 | **白箱測試與 MC/DC (Modified Condition/Decision Coverage)**：航空級高可靠度軟體的覆蓋率標準與圈複雜度推導 | JaCoCo 高級分析：分支與指令覆蓋率解讀、為未覆蓋路徑精準補彈 | **守護：功能適合性、結構完整**<br>Lab 07：高強度白箱實戰 | 📖 [Ch06 白箱測試](Lecture/ch06_whitebox.md)<br>🛠️ [Lab 07 白箱測試](Lab/u06_wbtesting/whitebox_test.md) |
| **08** | **期中檢驗** | **【期中能力鑑定 (Midterm Exam)】**（涵蓋 ISO 25010 品質模型、規格推導、MC/DC 分析、屬性不變量與測試設計） | **【專題提案與藍軍品質架構審查 (Architecture & Spec Review)】**：各組發表系統規格、防禦藍圖與品質指標目標 | **期中考 & 藍軍防禦架構確認** | - |
| **09** | **第三階段：微服務、真實環境與非功能測試** | **變異測試 (Mutation Testing) —— 測試品質的唯一真理**：誰來監督監督者？變異算子、殺死率與等價變異體難題 | **PIT (Pitest) 實戰**：注入故障變異體，計算 Mutation Score，揪出「高覆蓋率卻測不出 Bug」的假測試 | **守護：測試套件可維護性與有效性**<br>Lab 08：變異殺死挑戰 | 🛠️ [Lab 08 變異測試](Lab/u06_mutation/mutation_test.md) |
| **10** | 第三階段 | **隔離架構與 Test Double 原則**：何時用 Mock？何時不用 Mock？過度 Mock 的反模式與脆化測試 (Brittle Tests) | **Mockito 進階**：深入 `ArgumentCaptor`、Spy、嚴格 Stubbing (`Strictness.STRICT_STUBS`) 與架構解耦 | **守護：可測試性、模組化**<br>Lab 09：重構過度 Mock 代碼 | 📖 [Ch07 整合測試](Lecture/ch07_integration.md)<br>🛠️ [Lab 09 Mockito](Lab/u07_integration/mokito.md) |
| **11** | 第三階段 | **真實環境整合測試 (Testcontainers) 與 API 測試**：拒絕 H2 內存庫幻覺，使用真實 Docker 容器測試資料庫與中介軟體 | **Testcontainers + Spring Boot Test**：一鍵拉起真實 PostgreSQL / Redis 容器進行毫秒級資料庫整合測試 | **守護：可移植性、環境一致性**<br>Lab 10：容器化整合實戰 | 🛠️ [Lab 10 Testcontainers](Lab/u07_integration/testcontainers_spring.md) |
| **12** | 第三階段 | **微服務契約測試 (Contract Testing with Pact) & 現代 E2E**：分散式系統中 API 契約防護；Playwright 現代化 Web 測試 | **Pact 實戰 + Playwright 自動化**：定義 Consumer-Driven Contracts；撰寫抗網路波動、具錄影回放的 Web 驗收測試 | **守護：相容性、易用性**<br>Lab 11：契約與 E2E 防衛 | 📖 [Ch08 系統測試](Lecture/ch08_system.md)<br>🛠️ [Lab 11 契約與 Playwright](Lab/u08_contract_e2e/pact_and_playwright.md) |
| **13** | **第四階段：前沿品質工程、AI 與混沌實戰** | **高併發、效能與負載工程**：TPS、P99 延遲、資源洩漏、排隊理論與壓測模型設計 | **k6 / JMeter 現代壓測**：用程式碼定義負載情境（Load as Code），進行突波測試 (Spike) 與耐力測試 (Soak) | **守護：效能效率**<br>Lab 12：效能瓶頸診斷 | 📖 [Ch08 效能測試](Lecture/ch08_system.md)<br>🛠️ [Lab 12 k6 壓測](Lab/u08_performance/k6_load_testing.md) |
| **14** | 第四階段 | **模糊測試 (Fuzzing) 與混沌工程 (Chaos Engineering)**：注入混亂，在生產環境崩潰前主動搞壞系統 | **Chaos-Mesh / Fault Injection 實作**：隨機注入網路延遲、殺死 Pod、模擬磁碟滿載，驗證系統容錯自癒力 | **守護：可靠性、安全性**<br>Lab 13：混沌破壞實驗 | 🛠️ [Lab 13 模糊與混沌測試](Lab/u08_chaos_fuzzing/chaos_and_fuzzing.md) |
| **15** | 第四階段 | **【AI in SQA 前沿】Agentic Testing 與自律測試機器人**：Prompt Engineering for QA、AI 輔助探索性測試、自我修復測試套件 | 打造 GitHub Actions 自動化 CI/CD 全防線（包含 SonarQube + JaCoCo + PIT + AI 程式碼審查 Bot） | **守護：DevOps 流程品質**<br>藍軍系統封裝，紅軍備戰 | 📖 [AI in QA 專題](Lecture/UX_and_AI.md)<br>🛠️ [Lab 14 CI 品質防線](Lab/u10_devops/github_actions_quality_gate.md) |
| **16** | **決戰與收官** | **【期末能力筆試 (Final Exam)】**（30% 觀念：ISO 25010 品質架構、微服務測試、混沌工程、AI 測試驗證準則） | **🔥【紅藍軍品質攻防大擂台 (Red vs Blue Chaos Arena)】**：現場 Live 攻防滲透、展示自癒防線與頒獎 | **專題公開發表與競賽** | 🏆 成果發表 |

---

## ⚔️ 期末專題：【紅藍軍軟體品質攻防擂台 ＆ 種子缺陷挑戰賽】

專題跳脫「交普通 CRUD 系統」的傳統模式，採用賽事化 **「紅藍攻防 + 種子缺陷 (Defect Seeding)」** 機制：

```
  【階段一：藍軍築防 (第 08～14 週)】             【階段二：紅藍交鋒 (第 15～16 週)】
  打造符合 ISO 25010 高可靠微服務系統             各隊互換權限，扮演「紅軍攻擊者」
  • 完整的規格定義 (OpenAPI / Invariants)        • 使用 AI 模糊測試 (Fuzzing) 注入極端測資
  • Property-Based Testing + PIT > 75%         • 注入混沌故障 (Chaos/Concurrency/Memory)
  • Testcontainers + Playwright E2E             • 尋找防守方未考慮的邊界漏洞與當機條件
  • 秘密埋藏 3 個極隱蔽的【種子缺陷】給老師備查   • 撰寫「漏洞滲透報告」並提交 Issue
  • 產出《ISO 25010 品質模型達成度度量報告》
```

### 🎯 攻防積分與結算機制
* **藍軍（防守方）**：
  * 基礎防線：屬性測試、PIT 變異殺死率 $\ge 70\%$、GitHub Actions 自動阻擋不合格 PR。
  * 若紅軍發動萬筆模糊攻擊但藍軍系統**完全防禦且無崩潰** $\rightarrow$ 獲得【鋼鐵防禦大獎】。
  * 藍軍故意埋的種子缺陷未被紅軍發現 $\rightarrow$ 獲得【高超設計分】。
* **紅軍（進攻方）**：
  * 🎯 抓到藍軍【故意埋的種子缺陷】 $\rightarrow$ 獲得「精準打擊分」。
  * 💥 抓到藍軍【自己都不知道的真實未預期 Bug (Zero-Day)】 $\rightarrow$ 獲得「暴擊超額加分」！
  * 提交嚴謹、具備可重現測試腳本的 Issue 報告。

---

## 📊 學期考核與評分比重 (Assessment Weights)

* **平時實習作業與課堂 CCQ (Lab & Quizzes)**：**25%**（每週上機即時驗收，重視動手能力）
* **期中能力鑑定筆試 (Midterm Exam)**：**25%**（第 08 週，著重於 ISO 品質模型、黑白箱設計推導、MC/DC 與屬性不變量）
* **期末能力鑑定筆試 (Final Exam)**：**20%**（第 16 週，著重於微服務測試架構、Test Double、混沌工程、AI 測試驗證）
* **期末紅藍攻防專題與發表 (Final Project & Arena)**：**30%**（含藍軍系統品質、PIT 變異殺死率、紅軍滲透報告與現場 Live Demo）
