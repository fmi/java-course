package bg.sofia.uni.fmi.mjt.flightscanner.flight;

import bg.sofia.uni.fmi.mjt.flightscanner.airport.Airport;
import bg.sofia.uni.fmi.mjt.flightscanner.exception.FlightCapacityExceededException;
import bg.sofia.uni.fmi.mjt.flightscanner.exception.InvalidFlightException;
import bg.sofia.uni.fmi.mjt.flightscanner.passenger.Passenger;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RegularFlight implements Flight {

    private final String flightId;
    private final Airport from;
    private final Airport to;
    private final int totalCapacity;
    private final Set<Passenger> passengerSet;

    private RegularFlight(String flightId, Airport from, Airport to, int totalCapacity) {
        this.flightId = flightId;
        this.from = from;
        this.to = to;
        this.totalCapacity = totalCapacity;
        this.passengerSet = HashSet.newHashSet(totalCapacity); //new HashSet<>();
    }

    public static RegularFlight of(String flightId, Airport from, Airport to, int totalCapacity) {
        if (flightId == null || flightId.isEmpty() || flightId.isBlank() || from == null || to == null ||
            totalCapacity < 0) {
            throw new IllegalArgumentException();
        }

        if (from.equals(to)) {
            throw new InvalidFlightException("from and to are the same");
        }

        return new RegularFlight(flightId, from, to, totalCapacity);
    }

    public Airport getFrom() {
        return from;
    }

    public Airport getTo() {
        return to;
    }

    @Override
    public void addPassenger(Passenger passenger) throws FlightCapacityExceededException {
        if (passenger == null) {
            throw new IllegalArgumentException();
        }

        if (passengerSet.size() == totalCapacity) {
            throw new FlightCapacityExceededException(String.valueOf(totalCapacity));
        }

        passengerSet.add(passenger);
    }

    @Override
    public void addPassengers(Collection<Passenger> passengers) throws FlightCapacityExceededException {
        if (passengerSet.size() + passengers.size() > totalCapacity) {
            throw new FlightCapacityExceededException(String.valueOf(totalCapacity));
        }

        this.passengerSet.addAll(passengers);
    }

    @Override
    public Collection<Passenger> getAllPassengers() {
        return Set.copyOf(passengerSet);
    }

    @Override
    public int getFreeSeatsCount() {
        return totalCapacity - passengerSet.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegularFlight flight = (RegularFlight) o;
        return Objects.equals(flightId, flight.flightId);
    }

    @Override
    public String toString() {
        return flightId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightId);
    }

}
