package bg.sofia.uni.fmi.mjt.carstore;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import bg.sofia.uni.fmi.mjt.carstore.car.Car;
import bg.sofia.uni.fmi.mjt.carstore.enums.Model;
import bg.sofia.uni.fmi.mjt.carstore.exception.CarNotFoundException;

public class CarStore {

	private Set<Car> cars = new TreeSet<>(new DefaultComparator());
	private int totalPrice = 0;

	/**
	 * Adds the specified car in the store.
	 * 
	 * @return true if the car was added successfully to the store
	 */
	public boolean add(Car car) {
		if (cars.contains(car)) {
			return false;
		}

		totalPrice += car.getPrice();
		return cars.add(car);
	}

	/**
	 * Adds all of the elements of the specified collection in the store.
	 * 
	 * @return true if the store cars are changed after the execution (i.e. at least
	 *         one new car is added to the store)
	 */
	public boolean addAll(Collection<Car> cars) {
		boolean isAddedAtLeastOne = false;
		for (Car current : cars) {
			boolean isAdded = add(current);
			if (isAdded) {
				isAddedAtLeastOne = true;
			}
		}

		return isAddedAtLeastOne;
	}

	/**
	 * Removes the specified car from the store.
	 * 
	 * @return true if the car is successfully removed from the store
	 */
	public boolean remove(Car car) {
		if (!cars.contains(car)) {
			return false;
		}

		totalPrice -= car.getPrice();
		return cars.remove(car);
	}

	/**
	 * Returns all cars of a given model. The cars need to be sorted by year of
	 * manufacture (in ascending order).
	 */
	public Collection<Car> getCarsByModel(Model model) {
		TreeSet<Car> byModel = new TreeSet<>(new DefaultComparator());
		for (Car current : cars) {
			if (model == current.getModel()) {
				byModel.add(current);
			}
		}

		return byModel;
	}

	/**
	 * Finds a car from the store by its registration number.
	 * 
	 * @throws CarNotFoundException
	 *             if a car with this registration number is not found in the store
	 **/
	public Car getCarByRegistrationNumber(String registrationNumber) {
		for (Car current : cars) {
			if (registrationNumber == current.getRegistrationNumber()) {
				return current;
			}
		}

		String message = String.format("Car with registration number %s not found", registrationNumber);
		throw new CarNotFoundException(message);
	}

	/**
	 * Returns all cars sorted by their default order*
	 **/
	public Collection<Car> getCars() {
		return cars;
	}

	/**
	 * Returns all cars sorted according to the order induced by the specified
	 * comparator.
	 */
	public Collection<Car> getCars(Comparator<Car> comparator) {
		TreeSet<Car> byComparator = new TreeSet<>(comparator);
		byComparator.addAll(cars);
		return byComparator;
	}

	/**
	 * Returns all cars sorted according to the given comparator and boolean flag
	 * for order.
	 * 
	 * @param isReversed
	 *            if true the cars should be returned in reversed order
	 */
	public Collection<Car> getCars(Comparator<Car> comparator, boolean isReversed) {
		if (isReversed) {
			return getCars(Collections.reverseOrder(comparator));
		}

		return getCars(comparator);
	}

	/**
	 * Returns the total number of cars in the store.
	 */
	public int getNumberOfCars() {
		return cars.size();
	}

	/**
	 * Returns the total price of all cars in the store.
	 */
	public int getTotalPriceForCars() {
		return totalPrice;
	}
}