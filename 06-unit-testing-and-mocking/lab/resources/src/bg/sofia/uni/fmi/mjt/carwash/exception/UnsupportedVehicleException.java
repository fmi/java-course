package bg.sofia.uni.fmi.mjt.carwash.exception;

public class UnsupportedVehicleException extends RuntimeException {

    public UnsupportedVehicleException(String message) {
        super(message);
    }

    public UnsupportedVehicleException(String message, Throwable cause) {
        super(message, cause);
    }

}
