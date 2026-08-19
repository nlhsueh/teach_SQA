package demo;

import java.util.Random;

public class BubbleSort {

    public static void main(String[] args) {
        // Generate an array of random integers
        int[] data = generateRandomArray(10, 100);

        System.out.println("\nBefore Sort");
        printArray(data);

        // Sort the array using bubble sort
        bubbleSort(data);

        System.out.println("\nAfter Sort");
        printArray(data);
    }

    /**
     * Generates an array with random integers.
     *
     * @param size the size of the array to generate
     * @param bound the upper bound for the random integers
     * @return an array of random integers
     */
    public static int[] generateRandomArray(int size, int bound) {
        int[] data = new int[size];
        Random random = new Random();

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
    public static void printArray(int[] data) {
        for (int x : data) {
            System.out.printf("%4d", x);
        }
        System.out.println();
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

        for (int path = 0; path < length - 1; path++) {
            boolean swapped = false;

            // Perform a single pass of the bubble sort
            for (int i = 0; i < length - path - 1; i++) {
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
