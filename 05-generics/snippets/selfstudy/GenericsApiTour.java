/*
 * LAB 05 · SELF-STUDY — Generics API, a copy-paste reference. (not walked through)
 * Generic class, generic method, bounded & multiple-bounded type params, generic record.
 */
import java.util.List;

public class GenericsApiTour {

    // --- generic class: two independent type params ---
    static class Pair<K, V> {
        private final K key;
        private final V value;
        Pair(K key, V value) { this.key = key; this.value = value; }
        K key()   { return key; }
        V value() { return value; }
        public String toString() { return "(" + key + ", " + value + ")"; }
    }

    // --- generic record: K, V become the components for free ---
    record Entry<K, V>(K key, V value) {}

    // --- bounded type param: T is at least a Number, so Number's methods are callable ---
    static <T extends Number> double sum(List<T> nums) {
        double total = 0;
        for (T n : nums) {
            total += n.doubleValue();   // doubleValue() is available because T extends Number
        }
        return total;
    }

    // --- multiple bounds: T must be BOTH Comparable AND CharSequence (class/interfaces with &) ---
    //     first bound may be a class; the rest are interfaces.
    static <T extends Comparable<T> & CharSequence> T max(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    // --- generic STATIC method: <K, V> declared before the return type ---
    static <K, V> Pair<K, V> pairOf(K k, V v) {
        return new Pair<>(k, v);
    }

    public static void main(String... args) {
        Pair<String, Integer> p = new Pair<>("Stoyo", 1);
        System.out.println(p);                             // (Stoyo, 1)

        Entry<String, Integer> e = new Entry<>("Boyo", 6);
        System.out.println(e.key() + " -> " + e.value());  // Boyo -> 6

        System.out.println(sum(List.of(1, 2, 3)));         // 6.0  (Integer extends Number)
        System.out.println(sum(List.of(1.5, 2.5)));        // 4.0  (Double extends Number)
        // sum(List.of("a", "b"));                         // won't compile: String is not a Number

        System.out.println(max("apple", "pear"));          // pear  (String is Comparable & CharSequence)

        var inferred = pairOf("x", 42);                    // K=String, V=Integer, both inferred
        System.out.println(inferred);                      // (x, 42)
    }
}
