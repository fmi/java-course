/**
 * ============================================================================
 *  LAB 02 · INTERACTIVE SNIPPET
 * ============================================================================
 *
 *  An AI assistant wrote the two equals() methods below.
 *
 *  The equals() contract (java.lang.Object) says equals must be:
 *          reflexive   — x.equals(x) is true
 *          symmetric   — x.equals(y) == y.equals(x)
 *          transitive  — x.equals(y) and y.equals(z)  ⇒  x.equals(z)
 *          consistent  — repeated calls give the same result (if nothing changes)
 *
 */
public class EqualsHashCodeChallenge {

    static final class Email {
        private final String value;

        Email(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Email e) {
                return value.equalsIgnoreCase(e.value);
            }
            if (o instanceof String s) {
                return value.equalsIgnoreCase(s);
            }
            return false;
        }
        // (hashCode intentionally omitted)
    }

    static void theHelpfulEmail() {
        Email email = new Email("ivan@fmi.bg");
        String raw  = "IVAN@FMI.BG";

        System.out.println(email.equals(raw));
        // PREDICT: ____   WHY: ____

        System.out.println(raw.equals(email));
        // PREDICT: ____   WHY: ____
    }

    static final class Session {
        String token;

        Session(String token) {
            this.token = token;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Session s && token.equals(s.token);
        }
    }

    static void theDriftingSession() {
        Session a = new Session("abc");
        Session b = new Session("abc");

        System.out.println(a.equals(b));
        // PREDICT: ____   WHY: ____

        a.token = "xyz";

        System.out.println(a.equals(b));
        // PREDICT: ____   WHY: ____
    }

    public static void main(String... args) {
        theHelpfulEmail();
        theDriftingSession();
    }
}
