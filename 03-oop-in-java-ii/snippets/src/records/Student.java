package records;

public record Student(String name, int facultyNumber) {

    public Student {
        // this is compact constructor: note the absence of () after Student
        if (name.isBlank()) {
            throw new IllegalArgumentException("Student's name is blank");
        }
        if (facultyNumber < 0) {
            throw new IllegalArgumentException("Faculty number is negative");
        }
    }
    // Note that we skip explicit fields assignment, i.e.
    // this.name = name;
    // this.facultyNumber = facultyNumber;
    // These assignments are done by the implicit canonical constructor.
    // The code in the compact constructor is appended by the compiler to the definition of the canonical.

    public static void main(String[] args) {
        Student student = new Student("Anton Krasimirov", 62805);
        System.out.println(student);
        Student anotherStudent = new Student(" ", 42365); // throws exception at runtime
        System.out.println(anotherStudent);
    }

}
