import java.util.ArrayList;
import java.util.List;

class LivingThing {

}

class Human extends LivingThing {
    private String name;
    public Human(String name) {
        this.name = name;
    }
}

class Student extends Human {
    private int fn;
    public Student(String name, int fn) {
        super(name);
        this.fn = fn;
    }
}

class FMIStudent extends Student {
    public FMIStudent(String name, int fn) {
        super(name, fn);
    }
}

public class GenericsPlayground {

    // The Get & Put principle in action
    private static List<? extends Human> listOfHumans = new ArrayList<>(); // only get is allowed
    private static List<? super Human> listOfSuperHumans = new ArrayList<>(); // only put is allowed

    public static void main(String[] args) {
        for (Human h : listOfHumans) { // we can safely iterate as Human
            System.out.println(h);
        }

        listOfSuperHumans.add(new Student("Georgi Todorov", 62348)); // we can safely put elements
        listOfSuperHumans.add(new Human("Anelia Angelova"));
        listOfSuperHumans.add(new FMIStudent("Zahari Zvezdomirov", 62216));

    }
}
