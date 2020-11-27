package bg.sofia.uni.fmi.mjt.io;

import java.io.Serializable;

public class Student implements Serializable {

    private static final long serialVersionUID = -7849460397902343492L;

    private String name;
    private int age;

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

    @Override
    public String toString() {
        return "Student [name=" + name + ", age=" + age + "]";
    }

}
