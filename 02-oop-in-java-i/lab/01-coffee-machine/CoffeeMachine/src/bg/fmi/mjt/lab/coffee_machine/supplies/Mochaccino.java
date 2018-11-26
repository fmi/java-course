package bg.fmi.mjt.lab.coffee_machine.supplies;

public class Mochaccino implements Beverage {
    private static final double COFFEE = 18;
    private static final double MILK = 150;
    private static final double CHOCOLATE = 10;
    private static final String NAME = "Mochaccino";

    @Override
    public double getMilk() {
        return MILK;
    }

    @Override
    public double getCoffee() {
        return COFFEE;
    }

    @Override
    public double getWater() {
        return 0;
    }

    @Override
    public double getCacao() {
        return CHOCOLATE;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
