package howmany;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

public class HowManyPlatformThreads {

    // ATTENTION: THIS MAY CRASH YOUR MACHINE!

    // Enable NMT and inspect native vs heap memory: start JVM with -XX:NativeMemoryTracking=summary,
    // use `jcmd <pid> VM.native_memory` for native breakdown (e.g., per-thread stacks),
    // and `jmap -histo <pid>` for on-heap class sizes—useful when many platform threads cause OOM
    // due to native (not heap) usage.

    // Safe limit on Windows 11 with 64 GB: 20,000
    private static final int NUMBER_OF_THREADS = 20_000;

    static void main() throws InterruptedException {
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
