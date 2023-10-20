package bg.sofia.uni.fmi.mjt.overridings;

public class AudiGarage extends Garage {

    public AudiGarage() {
        // Note that parent default constructor will be invoked prior to this one.
        // Calling it explicitly with super() here will have the same effect
        System.out.println("Audi garage constructed");
    }

    public static void main(String... args) { // varargs syntax
        new AudiGarage();
    }

    @Override
    public Audi repair(Car c) {
        // 1. access modifier upgraded from protected to public. Trying to reduce it to private will not compile
        // 2. covariant return type: overriden method in parent returns Car
        // 3. signature remains identical: otherwise, with the @Override annotation in place, will not compile
        return new Audi();
    }

}
