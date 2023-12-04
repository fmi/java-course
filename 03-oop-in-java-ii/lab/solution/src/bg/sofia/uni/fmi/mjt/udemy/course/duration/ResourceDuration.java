package bg.sofia.uni.fmi.mjt.udemy.course.duration;

public record ResourceDuration(int minutes) {
    private static final int MINUTES_IN_HOUR = 60;

    public ResourceDuration {
        if (minutes < 0 || minutes > MINUTES_IN_HOUR) {
            throw new IllegalArgumentException("Course duration is calculated in minutes and must be between 0 and 60");
        }
    }
}
