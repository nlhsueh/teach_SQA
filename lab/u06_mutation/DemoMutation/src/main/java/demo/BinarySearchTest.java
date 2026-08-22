package demo;

import demo.mutant.*;

import java.util.ArrayList;
import java.util.Arrays;

public class BinarySearchTest {

    ArrayList<String> mutations = new ArrayList<>();
    BinarySearch bs01 = new BinarySearch();
    BinarySearch02 bs02 = new BinarySearch02();
//    BinarySearch03 bs03 = new BinarySearch03();

    public static void main(String[] args) {

        BinarySearchTest main = new BinarySearchTest();
        main.mutations.add("bs02");
//        main.mutations.add("bs03");

        main.muTest(0, new int[] {});               // test data 01
        main.muTest(1, new int[] {0, 1, 2});        // test data 02
        main.muTest(9, new int[] {0, 1, 2, 3, 4});  // test data 03

    }

    public void muTest(int key, int[] data) {

        if (bs01.search(key, data) != bs02.search(key, data)) {
            System.out.println("\t bs02 is killed");
            mutations.remove("bs02");
        }

//        if (bs01.search(key, data) != bs03.search(key, data)) {
//            System.out.println("\t bs03 is killed");
//            mutations.remove("bs03");
//        }

        System.out.printf("Test data: %d %s\n", key, Arrays.toString(data));
        System.out.printf("The alive mutations are: %s\n", mutations);
    }
}


