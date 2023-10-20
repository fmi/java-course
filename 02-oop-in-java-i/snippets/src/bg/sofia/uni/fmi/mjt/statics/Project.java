package bg.sofia.uni.fmi.mjt.statics;

public class Project {

    private static final String PROJECT_PREFIX = "proj-"; // constant
    private static int totalProjectInstances;

    private String name;

    public Project(String name) {
        // We can use static variable to count the number of Project instances created.
        // All instances of Project will share the same copy of the variable.
        totalProjectInstances++;

        this.name = name;
    }

    public static int getTotalProjectInstances() {
        return totalProjectInstances;
    }

    // We cannot use instance variables/methods in static methods because
    // static methods are not bound to any instance, but rather to the class itself
    // Uncommenting the method below will not compile:
    //   "non-static variable name cannot be referenced from a static context"

    //public static void printName() {
    //    System.out.println(name);
    //}

}
