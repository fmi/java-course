package bg.fmi.mjt.lab.coffee_machine.container;

public abstract class Container {

    /**
     * Returns the current quantity of the water in the container
     */
    public abstract double getCurrentWater();

    /**
     * Returns the current quantity of the milk in the container
     */
    public abstract double getCurrentMilk();

    /**
     * Returns the current quantity of the coffee in the container
     */
    public abstract double getCurrentCoffee();

    /**
     * Returns the current quantity of the cacao in the container
     */
    public abstract double getCurrentCacao();
}
