/**
 * ============================================================================
 *  LAB 02 · INTERACTIVE SNIPPET
 * ============================================================================
 *
 *    PREDICT every println below.
 *
 */
public class AccountingForPolymorphism {

    static class Account {
        String kind = "generic";

        String describe() {
            return "generic account";
        }

        String report() {
            return kind + " / " + describe();
        }
    }

    static final class Savings extends Account {
        String kind = "savings";

        @Override
        String describe() {
            return "savings account";
        }
    }

    public static void main(String... args) {
        Account acc = new Savings();

        System.out.println(acc.kind);
        // PREDICT: ____   WHY: ____

        System.out.println(acc.describe());
        // PREDICT: ____   WHY: ____

        System.out.println(acc.report());

        System.out.println("-----");

        Savings s = new Savings();
        System.out.println(s.kind);              // PREDICT: ____
        System.out.println(((Account) s).kind);  // PREDICT: ____
    }
}
