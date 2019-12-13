public class ThreadCreation {

    public static void main(String[] args) throws Exception {

        // Start few custom threads
        CustomThread customThread = new CustomThread("Custom#1");
        customThread.start();

        CustomThread customThread2 = new CustomThread("Custom#2");
        customThread2.start();

        // Start a runnable
        Thread customThread3 = new Thread(new CustomRunnable());
        customThread3.start();

        // Sleeps are used to stress that the threads are executiong asynchronous
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1);
            System.out.println("Hello world from main thread");
        }
    }
    
}

/**
 * Sample thread implementation. A thread subclass is ordinary java object
 * it may have state, custom constructor and additional methods.
 */
class CustomThread extends Thread {
    private String name;

    public CustomThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("Hello world from custom " + name);
        }
    }
}

/**
 * Sample runnable implementation.
 */
class CustomRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("Hello world from runnable ");
        }
    }
}