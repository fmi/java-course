package bg.sofia.uni.fmi.mjt.simcity.exception;

public class BuildableAlreadyExistsException extends RuntimeException {

    public BuildableAlreadyExistsException(String message) {
        super(message);
    }

    public BuildableAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
