public class Car implements Comparable<Car> {

    private int topSpeed;
    private String color;
    private String brand;

    public int getTopSpeed() {
        return topSpeed;
    }

    public String getColor() {
        return getColor();
    }

    public String getBrand() {
        return brand;
    }

    public Car(int topSpeed, String color, String model) {
        this.topSpeed = topSpeed;
        this.color = color;
        this.brand = model;
    }

    @Override
    public int compareTo(Car other) {
        // Let's sort the cars by top speed in ascending order.
        // The method returns a negative integer, zero, or a positive integer when this car's top speed
        // is less than, equal to, or greater than the top speed of the specified object.
        // return (other.topSpeed - this.topSpeed);
        return (this.topSpeed - other.topSpeed);
    }

    @Override
    public String toString() {
        return "[topSpeed=" + this.topSpeed + ", color=" + this.color + ", model=" + this.brand + "]";
    }

}
