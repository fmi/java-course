package bg.sofia.uni.fmi.mjt.restaurant.customer;

import bg.sofia.uni.fmi.mjt.restaurant.Restaurant;

public class VipCustomer extends AbstractCustomer {

    public VipCustomer(Restaurant workshop) {
        super(workshop);
    }

    @Override
    public boolean hasVipCard() {
        return true;
    }

}
