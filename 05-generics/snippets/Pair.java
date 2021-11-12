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

        Pair<List<String>, Set<Integer>> pair4 = new Pair<>(List.of("FMI", "rulez"), Set.of(2020, 2021));
    }
}
