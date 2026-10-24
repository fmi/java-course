/**
 * ============================================================================
 *  LAB 02 · INTERACTIVE SNIPPET
 * ============================================================================
 *
 *    1. Add a brand-new notification type (e.g. DiscordNotification).
 *    2. Do the same in NonPolymorphicNotification and compare both implementations.
 *    2. Which version of sendAll is:
 *          clearer to read:  ______
 *          more performant:  ______
 *          less error-prone: ______
 *          easier to extend: ______
 */
public class PolymorphicNotification {

    interface Notification {
        void send();
    }

    static abstract class AbstractNotification implements Notification {
        protected final String recipient;

        AbstractNotification(String recipient) {
            this.recipient = recipient;
        }

        @Override
        public void send() {
            System.out.println("abstract -> " + recipient);
        }
    }

    static final class EmailNotification extends AbstractNotification {
        EmailNotification(String recipient) {
            super(recipient);
        }

        @Override
        public void send() {
            System.out.println("email -> " + recipient);
        }
    }

    static final class SmsNotification extends AbstractNotification {
        SmsNotification(String recipient) {
            super(recipient);
        }

        @Override
        public void send() {
            System.out.println("sms   -> " + recipient);
        }
    }

    static final class PushNotification extends AbstractNotification {
        PushNotification(String recipient) {
            super(recipient);
        }

        @Override
        public void send() {
            System.out.println("push  -> " + recipient);
        }
    }

    static void sendAll(Notification[] all) {
        for (Notification n : all) {
            n.send();
        }
    }

    public static void main(String... args) {
        Notification[] all = {
            new EmailNotification("ivan@fmi.bg"),
            new SmsNotification("+359..."),
            new PushNotification("device-42"),
        };

        sendAll(all);
    }
}
