import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BreakpointTest {

    @Test
    void testExchange() {
        int[] data = { 1, 2, 3, 10, 9, 6, 7 };
        int[] expected = { 1, 9, 3, 10, 2, 6, 7 };

        // Swap the 2nd and 5th elements (index 1 and 4)
        int[] result = Breakpoint.exchange(data.clone(), 1, 4);

        assertArrayEquals(expected, result);
    }

    @Test
    void testSort() {
        int[] data = { 3, 2, 1, 7, 6, 5, 4 };
        int[] expected = { 1, 2, 3, 4, 5, 6, 7 };

        BreakpointDemo.sort(data);

        assertArrayEquals(expected, data);
    }

    @Test
    void testExchangeWithOutOfBounds() {
        int[] data = { 1, 2, 3, 4 };
        Exception exception = assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            BreakpointDemo.exchange(data, 1, 5); // index 5 is out of bounds
        });
        String expectedMessage = "Index 5 out of bounds for length 4";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
