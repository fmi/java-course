/**
 * Deamon threads sample
 */
public class DaemonThreads {
    public static void main(String[] args) {
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.start();

        System.out.println("Main thread terminates");
    }

}

/**
 * The thread will count to 1000 in background.
 */
class BackgroundTask extends Thread {
    public BackgroundTask() {
        // if you comment next line daemon thread will 
        // always reach to the end and exit normally
        setDaemon(true);
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
        System.out.println("Deamon thread terminates");
    }

}
