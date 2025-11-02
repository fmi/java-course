package collections.scheduler;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class TaskScheduler {

    public static void main(String[] args) {
        Queue<Task> queue = new PriorityQueue<>(Comparator.reverseOrder());
        queue.add(new Task("Email", 2));
        queue.add(new Task("Backup", 1));
        queue.add(new Task("Report", 3));
        while (!queue.isEmpty()) {
            System.out.println("Next: " + queue.poll());
        }
        // Output: Next: Backup(1), Next: Email(2), Next: Report(3)
    }

}

class Task implements Comparable<Task> {
    String name;
    int priority;

    Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public int compareTo(Task other) {
        return Integer.compare(other.priority, this.priority);
    }

    public String toString() {
        return name + "(" + priority + ")";
    }
}
