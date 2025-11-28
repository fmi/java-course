package bg.sofia.uni.fmi.mjt.ints;

import org.w3c.dom.ls.LSOutput;

import java.util.List;
import java.util.Set;

public class IntsSum {

    static void main() {
        Set<Integer> numbers = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        int sumOf5 = numbers
            .stream()
            .limit(5)
            .mapToInt(Integer::intValue)
            .sum();

        System.out.println(sumOf5); // what will be the result?

        // Map-Reduce: Divide and Conquer for Data
        // Map-Reduce is a two-step recipe for processing massive amounts of data:
        //     1. Map phase: Break the problem into smaller independent pieces and process each piece in parallel
        //     2. Reduce phase: Combine all the results into one final answer
        List<Integer> listOfnumbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        int result = listOfnumbers.stream()
            .map(n -> n * 2)              // MAP phase: transform each element
            .reduce(0, Integer::sum);      // REDUCE phase: combine all results

        System.out.println(result);

    }

}
