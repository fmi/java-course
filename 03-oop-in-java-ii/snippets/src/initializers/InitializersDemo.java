package initializers;

class InitializersDemoBase {
    private int x;
    private static int y;

    { // cannot be static - results in compilation error
        this.x = 1;
        System.out.println("Parent initializer executed");
    }

    static {
        InitializersDemoBase.y = 1;
        System.out.println("parent static initializer executed");
    }

    public InitializersDemoBase() {
        System.out.println("Parent constructor executed");
    }
}

public class InitializersDemo extends InitializersDemoBase {
    private int a;
    private static int b;

    {
        a = 5;
        System.out.println("Child initializer 1 executed");
    }

    static {
        b = 7;
        System.out.println("Child static initializer executed");
    }

    public InitializersDemo() {
        a = 10;
        b = 10;
        System.out.println("Child constructor executed");
    }

    {
        a = 16;
        System.out.println("Child initializer 2 executed");
    }

    public static void main(String[] args) {
        new InitializersDemo();
    }

}
