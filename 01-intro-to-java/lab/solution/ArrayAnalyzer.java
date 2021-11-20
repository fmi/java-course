public class ArrayAnalyzer {

    public static boolean isMountainArray(int[] array) {

        if (array.length < 3) {
            return false;
        }

        int i = 0;

        // walk up
        while (i + 1 < array.length && array[i] < array[i + 1]) {
            i++;
        }

        // walk down
        while (i + 1 < array.length && array[i] > array[i + 1]) {
            i++;
        }

        return i == array.length - 1;
    }

}
