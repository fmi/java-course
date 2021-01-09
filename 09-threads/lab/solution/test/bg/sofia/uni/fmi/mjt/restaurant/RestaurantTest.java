package bg.sofia.uni.fmi.mjt.restaurant;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import bg.sofia.uni.fmi.mjt.restaurant.customer.AbstractCustomer;
import bg.sofia.uni.fmi.mjt.restaurant.customer.Customer;
import bg.sofia.uni.fmi.mjt.restaurant.customer.VipCustomer;

@RunWith(Parameterized.class)
public class RestaurantTest {

    private final int chefsCount;
    private final int customersCount;

    public RestaurantTest(int chefsCount, int customersCount) {
        this.chefsCount = chefsCount;
        this.customersCount = customersCount;
    }

    @Parameters(name = "{index}: chefsCount={0} customersCount={1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { { 10, 30 }, { 10, 100 }, { 100, 30 }, { 100, 100 }, { 100, 500 } });
    }

    @Test(timeout = 60000)
    public void testServedOrders() throws InterruptedException {
        int numberOfActiveThreadsBeforeCreatingRestaurant = Thread.activeCount();

        Restaurant restaurant = new MJTDiningPlace(chefsCount);

        int numberOfActiveThreadsAfterCreatingRestaurant = Thread.activeCount();

        AbstractCustomer[] customers = new AbstractCustomer[customersCount];
        for (int i = 0; i < customersCount; i++) {
            if (i % 2 == 0) {
                customers[i] = new Customer(restaurant);
            } else {
                customers[i] = new VipCustomer(restaurant);
            }
            customers[i].start();
        }

        for (AbstractCustomer customer : customers) {
            customer.join();
        }

        restaurant.close();
        for (Chef chef : restaurant.getChefs()) {
            chef.join();
        }

        int actualNumberOfChefs = numberOfActiveThreadsAfterCreatingRestaurant
                - numberOfActiveThreadsBeforeCreatingRestaurant;
        String wrongNumberOfChefsMessage = "Not enough chefs are working in the Restaurant. Expected: " + chefsCount
                + " Actual:" + actualNumberOfChefs;
        assertEquals(wrongNumberOfChefsMessage, chefsCount, actualNumberOfChefs);

        int totalMealsCooked = Arrays.stream(restaurant.getChefs()).mapToInt(Chef::getTotalCookedMeals).sum();
        String wrongNumberOfCookedMealsMessage = "The number of cooked meals is not equal to the number of submitted orders Expected: "
                + restaurant.getOrdersCount() + " Actual: " + totalMealsCooked;
        assertEquals(wrongNumberOfCookedMealsMessage, restaurant.getOrdersCount(), totalMealsCooked);
    }

}
