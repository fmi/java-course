interface Shape {
    double area();
}

class Rectangle implements Shape, Comparable<Shape> {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double area() {
        return length * width;
    }

    @Override
    public int compareTo(Shape other) {
        return Double.compare(this.area(), other.area());
    }

    @Override
    public String toString() {
        return "Rectangle[Area=" + area() + "]";
    }
}

class Square extends Rectangle {

    public Square(double side) {
        super(side, side);
    }

    @Override
    public String toString() {
        return "Square[Area=" + area() + "]";
    }
}

// A utility class that will use lower-bounded wildcard generics
class ShapeUtils {

    // This method leverages generics and bounded wildcards to handle a flexible range of types
    // while maintaining type safety.
    public static <T extends Shape> T findLargestShape(T[] shapes) {
        T largest = shapes[0];
        for (T shape : shapes) {
            // The expression `Comparable<? super T>` is used to cast `shape` to ensure compatibility
            // with the `compareTo` method. The lower bound (`super`) is necessary for handling the case where `T`
            // could be a subtype of `Rectangle`, allowing comparison across derived classes like `Square`.
            if (((Comparable<? super T>) shape).compareTo(largest) > 0) {
                largest = shape;
            }
        }
        return largest;
    }

    // Note that if we change the generic type of the method above to <T extends Shape & Comparable<T>>,
    // the `shapes` array can only contain elements of the same type `T`, because `Comparable<T>` restricts `T`
    // to be compared only with objects of the same type.
    // As a result, it would not be possible to use this method with an array of mixed types like
    // `[Rectangle, Rectangle, Square, Square]`, reducing its flexibility.
}

// Main class to test the implementation
public class LowerBoundedWildcardGenerics {
    public static void main(String[] args) {
        Rectangle rect1 = new Rectangle(4, 5);
        Rectangle rect2 = new Rectangle(2, 3);
        Square square1 = new Square(3);
        Square square2 = new Square(4);

        Shape[] shapes = {rect1, rect2, square1, square2};
        Rectangle[] rectangles = {rect1, rect2, square1, square2};
        Square[] squares = {square1, square2};

        // Find the largest shape
        Shape largestShape = ShapeUtils.findLargestShape(shapes);
        //Shape largestRectangle = ShapeUtils.findLargestShape(rectangles);
        //Shape largestSquare = ShapeUtils.findLargestShape(squares);

        System.out.println("The largest shape is: " + largestShape);
    }
}
