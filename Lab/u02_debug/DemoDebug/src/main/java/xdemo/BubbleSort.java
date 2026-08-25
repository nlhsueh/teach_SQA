package xdemo;
import java.util.Random;

/**
 * Discuss what problems of this code; is it easy to determine it is a correct code?
 * Make the result visible!
 * Fix the bug!
 * Remove the bad smell of the code!
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] data;
        data = new int[10];
        Random r = new Random();

        for (int i = 0; i < 10; i++) {
            data[i] = r.nextInt(100);
        }

        BubbleSort bs = new BubbleSort();

        bs.bubbleSort(data);
        System.out.println("Bubble sort complete");
    }

    public void bubbleSort(int[] data) {
        int length = data.length - 1;
        int temp = 0;

        for (int path = 0; path < length; path++) {
            for (int i = 0; i < length - path; i++) {
                if (data[i] > data[i + 1]) {
                    temp = data[i];
                    data[i] = temp;
                    data[i + 1] = data[i];
                }
            }
        }
    }
}