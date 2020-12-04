package bg.sofia.uni.fmi.mjt.shopping;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
