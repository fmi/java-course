package bg.sofia.uni.fmi.mjt.airbnb;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.Bookable;
import bg.sofia.uni.fmi.mjt.airbnb.filter.Criterion;

public class Airbnb implements AirbnbAPI {

    private final Bookable[] accommodations;

    public Airbnb(Bookable[] accommodations) {
        this.accommodations = accommodations;
    }

    @Override
    public Bookable findAccommodationById(String id) {
        if ((id == null) || id.isBlank()) {
            return null;
        }

        for (Bookable accommodation : accommodations) {
            if (accommodation.getId().equalsIgnoreCase(id)) {
                return accommodation;
            }
        }

        return null;
    }

    @Override
    public double estimateTotalRevenue() {
        double estimatedRevenue = 0.0d;

        for (Bookable accommodation : accommodations) {
            estimatedRevenue += accommodation.getTotalPriceOfStay();
        }

        return estimatedRevenue;
    }

    @Override
    public long countBookings() {
        long count = 0L;

        for (Bookable bookable : accommodations) {
            if (bookable.isBooked()) {
                ++count;
            }
        }

        return count;
    }

    @Override
    public Bookable[] filterAccommodations(Criterion... criteria) {
        int countMatching = 0;
        for (int i = 0; i < accommodations.length; i++) {
            if (matchCriteria(accommodations[i], criteria)) {
                countMatching++;
            }
        }

        Bookable[] included = new Bookable[countMatching];
        int resultIndex = 0;
        for (int i = 0; i < accommodations.length; i++) {
            if (matchCriteria(accommodations[i], criteria)) {
                included[resultIndex++] = accommodations[i];
            }
        }

        return included;
    }

    private boolean matchCriteria(Bookable accommodation, Criterion[] criteria) {
        for (Criterion criterion : criteria) {
            if ((criterion != null) && !criterion.check(accommodation)) {
                return false;
            }
        }

        return true;
    }

}
