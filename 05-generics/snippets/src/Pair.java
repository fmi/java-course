import java.util.List;
import java.util.Set;

public class Pair<K, V> {

    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Pair{" +
            "key=" + key +
            ", value=" + value +
            '}';
    }

    public static void main(String[] args) {
        Pair<String, Integer> pair1 = new Pair<>("Stoyo", 1);
        Pair<String, Integer> pair2 = new Pair<>("Boyo", 6);

        Pair<Double, Double> pair3 = new Pair<>(1.0, 1.0);

        System.out.println(Util.areEqual(pair1, pair2));
        // the next line will not compile: "reason: Incompatible equality constraint: Double and String"
        //System.out.println(Util.areEqual(pair1, pair3));

        Pair<List<String>, Set<Integer>> pair4 = new Pair<>(List.of("FMI", "rulez"), Set.of(2024, 2025));
    }

}

class Util {
    // Generic static method
    public static <K, V> boolean areEqual(Pair<K, V> p1, Pair<K, V> p2) {
        return p1.getKey().equals(p2.getKey()) &&
            p1.getValue().equals(p2.getValue());
    }
}
