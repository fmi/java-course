package creational.factory;

public interface Shape {

    void draw();

    // use of method to get object of type shape
    static Shape of(ShapeType type) {
        return switch (type) {
            case RECTANGLE -> new Rectangle();
            case SQUARE -> new Square();
            case CIRCLE -> new Circle();
            case null -> null; // Since Java 21, case null can be provided and the selector expression can be null.
                               // Before Java 21, or if a null case is not present, even if a default case is,
                               // Shape.of(null) would throw a NPE.
        };
    }

}
