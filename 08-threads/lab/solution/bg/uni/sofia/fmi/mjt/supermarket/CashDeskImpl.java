package bg.uni.sofia.fmi.mjt.supermarket;

public class CashDeskImpl implements CashDesk {

    private static final int MAX_CASH = 100;

    private double amount;

    public synchronized double getAmount() {
        return amount;
    }

    @Override
    public synchronized void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public void serveCustomer(bg.uni.sofia.fmi.mjt.supermarket.Customer customer) {
        synchronized (this) {
            amount += customer.buyGoods();

            if (amount > MAX_CASH) {
                this.notify();
            }
        }
    }

}