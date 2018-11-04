package bg.sofia.uni.fmi.mjt.carstore;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import bg.sofia.uni.fmi.mjt.carstore.car.Car;
import bg.sofia.uni.fmi.mjt.carstore.car.OrdinaryCar;
import bg.sofia.uni.fmi.mjt.carstore.enums.EngineType;
import bg.sofia.uni.fmi.mjt.carstore.enums.Model;
import bg.sofia.uni.fmi.mjt.carstore.enums.Region;

public class SampleCarStoreTest {

	private static final int CAR_NEW_YEAR = 2006;
	private static final int CAR_MID_YEAR = 2003;
	private static final int CAR_OLD_YEAR = 2001;

	private static final int CHEAP_CAR_PRICE = 300;
	private static final int EXPENSIVE_CAR_PRICE = 5000;
	private static final int VERY_EXPENSIVE_CAR_PRICE = 8000;

	private CarStore carStore;

	@Before
	public void setup() {
		carStore = new CarStore();
	}

	@Test
	public void testIfGetCarsByModelReturnsCorrectCars() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_NEW_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_MID_YEAR, CHEAP_CAR_PRICE, EngineType.ELECTRIC, Region.BURGAS);
		Car three = new OrdinaryCar(Model.AUDI, CAR_OLD_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.GASOLINE, Region.BURGAS);

		carStore.add(one);
		carStore.add(two);
		carStore.add(three);

		Collection<Car> cars = carStore.getCarsByModel(Model.AUDI);

		Car[] expected = { three, one };
		Car[] actual = cars.toArray(new Car[cars.size()]);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testGetCarsWithComparatorInDefaultOrder() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_MID_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_NEW_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.ELECTRIC, Region.BURGAS);
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
	public void testFindByRegistrationNumber() {
		Car one = new OrdinaryCar(Model.AUDI, CAR_MID_YEAR, EXPENSIVE_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);
		Car two = new OrdinaryCar(Model.BMW, CAR_NEW_YEAR, VERY_EXPENSIVE_CAR_PRICE, EngineType.ELECTRIC, Region.BURGAS);
		carStore.add(one);
		carStore.add(two);

		assertEquals(one, carStore.getCarByRegistrationNumber(one.getRegistrationNumber()));
	}
}
