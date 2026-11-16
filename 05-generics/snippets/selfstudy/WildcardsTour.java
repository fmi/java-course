/*
 * LAB 05 · SELF-STUDY — Wildcards and The Get & Put Principle (PECS). (not walked through)
 *   ? extends T  — PRODUCER: safe to GET (as T), cannot put (except null)
 *   ? super   T  — CONSUMER: safe to PUT (T and subtypes), get yields Object
 *   ?            — unbounded: neither get-as-T nor put (except null)
 * Mnemonic: PECS — Producer Extends, Consumer Super.
 */
import java.util.ArrayList;
import java.util.List;

public class WildcardsTour {

    static class Fruit {
        public String toString() {
            return "Fruit";
        }
    }

    static class Apple extends Fruit {
        public String toString() {
            return "Apple";
        }
    }

    static class Banana extends Fruit {
        public String toString() {
            return "Banana";
        }
    }

    // PRODUCER — read fruits out. Accepts List<Fruit>, List<Apple>, List<Banana>, ...
    static void eatAll(List<? extends Fruit> producer) {
        for (Fruit f : producer) {       // GET is safe: every element IS-A Fruit
            System.out.println("eating " + f);
        }
        // producer.add(new Apple());    // won't compile: could be a List<Banana>
        producer.add(null);              // the only legal add: null fits any reference type
    }

    // CONSUMER — write fruits in. Accepts List<Fruit>, List<Object>, ...
    static void fillWithApples(List<? super Apple> consumer) {
        consumer.add(new Apple());       // PUT is safe: an Apple fits any List<? super Apple>
        // consumer.add(new Fruit());    // won't compile: list might be List<Apple>
        Object o = consumer.get(0);      // GET yields only Object — we don't know the exact type
        System.out.println("got (as Object): " + o);
    }

    // UNBOUNDED — type-agnostic use only (size, clear, iterate as Object)
    static int count(List<?> any) {
        // any.add(new Apple());         // won't compile
        any.add(null);                   // only null
        return any.size();
    }

    public static void main(String... args) {
        List<Apple> apples = new ArrayList<>(List.of(new Apple(), new Apple()));
        eatAll(apples);                  // List<Apple> is a ? extends Fruit

        List<Fruit> basket = new ArrayList<>();
        fillWithApples(basket);          // List<Fruit> is a ? super Apple
        System.out.println(basket);      // [Apple]

        System.out.println(count(new ArrayList<>(List.of(1, 2, 3))));  // 4 (added a null)
    }
}
