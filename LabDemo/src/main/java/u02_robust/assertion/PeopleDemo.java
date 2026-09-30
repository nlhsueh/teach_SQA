package u02_robust.assertion;

import java.time.Year;

/**
 * 類別不變量與狀態斷言展示範例：Person / People
 *
 * <p>本範例展示軟體品質保證 (SQA) 中的「類別不變量 (Class Invariant)」與「內部狀態斷言」：</p>
 * <ul>
 *   <li><b>公開 API 參數驗證</b>：在建構子與 setter 中驗證外部傳入參數，若不合法則拋出 IllegalArgumentException。</li>
 *   <li><b>類別不變量 (Class Invariant)</b>：物件在任何公開方法執行後，內部狀態必須維持合理範圍（如身高、體重 > 0）。</li>
 *   <li><b>內部計算斷言</b>：計算 BMI 時，斷言計算結果落在人體生理合理範圍內，防止公式誤寫。</li>
 * </ul>
 */
public class PeopleDemo {
    private final String name;
    private double height;      // 身高（公尺）
    private double weight;      // 體重（公斤）
    private int birthYear;      // 出生年份
    private PeopleDemo father;  // 父親參照

    /**
     * 建構子：初始化人物屬性。
     *
     * @param name 姓名
     * @param height 身高（公尺）
     * @param weight 體重（公斤）
     * @param birthYear 出生年份
     * @throws IllegalArgumentException 若姓名為空或數值不合理
     */
    public PeopleDemo(String name, double height, double weight, int birthYear) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("姓名不可為空");
        }
        if (height <= 0 || weight <= 0) {
            throw new IllegalArgumentException("身高與體重必須大於 0");
        }
        int currentYear = Year.now().getValue();
        if (birthYear < 1900 || birthYear > currentYear) {
            throw new IllegalArgumentException("出生年份必須介於 1900 與今年 (" + currentYear + ") 之間");
        }

        this.name = name;
        this.height = height;
        this.weight = weight;
        this.birthYear = birthYear;

        // 類別不變量斷言：確認內部狀態初始化完成後符合假設
        assert checkClassInvariant() : "初始化後違反類別不變量！";
    }

    /**
     * 類別不變量純函式：無副作用，僅回傳布林值。
     */
    private boolean checkClassInvariant() {
        int currentYear = Year.now().getValue();
        return height > 0.0 && height < 3.0
                && weight > 0.0 && weight < 500.0
                && birthYear >= 1900 && birthYear <= currentYear;
    }

    /**
     * 計算 BMI (體重 / 身高^2)。
     *
     * @return BMI 數值
     */
    public double calculateBmi() {
        assert checkClassInvariant() : "呼叫 calculateBmi 前違反類別不變量！";

        double bmi = weight / (height * height);

        // 內部斷言：確認計算公式未發生嚴重溢位或負數
        assert bmi > 5.0 && bmi < 150.0 : "計算出的 BMI 數值異常: " + bmi;

        return bmi;
    }

    public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("身高必須大於 0");
        }
        this.height = height;
        assert checkClassInvariant() : "修改身高後違反類別不變量！";
    }

    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("體重必須大於 0");
        }
        this.weight = weight;
        assert checkClassInvariant() : "修改體重後違反類別不變量！";
    }

    public void setFather(PeopleDemo father) {
        // 內部斷言：父親的年齡必須比自己大
        if (father != null) {
            assert father.birthYear < this.birthYear :
                    String.format("邏輯錯誤：父親出生年 (%d) 應早於子女出生年 (%d)", father.birthYear, this.birthYear);
        }
        this.father = father;
    }

    public PeopleDemo getFather() {
        return father;
    }

    public String getName() {
        return name;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public int getBirthYear() {
        return birthYear;
    }

    @Override
    public String toString() {
        return String.format("People[姓名=%s, 身高=%.2fm, 體重=%.1fkg, 出生年=%d, BMI=%.1f]",
                name, height, weight, birthYear, calculateBmi());
    }

    public static void main(String[] args) {
        System.out.println("=== 類別不變量斷言示範：PeopleDemo ===");

        // 正常建立物件
        PeopleDemo john = new PeopleDemo("John", 1.75, 70.0, 1995);
        PeopleDemo mark = new PeopleDemo("Mark", 1.80, 82.0, 1970);
        john.setFather(mark);
        System.out.println("建立成功: " + john);
        System.out.println("父親資訊: " + john.getFather());

        // 示範 1：公開 API 傳入非法參數觸發 IllegalArgumentException
        try {
            System.out.println("\n[測試 1] 傳入負數身高...");
            new PeopleDemo("Alice", -1.6, 50.0, 2000);
        } catch (IllegalArgumentException e) {
            System.out.println(">> 成功捕捉預期例外 (IllegalArgumentException): " + e.getMessage());
        }

        // 示範 2：若內部關係不合邏輯，觸發 AssertionError
        try {
            System.out.println("\n[測試 2] 設定出生年份晚於自己的父親（違反邏輯關係）...");
            PeopleDemo babyFather = new PeopleDemo("BabyFather", 1.70, 65.0, 2010);
            john.setFather(babyFather);
        } catch (AssertionError e) {
            System.err.println("🛑 成功觸發斷言錯誤！" + e.getMessage());
        }
    }
}
