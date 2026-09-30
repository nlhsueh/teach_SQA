package u02_robust.assertion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TriangleAssertionPractice 三角形分類與斷言防禦測試")
class TriangleAssertionPracticeTest {

    @ParameterizedTest
    @CsvSource({
            "3, 3, 3, EQUILATERAL",
            "5, 5, 8, ISOSCELES",
            "5, 8, 5, ISOSCELES",
            "8, 5, 5, ISOSCELES",
            "3, 4, 5, SCALENE",
            "1, 2, 5, NOT_TRIANGLE",
            "2, 3, 5, NOT_TRIANGLE"
    })
    @DisplayName("測試各類邊長組合之三角形分類結果")
    void testClassifyTriangle(int a, int b, int c, TriangleAssertionPractice.TriangleType expected) {
        assertEquals(expected, TriangleAssertionPractice.classifyTriangle(a, b, c));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 4, 5",
            "-1, 2, 3",
            "3, -4, 5",
            "3, 4, -5"
    })
    @DisplayName("測試公開 API 防禦：非正整數邊長應拋出 IllegalArgumentException")
    void testNonPositiveSidesThrowException(int a, int b, int c) {
        assertThrows(
                IllegalArgumentException.class,
                () -> TriangleAssertionPractice.classifyTriangle(a, b, c)
        );
    }
}
