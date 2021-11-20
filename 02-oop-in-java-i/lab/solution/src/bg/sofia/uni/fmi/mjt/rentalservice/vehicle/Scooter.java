package bg.sofia.uni.fmi.mjt.rentalservice.vehicle;

import bg.sofia.uni.fmi.mjt.rentalservice.location.Location;

public class Scooter extends AbstractVehicle {
    private static final String TYPE = "SCOOTER";
    private static final double PRICE_PER_MINUTE = 0.3;

    public Scooter(String id, Location location) {
        super(location, TYPE, id, PRICE_PER_MINUTE);
    }
}
