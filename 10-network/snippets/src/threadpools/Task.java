package threadpools;

public class Task implements Runnable {
    private final String taskId;

    public Task(String id) {
        this.taskId = id;
    }

    @Override
    public void run() {
        System.out.println("Executing Task Id: " + taskId + ", by " + Thread.currentThread().getName());
        try {
            Thread.sleep(1_000); // Simulate some work with a sleep.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
