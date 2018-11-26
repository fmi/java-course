package bg.fmi.mjt.lab.coffee_machine.supplies;

public class Espresso implements Beverage {
    private static final double COFFEE = 10;
    private static final double WATER = 30;
    private static final String NAME = "Espresso";

    @Override
    public double getCoffee() {
        return COFFEE;
    }

    @Override
    public double getWater() {
        return WATER;
    }

    @Override
    public double getMilk() {
        return 0;
    }

    @Override
    public double getCacao() {
        return 0;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
