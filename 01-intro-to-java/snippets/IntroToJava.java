import java.util.Arrays;
import java.util.Collections;

public class IntroToJava {

    public static void exploreString() {

        // 1. string literals and the string pool
        System.out.println("==============1==============");
        String literalOne = "FMI"; // goes to the string pool
        String literalTwo = "FMI"; // "FMI" is present in the String pool -> literalTwo will refer to the same
        // object

        System.out.println(literalOne == literalTwo); // true

        String newString = new String("FMI"); // new String object in the heap

        System.out.println(literalOne == newString); // false, literalOne refers to an object in the string pools,
        // newString refers to an object in the heap
        System.out.println(literalOne.equals(newString)); // true: string content is the same

        String intern = newString.intern(); // String "interning" will add the string in the pool and return a reference
        // to it
        System.out.println(literalOne == newString); // false, newString is not reassigned
        System.out.println(literalOne == intern); // true

        // 2. string concatenation and splitting
        System.out.println("==============2==============");
        int year = 2024;
        String message = "Current year is ";
        message += year + "."; // Strings are immutable so this will create a new String object and some
        // garbage

        String[] words = message.split(" "); // {"Current", "year", "is", "2024."}
        System.out.println(words); // Will not print a human-readable representation. Instead it will print
        // something like [Ljava.lang.String;@1dbd16a6
        System.out.println(Arrays.toString(words)); // "[Current, year, is, 2024.]"

        // 3. string iteration and conversion String -> char array
        System.out.println("==============3==============");
        String example = "Example";
        char secondChar = example.charAt(1); // 'x'. Note that Strings are not char arrays, i.e. you cannot do e.g.
        // example[1];
        System.out.println("Second char is: " + secondChar);

        char[] chars = example.toCharArray(); // convert a String to char array

        System.out.println("_BEGIN_ITERATION_");
        for (int i = 0; i < chars.length; i++) { // iterate the char array with standard for-loop
            System.out.println(chars[i]);
        }
        System.out.println("_END_ITERATION_");

        System.out.println("_BEGIN_ITERATION_");
        for (char current : chars) { // the same as above but with for-each loop -> recommended because it's shorter
            System.out.println(current);
        }
        System.out.println("_END_ITERATION_");

        // 4. conversion char array -> String
        System.out.println("==============4==============");
        String backToStr0 = chars.toString(); // WRONG! Note that chars.toString() does not work as you may expect
        String backToStr1 = String.valueOf(chars); // Right way!
        String backToStr2 = Arrays.toString(chars); // Encloses char elements in brackets and adds ',' between them

        System.out.println(backToStr0 + " : " + backToStr1 + " : " + backToStr2);

        // 5. String manipulation
        System.out.println("==============5==============");
        String lower = "WHatEveR".toLowerCase(); // "whatever"
        String upper = "WHatEveR".toUpperCase(); // "WHATEVER"
        String reverse = new StringBuilder(lower).reverse().toString(); // "revetahw"

        System.out.println(lower + " : " + upper + " : " + reverse);

        String replaced = "cut".replace('c', 'p'); // "put"
        String substring = "Anaconda".substring(2, 6); // "acon"
        boolean containsCo = substring.contains("co"); // true
        boolean startsWithB = substring.startsWith("b"); // false
        boolean endsWithOn = substring.endsWith("on"); // true
        int indexOfN = "Coconut".indexOf("n"); // 4
        int length = "Coconut".length(); // 7 (note that the length is a method, not a field)

        String strippedWhiteSpaces = "      some whitespaces we want to strip ".strip(); // "some whitespaces we want to
        // strip"
        String indentedText = "Indent this".indent(4); // " Indent this" -> indents the text by appending spaces in
        // front

        // 6. More String comparisons
        System.out.println("==============6==============");
        boolean isEmpty = "".isEmpty(); // true
        boolean isNotEmpty = !"  ".isEmpty(); // true
        boolean isBlank = "  ".isBlank(); // true
        boolean equalIgnoringCapitalization = "Java".equalsIgnoreCase("jAVA"); // true

        // 7. Emojis
        System.out.println("I am from \uD83C\uDDE7\uD83C\uDDEC");

        System.out.println((Character.toChars(9203)));
        System.out.println(Character.isEmoji(9203));
    }

    public static void exploreStringBuilder() {

        // We use StringBuilder when we expect the string to be manipulated
        // e.g. in loops:
        StringBuilder oddNumbers = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            if (i % 2 != 0) {
                oddNumbers.append(i).append(" ");
            }
        }
        System.out.println(oddNumbers); // "1 3 5 7 9 11 13 15 17 19 "

        // or in something like this:
        StringBuilder word = new StringBuilder("racecar");
        boolean isPalindrome = word.equals(word.reverse()); // true

        // As the following concatenation is in the same statement,
        // the compiler will optimize this and use StringBuilder instead
        String feelings = "I " + "<3 " + "Java";
    }

    public static void exploreArrays() {

        int[] array; // just declaration - the array is not created, no memory is allocated
        array = new int[5]; // initialization - now we have {0, 0, 0, 0, 0}

        String[] stringArray = {"apple", "banana", "cherry"}; // string array with 3 elements is declared and
        // initialized
        char[] charArray = new char[] {'a', 'b'}; // such initialization is also valid
        int length = stringArray.length; // 3 (note that the length is a field, not a method)

        long[][] longMatrix = new long[10][10]; // 10x10 array initialized
        double[][] doubleMatrix = new double[100][]; // array with 100 rows initialized, but all rows still
        // uninitialized

        for (int i = 0; i < 100; i++) {
            doubleMatrix[i] =
                new double[10]; // array now initialized. Note that rows need not be of the same length - called jagged (or ragged) array
        }

        int[] intArray = {1, 6, 7, 3};
        Arrays.sort(intArray); // { 1, 3, 6, 7 }
        String[] animals = {"cat", "dog", "bird"};
        Arrays.sort(animals, Collections.reverseOrder()); // { "dog", "cat", "bird" }

        for (String animal : animals) {
            System.out.println(animal);
        }

        int index = Arrays.binarySearch(intArray, 7); // 3

        int[] copy = Arrays.copyOf(intArray, intArray.length);
        boolean areEqual = Arrays.equals(intArray, copy); // true

        System.out.println(Arrays.toString(copy));

        // As we know, arrays are objects and need to be compared with "equals()".
        // However, two (and more) dimensional arrays are arrays of objects
        // and Java has a special method for comparing them:
        char[][] currentBoard = {{'X', 'O', 'X'}, {'X', 'O', 'O'}, {'X', 'X', 'O'}};
        char[][] winBoard = {{'X', 'O', 'X'}, {'X', 'O', 'O'}, {'X', 'X', 'O'}};
        boolean isWinning = Arrays.deepEquals(currentBoard, winBoard); // true
    }

    public static void main(String[] args) {
        exploreString();
        exploreStringBuilder();
        exploreArrays();
    }

}
