package xdemo;

public class People {
    private String name;
    private double height;  // 身高 (以公尺為單位)
    private double weight;  // 體重 (以公斤為單位)
    private int birthdayYear; // 生日年
    private People father; // 父親

    // Constructor: 初始化屬性
    public People(String name, double height, double weight, int birthdayYear) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        setBirthdayYear(birthdayYear); // 使用 setter 方法來驗證生日年
        assert height > 0 : "Height must be greater than 0.";
        assert weight > 0 : "Weight must be greater than 0.";
    }

    // 計算 BMI (體重 / 身高的平方)
    public double bmi() {
        double bmiValue = weight / (height * height);
        assert bmiValue >= 10 && bmiValue <= 50 : "BMI value must be between 10 and 50."; // 假設的合理範圍
        return bmiValue;
    }

    // 設定生日年，並透過 assert 確保生日年的合理性 (例如：應在1900-今年之間)
    public void setBirthdayYear(int birthdayYear) {
        int currentYear = java.time.Year.now().getValue();
        assert birthdayYear >= 1900 && birthdayYear <= currentYear : "Birthday year must be between 1900 and the current year.";
        this.birthdayYear = birthdayYear;
    }

    // 設定父親
    public void setFather(People father) {
        this.father = father;
    }

    // 獲取父親
    public People getFather() {
        return father;
    }

    // 簡單的 toString 方法來顯示人員資訊
    @Override
    public String toString() {
        return "Name: " + name + ", Height: " + height + "m, Weight: " + weight + "kg, Birthday Year: " + birthdayYear + ", BMI: " + bmi();
    }

    public static void main(String[] args) {
        // 測試 People 類別
        People john = new People("John", 1.75, 70, 1990);
        System.out.println(john);

        // 測試父親屬性
        People mark = new People("Mark", 180, 85, 1965);
        john.setFather(mark);
        System.out.println("Father: " + john.getFather());

        john.setBirthdayYear(1985);  // 應該不會有問題
        mark.setBirthdayYear(1800);  // 這行會觸發 assert 錯誤，因為1800超出合理範圍
    }
}
