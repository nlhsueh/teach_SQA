package xdemo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobustBubbleSortTest {

    @Test
    void bubbleSort() {
        int[] data = {5,4,3,2,1};
        int[] expected = {1,2,3,4,5};
        RobustBubbleSort rbs = new RobustBubbleSort(data);
        rbs.bubbleSort();
//        assertArrayEquals(expected, rbs.sorted());
    }
}