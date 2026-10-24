/**
 * ============================================================================
 *  LAB 01 · INTERACTIVE SNIPPET
 *  Binary search over a sorted int array
 * ============================================================================
 *
 *  This is a correct, textbook binary search. Read it. Run it on the sample
 *  below — every case passes.
 *
 *  Then, together: this method is used everywhere, it has a formal proof of
 *  correctness in textbooks, and a version of it shipped inside the Java JDK
 *  for years. And yet it has a bug.
 *
 *      ❓ Can you find an input for which this returns the WRONG answer
 *         (or crashes)?
 *
 *  Think about it before anyone touches the keyboard. Where could it possibly
 *  go wrong, if the array is sorted and the key is a normal int?
 * ============================================================================
 */
public class BinarySearch {

    /**
     * Returns an index of {@code key} in the sorted array {@code a},
     * or {@code -1} if the key is not present.
     */
    static int search(int[] a, int key) {
        int low = 0;
        int high = a.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int midValue = a[mid];

            if (midValue < key) {
                low = mid + 1;
            } else if (midValue > key) {
                high = mid - 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] sorted = {1, 3, 4, 7, 9, 11, 15};

        System.out.println(search(sorted, 7));   // expect index 3
        System.out.println(search(sorted, 1));   // expect index 0
        System.out.println(search(sorted, 15));  // expect index 6
        System.out.println(search(sorted, 8));   // expect -1 (absent)

        // All green. So where's the bug?
        // The array is sorted, the key is a normal int. Keep thinking...
    }
}
