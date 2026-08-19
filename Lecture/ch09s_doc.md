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

### 第九章：測試文件與標準

授課教師：軟體品質保證教學團隊

---

<!-- _class: lead -->

# **9.1 IEEE 829 測試文件標準**

---

## 9.1 IEEE 829 測試文件標準

* **IEEE 829 標準架構概覽**
* **測試階段與文件對應關係**
* **敏捷開發中的測試文件取捨**

---

## Concept Check Question (CCQ 1)

<div class="ccq-columns">
  <div class="ccq-text">

**針對「9.1 IEEE 829 測試文件標準」，下列何者敘述最為正確？**

* **A.** 測試僅需在程式碼撰寫完成後由開發者單獨執行即可
* **B.** IEEE 829 標準架構概覽 是確保系統品質的重要實踐
* **C.** 品質控制 (QC) 與品質保證 (QA) 完全等價且無區別
* **D.** 達到 100% 程式碼涵蓋率代表軟體絕對無任何缺陷

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
  * **選項 B 正確**：IEEE 829 標準架構概覽 確實是軟體品質保證中的核心重點。
  * **選項 A 錯誤**：測試應貫穿整個軟體生命週期，且包含獨立測試與同儕檢視。
  * **選項 C 錯誤**：QC 著重於產品檢查與缺陷發現，QA 著重於流程建立與預防。
  * **選項 D 錯誤**：高涵蓋率不代表無邏輯錯誤或規格遺漏，無法保證絕對零缺陷。

  </div>
  <div class="ccq-logo">
    <img src="../img/ch01/answer_icon.svg" alt="Answer" />
  </div>
</div>

<!-- _class: lead -->

# **9.2 測試核心文件規格**

---

## 9.2 測試核心文件規格

* **測試計畫書 (Test Plan)**
* **測試設計規格書 (Test Design Spec)**
* **測試案例規格書 (Test Case Spec)**
* **測試程序規格書 (Test Procedure Spec)**

---

## Concept Check Question (CCQ 2)

<div class="ccq-columns">
  <div class="ccq-text">

**針對「9.2 測試核心文件規格」，下列何者敘述最為正確？**

* **A.** 測試僅需在程式碼撰寫完成後由開發者單獨執行即可
* **B.** 測試計畫書 (Test Plan) 是確保系統品質的重要實踐
* **C.** 品質控制 (QC) 與品質保證 (QA) 完全等價且無區別
* **D.** 達到 100% 程式碼涵蓋率代表軟體絕對無任何缺陷

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
  * **選項 B 正確**：測試計畫書 (Test Plan) 確實是軟體品質保證中的核心重點。
  * **選項 A 錯誤**：測試應貫穿整個軟體生命週期，且包含獨立測試與同儕檢視。
  * **選項 C 錯誤**：QC 著重於產品檢查與缺陷發現，QA 著重於流程建立與預防。
  * **選項 D 錯誤**：高涵蓋率不代表無邏輯錯誤或規格遺漏，無法保證絕對零缺陷。

  </div>
  <div class="ccq-logo">
    <img src="../img/ch01/answer_icon.svg" alt="Answer" />
  </div>
</div>

<!-- _class: lead -->

# **9.3 測試執行與總結報告**

---

## 9.3 測試執行與總結報告

* **測試日誌 (Test Log) 與 缺陷報告 (Incident Report)**
* **測試總結報告 (Test Summary Report)**
* **品質指標評估與發布準則**

---

## Concept Check Question (CCQ 3)

<div class="ccq-columns">
  <div class="ccq-text">

**針對「9.3 測試執行與總結報告」，下列何者敘述最為正確？**

* **A.** 測試僅需在程式碼撰寫完成後由開發者單獨執行即可
* **B.** 測試日誌 (Test Log) 與 缺陷報告 (Incident Report) 是確保系統品質的重要實踐
* **C.** 品質控制 (QC) 與品質保證 (QA) 完全等價且無區別
* **D.** 達到 100% 程式碼涵蓋率代表軟體絕對無任何缺陷

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
  * **選項 B 正確**：測試日誌 (Test Log) 與 缺陷報告 (Incident Report) 確實是軟體品質保證中的核心重點。
  * **選項 A 錯誤**：測試應貫穿整個軟體生命週期，且包含獨立測試與同儕檢視。
  * **選項 C 錯誤**：QC 著重於產品檢查與缺陷發現，QA 著重於流程建立與預防。
  * **選項 D 錯誤**：高涵蓋率不代表無邏輯錯誤或規格遺漏，無法保證絕對零缺陷。

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

* 掌握 **第九章：測試文件與標準** 的核心概念與實務手法。
* 熟悉各項 SQA 技術在軟體生命週期中的應用時機與效益。
* 課後請完成隨堂練習與 Lab 實作，以深化觀念。

---

<!-- _class: lead -->

# **Q & A**

### 謝謝大家！
