# 🧪 DemoSQA: 軟體品質保證與測試實務教學專案

歡迎使用 **DemoSQA** 課程專案！本專案採用 **All-in-One 一站式架構**，整合全學期軟體品質保證（SQA）與測試實務的範例代碼、單元測試、自動化測試架構與各單元實驗指引（Lab Manuals）。

---

## 🚀 快速上手 (Quick Start)

1. **開啟專案**：在 IntelliJ IDEA 或 VS Code 中選擇 **`Open`**，直接選取本 `DemoSQA` 資料夾開啟。
2. **Maven 同步**：IDE 會自動解析根目錄的 `pom.xml` 並下載相依套件（支援 Java 21、JUnit 5、Mockito 5、Cucumber 7、Selenium 4、Log4j 2、JaCoCo 與 PIT）。
3. **瀏覽文件**：直接在 IDE 的左側導覽列展開 **[`docs/`](./docs/)** 資料夾，點選各單元 Markdown 即可閱讀實驗步驟並直接點擊跳轉至對應程式碼。

---

## 📚 實驗單元導覽 (Lab Units Index)

| 單元 | 主題 | 實驗手冊 (Lab Manual) | 主要範例與測試代碼 |
| :--- | :--- | :--- | :--- |
| **Unit 01** | **除錯與 IDE 實務** | [除錯指引](./docs/u01_debug/debug.md) · [IntelliJ 設定](./docs/u01_debug/Intellij.md) · [Maven POM](./docs/u01_debug/POM.md) · [AI 除錯](./docs/u01_debug/ai_code_break.md) | `src/main/java/u01_debug/`<br>`src/main/java/xdemo/` |
| **Unit 02** | **防禦性設計與日誌** | [日誌記錄](./docs/u02_preventive/logging.md) · [例外處理](./docs/u02_preventive/exception.md) · [斷言防護](./docs/u02_preventive/assertion.md) | `src/main/java/u02_preventive/`<br>`src/main/resources/log4j2.xml` |
| **Unit 03** | **程式碼檢視與靜態分析** | [PMD 靜態分析與規則](./docs/u03_inspection/pmd.md) | `src/main/resources/pmd/ruleset.xml`<br>`src/main/java/u03_inspection/` |
| **Unit 04** | **單元測試與黑箱測試** | [JUnit 5 核心實務](./docs/u04_utest/junit.md) · [屬性測試](./docs/u04_utest/jqwik_property_based.md) · [度量分析](./docs/u04_utest/metrics.md) | `src/test/java/u04_utest/`<br>`src/main/java/u04_utest/` |
| **Unit 05** | **白箱覆蓋率分析** | [白箱測試與 JaCoCo 分析](./docs/u06_wbtesting/whitebox_test.md) | `src/test/java/u04_utest/whitebox/`<br>`pom.xml (JaCoCo Plugin)` |
| **Unit 06** | **變異測試 (Mutation)** | [變異測試與 PIT 工具](./docs/u07_mutation/mutation_test.md) | `src/main/java/u06_mutation/`<br>`src/test/java/u06_mutation/` |
| **Unit 07** | **隔離與 Mock 整合測試** | [Mockito 實務](./docs/u08_integration/mokito.md) · [Spring 整合測試](./docs/u08_integration/Spring.md) · [Testcontainers](./docs/u08_integration/testcontainers_spring.md) | `src/main/java/u07_mockito/`<br>`src/test/java/u07_mockito/` |
| **Unit 08** | **行為驅動開發 (BDD) 與 Web 測試** | [BDD 導論](./docs/u09_cucumber_bdd/intro_BDD.md) · [Cucumber 實務](./docs/u09_cucumber_bdd/bmi_cucumber.md) · [Selenium 測試](./docs/u09_cucumber_bdd/bmi_selenium.md) · [契約與 E2E 測試](./docs/u09_cucumber_bdd/pact_and_playwright.md) | `src/test/resources/features/`<br>`src/test/java/u08_cucumber/`<br>`src/test/java/u09_web/` |
| **Unit 09** | **負載與混沌測試** | [K6 負載測試](./docs/u10_performance/k6_load_testing.md) · [JMeter 壓力測試](./docs/u10_performance/jmeter.md) · [混沌工程與模糊測試](./docs/u10_chaos_fuzzing/chaos_and_fuzzing.md) | `docs/u10_performance/` |
| **Unit 10** | **DevOps 與品質門檻** | [Git 流程](./docs/u11_devops/using_git.md) · [GitHub Actions 品質門檻](./docs/u11_devops/github_actions_quality_gate.md) | `docs/u11_devops/` |

---

## 📁 專案目錄結構

```text
DemoSQA/
├── pom.xml                               # 統一 Maven 依賴與插件配置
├── README.md                             # 專案總導覽與各單元索引
├── docs/                                 # 各單元詳細實驗手冊與說明文件
│   ├── u01_debug/                        # 中斷點除錯、IDE、POM
│   ├── u02_preventive/                   # 日誌、例外、斷言
│   ├── u03_inspection/                   # 代碼檢視、PMD
│   ├── u04_utest/                        # 單元測試、參數化、屬性測試
│   ├── u06_wbtesting/                    # 白箱覆蓋率、JaCoCo
│   ├── u07_mutation/                     # 變異測試、PIT
│   ├── u08_integration/                  # Mockito、Spring、Testcontainers
│   ├── u09_cucumber_bdd/                 # Cucumber BDD、Selenium、Pact
│   ├── u10_performance/                  # K6、JMeter
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

```bash
# 編譯整個專案
mvn clean test-compile

# 執行所有單元測試（包含 JaCoCo 涵蓋度報告生成）
mvn test

# 執行 PMD 代碼靜態分析
mvn pmd:check

# 執行變異測試 (PIT Mutation)
mvn pitest:mutationCoverage
```
