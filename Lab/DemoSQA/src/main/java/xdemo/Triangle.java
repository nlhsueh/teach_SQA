package xdemo;

/**
 * LAB: use assert / exception to achieve preventive programming; make the program robust
 * - This program has significant bugs
 */

public class Triangle {

    public static void main(String[] args) {
        System.out.println(Triangle.checkTriangle(10, 23, 11));
        System.out.println(Triangle.checkTriangle(1, 1, 1));
        System.out.println(Triangle.checkTriangle(2, 2, 3));
        System.out.println(Triangle.checkTriangle(3, 2, 2));
        System.out.println(Triangle.checkTriangle(0, -1, -2));
        System.exit(0);
    }

    public static String checkTriangle(int a, int b, int c) {
        if (a <= 0 || b <= 0 || c <= 0) {
            System.out.println("長度不可以是負的");
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
