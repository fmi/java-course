public class Container<T extends Number> {

    public static void main(String[] args) {
        Container<Integer> ci = new Container<>();
        Container<Double> cd = new Container<>();

        // Container<String> cs = new Container<>(); // will not compile

    }

}
