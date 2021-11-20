package bg.sofia.uni.fmi.mjt.rentalservice.service;

import java.time.Duration;
import java.time.LocalDateTime;

import bg.sofia.uni.fmi.mjt.rentalservice.location.Location;
import bg.sofia.uni.fmi.mjt.rentalservice.vehicle.Vehicle;

public class RentalService implements RentalServiceAPI {
    private final Vehicle[] vehicles;

    public RentalService(Vehicle[] vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public double rentUntil(Vehicle vehicle, LocalDateTime until) {
        if (!exists(vehicle)) {
            return -1.0;
        }

        if (until.isBefore(LocalDateTime.now()) || //
                vehicle.getEndOfReservationPeriod().isAfter(LocalDateTime.now())) {
            return -1.0;
        }

        long minutes = getTripMinutes(until);

        vehicle.setEndOfReservationPeriod(until);
        return minutes * vehicle.getPricePerMinute();
    }

    private boolean exists(Vehicle vehicle) {
        for (Vehicle v : vehicles) {
            if (v.equals(vehicle)) {
                return true;
            }
        }

        return false;
    }

    private long getTripMinutes(LocalDateTime until) {
        Duration duration = Duration.between(LocalDateTime.now(), until);
        long minutes = duration.toMinutes();

        return duration.toSecondsPart() > 0 ? minutes + 1 : minutes;
    }

    @Override
    public Vehicle findNearestAvailableVehicleInRadius(String type, Location location, double maxDistance) {
        if (maxDistance < 0 || !isValidType(type)) {
            return null;
        }

        int idx = -1;
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < vehicles.length; i++) {
            if (vehicles[i].getType() != type || vehicles[i].getEndOfReservationPeriod().isAfter(LocalDateTime.now())) {
                continue;
            }

            double distance = getDistance(location, vehicles[i].getLocation());
            if (distance < minDistance) {
                minDistance = distance;
                idx = i;
            }
        }

        if (minDistance <= maxDistance && idx != -1) {
            return vehicles[idx];
        }

        return null;
    }

    private boolean isValidType(String type) {
        return "CAR".equals(type) || "SCOOTER".equals(type) || "BICYCLE".equals(type);
    }

    private double getDistance(Location location1, Location location2) {
        return Math.sqrt(
                Math.pow(location1.getX() - location2.getX(), 2) + Math.pow(location1.getY() - location2.getY(), 2));
    }

}
