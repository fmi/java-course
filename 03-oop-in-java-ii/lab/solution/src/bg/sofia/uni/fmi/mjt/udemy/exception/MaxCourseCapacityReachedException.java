package bg.sofia.uni.fmi.mjt.udemy.exception;

public class MaxCourseCapacityReachedException extends Exception {
    public MaxCourseCapacityReachedException(String message) {
        super(message);
    }

    public MaxCourseCapacityReachedException(String message, Throwable cause) {
        super(message, cause);
    }
}
