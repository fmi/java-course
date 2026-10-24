/**
 * ============================================================================
 * LAB 02 · MINI-PROBLEM (10–15 min, solo)
 * ============================================================================
 *
 * Implement an AlertService that sends alerts without knowing or caring how they are delivered. The
 * delivery mechanism should be easily swappable meaning zero changes to the AlertService class are
 * required
 *
 * TODO comments have been provided to guide your implementation
 *
 */
class AlertServiceMiniProblem {

    interface Notifier {
        void notify(String recipient, String message);
    }

    static final class AlertService {
        // TODO 1: Store a notifier

        void alert(String recipient, String text) {
            // TODO 2: Delegate to the stored notifier, sending "ALERT: " + text
        }
    }

    static final class SmsNotifier implements Notifier {
        @Override
        public void notify(String recipient, String message) {
            System.out.println("SMS to " + recipient + ": " + message);
        }
    }

    static final class TeamsNotifier implements Notifier {
        @Override
        public void notify(String channel, String message) {
            System.out.println("Posting to channel" + channel + ": " + message);
        }
    }

    static final class SilentNotifier implements Notifier {
        @Override
        public void notify(String recipient, String message) {
            // intentionally does nothing
        }
    }

    public static void main(String... args) {
        demo();
        testNotifiers();
    }

    static void demo() {
        // TODO 3: create an AlertService with a SmsNotifier, and call alert() on it.
        // Try changing the notifier to TeamsNotifier and SilentNotifier
        // Your code should not require any changes to the AlertService class.
    }

    static void testNotifiers() {
        // TODO 4: Think of a way we can automatically check if the AlertService works instead of
        // manually running the program every time with every possible combination. Hint: Use a
        // "fake" notifier that just records the last call, and check if said call is correct. You
        // can use the assertStringEquals and assertIntEquals helper methods below.
    }

    static void assertStringEquals(String actual, String expected) {
        boolean isOk = expected.equals(actual);

        System.out.println(
                isOk ? "PASS" : "FAIL - expected [" + expected + "] but got [" + actual + "]");
    }

    static void assertIntEquals(int actual, int expected) {
        boolean isOk = expected == actual;

        System.out.println(
                isOk ? "PASS" : "FAIL - expected [" + expected + "] but got [" + actual + "]");
    }

}
