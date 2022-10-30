package bg.sofia.uni.fmi.mjt.airbnb;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.Bookable;
import bg.sofia.uni.fmi.mjt.airbnb.filter.Criterion;

public interface AirbnbAPI {

    /**
     * Finds an accommodation by ID.
     *
     * @param id the unique ID of the bookable
     * @return the bookable with the specified id (the IDs are case-insensitive).
     * If the id is null or blank, or no accommodation with the specified id is found, return null.
     */
    Bookable findAccommodationById(String id);

    /**
     * Estimates the total revenue of all booked accommodations.
     *
     * @return the sum of total prices of stay for all booked accommodations.
     */
    double estimateTotalRevenue();

    /**
     * @return the number of booked accommodations.
     */
    long countBookings();

    /**
     * Filters the accommodations by given criteria
     *
     * @param criteria the criteria to apply
     * @return an array of accommodations matching the specified criteria
     */
    Bookable[] filterAccommodations(Criterion... criteria);

}
