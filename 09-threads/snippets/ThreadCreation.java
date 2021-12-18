public class ThreadCreation {

    public static void main(String[] args) throws Exception {

        // Start few custom threads
        CustomThread customThread = new CustomThread("Custom#1");
        customThread.start();

        CustomThread customThread2 = new CustomThread("Custom#2");
        customThread2.start();

        // Use dedicated Runnable
        Thread customThread3 = new Thread(new CustomRunnable());
        customThread3.start();

        // Use anonymous class
        Thread customThread4 = new Thread() {
            @Override
            public void run() {
                System.out.println("Hello world, from an anonymous class!");
            }
        };
        customThread4.start();

        // Use lambda
        Thread customThread5 = new Thread(() -> System.out.println("Hello world, from a lambda!"));
        customThread5.start();

        // Sleeps are used to stress that the threads are executing asynchronously
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1);
            System.out.println("Hello world from main thread");
        }
    }
    
}

/**
 * Sample thread implementation. A thread subclass is an ordinary java class -
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
                e.printStackTrace();
            }
            System.out.println("Hello world from runnable");
        }
    }
}
