package bg.uni.sofia.fmi.mjt.supermarket;

public class Start {

	public static void main(String[] args) {
		CashDeskImpl cashDesk = new CashDeskImpl();
		Supermarket supermarket = new Supermarket();

		test(cashDesk);
		test(supermarket);

	}

	public static void test(CashDesk cashDesk) {
		final int CUSTOMERS = 2000;

		long start = System.currentTimeMillis();

		Customer[] customers = new Customer[CUSTOMERS];
		for (int i = 0; i < customers.length; i++) {
			customers[i] = new Customer(cashDesk, 10, 1.0);
			customers[i].start();
		}

		for (int i = 0; i < customers.length; i++) {
			try {
				customers[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		long end = System.currentTimeMillis();
		System.out.println("" + (end - start) / 1000 + "s");
		System.out.println(cashDesk.getAmount());
	}

}
