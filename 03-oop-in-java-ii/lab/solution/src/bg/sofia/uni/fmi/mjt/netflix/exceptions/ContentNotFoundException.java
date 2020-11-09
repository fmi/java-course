package bg.sofia.uni.fmi.mjt.netflix.exceptions;

public class ContentNotFoundException extends RuntimeException {
    public ContentNotFoundException(String message) {
        super(message);
    }
}
