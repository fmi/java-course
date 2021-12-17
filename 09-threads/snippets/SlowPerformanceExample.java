/**
 * Shows how well-defined short synchronization would
 * perform better than unnecessarily long one.
 */
public class SlowPerformanceExample {

    public static long testBankAccount(BankAccount bankAccount) throws Exception {
        // Gets the current time in the beggining
        long startTime = System.currentTimeMillis();

        // 5 threads will deposit money in a shared bank account
        Depositor[] depositors = new Depositor[5];
        for (int i = 0; i < depositors.length; i++) {
            depositors[i] = new Depositor(bankAccount, 1_000);
            depositors[i].start();
        }

        // Main thread will wait depositor threads to terminate
        for (int i = 0; i < depositors.length; i++) {
            depositors[i].join();
        }

        // Get the current time in the end
        long endTime = System.currentTimeMillis();
        System.out.println(bankAccount);

        // return the ~ method duration time
        return (endTime - startTime) / 1_000;
    }

    public static void main(String[] args) throws Exception {
        // Test against two bank account implementations
        long resultSlow = testBankAccount(new SlowPrintableBankAccount("Ivan"));
        long resultFast = testBankAccount(new FastPrintableBankAccount("Pesho"));

        System.out.println("Slow implementation executed for : " + resultSlow + " sec.");
        System.out.println("Fast implementation executed for : " + resultFast + " sec.");
    }
}

/**
 * Printable bank account - an invoice is "issued" after each deposit.
 */
class SlowPrintableBankAccount extends BankAccount {
    public SlowPrintableBankAccount(String name) {
        super(name, 0);
    }

    @Override
    public synchronized void deposit(double amount) {
        // deposit money
        super.deposit(amount);
        // print invoice
        printInvoice();
    }

    private void printInvoice() {
        try {
            System.out.println(balance);
            // Purposely sleep to emulate slow operation
            Thread.sleep(1);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

/**
 * Банкова сметка, която "отпечатва" фактура след извършване на депозит.
 * Тя работи няколко пъти по-бързо (на моето PC с този вход - около 6 пъти)
 * от бавната банкова сметка. Причината е, че бавният метод printInvoice е
 * вече извън критичната секция
 */
class FastPrintableBankAccount extends BankAccount {
    public FastPrintableBankAccount(String name) {
        super(name, 0);
    }

    @Override
    public void deposit(double amount) {

        // we must save the current balance in local snapshot.
        // This variable is not shared and we can safely
        // print it outside of synchronization block.
        double snapshot = 0;

        // Synchronize only the concurrent part
        synchronized (this) {
            super.deposit(amount);
            snapshot = this.balance;
        }

        // Safely execute slow operation outside of synchronization block.
        printInvoice(snapshot);
    }

    private void printInvoice(double balance) {
        try {
            System.out.println(balance);
            // Purposely sleep to emulate slow operation
            Thread.sleep(1);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
