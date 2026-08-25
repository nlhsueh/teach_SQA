package demo;

/**
 * 設計一個貸款利率計算程式，根據貸款年限、貸款金額、是否為青年及婚姻狀況計算利率；規則如下：
 * 基礎利率為 5%，貸款超過 10 年折扣 0.5%，超過 20 年折扣 1%；若貸款金額超過(含) 500 萬則再每 100萬 減少 0.1%;
 * 若申請者為青年則減少 1%，若已婚則再減少 0.5%；利率最低為 2%。
 */

public class LoanCalculator {

    public static double calculateInterestRate(int loanYears, double loanAmount, boolean isYouth, boolean isMarried) {
        double baseRate = 5.0; // 基礎利率為 5%
        double discount = 0.0;

        // 貸款年限折扣
        if (loanYears > 20) {
            discount += 1.0;
        } else if (loanYears > 10) {
            discount += 0.5;
        }

        // 貸款金額折扣
        if (loanAmount >= 5_000_000) {
            double over = (loanAmount - 4_999_999);
            discount += Math.ceil(over / 1_000_000) * 0.1;
            System.out.println(discount);
        }

        // 青年折扣
        if (isYouth) {
            discount += 1.0;
        }

        // 已婚折扣
        if (isMarried) {
            discount += 0.5;
        }

        // 計算最終利率
        double finalRate = baseRate - discount;

        // 最低利率限制
        if (finalRate < 2.0) {
            finalRate = 2.0;
        }

        return finalRate;
    }

    public static void main(String[] args) {
        int years = 15; // 貸款年限
        double amount = 500000000; // 貸款金額
        boolean isYouth = true; // 是否為青年
        boolean isMarried = false; // 是否已婚

        double interestRate = calculateInterestRate(years, amount, isYouth, isMarried);
        System.out.println("最終貸款利率為: " + interestRate + "%");
    }
}
