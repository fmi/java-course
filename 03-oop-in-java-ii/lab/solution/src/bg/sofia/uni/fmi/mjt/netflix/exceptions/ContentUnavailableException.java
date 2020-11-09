package bg.sofia.uni.fmi.mjt.netflix.exceptions;

public class ContentUnavailableException extends Exception {
    public ContentUnavailableException(String message) {
        super(message);
    }

    public ContentUnavailableException(String message, Throwable reason) {
        super(message, reason);
    }
}
