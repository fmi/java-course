package bg.sofia.uni.fmi.mjt.flightscanner.flight;

import bg.sofia.uni.fmi.mjt.flightscanner.airport.Airport;
import bg.sofia.uni.fmi.mjt.flightscanner.exception.FlightCapacityExceededException;
import bg.sofia.uni.fmi.mjt.flightscanner.passenger.Passenger;

import java.util.Collection;

public interface Flight {

    /**
     * Gets the start airport of this flight.
     */
    Airport getFrom();

    /**
     * Gets the destination airport of this flight.
     */
    Airport getTo();

    /**
     * Adds a passenger to this flight.
     *
     * @param passenger the passenger to add.
     * @throws FlightCapacityExceededException, in case the flight is already fully booked (there are no free seats).
     */
    void addPassenger(Passenger passenger) throws FlightCapacityExceededException;

    /**
     * Adds a collection of passengers to this flight.
     *
     * @param passengers the passengers to add.
     * @throws FlightCapacityExceededException, in case the flight cannot accommodate that many passengers
     * (the available free seats are not enough).
     */
    void addPassengers(Collection<Passenger> passengers) throws FlightCapacityExceededException;

    /**
     * Gets all passengers on this flight. If there are no passengers, returns an empty list.
     *
     * @return a collection of all passengers on this flight, as an unmodifiable copy.
     */
    Collection<Passenger> getAllPassengers();

    /**
     * Gets the number of free seats on this flight
     */
    int getFreeSeatsCount();

}
