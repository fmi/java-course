package bg.sofia.uni.fmi.mjt.netflix.content.enums;

public enum PgRating {
    G("General audience"),
    PG13("May be inappropriate for children under 13"),
    NC17("Adults Only");

    public final String details;

    PgRating(String details) {
        this.details = details;
    }
}
