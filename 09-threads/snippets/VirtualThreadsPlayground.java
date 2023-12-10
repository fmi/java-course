import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class VirtualThreadsPlayground {

    // Virtual threads are a newly introduced preview feature in Java 19
    // To compile and run it, make sure preview features are enabled in your IDE

    private void startVirtualThreads() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 100_000).forEach(i -> executor.submit(() -> {
                Thread.sleep(Duration.ofSeconds(1));
                return i;
            }));
        }  // close() called implicitly
    }

    private void startPlatformThreadsFixedPool() {
        try (var executor = Executors.newFixedThreadPool(1_000)) {
            IntStream.range(0, 100_000).forEach(i -> executor.submit(() -> {
                Thread.sleep(Duration.ofSeconds(1));
                return i;
            }));
        }
    }

    private void startPlatformThreadsCachedPool() {
        try (var executor = Executors.newCachedThreadPool()) {
            // this one is expected to fail: platform threads are an OS-limited resource
            IntStream.range(0, 100_000).forEach(i -> executor.submit(() -> {
                Thread.sleep(Duration.ofSeconds(1));
                return i;
            }));
        }
    }

    public static void main(String... args) {
        var playground = new VirtualThreadsPlayground();

        long startTime = System.nanoTime();
        playground.startVirtualThreads();
        long endTime = System.nanoTime();

        System.out.println("Starting virtual threads took " + (endTime - startTime) / 1_000_000 + "ms.");

        startTime = System.nanoTime();
        playground.startPlatformThreadsFixedPool();
        endTime = System.nanoTime();

        System.out.println("Starting platform threads from fixed pool took " + (endTime - startTime) / 1_000_000 + "ms.");

        startTime = System.nanoTime();
        playground.startPlatformThreadsCachedPool();
        endTime = System.nanoTime();

        System.out.println("Starting platform threads from cached pool took " + (endTime - startTime) / 1_000_000 + "ms.");
    }

}
