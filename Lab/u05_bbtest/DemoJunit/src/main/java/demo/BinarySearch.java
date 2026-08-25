package demo;

/**
 * 設計一個 `BinarySearch` 類別，包含 `search(int key, int[] array)` 方法，
 * 該方法接受一個目標數字 `key` 和已排序的整數陣列 `array`，並回傳一個 `Result` 物件；
 * `Result` 包含布林值 `Found`，表示是否找到目標數字，及整數 `index`，為目標數字在陣列中的索引位置（若未找到則為 -1）。
 */

public class BinarySearch {

    public static class Result {
        public boolean Found;
        public int index;

        public Result(boolean found, int index) {
            this.Found = found;
            this.index = index;
        }
    }

    public Result search(int key, int[] array) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == key) {
                return new Result(true, mid);
            }

            if (array[mid] < key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return new Result(false, -1); // 若未找到，index 回傳 -1
    }
}
