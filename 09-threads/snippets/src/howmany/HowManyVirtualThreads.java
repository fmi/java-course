package howmany;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

public class HowManyVirtualThreads {

    // Activate GC logging: -Xlog:gc*
    // jmap -histo <pid>

    private static final int NUMBER_OF_VIRTUAL_THREADS = 100_000_000;
    private static final int PRINT_STEP = Math.min(NUMBER_OF_VIRTUAL_THREADS / 10, 100_000);

    public static void main(String[] args) throws InterruptedException {
        AtomicLong runningThreadsCounter = new AtomicLong();

        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= NUMBER_OF_VIRTUAL_THREADS; i++) {
            Thread.ofVirtual()
                .start(
                    () -> {
                        runningThreadsCounter.incrementAndGet();
                        try {
                            Thread.sleep(Duration.ofDays(100));
                        } catch (InterruptedException e) {
                            // Let the thread die
                        }
                        System.out.println("I died");
                    });

            if (i % PRINT_STEP == 0) {
                long runningThreads = runningThreadsCounter.get();
                long time = System.currentTimeMillis() - startTime;
                System.out.printf(
                    "%,d virtual threads started, %,d virtual threads running after %,d ms%n",
                    i, runningThreads, time);

                if (i - runningThreads > 100_000) {
                    HowManyThreadsHelper.waitForVirtualThreadsToCatchUp(i, runningThreadsCounter, startTime);
                }
            }
        }

        HowManyThreadsHelper.waitForVirtualThreadsToCatchUp(
            NUMBER_OF_VIRTUAL_THREADS, runningThreadsCounter, startTime);

        // Sleep, so we can look at the memory usage
        Thread.sleep(Duration.ofHours(1));
    }
}
