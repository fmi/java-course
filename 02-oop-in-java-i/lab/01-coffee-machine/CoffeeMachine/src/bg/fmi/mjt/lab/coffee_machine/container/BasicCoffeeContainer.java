package bg.fmi.mjt.lab.coffee_machine.container;

import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;

public class BasicCoffeeContainer extends Container {
    private final double coffee; // grams
    private final double water; // milliliters

    private double currentCoffee; // grams
    private double currentWater; // milliliters

    public BasicCoffeeContainer(double coffeeBeans, double water) {
        this.coffee = coffeeBeans;
        this.currentCoffee = coffeeBeans;

        this.water = water;
        this.currentWater = water;
    }

    public void refill() {
        currentCoffee = coffee;
        currentWater = water;
    }

    public double getCurrentCoffee() {
        return currentCoffee;
    }

    public double getCurrentWater() {
        return currentWater;
    }

    @Override
    public double getCurrentMilk() {
        return 0;
    }

    @Override
    public double getCurrentCacao() {
        return 0;
    }

    public void updateSupplies(Beverage beverage) {
        currentCoffee = currentCoffee - beverage.getCoffee();
        currentWater = currentWater - beverage.getWater();
    }

}
