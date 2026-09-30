package u02_robust.assertion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SinPractice 泰勒展開式與斷言測試")
class SinPracticeTest {

    private static final double DELTA = 1e-4;

    @Test
    @DisplayName("測試常用特殊角度的 sin 計算精確度")
    void testStandardAngles() {
        // sin(0) = 0
        assertEquals(0.0, SinPractice.calculateSin(0.0), DELTA);

        // sin(30°) = sin(π / 6) = 0.5
        assertEquals(0.5, SinPractice.calculateSin(Math.PI / 6.0), DELTA);

        // sin(90°) = sin(π / 2) = 1.0
        assertEquals(1.0, SinPractice.calculateSin(Math.PI / 2.0), DELTA);

        // sin(180°) = sin(π) = 0.0
        assertEquals(0.0, SinPractice.calculateSin(Math.PI), DELTA);

        // sin(270°) = sin(3π / 2) = -1.0
        assertEquals(-1.0, SinPractice.calculateSin(3 * Math.PI / 2.0), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = { -10.0, -1.0, 0.0, 0.5, 1.57, 3.14, 6.28, 100.0 })
    @DisplayName("驗證後置條件：任意實數之計算結果皆落在 [-1.0, 1.0] 區間")
    void testOutputRangeInvariant(double x) {
        double result = SinPractice.calculateSin(x);
        assertTrue(result >= -1.00001 && result <= 1.00001,
                "計算值超出 [-1, 1] 邊界: " + result);
    }

    @Test
    @DisplayName("測試奇函數對稱性質：sin(-x) == -sin(x)")
    void testSymmetry() {
        double x = 1.234;
        double pos = SinPractice.calculateSin(x);
        double neg = SinPractice.calculateSin(-x);
        assertEquals(-pos, neg, DELTA);
    }

    @Test
    @DisplayName("測試非法輸入防禦：NaN 應拋出 IllegalArgumentException")
    void testInvalidInputThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> SinPractice.calculateSin(Double.NaN));
    }
}
