package bg.sofia.uni.fmi.mjt.io;

import java.io.Serial;
import java.io.Serializable;

public class Student implements Serializable {

    // Serialization will not work the same for a record:
    // the serialVersionUID will be ignored.
    // The @Serial annotation is introduced in Java 14.

    @Serial
    private static final long serialVersionUID = -7737678883550377070L;

    String name;
    int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

}
