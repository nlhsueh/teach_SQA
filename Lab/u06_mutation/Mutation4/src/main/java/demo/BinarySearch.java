package demo;

/**
 * 設計一個 `BinarySearch` 類別，包含 `search(int key, int[] array)` 方法，
 * 該方法接受一個目標數字 `key` 和已排序的整數陣列 `array`，並回傳一個整數表示該資料所在位置；
 * 如果沒有找到，就回傳 -1
 */

public class BinarySearch {

    public int search(int key, int[] array) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == key) {
                return mid;
            }

            if (array[mid] < key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }
}
