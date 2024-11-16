import java.util.ArrayList;
import java.util.List;

class LivingThing {

}

class Human extends LivingThing {
    private String name;

    public Human(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Student extends Human {
    private int fn;

    public Student(String name, int fn) {
        super(name);
        this.fn = fn;
    }

    @Override
    public String toString() {
        return "Student{" +
                "fn=" + fn +
                '}';
    }
}

class FMIStudent extends Student {
    public FMIStudent(String name, int fn) {
        super(name, fn);
    }

}

public class GenericsPlayground {

    // The Get & Put principle in action

    private static void getHumans(List<? extends Human> listOfHumans) {

        // we can safely get, and we can rely on always getting a Human
        for (Human h : listOfHumans) { // we can safely iterate as Human and call methods of Human
            System.out.println(h.getName());
        }

        // we cannot add any elements to the list, because the compiler cannot verify the type safety.
        // The only exception to this is adding `null`, as `null` is a valid value for any reference type.
        listOfHumans.add(null);

    }

    private static void putHumans(List<? super Human> listOfSuperHumans) {

        // we can safely put instances of Human and successors of Human
        listOfSuperHumans.add(new Student("Georgi Todorov", 62348));
        listOfSuperHumans.add(new Human("Anelia Angelova"));
        listOfSuperHumans.add(new FMIStudent("Zahari Zvezdomirov", 62216));
        // listOfSuperHumans.add(new LivingThing()); // will not compile, why?

        // if we get, we can just rely on getting a java.lang.Object
        Object o = listOfSuperHumans.get(0);
        if (o instanceof Student) {
            System.out.println(((Student) o).getName());
        }
        System.out.println(o);

    }

    private static int neitherGetNorPut(List<?> listOfUnknown) {

        // if we get, we can just rely on getting a java.lang.Object
        Object o = listOfUnknown.get(0);

        // we can add only `null`
        listOfUnknown.add(null);
        // listOfUnknown.add("kuku"); // will not compile

        // we can use only methods that are agnostic to the type of elements
        return listOfUnknown.size();

    }

    public static void main(String[] args) {

        List<FMIStudent> listOfFMIStudents = new ArrayList<>();
        List<Student> listOfStudents = new ArrayList<>();
        List<Human> listOfHumans = new ArrayList<>();
        List<LivingThing> listOfLivingThings = new ArrayList<>();
        List<Object> listOfObjects = new ArrayList<>();

        getHumans(listOfHumans);
        getHumans(listOfStudents);
        getHumans(listOfFMIStudents);

        putHumans(listOfHumans);
        putHumans(listOfLivingThings);
        putHumans(listOfObjects);

        System.out.println(neitherGetNorPut(new ArrayList<>(List.of(1, 2, 3)))); // 4

    }

}
