package bg.sofia.uni.fmi.mjt.instanceoff;

class Shape {
    void draw() {
        System.out.println("Drawing a shape");
    }
}

class Circle extends Shape {
    @Override
    void draw() {
        System.out.println("Drawing a circle");
    }
}

class Rectangle extends Shape {
    @Override
    void draw() {
        System.out.println("Drawing a rectangle");
    }
}

public class InstanceOfExample {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[]{
                new Circle(),
                new Rectangle(),
                new Circle(),
                new Rectangle(),
        };

        // old-school way:
        for (Shape shape : shapes) {
            if (shape instanceof Circle) {
                System.out.println("Circle detected:");
                Circle circle = (Circle) shape; // Cast to Circle
                circle.draw();
            } else if (shape instanceof Rectangle) {
                System.out.println("Rectangle detected:");
                Rectangle rectangle = (Rectangle) shape; // Cast to Rectangle
                rectangle.draw();
            } else {
                System.out.println("Unknown shape detected.");
                shape.draw();
            }
        }

        // equivalent with enhanced switch
        for (Shape shape : shapes) {
            switch (shape) {
                case Circle c -> {
                    {
                        c.draw();
                    }
                }
                case Rectangle r -> {
                    {
                        r.draw();
                    }
                }
                default -> System.out.println("Unexpected value: " + shape);
            }
        }
    }
}
