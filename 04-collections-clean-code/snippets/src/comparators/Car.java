package comparators;

import java.util.Objects;

/**
 * Represents a simple car with basic attributes.
 * Implements Comparable to allow natural ordering by top speed (ascending).
 */
public class Car implements Comparable<Car> {
    private final String vin;
    private final int topSpeed;
    private final String color;
    private final String brand;

    public Car(String vin, int topSpeed, String color, String brand) {
        this.vin = vin;
        this.topSpeed = topSpeed;
        this.color = color;
        this.brand = brand;
    }

    public String getVin() {
        return vin;
    }

    public int getTopSpeed() {
        return topSpeed;
    }

    public String getColor() {
        return color;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public int compareTo(Car other) {
        return Integer.compare(this.topSpeed, other.topSpeed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return Objects.equals(vin, car.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin);
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %dkm/h, %s)", brand, color, topSpeed, vin);
    }

}
