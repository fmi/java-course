package bg.fmi.mjt.lab.coffee_machine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import bg.fmi.mjt.lab.coffee_machine.BasicCoffeeMachine;
import bg.fmi.mjt.lab.coffee_machine.CoffeeMachine;
import bg.fmi.mjt.lab.coffee_machine.PremiumCoffeeMachine;
import bg.fmi.mjt.lab.coffee_machine.container.Container;
import bg.fmi.mjt.lab.coffee_machine.supplies.Espresso;

public class SampleCoffeeMachineTest {

	private static final double DELTA = 1e-15;

	private static final double BASIC_CONTAINER_INITIAL_WATER = 600;
	private static final double BASIC_CONTAINER_INITIAL_COFFEE = 600;

	private static final double PREMIUM_CONTAINER_INITIAL_WATER = 1000;
	private static final double PREMIUM_CONTAINER_INITIAL_COFFEE = 1000;

	private static final double ESPRESSO_COFFEE = 10;
	private static final double ESPRESSO_WATER = 30;

	private CoffeeMachine basicMachine;
	private CoffeeMachine premiumMachine;

	@Before
	public void setUp() {
		basicMachine = new BasicCoffeeMachine();
		premiumMachine = new PremiumCoffeeMachine();
	}

	@Test
	public void testBasicBrew_UpdatesCorrectlyContainer() {
		basicMachine.brew(new Espresso());
		Container supplies = basicMachine.getSupplies();

		assertEquals(BASIC_CONTAINER_INITIAL_WATER - ESPRESSO_WATER, supplies.getCurrentWater(), DELTA);
		assertEquals(BASIC_CONTAINER_INITIAL_COFFEE - ESPRESSO_COFFEE, supplies.getCurrentCoffee(), DELTA);
	}

	@Test
	public void testPremiumBrew_UpdatesCorrectlyContainer() {
		premiumMachine.brew(new Espresso());
		Container supplies = premiumMachine.getSupplies();

		assertEquals(PREMIUM_CONTAINER_INITIAL_WATER - ESPRESSO_WATER, supplies.getCurrentWater(), DELTA);
		assertEquals(PREMIUM_CONTAINER_INITIAL_COFFEE - ESPRESSO_COFFEE, supplies.getCurrentCoffee(), DELTA);
	}

	@Test
	public void testPremiumBrew_ReturnsFirstLuck() {
		String actual = premiumMachine.brew(new Espresso()).getLuck();
		assertEquals("If at first you don�t succeed call it version 1.0.", actual);
	}

}
