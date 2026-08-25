package demo;

/**
 * 撰寫一個 `getTriangleType` 方法，根據三邊長 `a`、`b`、`c` 的值來判斷三角形的類型：
 * 若任一邊長小於等於 0 或兩邊之和小於等於第三邊，回傳 "Not a valid triangle"；
 * 若三邊相等，回傳 "Equilateral"；若兩邊相等，回傳 "Isosceles"；若三邊皆不相等，回傳 "Scalene"。
 */

public class Triangle {
    public static String getTriangleType(double a, double b, double c) {
        if (a <= 0 || b <= 0 || c <= 0 || a + b <= c || a + c <= b || b + c <= a) {
            return "Not a valid triangle";
        }

        // Check for equilateral triangle
        if (a == b && b == c && a == c) {
            return "Equilateral";
        }

        // Check for isosceles triangle
        if (a == b || b == c || a == c) {
            return "Isosceles";
        }

        // Otherwise, it's a scalene triangle
        return "Scalene"; // 不等邊三角形
    }

}
