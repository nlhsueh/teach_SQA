package xdemo;

public class BubbleSort {

    public static void main(String[] args) {
        int[] data = new int[] { 5, 4, 3, 2, 1 };

        bubbleSort(data);
        System.out.println("Done");
    }

    /**
     * Sorts an array using the bubble sort algorithm.
     *
     * @param data the array to sort
     */
    public static void bubbleSort(int[] data) {
        int length = data.length;

        // Handle edge case: empty array
        if (length == 0) {
            return;
        }

        for (int pass = 1; pass < length; pass++) {
            boolean swapped = false;

            // Perform a single pass of the bubble sort
            for (int i = 0; i < length - pass - 1; i++) {
                if (data[i] > data[i + 1]) {
                    // Swap elements
                    int temp = data[i];
                    data[i] = data[i + 1];
                    data[i + 1] = temp;
                    swapped = true;
                }
            }

            // If no elements were swapped, the array is already sorted
            if (!swapped) {
                break;
            }
        }
    }
}
