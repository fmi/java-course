package bg.sofia.uni.fmi.mjt.carwash.vehicle;

public record Vehicle(String registrationNumber, VehicleType type) {

    public Vehicle {
        if (registrationNumber == null || registrationNumber.isBlank()) {
            throw new IllegalArgumentException("Registration number must not be null or blank");
        }

        if (type == null) {
            throw new IllegalArgumentException("Vehicle type must not be null");
        }
    }

}
