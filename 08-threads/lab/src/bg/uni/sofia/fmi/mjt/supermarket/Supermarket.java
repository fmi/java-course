package bg.uni.sofia.fmi.mjt.supermarket;

public class Supermarket implements CashDesk {

    private static final int MAX_DESKS = 20;
    private static final int MAX_DELAY = 500;
    private static final int MIN_DELAY = 100;

    public Supermarket() {

    }

    @Override
    public void serveCustomer(Customer customer) {
        throw new UnsupportedOperationException("Please implement this method");
    }

    @Override
    public double getAmount() {
        return 0.0;
    }

    @Override
    public void setAmount(double amount) {

    }

}
