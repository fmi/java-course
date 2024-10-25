package argumentpassing;

public class PrimitiveArgumentsExample {
    public static void main(String[] args) {
        int x = 10;
        System.out.println("Before modifyPrimitive: " + x);

        modifyPrimitive(x);

        System.out.println("After modifyPrimitive: " + x);
    }

    public static void modifyPrimitive(int number) {
        number = 20;
        System.out.println("Inside method: " + number);
    }
}
