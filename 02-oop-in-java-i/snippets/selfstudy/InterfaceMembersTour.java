/**
 * ============================================================================
 *  LAB 02 · SELF-STUDY SNIPPET  (NOT walked through in the lab)
 *  Interface members — what you may legally put in an `interface`
 * ============================================================================
 */
public class InterfaceMembersTour {

    // --- 1. A normal interface: abstract methods + constants ----------------
    interface Channel {
        // Fields are implicitly  public static final (constants). No instance state.
        int MAX_RETRIES = 3;

        // Methods are implicitly  public abstract (no body). Stating them is style.
        void send(String message);

        boolean isAvailable();
    }

    // --- 2. default methods (Java 8): a body the implementor gets for free --
    interface Retryable {
        void attempt();

        default void attemptWithRetries() {        // reuses attempt(), no duplication
            for (int i = 0; i < Channel.MAX_RETRIES; i++) {
                attempt();
            }
        }
    }

    // --- 3. static methods (Java 8): utility / factory ON the interface -----
    interface Validator {
        boolean isValid(String s);

        // Called as Validator.nonBlank(...), NOT on an instance. Returns a Validator.
        // We'll meet static "factory methods" formally soon,
        // the point here is only: an interface can hold static code.
        static Validator nonBlank() {
            return new NonBlankValidator();
        }
    }

    // a plain class implementing the interface
    static final class NonBlankValidator implements Validator {
        @Override
        public boolean isValid(String s) {
            return s != null && !s.isBlank();
        }
    }

    // --- 4. private methods (Java 9): share code between default/static methods
    interface Logger {
        void log(String line);

        default void logInfo(String msg)  {
            log(prefix("INFO",  msg));
        }
        default void logError(String msg) {
            log(prefix("ERROR", msg));
        }

        private String prefix(String level, String msg) {   // hidden helper
            return "[" + level + "] " + msg;
        }
    }

    // --- 5. a MARKER interface: zero methods, a type-level tag -------------
    interface Audited { }   // e.g. java.lang.Cloneable is a famous marker

    // --- 6. a FUNCTIONAL interface: exactly ONE abstract method ------------
    //     @FunctionalInterface makes the compiler enforce "exactly one abstract
    //     method". In Week 8 you'll implement these with a lambda in one line,
    //     but for now, a normal class implements it the long way.
    @FunctionalInterface
    interface Transformer {
        String apply(String input);

        // (default / static methods do NOT count against the "one abstract" rule)
        default String applyTwice(String input) {
            return apply(apply(input));
        }
    }

    static final class Shouter implements Transformer {
        @Override public String apply(String input) {
            return input.toUpperCase();
        }
    }

    // --- a class can implement MANY interfaces at once ----------------------
    static final class EmailChannel implements Channel, Retryable, Audited {
        @Override
        public void send(String message) {
            System.out.println("email: " + message);
        }

        @Override
        public boolean isAvailable() {
            return true;
        }

        @Override
        public void attempt() {
            send("attempt");
        }
    }

    public static void main(String... args) {
        EmailChannel email = new EmailChannel();
        email.send("hello");
        email.attemptWithRetries();                       // default method, 3×
        System.out.println("constant MAX_RETRIES = " + Channel.MAX_RETRIES);

        Validator v = Validator.nonBlank();               // static factory on interface
        System.out.println("'  ' valid? " + v.isValid("  "));

        Transformer shout = new Shouter();
        System.out.println(shout.applyTwice("hi"));       // default method reuse

        System.out.println("email is Audited? " + (email instanceof Audited)); // marker check

        // Summary of what an interface may contain:
        //    public abstract methods · public static final constants ·
        //    default methods (Java 8) · static methods (Java 8) ·
        //    private methods (Java 9).  NO instance fields, NO constructors.
    }
}
