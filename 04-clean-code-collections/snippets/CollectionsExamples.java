import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CollectionsExamples {

    private static void exploreCollections() {
        // 1. Sorting
        List<Integer> nums = new ArrayList<>();
        nums.add(4);
        nums.add(9);
        nums.add(0);
        nums.add(7);
        nums.add(-1);

        System.out.println(nums); // [4, 9, 0, 7, -1]
        Collections.sort(nums);
        System.out.println(nums); // [-1, 0, 4, 7, 9]
        Collections.sort(nums, Collections.reverseOrder());
        System.out.println(nums); // [9, 7, 4, 0, -1]

        // 2. Searching: indexOf(), binarySearch()
        System.out.println(nums.indexOf(4)); // 2
        Collections.sort(nums);
        int index = Collections.binarySearch(nums, 4);
        System.out.println(index); // 2

        // 3. Shuffling
        Collections.shuffle(nums); // will randomly shuffle list elements

        // 4. copy(), fill(), reverse(), swap()
        List<String> from = new ArrayList<>();
        from.add("foo");
        from.add("bar");
        List<String> to = new LinkedList<>();
        to.add("only");
        to.add("third");
        to.add("remains");

        Collections.copy(to, from);
        System.out.println(to); // [foo, bar, remains]

        Collections.fill(to, "a");
        System.out.println(to); // [a, a, a]

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        Collections.reverse(list);
        System.out.println(list); // [3, 2, 1]

        Collections.swap(list, 0, 1);
        System.out.println(list); // [2, 3, 1]

        // 5. Statistics: min(), max(), frequency()
        List<Integer> numList = List.of(4, 9, 0, 7, -1, 7);

        System.out.println(Collections.min(numList)); // -1
        System.out.println(Collections.max(numList)); // 9
        System.out.println(Collections.frequency(numList, 7)); // 2

        // 6. Collection static factory methods
        List<String> listOfWords = List.of("Java", "13", "rulez");

        Set<Integer> setOfInts = Set.of(1, 2, 3, 5, 8);

        Map<String, Integer> cities = Map.of(
                "Brussels", 1_139_000,
                "Cardiff", 341_000
        );

        cities = Map.ofEntries(
                Map.entry("Brussels", 1_139_000),
                Map.entry("Cardiff", 341_000)
        );
    }

    private static void findDistinctWords(String[] words) {
        Set<String> distinctWords = new TreeSet<>();
        for (String word : words) {
            distinctWords.add(word);
        }

        System.out.printf("%d distinct words: %s%n", distinctWords.size(), distinctWords);
    }

    private static void filterWords(Collection<String> words) {
        for (Iterator<String> it = words.iterator(); it.hasNext();) {
            if (it.next().contains("a")) {
                it.remove();
            }
        }
    }

    public static void main(String[] args) {
        exploreCollections();
        findDistinctWords(new String[]{"car", "cat", "dog", "cat", "dog"}); // 3 distinct words: [car, cat, dog]
        List<String> words = new ArrayList<>(Arrays.asList("car", "cat", "dog"));
        filterWords(words);
        System.out.println(words); // [dog]
    }

}
