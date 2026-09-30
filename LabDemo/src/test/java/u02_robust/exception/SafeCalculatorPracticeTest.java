package u02_robust.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SafeCalculatorPractice 安全計算機測試")
class SafeCalculatorPracticeTest {

    @Test
    @DisplayName("測試正常整數除法")
    void testNormalDivision() {
        SafeCalculatorPractice.CalculationResult res = SafeCalculatorPractice.safeDivide("100", "5");
        assertTrue(res.isSuccess());
        assertEquals(20, res.getValue());
        assertNull(res.getErrorMessage());
    }

    @Test
    @DisplayName("測試除以零異常捕捉")
    void testDivisionByZero() {
        SafeCalculatorPractice.CalculationResult res = SafeCalculatorPractice.safeDivide("50", "0");
        assertFalse(res.isSuccess());
        assertTrue(res.getErrorMessage().contains("除數不能為零"));
    }

    @Test
    @DisplayName("測試非法數字格式異常捕捉")
    void testInvalidNumberFormat() {
        SafeCalculatorPractice.CalculationResult res = SafeCalculatorPractice.safeDivide("hello", "10");
        assertFalse(res.isSuccess());
        assertTrue(res.getErrorMessage().contains("格式錯誤") || res.getErrorMessage().contains("解析失敗"));
    }

    @Test
    @DisplayName("測試 null 參數防禦")
    void testNullInputs() {
        SafeCalculatorPractice.CalculationResult res = SafeCalculatorPractice.safeDivide(null, "5");
        assertFalse(res.isSuccess());
    }
}
