package bg.sofia.uni.fmi.mjt.airbnb.filter;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.Bookable;

public interface Criterion {

    /**
     * @return true, if the bookable matches the criterion
     */
    boolean check(Bookable bookable);

}
