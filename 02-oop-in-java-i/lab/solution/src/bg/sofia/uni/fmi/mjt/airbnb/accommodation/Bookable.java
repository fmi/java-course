package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

import java.time.LocalDateTime;

public interface Bookable {

    /**
     * @return the unique ID of the Bookable.
     * It is String with prefix the first three letters (all-caps) of the accommodation type,
     * a dash and a sequential number counting the number of created accommodation instances of the respective type.
     */
    String getId();

    /**
     * @return the location of the Bookable.
     */
    Location getLocation();

    /**
     * Checks if the accommodation is booked.
     *
     * @return true, if the accommodation is booked.
     */
    boolean isBooked();

    /**
     * Books the accommodation for the specified date interval.
     *
     * @param checkIn  check-in date
     * @param checkOut check-out date
     * @return true, if the accommodation is booked successfully.
     * If the accommodation has been previously booked, does nothing and returns false.
     * If checkIn or checkOut is null, or checkIn is in the past, or checkIn is not strictly before checkOut, returns false
     */
    boolean book(LocalDateTime checkIn, LocalDateTime checkOut);

    /**
     * @return If the accommodation is booked, returns the total price of the stay: the number of nights of the booking,
     * multiplied by the price per night. If the accommodation is not booked, returns 0.0.
     */
    double getTotalPriceOfStay();

    /**
     * @return The price per night for this accommodation.
     */
    double getPricePerNight();

}
