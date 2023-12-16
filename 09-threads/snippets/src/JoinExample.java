public class JoinExample {
    public static void main(String[] args) {
        TimerBomb bomb = new TimerBomb();
        bomb.start();

        // Main thread will wait until bomb thread finishes
        try {
            bomb.join();
        } catch (InterruptedException e) {
            // Should not happen
        }

        System.out.println("BOOOOOM!");
    }

}

class TimerBomb extends Thread {
    private String[] count = {"five", "four", "three", "two", "one"};

    @Override
    public void run() {
        for (String c : count) {
            System.out.println(c);
            
            try {
                Thread.sleep(1_500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
