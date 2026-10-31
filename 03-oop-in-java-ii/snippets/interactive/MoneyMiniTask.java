/**
 * ============================================================================
 *  LAB 03 · MINI-TASK (solo, ~10 min)
 * ============================================================================
 *
 *  You are given a `Money` record that stores an amount in whole CENTS (so no
 *  floating-point rounding bugs). Three small pieces are missing. Fill them in.
 *
 *  THE SPEC
 *  --------
 *  TODO 1 — add constructor validation:
 *           reject a negative `cents` with an `IllegalArgumentException`
 *
 *  TODO 2 — static factory `ofEuros(int euros)`:
 *           build a Money from a whole number of euros (1 euro = 100 cents).
 *           e.g. ofEuros(3) == new Money(300).
 *
 *  TODO 3 — `plus(Money other)`:
 *           return a NEW Money whose cents is the sum of the two amounts.
 *
 *  Run main(). All five checks must print PASS.
 *  Do NOT edit main() or the check() helper.
 * ============================================================================
 */
public class MoneyMiniTask {

    public record Money(int cents) {

        public Money {
        }

        public static Money ofEuros(int euros) {
            return null; // replace me
        }

        public Money plus(Money other) {
            return null; // replace me
        }

        // Already written for you — formats e.g. 350 as "3.50".
        @Override
        public String toString() {
            return cents / 100 + "." + String.format("%02d", cents % 100);
        }
    }

    public static void main(String... args) {
        check("ofEuros(3) is 300c", () -> Money.ofEuros(3).cents(), 300);

        check("350c + 175c is 525c", () -> new Money(350).plus(new Money(175)).cents(), 525);
        check("toString of sum", () -> new Money(350).plus(new Money(175)).toString(), "5.25");

        check("negative throws", () -> throwsIAE(() -> new Money(-1)), true);
        check("ofEuros(-2) throws", () -> throwsIAE(() -> Money.ofEuros(-2)), true);
    }

    // ---- helpers: do not edit ----

    interface Body { void run(); }

    interface Producer { Object get(); }

    static boolean throwsIAE(Body body) {
        try {
            body.run();
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    static void check(String label, Producer actual, Object expected) {
        Object got;
        try {
            got = actual.get();
        } catch (Exception e) {
            got = "<threw " + e.getClass().getSimpleName() + ">";
        }
        boolean ok = expected.equals(got);
        System.out.printf("%-22s %s%n", label,
            ok ? "PASS" : "FAIL (expected [" + expected + "] but got [" + got + "])");
    }
}
