/**
 * ============================================================================
 *  LAB 02 · SELF-STUDY SNIPPET  (NOT walked through in the lab)
 *  Abstract class vs. interface — when to reach for which
 * ============================================================================
 *
 *  Both let you program to an abstraction. Beginners pick by habit, engineers
 *  pick by intent. This tour shows the SAME tiny domain modelled both ways,
 *  then states the decision rule. Read it at home.
 *
 *  Rule of thumb up front:
 *    • interface  = a CONTRACT / capability. "CAN-DO."   A class may have many.
 *    • abstract class = a PARTIAL BASE with shared STATE + code. "IS-A."  One only.
 * ============================================================================
 */
public class AbstractVsInterfaceTour {

    // ------------------------------------------------------------------------
    //  (1) INTERFACE — a capability several unrelated types can advertise.
    //      No state, no constructor, many implementors, a class can combine
    //      several such capabilities.
    // ------------------------------------------------------------------------
    interface Sendable {
        void send(String message);              // the contract
        default boolean isUrgent() { return false; } // optional shared behavior
    }

    interface Schedulable {
        void scheduleAt(long epochMillis);      // a SECOND, independent capability
    }

    // ------------------------------------------------------------------------
    //  (2) ABSTRACT CLASS — a real base with shared STATE and shared CODE that
    //      every subclass needs, plus one hole each subclass must fill.
    // ------------------------------------------------------------------------
    abstract static class Notification implements Sendable {
        protected final String recipient;      // shared STATE — interfaces can't hold this

        protected Notification(String recipient) {   // shared construction logic
            this.recipient = recipient;
        }

        // shared, final behavior all notifications get for free
        protected final String envelope(String body) {
            return "to=" + recipient + " | " + body;
        }

        // the hole: HOW to send differs per channel. Subclass must fill it.
        @Override
        public abstract void send(String message);
    }

    // concrete subclasses: reuse the base's state + envelope(), fill the hole
    static final class EmailNotification extends Notification {
        EmailNotification(String recipient) { super(recipient); }

        @Override
        public void send(String message) {
            System.out.println("EMAIL  " + envelope(message));
        }
    }

    static final class SmsNotification extends Notification {
        SmsNotification(String recipient) { super(recipient); }

        @Override
        public void send(String message) {
            System.out.println("SMS    " + envelope(message));
        }

        @Override
        public boolean isUrgent() {
            return true; // override the default
        }
    }

    // ------------------------------------------------------------------------
    //  (3) COMBINING: a class can be an IS-A (extends one base) AND advertise
    //      extra CAN-DO capabilities (implements more interfaces).
    // ------------------------------------------------------------------------
    static final class PushNotification extends Notification implements Schedulable {
        private long when;

        PushNotification(String recipient) {
            super(recipient);
        }

        @Override
        public void send(String message) {
            System.out.println("PUSH   " + envelope(message) + (when > 0 ? " @" + when : ""));
        }

        @Override
        public void scheduleAt(long epochMillis) {
            this.when = epochMillis;
        }
    }

    public static void main(String... args) {
        Notification[] all = {
            new EmailNotification("ivan@fmi.bg"),
            new SmsNotification("+359..."),
            new PushNotification("device-42"),
        };

        for (Notification n : all) {

            n.send("deploy finished"); // polymorphism in action
            
            if (n.isUrgent()) {
                System.out.println("   ↳ (marked urgent)");
            }

        }

        // a capability check — push is also Schedulable, the others are not
        for (Notification n : all) {

            if (n instanceof Schedulable s) {
                s.scheduleAt(1_700_000_000_000L);
                n.send("scheduled ping");
            }
            
        }

        // DECISION RULE (the takeaway):
        //   Choose an INTERFACE when:
        //     • unrelated types need the same capability ("CAN-DO")
        //     • you want a type to have SEVERAL of them (no single-parent limit)
        //     • there is no shared state/implementation to inherit
        //   Choose an ABSTRACT CLASS when:
        //     • there is genuine shared STATE + CODE ("IS-A" specialization)
        //     • you want to force a construction/initialization path
        //   Modern default: prefer interfaces (+ default methods) for flexibility,
        //   add an abstract base ONLY when real shared state/code justifies it.
        //
        //   💼 Classic interview question: and now you can answer it with an
        //      example, not a definition.
    }
}
