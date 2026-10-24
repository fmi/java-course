/**
 * ============================================================================
 *  LAB 02 · INTERACTIVE SNIPPET
 * ============================================================================
 */
public class OverloadVsOverride {

    static String call(long x) {return "long";}

    static String call(Integer x) {return "Integer";}

    static String call(int... x) {return "int...";}

    static String call(Object x) {return "Object";}

    static void threePhaseResolution() {
        int i = 7;
        System.out.println(call(i));
        System.out.println(call(Integer.valueOf(7)));
        System.out.println(call(7L));
        System.out.println(call(7, 8));
        System.out.println(call("seven"));
    }

    static String ambiguous(String s) {return "String";}

    static String ambiguous(Object o) {return "Object";}

    // static String ambiguous(Integer i) { return "Integer"; }

    static void nullDemo() {
        System.out.println(ambiguous(null));
    }

    public static void main(String[] args) {
        threePhaseResolution();
        System.out.println();
        nullDemo();
        System.out.println();
    }
}
