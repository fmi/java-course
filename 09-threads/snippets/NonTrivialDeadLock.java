/**
 * Complex dead-lock example.
 */
public class NonTrivialDeadLock {

    public static void main(String[] args) throws Exception {
        BankAccount ivan = new BankAccount("Ivan", 1000);
        BankAccount pesho = new BankAccount("Pesho", 1000);

        Thread t1 = new Thread(() -> transfer(ivan, pesho, 100));

        Thread t2 = new Thread(() -> transfer(pesho, ivan, 100));

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(ivan);
        System.out.println(pesho);
    }

    // This will deadlock!
    public static void transferBuggy(BankAccount source, BankAccount target, double amount) {
        synchronized (source) {
            System.out.println("Locked " + source.hashCode());

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (target) {
                System.out.println("Locked " + target.hashCode());

                source.withdraw(amount);
                target.deposit(amount);
            }
        }
    }

    public static void transfer(BankAccount source, BankAccount target, double amount) {
        BankAccount first;
        BankAccount second;

        // Always order the locks in the same way
        // Here, we will use the names of the accounts' owners
        // The names are immutable so we can use them safely outside synchronized block
        if (source.getName().compareTo(target.getName()) < 0) {
            first = source;
            second = target;
        } else {
            first = target;
            second = source;
        }

        synchronized (first) {
            System.out.println("Locked " + first.hashCode());

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (second) {
                System.out.println("Locked " + second.hashCode());

                source.withdraw(amount);
                target.deposit(amount);
            }
        }
    }

}
