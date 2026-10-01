package u02_robust.assertion;

public class Grade {
    public static void main(String[] args) {
        int g = 101;
        assert g >= 0 && g <= 100 : "Grade must be between 0 and 100";
        System.out.println("Grade is " + g);
        if (g >= 90) {
            System.out.println("A");
        } else if (g >= 80) {
            System.out.println("B");
        } else if (g >= 70) {
            System.out.println("C");
        } else if (g >= 60) {
            System.out.println("D");
        } else {
            System.out.println("F");
        }
    }
}
