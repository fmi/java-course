package bg.sofia.uni.fmi.mjt.rentalservice.service;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.mjt.rentalservice.location.Location;
import bg.sofia.uni.fmi.mjt.rentalservice.vehicle.Vehicle;

public interface RentalServiceAPI {

    /**
     * Rents an available vehicle until a certain point in time
     * 
     * @param vehicle a valid vehicle registered with the RentalService
     * @param until point in time until which the vehicle will be rented.
     *              The vehicle will be available for next booking after @until
     * @return the price of the trip (starting now).
     *         If the vehicle does not exist or is already booked, return -1.0
     */
    double rentUntil(Vehicle vehicle, LocalDateTime until);

    /**
     * Returns the nearest available vehicle of the specified type within a certain radius
     * of the given location
     * 
     * @param type the type of the vehicle
     * @param location current location
     * @param maxDistance non-negative maximum distance to the vehicle
     * @return the nearest available vehicle of the specified @type within @maxDistance, null otherwise.
     *         If there are two or more equidistant nearest vehicles, return any of them
     */
    public Vehicle findNearestAvailableVehicleInRadius(String type, Location location, double maxDistance);

}
