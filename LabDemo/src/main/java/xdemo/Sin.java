package xdemo;

/**
 * 📚 【教學引導：電腦如何計算 sin(x)？—— 泰勒展開式 (Taylor Series)】
 *
 * 1. 角度與弧度 (Radians)：
 *    Java 的 Math 函式庫中，三角函式的輸入單位皆為「弧度 (Radian)」，而非角度 (Degree)。
 *    換算公式：180° = π 弧度，因此 30° = π / 6 弧度。
 *    預期目標（測試預期值 Oracle）：sin(30°) = sin(π / 6) 理論值恰好為 0.5！
 *
 * 2. 泰勒展開式原理：
 *    電腦底層只能執行加、減、乘、除，無法直接算出 sin 函數。數學上使用
 *    泰勒展開式（在 x=0 展開的馬克勞林級數）透過多項式累加來逼近 sin(x)：
 *
 *        sin(x) = x - (x^3 / 3!) + (x^5 / 5!) - (x^7 / 7!) + (x^9 / 9!) - ...
 *
 *    展開規律：
 *    - 正負符號交替：第 1 項為正 (+)，第 2 項為負 (-)，第 3 項為正 (+)...
 *    - 次方與階乘皆為連續奇數：1, 3, 5, 7, 9...
 *    - 項數越多，計算值就越精準逼近真實值（收斂性）。
 *
 * 3. 電腦如何停止逼近？
 *    因為不可能計算無窮多項，實務上會逐步增加展開項數。當前後兩次計算結果的
 *    差距小於極小的容許誤差 (如 stop = 0.0000001) 時，即視為達到精度並停止。
 *
 * 🎯 【除錯任務】：
 * 目前本程式執行 sin(30°) 時輸出嚴重失真（遠非 0.5）。
 * 請設定中斷點 (Breakpoint) 與變數監視 (Watch)，追蹤每一項的累加運算與迴圈終止條件，
 * 排查出隱藏在程式中的邏輯缺陷！
 */
public class Sin {

    public static void main(String[] args) {
        Sin s = new Sin();
        assert (1>2);
        System.out.println("The value of sin(30')");
        System.out.println(s.sin(Math.PI / 6.0));
    }

    /*
     * @param x: 弧度 (Radians)
     * 此函式透過泰勒展開式計算 x 的 sin 值。
     * 由於泰勒展開式可以一直展到無窮項，我們必須設定收斂停止條件：
     * 一開始先計算 n 項，接著每次增加 inc 項。當前後兩次計算結果的差距小於 stop 時停止，否則繼續逼近。
     */
    public double sin(double x) {

        int n = 2; // 一開始跑的個數
        int inc = 3; // 每一次多加 inc 個個數
        double stop = 0.0000001; // 停止的條件

        double s1 = sin(x, n);
        n = n + inc;
        double s2 = sin(x, n);

        while (Math.abs(s2 - s1) < stop) {
            s1 = s2;
            n = n + inc;
            s2 = sin(x, n);
        }
        return s2;
    }

    /*
     * @param n: number of items, for example, x-x^3/3!+x^5/5!, then n=3
     *
     * 這個副程式裡面有不少的變數，可以使用 breakpoint 讓程式停在這個副程式，再使用 variable view
     * 來看執行過程中這些變數的變化。
     */
    double sin(double x, int n) {
        double v = x;
        int postive = 1;
        for (int i = 1; i < 2 * n; i = i + 2) {
            // you can use breakpoint 'watch' to check
            v = v + postive * (Math.pow(x, i) / factorial(i));
            postive = postive * -1;
        }

        return v;
    }

    /*
     * 數學的階乘; factorial(3) = 3*2*1
     */
    double factorial(double s) {
        double r = 1;

        for (int i = 1; i <= s; i++) {
            r = r * i;
        }
        return r;
    }

}