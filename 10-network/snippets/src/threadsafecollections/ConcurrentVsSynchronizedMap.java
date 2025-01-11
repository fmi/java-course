package threadsafecollections;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentVsSynchronizedMap {

    private static final int NUM_THREADS = 2_000;
    private static final int NUM_OPERATIONS = 10_000;

    public static void main(String[] args) throws InterruptedException {

        // Hashtable example
        Map<Integer, Integer> hashtable = new Hashtable<>();

        long hashtableStartTime = System.nanoTime();
        Thread[] hashtableThreads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            hashtableThreads[i] = new Thread(() -> {
                for (int j = 0; j < NUM_OPERATIONS; j++) {
                    hashtable.put(j, j);
                    hashtable.get(j);
                }
            });
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            hashtableThreads[i].start();
        }
        for (int i = 0; i < NUM_THREADS; i++) {
            hashtableThreads[i].join();
        }

        long hashtableEndTime = System.nanoTime();

        System.out.println("Hashtable execution time: " + (hashtableEndTime - hashtableStartTime) / 1_000_000 + " ms");

        // ConcurrentHashMap example
        Map<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();

        long concurrentHashMapStartTime = System.nanoTime();
        Thread[] concurrentHashMapThreads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            concurrentHashMapThreads[i] = new Thread(() -> {
                for (int j = 0; j < NUM_OPERATIONS; j++) {
                    concurrentHashMap.put(j, j);
                    concurrentHashMap.get(j);
                }
            });
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            concurrentHashMapThreads[i].start();
        }
        for (int i = 0; i < NUM_THREADS; i++) {
            concurrentHashMapThreads[i].join();
        }

        long concurrentHashMapEndTime = System.nanoTime();

        System.out.println(
            "ConcurrentHashMap execution time: " + (concurrentHashMapEndTime - concurrentHashMapStartTime) / 1_000_000 + " ms");
    }

}