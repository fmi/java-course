public class AdmissionTestProblems {

    // Create a function that takes as parameters two binary numbers as strings
    // and returns the minimum number of bit swaps to convert the first binary string into the second.
    // For example, 
    //     minSwaps("1100", "1001") ➞ 1
    //     minSwaps("110011", "010111") ➞ 1
    //     minSwaps("10011001", "01100110") ➞ 4
    // Both binary strings will be of equal length and will have an equal number of zeroes and ones.
    // A swap is switching two arbitrary bits in the binary string.
    public static int minSwaps(String s1, String s2) {
        int differences = 0;
        for (int i = 0; i < s1.length(); ++i) {
            differences += (s1.charAt(i) == s2.charAt(i)) ? 0 : 1;
        }
        return differences / 2;
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
            denominator *=2;
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
        System.out.println(minSwaps("1100", "1001")); // 1
        System.out.println(minSwaps("110011", "010111")); // 1
        System.out.println(minSwaps("10011001", "01100110")); // 4
        System.out.println(sumIter(3)); // 1.875
        System.out.println(sumRec(3)); // 1.875
        System.out.println(sumRecNoPow(4)); // 1.9375
    }

}
