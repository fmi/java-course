package bg.uni.sofia.fmi.mjt.supermarket;

public class CashDeskImpl implements CashDesk {

    private static final int MAX_CASH = 100;

    public CashDeskImpl() {

    }

    @Override
    public void serveCustomer(Customer customer) {
        throw new UnsupportedOperationException("Please implement this method");
    }

    @Override
    public double getAmount() {
        throw new UnsupportedOperationException("Please implement this method");
    }

    @Override
    public void setAmount(double amount) {
        throw new UnsupportedOperationException("Please implement this method");
    }

}
