package demo;

public class ExceptionExample {
    public static void main(String[] args) {
        try {
            int result = 10 / 0; // 這裡會引發 ArithmeticException
        } catch (ArithmeticException e) {
            System.out.println("捕捉到例外: " + e.getMessage());
        }
        System.out.println("Hello, Nice to meet you");

//        (new ExceptionExample()).divideByZero();;
    }

    public void divideByZero() {
        // This will cause a unchecked exception, and terminate the program
        double zero = 0.0;
        int x = 10 / 0;
        System.out.println("Hello, I am divided by zero");
    }
}
