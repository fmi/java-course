package bg.sofia.uni.fmi.mjt.statics;

class Parent {
    void display() {
        System.out.println("Non-static method in Parent");
    }

    static void staticDisplay() {
        System.out.println("Static method in Parent");
    }
}

class Child extends Parent {
    @Override
    void display() {
        System.out.println("Non-static method in Child");
    }

    static void staticDisplay() {
        System.out.println("Static method in Child (Hidden)");
    }
}

public class MethodHidingExample {
    public static void main(String[] args) {
        Parent parent = new Parent();
        Parent childAsParent = new Child();
        Child child = new Child();

        parent.display(); // Output: Non-static method in Parent
        childAsParent.display(); // Output: Non-static method in Child

        parent.staticDisplay(); // Output: Static method in Parent; same as calling Parent.staticDisplay();
        childAsParent.staticDisplay();  // Output: Static method in Parent, because the reference type is Parent; same as calling Parent.staticDisplay();
        child.staticDisplay(); // Output: Static method in Child (Hidden), because the reference type is Child;same as calling Child.staticDisplay();

        // In summary - static methods belong to the class and no overriding is possible -
        // it's possible to redefine the same method in the child class, but that's not overriding.
    }
}

