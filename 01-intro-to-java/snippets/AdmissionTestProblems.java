public class AdmissionTestProblems {

	// Create a function that takes as parameters two binary numbers as strings
	// and returns the minimum number of bit swaps to convert the first binary
	// string into the second.
	// For example,
	// minSwaps("1100", "1001") ➞ 1
	// minSwaps("110011", "010111") ➞ 1
	// minSwaps("10011001", "01100110") ➞ 4
	// Both binary strings will be of equal length and will have an equal number of
	// zeroes and ones.
	// A swap is switching two arbitrary bits in the binary string.
	public static int minSwaps(String s1, String s2) {
		int differences = 0;
		for (int i = 0; i < s1.length(); ++i) {
			differences += (s1.charAt(i) == s2.charAt(i)) ? 0 : 1;
		}
		return differences / 2;
	}

	// Create two versions of a function that, for two given integers N and M,
	// calculates a real number: the sum
	// 1/M + 2/M^2 + 3/M^3 + ... + N/M^N
	// All divisions are real-number divisions.
	// One of the versions should use recursion, the other – iteration.
	public static double sumIter(int n, int m) {
		double result = 0.0;
		int denominator = m;
		for (int i = 1; i <= n; i++) {
			result += i / (double) denominator;
			denominator *= m;
		}
		return result;
	}

	public static double sumRec(int n, int m) {
		if (n == 1) {
			return 1.0 / m;
		} else {
			return (double) n / Math.pow(m, n) + sumRec(n - 1, m);
		}
	}

	public static void main(String[] args) {
		System.out.println(minSwaps("1100", "1001")); // 1
		System.out.println(minSwaps("110011", "010111")); // 1
		System.out.println(minSwaps("10011001", "01100110")); // 4
		System.out.println(sumIter(3, 2)); // 1.375
		System.out.println(sumRec(3, 2)); // 1.375
	}
}