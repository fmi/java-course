/**
 * ============================================================================
 *  LAB 01 · CaffeineTracker — your task to implement
 * ============================================================================
 *
 *  THE SPEC
 *  --------
 *  Caffeine decays with a fixed 5-hour half-life (300 min). The amount left at
 *  minute `now` from a cup of `mg[i]` drunk at `intakeTimes[i]` (if that cup was
 *  drunk at or before now) is:
 *
 *      mg[i] * 0.5 ^ ((now - intakeTimes[i]) / 300.0)
 *
 *  Sum over all cups drunk at/before `now`. Cups in the FUTURE (drunk after
 *  `now`) contribute nothing. Empty input, or `now` before every intake, → 0.0.
 *
 *  REFERENCE CHECKS (what a CORRECT implementation must produce):
 *      caffeineAtTime({0},     {200},     300)  ≈ 100.0   (one half-life → half left)
 *      caffeineAtTime({0,600}, {200,95},  480)  ≈ 65.98   (the 600-min cup is in the future)
 *
 *  ASSUMPTIONS
 *  -----------
 *  `intakeTimes` and `mg` are parallel arrays of the same length; `intakeTimes[i]`
 *  is the minute cup i was drunk and `mg[i]` its dose. Times are in minutes.
 *
 *  Implement the method below. Run `main` to check your work against the three
 *  cases — all three should PASS.
 * ============================================================================
 */
public class CaffeineTrackerBuggy {

    /**
     * @param intakeTimes minute each cup was drunk; parallel to {@code mg}
     * @param mg          dose of each cup in milligrams; parallel to {@code intakeTimes}
     * @param now         the minute at which to measure remaining caffeine
     * @return total remaining caffeine in mg at minute {@code now}
     */
    public static double caffeineAtTime(int[] intakeTimes, int[] mg, int now) {
        double total = 0.0;
        for (int i = 0; i < intakeTimes.length; i++) {
            if (intakeTimes[i] < now) {
                int elapsed = now - intakeTimes[i];
                double halfLives = elapsed / 300;
                total += mg[i] * Math.pow(0.5, halfLives);
            }
        }
        return total;
    }

    public static void main(String[] args) {
        check("case1 (expect ~100.0)", caffeineAtTime(new int[] {0}, new int[] {200}, 300), 100.0);
        check("case2 (expect ~141.42)", caffeineAtTime(new int[] {0}, new int[] {200}, 150), 141.42);
        check("case3 (expect 95.0)", caffeineAtTime(new int[] {100}, new int[] {95}, 100), 95.0);
    }

    /** Simple assertion-by-print; |expected - actual| within a small epsilon. */
    static void check(String label, double actual, double expected) {
        boolean ok = Math.abs(actual - expected) < 0.5;
        System.out.printf("%-24s actual=%-10.4f %s%n",
            label, actual, ok ? "PASS" : "FAIL  (expected ~" + expected + ")");
    }
}
