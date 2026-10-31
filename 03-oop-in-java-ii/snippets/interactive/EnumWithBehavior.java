/**
 * ============================================================================
 *  LAB 03 · INTERACTIVE SNIPPET
 *  An enum is a full class — each constant carries its own data AND behavior
 * ============================================================================
 *
 *  If you think an enum is "just a list of names" (the C/C++ habit), this
 *  snippet will surprise you. Each CoffeeSize constant stores its volume and
 *  price, and answers questions about itself.
 *
 *  PREDICT each printed line BEFORE running.
 * ============================================================================
 */
public class EnumWithBehavior {

    enum CoffeeSize {
        SMALL(200, 250),     // (ml, price in cents)
        MEDIUM(350, 320),
        LARGE(500, 390);

        private final int ml;
        private final int priceCents;

        CoffeeSize(int ml, int priceCents) {
            this.ml = ml;
            this.priceCents = priceCents;
        }

        int ml() {
            return ml;
        }

        int priceCents() {
            return priceCents;
        }

        // Behavior that lives ON the constant — no external if/switch needed.
        boolean isSharable() {
            return ml >= 400;
        }

        // A constant can look at the OTHER constants too (values() is generated).
        static CoffeeSize cheapest() {
            CoffeeSize best = values()[0];
            for (CoffeeSize s : values()) {
                if (s.priceCents < best.priceCents) {
                    best = s;
                }
            }
            return best;
        }
    }

    public static void main(String... args) {
        CoffeeSize s = CoffeeSize.LARGE;

        System.out.println(s + " -> " + s.ml() + "ml");                         // PREDICT: ______
        System.out.println("sharable? " + s.isSharable());                      // PREDICT: ______
        System.out.println("SMALL sharable? " + CoffeeSize.SMALL.isSharable()); // PREDICT: ______

        System.out.println(CoffeeSize.valueOf("MEDIUM").priceCents());    // PREDICT: ______
        System.out.println("cheapest = " + CoffeeSize.cheapest());              // PREDICT: ______

        System.out.println("ordinal of LARGE: " + CoffeeSize.LARGE.ordinal());  // PREDICT: ______

        for (CoffeeSize size : CoffeeSize.values()) {
            String cup = switch (size) {
                case SMALL -> "espresso cup";
                case MEDIUM -> "regular mug";
                case LARGE -> "takeaway cup";
            };
            System.out.printf("%-7s (%dml): %s%n", size, size.ml(), cup);
        }
    }
}
