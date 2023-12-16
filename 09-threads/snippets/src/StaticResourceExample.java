public class StaticResourceExample {
    public static void main(String[] args) throws Exception {

        // BankAccount of Ivan and Pesho
        BankAccount ivanAcc = new BankAccount("Ivan");
        BankAccount peshoAcc = new BankAccount("Pesho");

        // 10 depositor threads - 5 will deposit to Ivan and 5 to Pesho
        Depositor[] depositors = new Depositor[10];
        for (int i = 0; i < depositors.length; i++) {
            if (i % 2 == 0) {
                depositors[i] = new Depositor(ivanAcc, 1000);
            } else {
                depositors[i] = new Depositor(peshoAcc, 1000);
            }
            depositors[i].start();
        }

        for (int i = 0; i < depositors.length; i++) {
            depositors[i].join();
        }

        // Should be correct
        System.out.println(ivanAcc);
        System.out.println(peshoAcc);

        // Total ops count should be 10000
        System.out.println("Total deposit ops : " + BankAccount.getOpCount());
    }
}
