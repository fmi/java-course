package bg.sofia.uni.fmi.mjt.udemy.course.duration;

import bg.sofia.uni.fmi.mjt.udemy.course.Resource;

public record CourseDuration(int hours, int minutes) {
    private static final int HOURS_IN_DAY = 24;
    private static final int MINUTES_IN_HOUR = 60;

    public CourseDuration {
        if (hours < 0 || hours > HOURS_IN_DAY) {
            throw new IllegalArgumentException("The hours should be between 0 and 24");
        }

        if (minutes < 0 || minutes > MINUTES_IN_HOUR) {
            throw new IllegalArgumentException("The hours should be between 0 and 60");
        }
    }

    public static CourseDuration of(Resource[] content) {
        int minutes = 0;

        for (Resource resource : content) {
            minutes += resource.getDuration().minutes();
        }

        int hours = minutes / MINUTES_IN_HOUR;
        minutes = minutes - hours * MINUTES_IN_HOUR;
        return new CourseDuration(hours, minutes);
    }

    public boolean isLongerThan(CourseDuration otherDuration) {
        int totalMinutes = this.hours * MINUTES_IN_HOUR + this.minutes;
        int otherTotalMinutes = otherDuration.hours * MINUTES_IN_HOUR + otherDuration.minutes;

        return totalMinutes > otherTotalMinutes;
    }
}
