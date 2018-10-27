package bg.fmi.mjt.lab.coffee_machine;

import bg.fmi.mjt.lab.coffee_machine.container.BasicCoffeeContainer;
import bg.fmi.mjt.lab.coffee_machine.container.Container;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;
import bg.fmi.mjt.lab.coffee_machine.supplies.Espresso;

public class BasicCoffeeMachine implements CoffeeMachine {

    private static final double COFFEE_AMOUNT = 600; // grams
    private static final double WATER_AMOUNT = 600; // milliliters

    private BasicCoffeeContainer supplies;

    public BasicCoffeeMachine() {
        supplies = new BasicCoffeeContainer(COFFEE_AMOUNT, WATER_AMOUNT);
    }

    @Override
    public Product brew(Beverage beverage) {
        if (!(beverage instanceof Espresso) || !hasEnoughSupplies(beverage)) {
            return null;
        }

        supplies.updateSupplies(beverage);

        return new Product(beverage.getClass().getSimpleName(), 1, null);
    }

    private boolean hasEnoughSupplies(Beverage beverage) {
        return supplies.getCurrentCoffee() >= beverage.getCoffee() && supplies.getCurrentWater() >= beverage.getWater();
    }

    @Override
    public Container getSupplies() {
        return new BasicCoffeeContainer(supplies.getCurrentCoffee(), supplies.getCurrentWater());
    }

    @Override
    public void refill() {
        supplies.refill();
    }
}