package xdemo;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

/**
 * This program may have BUGS
 * Design test cases to test this program
 */
public class MyDate {

    int y, m, d;
    String month; // January, etc.
    static Integer[] solarMonth = { 1, 3, 5, 7, 8, 10, 12 };
    static Integer[] lunarMonth = { 4, 6, 9, 10, 11 };
    static int[] leapDays = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    static int[] normalDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    public MyDate(int y, String m, int d) {
        this.y = y;
        this.month = m;
        this.m = to_m(month);
        this.d = d;
    }

    public MyDate(int y, int m, int d) {
        this.y = y;
        this.m = m;
        this.d = d;
    }

    /**
     * 計算星期
     * 只要知道這天和 1900 的日期差即可推算。我們知道 1900/1/1 是 Monday。
     */
    public String dayOfWeek() {
        String[] dayOfWeekString = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

        int diffTo1900 = diffTo1900_1_1(this);
        int r = diffTo1900 % 7; // 1 because 1900/1/1 is Monday

        return dayOfWeekString[r - 1];
    }

    /**
     * 計算明天
     * 原則上都是 d++, 但遇到月底的時候需要 reset,
     * 要特別注意閏二月和十二月
     */
    public MyDate tomorrow() {
        int _y = y;
        int _d = d;
        int _m = m;

        boolean isLeap = (y % 400 == 0) || (y % 4 == 0 && y % 100 != 0); // 閏年
        boolean isFeb = (m == 2);
        boolean isSolar = Arrays.asList(solarMonth).contains(m); // 大月
        boolean isLunar = Arrays.asList(lunarMonth).contains(m); // 小月

        boolean isNormal228 = !isLeap && isFeb && d == 28; // 平年二月底
        boolean isLeap229 = isLeap && isFeb && d == 29; // 閏年二月底
        boolean isEndOfMonth = (isSolar && d == 31) || (isLunar && d == 30) || isNormal228 || isLeap229; // 月底

        if (isEndOfMonth) {
            _m++;
            _d = 1; // advanced to next month
        } else
            _d++;

        if (_m == 13) { // case of 12/31
            _m = 1;
            _y++; // advanced to next year
        }

        return new MyDate(_y, _m, _d);
    }

    /**
     * 距離 1900/1/1 的天數
     * = 之前年的天數 + 該年度第幾天 - 1
     */
    public static int diffTo1900_1_1(MyDate d) {
        int daysTo1900_1_1 = 0;
        for (int i = 1900; i < d.y; i++) {
            daysTo1900_1_1 += year_days(i);
        }
        daysTo1900_1_1 += daysOfYear(d);
        return daysTo1900_1_1 - 1;
    }

    /**
     * 該年第幾天
     */
    public static int daysOfYear(MyDate d) {
        int doy = 0;
        for (int i = 1; i < d.m; i++) {
            if (isLeapYear(d.y))
                doy += leapDays[i - 1];
            else
                doy += normalDays[i - 1];
        }
        doy += d.d;
        return doy;
    }

    /** 該年有幾天 */
    public static int year_days(int y) {
        return isLeapYear(y) ? 366 : 365;
    }

    /** 是否為閏年 */
    public static boolean isLeapYear(int y) {
        return (y % 400 == 0) || (y % 4 == 0 && y % 100 != 0);
    }

    /** 轉為整數的月數 1.. 12 */
    public static int to_m(String month) {
        return Integer.parseInt(month);
    }

    /** 轉為 Jan, Feb 等英文的月 */
    public static String to_Month(int m) {
        return String.valueOf(m);
    }

    /** 明天星期幾 */
    public String tomorrowDayOfWeek() {
        return this.tomorrow().dayOfWeek();
    }

    /** 印出本日期, 如 2000-12-1 */
    public String toString() {
        String[] r = { String.valueOf(y), String.valueOf(m), String.valueOf(d) };
        return StringUtils.join(r, "-");
    }
}