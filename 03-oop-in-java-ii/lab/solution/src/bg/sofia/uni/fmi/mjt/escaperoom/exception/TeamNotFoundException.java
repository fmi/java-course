package bg.sofia.uni.fmi.mjt.escaperoom.exception;

public class TeamNotFoundException extends Exception {

    public TeamNotFoundException(String message) {
        super(message);
    }

    public TeamNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
