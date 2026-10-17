/**
 * ============================================================================
 *  LAB 01 · INTERACTIVE SNIPPET
 *  String pool vs heap · == vs .equals() · immutability
 * ============================================================================
 *
 *  For each `println`, write down your PREDICTION and ONE word of WHY — commit
 *  to it BEFORE you run. The moment a prediction is wrong is the moment you
 *  learn something.
 *
 *      System.out.println(a == b);   // PREDICT: ____   WHY: ____
 * ============================================================================
 */
public class StringPoolMentalModel {

    // 1) Two identical literals.
    static void literalsAndThePool() {
        System.out.println("=== 1. literals ===");

        String literalOne = "FMI";
        String literalTwo = "FMI";

        System.out.println(literalOne == literalTwo);
        // PREDICT: ____   WHY: ____
        // (Did Java make a second "FMI", or hand back the first one?)
    }

    // 2) new String(...)
    static void newKeywordBreaksSharing() {
        System.out.println("=== 2. new ===");

        String literal = "FMI";
        String newString = new String("FMI");

        System.out.println(literal == newString);
        // PREDICT: ____   WHY: ____

        System.out.println(literal.equals(newString));
        // PREDICT: ____   WHY: ____
        // (Same CONTENT? vs. same OBJECT? — which does each operator ask?)
    }

    // 3) Comparing user input to an expected value.
    static void theEqualsFix() {
        System.out.println("=== 3. == vs equals ===");

        String typedByUser = new String("admin");
        String expected = "admin";

        System.out.println(typedByUser == expected);
        // PREDICT: ____   WHY: ____

        System.out.println(typedByUser.equals(expected));
        // PREDICT: ____   WHY: ____
    } 

    // 4) 100yo claimed on the lecture:
    //
    //      "If you do a modifying operation on a string, the NEW string lives
    //       where the ORIGINAL lived — a pooled string's result goes back to
    //       the pool, a heap string's result stays on the heap."
    //
    //    Before running: is this claim TRUE or FALSE? Commit a vote, then let
    //    the output decide.
    static void the100yoClaim() {
        System.out.println("=== 4. verify the claim ===");

        // CLAIM VOTE (commit before running):  TRUE / FALSE  ?

        String pooled = "hello"; // pooled
        String fromHeap = new String("hello"); // heap

        String resultA = pooled.toUpperCase();     // operation on a POOLED string
        String resultB = fromHeap.toUpperCase();   // operation on a HEAP string

        String literalUpper = "HELLO"; // pooled

        System.out.println(resultA == literalUpper);
        // PREDICT: ____   WHY: ____

        System.out.println(resultB == literalUpper);
        // PREDICT: ____   WHY: ____

        System.out.println(resultA.intern() == literalUpper);
        // PREDICT: ____   WHY: ____
    }

    // 5) Compile-time vs run-time string concatenation resolution
    static void compileVsRunTimeConcatResolution() {
        System.out.println("=== 5. compile-time vs run-time ===");

        String literalOne = "Hello" + " " + "World"; // pooled, two literals
        String literalTwo = "Hello World"; // pooled

        String literalThree = "Hello " + 1; // pooled, two literals
        String literalFour = "Hello 1"; // pooled

        String literalFive = "Hello" + " ";
        literalFive += "World"; // heap, expression includes a variable
        String literalSix = "Hello World"; // pooled
        
        System.out.println(literalOne == literalTwo);
        // PREDICT: ____   WHY: ____
        
        System.out.println(literalThree == literalFour);
        // PREDICT: ____   WHY: ____

        System.out.println(literalFive == literalSix);
        // PREDICT: ____   WHY: ____
    }

    public static void main(String[] args) {
        literalsAndThePool();
        newKeywordBreaksSharing();
        theEqualsFix();
        the100yoClaim();
    }
}
