package threadpools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolExample {

    // A fixed thread pool contains a specified number of threads. If all threads are busy
    // and additional tasks are submitted, they will wait in the queue until a thread is available.

    private static final int MAX_THREADS = 3;
    private static final int MAX_TASKS = 15;

    public static void main(String... args) {

        // Creates a thread pool with a fixed number of threads
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

        // Submit tasks to the executor.
        for (int i = 0; i < MAX_TASKS; i++) {
            Runnable task = new Task("" + i);
            executor.execute(task); // Execute the task using the thread pool.
        }

        // Shutdown the executor service. It will not accept new tasks but complete existing ones.
        executor.shutdown();
    }
}
