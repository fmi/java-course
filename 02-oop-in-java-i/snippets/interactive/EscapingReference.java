/**
 * ============================================================================
 *  LAB 02 · INTERACTIVE SNIPPET
 * ============================================================================
 *
 *  THE BUG HUNT (use the DEBUGGER, not println):
 *    1. Put a breakpoint in main().
 *    2. Add a WATCH on `group.recipients` (the private array).
 *    3. Step OVER each line and trace the bug.
 *
 */
public class EscapingReference {

    static final class RecipientGroup {
        private final String[] recipients;
        private int count;

        RecipientGroup(int capacity) {
            this.recipients = new String[capacity];
        }

        boolean add(String recipient) {
            if (recipient == null || recipient.isBlank()) {
                return false; // invariant: no blanks
            }
            if (count == recipients.length) {
                return false; // invariant: no overflow
            }
            recipients[count++] = recipient;
            return true;
        }

        int size() {
            return count;
        }

        String[] getRecipients() {
            return recipients;
        }
    }

    public static void main(String... args) {
        RecipientGroup group = new RecipientGroup(5);
        group.add("ivan@fmi.bg");
        group.add("maria@fmi.bg");

        String[] view = group.getRecipients();
        view[0] = "";
        view[2] = "stoyo@fmi.bg";

        System.out.println("size: " + group.size());
        System.out.print("contents: ");

        for (String r : group.getRecipients()) {
            System.out.println(r);
        }
        // PREDICT: __________________
    }
}
