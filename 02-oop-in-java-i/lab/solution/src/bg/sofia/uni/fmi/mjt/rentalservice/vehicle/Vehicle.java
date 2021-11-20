package bg.sofia.uni.fmi.mjt.rentalservice.vehicle;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.mjt.rentalservice.location.Location;

public interface Vehicle {

    /**
     * @return the price per minute for booking this vehicle.
     *         Prices for the different types are:
     *             "CAR" -> 0.50
     *             "SCOOTER" -> 0.30
     *             "BICYCLE" -> 0.20
     */
    double getPricePerMinute();

    /**
     * @return the type of this vehicle. Valid types are: "CAR", "SCOOTER" or
     *         "BICYCLE"
     */
    String getType();

    /**
     * @return the ID of this vehicle. The ID should not be blank or null
     */
    String getId();

    /**
     * @return the location of this vehicle. The location should not be null
     */
    Location getLocation();

    /**
     * @return the end of the reservation period, in case the vehicle is currently
     *         booked, otherwise return current time
     */
    LocalDateTime getEndOfReservationPeriod();

    /**
     * 
     * @param until time until the vehicle is booked
     */
    void setEndOfReservationPeriod(LocalDateTime until);

}
