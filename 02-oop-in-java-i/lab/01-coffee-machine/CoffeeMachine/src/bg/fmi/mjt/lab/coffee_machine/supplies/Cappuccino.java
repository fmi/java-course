package bg.fmi.mjt.lab.coffee_machine.supplies;

public class Cappuccino implements Beverage {
    private static final double COFFEE = 18;
    private static final double MILK = 150;
    private static final String NAME = "Cappuccino";

    @Override
    public double getCoffee() {
        return COFFEE;
    }

    @Override
    public double getMilk() {
        return MILK;
    }

    @Override
    public double getWater() {
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
