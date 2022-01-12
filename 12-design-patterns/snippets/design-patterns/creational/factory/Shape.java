package creational.factory;

public interface Shape {

    void draw();

    // use of method to get object of type shape
    static Shape of(ShapeType type) {
        return switch (type) {
            case RECTANGLE -> new Rectangle();
            case SQUARE -> new Square();
            case CIRCLE -> new Circle();
            default -> null;
        };
    }

}
