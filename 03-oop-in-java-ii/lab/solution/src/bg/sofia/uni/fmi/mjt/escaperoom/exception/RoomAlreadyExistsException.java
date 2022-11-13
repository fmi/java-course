package bg.sofia.uni.fmi.mjt.escaperoom.exception;

public class RoomAlreadyExistsException extends Exception {

    public RoomAlreadyExistsException(String message) {
        super(message);
    }

    public RoomAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
