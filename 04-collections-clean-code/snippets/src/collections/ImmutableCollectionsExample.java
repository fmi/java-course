package collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ImmutableCollectionsExample {

    public static void main(String[] args) {
        // 1. Static factory methods (Java 9+)
        List<String> immutableList = List.of("A", "B", "C");
        Set<String> immutableSet = Set.of("X", "Y", "Z");
        Map<String, Integer> immutableMap = Map.of("A", 1, "B", 2);

        System.out.println("Immutable List: " + immutableList);
        System.out.println("Immutable Set: " + immutableSet);
        System.out.println("Immutable Map: " + immutableMap);

        // 2. Unmodifiable copy (Java 10+)
        List<String> originalList = new ArrayList<>(List.of("one", "two", "three"));
        List<String> unmodifiableCopy = List.copyOf(originalList); // true copy, not affected by changes to original

        System.out.println("\nUnmodifiable Copy: " + unmodifiableCopy);

        originalList.add("four");
        System.out.println("Original List after modification: " + originalList);
        System.out.println("Unmodifiable Copy after original modified: " + unmodifiableCopy); // remains unchanged

        // 3. Unmodifiable view
        List<String> modifiableList = new ArrayList<>(List.of("alpha", "beta"));
        List<String> unmodifiableView =
            Collections.unmodifiableList(modifiableList); // view, reflects changes to original

        System.out.println("\nUnmodifiable View: " + unmodifiableView);

        modifiableList.add("gamma");
        System.out.println("Modifiable List after modification: " + modifiableList);
        System.out.println("Unmodifiable View after original modified: " + unmodifiableView); // reflects change

        // 4. Attempting modification
        try {
            immutableList.add("D"); // throws UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            System.out.println("\nCannot modify immutableList: " + e);
        }

        try {
            unmodifiableCopy.add("five"); // throws UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify unmodifiableCopy: " + e);
        }

        try {
            unmodifiableView.add("delta"); // throws UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify unmodifiableView: " + e);
        }

        // 5. Summary of differences
        System.out.println("\nSummary:");
        System.out.println("- Static factory methods create truly immutable collections.");
        System.out.println("- Unmodifiable copy is a snapshot: changes to the original do NOT affect the copy.");
        System.out.println("- Unmodifiable view is a wrapper: changes to the original ARE reflected in the view.");
    }

}
