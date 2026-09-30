# Google Antigravity IDE 介紹與 Java Maven 開發實務指南

介紹 Google 所推出的 AI 原生整合開發環境 —— **Antigravity IDE**，涵蓋其誕生背景與核心特色、跨平台（macOS / Windows）安裝步驟、Java 21 與 Maven 專案環境設定，以及常見的疑難排解與 AI 提問技巧。

---

## 1. 什麼是 Google Antigravity？

### 1.1 誕生背景：從「AI 輔助寫 code」進化至「Agentic 代理人協作」
近年程式碼輔助工具（如 GitHub Copilot）大多停留在「被動程式碼補全」或「側邊欄問答」的模式，開發者仍需頻繁手動複製貼上、在終端機輸入編譯與測試指令、手動排查 StackTrace 錯誤。

**Google Antigravity** 是由 Google DeepMind 與 Core Engineering 團隊聯手打造的 **AI-First 原生軟體開發平台**。它的設計理念並非單純在編輯器中塞入聊天機器人，而是將 **Agentic AI（代理人 AI）** 深度整合至整個軟體開發生命週期中：
* **具備環境感測能力**：AI 不只閱讀當前檔案，能全局索引工作區程式碼、解析 `pom.xml` 依賴結構、即時讀取終端機輸出與編譯報錯。
* **具備自主操作工具能力**：AI 代理人可由您授權自主讀寫檔案、執行 Shell/Terminal 指令（如 `mvn test`、`git commit`）、自動呼叫瀏覽器進行端對端驗證。
* **透明可控的計畫模式（Planning Mode）**：對於大型任務，AI 會先生成詳細的實作計畫（Implementation Plan）讓開發者審查核准，再一步步自主執行並驗證。

### 1.2 產品生態架構
Antigravity 平台提供多種運作型態：
1. **Antigravity IDE**：以大家熟悉的開源 **VS Code** 為基底所打造的獨立桌面 IDE。不僅完全相容廣大的 VS Code 擴充套件生態系（如 Java Extension Pack、GitLens 等），更直接在編輯器核心內嵌強大的 Gemini/DeepMind 模型推論引擎與 Agentic 側邊面板。
2. **Antigravity 2.0 / 桌面應用**：提供獨立的 Agent 畫布、子代理（Subagents）管理與多任務並行監控介面。
3. **Antigravity CLI (`agy`)**：提供命令列互動工具，可直接在遠端伺服器或本機 Shell 觸發代理人工作流程。

### 1.3 三大 AI 核心交互模式

| 交互模式　　　　　　　　　　　　　　　　　　 | 啟動方式　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　| 特色與適用情境　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　|
| :---------------------------------------------| :----------------------------------------------------------------------------| :----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **被動預測 (Passive)<br>Antigravity Tab**　　| `Tab` / `→`　　　　　　　　　　　　　　　　　　　　　　　 | **次意圖即時預測補全**：不只補全目前行，能一次生成跨行修改、預測下一個游標跳轉位置（Tab to Jump），或在新增類別時自動引入 Import（Tab to Import）。　　　　　　 |
| **指引行內 (Instructive)<br>Inline Command** | `Cmd + I` (Mac)<br>`Ctrl + I` (Win) | **區域化程式碼重構與生成**：框選程式碼區塊後呼叫，可針對選取範圍進行原地優化、解說、加註解或修正邏輯，不干擾其他程式碼。　　　　　　　　　　　　　　　　　　　　　|
| **代理人協作 (Collaborative)<br>Agent Mode** | 側邊欄對話面板　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　| **全自動配對編程夥伴**：處理複合型任務。例如「請幫我寫出 `GCD` 的單元測試並用 `mvn test` 驗證，若有錯誤請自動修好」，Agent 會自主規畫、寫檔、下指令執行並回報。 |

---

## 2. Antigravity IDE 安裝指引 (macOS & Windows)

### 2.1 下載與安裝

1. 前往 Antigravity 官方網站下載對應您作業系統的安裝程式：
   * 官方網站：[https://antigravity.google](https://antigravity.google)
2. **安裝步驟**：
   * **🔹 macOS**：
     1. 下載對應晶片架構的 `.dmg` 檔案（Apple Silicon M 系列選 `ARM64`；Intel 晶片選 `x64`）。
     2. 開啟 `.dmg` 檔，將 **Antigravity IDE** 拖曳至 `Applications`（應用程式）資料夾。
     3. 首次開啟若遇 macOS 安全提示，請至「系統設定」->「隱私權與安全性」點選「仍要打開」。
   * **🔹 Windows**：
     1. 下載 Windows 安裝檔（`.exe`）。
     2. 依安裝精靈指示逐步安裝，建議勾選「將 Antigravity 加入 PATH」與「以 Antigravity 開啟資料夾」。
3. **首次啟動與登入**：
   * 開啟 Antigravity IDE 後，右下角或歡迎畫面會提示登入 Google 帳戶以啟用 Gemini 模型服務。

---

## 3. Antigravity Agent 與核心設定解析

與傳統 AI 僅具備聊天視窗不同，Antigravity 將 AI 提升為具備完整專案理解能力、工具調用權限與工作流程規範的 **Agent（自主代理人）**。以下是其核心概念與設定說明：

### 3.1 Agent 運作機制與模式

1. **規劃模式（Planning Mode）**：
   * 當您要求進行架構變更、大型除錯或複合功能開發時，Agent 不會盲目修改程式碼。
   * 它會先進入規劃模式，產出一份技術規格書 —— **實作計畫（`implementation_plan.md`）**，列出即將修改的檔案、函式、架構決策與驗證清單。
   * 系統會提供 **`Proceed`** 按鈕，唯有在您審閱並核准後，Agent 才會開始動手修改程式碼。
2. **工具調用與執行權限（Tool Execution & Permissions）**：
   * **檔案讀寫**：精準進行單區塊或多區塊程式碼修補，自動維護程式碼註解與格式。
   * **終端機指令（Terminal Tools）**：能主動執行 `mvn compile`、`mvn test`、`git status` 等指令，並即時由終端機輸出判斷成功與否，若有報錯則自動自我修復。
   * **瀏覽器子代理（Browser Subagent）**：具備直接開啟無頭瀏覽器（Headless Chrome）進行 UI 互動、擷取螢幕截圖與 E2E 測試驗收的能力。
3. **子代理與背景任務（Subagents & Background Tasks）**：
   * 當遇到龐大耗時的任務（例如全專案靜態分析、長時間執行的壓力測試），主 Agent 可分裂出「子代理人（Subagents）」並行處理，完成後彙報結論，不阻礙當前對話。
4. **工作產出成果物（Artifacts）**：
   * Agent 會在專屬的工作區中自動產生結構化的 Markdown 報告（如執行驗收報告 `walkthrough.md`、架構圖表 Mermaid 等），供開發者後續審視。

---

### 3.2 Agent 安全模式與沙盒防護（Secure Mode & Sandbox Isolation）

由於 Antigravity Agent 具備自主呼叫終端機（Terminal）與讀寫檔案的強大權限，為了保障開發者電腦的資訊安全並防範惡意程式碼注入（Prompt Injection），Antigravity 內建了企業級的**安全沙盒（Terminal Sandbox）與權限隔離機制**：

#### 1. 標準沙盒隔離模式（Standard Sandbox Mode，預設開啟）
在預設狀態下，Agent 執行的所有終端機命令與檔案操作皆被限制在安全沙盒環境中：
* **工作區權限限制（Workspace Boundary）**：Agent 僅能在您所開啟的專案目錄（如 `LabDemo`）內進行讀寫，**嚴禁且無法存取工作區以外的作業系統敏感路徑**（例如 `~/.ssh` 私鑰、`~/.aws` 雲端憑證、`/etc/` 系統設定檔或其它個人目錄）。
* **隔離外網連線（Network Isolation）**：沙盒預設阻斷非必要的對外連線，避免惡意提示詞誘使 AI 將專案程式碼或環境變數外洩至不知名的外部伺服器。
* **自動安全放行**：在安全沙盒內部的無害命令（如 `git status`、檔案檢視、本機單元測試），Agent 可自動執行並快速回報，無需開發者繁瑣點擊允許。

#### 2. 沙盒旁路模式（Bypass Sandbox Mode / 提權手動核准）
當 Agent 需要執行超出沙盒隔離範圍的操作時（例如：首次下載專案需要的 Maven 外部依賴套件、`git push` 同步至遠端 GitHub、部署服務至雲端平台）：
* **觸發人工授權（User Approval Required）**：系統會自動切換為 Bypass Sandbox 請求，並在 IDE 介面彈出明確的核准視窗，列出指令全文。
* **人工把關放行**：唯有在您親自檢視該指令並點擊「允許（Allow）」後，該指令才會在主機環境執行，確保任何具有外網傳輸或跨目錄存取特性的動作皆受到人類開發者的實質監督。

#### 3. 指令自動核准設定（Auto-Approval & Always Allow）
為了避免頻繁核准打斷開發心流：
* 當您在授權彈窗勾選 **「Always Allow（一律允許）」** 時，Antigravity 會記錄該指令的**前綴規則（Prefix-Matching）**（例如自動放行 `mvn compile` 或 `git status`）。
* 未來遇到相同前綴的日常建置操作，便能兼顧安全性與極致的配對編程流暢度。

---

### 3.3 Antigravity 客製化架構（Customization System）

Antigravity 允許團隊將「軟體品質規範」直接寫入專案中，讓 Agent 在協作時完全遵守團隊的 Coding Style 與流程限制：

```text
專案根目錄/
  ├── AGENTS.md             # 專案頂層行為準則 (Rules)
  ├── .agents/
  │    ├── rules/           # 細部領域規範 (如 coding-style.md, testing-rules.md)
  │    ├── skills/          # 自訂工作流程技能 (如 git-workflow/SKILL.md)
  │    ├── hooks.json       # 生命週期自動化腳本
  │    └── mcp_config.json  # 外部工具擴充 (Model Context Protocol)
```

| 客製化組件 | 設定路徑 / 檔案 | 適用情境與意義 |
| :--- | :--- | :--- |
| **Rules (行為準則)** | `AGENTS.md`<br>`GEMINI.md`<br>`.agents/rules/*.md` | **為 Agent 建立絕對紅線與原則**：<br>例如規定「所有回覆一律使用台灣繁體中文」、「嚴格使用 JUnit 5 撰寫單元測試」、「未經同意不可自行執行 Commit」等，Agent 在每一次對話都會優先嚴格遵守。 |
| **Skills (流程技能)** | `.agents/skills/<name>/SKILL.md`<br>`~/.gemini/config/skills/` | **教導 Agent 專屬的操作手冊 (Runbooks)**：<br>例如教 Agent 如何跑特定 Maven 外掛、如何排查 Cucumber 報錯。Skills 採用**漸進式揭露（Progressive Disclosure）**，平常不佔用 context 記憶體，需要時才動態啟動。 |
| **MCP Servers** | `mcp_config.json` | **外部工具整合（Model Context Protocol）**：<br>讓 Agent 連線外部系統（例如本機資料庫、GitHub API、公司內部專屬 API 服務）。 |
| **Hooks** | `hooks.json` | **生命週期鉤子**：<br>在 Agent 執行工具前後自動觸發（例如修改程式碼後自動觸發格式化工具）。 |

---

### 3.4 常用斜線指令 (Slash Commands)

在對話輸入框中鍵入 `/` 即可叫出快捷專用指令：

* **`/goal`**：進入「直到達成目標前不停止」的深度迭代模式，適合讓 Agent 自主除錯複雜的測試直到綠燈。
* **`/schedule`**：設定定時提醒或背景輪詢排程（例如定時監控 CI 建置狀態）。
* **`/grill-me`**：啟動「面談質詢模式」，在動手實作前讓 Agent 透過互動式多選題對焦架構設計決策。
* **`/learn`**：讓 Agent 記憶您當前給予的修正或特定開發偏好，儲存至未來的客製化知識中。

---

### 3.5 模型選擇（Model Selection）

在 IDE 頂端或狀態列可切換底層 Gemini 思考模型：
* **Gemini 1.5 Pro / Thinking (High Reasoning)**：具備極強的深層邏輯推演與跨檔案關聯分析能力，適合複雜重構、系統架構設計與困難除錯。
* **Gemini 1.5 Flash (Medium / Fast)**：回應極其迅速，適合一般的行內補全（Tab Autocomplete）、單行註解生成或輕量問答。

---

## 4. 在 Antigravity 上開發 Java + Maven 專案所需環境

因為 Antigravity IDE 底層基於 VS Code 架構，若要編譯、除錯與執行 Java Maven 專案（如本課程的 `LabDemo`），需要完成 **本機基礎工具（JDK & Maven）** 與 **IDE 延伸模組（Extensions）** 的安裝：

### 4.1 步驟一：安裝本機底層環境（JDK 21 & Maven 3.x）

本專案指定使用 **Java 21 (LTS)** 與 **Maven 3.x**：

#### 🔹 macOS 系統安裝 (建議使用 Homebrew)
開啟終端機（Terminal）執行以下指令：
```bash
# 1. 安裝 OpenJDK 21 與 Maven
brew install openjdk@21 maven

# 2. 設定環境變數 (以預設 zsh 為例)
echo 'export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc
echo 'export JAVA_HOME="/opt/homebrew/opt/openjdk@21"' >> ~/.zshrc
source ~/.zshrc

# 3. 驗證安裝結果
java -version    # 應顯示 openjdk version "21.x.x"
mvn -v           # 應顯示 Apache Maven 3.x.x
```

#### 🔹 Windows 系統安裝 (建議使用 PowerShell Winget)
以系統管理員身分開啟 PowerShell，執行以下指令：
```powershell
# 1. 安裝 Eclipse Temurin JDK 21 與 Apache Maven
winget install Eclipse.Temurin.JDK.21
winget install Apache.Maven

# 2. 驗證安裝（請重啟 PowerShell 視窗以套用 PATH）
java -version
mvn -v
```
*(若指令無效，請手動確認 Windows「系統屬性」->「環境變數」中是否已建立 `JAVA_HOME` 並將 Maven 的 `bin` 目錄加入 `Path`。)*

---

### 4.2 步驟二：在 Antigravity IDE 內安裝 Java 延伸模組

Antigravity IDE 能夠直接連線 VS Code 擴充套件市集：

1. 點擊 IDE 左側活動列的 **Extensions**（延伸模組）圖示（快捷鍵 `Cmd + Shift + X` / `Ctrl + Shift + X`）。
2. 在搜尋框輸入：**`Extension Pack for Java`**（由 Microsoft 發行）。
3. 點選 **Install** 安裝。該套件包會自動為您安裝以下核心組件：
   * **Language Support for Java™ by Red Hat**：提供 Java 語法分析、智慧提示、AST 樹與程式碼導航。
   * **Debugger for Java**：提供可視化除錯器（支援中斷點、變數監控、Call Stack）。
   * **Test Runner for Java**：整合 JUnit 4/5 測試執行面板。
   * **Maven for Java**：整合 Maven 生命週期（Lifecycle）與 `pom.xml` 管理。
   * **Project Manager for Java**：Java 專案依賴管理。

> 💡 **更輕鬆的做法：直接透過 AI 互動視窗請 AI 協助安裝**  
> 如果您不想手動在介面中搜尋與點選，您可以直接在右側 **Agent 對話面板** 輸入提示詞：  
> > *「請幫我安裝 Java 開發所需的延伸模組，並檢查本機環境是否具備 JDK 21 與 Maven。」*  
> 
> Antigravity Agent 會自動為您執行安裝指令（例如調用 `code --install-extension vscjava.vscode-java-pack`），並自主在終端機執行 `java -version` 與 `mvn -v` 檢測環境；若發現本機缺少 JDK 或 Maven，還會主動提供一鍵安裝指令或引導完成設定，大幅降低環境建置的門檻！

---

### 4.3 步驟三：建立全新 Java Maven 專案（HelloWorld 實作）

若您想建立一個全新的 Java 專案，Antigravity IDE 提供了極其便捷的可視化精靈：

#### 1. 透過指令面板建立 Maven 專案
1. 按下快捷鍵 `Cmd + Shift + P`（Windows 為 `Ctrl + Shift + P`）叫出命令面板。
2. 輸入並選取：**`Java: Create Java Project...`**。
3. 在專案類型中選擇 **`Maven`** -> **`maven-archetype-quickstart`**（或是選擇 `No build tools` 建立輕量專案）。
4. 選擇原型版本（預設即可），接著在下方終端機依提示確認：
   * **`groupId`**：例如 `com.example`（公司或組織名稱反寫）
   * **`artifactId`**：例如 `my-first-app`（專案名稱）
   * **`version`**：直接按 Enter 使用預設 `1.0-SNAPSHOT`
5. 選擇本機存放資料夾，IDE 就會自動產生標準 Maven 目錄結構並提示開啟新視窗。

#### 2. 編寫第一個 HelloWorld 程式
在產生的目錄中，開啟 `src/main/java/com/example/App.java`（或手動新增 `HelloWorld.java`）：

```java
package com.example;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello, Antigravity & Java Maven!");
    }
}
```

#### 3. 執行與驗證
1. **點擊執行**：在 `main` 函式上方會出現灰色小字 **`Run | Debug`**，直接點擊 **`Run`**。
2. **終端機輸出**：下方終端面板將立即編譯並印出：
   ```text
   Hello, Antigravity & Java Maven!
   ```
3. 恭喜！您已成功在 Antigravity IDE 中建立並運行第一個 Java Maven 專案。

---

### 4.4 步驟四：引用本課程教材範例

如果您是要練習本課程現有的實習專案（如 `LabDemo`）：

#### 1. 開啟專案資料夾
在 Antigravity IDE 頂端選單點擊 **File -> Open Folder...**（或快捷鍵 `Cmd + O` / `Ctrl + O`）：
* ⚠️ **請務必「只開啟 `LabDemo`」資料夾**，而**不要**開啟最外層的 `gTeachSQA` 資料夾！
* **原因**：Java Language Server 與 Maven 外掛是以「開啟的資料夾根目錄」作為專案基準點。IDE 必須在根目錄直接看到 `pom.xml`，才能正確解析相依套件庫（Dependencies）、配置 Source Folders（`src/main/java`、`src/test/java`）與建置類別路徑（Classpath）。若開啟外層目錄，IDE 會將其視為純文字或一般資料夾，導致程式碼無法自動編譯、語法提示失效或在執行時報出 `ClassNotFoundException`。

#### 2. 觀察專案載入狀態
* 首次開啟 `LabDemo` 後，右下角狀態列會顯示 `Opening Java Projects...` 與 `Importing Maven projects...`。
* 請稍候 5~10 秒，待背景索引與下載依賴套件完成後，左側側邊欄的 **JAVA PROJECTS** 與 **MAVEN** 面板便會正確列出所有模組與依賴庫。


---

## 5. 常見問題與疑難排除 (FAQ & Troubleshooting)

### Q1: 啟動除錯時報錯 `ClassNotFoundException: u01_debug.GCD` 或 `BubbleSort`？
* **問題原因**：IDE 尚未編譯出對應的 `.class` 檔，或其類別路徑（Classpath）設定未抓到 `target/classes`。
* **排查與修復步驟**：
  1. 打開終端機手動編譯一次：
     ```bash
     mvn clean compile
     ```
  2. 檢查 `target/classes/u01_debug/` 下是否存在對應的 `.class` 檔。
  3. 按 `Cmd + Shift + P`（Windows 為 `Ctrl + Shift + P`）叫出指令面板，輸入並執行：
     ```text
     Java: Clean Java Language Server Workspace -> Restart and delete
     ```
     強制清理快取並重啟 Java 伺服器即可恢復正常。

---

### Q2: 專案放在 Google Drive / OneDrive 導致同步卡頓或 IDE 異常？
* **問題原因**：Maven 編譯與測試時會在 `target/` 目錄中產生成千上萬個 `.class` 暫存檔與報告，觸發雲端硬碟頻繁同步導致磁碟 I/O 卡死。
* **最佳實務解法（符號連結 Symlink）**：
  保持 `pom.xml` 的預設 `target` 路徑不變，直接將 `target` 資料夾軟連結到系統暫存區：
  ```bash
  # 在專案目錄 (LabDemo) 下執行：
  rm -rf target
  mkdir -p /tmp/maven-builds/LabDemo/target
  ln -s /tmp/maven-builds/LabDemo/target target
  ```
  雲端硬碟只會辨識到一個數十 bytes 的連結檔，而不會同步裡面的暫存檔，既能避開卡頓，又能確保 IDE 與 Maven 的路徑解析完全相容！

---

### Q3: 出現 `release version 21 not supported` 或版本衝突？
* **問題原因**：本機安裝的 JDK 版本低於專案要求的 Java 21，或 IDE 預設的 Java Runtime 抓錯路徑。
* **解法**：
  1. 確認終端機 `java -version` 為 21。
  2. 開啟 Antigravity IDE 設定（`Cmd + ,` 或 `Ctrl + ,`），搜尋 `java.configuration.runtimes`，確認 JDK 21 已設為預設運作環境。

---

## 6. Antigravity AI 助理實用咒語與技巧（Prompts 範例）

在右側對話面板直接向 Antigravity Agent 下達指令：

1. **協助除錯與分析錯誤**：
   > 「我在執行 `u01_debug/GCD.java` 時出現錯誤，請幫我檢查程式邏輯並使用終端機執行驗證。」
2. **撰寫單元測試**：
   > 「請針對 `BubbleSort.java` 撰寫包含等價劃分與邊界值測試的 JUnit 5 測試案例，放在 `src/test/java/` 對應路徑中，並直接下指令確認測試全部通過。」
3. **優化與重構程式碼**：
   > 「請分析當前開啟的類別是否有潛在的 Code Smells，並依照 Clean Code 原則進行重構，保留所有原有功能的正確性。」

---

## 課堂互動與概念檢核

<!-- id: sqa-u01-antigravity-ccq1 -->
#### 🙋 **概念核對問答 (CCQ 1)：Agentic AI 與傳統 Copilot 的核心本質區別**





**問題**

傳統的程式碼輔助工具（如早期 GitHub Copilot）與 Google Antigravity 的「Agentic 代理人協作模式」相比，後者最關鍵的架構突破為何？

A) 代理人模式能將程式碼直接轉換為機器碼以提升 CPU 執行效率  
B) 具備環境感測能力（全局索引專案、讀取編譯與測試日誌）與自主工具調用能力（檔案精準讀寫、執行終端機指令、形成自動修復閉環）  
C) 代理人模式完全不需要人類工程師參與或下達 Prompt，就會自主開發完成系統並發布上線  
D) 代理人模式只能在雲端伺服器運作，無法在本機 IDE 編輯器中執行  

<details>
<summary>點擊查看【概念核對問答】答案與解析</summary>

**正確答案：B**

* **解析**：
  * **選項 B 正確**：傳統 Copilot 多停留在「被動程式碼補全」或「聊天室問答」，開發者仍須手動複製貼上與編譯排查；而 Agentic AI 具備感知工作區狀態（讀取 `pom.xml`、終端機報錯）、自主操作工具（檔案修改、執行 `mvn test`）的能力，能形成「修改 ➔ 測試 ➔ 偵錯 ➔ 再驗證」的主動自主閉環。
  * **選項 A 錯誤**：編譯為機器碼是 JVM / JIT 編譯器的職責，非 AI 模型的功能。
  * **選項 C 錯誤**：AI 代理人仍需人類工程師提供需求目標，且重大操作需人類審查核准（Human-in-the-Loop）。
  * **選項 D 錯誤**：Antigravity IDE 為整合於本機桌面的原生開發環境。

</details>

---

[課堂互動](https://nlhsueh.github.io/nickedupocket/#/student/sqa-u01-antigravity-ccq1)

<!-- id: sqa-u01-antigravity-ccq2 -->
#### 🙋 **概念核對問答 (CCQ 2)：三大 AI 互動模式之情境選用**





**問題**

工程師正在檢視 `GCD.java`，發現其中一個輔助函式邏輯巢狀太深。他只想針對「選取的這 10 行程式碼」進行原地重構與加入 JavaDoc 說明，不想改動或干擾工作區的其他任何檔案。請問下列哪一種互動模式最迅速且最合適？

A) 啟動 Planning Mode 生成全局架構實作計畫書  
B) 使用 Inline Command 行內指引模式（按下 `Cmd + I` / `Ctrl + I`）  
C) 呼叫 Browser Subagent 開啟無頭瀏覽器  
D) 切換至全域終端機執行 `agy` 命令列背景排程  

<details>
<summary>點擊查看【概念核對問答】答案與解析</summary>

**正確答案：B**

* **解析**：
  * **選項 B 正確**：**Inline Command（`Cmd + I` / `Ctrl + I`）** 專門用於「局部程式碼修改與重構」，它直接針對游標選取的區域進行原地優化、解說或修正，輕量迅速且完全不影響檔案外的其他邏輯。
  * **選項 A 錯誤**：Planning Mode 適合跨檔案、多步驟或具有架構影響的複合型任務，局部修改使用它會顯得過於繁瑣。
  * **選項 C/D 錯誤**：Browser Subagent 用於 Web E2E 介面測試驗收，非編輯器內重構工具。

</details>

---

[課堂互動](https://nlhsueh.github.io/nickedupocket/#/student/sqa-u01-antigravity-ccq2)

<!-- id: sqa-u01-antigravity-ccq3 -->
#### 🙋 **概念核對問答 (CCQ 3)：安全沙盒與指令執行審查**





**問題**

在 Antigravity 預設的「標準沙盒隔離模式（Standard Sandbox Mode）」下，當 Agent 為了修復 Bug 而嘗試在終端機執行可能影響系統環境或高風險的指令時，系統會如何處理？

A) 為了追求最高自主效率，IDE 會一律自動靜默執行，不通知使用者  
B) 系統會直接強制關閉 IDE 並鎖死作業系統  
C) 指令會被安全攔截並彈出審查提示，清楚呈現即將執行的完整指令，必須由開發者手動點擊核准（Approve）後方可執行  
D) 沙盒模式下嚴禁執行任何終端機指令，即使是 `git status` 或 `mvn compile` 等唯讀指令也會被永久阻斷  

<details>
<summary>點擊查看【概念核對問答】答案與解析</summary>

**正確答案：C**

* **解析**：
  * **選項 C 正確**：Antigravity 設計了嚴格的人機協同安全防護（Human-in-the-Loop）。在沙盒防護下，可能危害系統或逃逸沙盒的指令均須經過開發者透明審查與顯式授權，確保 Agent 的自主操作完全在人類的掌控邊界之內。
  * **選項 A 錯誤**：靜默執行重大風險指令會帶來極大的安全隱患。
  * **選項 B/D 錯誤**：無此極端行為；一般讀取與安全指令可依設定自動放行或受控執行。

</details>

---

[課堂互動](https://nlhsueh.github.io/nickedupocket/#/student/sqa-u01-antigravity-ccq3)

<!-- id: sqa-u01-antigravity-ccq4 -->
#### 🙋 **概念核對問答 (CCQ 4)：Java 專案開啟根目錄與 Classpath 解析**





**問題**

在 Antigravity / VS Code 開發 Java Maven 專案時，指引特別強調「必須直接開啟包含 `pom.xml` 的專案資料夾（如 `LabDemo/`），而不要開啟最外層的父目錄（如 `gTeachSQA/`）」。其背後最關鍵的技術原因為何？

A) 開啟外層目錄會超過作業系統的檔案路徑長度限制  
B) Java Language Server 必須以開啟的資料夾根目錄為基準定位 `pom.xml`，才能正確解析相依套件庫並建立編譯 Classpath；若開外層目錄會導致語法提示失效甚至執行時報出 `ClassNotFoundException`  
C) Maven 專案規格強制規定一個資料夾內只能有一個檔案，外層有多個子目錄會破壞規範  
D) 外層目錄通常包含 Git 版本控制，IDE 禁止載入含有 `.git` 的資料夾  

<details>
<summary>點擊查看【概念核對問答】答案與解析</summary>

**正確答案：B**

* **解析**：
  * **選項 B 正確**：VS Code 與 Antigravity 的 Java Language Server 依賴根目錄的 `pom.xml` 來辨識專案結構與建立 Classpath。若開啟外層目錄，IDE 會將內部子資料夾視為普通資料夾而非 Java 專案，無法正確下載並掛載依賴庫，導致主程式無法執行並報出 `ClassNotFoundException`。
  * **選項 A/C/D 錯誤**：皆非技術事實。

</details>

[課堂互動](https://nlhsueh.github.io/nickedupocket/#/student/sqa-u01-antigravity-ccq4)
