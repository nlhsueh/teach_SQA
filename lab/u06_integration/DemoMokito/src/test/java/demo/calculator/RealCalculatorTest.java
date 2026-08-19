package demo.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RealCalculatorTest {
    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new RealCalculatorService(); // 測試真實的實作
    }

    @Test
    public void testAdd() {
        assertEquals(5, calculatorService.add(2, 3));
    }

    @Test
    public void testSubtract() {
        assertEquals(2, calculatorService.subtract(5, 3));
    }
}
