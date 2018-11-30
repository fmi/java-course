

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BlockingQueueExample {


  public static void main(String[] args) {
    // Shared object
    ArrayBlockingQueue<String> data = new ArrayBlockingQueue<String>(5);

    // Create a thread pool
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

    // produce some items asynchronously
    executor.scheduleAtFixedRate(new Producer(data), 0, 1, TimeUnit.SECONDS);
    // consume some items asynchronously
    executor.scheduleAtFixedRate(new Consumer(data), 10, 2, TimeUnit.SECONDS);
  }
}

class Producer implements Runnable {
  private ArrayBlockingQueue<String> data;

  public Producer(ArrayBlockingQueue<String> data) {
    this.data = data;
  }

  @Override
  public void run() {
    try {
      // no need of synchronization as BlockingQueue is thread safe.
      // We will wait if the queue is full and automatically get 
      // notified when consumer takes an item.
      String result = "" + new Random().nextInt(10); 
      data.put(result);
      System.out.println("Producer put " + result);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

class Consumer implements Runnable {
  private ArrayBlockingQueue<String> data;

  public Consumer(ArrayBlockingQueue<String> data) {
    this.data = data;
  }

  @Override
  public void run() {
    try {
      // no need of synchronization as BlockingQueue is thread safe.
      // We will wait if there are no items available and 
      // automatically get notified when producer put item.
      String result = data.take();
      System.out.println("Consumer got " + result);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
