package bg.sofia.uni.fmi.mjt.netflix.content;

import bg.sofia.uni.fmi.mjt.netflix.content.enums.Genre;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.PgRating;

public final class Movie extends VideoContent {
    private final int runtime;

    public Movie(String name, Genre genre, PgRating rating, int runtime) {
        super(name, genre, rating);
        this.runtime = runtime;
    }

    public int getDuration() {
        return runtime;
    }
}
