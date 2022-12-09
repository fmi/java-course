/**
 * Daemon threads sample
 */
public class DaemonThreads {

    public static void main(String[] args) {
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.start();

        System.out.println("Main thread terminates");
    }

}

/**
 * The thread will count to 1000 in the background.
 */
class BackgroundTask extends Thread {
    public BackgroundTask() {
        // if you comment next line, thread will
        // be non-daemon and will always reach to the end and exit normally
        setDaemon(true);
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
        System.out.printf("%s thread terminates", (isDaemon() ? "Daemon" : "Non-daemon"));
    }

}
