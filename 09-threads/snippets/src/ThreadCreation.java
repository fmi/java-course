import java.util.concurrent.ThreadFactory;
import java.util.random.RandomGenerator;

public class ThreadCreation {

    public static void main(String[] args) throws Exception {
        startThreadsClassic();
        startThreadsJava21Style();

        // Sleeps are used to stress that the threads are executing asynchronously

        //Thread.sleep(1);
        System.out.println("Hello world from main thread");
    }

    private static void startThreadsClassic() {
        // Option 1: extend java.lang.Thread
        CustomThread customThread1 = new CustomThread("Custom Thread #1");
        customThread1.start();

        // Option 2: implement java.lang.Runnable
        Thread customThread2 = new Thread(new CustomRunnable(), "Custom Thread #2");
        customThread2.start();

        // Use anonymous class
        Thread customThread3 = new Thread("Custom Thread #3") {
            @Override
            public void run() {
                System.out.println("Hello world, from an anonymous class!");
            }
        };
        customThread3.start();

        // Use lambda
        Thread customThread4 = new Thread(() -> System.out.println("Hello world, from a lambda!"), "Custom Thread #4");
        customThread4.start();
    }

    private static void startThreadsJava21Style() {
        Thread platformThread1 = Thread.ofPlatform().start(() -> System.out.println("Simple, right?"));
        platformThread1.setName("gogo");
        //platformThread1.setDaemon(true);
        platformThread1.setPriority(7);

        ThreadFactory factory = Thread.ofPlatform()
            .daemon()
            .group(new ThreadGroup("Netherflame Syndicate"))
            .name("Harmless Daemon-", 0).factory();

        Thread platformThread2 = factory.newThread(new CustomRunnable());
        platformThread2.start();

        Thread platformThread3 = factory.newThread(new CustomRunnable());
        platformThread3.start();

        System.out.println(platformThread2.getName());
        System.out.println(platformThread3.getName());

        Thread virtualThread1 = Thread.ofVirtual().start(new CustomRunnable());

        Thread virtualThread2 = Thread.startVirtualThread(() -> {
            System.out.println("Also quite straightforward");
        });

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
        try {
            Thread.sleep(RandomGenerator.getDefault().nextInt(10));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Hello world from " + name);
    }
}

/**
 * Sample runnable implementation.
 */
class CustomRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello world from a Runnable");
    }
}
