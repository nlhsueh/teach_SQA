package demo;

import static org.junit.Assert.*; // Use JUnit 4 assertions
import org.junit.Test; // Use JUnit 4 test annotation

public class BinarySearchTest {

    @Test
    public void testEmptyFound() {
        BinarySearch bs = new BinarySearch();
        int key = 7;
        int[] data = {};
        int expected = -1;
        assertEquals(expected, bs.search(key,data));
    }

}
