# 🧪 LabDemo: 軟體品質保證與測試實務教學專案

本專案為 **gTeach SQA（軟體品質保證與測試實務）** 課程實習示範專案，涵蓋 Java 現代測試技術、防禦性設計、靜態分析、覆蓋率度量、變異測試、BDD/Web 自動化與 DevOps 品質門檻。

---

## 📚 實驗單元導覽 (Lab Units Index)

| 單元 | 主題 | 實驗手冊 (Lab Manual) | 主要範例與測試程式碼 |
| :--- | :--- | :--- | :--- |
| **Unit 01** | **開發環境與建置工具** | [Antigravity IDE](./docs/u01_intro/antigravity.md) · [Maven 設定](./docs/u01_intro/maven.md) | `pom.xml`<br>`docs/u01_intro/` |
| **Unit 02** | **錯與除錯、防禦與日誌** | [除錯指引](./docs/u02_debug/debug.md) · [AI 破壞實驗](./docs/u02_debug/ai_code_break.md) · [斷言防護](./docs/u02_robust/assertion.md) · [例外處理](./docs/u02_robust/exception.md) · [日誌記錄](./docs/u02_robust/logging.md) | `src/main/java/u02_debug/`<br>`src/main/java/u02_robust/`<br>`src/test/java/u02_robust/` |
| **Unit 04** | **程式碼檢視與靜態分析** | [PMD 靜態分析與規則](./docs/u04_inspection/pmd.md) | `src/main/resources/pmd/ruleset.xml`<br>`src/main/java/u04_inspection/` |
| **Unit 05** | **單元測試與黑箱測試** | [JUnit 5 核心實務](./docs/u05_utest/junit.md) · [屬性測試](./docs/u05_utest/jqwik_property_based.md) · [度量分析](./docs/u05_utest/metrics.md) | `src/test/java/u05_utest/`<br>`src/main/java/u05_utest/` |
| **Unit 06** | **白箱覆蓋率分析** | [白箱測試與 JaCoCo 分析](./docs/u06_wbtesting/whitebox_test.md) | `src/test/java/u05_utest/whitebox/`<br>`pom.xml (JaCoCo Plugin)` |
| **Unit 07** | **變異測試 (Mutation)** | [變異測試與 PIT 工具](./docs/u07_mutation/mutation_test.md) | `src/main/java/u06_mutation/`<br>`src/test/java/u06_mutation/` |
| **Unit 08** | **隔離與 Mock 整合測試** | [Mockito 實務](./docs/u08_integration/mokito.md) · [Spring 整合測試](./docs/u08_integration/Spring.md) · [Testcontainers](./docs/u08_integration/testcontainers_spring.md) | `src/main/java/u07_mockito/`<br>`src/test/java/u07_mockito/` |
| **Unit 09** | **行為驅動開發 (BDD) 與 Web 測試** | [BDD 導論](./docs/u09_cucumber_bdd/intro_BDD.md) · [Cucumber 實務](./docs/u09_cucumber_bdd/bmi_cucumber.md) · [Selenium 測試](./docs/u09_cucumber_bdd/bmi_selenium.md) · [契約與 E2E 測試](./docs/u09_cucumber_bdd/pact_and_playwright.md) | `src/test/resources/features/`<br>`src/test/java/u08_cucumber/`<br>`src/test/java/u09_web/` |
| **Unit 10** | **負載與混沌測試** | [K6 負載測試](./docs/u10_performance/k6_load_testing.md) · [JMeter 壓力測試](./docs/u10_performance/jmeter.md) · [混沌工程與模糊測試](./docs/u10_chaos_fuzzing/chaos_and_fuzzing.md) | `docs/u10_performance/`<br>`docs/u10_chaos_fuzzing/` |
| **Unit 11** | **DevOps 與品質門檻** | [Git 流程](./docs/u11_devops/using_git.md) · [GitHub Actions 品質門檻](./docs/u11_devops/github_actions_quality_gate.md) | `docs/u11_devops/` |
| **Unit XX** | **開發環境與 IDE 指南** | [IntelliJ IDEA 與專案設定指南](./docs/uxx_backup/Intellij.md) | 全專案開發環境設定 |

---

## 📁 專案目錄結構

```text
LabDemo/
├── pom.xml                               # 統一 Maven 依賴與插件配置
├── README.md                             # 專案總導覽與各單元索引
├── docs/                                 # 各單元詳細實驗手冊與說明文件
│   ├── u01_intro/                        # Antigravity IDE、Maven 指南
│   ├── u02_debug/                        # 中斷點除錯、AI 破壞實驗
│   ├── u02_robust/                       # 日誌追蹤、例外處理、斷言防護
│   ├── u04_inspection/                   # 程式碼檢視、PMD 規則
│   ├── u05_utest/                        # 單元測試、參數化、屬性測試
│   ├── u06_wbtesting/                    # 白箱覆蓋率、JaCoCo
│   ├── u07_mutation/                     # 變異測試、PIT
│   ├── u08_integration/                  # Mockito、Spring、Testcontainers
│   ├── u09_cucumber_bdd/                 # Cucumber BDD、Selenium、Pact
│   ├── u10_performance/                  # K6、JMeter 壓測
│   ├── u10_chaos_fuzzing/                # 混沌工程、Fuzzing
│   ├── u11_devops/                       # Git、GitHub Actions CI/CD
│   └── img/                              # 說明文件附圖
└── src/
    ├── main/
    │   ├── java/                         # 待測類別與各單元範例
    │   └── resources/                    # log4j2.xml, pmd/ruleset.xml, medals.json
    └── test/
        ├── java/                         # 各單元測試案例 (JUnit, Mockito, Cucumber)
        └── resources/                    # Cucumber .feature 檔與測試數據
```

---

## 🛠️ 常見 Maven 執行指令

### 1. 基礎建置與日常單元測試
```bash
# 編譯整個專案（確認程式碼無語法錯誤）
mvn clean test-compile

# 執行常規單元測試（包含 JaCoCo 涵蓋度收集，約 2 秒完成）
mvn test
```

### 2. 依單元執行測試（課堂學習推薦）
本課程採循序漸進講授，在終端機可指定 package 只執行當前單元的測試：
```bash
# 只執行 Unit 04 單元測試
mvn test -Dtest="u04_utest.**"

# 只執行 Unit 07 Mockito 隔離測試
mvn test -Dtest="u07_mockito.**"
```
*(在 IDE 中亦可直接在對應單元的測試資料夾或測試檔案上按右鍵點選 **`Run`** 執行)*

### 3. 特別測試與進階工具（需特定前置條件）
以下測試工具因需要大量計算或特定外部環境，日常 `mvn test` 已預設排除或由特定外掛觸發：

| 工具 / 測試類型 | 對應單元 | 執行指令 | 前置條件與特性說明 |
| :--- | :--- | :--- | :--- |
| **PMD 程式碼檢視** | **Unit 03** | `mvn pmd:check` | 靜態程式碼規則檢查，分析是否有 Code Smells 與潛在缺陷。 |
| **PIT 變異測試** | **Unit 06** | `mvn pitest:mutationCoverage` | 自動植入突變體驗證測試強健度，計算量較大，結果報告產於 `target/pit-reports/`。 |
| **Cucumber BDD** | **Unit 08** | `mvn test -Dtest="RunCucumberTest"` | 依 Gherkin 規格（`.feature`）執行行為驅動驗收測試。 |
| **Selenium Web 測試** | **Unit 08/09** | `mvn test -Dtest="u09_web.**"` | ⚠️ **需前置環境**：電腦需安裝 Chrome 瀏覽器，並啟動待測的本機 Web 伺服器（Port 5500），否則會因連線中斷報錯。 |

> 📖 更詳盡的測試類型解析與排錯指引，請參閱：👉 [特別測試說明與執行指南 (docs/uxx/Intellij.md)](./docs/uxx/Intellij.md#25-專案各類測試說明與特別測試執行指南)。
