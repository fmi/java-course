package bg.sofia.uni.fmi.mjt.escaperoom.exception;

public class RoomNotFoundException extends Exception {

    public RoomNotFoundException(String message) {
        super(message);
    }

    public RoomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
