package argumentpassing;

public class ReferencesExample {
    public static void main(String[] args) {
        Person person = new Person("Alice");

        System.out.println("Before modifyReference: " + person.getName());
        modifyReference(person);
        System.out.println("After modifyReference: " + person.getName());

        System.out.println("Before modifyObject: " + person.getName());
        modifyObject(person);
        System.out.println("After modifyObject: " + person.getName());
    }

    public static void modifyObject(Person p) {
        p.setName("Bob");
        System.out.println("Inside modifyObject: " + p.getName());
    }

    public static void modifyReference(Person p) {
        p = new Person("Mary");
        System.out.println("Inside modifyReference: " + p.getName());
    }
}

class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
