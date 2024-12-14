package threadpools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolExample {

    // A cached thread pool will reuse available threads, but if all threads are busy
    // and more tasks are submitted, it will create new threads.
    // If a thread is idle for 60s, it will be decommissioned.

    private static final int MAX_TASKS = 15;

    public static void main(String... args) {

        // Creates a thread pool with a fixed number of threads
        ExecutorService executor = Executors.newCachedThreadPool();

        // Submit tasks to the executor.
        for (int i = 0; i < MAX_TASKS; i++) {
            Runnable task = new Task("" + i);
            executor.execute(task); // Execute the task using the thread pool.
        }

        // Shutdown the executor service. It will not accept new tasks but complete existing ones.
        executor.shutdown();
    }
}
