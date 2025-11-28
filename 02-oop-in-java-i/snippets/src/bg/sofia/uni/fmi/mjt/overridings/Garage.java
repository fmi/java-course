package bg.sofia.uni.fmi.mjt.overridings;

public class Garage {

    public Garage() {
        System.out.println("Garage constructed");
    }

    protected Car repair(Car c) {
        return new Car();
    }

    public Car repair(Car c, String customerName) {
        // overloaded method:
        // 1. access modifier may be any
        // 2. return type may be any
        // 3. name should be identical
        // 4. parameter list should be different
        return this.repair(c); // "this." is optional here
    }

}
