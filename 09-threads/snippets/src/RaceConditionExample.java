public class RaceConditionExample {

    public static void main(String[] args) throws Exception {
        // Shared bank account
        BankAccount acc = new BankAccount("Anonymous");
        BankAccount acc2 = new BankAccount("Second...");

        // Many threads will concurrently deposit money into the same account
        Depositor[] depositors = new Depositor[100];
        for (int i = 0; i < depositors.length; i++) {
            depositors[i] = new Depositor(acc, 500);
            depositors[i].start();
        }

        // Main thread will wait until all depositors are done.
        for (int i = 0; i < depositors.length; i++) {
            depositors[i].join();
        }

        // Result should be consistent, if BankAccount is thread-safe
        System.out.println(acc);
        System.out.println("Operations: " + BankAccount.getOpCount());
    }

}
