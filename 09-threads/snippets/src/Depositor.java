/**
 * Thread will simply iterate n times and deposit 1
 * into a shared account
 */
public class Depositor extends Thread {
    private BankAccount account;
    private int attempts;

    public Depositor(BankAccount account, int attempts) {
        this.account = account;
        this.attempts = attempts;
    }

    @Override
    public void run() {
        for (int i = 0; i < attempts; i++) {
            account.deposit(1);
        }
    }

}
