package bg.sofia.uni.fmi.mjt.exchange;

public class UnknownCurrencyException extends Exception {

    public UnknownCurrencyException(String message) {
        super(message);
    }

    public UnknownCurrencyException(String message, Throwable cause) {
        super(message, cause);
    }

}
