package bg.sofia.uni.fmi.mjt;

import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

public class MethRefExample {

    public MethRefExample() {
        System.out.println("No arg constructor invoked");
    }

    public MethRefExample(String s) {
        System.out.println("String arg constructor invoked with param " + s);
    }

    public static void fun() {
        System.out.println("fun() invoked");
    }

    public static void fun(int i) {
        System.out.println("fun(int) invoked with argument " + i);
    }

    public static void main(String[] args) {

        // method reference to overloaded constructor
        Function<String, MethRefExample> stringConstructorRef1 = MethRefExample::new;
        Function<String, MethRefExample> stringConstructorRef2 = s -> new MethRefExample(s);
        MethRefExample example1 = stringConstructorRef1.apply("FMI");
        MethRefExample example2 = stringConstructorRef1.apply("Rulez");

        Supplier<MethRefExample> defaultConstructorRef = MethRefExample::new;
        MethRefExample example3 = defaultConstructorRef.get();

        // method reference to overloaded methods
        IntConsumer methRef1 = MethRefExample::fun;
        methRef1.accept(5);

        NoArgNoReturn methRef2 = MethRefExample::fun;
        methRef2.accept();
    }

    interface NoArgNoReturn {
        void accept();
    }
}
