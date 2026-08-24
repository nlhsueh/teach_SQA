/**
 * This is example code for introducing BASIC PATH TESTING
 * (n) is a NODE, mapping the flow chart diagram
 */

public class GradeAverage {

    public double computeAverage(int[] grade) {
        // (1)
        int sum = 0, max=100, min=10, valid=0, index=0;
        double average;

        //                  (2)             (3)
        while (grade[index] != -999 && index < 50) {
            //               (4)                  (5)
            if (grade[index] >= min && grade[index] <= max) {
                // (6)
                sum += grade[index];
                valid++;
            } else {
                System.out.println("成績範圍錯誤"); // (7)
            }
            index++; // (8)
        }
        //       (9)
        if (valid > 0)
            average = (double) sum / valid; // (10)
        else
            average = -999; // (11)

        return average; // (12)
    }
}
