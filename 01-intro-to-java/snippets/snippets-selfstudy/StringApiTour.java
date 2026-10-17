import java.util.Arrays;
import java.util.Collections;

/**
 * ============================================================================
 *  LAB 01 · SELF-STUDY SNIPPET  (NOT walked through in the lab)
 *  String & StringBuilder API tour — your copy-paste reference
 * ============================================================================
 */
public class StringApiTour {

    static void stringBasics() {
        // creation & concatenation
        String language = "Java";
        String message  = "I <3 " + language;          // "I <3 Java"
        String year     = "The current year is " + 2026; // int auto-converted to its text

        // length is a METHOD on String (unlike arrays, where length is a field)
        int len = "Coconut".length();                   // 7

        // indexing / substrings
        char second     = "Example".charAt(1);          // 'x'  (no s[1] — not a char array)
        String sub      = "Anaconda".substring(2, 6);   // "acon"  (end exclusive)
        int idx         = "Coconut".indexOf("n");        // 4

        // case & trimming
        String lower    = "WHatEveR".toLowerCase();      // "whatever"
        String upper    = "WHatEveR".toUpperCase();      // "WHATEVER"
        String stripped = "  padded  ".strip();          // "padded"  (Unicode-aware; prefer over trim())

        // predicates
        boolean empty   = "".isEmpty();                  // true
        boolean blank   = "   ".isBlank();               // true (only whitespace)
        boolean sameCI  = "Java".equalsIgnoreCase("jAVA"); // true
        boolean has     = "acon".contains("co");         // true
        boolean starts  = "acon".startsWith("ac");       // true
        boolean ends    = "acon".endsWith("on");         // true

        // replace / split / join
        String put      = "cut".replace('c', 'p');       // "put"
        String[] tokens = "a,b,c".split(",");            // {"a","b","c"}
        String joined   = String.join("-", "a", "b", "c"); // "a-b-c"

        // formatting (handy for building messages)
        String greet    = "Hello, %s! You are %d.".formatted("Gen Z", 20);

        // parse text → number (you'll use this a LOT in the assignments)
        int parsed      = Integer.parseInt("2026");      // 2026

        System.out.println(message + " / " + year + " / len=" + len
            + " / " + second + sub + idx + lower + upper + stripped
            + empty + blank + sameCI + has + starts + ends
            + put + Arrays.toString(tokens) + joined + greet + parsed);
    }

    static void stringToChars() {
        String s = "Firebird";

        char[] chars = s.toCharArray();                  // String → char[]
        for (char c : chars) {
            // for-each over the characters; body intentionally empty in this demo
        }

        Arrays.sort(chars);                              // sort the char[] in place
        String sorted = String.valueOf(chars);          // char[] → String ("Fbdeiirr")

        // ⚠️ common trap: chars.toString() does NOT give you the text — it gives
        // something like "[C@1b6d3586". Use String.valueOf(chars) instead.
        System.out.println(sorted);
    }

    static void stringBuilderBasics() {
        // Use StringBuilder when you build a string piece-by-piece in a loop.
        // (Because String is immutable, "s += x" in a loop makes a new object
        //  each iteration = lots of garbage. StringBuilder mutates one buffer.)
        StringBuilder odds = new StringBuilder();
        for (int i = 1; i < 20; i += 2) {
            odds.append(i).append(' ');
        }
        System.out.println(odds.toString().strip());    // "1 3 5 7 9 11 13 15 17 19"

        String reversed = new StringBuilder("racecar").reverse().toString(); // "racecar"
        System.out.println(reversed);

        // NOTE: the compiler already optimizes simple one-statement
        // concatenation like "a" + "b" + "c" into StringBuilder for you — so
        // you only reach for StringBuilder EXPLICITLY when concatenating across
        // multiple statements / loop iterations.
    }

    public static void main(String[] args) {
        stringBasics();
        stringToChars();
        stringBuilderBasics();
    }
}
