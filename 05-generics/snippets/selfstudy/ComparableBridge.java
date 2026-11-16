/*
 * ============================================================================
 *  GENERICS UNDER THE HOOD · BRIDGE METHOD via a GENERIC INTERFACE
 * ============================================================================
 *
 *  This is the bridge you've written a hundred times without noticing.
 *
 *  Comparable<T> declares `int compareTo(T)`. After erasure the JVM needs
 *  `int compareTo(Object)` so polymorphic calls through Comparable dispatch
 *  correctly. You wrote compareTo(ComparableBridge). The compiler SILENTLY
 *  adds compareTo(Object) — a "bridge method".
 *
 *      javap -p -v ComparableBridge.class
 *
 *      compareTo(java.lang.Object) flags:
 *          (0x1041) ACC_PUBLIC, ACC_BRIDGE, ACC_SYNTHETIC
 *                                          (the bridge body = cast + forward)
 *
 *      public int compareTo(java.lang.Object);
 *         2: checkcast     // class ComparableBridge
 *         5: invokevirtual // compareTo:(LComparableBridge;)I
 *         8: ireturn
 *
 *  i.e. the synthetic bridge is literally:
 *      public int compareTo(Object o) { return compareTo((ComparableBridge) o); }
 * ============================================================================
 */
public class ComparableBridge implements Comparable<ComparableBridge> {
    private final int cents;

    public ComparableBridge(int cents) { this.cents = cents; }

    public int compareTo(ComparableBridge other) {
        return Integer.compare(cents, other.cents);
    }
}
