/**
 * ============================================================================
 *  LAB 03 · INTERACTIVE SNIPPET
 *  sealed + record patterns: destructure right inside the switch
 * ============================================================================
 *
 *  PREDICT each line of output BEFORE running.
 * ============================================================================
 */
public class PaymentResultPatterns {

    sealed interface PaymentResult permits Approved, Declined, Pending {}

    record Approved(String txId, int cents) implements PaymentResult {}
    record Declined(String reason) implements PaymentResult {}
    record Pending(String txId) implements PaymentResult {}

    static String describe(PaymentResult r) {
        return switch (r) {
            case Approved(String id, int cents) -> "approved " + id + " for " + cents + "c";
            case Declined(String reason)        -> "declined: " + reason;
            case Pending(String id)             -> "pending " + id;
        };
    }

    static String risk(PaymentResult r) {
        return switch (r) {
            case Approved(String id, int cents) when cents > 10_000 -> "BIG approval " + id;
            case Approved(String id, int cents)                     -> "normal approval " + id;
            case Declined d                                         -> "no money moved";
            case Pending p                                          -> "waiting";
        };
    }

    public static void main(String... args) {
        PaymentResult a = new Approved("tx-1", 500);
        PaymentResult d = new Declined("insufficient funds");
        PaymentResult p = new Pending("tx-2");

        System.out.println(describe(a));   // PREDICT: ______
        System.out.println(describe(d));   // PREDICT: ______
        System.out.println(describe(p));   // PREDICT: ______

        System.out.println(risk(a));                                         // PREDICT: ______
        System.out.println(risk(new Approved("tx-9", 25_000)));   // PREDICT: ______
        System.out.println(risk(d));                                         // PREDICT: ______
    }
}
