package bg.sofia.uni.fmi.mjt.netflix.content;

import bg.sofia.uni.fmi.mjt.netflix.content.enums.Genre;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.PgRating;

import java.util.Objects;

public abstract sealed class VideoContent implements Streamable permits Movie, Series {

    private String name;
    private Genre genre;
    private PgRating rating;

    public VideoContent(String name, Genre genre, PgRating rating) {
        this.name = name;
        this.genre = genre;
        this.rating = rating;
    }

    public abstract int getDuration();

    public PgRating getRating() {
        return rating;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getTitle() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoContent)) return false;
        VideoContent that = (VideoContent) o;
        return Objects.equals(name, that.name) &&
            genre == that.genre &&
            rating == that.rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, genre, rating);
    }
}
