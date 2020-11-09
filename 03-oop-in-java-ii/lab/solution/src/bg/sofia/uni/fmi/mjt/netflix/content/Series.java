package bg.sofia.uni.fmi.mjt.netflix.content;

import bg.sofia.uni.fmi.mjt.netflix.content.enums.Genre;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.PgRating;

public final class Series extends VideoContent {
    private final Episode[] episodes;

    public Series(String name, Genre genre, PgRating rating, Episode[] episodes) {
        super(name, genre, rating);
        this.episodes = episodes;
    }

    @Override
    public int getDuration() {
        int total = 0;
        for (Episode ep : episodes) {
            total += ep.duration();
        }

        return total;
    }
}
