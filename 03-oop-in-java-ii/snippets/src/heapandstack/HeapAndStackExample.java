package heapandstack;

public class HeapAndStackExample {

    public static void heapExample() {
        // Creating objects on the heap
        Person person1 = new Person("Alice");
        Person person2 = new Person("Bob");

        // Storing references to objects on the stack
        Person[] people = new Person[2];
        people[0] = person1;
        people[1] = person2;

        System.out.println(people[0].name());
        System.out.println(people[1].name());
    }

    public static void stackExample() {
        int x = 10; // x is stored on the stack
        int y = 20; // y is also stored on the stack

        int result = add(x, y); // result is stored on the stack
        System.out.println(result);
    }

    public static int add(int a, int b) {
        return a + b; // The result is stored on the stack
    }

    public static void main(String[] args) {
        stackExample();
        heapExample();
    }
    
}

record Person(String name) {
}
