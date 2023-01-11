package creational.factory;

import static creational.factory.ShapeType.*;

public class FactoryPatternDemo {

    public static void main(String[] args) {

        // get an object of Circle and call its draw method.
        Shape shape1 = Shape.of(CIRCLE);

        // call draw method of Circle
        shape1.draw();

        // get an object of Rectangle and call its draw method.
        Shape shape2 = Shape.of(RECTANGLE);

        // call draw method of Rectangle
        shape2.draw();

        // get an object of Square and call its draw method.
        Shape shape3 = Shape.of(SQUARE);

        // call draw method of square
        shape3.draw();
    }
}
