package u02_robust.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TriangleExceptionDemo 自訂例外測試")
class TriangleExceptionDemoTest {

    @Test
    @DisplayName("測試合法三角形正常建立")
    void testValidTriangles() throws TriangleExceptionDemo.TriangleException {
        assertTrue(TriangleExceptionDemo.classify(3, 3, 3).contains("正三角形"));
        assertTrue(TriangleExceptionDemo.classify(5, 5, 8).contains("等腰三角形"));
        assertTrue(TriangleExceptionDemo.classify(3, 4, 5).contains("一般不等邊三角形"));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 5, 5",
            "-2, 4, 4",
            "1, 2, 5",
            "10, 2, 2"
    })
    @DisplayName("測試無效邊長拋出自訂 TriangleException")
    void testInvalidTrianglesThrowException(int a, int b, int c) {
        TriangleExceptionDemo.TriangleException ex = assertThrows(
                TriangleExceptionDemo.TriangleException.class,
                () -> TriangleExceptionDemo.classify(a, b, c)
        );
        assertEquals(a, ex.getA());
        assertEquals(b, ex.getB());
        assertEquals(c, ex.getC());
        assertNotNull(ex.getReason());
    }
}
