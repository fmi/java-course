import java.util.Arrays;

/**
 * ============================================================================
 *  LAB 01 · INTERACTIVE SNIPPET
 *  switch: statement → arrow → expression → pattern matching
 * ============================================================================
 *
 *  Four generations of `switch`, same keyword. Read each form, predict the
 *  output where asked, then run.
 * ============================================================================
 */
public class SwitchEvolution {

    public static void main(String[] args) {
        String food = "RAMEN";

        System.out.println(oldStyle(food));
        System.out.println(arrowStyle(food));
        System.out.println(asExpression(food));

        System.out.println(describe("Hello"));
        System.out.println(describe(25));
        System.out.println(describe(250));
        System.out.println(describe(new char[] {'a', 'b'}));
        System.out.println(describe(null));
    }

    // (1) The classic fall-through statement with break.
    static String oldStyle(String food) {
        String grade;
        switch (food) {
            case "PIZZA":
                grade = "NICE!";
                break;
            case "RAMEN":
                grade = "OOOH FANCY!";
                break;
            default:
                grade = "IF YOU MUST...";
        }
        return grade;
    }

    // (2) Arrow form: no break, no fall-through.
    static String arrowStyle(String food) {
        String grade;
        switch (food) {
            case "PIZZA" -> grade = "NICE!";
            case "RAMEN" -> grade = "OOOH FANCY!";
            default -> grade = "IF YOU MUST...";
        }
        return grade;
    }

    // (3) As an EXPRESSION — the whole switch evaluates to a value.
    static String asExpression(String food) {
        return switch (food) {
            case "PIZZA" -> "NICE!";
            case "RAMEN" -> "OOOH FANCY!";
            case "SNAILS" -> {
                String s = "ARE YOU";
                yield s + " SERIOUS?!";
            }
            default -> "IF YOU MUST...";
        };
        // PREDICT: what happens if you delete the `default` line and pass a
        // food that isn't listed?
        // PREDICT: ____   WHY: ____
    }

    // (4) Pattern matching: switch on the TYPE of an object.
    static String describe(Object obj) {
        return switch (obj) {
            case Integer i when i > 100 -> "big Integer: " + i;
            case Integer i -> "Integer: " + i;
            case String s -> "String of length " + s.length();
            case char[] cs -> "char array: " + Arrays.toString(cs);
            case null -> "a null reference";
            default -> "something else";
        };
        // PREDICT: describe(25) vs describe(250) — which arm wins, and why does
        // the ORDER of the cases matter here?
        // PREDICT: describe(25): ____   describe(250): ____   WHY: ____
    }

    // 🎬 OPEN QUESTION (we'll answer this one next lecture):
    //    With pattern matching, you CAN switch on a reference of an abstract
    //    base type and list its concrete subclasses in the cases. It compiles.
    //    But should you? Hold that thought.
}
