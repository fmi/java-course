package exceptions;

class Human {
    private String name;

    public Human (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Student extends Human {
    int facultyNumber;

    public Student(String name, int facultyNumber) {
        super(name);
        this.facultyNumber = facultyNumber;
    }

}

public class SmartNPEExample {

    public static void main(String[] args) {
        Student student1 = new Student("Pancho", 62438);
        Student student2 = new Student(null, 45689);

        System.out.println(student1.getName().startsWith("P"));
        System.out.println(student2.getName().startsWith("P"));
    }

}
