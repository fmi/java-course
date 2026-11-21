package bg.sofia.uni.fmi.mjt.carwash.exception;

public class WashUnavailableException extends RuntimeException {

    public WashUnavailableException(String message) {
        super(message);
    }

    public WashUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

}
