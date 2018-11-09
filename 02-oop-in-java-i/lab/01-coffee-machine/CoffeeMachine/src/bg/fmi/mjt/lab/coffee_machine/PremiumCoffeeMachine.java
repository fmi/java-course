package bg.fmi.mjt.lab.coffee_machine;

import bg.fmi.mjt.lab.coffee_machine.container.Container;
import bg.fmi.mjt.lab.coffee_machine.container.PremiumCoffeeContainer;
import bg.fmi.mjt.lab.coffee_machine.supplies.Beverage;
import bg.fmi.mjt.lab.coffee_machine.supplies.Cappuccino;
import bg.fmi.mjt.lab.coffee_machine.supplies.Espresso;
import bg.fmi.mjt.lab.coffee_machine.supplies.Mochaccino;
import bg.fmi.mjt.lab.coffee_machine.utils.LuckGenerator;

public class PremiumCoffeeMachine implements CoffeeMachine {

    private static final double COFFEE_AMOUNT = 1000; // grams
    private static final double WATER_AMOUNT = 1000; // milliliters
    private static final double MILK_AMOUNT = 1000; // milliliters
    private static final double CACAO_AMOUNT = 300; // grams

    private PremiumCoffeeContainer supplies;
    private LuckGenerator luckGenerator;

    private boolean autoRefill;

    public PremiumCoffeeMachine() {
        supplies = new PremiumCoffeeContainer(COFFEE_AMOUNT, WATER_AMOUNT, MILK_AMOUNT, CACAO_AMOUNT);
        luckGenerator = new LuckGenerator();
    }

    public PremiumCoffeeMachine(boolean autoRefill) {
        // calling the default constructor of this class:
        this();
        this.autoRefill = autoRefill;
    }

    public Product brew(Beverage beverage) {
        return this.brew(beverage, 1);
    }

    public Product brew(Beverage beverage, int quantity) {
        if (!(beverage instanceof Espresso) && !(beverage instanceof Cappuccino) && !(beverage instanceof Mochaccino)) {
            return null;
        }

        if (quantity <= 0 || quantity > 3) {
            return null;
        }

        if (!hasEnoughSupplies(beverage)) {
            if (!autoRefill) {
                return null;
            }

            supplies.refill();
        }

        supplies.updateSupplies(beverage);

        return new Product(beverage.getClass().getSimpleName(), quantity, luckGenerator.generateLuck());
    }

    private boolean hasEnoughSupplies(Beverage beverage) {
        return supplies.getCurrentCoffee() >= beverage.getCoffee() && supplies.getCurrentWater() >= beverage.getWater()
                && supplies.getCurrentMilk() >= beverage.getMilk() && supplies.getCurrentCacao() >= beverage.getCacao();
    }

    @Override
    public Container getSupplies() {
        // We do not return the reference of supplies,
        // because "something" could modify the object.
        // Returning references could broke the encapsulation.
        return new PremiumCoffeeContainer(supplies.getCurrentCoffee(), supplies.getCurrentWater(),
                supplies.getCurrentMilk(), supplies.getCurrentCacao());
    }

    @Override
    public void refill() {
        supplies.refill();
    }

}
