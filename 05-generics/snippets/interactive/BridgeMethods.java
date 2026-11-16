class Box<T> {
    private T value;

    public void setValue(T value) {
        this.value = value;
    }
}

class BoxOfInt extends Box<Integer> {
    private Integer value;

    @Override
    public void setValue(Integer value) {
        this.value = value;
    }

}

public class BridgeMethods {

    public static void main(String... args) {
        Box<Integer> boxOfInt = new BoxOfInt();
        boxOfInt.setValue(1);
    }

}

/*
    After type parameter erasure, the overridden setValue() method in the superclass and here
    would have different signatures:

    public void setValue(Object value) { this.value = value; }  // in Box
    public void setValue(Integer value) { this.value = value; }  // in BoxOfInt

    To solve this, the compiler generates a synthetic method in BoxOfInt, called a "bridge method":

    public void setValue(Object value) { setValue((Integer) value); }  // in BoxOfInt

    Bridge methods are not visible in the source code but can be seen in the resulting bytecode.
    Have a look disassembling it with javap -c -v BoxOfInt.class
 */
