package bg.sofia.uni.fmi.mjt.gatherers;

import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

public class GatherersExample {

    // Stream Gatherers is a Java 22 & 23 preview feature
    // They allow writing custom intermediate operations

    public static void main(String[] args) {

        // Fold example: factorial
        // Similar to the terminal "reduce" but returns a stream of its single-element result
        Stream.of(1, 2, 3, 4, 5)                       // ⟵ Source
            .gather(Gatherers.fold(() -> 1, (a, b) -> a * b))  // ⟵ Intermediate operation
            .forEach(System.out::println);                     // ⟵ Terminal operation

        var words = List.of("the", "be", "two", "of", "and", "a", "in", "that");
        System.out.println(words);

        // Fixed window example
        var wordGroupsFixed = words.stream()              // ⟵ Source
            .gather(Gatherers.windowFixed(3))  // ⟵ Intermediate operation
            .toList();                                    // ⟵ Terminal operation
        System.out.println(wordGroupsFixed);

        // Sliding window example
        var wordGroupsSliding = words.stream()              // ⟵ Source
            .gather(Gatherers.windowSliding(3))  // ⟵ Intermediate operation
            .toList();                                      // ⟵ Terminal operation
        System.out.println(wordGroupsSliding);

        // Map concurrent example: leveraging virtual threads
        Stream.of(1, 2, 3, 4, 5)
            .gather(Gatherers.mapConcurrent(4, a -> a * 2))
            .forEach(System.out::println);

    }

}
