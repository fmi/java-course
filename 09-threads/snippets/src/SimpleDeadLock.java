/**
 * Trivial deadlock example.
 * The two threads take locks in exchanged order.
 */
public class SimpleDeadLock {
    public static void main(String[] args) {
        // Two shared resources
        Object vinegar = new Object();
        Object oil = new Object();

        // Friend1 always gets oil first then vinegar
        Thread friend1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (oil) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (vinegar) {
                        System.out.println("Friend1 made his salad");
                    }
                }
            }
        });

        // Friend2 always gets vinegar first then oil
        Thread friend2 = new Thread(new Runnable() {
            @Override
            public void run() {
                // To resolve the deadlock
                // lock the monitors in the same order
                synchronized (vinegar) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (oil) {
                        System.out.println("Friend2 made his salad");
                    }
                }
            }
        });

        friend1.start();
        friend2.start();
    }

}
