/*
 * Structured Concurrency Example in Java 25
 *
 * This example demonstrates Java's StructuredTaskScope, a preview feature in Java 25.
 * Structured concurrency allows you to fork multiple tasks within a scope, wait for all
 * of them to complete, and handle exceptions in a structured way.
 *
 * Key points:
 * 1. Tasks are forked concurrently using scope.fork().
 * 2. scope.join() waits for all tasks to finish and throws FailedException if any task fails.
 * 3. Results are retrieved using subtask.get() after join().
 * 4. Any running tasks are automatically cancelled if one fails.
 *
 * This makes concurrent code easier to read, safer, and less error-prone than manual thread management.
 */

void main() {
    // Open a structured task scope
    try (var scope = StructuredTaskScope.<String>open()) {

        // Fork tasks concurrently
        var task1 = scope.fork(() -> work("Task 1", 1000));
        var task2 = scope.fork(() -> work("Task 2 (fails)", 500));
        var task3 = scope.fork(() -> work("Task 3", 800));

        // Wait for all tasks to complete; throws FailedException if any task failed
        scope.join();

        // Retrieve results using get() after join() succeeds
        IO.println(task1.get());
        IO.println(task2.get());
        IO.println(task3.get());

    } catch (StructuredTaskScope.FailedException e) {
        IO.println("One or more tasks failed: " + e.getCause());
    } catch (InterruptedException e) {
        IO.println("Interrupted while waiting for tasks.");
    }
}

private static String work(String name, int delayMillis) throws Exception {
    Thread.sleep(delayMillis);
    if (name.contains("fails")) {
        throw new RuntimeException(name + " encountered an error!");
    }
    return name + " completed successfully";
}
