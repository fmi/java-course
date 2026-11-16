/*
 * LAB 05 · SELF-STUDY — Generic methods & type inference. (not walked through)
 * How the compiler figures out the type argument of a generic method,
 * and how to spell it out explicitly (the "type witness") when it can't.
 */
import java.util.List;

public class GenericMethodsAndInferenceTour {

    // <T> is declared BEFORE the return type → this is a generic method
    static <T> T first(List<T> list) {
        return list.get(0);
    }

    // inference flows through the arguments: T is the common type of a and b
    static <T> T pickAny(T a, T b) {
        return a;
    }

    // bounded generic method
    static <T extends Comparable<T>> T maxOf(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    // a method that returns an empty List<T> — nothing in the args mentions T,
    // so the compiler infers T from the ASSIGNMENT target (target typing)
    static <T> List<T> emptyListOf() {
        return List.of();
    }

    public static void main(String... args) {
        // 1. inference from the argument — no <...> needed
        String s = first(List.of("a", "b", "c"));
        System.out.println(s);                         // a

        // 2. inference finds the nearest common supertype of the two args
        Number n = pickAny(1, 2.0);                    // T inferred as Number (Integer & Double)
        System.out.println(n);                         // 1

        System.out.println(maxOf(3, 7));               // 7
        System.out.println(maxOf("apple", "pear"));    // pear

        // 3. target typing — T comes from the variable's declared type
        List<String> strings = emptyListOf();          // T = String, inferred from the left side
        System.out.println(strings);                   // []

        // 4. explicit TYPE WITNESS — when inference can't decide, spell T out:
        List<Integer> ints = GenericMethodsAndInferenceTour.<Integer>emptyListOf();
        System.out.println(ints);                      // []

        // without a target and without a witness, T would fall back to Object:
        Object o = pickAny("x", 1);                    // T inferred as Object (String & Integer)
        System.out.println(o);                         // x
    }
}
