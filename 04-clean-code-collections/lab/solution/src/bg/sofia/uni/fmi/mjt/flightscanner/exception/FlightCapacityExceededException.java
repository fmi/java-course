package bg.sofia.uni.fmi.mjt.flightscanner.exception;

public class FlightCapacityExceededException extends Exception {

    public FlightCapacityExceededException(String message) {
        super(message);
    }

    public FlightCapacityExceededException(String message, Throwable cause) {
        super(message, cause);
    }

}
