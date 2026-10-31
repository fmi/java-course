/**
 * ============================================================================
 *  LAB 03 · INTERACTIVE SNIPPET
 * ============================================================================
 *
 *  PREDICT each printed line BEFORE running (fill the blanks).
 * ============================================================================
 */
public class SwapThat {

    static final class Account {
        String owner;
        int balance;

        Account(String owner, int balance) {
            this.owner = owner;
            this.balance = balance;
        }

        @Override
        public String toString() {
            return owner + "=" + balance;
        }
    }

    static void swap(Account a, Account b) {
        Account tmp = a;
        a = b;
        b = tmp;
    }

    static void topUp(Account a, int amount) {
        a.balance += amount;
    }

    public static void main(String... args) {
        Account ivan = new Account("ivan", 100);
        Account maria = new Account("maria", 500);

        swap(ivan, maria);
        System.out.println("after swap:  " + ivan + ", " + maria);   // PREDICT: ______

        topUp(ivan, 50);
        System.out.println("after topUp: " + ivan + ", " + maria);   // PREDICT: ______

        Account[] accounts = { ivan, maria };
        reassignElementZero(accounts);
        System.out.println("array[0]:    " + accounts[0]);           // PREDICT: ______
    }

    static void reassignElementZero(Account[] arr) {
        arr[0] = new Account("stoyo", 999);
    }
}
