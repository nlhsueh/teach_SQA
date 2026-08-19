package xdemo;

/**
 * This program may have BUGS
 * Design test cases to test this program
 */
public class People {
    private String name;
    private double height; // 身高 (以公尺為單位)
    private double weight; // 體重 (以公斤為單位)
    private double bmi;
    private int birthdayYear; // 生日年
    private People father; // 父親

    // Constructor: 初始化屬性
    public People(String name, double height, double weight, int birthdayYear) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.bmi = weight / (height * height);
        setBirthdayYear(birthdayYear); // 使用 setter 方法來驗證生日年
    }

    // 計算 BMI (體重 / 身高的平方)
    public double bmi() {
        return this.bmi;
    }

    // 設定生日年，並透過 assert 確保生日年的合理性 (例如：應在1900-今年之間)
    public void setBirthdayYear(int birthdayYear) {
        int currentYear = java.time.Year.now().getValue();
        this.birthdayYear = birthdayYear;
    }

    // 設定新的體重
    public void setWeight(int newWeight) {
        this.weight = newWeight;
    }

    // 設定新的身高
    public void setHeight(int newHeight) {
        this.height = newHeight;
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
        return "Name: " + name + ", Height: " + height + "m, Weight: " + weight + "kg, Birthday Year: " + birthdayYear
                + ", BMI: " + bmi();
    }

    public static void main(String[] args) {
        // 測試 People 類別
        People person = new People("John", 1.75, 70, 1990);
        System.out.println(person);

        // 測試父親屬性
        People father = new People("Mark", 1.80, 85, 1965);
        person.setFather(father);
        System.out.println("Father: " + person.getFather());

        person.setBirthdayYear(1985); // 應該不會有問題
        person.setBirthdayYear(1800); // 這行會觸發 assert 錯誤，因為1800超出合理範圍
    }
}
