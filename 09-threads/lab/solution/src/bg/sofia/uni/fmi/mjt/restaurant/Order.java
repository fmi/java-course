package bg.sofia.uni.fmi.mjt.restaurant;

import bg.sofia.uni.fmi.mjt.restaurant.customer.AbstractCustomer;

public record Order(Meal meal, AbstractCustomer customer) implements Comparable<Order> {

    @Override
    public int compareTo(Order o) {
        boolean currHasVipCard = this.customer.hasVipCard();
        boolean oHasVipCard = o.customer.hasVipCard();

        if (currHasVipCard == oHasVipCard) {
            return Integer.compare(this.meal.getCookingTime(), o.meal.getCookingTime());
        }

        if (currHasVipCard) {
            return 1;
        }

        return -1;
    }

}
