package howmany;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

public class HowManyPlatformThreads {

    // ATTENTION: THIS MAY CRASH YOUR MACHINE!

    // start with -XX:NativeMemoryTracking=summary
    // jcmd <pid> VM.native_memory
    // jmap -histo <pid>

    // Safe limit on Windows 11 with 64 GB: 20,000
    private static final int NUMBER_OF_THREADS = 20_000;

    public static void main(String[] args) throws InterruptedException {
        AtomicLong runningThreadsCounter = new AtomicLong();

        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= NUMBER_OF_THREADS; i++) {
            Thread.ofPlatform()
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

            if (i % 1000 == 0) {
                long time = System.currentTimeMillis() - startTime;
                System.out.printf(
                    "%,d threads started, %,d threads running after %,d ms%n",
                    i, runningThreadsCounter.get(), time);
            }
        }
    }
}
