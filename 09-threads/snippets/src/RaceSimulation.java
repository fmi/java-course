class FinishLine {

    // what will happen if this method is not synchronized?
    public synchronized void cross(String runner) {
        String GREEN = "\u001B[32m";
        String RESET = "\u001B[0m";
        System.out.println(GREEN + runner + " has reached the finish line and takes a photo!" + RESET);
        try {
            // time to take a photo ;)
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String RED = "\u001B[31m";
        System.out.println(RED + runner + " crossed the finish line!" + RESET);
    }
}

class Runner extends Thread {
    private final String name;
    private final FinishLine finishLine;

    public Runner(String name, FinishLine finishLine) {
        this.name = name;
        this.finishLine = finishLine;
    }

    public void run() {
        System.out.println(name + " started running!");
        try {
            // random running speed for each competitor
            Thread.sleep((long) (Math.random() * 3000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        midRace();
        finishLine.cross(name);
    }

    // not synchronized
    public void midRace() {
        System.out.println(name + " has completed half of the distance!");
        try {
            // some may run out of energy ;(
            Thread.sleep((long) (Math.random() * 3_000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(name + " stop to drink water!");
    }
}

public class RaceSimulation {
    public static void main(String[] args) {
        FinishLine finishLine = new FinishLine();

        Runner runner1 = new Runner("Victor", finishLine);
        Runner runner2 = new Runner("Ismo", finishLine);
        Runner runner3 = new Runner("100yo", finishLine);

        runner1.start();
        runner2.start();
        runner3.start();

        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String BLUE = "\u001B[34m";
        String RESET = "\u001B[0m";
        System.out.println(BLUE + "The main thread continues with his work while the runners race" + RESET);

        try {
            runner1.join();
            runner2.join();
            runner3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("All runners have finished the race.");
    }
}
