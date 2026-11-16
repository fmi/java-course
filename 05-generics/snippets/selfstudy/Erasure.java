/*
 * ============================================================================
 *  GENERICS UNDER THE HOOD · ERASURE
 * ============================================================================
 *
 *  Generics are a compile-time feature. At runtime the type parameter T is
 *  "erased" to the ERASURE OF ITS BOUND:
 *      • no bound           ->  Object
 *      • <T extends Number> ->  Number
 *
 *  Compile, then look at the real (erased) method descriptors:
 *      javac Erasure.java
 *      javap -p -s Erasure.class
 *      javap -p -s ErasureBounded.class
 *
 *  Unbounded — T became Object:
 *      T pick(T);
 *        descriptor: (Ljava/lang/Object;)Ljava/lang/Object;
 *
 *  Bounded — T extends Number became Number:
 *      T pick(T);
 *        descriptor: (Ljava/lang/Number;)Ljava/lang/Number;
 *
 *  So "erasure" does NOT always mean "becomes Object" — it means "becomes the
 *  erasure of the bound". This is why a bounded T can call Number's methods.
 * ============================================================================
 */
public class Erasure<T> {
    T pick(T x) { return x; }
}

class ErasureBounded<T extends Number> {
    T pick(T x) { return x; }
}
