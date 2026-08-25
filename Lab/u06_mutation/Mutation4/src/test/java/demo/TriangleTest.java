package demo;

import static org.junit.Assert.*; // Use JUnit 4 assertions
import org.junit.Test; // Use JUnit 4 test annotation

public class TriangleTest {

    @Test
    public void testValidTriangle() {
        assertEquals("Not a valid triangle",
                Triangle.getTriangleType(-1, -2, -1));
    }

    @Test
    public void testEquilateral() {
        assertEquals("Equilateral",
                Triangle.getTriangleType(2,2,2));
    }

}