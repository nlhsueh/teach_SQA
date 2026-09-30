package u02_robust.assertion;

/**
 * 實習練習題：泰勒展開式計算 sin(x) 的斷言防護 (SinPractice)
 *
 * 【核心觀念與背景】：
 * 電腦底層常使用泰勒展開式（馬克勞林級數）逼近 sin(x)：
 *   sin(x) = x - (x^3 / 3!) + (x^5 / 5!) - (x^7 / 7!) + ...
 * 輸入單位為「弧度 (Radian)」，例如 30° = π / 6 弧度，預期理論值為 0.5。
 *
 * 【學生實習任務】：
 * 請在標示「TODO: 練習」的區塊加入適當的 Java 斷言 (assert)：
 * 1. 前置條件斷言：驗證正規化後的角度範圍 normalizedX 是否落在合理區間 [-2π, 2π]。
 * 2. 迴圈不變量斷言：驗證級數項的絕對值是否隨階乘增長逐漸收斂變小。
 * 3. 後置條件斷言：驗證計算結果 sum 必定落在數學理論值 [-1.0, 1.0] 區間內。
 *
 * 【測試執行方式】：
 * - 啟用斷言執行主程式：java -ea -cp target/classes u02_robust.assertion.SinPractice
 * - 執行對應單元測試：mvn test -Dtest=SinPracticeTest
 *
 * 【對應講義與手冊】：
 * - 講義：Ch 02 防禦性程式設計 / 斷言機制
 * - 實習文件：LabDemo/docs/u02_robust/assertion.md
 */
public class SinPractice {

    /**
     * 容許收斂誤差
     */
    public static final double EPSILON = 1e-7;

    /**
     * 最大允許疊代項數，防止浮點數溢位或無窮迴圈
     */
    public static final int MAX_TERMS = 100;

    /**
     * 使用泰勒展開式計算 sin(x)。
     *
     * @param x 弧度 (Radians)
     * @return sin(x) 近似值
     * @throws IllegalArgumentException 若輸入為 NaN 或 Infinite
     */
    public static double calculateSin(double x) {
        // 公開 API 基本防禦：若為 NaN 則拋出 IllegalArgumentException
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            throw new IllegalArgumentException("輸入角度弧度不能為 NaN 或 Infinite");
        }

        // 為了數值計算穩定性，先將 x 正規化至 [-2π, 2π]
        double normalizedX = x % (2 * Math.PI);

        // ------------------------------------------------------------------
        // TODO: 練習 1 - 前置狀態斷言 (Precondition)
        // 說明：請使用 assert 驗證正規化後的角度 normalizedX 絕對值不超過 2π (含微小容許誤差 1e-9)
        // 語法範例：assert <條件式> : "<自訂錯誤訊息>";
        // ------------------------------------------------------------------
        // 請在此處撰寫你的 assert 程式碼...

        double term = normalizedX; // 第一項：x
        double sum = term;
        int n = 1;

        while (Math.abs(term) > EPSILON && n < MAX_TERMS) {
            double prevTerm = term;

            // 依據遞迴關係計算下一項：term_{k} = -term_{k-1} * x^2 / ((2k)*(2k+1))
            term = -term * normalizedX * normalizedX / ((2 * n) * (2 * n + 1));
            sum += term;
            n++;

            // --------------------------------------------------------------
            // TODO: 練習 2 - 迴圈不變量斷言 (Loop Invariant / 收斂性檢查)
            // 說明：驗證級數項的絕對值在階乘增長下應該逐漸變小 (Math.abs(term) <= Math.abs(prevTerm))
            // 語法範例：assert Math.abs(term) <= Math.abs(prevTerm) : "級數發散！";
            // --------------------------------------------------------------
            // 請在此處撰寫你的 assert 程式碼...
        }

        // ------------------------------------------------------------------
        // TODO: 練習 3 - 後置條件斷言 (Postcondition)
        // 說明：數學定理保證任意角度的 sin(x) 結果必定介於 -1.0 與 1.0 之間
        // 請在此加入 assert 驗證計算出的 sum 落在 [-1.0, 1.0] 之內，並附帶有意義的錯誤訊息
        // 語法範例：assert sum >= -1.0 && sum <= 1.0 : "計算結果超出理論範圍: " + sum;
        // ------------------------------------------------------------------
        // 請在此處撰寫你的 assert 程式碼...

        return sum;
    }

    /**
     * 故意外漏缺陷的 sin 計算版本（例如階乘算錯或符號沒有交替），
     * 供學生練習並驗證自己的後置條件斷言能否成功揪出 Bug！
     */
    public static double buggyCalculateSin(double x) {
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            throw new IllegalArgumentException("輸入不可為 NaN 或 Infinite");
        }

        // 故意缺少負號交替的錯誤實作：全部相加導致結果迅速暴增爆表
        double term = x;
        double sum = term;
        for (int i = 1; i <= 5; i++) {
            term = term * x * x / ((2 * i) * (2 * i + 1)); // BUG: 漏了負號！
            sum += term;
        }

        // 後置條件斷言（若啟用 -ea，計算大角度時必將觸發 AssertionError）
        assert sum >= -1.0 && sum <= 1.0 :
                "後置條件成功攔截缺陷！buggyCalculateSin 計算結果超出範圍: " + sum;

        return sum;
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" 實習練習：泰勒展開式計算 sin(x) 斷言防護");
        System.out.println("==================================================");

        // 檢驗 -ea 開關
        boolean eaEnabled = false;
        assert eaEnabled = true;
        System.out.println("斷言開關狀態: " + (eaEnabled ? "✅ 已開啟 (-ea)" : "⚠️ 未開啟 (建議加入 -ea)"));

        // 測試 1：標準角 30° (π / 6)，理論值 0.5
        double rad30 = Math.PI / 6.0;
        double result30 = calculateSin(rad30);
        System.out.printf("sin(30°) 計算值 = %.8f (理論值 = 0.50000000)%n", result30);

        // 測試 2：標準角 90° (π / 2)，理論值 1.0
        double rad90 = Math.PI / 2.0;
        double result90 = calculateSin(rad90);
        System.out.printf("sin(90°) 計算值 = %.8f (理論值 = 1.00000000)%n", result90);

        // 測試 3：測試有缺陷的演算法
        System.out.println("\n[測試 3] 執行有缺陷的 buggyCalculateSin(π / 2)...");
        try {
            double buggyResult = buggyCalculateSin(Math.PI / 2.0);
            System.out.println("⚠️ 計算完成（未開 -ea 時靜默通過）: " + buggyResult);
        } catch (AssertionError e) {
            System.err.println("🛑 成功觸發斷言錯誤！後置條件及時抓出演算法缺陷：");
            System.err.println("   " + e.getMessage());
        }
    }
}
