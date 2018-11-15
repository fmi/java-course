package bg.sofia.uni.fmi.mjt.carstore.car;

import bg.sofia.uni.fmi.mjt.carstore.enums.EngineType;
import bg.sofia.uni.fmi.mjt.carstore.enums.Model;
import bg.sofia.uni.fmi.mjt.carstore.enums.Region;

public abstract class Car {

	private Model model;
	private int year;
	private int price;
	private EngineType engineType;
	private String registrationNumber;

	protected Car(Model model, int year, int price, EngineType engineType, Region region) {
		this.model = model;
		this.year = year;
		this.price = price;
		this.engineType = engineType;
		this.registrationNumber = region.getRegistrationNumber();
	}

	/**
	 * Returns the model of the car.
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * Returns the year of manufacture of the car.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Returns the price of the car.
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Returns the engine type of the car.
	 */
	public EngineType getEngineType() {
		return engineType;
	}

	/**
	 * Returns the unique registration number of the car.
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((registrationNumber == null) ? 0 : registrationNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Car other = (Car) obj;
		return registrationNumber.equals(other.registrationNumber);
	}
}
