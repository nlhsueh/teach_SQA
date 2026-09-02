# Repository Guidelines & Agent Instructions

## 📚 專案概覽 (Project Overview)
本專案為 **gTeach SQA（軟體品質保證 Software Quality Assurance）** 課程教材與實習講義專案，包含各章節 Markdown 講義 (`Lecture/source/`)、自動編譯生成的講義 PDF (`Lecture/pdf/`)、實習文件 (`LabDemo/`) 與相關輔助工具腳本 (`scripts/`)。

---

## 🛠️ 講義與 PDF 生成工作流程 (Lecture & PDF Generation Workflow)

### 1. 講義撰寫與排版規範 (`Lecture/source/*.md`)
* **語言風格**：使用流暢、專業且親切的繁體中文（台灣軟體工程用語，如：函式、變數、類別、物件、重構、缺陷）。
* **教學結構**：
  * **金句與寓言**：章節開頭引用大師名言或生動寓言（如溫伯格寓言）。
  * **圖文並茂**：架構圖、狀態機、矩陣圖與流程圖置於 `img/` 目錄中，並附帶詳細「圖形解說」。
  * **程式碼對照**：提供良好（✅）與劣質（❌）代碼對照範例。
  * **概念核對問答 (CCQ)**：每個關鍵知識點附上 1~2 題多選或是非題，使用 `<details><summary>` 摺疊答案與解析。
  * **跨章節呼應**：例如 Ch02（開發者個人 Clean Code 防錯心法）與 Ch04（團隊靜態檢視 Code Review / Code Smells 查核標準）互相引用連結。

### 2. 互動題目 (CCQ / Interactive Activities) 規範與生命週期
* **標題前綴規範**：所有的互動題目一律使用 `🙋` 作為標題前綴 Emoji（例如：`#### 🙋 **概念核對問答 (CCQ N)**`、`#### 🙋 **文字雲互動：...**`、`#### 🙋 **投票互動：...**`、`#### 🙋 **排序互動：...**`、`#### 🙋 **簡答互動：...**`）。
* **題目產生原則（專注內容，不自動觸發同步）**：
  * 當使用者要求新增、插入或修改互動題目時，**先產生題目文字、選項與解析即可**，保持草稿狀態。
  * **❌ 切勿自動啟動同步**：不要主動執行 `sync_iActivity.py` 或與 nickedupocket 溝通產生 QR Code（避免耗費時間與產生題號衝突/覆蓋錯誤）。
* **同步標準程序（僅在使用者明確要求時執行）**：
  * **階段 1 (草稿狀態)**：撰寫新題目時不放 `[課堂互動]` 連結或 QR Code。
  * **階段 2 (使用者指令觸發同步)**：當使用者明確要求同步題庫時，才執行 `python3 scripts/sync_iActivity.py --course gTeachSQA`，自動完成：
    1. 解析題目並指派/保留 `<!-- id: sqa-chXX-... -->` 註解。
    2. 匯出題庫至 `nickedupocket/public/courses/gTeachSQA.md`，若內容有變更將**自動執行 Git Commit & Push** 同步至遠端。
    3. 產生 QR Code 圖片至 `img/chXX/`（供投影片使用）。
    4. 自動將 `<!-- id: ... -->` 與 `[課堂互動]` 連結回寫講義（講義不放 QR Code），投影片則同時嵌入 `[課堂互動]` 連結與 QR Code 圖片。
  * **階段 3 (題目修改重同步)**：若後續修改題目文字或選項，保留原本的 `<!-- id: ... -->` 註解，再次依使用者指令執行 `sync_iActivity.py` 即可冪等更新。
  * **階段 4 (編譯 PDF)**：同步完成後再編譯 Lecture PDF 或 Slide PDF。

### 3. PDF 生成工具與指令
* **產生腳本**：[`scripts/generate_lecture_pdf.js`](scripts/generate_lecture_pdf.js)
* **執行指令**：
  ```bash
  node scripts/generate_lecture_pdf.js Lecture/source/ch02_bug.md
  ```

* **PDF 輸出位置**：自動存於 `Lecture/pdf/<filename>.pdf`。
* **特性**：
  * 支援 Headless Chrome 轉譯、KaTeX 數學式排版、Highlight.js 程式碼高亮。
  * 頁尾自動生成中英文章節標題、頁碼（如 `Ch 02 · 錯與除錯 (Bugs, Faults, and Debugging)  1 / 19`）。
  * 自動調用 [`scripts/add_pdf_links.py`](scripts/add_pdf_links.py) 注入可點擊超連結。

### 3. 圖片路徑規範
* Markdown 中的圖片請統一使用標準格式：
  ```html
  <img src="../../img/chXX/image_name.png" width="650">
  ```
  或
  ```markdown
  ![Description](../../img/chXX/image_name.png)
  ```
* 避免在 `src = ` 等號周圍留下非標準空格。

---

## 📖 章節主題架構 (Curriculum Architecture)
* **Ch01**: 軟體品質導論 (Software Quality Concepts & Garvin 5 Views)
* **Ch02**: 錯與除錯 (Bugs, Clean Code, Debugging, DbC, BTS)
* **Ch03**: 軟體測試基礎 (Testing Foundations & Principles)
* **Ch04**: 軟體檢視 (Static Code Inspection, Code Smells, OWASP Top 10, PMD)
* **Ch05**: 黑箱測試 (Black-Box Testing: EP, BVA, Decision Table)
* **Ch06**: 白箱測試 (White-Box Testing: Statement, Branch, MC/DC)
* **Ch07**: 整合測試 (Integration Testing & Stubs/Mocks)
* **Ch08**: 系統與驗收測試 (System & Acceptance Testing)
