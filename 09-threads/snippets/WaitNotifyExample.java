public class WaitNotifyExample {

    public static void main(String[] args) throws Exception {
        CreditBankAccount account = new CreditBankAccount("Student");

        // The thread will try to withdraw 100
        CreditFeeTaker creditFeeTaker = new CreditFeeTaker(account, 100);
        creditFeeTaker.start();

        // The thread will try to withdraw 50
        CreditFeeTaker creditFeeTaker2 = new CreditFeeTaker(account, 50);
        creditFeeTaker2.start();

        // Wait a bit
        Thread.sleep(500);

        // Deposit 120
        account.deposit(120);

        // Wait a bit
        Thread.sleep(500);

        // Deposit more money
        account.deposit(30);

        creditFeeTaker.join();
        creditFeeTaker2.join();
        System.out.println(account);
    }
}

class CreditBankAccount extends BankAccount {
    public CreditBankAccount(String name) {
        super(name, 0);
    }

    @Override
    public synchronized void deposit(double amount) {
        super.deposit(amount);
        // We use notifyAll() as there may be enough money so few threads can proceed.
        this.notifyAll();
    }

    public synchronized void withdrawCreditPayment(double monthFee) {
        // Wait guarded - we check in while block whether the condition is met
        // if no - the thread will wait again. This enables us to safely use notifyAll
        while (balance < monthFee) {
            System.out.println("Not enough money, thread will wait");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        withdraw(monthFee);
        System.out.println("Successfully withdraw money. Remaining balance " + balance);
    }
}

class CreditFeeTaker extends Thread {
    private CreditBankAccount account;
    private double fee;

    public CreditFeeTaker(CreditBankAccount account, double fee) {
        this.account = account;
        this.fee = fee;
    }

    @Override
    public void run() {
        account.withdrawCreditPayment(this.fee);
    }
}
