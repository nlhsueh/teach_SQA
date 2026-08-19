package xdemo;

import java.util.Arrays;

/**
 * 撰寫一個 `price` 方法，根據顧客的年齡、是否為會員以及是否為假日來計算票價：
 * 若顧客年齡超過 70 歲，票價為 50；若為會員，假日票價為 120，平日票價為 70；
 * 若不是會員，假日票價為 150，平日票價為 100。假日定義為週六及週日。
 */
public class SwimmingPool {

    public double price(String day, boolean isMember, int age) {
        double price;
        String[] working = { "Monday", "Tuesday", "Wednsday", "Thursday", "Freday" };
        boolean isHoliday = !Arrays.asList(working).contains(day);
        if (age > 70) {
            price = 50;
        } else if (isMember) {
            if (isHoliday) {
                price = 120;
            } else
                price = 70;
        } else if (isHoliday) {
            price = 150;
        } else
            price = 100;

        return price;
    }
}
