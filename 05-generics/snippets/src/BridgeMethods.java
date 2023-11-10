class Box<T> {
    private T value;

    public void setValue(T value) {
        this.value = value;
    }
}

class BoxOfInt extends Box<Integer> {
    private Integer value;

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
