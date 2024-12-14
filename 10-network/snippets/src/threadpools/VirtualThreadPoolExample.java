package threadpools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreadPoolExample {

    public static void main(String[] args) {

        // Create an executor service using a new virtual threads for every new task
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

        try (executorService) {

            // Submit several tasks to the executor
            for (int i = 0; i < 5; i++) {

                final int taskId = i;

                executorService.submit(() -> {
                    // Note that virtual threads are created without a name by default to minimize overhead
                    // and because the high number of threads typically makes naming them impractical.
                    System.out.println("Running task " + taskId + " in " + Thread.currentThread());

                    // Simulate some work with sleep
                    try {
                        Thread.sleep(1_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }

        } // executorService will be autoclosed --> auto shut down

    }
}
