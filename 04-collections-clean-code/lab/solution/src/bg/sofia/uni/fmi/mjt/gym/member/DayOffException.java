package bg.sofia.uni.fmi.mjt.gym.member;

public class DayOffException extends RuntimeException {
    public DayOffException(String message) {
        super(message);
    }

    public DayOffException(String message, Throwable cause) {
        super(message, cause);
    }
}
