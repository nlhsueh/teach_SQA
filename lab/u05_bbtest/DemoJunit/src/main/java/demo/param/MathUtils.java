package demo.param;

public class MathUtils {

    // 檢驗是否為質數
    public static boolean isPrime(int n) {
        // 排除小於2的數字，因為質數必須大於1
        if (n <= 1) {
            return false;
        }

        // 檢查從2到√n的數字，是否有可以整除的數
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true; // 若無可整除的數，則為質數
    }
}
