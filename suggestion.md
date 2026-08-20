# 資工系大三「軟體品質保證與測試 (SQA)」課程規劃與教學建議書

> **課程基本設定**：
> * **授課對象**：資訊工程學系 大三學生（已具備基礎程式設計、物件導向、資料結構與演算法基礎，即將面臨大四畢業專題與業界實習）。
> * **學期時程**：16 週。
> * **每週時數**：3 小時（**1 小時一般教室授課** + **2 小時電腦教室實習演練**）。
> * **核心考核**：期中考、期末考、**期末專題（著重於軟體測試與品質工程，需公開發表）**。
> * **核心教學目標**：軟體品質概念與重要性 $\rightarrow$ 傳統/經典測試理論與技巧 $\rightarrow$ 現代自動化測試與 CI/CD $\rightarrow$ 最新 AI 輔助測試技術。

---

## 🧭 一、 總體評估與定位分析

### 1.1 現有教材內容合適度評估
整體而言，本教材（`Lecture/` 與 `Lab/`）的體系非常**扎實且完整**，涵蓋了從軟體危機、品質模型（ISO 9126）、靜態檢視（Fagan/PMD）、黑白箱測試、變異測試（PIT）、Mockito、Spring 整合測試、BDD/Cucumber、JMeter 效能測試到 CMMI/DevOps。

### 1.2 針對「資工大三學生」的特點與教學痛點
1. **先備知識**：學生已經會寫程式（Java/Python/C++），但程式風格多為「能動就好」，缺乏「防禦性編程（Defensive Programming）」與「為可測試性而設計（Design for Testability）」的架構習慣。
2. **最大痛點**：
   * 過去寫作業通常只測「Happy Path（正常流程）」，不知道如何有系統地找出邊界漏洞。
   * 覺得「寫測試很浪費時間」，尚未體會大型專案中回歸測試（Regression Test）與自動化 CI 防線的威力。
   * 普遍已在日常使用 ChatGPT、GitHub Copilot 或 Claude 等 AI 工具，但**缺乏鑑別 AI 產生之測試品質與抓出 AI 幻覺（Hallucination）的能力**。
3. **課程價值**：本課程是資工系大三銜接「大四畢業專題（Capstone）」與「業界軟體工程師實習/就業」的關鍵實戰課。

---

## 🏛️ 二、 大方向建議 (Macro Directions)

### 2.1 核心教學主軸：三階段漸進演進
建議將 16 週的教學脈絡緊扣「思維演進」，讓學生循序漸進：
```
  【階段一：品質思維與手動設計】
   理解軟體危機 ➔ Garvin/ISO 品質模型 ➔ 嚴謹黑箱/白箱/路徑推導（數學與邏輯思維）
               ▼
  【階段二：工程化與自動化防線】
   單元測試 (JUnit 5) ➔ 依賴隔離 (Mockito) ➔ 變異測試 (PIT) ➔ BDD/E2E ➔ CI/CD Quality Gate
               ▼
  【階段三：現代 AI 輔助與效能驗證】
   AI 生成測試 ➔ Test Oracle 鑑別 ➔ 變異殺死率驗證 AI 測試 ➔ 負載/壓力測試 (JMeter)
```

### 2.2 「1 小時講授 + 2 小時實習」的黃金節奏配置
* **1 小時一般教室 (Lecture)**：
  * **拒絕純唸投影片**：每堂課聚焦「一個震撼案例（如波斯灣愛國者飛彈、名古屋空難）」+「一個核心演算法/理論推導」。
  * **紙筆/白板即時演練 (Active Learning)**：課堂上直接給出一段邏輯或規格，讓學生用 5~10 分鐘推導決策表、計算圈複雜度或劃分等價類，並搭配 CCQ 互動測驗。
* **2 小時電腦教室 (Lab/Hands-on)**：
  * **將課堂推導直接轉化為程式碼**：將 1 小時課堂上推導的測試案例，直接在 IDE 中寫成 JUnit 5 測試。
  * **破壞性學習（Break the Code）**：引導學生故意寫出 Bad Code 或注入 Fault，體會變異測試（PIT）如何揪出無效測試。
  * **AI 對照實驗**：讓學生自己寫測試 vs. 讓 Copilot/ChatGPT 寫測試，並用覆蓋率（JaCoCo）與變異殺死率（PIT）客觀比較兩者差距。

### 2.3 評量機制配置建議（建議配分比重）
* **平時實習作業與課堂 CCQ (Lab & Quizzes)**：**25%**（每週上機即時驗收，重視動手能力）
* **期中考試 (Midterm Exam)**：**25%**（第 08 週，著重於理論、計算與手動測試設計：邊界值、等價類、決策表、狀態機、CFG 圈複雜度、基準路徑與 MC/DC 推導）
* **期末考試 (Final Exam)**：**20%**（第 16 週，著重於整合架構、Mockito 原理、系統測試、品質成本、CI/CD 概念與 AI 測試評估原則）
* **期末專題與發表 (Final Project & Presentation)**：**30%**（著重於測試工程與品質保證實踐，包含書面報告、成果展示與同儕互測）

---

## 📅 三、 16 週精準教學大綱規劃 (Weekly Syllabus)

| 週次 | 模組主題 | 1 小時講授 (Lecture - 理論/思維) | 2 小時實習 (Lab - 上機實戰) | 產出與里程碑 |
| :---: | :--- | :--- | :--- | :--- |
| **01** | **模組一：品質思維與防禦基石** | 課程導論、軟體危機經典案例、軟體品質定義與 ISO 9126 / ISO 25010 模型 | Java / Maven 環境建置、Git 流程規範與 IDE 整合測試外掛配置 | 熟悉專案骨架與環境 |
| **02** | 模組一 | 錯與除錯理論：Bug / Fault / Failure 差異、除錯思維與二分定位法 | IntelliJ IDEA 高級除錯實務：條件斷點 (Conditional Breakpoints)、Exception 攔截與日誌追蹤 | Lab 01 驗收 |
| **03** | 模組一 | 防禦性程式設計 (Defensive Programming)：前置/後置條件、Invariants 與斷言理論 | 斷言 (Assertion)、例外處理架構與 SLF4J/Logback 結構化日誌實務 | Lab 02 驗收 |
| **04** | 模組一 | 靜態程式碼檢視 (Fagan Inspection) 與程式碼異味 (Code Smells) 識別 | 自動化靜態分析工具：PMD、Checkstyle 與 SpotBugs 規則客製化與實作 | Lab 03 驗收 |
| **05** | **模組二：經典測試理論與案例設計** | 黑箱測試 (I)：等價類劃分 (EP)、邊界值分析 (BVA) 與全成對組合測試 (Pairwise) | JUnit 5 核心語法、Assertions、`@ParameterizedTest` 參數化測試實務 | 專題分組與題目發布 |
| **06** | 模組二 | 黑箱測試 (II)：決策表測試 (Decision Tables) 與 狀態轉換測試 (State Transition) | 複雜業務邏輯與有限狀態機 (FSM) 之單元測試撰寫實務 | Lab 04 驗收 |
| **07** | 模組二 | 白箱測試 (I)：邏輯覆蓋率（語句 C0、分支 C1、條件 C2、MC/DC）推導 | JaCoCo 程式碼覆蓋率分析、覆蓋率報告解讀與測試案例補全演練 | 專題題目提案 (Proposal) |
| **08** | **期中檢驗週** | **【期中考試 (Midterm Exam)】**（60 分鐘筆試：理論、黑白箱測試設計推導） | **期末專題提案審查 (Proposal Review)**：各組 3 分鐘簡報確認專案架構與測試目標 | **期中考 & 專題確定** |
| **09** | **模組三：進階評估與自動化測試體系** | 白箱測試 (II)：控制流圖 (CFG)、McCabe 圈複雜度與基準路徑測試 (Basis Path Testing) | 基準路徑測試實作與代碼複雜度度量工具演練 | Lab 05 驗收 |
| **10** | 模組三 | 變異測試 (Mutation Testing)：評估測試套件的有效性、殺死變異體與等價變異 | PIT (Pitest) 變異測試工具實戰、分析存活變異並優化測試強度 | Lab 06 驗收 |
| **11** | 模組三 | 整合測試策略與 Test Double（Dummy, Stub, Spy, Mock, Fake）架構原則 | Mockito 框架實戰：`when-then`、`verify()`、`ArgumentCaptor` 依賴隔離測試 | Lab 07 驗收 |
| **12** | 模組三 | 服務層與 Web API 整合測試架構 | Spring Boot Test (`@SpringBootTest`, `MockMvc`, `@MockBean`) RESTful API 測試實踐 | Lab 08 驗收 |
| **13** | **模組四：端到端、AI 增強與專題發表** | 行為驅動開發 (BDD) 與端到端 (E2E) 測試：以使用者視角驗收系統 | Cucumber (Gherkin 語法) BDD 實作 或 Playwright / Selenium Web 自動化驗收測試 | Lab 09 驗收 |
| **14** | 模組四 | 效能測試與非功能測試：負載 (Load)、壓力 (Stress) 與容量指標 (TPS/RT/Error Rate) | Apache JMeter 壓力測試實務：建立執行緒群組、設定負載曲線與解讀效能報告 | Lab 10 驗收 |
| **15** | 模組四 | **現代 AI 輔助軟體測試 (AI in SQA)**：LLM / Copilot 測試生成、Prompt Engineering for QA、Test Oracle 挑戰與限制 | 實作演練：使用 Copilot / ChatGPT 生成單元測試、使用 PIT 檢驗 AI 生成測試之品質；GitHub Actions CI 自動化測試建置 | 專題最終除錯與整合作業 |
| **16** | **期末驗收週** | **【期末考試 (Final Exam)】**（50 分鐘觀念與架構測驗） | **【期末專題成果公開發表會 (Final Project Demo & Presentation)】** | **專題成果發表與總結** |

---

## 🤖 四、 現代 AI 輔助測試（AI-Assisted Testing）融入策略

大三學生非常需要理解「如何正確且批判性地使用 AI 工具」，避免養成盲目複製貼上的壞習慣。建議在課程中融入以下具體觀念與實作：

### 4.1 核心觀念引導
1. **AI 測試的「自我印證偏誤（Self-Fulfilling Bias）」**：
   * 如果程式碼本身有 Bug，直接叫 AI 寫測試，AI 往往會依據有 Bug 的程式碼來寫 Assertion，導致測試全綠但系統依然錯誤！
   * **教學重點**：測試必須基於「**規格（Specification）**」而非「既有程式碼（Code Implementation）」。
2. **Test Oracle 難題**：
   * AI 很擅長生成 Mock 資料與語法骨架，但最難的是判斷「正確預期輸出（Oracle）」是什麼。
3. **AI 生成測試的有效性驗證**：
   * 建立標準 SOP：`AI 生成測試 ➔ 執行 JaCoCo 看涵蓋率 ➔ 執行 PIT 變異測試看殺死率 ➔ 人工介入補充邊界值與例外條件`。

### 4.2 實習課具體 AI 演練題目設計
* **實驗：人機對決與人機協同 (Human vs. AI Testing Challenge)**：
  1. 給定一個有隱藏缺陷的複雜業務邏輯（如：電商折扣促銷計算、跨時區預約系統）。
  2. **Round 1**：學生純手動依據等價類與邊界值設計測試案例。
  3. **Round 2**：學生使用 Prompt 要求 GitHub Copilot / ChatGPT 生成測試案例。
  4. **Round 3**：同時對兩組測試執行 PIT 變異測試與 Mutation 注入。
  5. **討　論**：比較兩者在覆蓋率、變異殺死率、邊界極端值挖掘上的差異，總結 AI 容易遺漏的盲點。

---

## 🏆 五、 期末專題（Final Project）設計與評審規範

為了確保期末專題**真正聚焦在「軟體測試與品質保證」**，而非變成普通的寫 App 作業，建議提供以下專案設計：

### 5.1 專題題型二選一 (Project Tracks)

#### 【Track A：開源/既有專案之「品質體檢與測試重構」】（強烈推薦）
* **說明**：選定一個公開的 GitHub 開源專案（或系上過往留下的中型 Java/Web 系統，約 1,000~3,000 行）。
* **任務**：
  1. 針對現有專案進行靜態分析（PMD/SonarQube），抓出所有 Code Smells 與潛在 Bug。
  2. 重新規劃測試策略，補齊測試金字塔（Unit Test + Mockito Integration Test）。
  3. 導入 JaCoCo 與 PIT，將行覆蓋率提升至 **85% 以上**，變異殺死率提升至 **70% 以上**。
  4. 建立 GitHub Actions CI 自動化測試流程與 PR Quality Gate。

#### 【Track B：全自建系統之「全方位測試金字塔實踐」】
* **說明**：小組自選主題開發一套具備 REST API 與資料庫的服務系統（如：圖書館借還系統、線上點餐系統）。
* **任務**：
  1. **規格先行**：撰寫 IEEE 829 風格的測試計畫書與測試案例規格。
  2. **單元與整合測試**：使用 JUnit 5 + Mockito + Spring Boot Test 達成嚴格測試涵蓋。
  3. **E2E / BDD 測試**：撰寫至少 5 個核心使用者情境的 Cucumber 或 Playwright 自動化驗收測試。
  4. **效能測試**：使用 JMeter 進行高併發壓力測試，找出系統效能瓶頸並給出調優數據報告。
  5. **CI/CD 自動化**：每次 Git Push 自動執行完整測試套件並產生測試報告。

### 5.2 專題評分規準 (Rubrics, 滿分 100%)

| 評分維度 | 權重 | 具體評審重點與標準 |
| :--- | :---: | :--- |
| **1. 測試案例設計嚴謹度** | **25%** | 是否系統性運用等價類、邊界值、決策表、狀態轉換設計測資？是否涵蓋足夠的異常/極端路徑？ |
| **2. 測試覆蓋度與變異品質** | **25%** | JaCoCo 程式碼覆蓋率（行覆蓋 $\ge 80\%$、分支覆蓋 $\ge 75\%$）與 PIT 變異殺死率（$\ge 65\%$）。 |
| **3. 自動化與 CI/CD 整合** | **20%** | GitHub Actions 能否全自動執行測試？是否具備測試失敗阻擋 Merge 的保護機制？ |
| **4. 非功能 / BDD / AI 應用** | **15%** | 是否具備 JMeter 效能壓測分析、BDD 情境測試或批判性 AI 測試生成與檢驗報告？ |
| **5. 現場展示與發表答辯** | **15%** | 簡報流暢度、現場 Live Demo（展示故意注入 Bug 時測試如何精準攔截）、答辯邏輯清晰度。 |

### 5.3 發表會亮點環節：【互測挑戰賽 (Chaos & Bug Bounty)】
在第 16 週專題發表時，可安排「**小組互測時間**」：
* A 組將自己寫的專案（隱藏 3 個故意設計的邊界 Bug）交給 B 組。
* B 組限時使用 AI 工具或測試技巧進行黑箱測試/探索性測試，看能否在時間內攻破 A 組防線。
* 這能大幅提升學生的參與感與實戰成就感！

---

## 🛠️ 六、 現有教材庫優化建議 (Actionable Steps for Repository)

1. **章節檔案重命名與目錄標準化**：
   * 已完成 `Lecture/` 檔名空格消除（`ch01_intro.md` ～ `ch10_cmmi.md`）與 Marp 簡報骨架生成。
2. **補齊 AI 輔助測試章節**：
   * 建議可將現有的 `Lecture/UX_and_AI.md` 擴充改版為 **`Lecture/ch11_ai_testing.md`（AI 輔助軟體測試實踐）**，納入 Prompt Engineering for Testing、Test Oracle、AI 生成測試之驗證等教材。
3. **更新首頁 `readme.md`**：
   * 將上述優化後的 16 週進度表、期末專題規範（Track A/B）與評分 Rubrics 同步至 `readme.md`，作為學生開學第一天的清楚修課指引。
