package bg.sofia.uni.fmi.mjt.restaurant.customer;

import bg.sofia.uni.fmi.mjt.restaurant.Restaurant;

public class Customer extends AbstractCustomer {

    public Customer(Restaurant workshop) {
        super(workshop);
    }

    @Override
    public boolean hasVipCard() {
        return false;
    }

}
