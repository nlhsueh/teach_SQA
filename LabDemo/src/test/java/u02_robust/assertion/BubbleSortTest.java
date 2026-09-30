package u02_robust.assertion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BubbleSort 斷言與單元測試")
class BubbleSortTest {

    @Test
    @DisplayName("測試正常排序與後置條件斷言")
    void testBubbleSortSuccess() {
        int[] data = { 5, 2, 8, 1, 9, 3 };
        int[] expected = { 1, 2, 3, 5, 8, 9 };

        BubbleSort.bubbleSort(data);

        assertArrayEquals(expected, data);
        assertTrue(BubbleSort.isSorted(data));
    }

    @Test
    @DisplayName("測試空陣列與單一元素陣列（邊界條件）")
    void testEdgeCases() {
        int[] empty = {};
        BubbleSort.bubbleSort(empty);
        assertEquals(0, empty.length);

        int[] single = { 42 };
        BubbleSort.bubbleSort(single);
        assertArrayEquals(new int[] { 42 }, single);
    }

    @Test
    @DisplayName("測試已排序與逆序陣列")
    void testSortedAndReversed() {
        int[] sorted = { 1, 2, 3, 4, 5 };
        BubbleSort.bubbleSort(sorted);
        assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, sorted);

        int[] reversed = { 5, 4, 3, 2, 1 };
        BubbleSort.bubbleSort(reversed);
        assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, reversed);
    }

    @Test
    @DisplayName("測試含有重複元素的陣列")
    void testDuplicates() {
        int[] data = { 3, 1, 3, 2, 1, 2 };
        int[] expected = { 1, 1, 2, 2, 3, 3 };
        BubbleSort.bubbleSort(data);
        assertArrayEquals(expected, data);
    }

    @Test
    @DisplayName("測試公開 API 防禦：傳入 null 應拋出 IllegalArgumentException")
    void testNullArrayThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> BubbleSort.bubbleSort(null)
        );
        assertTrue(exception.getMessage().contains("null"));
    }

    @Test
    @DisplayName("測試有缺陷的排序在啟用斷言時拋出 AssertionError")
    void testBuggySortTriggersAssertionError() {
        int[] reversed = { 5, 4, 3, 2, 1 };
        assertThrows(
                AssertionError.class,
                () -> BubbleSort.buggyBubbleSort(reversed),
                "未開啟 -ea 時可能不會拋出，若 Maven 測試已啟用斷言則必須拋出 AssertionError"
        );
    }
}
