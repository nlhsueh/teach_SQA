package demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 測試以下的測試案例，觀察其 Coverage 的變化。
 * 擴充測試案例，使得 branch coverage 為 100%; 若無法達成，為什麼？
 */

class TriangleTest {

    @Test
    void not_valid_triangle() {
        assertAll("not validate",
                ()-> assertEquals("Not a valid triangle", Triangle.getTriangleType(-1, 2, 3)),
                ()-> assertEquals("Not a valid triangle", Triangle.getTriangleType(2, -2, 3)),
                ()-> assertEquals("Not a valid triangle", Triangle.getTriangleType(2, 2, -3))
                )
        ;
    }


}