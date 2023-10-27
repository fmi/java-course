package initializers;

class InitializersDemoBase {
    private static int y;

    static {
        InitializersDemoBase.y = 1;
        System.out.println("parent static initializer executed");
    }

    private int x;

    { // cannot be static - results in compilation error
        this.x = 1;
        System.out.println("Parent initializer executed");
    }

    public InitializersDemoBase() {
        System.out.println("Parent constructor executed");
    }
}

public class InitializersDemo extends InitializersDemoBase {
    private static int b;

    static {
        b = 7;
        System.out.println("Child static initializer executed");
    }

    private int a;

    {
        a = 5;
        System.out.println("Child initializer 1 executed");
    }

    {
        a = 16;
        System.out.println("Child initializer 2 executed");
    }

    public InitializersDemo() {
        a = 10;
        b = 10;
        System.out.println("Child constructor executed");
    }

    public static void main(String[] args) {
        new InitializersDemo();
    }

}
