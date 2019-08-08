package bg.uni.sofia.fmi.mjt.supermarket;

public class Vault extends Thread {

    private double totalCash = 0.0;
    private CashDeskImpl cashDesk;

    public Vault(CashDeskImpl cashDesk) {
        setDaemon(true);
        this.cashDesk = cashDesk;
    }

    public double getTotalCash() {
        return totalCash;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (cashDesk) {
                try {
                    cashDesk.wait();
                    totalCash += cashDesk.getAmount();
                    cashDesk.setAmount(0.0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
