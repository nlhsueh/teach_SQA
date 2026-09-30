package u02_robust.exception;

/**
 * 實習練習題：安全計算機與例外防禦 (SafeCalculatorPractice)
 *
 * 【核心學習目標】：
 * 1. NumberFormatException 處理：
 *    - 當使用者輸入非純數字字串時，如何防止程式崩潰並給予友善提示。
 * 2. ArithmeticException 處理：
 *    - 當整數除法除數為 0 時，如何優雅捕捉與防護。
 * 3. 結果物件封裝模式 (Result Pattern)：
 *    - 利用 CalculationResult 物件封裝成功/失敗狀態，提供穩健的呼叫端介面。
 *
 * 【測試執行方式】：
 * - 執行主程式檢驗流程：java -cp target/classes u02_robust.exception.SafeCalculatorPractice
 * - 執行單元測試：mvn test -Dtest=SafeCalculatorPracticeTest
 *
 * 【對應講義與手冊】：
 * - 講義：Ch 02 防禦性程式設計 / 例外處理機制
 * - 實習文件：LabDemo/docs/u02_robust/exception.md (Ex01 除零異常 & Ex02 格式異常)
 */
public class SafeCalculatorPractice {

    /**
     * 計算結果封裝物件
     */
    public static class CalculationResult {
        private final boolean success;
        private final int value;
        private final String errorMessage;

        public static CalculationResult ok(int value) {
            return new CalculationResult(true, value, null);
        }

        public static CalculationResult error(String errorMessage) {
            return new CalculationResult(false, 0, errorMessage);
        }

        private CalculationResult(boolean success, int value, String errorMessage) {
            this.success = success;
            this.value = value;
            this.errorMessage = errorMessage;
        }

        public boolean isSuccess() { return success; }
        public int getValue() { return value; }
        public String getErrorMessage() { return errorMessage; }

        @Override
        public String toString() {
            return success ? "成功 [結果=" + value + "]" : "失敗 [原因=" + errorMessage + "]";
        }
    }

    /**
     * 嘗試將輸入字串轉為整數並執行整數除法。
     *
     * 【學生實習任務】：
     * 請在方法內使用 try-catch 結構妥善處理潛在例外：
     * - 若輸入為 null 或無法解析為整數，捕捉 NumberFormatException 並回傳包含錯誤訊息的結果。
     * - 若除數為 0，捕捉 ArithmeticException 並回傳除數不能為零的錯誤結果。
     *
     * @param numeratorStr 被除數字串
     * @param denominatorStr 除數字串
     * @return 封裝之計算結果
     */
    public static CalculationResult safeDivide(String numeratorStr, String denominatorStr) {
        if (numeratorStr == null || denominatorStr == null) {
            return CalculationResult.error("輸入字串不能為 null");
        }

        try {
            // 嘗試解析字串為整數（可能引發 NumberFormatException）
            int numerator = Integer.parseInt(numeratorStr.trim());
            int denominator = Integer.parseInt(denominatorStr.trim());

            // 執行除法運算（若 denominator == 0，可能引發 ArithmeticException）
            int quotient = numerator / denominator;
            return CalculationResult.ok(quotient);

        } catch (NumberFormatException e) {
            // --------------------------------------------------------------
            // TODO: 練習 1 - 處理數字格式異常 (NumberFormatException)
            // 說明：回傳封裝好的錯誤結果 CalculationResult.error(...)，說明字串無法解析為整數
            // 語法範例：return CalculationResult.error("輸入格式錯誤，請輸入整數: " + e.getMessage());
            // --------------------------------------------------------------
            return CalculationResult.error("請完成 TODO 1：處理 NumberFormatException");
        } catch (ArithmeticException e) {
            // --------------------------------------------------------------
            // TODO: 練習 2 - 處理除零異常 (ArithmeticException)
            // 說明：回傳封裝好的錯誤結果 CalculationResult.error(...)，說明除數不能為零
            // 語法範例：return CalculationResult.error("運算錯誤：除數不能為零！");
            // --------------------------------------------------------------
            return CalculationResult.error("請完成 TODO 2：處理 ArithmeticException");
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" 實習練習：安全計算機例外處理");
        System.out.println("==================================================");

        String[][] testInputs = {
                { "100", "5" },      // 正常成功
                { "50", "0" },       // 除數為零
                { "abc", "10" },     // 格式錯誤
                { "30", "xyz" },     // 格式錯誤
                { "  42  ", " 2 " }  // 帶有空白的合法數字
        };

        for (String[] pair : testInputs) {
            CalculationResult result = safeDivide(pair[0], pair[1]);
            System.out.printf("輸入: (\"%s\", \"%s\") -> %s%n", pair[0], pair[1], result);
        }
    }
}
