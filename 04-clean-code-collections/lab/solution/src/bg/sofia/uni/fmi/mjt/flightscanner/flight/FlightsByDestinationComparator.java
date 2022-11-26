package bg.sofia.uni.fmi.mjt.flightscanner.flight;

import java.util.Comparator;

public class FlightsByDestinationComparator implements Comparator<Flight> {

    @Override
    public int compare(Flight o1, Flight o2) {
        return o1.getTo().id().compareTo(o2.getTo().id());
    }

}
