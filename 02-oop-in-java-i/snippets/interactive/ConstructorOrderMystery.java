/**
 * ============================================================================
 *  LAB 02 · INTERACTIVE SNIPPET
 * ============================================================================
 *
 * When you call `new EmailNotification(...)`, what runs first? the parent or the child?
 * Every step below prints a tag so you can see the order yourself.
 *
 * Before running, predict the order:
 *
 *     PREDICT order: ____ , ____ , ____ , ____ , ____ , ____ , ____ , ____
 *
 */
public class ConstructorOrderMystery {

    static int tag(String label) {
        System.out.println(label);
        return 0;
    }

    static class Notification {
        private final int baseField = tag("base field");
        private static final int baseStaticField = tag("base static field");

        Notification() {
            tag("Notification()");
        }
    }

    static final class EmailNotification extends Notification {
        private final int childField = tag("child field");
        private static final int childStaticField = tag("child static field");

        EmailNotification() {
            tag("EmailNotification()");
        }
    }

    public static void main(String... args) {
        tag("start");
        new EmailNotification();
        tag("end");
    }
}
