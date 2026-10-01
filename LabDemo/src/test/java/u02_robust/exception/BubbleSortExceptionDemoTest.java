package u02_robust.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BubbleSortExceptionDemoTest {

    @Test
    @DisplayName("bubbleSort 傳入 null 應拋出 IllegalArgumentException")
    void testBubbleSortNullThrowsIllegalArgument() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            BubbleSortExceptionDemo.bubbleSort(null);
        });
        assertTrue(ex.getMessage().contains("不可為 null"));
    }

    @Test
    @DisplayName("bubbleSort 正常排序非遞減數列")
    void testBubbleSortSuccess() throws BubbleSortExceptionDemo.SortingException {
        int[] data = { 5, 2, 8, 1, 9 };
        BubbleSortExceptionDemo.bubbleSort(data);
        assertArrayEquals(new int[]{ 1, 2, 5, 8, 9 }, data);
    }

    @Test
    @DisplayName("buggyBoundarySort 應引發 ArrayIndexOutOfBoundsException")
    void testBuggyBoundarySortThrowsArrayIndexOutOfBounds() {
        int[] data = { 4, 3, 2, 1 };
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            BubbleSortExceptionDemo.buggyBoundarySort(data);
        });
    }

    @Test
    @DisplayName("recursiveBubbleSortWithBug 應引發 StackOverflowError")
    void testRecursiveBubbleSortWithBugThrowsStackOverflow() {
        int[] data = { 3, 2, 1 };
        assertThrows(StackOverflowError.class, () -> {
            BubbleSortExceptionDemo.recursiveBubbleSortWithBug(data, data.length);
        });
    }
}
