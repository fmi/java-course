/**
 * ============================================================================
 * LAB 02 · INTERACTIVE SNIPPET Method contracts · the comparison that silently lies
 * ============================================================================
 *
 *    A method is a PROMISE (a "contract").
 *
 *    1. PREDICT the output.
 *    2. Run, check your predictions vs. the real output.
 *    3. Read the comment at the end of the class definition.
 *    4. Change the implementation as suggested & run again.
 *
 */
public class ContractOverflow {

    /**
     * Returns negative if a has lower priority than b, positive if higher, zero if equal.
     * (Higher number = higher priority.)
     */
    static int compareByPriority(int a, int b) {
        return a - b;
    }

    public static void main(String[] args) {
        int normalLow = 3;
        int normalHigh = 9;

        System.out.println("normal case: " + compareByPriority(normalHigh, normalLow)); // PREDICT: ____

        int urgent = Integer.MAX_VALUE;
        int chill = -2;

        System.out.println("edge case: " + compareByPriority(urgent, chill)); // PREDICT: ____

        int a = Integer.MAX_VALUE - 1;
        int b = -1;
        int c = -3;

        // a > b > c, right?
        System.out.println("a > b? " + (compareByPriority(a, b) > 0)); // PREDICT: ____
        System.out.println("b > c? " + (compareByPriority(b, c) > 0)); // PREDICT: ____
        System.out.println("a > c? " + (compareByPriority(a, c) > 0)); // PREDICT: ____
    }

}

// This exact "subtract to compare" pattern shipped in countless
// real codebases. On Java 6 it ran SILENTLY — just produced a
// subtly wrong order, no error. Java 7 introduced a smarter sort
// that RELIES ON the comparison being consistent (if a > b and b > c, then a > c).
// and when it caught the overflow-induced contradiction, it started crashing with:
//
//     java.lang.IllegalArgumentException:
//     Comparison method violates its general contract!
//
// So a routine Java 6 -> 7 upgrade turned a latent, invisible bug into
// a loud production crash across the industry — for teams whose
// only sin was writing "a - b".
//
// THE FIX: compare with Integer.compare(a, b) on line 21.
