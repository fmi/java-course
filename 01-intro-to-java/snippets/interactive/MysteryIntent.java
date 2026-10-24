import java.util.Arrays;

/**
 * ============================================================================
 *  LAB 01 · INTERACTIVE SNIPPET
 *  Read unfamiliar code: what does it DO?
 * ============================================================================
 *
 *  Two questions, answered by prediction + vote BEFORE running:
 *    Q1 (intent):        what does `mystery` return, in ONE sentence?
 *    Q2 (property):      what is `best` guaranteed to hold after the loop?
 *
 *  Don't trace line by line — reason about the INTENT.
 * ============================================================================
 */
public class MysteryIntent {

    // A: array of n numbers (n >= 1).
    static int mystery(int[] a) {
        int cur = a[0];
        int best = a[0];
        for (int i = 1; i < a.length; i++) {
            cur = Math.max(a[i], cur + a[i]);
            best = Math.max(best, cur);
        }
        return best;
    }

    public static void main(String[] args) {
        int[] mixed = {3, -2, 5, -1, 2};
        int[] allNeg = {-8, -3, -5};
        int[] leadingNeg = {-5, 10};

        // Q1 / Q2 — PREDICT before running:
        //   mystery(mixed) = ______   mystery(allNeg) = ______   mystery(leadingNeg) = ______
        System.out.println("mystery(mixed)      = " + mystery(mixed));
        System.out.println("mystery(allNeg)     = " + mystery(allNeg));
        System.out.println("mystery(leadingNeg) = " + mystery(leadingNeg));
    }
}
