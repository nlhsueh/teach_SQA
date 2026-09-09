package xdemo;

/**
 * Use break/watch to see debug and watch program execution
 */
public class GCD {

    public static void main(String[] args) {
        int r;
        GCD g = new GCD();
        r = g.gcd(24, 18);
        System.out.println(r);

        r = g.gcd2(24, 18);
        System.out.println(r);
    }

    public int gcd(int m, int n) {
        while (m != 0 && n != 0) {
            if (m > n) {
                m = m % n;
            } else {
                n = n % m;
            }
        }
        return m;
    }

    public int gcd2(int m, int n) {
        if (m % n == 0)
            return n;
        else {
            return gcd2(n, m % n);
        }
    }
}
