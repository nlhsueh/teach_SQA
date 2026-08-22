package demo;

import java.util.Random;

/**
 * Sort the data
 * @author nienlin hsueh
 */
public final class RobustBubbleSort {

    // the singleton design pattern
    private static final RobustBubbleSort INSTANCE = new RobustBubbleSort();

    private RobustBubbleSort() { }

    /**
     * This is a utility class, so always return the instance
     * @return th instance
     */
    public static RobustBubbleSort getInstance() {
        return INSTANCE;
    }

    /**
     * Generate the data randomly, and sort them
     * @param args: the arg to the main
     */
    public static void main(String[] args) {
        // Generate an array of random integers
        int[] data = generateRandomArray(10, 100);

        System.out.println("\nBefore Sort");
        printArray(data);

        // Sort the array using bubble sort
        bubbleSort(data);

        // 故意改成沒有排序的資料
        data = generateRandomArray(10, 100);

        // check the correctness
        checkSort(data);

        assert sortOK(data);

        System.out.println("\nAfter Sort");
        printArray(data);
    }

    /**
     * check if the data are sort correctly.
     * @param data the array to be checked
     */
    private static void checkSort(final int[] data) {
        final int len = data.length;

        // rewrite this by assert
        for (int i=0; i< len-1; i++) {
            if (data[i+1] < data[i]) {
                System.err.println("Something wrong in sort");
            }
        }
    }

    /**
     * check if the sorting ok, will return boolean for 'assert' use
     * @param data the data to be sorted
     * @return true means sorting is correct
     */
    private static boolean sortOK(final int[] data) {
        final int len = data.length;
        boolean isOk = true;

        // rewrite this by assert
        for (int i=0; i< len-1; i++) {
            if (data[i+1] < data[i]) {
                isOk = false;
                break;
            }
        }
        return isOk;
    }

    /**
     * Generates an array with random integers.
     *
     * @param size the size of the array to generate
     * @param bound the upper bound for the random integers
     * @return an array of random integers
     */
    public static int[] generateRandomArray(final int size, final int bound) {
        final int[] data = new int[size];
        final Random random = new Random();

        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt(bound);
        }

        return data;
    }

    /**
     * Prints the contents of an array.
     *
     * @param data the array to print
     */
    public static void printArray(final int[] data) {
        for (final int x : data) {
            System.out.printf("%4d", x);
        }
        System.out.println();
    }

    /**
     * Sorts an array using the bubble sort algorithm.
     *
     * @param data the array to sort
     */
    public static void bubbleSort(final int[] data) {
        final int length = data.length;

        // Handle edge case: empty array
        if (length == 0) {
            return;
        }

        for (int path = 0; path < length - 1; path++) {
            boolean swapped = false;

            // Perform a single pass of the bubble sort
            for (int i = 0; i < length - path - 1; i++) { //NOPMD
                if (data[i] > data[i + 1]) {
                    // Swap elements
                    final int temp = data[i];
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
