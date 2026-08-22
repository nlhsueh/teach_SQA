package demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;


record CalculatorTest() {

    @Test
    @DisplayName("Calculator ok 的啦")
    void test_plus() {
        Calculator cal = new Calculator();
        int expected = 2;
        int real = cal.add(1,1);
        assertEquals(expected, real);
    }

    @Test
    void test_assertAll() {
        Calculator cal = new Calculator();
        assertAll("test assert all on cal",
                ()-> assertEquals(2, cal.add(1,1)),
                ()-> assertEquals(0, cal.add(1, -1))
        );
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Calculator div OK 的啦")
    void test_div() throws Exception {
        Calculator cal = new Calculator();
        int expected = 2;
        double real = cal.divide(4,2);
        assertEquals(expected, real);
    }

}