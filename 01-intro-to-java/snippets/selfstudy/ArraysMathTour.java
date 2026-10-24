import java.util.Arrays;
import java.util.Collections;

/**
 * ============================================================================
 *  LAB 01 · SELF-STUDY SNIPPET  (NOT walked through in the lab) 
 *  Arrays & Math API tour — your copy-paste reference
 * ============================================================================
 */
public class ArraysMathTour {

    static void declareAndInit() {
        int[] a;                        // declaration — NO memory for elements yet
        a = new int[5];                 // init — now {0, 0, 0, 0, 0} (primitives default to 0)

        int[] b = {1, 2, 3, 4};         // declare + init with a literal (only at declaration)
        char[] c = new char[]{'a','b'}; // explicit new form, also valid
        int len = b.length;             // length is a FIELD on arrays (no parentheses)

        // multidimensional + jagged (rows of different lengths)
        int[][] grid = new int[3][4];  // 3×4, all zeros
        double[][] jagged = new double[3][]; // 3 rows, rows not yet created
        for (int i = 0; i < jagged.length; i++) {
            jagged[i] = new double[i + 1];   // row i has i+1 elements
        }
        System.out.println(len + " " + Arrays.deepToString(jagged));
    }

    static void commonArrayOps() {
        int[] x = {5, 1, 4, 2, 3};

        Arrays.sort(x);                              // in place → {1,2,3,4,5}
        int found = Arrays.binarySearch(x, 4);       // 3 (index) — needs a SORTED array
        int[] copy = Arrays.copyOf(x, x.length);     // independent copy
        boolean eq = Arrays.equals(x, copy);         // true (element-by-element)
        String text = Arrays.toString(x);            // "[1, 2, 3, 4, 5]"
        Arrays.fill(copy, 0);                        // {0,0,0,0,0}

        // sort descending needs the boxed type + a reverse comparator
        Integer[] boxed = {5, 1, 4, 2, 3};
        Arrays.sort(boxed, Collections.reverseOrder()); // {5,4,3,2,1}

        // 🧠 THE ONE TRICKY BIT: arrays are objects, so `x == copy` is reference
        //    comparison (false even if contents match). Use Arrays.equals for
        //    1-D, and Arrays.deepEquals for nested arrays:
        int[][] board1 = {{1, 0}, {0, 1}};
        int[][] board2 = {{1, 0}, {0, 1}};
        boolean shallow = Arrays.equals(board1, board2);     // false! (compares inner refs)
        boolean deep    = Arrays.deepEquals(board1, board2); // true  (compares contents)

        System.out.println(found + " " + eq + " " + text + " " + Arrays.toString(boxed)
            + " shallow=" + shallow + " deep=" + deep);
    }

    static void mathTour() {
        double v = Math.abs(-3.5);        // 3.5
        int    lo = Math.min(4, 9);       // 4
        int    hi = Math.max(4, 9);       // 9
        double p  = Math.pow(2, 10);      // 1024.0
        double r  = Math.sqrt(144);       // 12.0
        long   rd = Math.round(2.7);      // 3
        double rand = Math.random();      // in [0.0, 1.0)

        // Math.clamp (Java 21+): keep a value inside [min, max] in one call,
        // instead of an if/else-if ladder.
        int clamped = Math.clamp(130, 0, 100); // 100

        System.out.println(v + " " + lo + " " + hi + " " + p + " " + r + " "
            + rd + " " + (rand >= 0) + " " + clamped);
    }

    public static void main(String[] args) {
        declareAndInit();
        commonArrayOps();
        mathTour();
    }
}
