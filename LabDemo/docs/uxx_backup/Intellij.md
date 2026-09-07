# IntelliJ IDEA 介紹與專案設定指南

本單元介紹 Java 開發中最受歡迎的整合開發環境（IDE）—— **IntelliJ IDEA**，說明其核心特色、本機環境安裝（JDK 21 與 Maven）、Java JVM 與 Maven 的配置方法、避開雲端同步卡頓設定，如何善用 AI LLM 工具輔助專案建置，以及各項常見設定與疑難排除。

---

## 1. IntelliJ IDEA 的特色

IntelliJ IDEA 是由 JetBrains 開發的 Java 整合開發環境，廣受全球軟體工程師喜愛，其主要特色包括：

* **智慧程式碼補全（Smart Completion）**：能根據上下文、類別、變數及方法的類型，提供極其精準的補全建議，不僅僅是語法提示，還能預測開發者的意圖。
* **強大的重構功能（Refactoring）**：支援安全重命名、擷取方法（Extract Method）、調整方法簽章等，會自動同步更新整個專案中所有相關的引用。
* **開箱即用的 JVM 支援**：整合了對 Java、Kotlin、Scala 等 JVM 語言的優異支援，無需繁瑣設定即可直接開發。
* **內建建構工具與版本控制**：與 Maven、Gradle、Git 等工具無縫整合，在 IDE 內即可完成拉取、提交、編譯、打包等一站式操作。
* **優異的除錯器（Debugger）**：提供直覺的可視化除錯介面，支援條件中斷點（Conditional Breakpoints）、變數監控（Watch Variables）及運行時表示式求值（詳情可參考 [debug.md](../u01_debug/debug.md)）。

---

## 2. Java JVM 與 Maven 方面的應用

### 2.1 本機環境準備 (JDK 21 & Maven 3.x 安裝指引)

本專案要求使用 **Java 21 (LTS)** 與 **Maven 3.x**。如果您的電腦尚未安裝相關工具，請參考以下指南：

#### A. Java 版本的選擇原則
* **優先使用 LTS (Long Term Support, 長期支援) 版本**：在實際開發與教學中，應優先選擇 LTS 版本以確保穩定性與長期更新支援。目前常見的 LTS 版本有：
  - **Java 8 (JDK 1.8)**：許多企業舊有系統仍在運作，但已不建議用於新專案。
  - **Java 11**：過渡時期的主流。
  - **Java 17**：目前被廣泛採用於現代框架（如 Spring Boot 3）的 LTS 版本。
  - **Java 21**：最新且極力推薦的 LTS 版本，引入了虛擬執行緒（Virtual Threads）等強大特性。本學期的練習專案（如 `LabDemo`）預設皆使用 **Java 21**。
* **避免使用非 LTS 版本**（如 Java 22, 23 等）：非 LTS 版本每 6 個月發布一次且很快停止支援，不適合做為教學與主要開發的環境。
* **推薦發行版**：建議使用免費、開放原始碼且穩定的 OpenJDK 發行版，例如 **Eclipse Temurin (Adoptium)** 或 **Amazon Corretto**。

#### B. 各作業系統安裝方式
* **🔹 macOS 系統 (推薦使用 Homebrew 安裝)**：
  開啟終端機並執行以下指令：
  ```bash
  # 安裝 JDK 21
  brew install openjdk@21

  # 將 JDK 21 設定至環境變數 (以 zsh 為例)
  echo 'export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc
  echo 'export JAVA_HOME="/opt/homebrew/opt/openjdk@21"' >> ~/.zshrc
  source ~/.zshrc

  # 安裝 Maven
  brew install maven
  ```

* **🔹 Windows 系統 (推薦使用 Winget 快速安裝)**：
  以系統管理員身分開啟 PowerShell，並執行以下指令：
  ```powershell
  # 安裝 Eclipse Temurin JDK 21
  winget install Eclipse.Temurin.JDK.21

  # 安裝 Apache Maven
  winget install Apache.Maven
  ```
  *(安裝完成後，請重啟終端機或 IDE，確保 `JAVA_HOME` 與 Maven 的 `bin` 目錄已自動或手動加入到系統「環境變數」中。)*

* **🔹 手動下載安裝包 (所有作業系統)**：
  - **JDK 21**：至 [Adoptium Temurin 21 官網](https://adoptium.net/temurin/releases/?version=21) 下載對應作業系統的安裝檔 (如 `.msi` 或 `.pkg`) 並執行安裝。
  - **Maven**：至 [Apache Maven 官方下載頁面](https://maven.apache.org/download.cgi) 下載 Binary zip 壓縮檔，將其解壓縮至本機資料夾，並手動將該資料夾內 `bin/` 的路徑新增至系統環境變數的 `Path` 中。

#### C. 安裝完成驗證
安裝完成後，開啟終端機（Terminal 或 PowerShell）執行以下指令確認安裝成功：
```bash
java -version  # 應顯示 21.x.x
mvn -v         # 應顯示 Apache Maven 3.x.x
```

---

### 2.2 在 Maven 與 IntelliJ 中的配置與同步
設定 Java 版本時，必須確保 **Maven 的宣告** 與 **IntelliJ 的編譯設定** 兩者對齊，否則在編譯時常會出現 `class file has wrong version` 或 `Source option 5 is no longer supported` 的錯誤。

1. **在 Maven 中宣告版本 (`pom.xml`)**：
   在 `pom.xml` 的 `<properties>` 區塊內指定編譯與運行的目標版本。這會強制限制專案所使用的語法標準：
   ```xml
   <properties>
       <maven.compiler.source>21</maven.compiler.source>
       <maven.compiler.target>21</maven.compiler.target>
       <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
   </properties>
   ```
2. **在 IntelliJ 中設定與對齊**：
   當您修改 `pom.xml` 並 Reload Maven 後，IntelliJ 通常會自動同步。若未同步，請手動檢查以下三個地方：
   - **Project SDK**：按快捷鍵 `Cmd + ;` (macOS) 或 `Ctrl + Alt + Shift + S` 開啟 `Project Structure` -> 在 `Project` 選項確認 SDK 選擇了對應的版本（例如 `temurin-21`）。如果沒有，可選 `Add SDK` -> `Download JDK` 直接下載。
   - **Language Level（語言層級）**：在同一個視窗中，確認 Project Language Level 設為相同的版本（如 `21 - Virtual threads...`）。
   - **Java Compiler Target 版本**：點選 `Settings` -> 搜尋 `Java Compiler` -> 確認各 Module 的 `Target bytecode version` 皆設為相同的版本（如 `21`）。

---

### 2.3 快速上手與匯入專案注意事項（Checkpoints）

> [!WARNING]
> ⚠️ **重要提醒：開啟專案時請只選擇 `LabDemo` 子資料夾**  
> 學生 Clone 此課程儲存庫時，會連同 `Lecture` 講義等其他目錄一併下載。  
> **在開啟 IntelliJ IDEA (或 VS Code / Antigravity) 時，請務必點選「只開啟 `LabDemo`」這個子資料夾**，而**不要**開啟最外層的 `gTeachSQA` 資料夾。這樣 IDE 才能正確識別 Maven 的 `pom.xml` 並自動匯入為專案。

#### 快速上手步驟 (Quick Start)
1. **開啟專案**：開啟 IntelliJ IDEA，選擇 **`Open`**，導航至下載的目錄並**只選擇 `LabDemo` 資料夾開啟**。
2. **Maven 自動同步**：IDE 會自動解析該目錄下的 `pom.xml` 並在背景下載所有相依套件（支援 Java 21、JUnit 5、Mockito 5、Cucumber 7、Selenium 4、Log4j 2、JaCoCo 與 PIT）。
3. **瀏覽實驗文件**：直接在 IDE 的左側導覽列展開 **`docs/`** 資料夾，點選各單元 Markdown 即可閱讀實驗步驟並直接點擊跳轉至對應程式碼。

#### 匯入他人專案時的關鍵檢核點
當您將同學的專案、作業或網路上的開源專案載入到自己的 IntelliJ 時，請依序確認以下事項以防編譯失敗：

1. **清除 IDE 快取與設定檔（.idea 與 *.iml）**：
   - **動作**：在用 IntelliJ 開啟專案資料夾前，請先在檔案總管中**將該專案目錄下的 `.idea` 資料夾與所有 `.iml` 檔案刪除**。
   - **原因**：這些檔案記錄了原作者電腦的絕對路徑、SDK 名稱等個人環境配置。直接載入會造成路徑衝突。刪除後，IntelliJ 會讀取 `pom.xml` 並根據您電腦的環境重新乾淨生成。
2. **對齊並下載對應的 JDK**：
   - 打開專案後，先查看 `pom.xml` 內設定的 Java 版本（如 17 或 21）。
   - 檢查專案 SDK 是否指向您電腦中對應版本的 JDK。若無，請利用 Project Structure 自動下載安裝。
3. **確認專案路徑不含中文或空白**：
   - Java 編譯器與 Maven 在路徑中包含中文字元或空白（如 `Users/Jacky Chen/`）時偶爾會發生編譯失敗。請將專案目錄放置於單純的英文路徑下。
4. **手動重新整理 Maven 依賴**：
   - 開啟專案後，點選右側 Maven 工具視窗的 **`Reload All Maven Projects`** 圖示，確保所有相依套件皆已在您的本機上下載完整。
5. **統一檔案編碼為 UTF-8**：
   - 至 `Settings` -> `Editor` -> `File Encodings`，將 `Global Encoding`、`Project Encoding` 與 `Properties Files` 全都調整為 `UTF-8`，避免中文註解或文字資料在編譯時產生亂碼或報錯。

### 2.4 Maven 整合與依賴管理
Maven 是專案管理與依賴建置的核心工具（詳細介紹可參閱 [maven.md](../u01_debug/maven.md)）。
* **自動識別與匯入**：當您在 IntelliJ 中開啟包含 `pom.xml` 的資料夾時，IDE 會自動偵測並將其視為 Maven 專案載入，並在背景下載所需的 Jar 包。
* **Maven 工具視窗**：視窗右側有一個 `Maven` 標籤，展開後可以看到專案的 **Lifecycle**（生命週期，如 `clean`, `compile`, `test`, `package`）與 **Plugins**。按兩下即可執行對應的指令。
* **重新載入 Maven（Reload）**：如果您手動修改了 `pom.xml` 中的依賴設定，專案右上角會出現一個藍色的小 Maven 圖示（或按 `Ctrl + Shift + O` / `Cmd + Shift + I`），點擊後 IDE 就會立刻重新同步並下載最新套件。

---

### 2.5 專案各類測試說明與特別測試執行指南

本專案涵蓋軟體品質保證（SQA）課程的多個技術單元。為了讓日常開發能秒級建置，並避免學生在尚未學習後續單元時遇到環境依賴錯誤，專案將測試明確分級管理：

#### A. 常規高速單元測試 (Unit 04, 05, 07)
* **涵蓋範圍**：JUnit 5 核心斷言、參數化測試、Mockito 模擬物件測試、JaCoCo 覆蓋率收集。
* **執行指令**：
  ```bash
  mvn test
  ```
* **特性**：不依賴外部服務與網路，執行時間約 1~2 秒即可全數完成。在日常開發與 CI 流程中，應隨時執行確保核心邏輯正確。

#### B. 特別測試與進階分析工具（需特定環境或指令）
以下幾類測試因涉及**外部瀏覽器、網路伺服器、大算力計算或靜態規則分析**，日常 `mvn test` 已預設排除或由獨立 Maven 外掛觸發：

1. **Selenium Web 介面端到端測試 (`u09_web`)**
   * **對應單元**：Unit 08 / Unit 09 Web 測試
   * **為何預設排除**：測試會透過 WebDriver 啟動真實 Chrome 瀏覽器並連線至 `http://127.0.0.1:5500`。若本機未開啟 Web 伺服器，會直接拋出 `ERR_CONNECTION_REFUSED` 錯誤。
   * **前置條件**：
     1. 本機安裝最新版 Google Chrome 瀏覽器。
     2. 啟動待測 HTML 的 Web Server（例如在 VS Code 安裝 Live Server 外掛並對 `bmi.html` 點選 Go Live，或在終端機執行 `python3 -m http.server 5500`）。
   * **執行方式**：
     - 指令：`mvn test -Dtest="u09_web.**"`
     - IDE：直接在 `src/test/java/u09_web/` 的測試檔案上按右鍵點選 `Run`。

2. **Cucumber 行為驅動測試 (`u08_cucumber`)**
   * **對應單元**：Unit 08 BDD 導論與實務
   * **特性**：使用自然語言 Gherkin 語法（`src/test/resources/features/*.feature`）定義驗收規格，透過 Step Definitions 驅動業務邏輯與流程。
   * **執行方式**：
     - 指令：`mvn test -Dtest="RunCucumberTest"`
     - IDE：右鍵點選 `.feature` 檔或 `RunCucumberTest.java` 執行。

3. **PIT 變異測試 (Mutation Testing)**
   * **對應單元**：Unit 06 變異測試
   * **特性**：自動在已編譯的字節碼中植入突變（例如將 `>` 改為 `>=`、將 `+` 改為 `-`），藉以檢驗測試案例是否能敏銳殺死變異體（Kill Mutants）。
   * **執行方式**：
     - 指令：`mvn pitest:mutationCoverage`
     - 報告輸出：運算完成後，請用瀏覽器開啟 `target/pit-reports/index.html` 檢視變異涵蓋率矩陣。

4. **PMD 程式碼靜態分析**
   * **對應單元**：Unit 03 程式碼檢視與靜態分析
   * **特性**：無需執行程式碼，直接根據 `src/main/resources/pmd/ruleset.xml` 規則掃描原始碼中的 Code Smells、未使用變數、潛在 Null 指針與架構瑕疵。
   * **執行方式**：
     - 指令：`mvn pmd:check`（若違反規則將在終端機警示並報錯）

---

### 2.6 本地編譯輸出路徑設定（避開雲端硬碟同步卡頓，選用）
本專案的 `pom.xml` 已進行參數化配置，**一般情況下直接執行編譯（如 IntelliJ 同步或 `mvn compile`）即可運作，不需要進行任何額外設定**。

然而，如果您將本專案放在 **Google Drive**、**OneDrive** 或 **iCloud Drive** 等雲端同步資料夾中進行開發，編譯時 `target/` 所產生的海量暫存檔可能會導致雲端硬碟同步卡死並耗費大量系統資源。若要避開雲端同步：

1. 在您個人電腦的 `~/.m2/settings.xml`（若檔案不存在，請直接手動建立）中新增以下設定：
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <settings xmlns="http://maven.apache.org/SETTINGS/1.2.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.2.0 https://maven.apache.org/xsd/settings-1.2.0.xsd">
     <profiles>
       <profile>
         <id>exclude-cloud-sync</id>
         <activation>
           <activeByDefault>true</activeByDefault>
         </activation>
         <properties>
           <!-- 將編譯輸出路徑移至雲端硬碟之外 (macOS/Linux 使用 /tmp) -->
           <maven.build.dir>/tmp/maven-builds/${project.artifactId}</maven.build.dir>
           
           <!-- 若為 Windows 電腦，可以改用以下路徑：
           <maven.build.dir>C:/temp/maven-builds/${project.artifactId}</maven.build.dir>
           -->
         </properties>
       </profile>
     </profiles>
   </settings>
   ```
2. 設定完成後重新開啟 IDE 或在 Maven 工具視窗點選 **Reload All Maven Projects**，編譯時的 `target` 輸出便會自動導向本機的暫存路徑，完美解決雲端硬碟同步卡頓問題！

---

### 2.7 IntelliJ 專案目錄與設定檔結構
在開啟 Java / Maven 專案時，您會在專案根目錄下看到一些由 IDE 自動生成或預設的檔案與資料夾。它們各自扮演不同的角色：

| 目錄/檔案 | 用途說明 | 是否需要納入 Git 版本控制？ |
| :--- | :--- | :--- |
| **`.idea/`** | **IntelliJ 專案專屬設定資料夾**：存放此專案在該 IDE 中的配置（如編輯器視窗排版、執行/除錯設定 `runConfigurations`、Maven 同步快取、程式碼風格樣式等）。 | **大部分排除**：通常將個人排版設定排除，僅保留團隊共用的執行設定（如特定的 `runConfigurations` 檔）。 |
| **`*.iml`** (如 `LabDemo.iml`) | **IntelliJ 模組設定檔（Module File）**：以 XML 格式記錄該模組的結構、路徑及依賴關係。為 IntelliJ 的舊版或相容性設計。 | **排除**：因為 Maven 專案的依賴關係已經由 `pom.xml` 定義，IDE 會自動從 `pom.xml` 生成此檔案，無需納入 Git。 |
| **`src/`** | **原始碼目錄**：存放所有 Java 程式碼與資源檔案。<br>・`src/main/java`：主程式邏輯。<br>・`src/main/resources`：設定檔或資源。<br>・`src/test/java`：單元測試程式碼。 | **必須納入**：這是開發的核心程式碼。 |
| **`target/`** | **編譯與建置輸出目錄**：Maven 執行 `compile` 或 `package` 後生成的檔案（包含編譯後的 `.class` 檔、包裝好的 `.jar` 檔、測試報告與 Jacoco 覆蓋率報告等）。 | **絕對排除**：此資料夾可以透過 `mvn clean` 隨時清除，並透過 `mvn compile` 重新生成，絕對不要提交至 Git。 |
| **`pom.xml`** | **Maven 專案物件模型（Project Object Model）**：定義專案基本資訊、依賴套件、編譯外掛等（參考 [maven.md](../u01_debug/maven.md)）。 | **必須納入**：這是定義專案建置與依賴的根本來源。 |
| **`.gitignore`** | **Git 排除清單**：定義哪些檔案與資料夾不需要被 Git 追蹤（例如 `.idea/`、`target/`、`*.iml` 及各種作業系統暫存檔）。 | **必須納入**：確保團隊成員在協作時不會提交垃圾檔案。 |

> **💡 提示：為什麼我的專案一直出現奇怪的編譯錯誤？**  
> 如果專案設定損壞，您可以嘗試安全地關閉 IntelliJ，在檔案管理器中直接刪除 `.idea/` 資料夾與所有的 `*.iml` 檔案，然後在 IntelliJ 中重新點選 `Open` 載入 `pom.xml`，IDE 就會重新為您乾淨地產生這些設定檔。

---

## 3. 透過 AI LLM 來幫忙設定專案

現代軟體開發中，生成式 AI（如 Gemini, GitHub Copilot, ChatGPT 等）是設定與維護專案的強大助手。以下是幾種常見的應用情境：

### 3.1 產生與配置 `pom.xml`
如果您不知道某個套件（例如 JUnit 5、MockBox 或 Selenium）在 Maven 的寫法，可以向 AI 發出提示詞：
> **Prompt 範例**：  
> `請幫我寫一個適用於 Maven 的 JUnit 5 (Jupiter) 依賴設定 xml，Java 版本使用 21，並加上說明。`

AI 會產生類似以下的區塊，您只需直接貼入 `pom.xml` 的 `<dependencies>` 中即可：
```xml
<!-- JUnit Jupiter API (撰寫測試用) -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.11.0</version>
    <scope>test</scope>
</dependency>
```

### 3.2 解決相依性衝突與編譯錯誤
當 Maven 下載套件出錯，或發生版本衝突（例如 `Dependency Resolution Exception`）時：
1. 複製終端機輸出的 Maven 錯誤訊息。
2. 貼給 LLM 並詢問原因與解決方案。
> **Prompt 範例**：  
> `我在執行 mvn compile 時遇到以下錯誤：[貼上錯誤訊息]，請問我的 pom.xml 應該怎麼修改？`

### 3.3 自動產生專案結構
若要手動建立一個符合 Maven 標準架構（Standard Directory Layout）的專案，可以請 LLM 撰寫腳本：
> **Prompt 範例**：  
> `請幫我寫一個 Bash / PowerShell 腳本，可以在目前目錄下快速建立 Maven 標準的 src/main/java/demo 和 src/test/java/demo 目錄。`

### 3.4 IntelliJ 中直接使用 AI LLM 外掛（Plugins）
除了在瀏覽器中使用 AI 外，您可以直接在 IntelliJ 內安裝 AI 外掛，在編輯器中實現「隨敲隨用」的體驗：

1. **GitHub Copilot** (推薦)
   - **特色**：由 GitHub 與 OpenAI 合作推出，是目前最主流的 AI 助手。能在您輸入程式碼時即時給出整行或整段的自動補全建議，並提供 Copilot Chat 側邊欄，支援在 IDE 中直接詢問程式碼解釋、重構建議或自動編寫測試。
   - **安裝方式**：至 `Settings` (macOS 系統為 `IntelliJ IDEA` -> `Settings`) -> `Plugins` -> 搜尋 `GitHub Copilot` 點選安裝，重啟 IDE 後登入帳號即可啟用。

2. **JetBrains AI Assistant**
   - **特色**：JetBrains 官方為其 IDE 系列量身打造。深度整合了 IntelliJ 專案的上下文（例如：它知道您的 Maven 設定、JDK 版本與模組相依性）。支援在程式碼中直接按快捷鍵（如 `Cmd + I` 或 `Ctrl + \`）叫出對話框，原地修改或優化程式碼。
   - **安裝方式**：至 `Settings` -> `Plugins` -> 搜尋 `AI Assistant` 啟用。

3. **Codeium** / **Tabnine** (免費或高隱私度方案)
   - **Codeium**：個人使用免費，自動補全速度極快，是 Copilot 之外的熱門免費選擇。
   - **Tabnine**：提供本地端（Local-only）運行模型的選項，適合公司內部不希望程式碼上傳到雲端的開發場景。

**AI 外掛的實用開發技巧**：
* **快速生成單元測試**：在您的 Class 或方法名稱上按右鍵 -> `AI Actions` -> `Generate Unit Tests`，AI 便會自動套用 JUnit 產生對應的測試案例。
* **解釋未知程式碼**：選取 Demo 專案中較複雜的邏輯 -> 右鍵 -> `AI Actions` -> `Explain Code`，幫助您快速理解他人撰寫的演算法。

---

## 4. 另選方案：使用 Antigravity IDE (VS Code 核心 + 內建 AI Agent) 開發

**Antigravity IDE** 是一款內建 AI Agent 的新一代整合開發環境（基於 VS Code 核心）。如果您想體驗端到端 AI 協同開發，可依循以下安裝與執行步驟：

### 4.1 下載與安裝
1. 至 [Antigravity 官網](https://antigravity.google) 下載並安裝適合您作業系統的 **Antigravity IDE**。
2. 啟動 Antigravity IDE。

### 4.2 安裝 Java 相關外掛 (Extensions)
由於 Antigravity IDE 預設是輕量級編輯器，需要安裝語言擴充以獲得完整的 Java/Maven 支援：
1. 點擊 IDE 左側的 **Extensions** 圖示（四個方塊積木），或按下快速鍵 `Cmd+Shift+X` (macOS) / `Ctrl+Shift+X` (Windows)。
2. 在搜尋欄輸入 **`Extension Pack for Java`**（微軟 Microsoft 發行）並點擊 **Install**。
*(這會自動為您安裝 Java 語言支援、Java 除錯器、Maven 專案管理與單元測試執行器等多合一工具。)*

### 4.3 開啟專案
1. 在 Antigravity IDE 中點擊 **`Open Folder`**（或由選單選擇 `File -> Open Folder`）。
2. 導航至 Clone 下來的目錄，**務必只選擇 `LabDemo` 資料夾開啟**（請勿開啟外層的 `gTeachSQA` 根目錄）。
3. 開啟後，Java 插件會自動偵測 `pom.xml` 並下載依賴（首次下載可能需要數分鐘）。

### 4.4 執行與除錯 (Run & Debug)
* **執行單元測試**：打開任何一個測試檔案（例如 `src/test/java/u04_utest/CalculatorTest.java`），在測試類別或測試方法上方，會出現小型的 **`Run` | `Debug`** 字樣，直接點擊即可單獨執行或設定斷點進行除錯。
* **終端機執行**：按下 `Ctrl + ~`（或從選單開啟 Terminal），可直接在下方輸入 Maven 指令（如 `mvn clean test`）。

### 4.5 使用內建 AI Agent 協同開發 (免裝外掛，已內建)
* **AI 側邊欄聊天 (Chat)**：按下 `Cmd + L` (macOS) / `Ctrl + L` (Windows) 開啟聊天視窗，可直接向 AI 發問或要求解釋程式碼。
* **Agent 模式 (Agent Mode)**：在聊天視窗中將模式切換至 **`Agent`**。您可以直接命令它：「*請幫我寫出 Unit 04 的單元測試並執行到通過*」，AI Agent 會自動讀寫專案檔案、自己開終端機跑測試，直到幫您排除所有 bug。
* **行內 AI 修改 (Inline Command)**：選取任一段程式碼，按下 `Cmd + I` (macOS) / `Ctrl + I` (Windows)，可以直接在該行輸入指令修改程式碼（如：「*請幫我加上 Preconditions 斷言防護*」）。

---

## 5. 課堂遊戲與觀念檢核

<!-- id: sqa-u01-game1 -->
#### 🙋 **課堂遊戲挑戰 (Game 1)：IntelliJ IDEA 與 Maven 開發實戰搶答**




**第 1 題：專案目錄與 Git 版本控制規範**  
當你在團隊協作中使用 Git 管理 IntelliJ IDEA + Maven 專案時，下列哪一個目錄或檔案**絕對不應該**被 Commit 提交到 Git 儲存庫中？

A) `pom.xml`（定義專案相依套件與建置外掛的核心檔案）  
B) `src/test/java`（存放單元測試與整合測試程式碼的目錄）  
C) `target/`（Maven 執行編譯與打包所輸出的二進位產物目錄）  
D) `.gitignore`（定義專案排除追蹤清單的設定檔）  

<details>
<summary>點擊查看【第 1 題】答案與解析</summary>

**正確答案：C**

* **解析**：`target/` 目錄存放的是 Maven 編譯輸出的 `.class` 檔、打包後的 `.jar` 檔及測試覆蓋率報告，可以透過 `mvn clean` 隨時清除並重新生成。將二進位產物提交至 Git 會導致儲存庫膨脹與嚴重的合併衝突，因此必須在 `.gitignore` 中明確排除。

</details>

---

**第 2 題：JDK 版本對齊與編譯錯誤排除**  
在 IntelliJ IDEA 中載入別人的 Maven 專案時，若編譯器回報 `java: error: release version 21 not supported` 或類別版本不相容的錯誤，最可能的原因與標準排除步驟為何？

A) 電腦硬碟空間不足，需刪除作業系統暫存檔後重啟電腦  
B) `pom.xml` 宣告使用了 Java 21，但 IntelliJ 的 Project SDK 或 Java Compiler Target Bytecode Version 仍設定在較舊版本的 JDK，需至 Project Structure 與 Settings 中對齊版本  
C) 網路連線中斷導致 Maven 無法連線至中央儲存庫下載依賴  
D) Java 21 不是 LTS（長期支援）版本，因此 IntelliJ 原生不支援其語法  

<details>
<summary>點擊查看【第 2 題】答案與解析</summary>

**正確答案：B**

* **解析**：Maven 的 `pom.xml` 宣告與 IntelliJ 的 IDE 設定必須完全對齊。如果 `pom.xml` 指定 Java 21，但 Project SDK 或 Module 的 Target Bytecode Version 停留在舊版（如 11 或 17），編譯器便會拋出版本不支援的錯誤。

</details>

---

**第 3 題：安全重構（Refactoring）與最佳實踐**  
在 IntelliJ IDEA 中進行程式碼重構時，若要修改一個核心類別（Class）或變數名稱，並確保整個專案所有引用該名稱的地方皆同步安全更新，應該採取哪一種做法？

A) 使用全域文字搜尋取代（Replace in Files）直接暴力更換字串  
B) 在作業系統的檔案總管中手動修改 `.java` 檔名後重新編譯  
C) 使用 IntelliJ 內建的 `Refactor -> Rename`（快捷鍵 `Shift + F6`），由 IDE 進行語法樹（AST）語意分析並自動同步更新所有引用點  
D) 直接刪除原類別，重新撰寫一個新類別並手動修改報錯的地方  

<details>
<summary>點擊查看【第 3 題】答案與解析</summary>

**正確答案：C**

* **解析**：IntelliJ 具備強大的 AST 語意分析引擎，使用 `Shift + F6` 重構命名不僅能改檔名，還會自動更新所有 import、方法呼叫與註解引用，避免全域字串取代時誤傷其他同名字串。

</details>

[課堂互動](https://nlhsueh.github.io/nickedupocket/#/student/sqa-u01-game1)



* **[IntelliJ IDEA 與專案設定指南 (Intellij.md)](./Intellij.md)**
  * **核心特色**：智慧程式碼補全、AST 語法樹重構、內建 JVM/Git/Maven 整合、視覺化除錯器。
  * **環境安裝指引**：JDK 21 (Temurin / Homebrew) 與 Maven 3.x 安裝與環境變數設定。
  * **IDE 設定與版本對齊**：Project SDK、Language Level 與 Compiler Bytecode Version 對齊。
  * **雲端硬碟防雷指南**：針對 Google Drive / OneDrive / iCloud 雲端同步卡頓問題的配置。
  * **他人專案載入注意事項**：清理 `.idea` 與 `*.iml` 避免環境衝突。
  * **AI LLM 開發輔助**：利用 AI 產生與除錯 `pom.xml`、分析例外 StackTrace、使用 IDE 外掛（GitHub Copilot / AI Assistant / Antigravity）。
  * **課堂搶答評量 (CCQ)**：4 題情境測驗題與解析。