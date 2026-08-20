# 實習 14：GitHub Actions CI/CD 全防線與品質門檻 (Quality Gate)

> 🎯 **實習目標**：
> 1. 將整學期所學的品質工程技術（**JUnit 5, JaCoCo, PITest, SonarQube**）整合進 **GitHub Actions 自動化 CI/CD 流水線**。
> 2. 設定嚴格的 **Quality Gate（品質門檻）**：當 PR 的變異殺死率 $< 70\%$ 或測試覆蓋率 $< 80\%$ 時，自動阻擋 Merge。
> 3. 封裝藍軍高可靠微服務系統，準備迎戰第 16 週的 **【紅藍攻防大擂台】**！

---

## 1. 完整的 GitHub Actions CI Workflow 腳本 (`.github/workflows/ci.yml`)

```yaml
name: SQA 2.0 Automated Quality Gate Pipeline

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  build-and-verify:
    runs-on: ubuntu-latest

    steps:
      # 1. 檢出程式碼
      - name: Checkout Code
        uses: actions/checkout@v4
        with:
          fetch-depth: 0

      # 2. 設定 JDK 17 / 21
      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
          cache: maven

      # 3. 執行單元測試與屬性基礎測試 (jqwik)
      - name: Run Unit & Property Tests
        run: mvn test

      # 4. 產生 JaCoCo 程式碼覆蓋率報告
      - name: Generate JaCoCo Coverage Report
        run: mvn jacoco:report

      # 5. 執行 PITest 變異測試 (Mutation Testing)
      - name: Run PITest Mutation Analysis
        run: mvn org.pitest:pitest-maven:mutationCoverage

      # 6. 靜態程式碼分析 (SonarCloud / SonarQube Scan)
      - name: SonarQube Quality Scan
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
        run: mvn sonar:sonar

      # 7. 上傳測試與變異報告 Artifacts
      - name: Upload Test & Mutation Reports
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: qa-verification-reports
          path: |
            target/site/jacoco/
            target/pit-reports/
```

---

## 2. 設定嚴格的 Branch Protection 規則

1. 進入 GitHub Repository **Settings $\rightarrow$ Branches**。
2. 新增分支保護規則 `main`：
   * 勾選 **Require a pull request before merging**。
   * 勾選 **Require status checks to pass before merging**。
   * 指定檢查項目：`build-and-verify`。
3. 任何未通過單元測試、變異測試或靜態掃描的 PR，一律由機器人自動拒絕合併！

---

## 📋 實習成果驗收標準
1. [ ] 專案成功建立 `.github/workflows/ci.yml` 並推送到 GitHub 觸發自動化 CI。
2. [ ] 刻意在分支提交一個「低測試覆蓋率」或「引入變異漏洞」的 PR，驗證 GitHub Actions 成功紅燈攔截。
3. [ ] 修復測試後再次推動，觀察全綠燈並自動產出 JaCoCo 與 PIT 報告。
