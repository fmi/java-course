public class InterruptExample {
  public static void main(String[] args) throws Exception {
    // ThreadGroup with max thread prio = 10
    ThreadGroup group = new ThreadGroup("Interruptable thread group");
    group.setMaxPriority(10);

    // The first thread will have max prio - 10
    InteruptableThread t1 = new InteruptableThread(group, "Worker 1");
    t1.setPriority(10);
    
    // The second thread will have min prio - 1
    InteruptableThread t2 = new InteruptableThread(group, "Worker 2");
    t2.setPriority(1);

    // Start the treads
    t1.start();
    t2.start();

    // Wait a bit
    Thread.sleep(2);

    // Send interrupt signal
    t1.interrupt();
    t2.interrupt();
  }
}

/**
 * The thread counts from 0 to 9999. Before each iteration checks whether
 * interrupt was called - if yes, prints exit and the last reached index.
 * You should see that priorities does not work as expected - worker 1 does not
 * reach 10 times further than Worker 2.
 */
class InteruptableThread extends Thread {

  public InteruptableThread(ThreadGroup group, String name) {
    super(group, name);
  }

  @Override
  public void run() {
    // the last index that has been read
    int last = 0;
    for (int i = 0; i < 10000; i++) {
      if (this.isInterrupted()) {
        System.out.println(getName() + " was interrupted");
        break;
      }

      last = i;
    }

    // print thread name, iterrupt flag and last index
    System.out.println(getName() + " (isInterrupted = " + isInterrupted() + ") counted to : " + last);
    System.out.println(getThreadGroup().getName() + " " + Thread.currentThread().getName() + " exits!");
  }

}
