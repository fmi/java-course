/**
 * ============================================================================
 *  LAB 02 · INTERACTIVE SNIPPET
 * ============================================================================
 *
 *    1. Add a brand-new notification type (e.g. DiscordNotification).
 *    2. Do the same in PolymorphicNotification and compare both implementations.
 *    2. Which version of sendAll is:
 *          clearer to read:  ______
 *          more performant:  ______
 *          less error-prone: ______
 *          easier to extend: ______
 */
public class NonPolymorphicNotification {

    static abstract class AbstractNotification {
        protected final String recipient;

        AbstractNotification(String recipient) {
            this.recipient = recipient;
        }
    }

    static final class EmailNotification extends AbstractNotification {
        EmailNotification(String recipient) {
            super(recipient);
        }

        public void sendEmail() {
            System.out.println("email -> " + recipient);
        }
    }

    static final class SmsNotification extends AbstractNotification {
        SmsNotification(String recipient) {
            super(recipient);
        }

        public void sendSms() {
            System.out.println("sms   -> " + recipient);
        }
    }

    static final class PushNotification extends AbstractNotification {
        PushNotification(String recipient) {
            super(recipient);
        }

        public void sendPush() {
            System.out.println("push  -> " + recipient);
        }
    }

    static void sendAll(AbstractNotification[] all) {
        for (AbstractNotification n : all) {
            if (n instanceof EmailNotification) {
                ((EmailNotification) n).sendEmail();
            } else if (n instanceof SmsNotification) {
                ((SmsNotification) n).sendSms();
            } else if (n instanceof PushNotification) {
                ((PushNotification) n).sendPush();
            }
        }
    }

    public static void main(String... args) {
        AbstractNotification[] all = {
            new EmailNotification("ivan@fmi.bg"),
            new SmsNotification("+359..."),
            new PushNotification("device-42"),
        };

        sendAll(all);
    }
}
