---
marp: true
theme: gaia
_class: lead
paginate: true
backgroundColor: #f5f5f5
color: #333
style: |
  section {
    font-family: 'Helvetica Neue', Arial, sans-serif;
    padding: 40px;
    font-size: 24px;
  }
  h1 {
    color: #0b3c5d;
  }
  h2 {
    color: #328cc1;
  }
  footer {
    position: absolute;
    left: 40px;
    bottom: 40px;
    text-align: left;
    font-size: 0.5em;
    color: #777;
  }
  header {
    font-size: 0.5em;
    color: #aaa;
    text-align: right;
  }
  blockquote {
    background: transparent;
    border-left: 4px solid #328cc1;
    margin: 1em 0;
    padding: 5px 20px;
    font-style: italic;
    color: inherit;
    opacity: 0.85;
  }
  blockquote::before {
    content: none !important;
  }
  table {
    font-size: 20px;
  }
  section:has(div.ccq-columns),
  section:has(div.discussion-columns),
  section:has(div.fill-blank-columns) {
    display: flex;
    flex-direction: column;
  }
  div.ccq-columns {
    display: flex;
    align-items: center;
    gap: 30px;
    margin-top: auto;
    margin-bottom: auto;
  }
  div.ccq-text {
    flex: 70%;
  }
  div.ccq-logo {
    flex: 30%;
    text-align: center;
  }
  div.ccq-logo img {
    width: 100%;
    max-width: 180px;
  }
  div.discussion-columns {
    display: flex;
    align-items: center;
    gap: 30px;
    margin-top: auto;
    margin-bottom: auto;
  }
  div.discussion-text {
    flex: 75%;
    font-size: 1.25em;
    line-height: 1.4;
  }
  div.discussion-logo {
    flex: 25%;
    text-align: center;
  }
  div.discussion-logo img {
    width: 100%;
    max-width: 150px;
  }
  div.fill-blank-columns {
    display: flex;
    align-items: center;
    gap: 30px;
    margin-top: auto;
    margin-bottom: auto;
  }
  div.fill-blank-text {
    flex: 75%;
  }
  div.fill-blank-logo {
    flex: 25%;
    text-align: center;
  }
  div.fill-blank-logo img {
    width: 100%;
    max-width: 150px;
  }
  div.split64, div.split46, div.split55 {
    display: flex;
    align-items: center;
    gap: 20px;
  }
  div.split64 > div.left {
    flex: 60%;
  }
  div.split64 > div.right {
    flex: 40%;
    text-align: center;
  }
  div.split64 > div.right img {
    width: 100%;
    max-width: 320px;
  }
  div.split46 > div.left {
    flex: 40%;
  }
  div.split46 > div.right {
    flex: 60%;
    text-align: center;
  }
  div.split46 > div.right img {
    width: 100%;
    max-width: 480px;
  }
  div.split55 > div.left {
    flex: 50%;
  }
  div.split55 > div.right {
    flex: 50%;
    text-align: center;
  }
  div.split55 > div.right img {
    width: 100%;
    max-width: 400px;
  }
  section.full-image-slide {
    padding: 0 !important;
  }
  section.full-image-slide::after {
    display: none !important;
  }
  section.full-image-slide header,
  section.full-image-slide footer {
    display: none !important;
  }
  section.full-image-slide div.centered-image {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 720px;
  }
  section.full-image-slide div.centered-image img {
    width: 100%;
    height: 100%;
    object-fit: contain;
  }
  section.title-image-slide {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: stretch;
  }
  section.title-image-slide h2 {
    margin-top: 0;
    margin-bottom: 10px;
  }
  section.title-image-slide div.image-wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-grow: 1;
    height: 480px;
  }
  section.title-image-slide div.image-wrapper img {
    max-width: 100%;
    max-height: 100%;
    object-fit: contain;
  }
  section.lead {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
  }
  section.lead h1 {
    margin: 0 0 20px 0;
  }
  section.lead h2 {
    margin: 0 0 20px 0;
  }
  section.lead p {
    margin: 0;
    font-size: 0.7em;
    line-height: 1.5;
  }
  section.lead p strong {
    color: #328cc1;
  }
  footer {
    position: absolute;
    left: 40px;
    bottom: 40px;
    text-align: left;
  }
  section.lead header {
    display: none !important;
  }
---

# 軟體品質保證導論 (SQA)

### 第一章：簡介與軟體品質概念

授課教師：軟體品質保證教學團隊

---

<!-- _class: lead -->

# **1.1 軟體危機 (Software Crisis)**

> 大家都知道物質不滅定律；我們更熟悉 Bug 不滅定律。

---

## 1.1 何謂軟體危機？

* **背景與起源**：
  * 1960 年代末期，硬體運算能力大幅提升，但軟體開發技術未能跟上。
  * 軟體規模與複雜度呈指數級成長，導致專案頻繁出現**延期、嚴重超支、品質低劣甚至崩潰**。
* **核心挑戰**：
  * 軟體「看不見、摸不著」（無形性），進度難以精確度量。
  * 系統複雜度超出個人大腦可完全掌控的範疇。
  * 缺乏標準化的工程方法、流程規範與品質控制體系。

---

## Case 1：愛國者反導彈事件 (1991)

* **事件背景**：
  * 1991 年波斯灣戰爭，伊拉克飛毛腿飛彈擊中美軍沙烏地達蘭基地，造成 **28 名美軍死亡、100+ 人受傷**。
* **致命缺陷**：
  * 愛國者系統時鐘暫存器採用 **24-bit 浮點數** 設計，每工作 1 小時產生微小的毫秒級轉換截斷誤差。
  * 系統連續運作超過 **100 小時** 未重新開機，時間誤差累計達 **0.33 秒**。
* **災難後果**：
  * 飛毛腿飛彈速度達 4.2 馬赫（1.5 km/s），0.33 秒相當於 **約 600 公尺距離偏差**，雷達搜尋窗無法鎖定目標，攔截飛彈未發射。
* **SQA 啟示**：數值精度與累計誤差問題，長時運行可靠度與壓力測試（Long-term Reliability Testing）。

---

## Case 2：NASA 火星氣候軌道探測器 (1998)

* **事件背景**：
  * 1998 年 NASA 發射「火星氣候軌道探測器」（造價近 2 億美元），抵達火星軌道後失聯焚毀。
* **致命缺陷**：
  * 兩個合作團隊使用 **不同的度量單位**：
    * 洛克希德馬丁（承包商）：**英制單位**（磅力·秒，$\text{lbf}\cdot\text{s}$）
    * NASA 噴射推進實驗室（JPL）：**公制單位**（牛頓·秒，$\text{N}\cdot\text{s}$）
  * 推進控制軟體直接將英制數值當作公制數值運算，未做單位轉換。
* **災難後果**：
  * 軌道高度原本預定 140~150 公里，實際降至 **57 公里**，直接在稀薄大氣層中摩擦焚毀。
* **SQA 啟示**：跨團隊介面合約規範（Interface Contract）、單位相容性與規格檢視審查。

---

## Case 3：華航名古屋空難 (1994)

* **事件背景**：
  * 1994 年華航 CI140（空中巴士 A300-622R）降落名古屋機場失事，**264 人罹難**。
* **致命缺陷**：
  * 副駕駛進場時誤觸「**重飛（Go-Around）**」模式。
  * 駕駛員試圖以手動力量強壓機首下降；然而自動飛控電腦因處於重飛爬升狀態，強行將機尾水平安定面往上配平以抬高機首。
* **災難後果**：
  * **人與電腦互搶控制權**，飛機仰角過大失速墜毀。空巴隨後修改全球 A300 飛控軟體邏輯。
* **SQA 啟示**：人機介面（HMI/UX）狀態透明度、異常操作回饋、人工介入與自動化優先級保護。

---

## Case 4：迪士尼《獅子王》遊戲與相容性危機 (1994)

* **事件背景**：
  * 1994 年聖誕節迪士尼推出《獅子王》PC 遊戲，伴隨 Compaq 電腦大量促銷，數以萬計家庭期待同樂。
* **致命缺陷**：
  * 遊戲採用微軟剛推出的 WinG 繪圖函式庫，但僅在極少數特定硬體上測試，**未進行廣泛相容性測試**。
  * 在大量家用 PC（如特定視訊晶片）上啟動即藍屏當機或程式崩潰。
* **災難後果**：
  * 聖誕節當日客服電話被憤怒家長打爆，造成品牌重大打擊。
* **SQA 啟示**：跨硬體/作業系統的多樣化**相容性測試（Compatibility Testing）**，促使微軟催生標準化 DirectX 架構。

---

## Case 5：臺灣公共重大資訊系統案例

* **2014 年 新戶政系統癱瘓**：
  * 新系統上線首日全台大塞車，民眾無法請領戶籍謄本與身分證。
  * 核心問題：軟硬體架構相容性、壓力負載預估不足、資料庫查詢未經優化。
* **2021 年 高中學習歷程檔案遺失**：
  * 外包工程師在虛擬主機搬移重啟時操作失誤，導致 2.5 萬名學生資料遺失。
  * 核心問題：組態管理（Configuration Management）、備份與災難復原驗證缺失。
* **2014 年 國道計程電子收費 (ETC)**：
  * 上線初期重覆扣款與幽靈扣款頻傳，突顯巨量即時交易系統的精準度與極端邊界測試需求。

---

## 軟體危機帶給我們的省思

* **「軟體和教堂非常相似——建成之後我們就開始祈禱。」** —— *Sam Redwine*
* 軟體問題不是單純的「寫完程式再找蟲」，而是涉及：
  * **需求與規格的清晰度與正確性**
  * **架構設計的健全性與強固性**
  * **跨團隊溝通、標準與合約遵循**
  * **完整的生命週期測試與品質保證體系**
* 軟體品質工程（SQA）的目的就是透過工程化方法將危機化為可控品質。

---

## Concept Check Question (CCQ 1)

<div class="ccq-columns">
  <div class="ccq-text">

**愛國者反導彈系統（1991）在達蘭基地攔截失效的根本軟體原因為何？**

* **A.** 通訊網路中斷導致雷達無法傳送指令給飛彈發射架
* **B.** 24-bit 時鐘暫存器的浮點捨入誤差在連續運行 100 小時後累加達 0.33 秒
* **C.** 程式碼發生記憶體洩漏（Memory Leak）導致作業系統當機
* **D.** 雷達演算法誤將美軍戰機辨識為敵方飛毛腿飛彈

  </div>
  <div class="ccq-logo">
    <img src="../img/ch01/question_icon.svg" alt="Question" />
  </div>
</div>

---

## CCQ 1 - 答案與解析

<div class="ccq-columns">
  <div class="ccq-text">

### **正確答案：B**

* **解析**：
  * **選項 B 正確**：愛國者系統採用 24-bit 浮點數記錄時間，每小時有微小的截斷誤差。連開 100 小時累積了 0.33 秒延遲，對 4.2 馬赫的飛彈造成約 600 公尺偏差。
  * **選項 A 錯誤**：雷達與發射系統通訊正常，是追蹤計算偏差。
  * **選項 C 錯誤**：系統未當機，而是內部時鐘與真實時間不同步。
  * **選項 D 錯誤**：並非目標辨識錯誤，而是目標軌跡計算位置偏移。

  </div>
  <div class="ccq-logo">
    <img src="../img/ch01/answer_icon.svg" alt="Answer" />
  </div>
</div>

---

## Concept Check Question (CCQ 2)

<div class="ccq-columns">
  <div class="ccq-text">

**NASA 火星氣候軌道探測器（1998）墜毀事件給軟體工程師最重要的啟示是？**

* **A.** 必須使用多執行緒避免運算堵塞
* **B.** 跨系統或團隊模組間的介面規格（如單位標準）必須嚴格定義與檢驗
* **C.** 太空任務軟體不得使用任何第三方函式庫
* **D.** 只要硬體推力足夠，軟體計算微小偏差不會影響軌道

  </div>
  <div class="ccq-logo">
    <img src="../img/ch01/question_icon.svg" alt="Question" />
  </div>
</div>

---

## CCQ 2 - 答案與解析

<div class="ccq-columns">
  <div class="ccq-text">

### **正確答案：B**

* **解析**：
  * **選項 B 正確**：洛克希德馬丁輸出英制單位（$\text{lbf}\cdot\text{s}$），JPL 輸入端預設公制單位（$\text{N}\cdot\text{s}$），介面契約不相容導致推力計算錯誤。
  * **選項 A 錯誤**：問題出在數值單位不相容，非執行緒排程。
  * **選項 C 錯誤**：現代軟體工程高度仰賴合約定義良好的模組化元件。
  * **選項 D 錯誤**：天體力學軌道計算對參數極度敏感，57 公里高度直接進入大氣層摩擦燒毀。

  </div>
  <div class="ccq-logo">
    <img src="../img/ch01/answer_icon.svg" alt="Answer" />
  </div>
</div>

---

## Concept Check Question (CCQ 3)

<div class="ccq-columns">
  <div class="ccq-text">

**1994 年華航名古屋空難中，飛控軟體與人機互動設計的關鍵缺失為？**

* **A.** 機載電腦中毒導致控制面板全黑
* **B.** 駕駛員手動操作與電腦重飛模式衝突時，缺乏控制權仲裁與明確狀態指示
* **C.** 飛機引擎燃料計算公式發生除以零例外
* **D.** 自動駕駛系統未實作高度感測功能

  </div>
  <div class="ccq-logo">
    <img src="../img/ch01/question_icon.svg" alt="Question" />
  </div>
</div>

---

## CCQ 3 - 答案與解析

<div class="ccq-columns">
  <div class="ccq-text">

### **正確答案：B**

* **解析**：
  * **選項 B 正確**：駕駛員在不知電腦仍處於「重飛」自動控制狀態下手動下壓機首，電腦持續推升機尾配平「糾正」駕駛員，人機爭奪控制權導致失速墜毀。
  * **選項 A 錯誤**：無電腦病毒因素。
  * **選項 C 錯誤**：非數值除零例外。
  * **選項 D 錯誤**：高度感測正常，核心問題為人機狀態可見度與控制權優先級邏輯缺陷。

  </div>
  <div class="ccq-logo">
    <img src="../img/ch01/answer_icon.svg" alt="Answer" />
  </div>
</div>

---

<!-- _class: lead -->

# **1.2 軟體品質與定義**

> 人們會忘記你做得多快，但總記得你做得多好。 —— *Howard Newton*

---

## 1.2.1 什麼是軟體？

* IEEE 對「軟體 (Software)」的廣義定義：
  > Computer **programs**, **procedures**, and possibly associated **documentation** and **data** pertaining to the operation of a computer system.
* 軟體不僅僅是可執行的程式碼（Code），更包含：
  * **程式碼 (Programs)**：指令與演算法
  * **作業程序 (Procedures)**：操作手冊、部署與維運規範
  * **文件 (Documentation)**：需求規格、架構設計、測試案例
  * **資料 (Data)**：初始化參數、設定檔、測試資料集

---

## 1.2.2 David Garvin 的五大品質觀點

| 品質觀點 | 核心意義 | 軟體領域範例 |
| :--- | :--- | :--- |
| **超自然觀點** (Transcendental) | 無法直接量化，但一體驗就能感受其精緻 | 流暢優雅的 UI/UX 與極致細節 |
| **使用者觀點** (User view) | 符合使用者需求與期望的程度 (Fitness for use) | 易用性、解決痛點的功能 |
| **製造觀點** (Manufacturing) | 符合工程規格與標準流程 (Conformance) | 符合 ISO 標準、零缺陷 (Zero Bug) |
| **產品觀點** (Product view) | 產品本身的內在技術特性與架構材質 | 高內聚低耦合、簡潔程式碼 |
| **價值觀點** (Value-based) | 顧客願意支付的價格與性價比 (ROI) | 軟體帶來的商業價值與訂閱意願 |

---

## 1.2.3 軟體品質的三層次定義

1. **符合規格需求** (*Crosby, 1979*)：
   * 符合明訂的需求規格書（Requirement Spec）。*(缺點：規格書常有遺漏)*
2. **符合顧客期望** (*Juran, 1998*)：
   * 滿足使用者與利害關係人的期望與實際需求。
3. **符合專業標準與內隱特性** (*Pressman*)：
   * 除了明訂的功能與效能，更包含專業軟體應具備的**可維護性、安全性、強固性**等內隱特質。

---

## Concept Check Question (CCQ 4)

<div class="ccq-columns">
  <div class="ccq-text">

**某系統完全符合合約規格書的每一項功能要求，但架構混亂極難維護、擴充。依 Garvin 觀點，該軟體在何種觀點下可能被判定品質不良？**

* **A.** 製造觀點（Manufacturing View）
* **B.** 產品觀點（Product View）與專業內隱品質
* **C.** 法律合約觀點
* **D.** 外包計費觀點

  </div>
  <div class="ccq-logo">
    <img src="../img/ch01/question_icon.svg" alt="Question" />
  </div>
</div>

---

## CCQ 4 - 答案與解析

<div class="ccq-columns">
  <div class="ccq-text">

### **正確答案：B**

* **解析**：
  * **選項 B 正確**：產品觀點著重於軟體內在結構特性（如模組化、架構整潔、可維護性）。雖然符合製造規格，但內在架構品質差。
  * **選項 A 錯誤**：符合規格流程在製造觀點通常算合格。
  * **選項 C/D 錯誤**：非 Garvin 的五大標準品質分類。

  </div>
  <div class="ccq-logo">
    <img src="../img/ch01/answer_icon.svg" alt="Answer" />
  </div>
</div>

---

<!-- _class: lead -->

# **1.3 品質模型 (ISO 9126)**

> 每種產品都有其品質模型，軟體亦有其六大特性。

---

## 1.3.1 ISO 9126 六大品質特性

* **功能性 (Functionality)**：正確性、合適性、互通性、合規性、安全性。
* **可靠性 (Reliability)**：成熟度 (MTBF/MTTF)、容錯度、可回復性。
* **可用性 (Usability)**：易理解性、易學習性、易操作性、吸引性。
* **效能性 (Efficiency)**：時間行為（回應時間）、資源利用率（CPU/RAM/I/O）。
* **可維護性 (Maintainability)**：易分析性、易變更性、穩定性、易測試性。
* **可移植性 (Portability)**：適應性、易安裝性、共存性、易置換性。

---

## Concept Check Question (CCQ 5)

<div class="ccq-columns">
  <div class="ccq-text">

**伺服器在網路斷線後能自動重連，且不遺失正在處理的交易資料，這屬於 ISO 9126 的哪一項品質特性？**

* **A.** 可靠性 (Reliability) 的 容錯度與可回復性
* **B.** 可移植性 (Portability) 的 易安裝性
* **C.** 可用性 (Usability) 的 吸引性
* **D.** 功能性 (Functionality) 的 合規性

  </div>
  <div class="ccq-logo">
    <img src="../img/ch01/question_icon.svg" alt="Question" />
  </div>
</div>

---

## CCQ 5 - 答案與解析

<div class="ccq-columns">
  <div class="ccq-text">

### **正確答案：A**

* **解析**：
  * **選項 A 正確**：網路斷線屬於環境異常，系統能在異常下維持運作並迅速恢復資料狀態，屬於可靠性中的容錯度 (Fault Tolerance) 與回復性 (Recoverability)。
  * **選項 B 錯誤**：可移植性指跨平台搬移的能力。
  * **選項 C 錯誤**：可用性著重於使用者操作體驗。
  * **選項 D 錯誤**：合規性指符合法律或產業標準。

  </div>
  <div class="ccq-logo">
    <img src="../img/ch01/answer_icon.svg" alt="Answer" />
  </div>
</div>

---

<!-- _class: lead -->

# **1.4 品質控制 (QC) 與 品質保證 (QA)**

---

## 1.4.1 QC vs. QA 的本質區別

* **品質控制 (Quality Control, QC)**：
  * **著重產品 (Product-oriented)**
  * 事後檢驗、抓蟲 (Defect Detection)
  * 主要活動：單元測試、系統測試、審查產品產出物
* **品質保證 (Quality Assurance, QA)**：
  * **著重流程 (Process-oriented)**
  * 事前預防、改善流程 (Defect Prevention)
  * 主要活動：訂定開發標準、Code Review 規範、CI/CD 流程、品質稽核

---

## 1.4.2 軟體品質成本 (Cost of Quality, CoQ)

```
                       ┌── 預防成本 (Prevention): 培訓、流程標準、設計審查
        ┌─ 一致性成本 ──┤
        │  (Conformance)└── 評估成本 (Appraisal): 單元測試、代碼檢視、自動化測試
品質成本 ┤
        │  (Non-       ┌── 內部失敗 (Internal Failure): 上線前修 Bug、重構
        └─ 非一致性成本 ──┤
           conformance)└── 外部失敗 (External Failure): 上線後當機、客戶客訴、賠償
```

* **1:10:100 定律**：
  * 需求階段修復 Bug 成本為 **$1**
  * 開發/測試階段修復成本為 **$10**
  * 產品上線後修復成本高達 **$100+** 且伴隨商譽損害！

---

## Concept Check Question (CCQ 6)

<div class="ccq-columns">
  <div class="ccq-text">

**導入自動化靜態程式碼分析（如 SonarQube）與工程師品質培訓課程，在品質成本 (CoQ) 分類中分別屬於？**

* **A.** 內部失敗成本、外部失敗成本
* **B.** 評估成本（Appraisal）、預防成本（Prevention）
* **C.** 外部失敗成本、評估成本
* **D.** 賠償成本、維護成本

  </div>
  <div class="ccq-logo">
    <img src="../img/ch01/question_icon.svg" alt="Question" />
  </div>
</div>

---

## CCQ 6 - 答案與解析

<div class="ccq-columns">
  <div class="ccq-text">

### **正確答案：B**

* **解析**：
  * **選項 B 正確**：靜態分析與測試屬於「評估/檢查現有品質」（Appraisal Cost）；人員教育培訓與標準制定屬於「事前預防缺陷發生」（Prevention Cost）。兩者皆屬「一致性成本」。
  * **選項 A/C/D 錯誤**：失敗成本指 Bug 已經產生後帶來的除錯與修復代價。

  </div>
  <div class="ccq-logo">
    <img src="../img/ch01/answer_icon.svg" alt="Answer" />
  </div>
</div>

---

<!-- _class: lead -->

# **本章重點回顧**

---

## 本章小結與重點

* **軟體危機**：歷史案例（愛國者、NASA、華航、迪士尼）告訴我們軟體缺陷可能致命且代價高昂。
* **軟體品質**：軟體包含程式、程序、文件與資料；Garvin 五大品質觀點多元詮釋品質。
* **ISO 9126**：六大核心品質特性（功能、可靠、可用、效能、維護、移植）。
* **QA vs. QC**：QA 重流程與預防，QC 重產品與檢驗；越早預防成本越低（1:10:100 法則）。

---

<!-- _class: lead -->

# **Q & A**

### 謝謝大家！
