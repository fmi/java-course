package bg.uni.sofia.fmi.mjt.supermarket;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Supermarket implements CashDesk {
    private static final int MAX_DESKS = 20;
    private static final int INIT_DESKS = 5;
    private static final int MAX_DELAY = 500;
    private static final int MIN_DELAY = 100;

    private BlockingQueue<CashDeskImpl> desks = new LinkedBlockingDeque<>();

    private AtomicInteger delay = new AtomicInteger();

    // Accumulate balance of all closed cashdesks
    private AtomicInteger totalAmountClosed = new AtomicInteger(0);

    public Supermarket() {
        for (int i = 0; i < INIT_DESKS; i++) {
            CashDeskImpl cashDesk = new CashDeskImpl();
            try {
                desks.put(cashDesk);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // NB! is not thread-safe it should not be executed concurrently
    @Override
    public double getAmount() {
        return totalAmountClosed.get() + desks.stream().mapToDouble(CashDeskImpl::getAmount).sum();
    }

    @Override
    public void setAmount(double amount) {

    }

    @Override
    public void serveCustomer(bg.uni.sofia.fmi.mjt.supermarket.Customer customer) {
        try {
            // update total wait time
            delay.addAndGet(customer.getServiceTime());

            // get free desk or wait
            CashDeskImpl desk = null;

            // If delay exceed some threshold - add new desk
            if (delay.get() > MAX_DELAY && desks.size() < MAX_DESKS) {
                desk = new CashDeskImpl();
            } else {
                desk = desks.take();
            }

            // serve the customer (synchronized in serverCustomer is no longer needed)
            desk.serveCustomer(customer);

            if (delay.get() > MIN_DELAY && desks.size() < 5) {
                desks.put(desk);
            } else {
                totalAmountClosed.addAndGet((int) desk.getAmount());
            }

            // decrement total delay time
            delay.addAndGet(-customer.getServiceTime());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
