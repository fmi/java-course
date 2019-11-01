public class Car implements Comparable<Car> {

	private int topSpeed;
	private String colour;
	private String brand;

	public int getTopSpeed() {
		return topSpeed;
	}

	public String getColour() {
		return getColour();
	}

	public String getBrand() {
		return brand;
	}

	public Car(int topSpeed, String colour, String model) {
		this.topSpeed = topSpeed;
		this.colour = colour;
		this.brand = model;
	}

	@Override
	public int compareTo(Car other) {
		//let's sort the cars based on an topSpeed in ascending order
		//returns a negative integer, zero, or a positive integer as this car topSpeed
		//is less than, equal to, or greater than the specified object.
		//return (other.topSpeed - this.topSpeed);
		return (this.topSpeed - other.topSpeed);
	}

	@Override
	public String toString() {
		return "[topSpeed=" + this.topSpeed + ", colour=" + this.colour + ", model=" + this.brand + "]";
	}

}
