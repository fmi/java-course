import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample {

    public static void main(String args[]) throws InterruptedException {
        AtomicInteger atomicInt = new AtomicInteger();
        NonAtomicInteger nonAtomicInt = new NonAtomicInteger();

        Runnable r = () -> {
            for (int j = 0; j < 100_000; j++) {
                atomicInt.getAndIncrement();
                nonAtomicInt.getAndIncrement();
            }
            System.out.println("Сбогом, жесток свят " + Thread.currentThread().getName());
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        Thread t3 = new Thread(r);
        Thread t4 = new Thread(r);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.printf("The atomic integer should be 400000, it is %d%n", atomicInt.get());
        System.out.printf("The non-atomic integer should be 400000, it is %d%n", nonAtomicInt.get());
    }

    public static class NonAtomicInteger {
        private int i;

        public NonAtomicInteger() {
            i = 0;
        }

        public synchronized int getAndIncrement() {
            return i++;
        }

        public synchronized int get() {
            return i;
        }
    }

}
