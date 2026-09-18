package xdemo;

public class GCD {

    public static void main(String[] args) {
        int r;
        GCD g = new GCD();

        r = g.gcd(24, 18);
        System.out.println("gcd(24, 18) = " + r);

        r = g.gcd2(24, 18);
        System.out.println("gcd2(24, 18) = " + r);

        r = g.gcd(18, 24);
        System.out.println("gcd(18, 24) = " + r);

        r = g.gcd2(18, 24);
        System.out.println("gcd2(18, 24) = " + r);
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
            return gcd2(m, m % n);
        }
    }
}
