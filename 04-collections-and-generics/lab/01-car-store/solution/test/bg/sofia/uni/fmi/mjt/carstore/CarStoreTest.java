package bg.sofia.uni.fmi.mjt.carstore;

import static bg.sofia.uni.fmi.mjt.carstore.TestConstants.CAR_MID_YEAR;
import static bg.sofia.uni.fmi.mjt.carstore.TestConstants.CAR_NEW_YEAR;
import static bg.sofia.uni.fmi.mjt.carstore.TestConstants.CAR_OLD_YEAR;
import static bg.sofia.uni.fmi.mjt.carstore.TestConstants.CHEAP_CAR_PRICE;
import static bg.sofia.uni.fmi.mjt.carstore.TestConstants.EXPENSIVE_CAR_PRICE;
import static bg.sofia.uni.fmi.mjt.carstore.TestConstants.VERY_EXPENSIVE_CAR_PRICE;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bg.sofia.uni.fmi.mjt.carstore.car.Car;
import bg.sofia.uni.fmi.mjt.carstore.car.OrdinaryCar;
import bg.sofia.uni.fmi.mjt.carstore.enums.EngineType;
import bg.sofia.uni.fmi.mjt.carstore.enums.Model;
import bg.sofia.uni.fmi.mjt.carstore.enums.Region;
import bg.sofia.uni.fmi.mjt.carstore.exception.CarNotFoundException;

public class CarStoreTest {

	private CarStore carStore;

	@Before
	public void setup() {
		carStore = new CarStore();
	}

	@Test
	public void testIfNewCarIsAddedSuccessfullyInTheStore() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_OLD_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);

		assertTrue(carStore.add(one));
		assertEquals(5000, carStore.getTotalPriceForCars());
		assertEquals(1, carStore.getNumberOfCars());
	}

	@Test
	public void testIfTheSameCarFailsToBeAdded() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_OLD_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);

		carStore.add(one);
		assertFalse(carStore.add(one));
	}

	@Test
	public void testIfACollectionOfNewCarsIsAddedSuccessfully() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_OLD_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_MID_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.ELECTRIC,
				Region.BURGAS);

		List<Car> cars = new ArrayList<>();
		cars.add(one);
		cars.add(two);

		assertTrue(carStore.addAll(cars));
		assertEquals(13000, carStore.getTotalPriceForCars());
		assertEquals(2, carStore.getNumberOfCars());
	}

	@Test
	public void testAddAllWithSomeCarsThatAlreadyExistInTheStore() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_OLD_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_MID_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.ELECTRIC,
				Region.BURGAS);

		List<Car> cars = new ArrayList<>();
		cars.add(one);
		cars.add(two);
		carStore.add(two);

		assertTrue(carStore.addAll(cars));

		assertEquals(13000, carStore.getTotalPriceForCars());
		assertEquals(2, carStore.getNumberOfCars());
	}

	@Test
	public void testAddAllWithAllCarsThatAlreadyExistInTheStore() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_OLD_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_MID_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.ELECTRIC,
				Region.BURGAS);

		List<Car> cars = new ArrayList<>();
		cars.add(one);
		cars.add(two);
		carStore.add(two);
		carStore.add(one);

		assertFalse(carStore.addAll(cars));

		assertEquals(13000, carStore.getTotalPriceForCars());
		assertEquals(2, carStore.getNumberOfCars());
	}

	@Test
	public void testIfGetCarsByModelReturnsCorrectCars() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_NEW_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_MID_YEAR, CHEAP_CAR_PRICE, EngineType.ELECTRIC, Region.BURGAS);
		Car three = new OrdinaryCar(Model.AUDI, CAR_OLD_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.GASOLINE,
				Region.BURGAS);

		carStore.add(one);
		carStore.add(two);
		carStore.add(three);

		Collection<Car> cars = carStore.getCarsByModel(Model.AUDI);

		Car[] expected = { three, one };
		Car[] actual = cars.toArray(new Car[cars.size()]);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testIfGetCarsByModelReturnsEmptyCollectionIfNoCarsAreContainedInTheStore() {
		Collection<Car> cars = carStore.getCarsByModel(Model.ALFA_ROMEO);

		assertTrue(cars.isEmpty());
	}

	@Test
	public void testIfGetCarsByModelReturnsEmptyCollectionIfNoCarsOfTheModelAreContainedInTheStore() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_OLD_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_MID_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.ELECTRIC,
				Region.BURGAS);

		carStore.add(one);
		carStore.add(two);

		Collection<Car> cars = carStore.getCarsByModel(Model.ALFA_ROMEO);

		assertTrue(cars.isEmpty());
	}

	@Test
	public void testGetCarsWithoutComparator() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_MID_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_NEW_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.ELECTRIC,
				Region.BURGAS);
		Car three = new OrdinaryCar(Model.AUDI, CAR_OLD_YEAR, CHEAP_CAR_PRICE, EngineType.ELECTRIC, Region.BURGAS);
		carStore.add(one);
		carStore.add(two);
		carStore.add(three);
		Collection<Car> cars = carStore.getCars();

		Car[] expected = { three, one, two };
		Car[] actual = cars.toArray(new Car[cars.size()]);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testGetCarsWithComparator() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_MID_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_NEW_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.ELECTRIC,
				Region.BURGAS);
		Car three = new OrdinaryCar(Model.AUDI, CAR_OLD_YEAR, CHEAP_CAR_PRICE, EngineType.ELECTRIC, Region.BURGAS);
		carStore.add(one);
		carStore.add(two);
		carStore.add(three);
		Collection<Car> cars = carStore.getCars(new CustomComparator());

		Car[] expected = { three, one, two };
		Car[] actual = cars.toArray(new Car[cars.size()]);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testGetCarsWithComparatorInReversedOrder() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_MID_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_NEW_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.ELECTRIC,
				Region.BURGAS);
		Car three = new OrdinaryCar(Model.AUDI, CAR_OLD_YEAR, CHEAP_CAR_PRICE, EngineType.ELECTRIC, Region.BURGAS);
		carStore.add(one);
		carStore.add(two);
		carStore.add(three);
		Collection<Car> cars = carStore.getCars(new CustomComparator(), true);

		Car[] expected = { two, one, three };
		Car[] actual = cars.toArray(new Car[cars.size()]);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testGetCarsWithComparatorInDefaultOrder() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_MID_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_NEW_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.ELECTRIC,
				Region.BURGAS);
		Car three = new OrdinaryCar(Model.AUDI, CAR_OLD_YEAR, CHEAP_CAR_PRICE, EngineType.ELECTRIC, Region.BURGAS);
		carStore.add(one);
		carStore.add(two);
		carStore.add(three);
		Collection<Car> cars = carStore.getCars(new CustomComparator(), false);

		Car[] expected = { three, one, two };
		Car[] actual = cars.toArray(new Car[cars.size()]);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testGetNumberOfCars() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_MID_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_NEW_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.ELECTRIC,
				Region.BURGAS);
		Car three = new OrdinaryCar(Model.AUDI, CAR_OLD_YEAR, CHEAP_CAR_PRICE, EngineType.ELECTRIC, Region.BURGAS);
		carStore.add(one);
		carStore.add(two);
		carStore.add(three);

		assertEquals(3, carStore.getNumberOfCars());
	}

	@Test
	public void testGetTotalPriceOfCars() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_MID_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_NEW_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.ELECTRIC,
				Region.BURGAS);
		Car three = new OrdinaryCar(Model.AUDI, CAR_OLD_YEAR, CHEAP_CAR_PRICE, EngineType.ELECTRIC, Region.BURGAS);
		carStore.add(one);
		carStore.add(two);
		carStore.add(three);

		assertEquals(13300, carStore.getTotalPriceForCars());
	}

	@Test
	public void testIfRemoveFails() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_MID_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_NEW_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.ELECTRIC,
				Region.BURGAS);
		carStore.add(one);

		assertFalse(carStore.remove(two));
	}

	@Test(expected = CarNotFoundException.class)
	public void testRemoveCar() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_MID_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_NEW_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.ELECTRIC,
				Region.BURGAS);
		carStore.add(one);
		carStore.add(two);

		assertTrue(carStore.remove(two));

		assertEquals(1, carStore.getNumberOfCars());
		assertEquals(5000, carStore.getTotalPriceForCars());
		carStore.getCarByRegistrationNumber(two.getRegistrationNumber());
	}

	@Test
	public void testFindByRegistrationNumber() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_MID_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_NEW_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.ELECTRIC,
				Region.BURGAS);
		carStore.add(one);
		carStore.add(two);

		assertEquals(one, carStore.getCarByRegistrationNumber(one.getRegistrationNumber()));
	}

	@Test(expected = CarNotFoundException.class)
	public void testFindByRegistrationNumberWithNonExistingCar() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_MID_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_NEW_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.ELECTRIC,
				Region.BURGAS);
		carStore.add(one);

		carStore.getCarByRegistrationNumber(two.getRegistrationNumber());
	}
}
