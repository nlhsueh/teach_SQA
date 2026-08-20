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

# 軟體品質保證 (SQA)

### 第八章：系統測試與非功能性測試

授課教師：軟體品質保證教學團隊

---

<!-- _class: lead -->

# **8.1 使用案例測試與 BDD**

---

## 8.1 使用案例測試與 BDD

* **使用案例 (Use Case) 測試設計**
* **行為驅動開發 (BDD) 概念**
* **Cucumber 與 Gherkin 語法實踐**

---

## Concept Check Question (CCQ 1)

<div class="ccq-columns">
  <div class="ccq-text">

**針對「8.1 使用案例測試與 BDD」，下列何者敘述最為正確？**

* **A.** 測試僅需在程式碼撰寫完成後由開發者單獨執行即可
* **B.** 使用案例 (Use Case) 測試設計 是確保系統品質的重要實踐
* **C.** 品質控制 (QC) 與品質保證 (QA) 完全等價且無區別
* **D.** 達到 100% 程式碼涵蓋率代表軟體絕對無任何缺陷

  </div>
  <div class="ccq-logo">
    <img src="../../img/ch01/question_icon.svg" alt="Question" />
  </div>
</div>

---

## CCQ 1 - 答案與解析

<div class="ccq-columns">
  <div class="ccq-text">

### **正確答案：B**

* **解析**：
  * **選項 B 正確**：使用案例 (Use Case) 測試設計 確實是軟體品質保證中的核心重點。
  * **選項 A 錯誤**：測試應貫穿整個軟體生命週期，且包含獨立測試與同儕檢視。
  * **選項 C 錯誤**：QC 著重於產品檢查與缺陷發現，QA 著重於流程建立與預防。
  * **選項 D 錯誤**：高涵蓋率不代表無邏輯錯誤或規格遺漏，無法保證絕對零缺陷。

  </div>
  <div class="ccq-logo">
    <img src="../../img/ch01/answer_icon.svg" alt="Answer" />
  </div>
</div>

<!-- _class: lead -->

# **8.2 可用性與使用者體驗測試**

---

## 8.2 可用性與使用者體驗測試

* **Nielsen 10 大易用性啟發原則**
* **A/B 測試 (A/B Testing) 實驗設計**
* **可用性評估指標**

---

## Concept Check Question (CCQ 2)

<div class="ccq-columns">
  <div class="ccq-text">

**針對「8.2 可用性與使用者體驗測試」，下列何者敘述最為正確？**

* **A.** 測試僅需在程式碼撰寫完成後由開發者單獨執行即可
* **B.** Nielsen 10 大易用性啟發原則 是確保系統品質的重要實踐
* **C.** 品質控制 (QC) 與品質保證 (QA) 完全等價且無區別
* **D.** 達到 100% 程式碼涵蓋率代表軟體絕對無任何缺陷

  </div>
  <div class="ccq-logo">
    <img src="../../img/ch01/question_icon.svg" alt="Question" />
  </div>
</div>

---

## CCQ 2 - 答案與解析

<div class="ccq-columns">
  <div class="ccq-text">

### **正確答案：B**

* **解析**：
  * **選項 B 正確**：Nielsen 10 大易用性啟發原則 確實是軟體品質保證中的核心重點。
  * **選項 A 錯誤**：測試應貫穿整個軟體生命週期，且包含獨立測試與同儕檢視。
  * **選項 C 錯誤**：QC 著重於產品檢查與缺陷發現，QA 著重於流程建立與預防。
  * **選項 D 錯誤**：高涵蓋率不代表無邏輯錯誤或規格遺漏，無法保證絕對零缺陷。

  </div>
  <div class="ccq-logo">
    <img src="../../img/ch01/answer_icon.svg" alt="Answer" />
  </div>
</div>

<!-- _class: lead -->

# **8.3 效能與負載測試**

---

## 8.3 效能與負載測試

* **負載測試 (Load)、壓力測試 (Stress)、耐力測試 (Endurance)**
* **度量指標：回應時間、吞吐量 (TPS/RPS)、錯誤率**
* **虛擬使用者 (Virtual Users) 與負載模型**

---

## Concept Check Question (CCQ 3)

<div class="ccq-columns">
  <div class="ccq-text">

**針對「8.3 效能與負載測試」，下列何者敘述最為正確？**

* **A.** 測試僅需在程式碼撰寫完成後由開發者單獨執行即可
* **B.** 負載測試 (Load)、壓力測試 (Stress)、耐力測試 (Endurance) 是確保系統品質的重要實踐
* **C.** 品質控制 (QC) 與品質保證 (QA) 完全等價且無區別
* **D.** 達到 100% 程式碼涵蓋率代表軟體絕對無任何缺陷

  </div>
  <div class="ccq-logo">
    <img src="../../img/ch01/question_icon.svg" alt="Question" />
  </div>
</div>

---

## CCQ 3 - 答案與解析

<div class="ccq-columns">
  <div class="ccq-text">

### **正確答案：B**

* **解析**：
  * **選項 B 正確**：負載測試 (Load)、壓力測試 (Stress)、耐力測試 (Endurance) 確實是軟體品質保證中的核心重點。
  * **選項 A 錯誤**：測試應貫穿整個軟體生命週期，且包含獨立測試與同儕檢視。
  * **選項 C 錯誤**：QC 著重於產品檢查與缺陷發現，QA 著重於流程建立與預防。
  * **選項 D 錯誤**：高涵蓋率不代表無邏輯錯誤或規格遺漏，無法保證絕對零缺陷。

  </div>
  <div class="ccq-logo">
    <img src="../../img/ch01/answer_icon.svg" alt="Answer" />
  </div>
</div>

<!-- _class: lead -->

# **8.4 JMeter 實務與非功能性測試**

---

## 8.4 JMeter 實務與非功能性測試

* **JMeter 腳本錄製與參數化**
* **相容性、安全性與回復性測試重點**

---

## Concept Check Question (CCQ 4)

<div class="ccq-columns">
  <div class="ccq-text">

**針對「8.4 JMeter 實務與非功能性測試」，下列何者敘述最為正確？**

* **A.** 測試僅需在程式碼撰寫完成後由開發者單獨執行即可
* **B.** JMeter 腳本錄製與參數化 是確保系統品質的重要實踐
* **C.** 品質控制 (QC) 與品質保證 (QA) 完全等價且無區別
* **D.** 達到 100% 程式碼涵蓋率代表軟體絕對無任何缺陷

  </div>
  <div class="ccq-logo">
    <img src="../../img/ch01/question_icon.svg" alt="Question" />
  </div>
</div>

---

## CCQ 4 - 答案與解析

<div class="ccq-columns">
  <div class="ccq-text">

### **正確答案：B**

* **解析**：
  * **選項 B 正確**：JMeter 腳本錄製與參數化 確實是軟體品質保證中的核心重點。
  * **選項 A 錯誤**：測試應貫穿整個軟體生命週期，且包含獨立測試與同儕檢視。
  * **選項 C 錯誤**：QC 著重於產品檢查與缺陷發現，QA 著重於流程建立與預防。
  * **選項 D 錯誤**：高涵蓋率不代表無邏輯錯誤或規格遺漏，無法保證絕對零缺陷。

  </div>
  <div class="ccq-logo">
    <img src="../../img/ch01/answer_icon.svg" alt="Answer" />
  </div>
</div>

---

<!-- _class: lead -->

# **本章重點回顧**

---

## 本章小結與重點

* 掌握 **第八章：系統測試與非功能性測試** 的核心概念與實務手法。
* 熟悉各項 SQA 技術在軟體生命週期中的應用時機與效益。
* 課後請完成隨堂練習與 Lab 實作，以深化觀念。

---

<!-- _class: lead -->

# **Q & A**

### 謝謝大家！
