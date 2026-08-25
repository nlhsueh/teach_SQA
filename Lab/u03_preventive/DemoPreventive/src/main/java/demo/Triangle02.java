package demo;

public class Triangle02 {

    public static void main(String[] args) {
        try {
            System.out.println(Triangle02.checkTriangle(10, 23, 11));
            System.out.println(Triangle02.checkTriangle(1, 1, 1));
            System.out.println(Triangle02.checkTriangle(2, 2, 3));
            System.out.println(Triangle02.checkTriangle(3, 2, 2));
            System.out.println(Triangle02.checkTriangle(0, -1, -2));
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    public static String checkTriangle(int a, int b, int c) throws Exception {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new TriangleException(a,b,c,"長度不可以是負的");
        }
        if (a + b > c && b + c > a && c + a > b) {
            if (a == b)
                if (b == c) {
                    return "正三角形";
                } else
                    return "等腰三角形";
            else if (b == c) {
                return "等腰三角形";
            }
            return "三角形";
        }
        return "非三角形";
    }
}

class TriangleException extends Exception {
    int a;
    int b;
    int c;
    String msg;

    public TriangleException(int a, int b, int c, String msg) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.msg = msg;
    }

    public String getLengthString() {
        return a + "-" + b + "-" + c;
    }

    public String toString() {
        return String.format("%s: (%d, %d, %d)", msg, a, b, c);
    }
}
