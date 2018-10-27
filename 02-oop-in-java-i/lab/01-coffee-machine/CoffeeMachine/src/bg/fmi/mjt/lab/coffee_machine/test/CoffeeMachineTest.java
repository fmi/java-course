package bg.fmi.mjt.lab.coffee_machine.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bg.fmi.mjt.lab.coffee_machine.BasicCoffeeMachine;
import bg.fmi.mjt.lab.coffee_machine.CoffeeMachine;
import bg.fmi.mjt.lab.coffee_machine.PremiumCoffeeMachine;
import bg.fmi.mjt.lab.coffee_machine.container.Container;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;
import bg.fmi.mjt.lab.coffee_machine.supplies.Cappuccino;
import bg.fmi.mjt.lab.coffee_machine.supplies.Espresso;
import bg.fmi.mjt.lab.coffee_machine.supplies.Mochaccino;
import bg.fmi.mjt.lab.coffee_machine.Product;

public class CoffeeMachineTest {

    private static final double DELTA = 1e-15;

    private static final double BASIC_CONTAINER_INITIAL_WATER = 600;
    private static final double BASIC_CONTAINER_INITIAL_COFFEE = 600;
    private static final double BASIC_CONTAINER_INITIAL_MILK = 0;
    private static final double BASIC_CONTAINER_INITIAL_CACAO = 0;

    private static final double PREMIUM_CONTAINER_INITIAL_WATER = 1000;
    private static final double PREMIUM_CONTAINER_INITIAL_COFFEE = 1000;
    private static final double PREMIUM_CONTAINER_INITIAL_MILK = 1000;
    private static final double PREMIUM_CONTAINER_INITIAL_CACAO = 300;

    private static final double ESPRESSO_COFFEE = 10;
    private static final double ESPRESSO_WATER = 30;

    private static final double CAPPUCCINO_COFFEE = 18;
    private static final double CAPPUCCINO_MILK = 150;

    private static final double MOCHACCINO_COFFEE = 18;
    private static final double MOCHACCINO_MILK = 150;
    private static final double MOCHACCINO_CACAO = 10;

    private static final List<String> lucks = Arrays.asList("If at first you don't succeed call it version 1.0.",
            "Today you will make magic happen!", "Have you tried turning it off and on again?",
            "Life would be much more easier if you had the source code.");

    private CoffeeMachine basicMachine;
    private CoffeeMachine premiumMachine;

    @Before
    public void setUp() {
        basicMachine = new BasicCoffeeMachine();
        premiumMachine = new PremiumCoffeeMachine();
    }

    @Test
    public void testIfInitialBasicContainerCapacityIsCorrect() {
        Container supplies = basicMachine.getSupplies();

        assertEqualsWithDelta(BASIC_CONTAINER_INITIAL_WATER, supplies.getCurrentWater());
        assertEqualsWithDelta(BASIC_CONTAINER_INITIAL_MILK, supplies.getCurrentMilk());
        assertEqualsWithDelta(BASIC_CONTAINER_INITIAL_COFFEE, supplies.getCurrentCoffee());
        assertEqualsWithDelta(BASIC_CONTAINER_INITIAL_CACAO, supplies.getCurrentCacao());

    }

    @Test
    public void testIfInitialPremiumContainerCapacityIsCorrect() {
        Container supplies = premiumMachine.getSupplies();

        assertEqualsWithDelta(PREMIUM_CONTAINER_INITIAL_WATER, supplies.getCurrentWater());
        assertEqualsWithDelta(PREMIUM_CONTAINER_INITIAL_MILK, supplies.getCurrentMilk());
        assertEqualsWithDelta(PREMIUM_CONTAINER_INITIAL_COFFEE, supplies.getCurrentCoffee());
        assertEqualsWithDelta(PREMIUM_CONTAINER_INITIAL_CACAO, supplies.getCurrentCacao());

    }

    @Test
    public void testIfTheResourcesAreCorrectlyUpdatedAfterMakingAnEspressoOnBasicMachine() {
        basicMachine.brew(new Espresso());
        Container supplies = basicMachine.getSupplies();

        assertEqualsWithDelta(BASIC_CONTAINER_INITIAL_WATER - ESPRESSO_WATER, supplies.getCurrentWater());
        assertEqualsWithDelta(BASIC_CONTAINER_INITIAL_COFFEE - ESPRESSO_COFFEE, supplies.getCurrentCoffee());
    }

    @Test
    public void testIfTheResourcesAreCorrectlyUpdatedAfterMakingAnEspressoOnPremiumMachine() {
        premiumMachine.brew(new Espresso());
        Container supplies = premiumMachine.getSupplies();

        assertEqualsWithDelta(PREMIUM_CONTAINER_INITIAL_WATER - ESPRESSO_WATER, supplies.getCurrentWater());
        assertEqualsWithDelta(PREMIUM_CONTAINER_INITIAL_COFFEE - ESPRESSO_COFFEE, supplies.getCurrentCoffee());
    }

    @Test
    public void testIfTheResourcesAreCorrectlyUpdatedAfterMakingAnCappuccinoOnPremiumMachine() {
        premiumMachine.brew(new Cappuccino());
        Container supplies = premiumMachine.getSupplies();

        assertEqualsWithDelta(PREMIUM_CONTAINER_INITIAL_MILK - CAPPUCCINO_MILK, supplies.getCurrentMilk());
        assertEqualsWithDelta(PREMIUM_CONTAINER_INITIAL_COFFEE - CAPPUCCINO_COFFEE, supplies.getCurrentCoffee());
    }

    @Test
    public void testIfTheResourcesAreCorrectlyUpdatedAfterMakingAnMochaccinoOnPremiumMachine() {
        premiumMachine.brew(new Mochaccino());
        Container supplies = premiumMachine.getSupplies();

        assertEqualsWithDelta(PREMIUM_CONTAINER_INITIAL_MILK - MOCHACCINO_MILK, supplies.getCurrentMilk());
        assertEqualsWithDelta(PREMIUM_CONTAINER_INITIAL_COFFEE - MOCHACCINO_COFFEE, supplies.getCurrentCoffee());
        assertEqualsWithDelta(PREMIUM_CONTAINER_INITIAL_CACAO - MOCHACCINO_CACAO, supplies.getCurrentCacao());
    }

    @Test
    public void testIfBasicMachineCannotMakeCappuccino() {
        assertNull(basicMachine.brew(new Cappuccino()));
    }

    @Test
    public void testIfBasicMachineCannotMakeMochaccino() {
        assertNull(basicMachine.brew(new Mochaccino()));
    }

    @Test
    public void testIfPremiumMachineCanMakeEspresso() {
        assertNotNull(premiumMachine.brew(new Espresso()));
    }

    @Test
    public void testIfPremiumMachineCanMakeCappuccino() {
        assertNotNull(premiumMachine.brew(new Cappuccino()));
    }

    @Test
    public void testIfPremiumMachineCanMakeMochaccino() {
        assertNotNull(premiumMachine.brew(new Mochaccino()));
    }

    @Test
    public void testIfPremiumMachineCannotMakeOtherDrinks() {
        assertNull(premiumMachine.brew(new Latte()));
    }

    @Test
    public void testIfRefillingReturnsTheBasicMachineContainerInItsInitialState() {
        basicMachine.brew(new Espresso());
        basicMachine.refill();

        Container supplies = basicMachine.getSupplies();

        assertEqualsWithDelta(BASIC_CONTAINER_INITIAL_WATER, supplies.getCurrentWater());
        assertEqualsWithDelta(BASIC_CONTAINER_INITIAL_MILK, supplies.getCurrentMilk());
        assertEqualsWithDelta(BASIC_CONTAINER_INITIAL_COFFEE, supplies.getCurrentCoffee());
        assertEqualsWithDelta(BASIC_CONTAINER_INITIAL_CACAO, supplies.getCurrentCacao());

    }

    @Test
    public void testIfRefillingReturnsThePremiumMachineContainerInItsInitialState() {
        premiumMachine.brew(new Espresso());
        premiumMachine.refill();

        Container supplies = premiumMachine.getSupplies();

        assertEqualsWithDelta(PREMIUM_CONTAINER_INITIAL_WATER, supplies.getCurrentWater());
        assertEqualsWithDelta(PREMIUM_CONTAINER_INITIAL_MILK, supplies.getCurrentMilk());
        assertEqualsWithDelta(PREMIUM_CONTAINER_INITIAL_COFFEE, supplies.getCurrentCoffee());
        assertEqualsWithDelta(PREMIUM_CONTAINER_INITIAL_CACAO, supplies.getCurrentCacao());
    }

    @Test
    public void testIfTheBasicCoffeeMachineRunsOutOfResourses() {
        for (int i = 0; i < 20; i++) {
            basicMachine.brew(new Espresso());
        }

        assertNull(basicMachine.brew(new Espresso()));
    }

    @Test
    public void testIfThePremiumCoffeeMachineRunsOutOfResourses() {
        for (int i = 0; i < 33; i++) {
            premiumMachine.brew(new Espresso());
        }

        assertNull(premiumMachine.brew(new Espresso()));
    }

    @Test
    public void testIfBasicCoffeeMachineProducesCorrectProductOnEspressoMade() {
        Product product = basicMachine.brew(new Espresso());
        assertNotNull(product);

        assertEquals("Espresso", product.getName());
        assertEquals(1, product.getQuantity());
        assertNull(product.getLuck());
    }

    @Test
    public void testIfPremiumCoffeeMachineProducesCorrectProductOnEspressoMade() {
        Product product = premiumMachine.brew(new Espresso());
        assertNotNull(product);

        assertEquals("Espresso", product.getName());
        assertEquals(1, product.getQuantity());
        assertTrue(lucks.contains(product.getLuck()));
    }

    @Test
    public void testIfPremiumCoffeeMachineProducesCorrectProductOnCappuccinoMade() {
        Product product = premiumMachine.brew(new Cappuccino());
        assertNotNull(product);

        assertEquals("Cappuccino", product.getName());
        assertEquals(1, product.getQuantity());
        assertTrue(lucks.contains(product.getLuck()));
    }

    @Test
    public void testIfPremiumCoffeeMachineProducesCorrectProductOnMochaccinoMade() {
        Product product = premiumMachine.brew(new Mochaccino());
        assertNotNull(product);

        assertEquals("Mochaccino", product.getName());
        assertEquals(1, product.getQuantity());
        assertTrue(lucks.contains(product.getLuck()));
    }

    @Test
    public void testIfPremiumCoffeeMachineProducesCorrectProductOnMoreThanOneDrink() {
        PremiumCoffeeMachine premiumMachine = new PremiumCoffeeMachine();
        Product product = premiumMachine.brew(new Mochaccino(), 3);
        assertNotNull(product);

        assertEquals("Mochaccino", product.getName());
        assertEquals(3, product.getQuantity());
        assertTrue(lucks.contains(product.getLuck()));
    }

    @Test
    public void testIfPremiumCoffeeBrewReturnsNullOnIncorrectQuantity() {
        PremiumCoffeeMachine premiumMachine = new PremiumCoffeeMachine();
        assertNull(premiumMachine.brew(new Mochaccino(), -1));
        assertNull(premiumMachine.brew(new Mochaccino(), 10));
    }

    @Test
    public void testIfAutoReffilWorksOnPremiumCoffeeMachine() {
        CoffeeMachine premiumMachine = new PremiumCoffeeMachine(true);

        for (int i = 0; i < 33; i++) {
            premiumMachine.brew(new Espresso());
        }

        assertNotNull(premiumMachine.brew(new Espresso()));
    }

    @Test
    public void testIfPremiumMachineGeneratesLuck() {
        CoffeeMachine premiumMachine = new PremiumCoffeeMachine(true);
        String actual = premiumMachine.brew(new Espresso()).getLuck();
        assertEquals("If at first you don't succeed call it version 1.0.", actual);
    }

    @Test
    public void testIfLuckListIsCircular() {
        CoffeeMachine premiumMachine = new PremiumCoffeeMachine(true);

        for (int i = 0; i < lucks.size(); i++) {
            Product product = premiumMachine.brew(new Espresso());
            assertTrue(lucks.contains(product.getLuck()));
        }
        String currentLuck = premiumMachine.brew(new Espresso()).getLuck();
        assertEquals(lucks.get(0), currentLuck);
    }

    private static void assertEqualsWithDelta(double expected, double actual) {
        assertEquals(expected, actual, DELTA);
    }
}

class Latte implements Beverage {

    @Override
    public String getName() {
        return "Latte";
    }

    @Override
    public double getCoffee() {
        return 30;
    }

    @Override
    public double getWater() {
        return 0;
    }

    @Override
    public double getMilk() {
        return 100;
    }

    @Override
    public double getCacao() {
        return 10;
    }
}