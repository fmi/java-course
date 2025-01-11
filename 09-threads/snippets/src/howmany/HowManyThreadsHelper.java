package howmany;

import java.util.concurrent.atomic.AtomicLong;

public class HowManyThreadsHelper {

    private static final long SLEEP_MILLIS = 100;

    static void doSomething() {
        long millis = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(SLEEP_MILLIS);
                millis += SLEEP_MILLIS;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("I died after " + millis + " milliseconds");
    }

    static void waitForVirtualThreadsToCatchUp(
        int startedThreads, AtomicLong runningThreadsCounter, long startTime)
        throws InterruptedException {
        long runningThreads;
        while (startedThreads > (runningThreads = runningThreadsCounter.get())) {
            long time = System.currentTimeMillis() - startTime;
            System.out.printf(
                "Waiting for virtual threads to catch up: (%,d running after %,d ms)...%n",
                runningThreads, time);
            Thread.sleep(100);
        }
        long time = System.currentTimeMillis() - startTime;
        System.out.printf(
            "Virtual threads caught up: %,d running after %,d ms%n", runningThreads, time);
    }
}
