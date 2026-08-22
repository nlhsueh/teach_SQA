package demo;

import demo.mutant.Triangle02;
import demo.mutant.Triangle03;
import demo.mutant.Triangle04;

import java.util.ArrayList;

public class TriangleMuTest {
    ArrayList<String> mutations = new ArrayList<>();

    public static void main(String[] args) {

        TriangleMuTest main = new TriangleMuTest();
        main.mutations.add("r2");
        main.mutations.add("r3");
        main.mutations.add("r4");

        main.muTest(0, 2, 3);
        main.muTest(2, 0, 1);
        main.muTest(1, 2, 3);
        main.muTest(1, 1, 1);
        main.muTest(2, 2, 1);
    }

    void muTest(int a, int b, int c) {

        String r1 = Triangle.getTriangleType(a, b, c);
        String r2 = Triangle02.getTriangleType(a, b, c);
        String r3 = Triangle03.getTriangleType(a, b, c);
        String r4 = Triangle04.getTriangleType(a, b, c);

        if (! r1.equals(r2)) {
            System.out.println("\t r2 is killed");
            mutations.remove("r2");
        }
        if (! r1.equals(r3)) {
            System.out.println("\t r3 is killed");
            mutations.remove("r3");
        }
        if (! r1.equals(r4)) {
            System.out.println("\t r4 is killed");
            mutations.remove("r4");
        }

        System.out.printf("Test data: %d, %d, %d\t", a, b, c);
        System.out.printf("The alive mutations are: %s\n", mutations);
    }
}
