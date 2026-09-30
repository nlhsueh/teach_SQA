package u02_robust.assertion;

/**
 * 實習練習題：三角形分類的斷言防護 (TriangleAssertionPractice)
 *
 * <p>【核心學習目標】：</p>
 * <ol>
 *   <li><b>區分公開 API 參數防禦與內部斷言</b>：
 *       外部輸入邊長小於等於 0 時，必須拋出 <code>IllegalArgumentException</code>，絕不可用 <code>assert</code>。</li>
 *   <li><b>狀態不變量斷言</b>：
 *       在分類回傳前，使用 <code>assert</code> 驗證內部判斷邏輯是否符合幾何不變量（如正三角形三邊必等長、兩邊之和大於第三邊）。</li>
 * </ol>
 */
public class TriangleAssertionPractice {

    public enum TriangleType {
        EQUILATERAL, // 正三角形
        ISOSCELES,   // 等腰三角形
        SCALENE,     // 一般不等邊三角形
        NOT_TRIANGLE // 非三角形
    }

    /**
     * 判定三邊長所構成的三角形類型。
     *
     * @param a 邊長 a
     * @param b 邊長 b
     * @param c 邊長 c
     * @return 三角形型態枚舉
     * @throws IllegalArgumentException 若任一邊長小於等於 0（公開 API 防禦）
     */
    public static TriangleType classifyTriangle(int a, int b, int c) {
        // ------------------------------------------------------------------
        // 1. 公開 API 參數防禦：絕不用 assert，拋出標準例外
        // ------------------------------------------------------------------
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new IllegalArgumentException(
                    String.format("邊長必須為正整數！輸入值: a=%d, b=%d, c=%d", a, b, c));
        }

        // 判斷是否滿足三角形幾何不等式定理：任意兩邊之和大於第三邊
        boolean isValid = isTriangleInequalitySatisfied(a, b, c);

        TriangleType type;
        if (!isValid) {
            type = TriangleType.NOT_TRIANGLE;
        } else if (a == b && b == c) {
            type = TriangleType.EQUILATERAL;
        } else if (a == b || b == c || a == c) {
            type = TriangleType.ISOSCELES;
        } else {
            type = TriangleType.SCALENE;
        }

        // ------------------------------------------------------------------
        // 2. 內部狀態不變量斷言 (Invariants Assertion)
        // ------------------------------------------------------------------
        if (type == TriangleType.EQUILATERAL) {
            // TODO: 練習 1 - 正三角形不變量斷言
            // 請使用 assert 驗證三邊長 a, b, c 必須完全相等，且幾何不等式 isValid 必須成立
            // 請在此撰寫 assert 程式碼...

        } else if (type == TriangleType.ISOSCELES) {
            // TODO: 練習 2 - 等腰三角形不變量斷言
            // 請使用 assert 驗證至少存在兩邊相等，且 isValid 必須成立
            // 請在此撰寫 assert 程式碼...

        } else if (type == TriangleType.NOT_TRIANGLE) {
            // TODO: 練習 3 - 非三角形不變量斷言
            // 請使用 assert 驗證幾何不等式必定不成立 (!isValid)
            // 請在此撰寫 assert 程式碼...
        }

        return type;
    }

    /**
     * 幾何輔助函式：三角形不等式定理（任意兩邊之和大於第三邊）。
     *
     * <p>作為斷言輔助函式，必須為無副作用的純函式。</p>
     */
    public static boolean isTriangleInequalitySatisfied(long a, long b, long c) {
        return (a + b > c) && (b + c > a) && (c + a > b);
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" 實習練習：三角形分類之例外與斷言防禦");
        System.out.println("==================================================");

        // 1. 正常分類測試
        System.out.println("邊長 (3, 3, 3) -> " + classifyTriangle(3, 3, 3));
        System.out.println("邊長 (3, 3, 4) -> " + classifyTriangle(3, 3, 4));
        System.out.println("邊長 (3, 4, 5) -> " + classifyTriangle(3, 4, 5));
        System.out.println("邊長 (1, 2, 5) -> " + classifyTriangle(1, 2, 5));

        // 2. 公開 API 防禦測試
        try {
            System.out.println("\n[測試公開參數防禦] 傳入負數邊長 (-2, 4, 5)...");
            classifyTriangle(-2, 4, 5);
        } catch (IllegalArgumentException e) {
            System.out.println(">> 成功攔截非法參數 (IllegalArgumentException): " + e.getMessage());
        }
    }
}
