package bg.sofia.uni.fmi.mjt.restaurant.customer;

import java.util.Random;

import bg.sofia.uni.fmi.mjt.restaurant.Meal;
import bg.sofia.uni.fmi.mjt.restaurant.Order;
import bg.sofia.uni.fmi.mjt.restaurant.Restaurant;

public abstract class AbstractCustomer extends Thread {

    private static final Random RANDOM = new Random();

    private final Restaurant restaurant;

    public AbstractCustomer(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(RANDOM.nextInt(5000));
        } catch (InterruptedException e) {
            System.err.print("Unexpected exception was thrown: " + e.getMessage());
            e.printStackTrace();
        }

        restaurant.submitOrder(new Order(Meal.chooseFromMenu(), this));
    }

    public abstract boolean hasVipCard();

}
