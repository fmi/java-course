import java.util.Arrays;
import java.util.Collections;

public class IntroToJava {

	public static void workWithStrings() {

		// 1. string literals and the string pool
		String s1 = "FMI"; // goes to string pool
		String s2 = "FMI"; // "FMI" is present in the String pool -> s2 will refer to the same object

		System.out.println(s1 == s2); // true

		String s3 = new String("FMI"); // new String object in the heap

		System.out.println(s1 == s3); // false, s1 refers to an object in the string pools, s2 refers to an object in the heap
		System.out.println(s1.equals(s3)); // true: string content is the same

		String s4 = s3.intern(); //  String "interning" will add the string in the pool and return a reference to it
		System.out.println(s1 == s3); // false, s3 is not reassigned
		System.out.println(s1 == s4); // true

		// 2. string concatenation and splitting
		int year = 2018;
		String message = "Current year is ";
		message += 2018 + "."; // String are immutable so this will create a new String object and some garbage

		String[] words = message.split(" ");

		// 3. string iteration and conversion String -> char array
		String s5 = "Example";
		char c = s5.charAt(1); // 'x'. Note that Strings are not char arrays, i.e. you cannot do e.g. s5[1];
		char[] carr = s5.toCharArray(); // convert a String to char array

		for (int i = 0; i < carr.length; i++) { // iterate the char array with standard for-loop
			System.out.println(carr[i]);
		}

		for (char ch : carr) { // the same as above but with for-each loop -> recommended because it's shorter
			System.out.println(ch);
		}

		// 4. conversion char array -> String
		String backToStr1 = String.valueOf(carr); // note that carr.toString() does not work as you may expect
		String backToStr2 = Arrays.toString(carr); // does the same

		// 5. String manipulation
		String lower = "WHatEveR".toLowerCase(); // "whatever"
		String upper = "WHatEveR".toUpperCase(); // "WHATEVER"
		String reverse = new StringBuilder(lower).reverse().toString(); // "revetahw"

		String repl = "cut".replace('c', 'p'); // "put"
		String sub = "Anaconda".substring(2, 6); // "acon"
		boolean con = sub.contains("co"); // true
		boolean star = sub.startsWith("b"); // false
		boolean endsWith = sub.endsWith("on"); // true
		int ind = "Coconut".indexOf("n"); // 4
		int len = "Coconut".length(); // 7

		// 6. Arrays

		int[] iarr; // just declaration:the array is not created, no memory is allocated
		iarr = new int[100]; // initialization
		String[] sarr = {"apple", "banana", "cherry"}; // string arrays with 3 elements is declared and initialized
		char[] ca = new char[]{'a', 'b'}; // such initialization is also valid
		int l = sarr.length; // 3

		long[][] l_matrix = new long[10][10]; // 10x10 array initialized
		double[][] d_matrix = new double[100][]; // array with 100 rows initialized, but all rows still uninitialized

		for (int i = 0; i < 10; i++) {
			d_matrix[i] = new double[10]; // array now initialized. Note that rows need not be of the same length
		}

		int[] ia = {1, 6, 7, 3};
		Arrays.sort(ia); // {1, 3, 6, 7}
		String[] sa = {"cat", "dog", "bird"};
		Arrays.sort(sa, Collections.reverseOrder()); // {"dog", "cat", "bird"}

		for (String s : sa) {
			System.out.println(s);
		}

		int index = Arrays.binarySearch(ia, 7); // 3

		int[] copy = Arrays.copyOf(ia, ia.length);
		boolean areEqual = Arrays.equals(ia, copy); // true

		System.out.println(Arrays.toString(copy));

	}

	public static void main(String... args) {
		workWithStrings();
	}

}
