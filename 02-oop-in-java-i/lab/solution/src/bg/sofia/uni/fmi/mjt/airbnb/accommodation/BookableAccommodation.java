package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public abstract class BookableAccommodation extends Accommodation implements Bookable {

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    private final double pricePerNight;

    public BookableAccommodation(String type, Location location, double pricePerNight) {
        super(type, location);

        this.pricePerNight = pricePerNight;
    }

    @Override
    public boolean isBooked() {
        return checkIn != null && checkOut != null;
    }

    @Override
    public boolean book(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (isBooked()) {
            return false;
        }

        if (checkIn == null || checkOut == null || checkIn.isBefore(LocalDateTime.now()) ||
            !checkIn.isBefore(checkOut)) {
            return false;
        }

        this.checkIn = checkIn;
        this.checkOut = checkOut;

        return true;
    }

    private long getBookingDays() {
        if (!isBooked()) {
            return 0;
        }

        return checkIn.until(checkOut, ChronoUnit.DAYS);
    }

    @Override
    public double getTotalPriceOfStay() {
        return getBookingDays() * pricePerNight;
    }

    @Override
    public double getPricePerNight() {
        return pricePerNight;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", super.toString(), pricePerNight);
    }

}
