package u02_debug;

/**
 * Use break/watch to see debug and watch program execution
 */
public class GCD {

    public static void main(String[] args) {
        int r;
        GCD g = new GCD();
        r = g.gcd(24,18);
        System.out.println(r);

        r = g.gcd2(24, 18);
        System.out.println(r);
    }

    public int gcd(int m, int n) {
        int r = 0;
        while (n != 0) {
            m = n;
            r = m % n;
            n = r;
        }
        return m;
    }

    public int gcd2(int m, int n) {
        if (n == 0)
            return m;
        else {
            return gcd2(m, m % n);
        }
    }
}