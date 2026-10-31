/**
 * ============================================================================
 *  LAB 03 · SELF-STUDY SNIPPET  (NOT walked through in the lab)
 * ============================================================================
 */
import java.util.Arrays;

public class EnumApiTour {

    enum Day {
        MON, TUE, WED, THU, FRI, SAT, SUN;

        boolean isWeekend() {
            return this == SAT || this == SUN;
        }
    }

    // enum with per-constant state + a constructor (the "full class" form)
    enum Planet {
        EARTH(5.976e+24, 6.37814e6),
        MARS(6.421e+23, 3.3972e6);

        private final double massKg;
        private final double radiusM;

        Planet(double massKg, double radiusM) {
            this.massKg = massKg;
            this.radiusM = radiusM;
        }

        double surfaceGravity() {
            final double G = 6.67300e-11;
            return G * massKg / (radiusM * radiusM);
        }
    }

    static void compilerGeneratedApi() {
        // values(): all constants, in declaration order (a fresh array each call)
        Day[] all = Day.values();                 // [MON, TUE, WED, THU, FRI, SAT, SUN]

        // valueOf(String): the constant with exactly this NAME (throws if unknown)
        Day fri = Day.valueOf("FRI");      // FRI
        // Day.valueOf("Friday") -> IllegalArgumentException

        // name(): the identifier verbatim; toString() defaults to the same but may be overridden
        String n = Day.WED.name();                // "WED"

        // ordinal(): position in the declaration (MON=0 ... SUN=6).
        // Handy for display, DANGEROUS to persist — reordering constants shifts them all.
        int pos = Day.WED.ordinal();              // 2

        // enums compare by identity — == is correct and idiomatic
        boolean same = (fri == Day.FRI);          // true

        System.out.println(Arrays.toString(all) + " / " + fri + " / " + n
            + " / ordinal=" + pos + " / ==? " + same + " / weekend? " + Day.SAT.isWeekend());
    }

    static void perConstantState() {
        for (Planet p : Planet.values()) {
            System.out.printf("%-6s surface gravity = %.2f m/s^2%n", p, p.surfaceGravity());
        }
    }

    public static void main(String... args) {
        compilerGeneratedApi();
        perConstantState();
    }
}
