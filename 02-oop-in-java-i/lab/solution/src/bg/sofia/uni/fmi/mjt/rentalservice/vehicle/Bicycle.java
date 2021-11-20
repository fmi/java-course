package bg.sofia.uni.fmi.mjt.rentalservice.vehicle;

import bg.sofia.uni.fmi.mjt.rentalservice.location.Location;

public class Bicycle extends AbstractVehicle {
    private static final String TYPE = "BICYCLE";
    private static final double PRICE_PER_MINUTE = 0.2;

    public Bicycle(String id, Location location) {
        super(location, TYPE, id, PRICE_PER_MINUTE);
    }
}
