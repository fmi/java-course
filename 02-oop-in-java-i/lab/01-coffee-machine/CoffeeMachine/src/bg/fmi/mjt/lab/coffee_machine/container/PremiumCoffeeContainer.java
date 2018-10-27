package bg.fmi.mjt.lab.coffee_machine.container;

import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;

public class PremiumCoffeeContainer extends BasicCoffeeContainer {

    private final double milk; // milliliters
    private final double cacao; // grams

    private double currentMilk; // milliliters
    private double currentCacao; // grams

    public PremiumCoffeeContainer(double coffee, double water, double milk, double cacao) {
        // calling the parent constructor:
        super(coffee, water);

        this.milk = milk;
        this.currentMilk = milk;

        this.cacao = cacao;
        this.currentCacao = cacao;
    }

    @Override
    public void refill() {
        super.refill();

        this.currentMilk = milk;
        this.currentCacao = cacao;
    }

    @Override
    public double getCurrentMilk() {
        return currentMilk;
    }

    @Override
    public double getCurrentCacao() {
        return currentCacao;
    }

    @Override
    public void updateSupplies(Beverage beverage) {
        super.updateSupplies(beverage);

        this.currentCacao = currentCacao - beverage.getCacao();
        this.currentMilk = currentMilk - beverage.getMilk();
    }
}
