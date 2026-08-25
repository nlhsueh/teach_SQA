package xdemo;

import java.util.Arrays;

/**
 */
public class SwimmingPool {

    public double price(String day, boolean isMember, boolean isGroup, int age, boolean early) {
        double price=200;
        String[] working = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
        boolean isWeekend = !Arrays.asList(working).contains(day);
        if (isMember) {
            if (!isWeekend) price = 100;
            else price = 125;
        }
        else {
            if (age >= 60 || age <= 12 || early) {
                price = 160;
            } else if (isGroup) {
                price = 140;
            }
        }
        return price;
    }

    public double ticket_price(String day, boolean isMember, int age) {
        double price;
        String[] working = { "Monday", "Tuesday", "Wdnesday", "Thursday", "Frday" }; // 故意寫錯的星期
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
