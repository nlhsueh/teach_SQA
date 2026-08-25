package u04_utest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BinarySearchTest {

    @Test
    void singleFound() {
        BinarySearch bs = new BinarySearch();
        int[] arr = {7};
        BinarySearch.Result r = bs.search(7, arr);
        assertTrue(r.Found);
        assertEquals(0, r.index);
    }

}