package u02_robust.assertion;

public class Hello {

    public static void main(String[] args) {
        int x = 100;
        assert x > 1000 : "x小於1000";
        System.out.println("Hello");
    }

}
