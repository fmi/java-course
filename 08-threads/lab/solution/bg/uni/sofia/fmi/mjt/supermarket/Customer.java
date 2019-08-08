package bg.uni.sofia.fmi.mjt.supermarket;

public class Customer extends Thread {

    private int serviceTime;
    private double totalPrice;
    private CashDesk cashDesk;

    public Customer(CashDesk cashDesk, int serviceTime, double totalPrice) {
        this.cashDesk = cashDesk;
        this.totalPrice = totalPrice;
        this.serviceTime = serviceTime;
    }

    @Override
    public void run() {
        cashDesk.serveCustomer(this);
    }

    public double buyGoods() {
        try {
            Thread.sleep(serviceTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getServiceTime() {
        return serviceTime;
    }

}
