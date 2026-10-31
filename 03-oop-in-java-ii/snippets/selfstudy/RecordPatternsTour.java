/**
 * ============================================================================
 *  LAB 03 · SELF-STUDY SNIPPET  (NOT walked through in the lab)
 * ============================================================================
 *
 *  What the compiler generates for a record, the compact constructor, nested
 *  record patterns, and pattern matching with `instanceof` and `switch`.
 *  Read top to bottom, run once, keep as a cheat sheet.
 * ============================================================================
 */
import java.util.List;
import java.util.Objects;

public class RecordPatternsTour {

    // The one-liner. Compiler generates: private final fields, canonical
    // constructor, accessors x()/y(), equals(), hashCode(), toString().
    record Point(int x, int y) {}

    // Compact constructor: validation + normalization only.
    // The field assignments are still generated for you.
    record Range(int lo, int hi) {
        Range {                                   // no parameter list
            if (lo > hi) {
                throw new IllegalArgumentException("lo > hi");
            }
        }

        int length() {                            // records may add methods
            return hi - lo;
        }
        
        static Range unit() {                     // ...and static factories
            return new Range(0, 1);
        }
    }

    // Nesting records → a small tree you can destructure in one pattern.
    record Line(Point from, Point to) {}

    static void generatedMembers() {
        Point p = new Point(3, 4);
        Point q = new Point(3, 4);

        int x = p.x();                            // 3  (accessor is x(), not getX())
        boolean eq = p.equals(q);                 // true  (value equality, generated)
        boolean id = (p == q);                    // false (different objects)
        int h = p.hashCode();                     // equal records → equal hashCodes
        String s = p.toString();                  // "Point[x=3, y=4]"

        System.out.println(x + " / equals=" + eq + " / ==? " + id
            + " / hashEq=" + (p.hashCode() == q.hashCode()) + " / " + s);
    }

    static void compactConstructor() {
        Range ok = new Range(2, 9);               // fine
        System.out.println(ok + " length=" + ok.length() + " unit=" + Range.unit());
        try {
            new Range(9, 2);                      // compact ctor rejects it
        } catch (IllegalArgumentException e) {
            System.out.println("rejected: " + e.getMessage());
        }
    }

    // instanceof pattern: test + bind + (optionally) deconstruct in one go.
    static String classify(Object o) {
        if (o instanceof Point(int x, int y)) {   // record deconstruction pattern
            return "point at " + x + "," + y;
        }
        if (o instanceof String str) {            // plain type pattern + bind
            return "text of length " + str.length();
        }
        return "something else";
    }

    // switch with nested record patterns + a guard (`when`).
    static String describe(Line line) {
        return switch (line) {
            case Line(Point(var x1, var y1), Point(var x2, var y2)) when x1 == x2 ->
                "vertical at x=" + x1;
            case Line(Point from, Point to) ->
                "from " + from + " to " + to;
        };
    }

    public static void main(String... args) {
        generatedMembers();
        compactConstructor();

        System.out.println(classify(new Point(1, 2)));
        System.out.println(classify("hello"));
        System.out.println(classify(List.of(1, 2, 3)));

        System.out.println(describe(new Line(new Point(5, 0), new Point(5, 9))));
        System.out.println(describe(new Line(new Point(0, 0), new Point(3, 4))));

        // records are shallowly immutable: to "change" one, build a new one
        Point a = new Point(1, 1);
        Point moved = new Point(a.x() + 10, a.y());   // "with" by hand
        System.out.println(a + " -> " + moved + " / same object? " + (a == moved));
        System.out.println("Objects.equals: " + Objects.equals(a, new Point(1, 1)));
    }
}
