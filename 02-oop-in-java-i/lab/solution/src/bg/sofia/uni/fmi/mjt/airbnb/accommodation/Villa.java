package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

public class Villa extends BookableAccommodation {

    private static final String VILLA = "Villa";

    private static long id_prefix;

    public Villa(Location location, double pricePerNight) {
        super(VILLA, location, pricePerNight);
        ++id_prefix;
    }

    @Override
    protected long getIdSuffix() {
        return id_prefix;
    }
    
}
