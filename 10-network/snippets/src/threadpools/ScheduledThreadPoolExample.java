package threadpools;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExample {

    // A scheduled thread pool is used for scheduling tasks
    // to execute after a delay or to execute periodically.

    public static void main(String[] args) throws InterruptedException {

        // Creates a scheduled thread pool with 2 threads.
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

        // Schedule tasks
        scheduler.schedule(() -> System.out.println("Delayed task executed after 5 seconds"), 5, TimeUnit.SECONDS);

        // Schedule a task to repeat at a fixed rate.
        // The interval is based on the scheduled start time of each execution, maintaining a strict execution schedule
        // which may lead to task overlap if execution time exceeds the scheduled rate.
        scheduler.scheduleAtFixedRate(() -> {
            doSomeWork(2_000);
            System.out.println("Some regular task executed at fixed rate by " + Thread.currentThread().getName());
        }, 0, 1, TimeUnit.SECONDS);

        // Schedule a task to repeat with a fixed delay based on the actual completion time of the previous execution,
        // providing buffer time if the task takes longer, preventing task overlap.
        scheduler.scheduleWithFixedDelay(() -> {
            doSomeWork(2_000);
            System.out.println("Some regular task executed with fixed delay by " + Thread.currentThread().getName());
        }, 0, 1, TimeUnit.SECONDS);

        // Shut down the scheduler after some delay to allow time for tasks to execute.
        scheduler.schedule(() -> scheduler.shutdown(), 20, TimeUnit.SECONDS);

    }

    private static void doSomeWork(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
