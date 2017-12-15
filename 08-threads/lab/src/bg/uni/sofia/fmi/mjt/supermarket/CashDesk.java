package bg.uni.sofia.fmi.mjt.supermarket;

public interface CashDesk {

    /**
     * Serves a customer
     */
    void serveCustomer(Customer customer);

    /**
     * Returns the cash amount currently contained in this cash desk
     */
    double getAmount();

    /**
     * Updates the cash amount currently contained in this cash desk
     */
    void setAmount(double amount);

}
