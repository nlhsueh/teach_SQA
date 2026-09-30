package u02_robust.exception;

/**
 * 自訂受檢例外教學展示範例：TriangleExceptionDemo
 *
 * 【核心學習目標】：
 * 1. 自訂業務受檢例外 (Custom Checked Exception)：
 *    - 繼承自 Exception，強制呼叫端明確處理或向上宣告 (Catch or Declare Rule - CDR)。
 * 2. 上下文資訊封裝 (Context Data Encapsulation)：
 *    - 在例外物件中封裝非法邊長 (a, b, c) 與失敗原因，利於日誌記錄與除錯排查。
 * 3. 領域邏輯防護：
 *    - 明確區分「非正整數邊長」與「違反三角形不等式定理」之業務錯誤。
 *
 * 【測試執行方式】：
 * - 執行主程式檢驗流程：java -cp target/classes u02_robust.exception.TriangleExceptionDemo
 * - 執行單元測試：mvn test -Dtest=TriangleExceptionDemoTest
 *
 * 【對應講義與手冊】：
 * - 講義：Ch 02 防禦性程式設計 / 自訂例外設計
 * - 實習文件：LabDemo/docs/u02_robust/exception.md
 */
public class TriangleExceptionDemo {

    /**
     * 自訂三角形業務例外類別
     */
    public static class TriangleException extends Exception {
        private final int a;
        private final int b;
        private final int c;
        private final String reason;

        public TriangleException(int a, int b, int c, String reason) {
            super(String.format("三角形建立失敗 [%s] - 邊長組合: (%d, %d, %d)", reason, a, b, c));
            this.a = a;
            this.b = b;
            this.c = c;
            this.reason = reason;
        }

        public int getA() { return a; }
        public int getB() { return b; }
        public int getC() { return c; }
        public String getReason() { return reason; }
    }

    /**
     * 驗證並建立三角形型態名稱。
     *
     * @param a 邊長 a
     * @param b 邊長 b
     * @param c 邊長 c
     * @return 三角形名稱（正三角形、等腰三角形、一般三角形）
     * @throws TriangleException 當邊長非正整數或無法構成三角形時拋出
     */
    public static String classify(int a, int b, int c) throws TriangleException {
        // 1. 檢驗邊長是否大於 0
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new TriangleException(a, b, c, "邊長必須皆為大於 0 之正整數");
        }

        // 2. 檢驗三角形不等式定理（任兩邊之和大於第三邊）
        if ((long) a + b <= c || (long) b + c <= a || (long) c + a <= b) {
            throw new TriangleException(a, b, c, "任兩邊之和必須大於第三邊");
        }

        // 3. 正常分類
        if (a == b && b == c) {
            return "正三角形 (Equilateral)";
        } else if (a == b || b == c || a == c) {
            return "等腰三角形 (Isosceles)";
        } else {
            return "一般不等邊三角形 (Scalene)";
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" 軟體品質保證 (SQA) 實習示範：自訂例外 TriangleException");
        System.out.println("==================================================");

        int[][] testCases = {
                { 3, 3, 3 },   // 正三角形
                { 5, 5, 8 },   // 等腰三角形
                { 3, 4, 5 },   // 一般三角形
                { 0, -2, 5 },  // 負數邊長 -> 預期拋出 TriangleException
                { 1, 2, 5 }    // 違反三角形定理 -> 預期拋出 TriangleException
        };

        for (int[] sides : testCases) {
            int a = sides[0], b = sides[1], c = sides[2];
            try {
                String type = classify(a, b, c);
                System.out.printf("✅ 邊長 (%d, %d, %d) 成功建立: %s%n", a, b, c, type);
            } catch (TriangleException e) {
                System.err.printf("🛑 捕捉到自訂例外: %s%n", e.getMessage());
                System.err.printf("   [除錯資訊] 原因: %s, 邊長: a=%d, b=%d, c=%d%n",
                        e.getReason(), e.getA(), e.getB(), e.getC());
            }
        }

        System.out.println("\n==================================================");
        System.out.println(" 示範執行完成。");
        System.out.println("==================================================");
    }
}
