package bg.sofia.uni.fmi.mjt.suuper;

class Parent {
    int parentValue;

    Parent(int parentValue) {
        this.parentValue = parentValue;
    }

    void displayParent() {
        System.out.println("Parent value: " + parentValue);
    }
}

class Child extends Parent {
    int childValue;

    Child(int parentValue, int childValue) {
        super(parentValue); // Call the superclass constructor
        this.childValue = childValue;
    }

    void displayChild() {
        System.out.println("Child value: " + childValue);
    }

    void displayParentAndChild() {
        super.displayParent(); // Call the parent class method using super
        displayChild();
    }
}

public class SuperExample {
    public static void main(String[] args) {
        Child child = new Child(10, 20);

        child.displayParentAndChild();
    }
}

