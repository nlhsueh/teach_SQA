
package xdemo;

import java.util.Scanner;

public class Triangle {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 輸入三角形三邊長
        System.out.print("請輸入第一邊長: ");
        double a = scanner.nextDouble();
        System.out.print("請輸入第二邊長: ");
        double b = scanner.nextDouble();
        System.out.print("請輸入第三邊長: ");
        double c = scanner.nextDouble();

        // 判斷是否為有效三角形
        if ((a + b > c) && (a + c > b)) {
            if (a == b && b == c) {
                System.out.println("這是正三角形。");
            } else if (a == b || b == c || a == c) {
                System.out.println("這是等腰三角形。");
            } else {
                // 判斷是否為直角三角形
                double max = Math.max(a, Math.max(b, c));
                if (Math.abs((max * max) - (a * a + b * b + c * c - max * max)) < 0.0001) {
                    System.out.println("這是直角三角形。");
                } else {
                    System.out.println("這是一般三角形。");
                }
            }
        } else {
            System.out.println("這不是一個有效的三角形。");
        }
        scanner.close();
    }
}
