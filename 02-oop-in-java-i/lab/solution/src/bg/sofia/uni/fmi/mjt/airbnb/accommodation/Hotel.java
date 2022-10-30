package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

public class Hotel extends BookableAccommodation {

    private static final String HOTEL = "Hotel";

    private static long id_prefix;

    public Hotel(Location location, double pricePerNight) {
        super(HOTEL, location, pricePerNight);
        ++id_prefix;
    }

    @Override
    protected long getIdSuffix() {
        return id_prefix;
    }

}
