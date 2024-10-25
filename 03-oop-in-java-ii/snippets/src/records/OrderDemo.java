package records;

import java.time.LocalDateTime;

public class OrderDemo {
    public static void main(String[] args) throws InterruptedException {
        Order o1 = Order.of("Misho");
        Thread.sleep(1000);
        Order o2 = Order.of("Desi");
        Thread.sleep(2000);
        Order o3 = Order.of("Stoyo");

        System.out.println(o1);
        System.out.println(o2);
        System.out.println(o3);
    }
}

