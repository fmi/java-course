package bg.sofia.uni.fmi.mjt.carstore;

import static bg.sofia.uni.fmi.mjt.carstore.TestConstants.CAR_OLD_YEAR;
import static bg.sofia.uni.fmi.mjt.carstore.TestConstants.CHEAP_CAR_PRICE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import bg.sofia.uni.fmi.mjt.carstore.car.Car;
import bg.sofia.uni.fmi.mjt.carstore.car.SportsCar;
import bg.sofia.uni.fmi.mjt.carstore.enums.EngineType;
import bg.sofia.uni.fmi.mjt.carstore.enums.Model;
import bg.sofia.uni.fmi.mjt.carstore.enums.Region;

public class CarTest {

	private static final int REGISTRATION_NUMBER_STARTING_ID = 1000;
	private static final int REGISTRATION_NUMBER_CORRECT_SIZE = 8;

	@Test
	public void testCarRegistrationNumberWithValidPrefix() {
		Car one = new SportsCar(Model.AUDI, CAR_OLD_YEAR, CHEAP_CAR_PRICE, EngineType.DIESEL, Region.SOFIA);
		Car two = new SportsCar(Model.AUDI, CAR_OLD_YEAR, CHEAP_CAR_PRICE, EngineType.DIESEL, Region.SOFIA);
		Car three = new SportsCar(Model.AUDI, CAR_OLD_YEAR, CHEAP_CAR_PRICE, EngineType.DIESEL, Region.PLOVDIV);

		assertTrue(one.getRegistrationNumber().length() == REGISTRATION_NUMBER_CORRECT_SIZE);
		assertTrue(one.getRegistrationNumber()
				.startsWith(Region.SOFIA.getPrefix() + String.valueOf(REGISTRATION_NUMBER_STARTING_ID)));
		assertTrue(two.getRegistrationNumber().length() == REGISTRATION_NUMBER_CORRECT_SIZE);
		assertTrue(two.getRegistrationNumber()
				.startsWith(Region.SOFIA.getPrefix() + String.valueOf(REGISTRATION_NUMBER_STARTING_ID + 1)));
		assertTrue(three.getRegistrationNumber().length() == REGISTRATION_NUMBER_CORRECT_SIZE);
		assertTrue(three.getRegistrationNumber()
				.startsWith(Region.PLOVDIV.getPrefix() + String.valueOf(REGISTRATION_NUMBER_STARTING_ID)));
	}

	@Test
	public void testGetters() {
		Car one = new SportsCar(Model.AUDI, CAR_OLD_YEAR, CHEAP_CAR_PRICE, EngineType.DIESEL, Region.BURGAS);

		assertEquals(Model.AUDI, one.getModel());
		assertEquals(CAR_OLD_YEAR, one.getYear());
		assertEquals(CHEAP_CAR_PRICE, one.getPrice());
		assertEquals(EngineType.DIESEL, one.getEngineType());
	}
}
