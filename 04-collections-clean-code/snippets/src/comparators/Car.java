package comparators;

import java.util.Objects;

public class Car implements Comparable<Car> {

    private final String vin; // unique identifier
    private int topSpeed;
    private String color;
    private String brand;

    public Car(String vin, int topSpeed, String color, String model) {
        this.vin = vin;
        this.topSpeed = topSpeed;
        this.color = color;
        this.brand = model;
    }

    public int getTopSpeed() {
        return topSpeed;
    }

    public String getColor() {
        return getColor();
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(vin, car.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(vin);
    }

    @Override
    public int compareTo(Car other) {
        // Let's sort the cars by top speed in ascending order.
        // The method returns a negative integer, zero, or a positive integer when this car's top speed
        // is less than, equal to, or greater than the top speed of the specified object.

        // make sure the natural ordering is consistent with equals()
        if (this.vin.equals(other.vin)) {
            return 0;
        } else if (this.topSpeed == other.topSpeed) {
            return -1; // workaround, but should not return 0 to remain consistent with equals()
        }
        return Integer.compare(this.topSpeed, other.topSpeed);
    }

    @Override
    public String toString() {
        return "Car{" +
            "vin='" + vin + '\'' +
            ", topSpeed=" + topSpeed +
            ", color='" + color + '\'' +
            ", brand='" + brand + '\'' +
            '}';
    }

}
