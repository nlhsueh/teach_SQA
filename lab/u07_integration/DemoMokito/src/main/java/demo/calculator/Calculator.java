package demo.calculator;

public class Calculator {
    private final CalculatorService service;

    public Calculator(CalculatorService service) {
        this.service = service;
    }

    public int add(int a, int b) {
        return service.add(a, b);
    }

    public int subtract(int a, int b) {
        return service.subtract(a, b);
    }

    public int multiply(int a, int b) {
        return service.multiply(a, b);
    }

    public int divide(int a, int b) {
        return service.divide(a, b);

//        if (b == 0) {
//            throw new IllegalArgumentException("Division by zero is not allowed");
//        }
//        return service.divide(a, b);
    }

}
