package bg.sofia.uni.fmi.mjt.flightscanner;

import bg.sofia.uni.fmi.mjt.flightscanner.airport.Airport;
import bg.sofia.uni.fmi.mjt.flightscanner.flight.Flight;

import java.util.Collection;
import java.util.List;

public interface FlightScannerAPI {

    /**
     * Adds a flight. If the same flight has already been added, call does nothing.
     *
     * @param flight the flight to add.
     * @throws IllegalArgumentException if flight is null.
     */
    void add(Flight flight);

    /**
     * Adds all flights specified. If any of the flights have already been added, those are ignored.
     *
     * @param flights the flights to be added.
     * @throws IllegalArgumentException if flights is null.
     */
    void addAll(Collection<Flight> flights);

    /**
     * Returns a list of flights from start to destination airports with minimum number of transfers.
     * If there are several such flights with equal minimum number of transfers, returns any of them.
     * If the destination is not reachable with any sequence of flights from the start airport, returns an empty list.
     *
     * @param from the start airport.
     * @param to   the destination airport.
     * @throws IllegalArgumentException if from or to is null, or if from and to are one and the same airport.
     */
    List<Flight> searchFlights(Airport from, Airport to);

    /**
     * Gets an unmodifiable copy of all outbound flights from the specified airport,
     * sorted by number of free seats, in descending order.
     *
     * @param from the airport.
     * @throws IllegalArgumentException if from is null.
     */
    List<Flight> getFlightsSortedByFreeSeats(Airport from);

    /**
     * Gets an unmodifiable copy of all outbound flights from the specified airport,
     * sorted by destination airport ID, in lexicographic order.
     *
     * @param from the airport.
     * @throws IllegalArgumentException if from is null.
     */
    List<Flight> getFlightsSortedByDestination(Airport from);

}
