package demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BinarySearchTest {

    @Test
    void singleFound() {
        BinarySearch bs = new BinarySearch();
        int[] arr = {7};
        demo.BinarySearch.Result r = bs.search(7, arr);
        assertEquals(true, r.Found);
        assertEquals(0, r.index);
    }

}