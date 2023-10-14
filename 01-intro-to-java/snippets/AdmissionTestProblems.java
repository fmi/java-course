public class AdmissionTestProblems {

    // Create a function that takes an array containing N different integers in the [O, N] interval
    // and returns the missing number in this interval.
    // For example, if the input array is [0, 1, 3], the function should return 2.
    public static int getMissingNo(int a[])
    {
        int i, total, n = a.length;
        total  = n * (n + 1) / 2;
        for ( i = 0; i < n; i++)
            total -= a[i];
        return total;
    }

    // Create two versions of a function that, for a given integer N,
    // calculates a real number: the sum
    //     1 + 1/2 + 1/4 + 1/8 + ... + 1/2^N
    // One of the versions should use recursion, the other – iteration.
    public static double sumIter(int n) {
        double result = 0.0;
        int denominator = 1;
        for (int i = 0; i <= n; i++) {
            result += 1.0 / denominator;
            denominator *= 2;
        }
        return result;
    }

    public static double sumRec(int n) {
        if (n == 0) {
            return 1.0;
        } else {
            return 1.0 / Math.pow(2, n) + sumRec(n - 1);
        }
    }

    public static double sumRecNoPow(int n) {
        return sumRecCalc(n, 1.0, 2);
    }

    private static double sumRecCalc(int n, double sum, int denominator) {
        if (n == 0) {
            return sum;
        } else {
            return sumRecCalc(n - 1, sum += 1.0 / denominator, denominator * 2);
        }
    }

    public static void main(String[] args) {
        System.out.println(getMissingNo(new int[]{0, 1, 3}));
        System.out.println(sumIter(2)); // 1.75
        System.out.println(sumRec(3)); // 1.875
    }
}