public class AdmissionTestProblems {

    // An online text processing service needs a feature that compresses strings to save space
    // and reduce transmission time – you're the developer assigned to this task.
    // Implement a function that performs a basic string compression using the counts
    // of repeated characters. For example, the string "aabcccccaaa" would become "a2b1c5a3".
    public static String compressString(String input) {
        if (input == null || input.length() <= 1) {
            return input;
        }

        StringBuilder compressed = new StringBuilder();
        int count = 1;  // Initialize the count of matching characters

        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == input.charAt(i - 1)) {
                count++;  // Increment the count if the current character matches the previous one
            } else {
                // Append the character and its count to the compressed string
                compressed.append(input.charAt(i - 1)).append(count);
                count = 1;  // Reset count for the new character
            }
        }

        // Append the last character and its count
        compressed.append(input.charAt(input.length() - 1)).append(count);

        // Check if the compressed string is actually shorter than the original string
        String compressedString = compressed.toString();
        return compressedString.length() < input.length() ? compressedString : input;
    }

    // Create two versions of a function that, for a given integer N >= 0,
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
        System.out.println(compressString("aabcccccaaa"));
        System.out.println(sumIter(2)); // 1.75
        System.out.println(sumRec(3)); // 1.875
    }
}
