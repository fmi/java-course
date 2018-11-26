package bg.sofia.uni.fmi.mjt.movies.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class Movie {

    private String title;
    private int year;
    private Set<Actor> actors;

    private Movie(String title, int year) {
        this.title = title;
        this.year = year;

        actors = new HashSet<>();
    }

    public static Movie createMovie(String movieLine) {
        String tokens[] = movieLine.split("/");
        String title = parseMovieTitle(tokens[0]);
        String releaseYear = parseMovieYear(tokens[0]);

        Movie movie = new Movie(title, Integer.valueOf(releaseYear));
        movie.addAllActors(parseActors(tokens));

        return movie;
    }

    private static Set<Actor> parseActors(String[] tokens) {
        Set<Actor> actors = new HashSet<>();

        for (int i = 1; i < tokens.length; i++) {
            String[] name = tokens[i].split(", ");
            String lastName = name[0].strip();
            String firstName = "";

            if (name.length > 1) {
                firstName = name[1].strip();
            }

            actors.add(new Actor(firstName, lastName));
        }

        return actors;
    }

    private static String parseMovieTitle(String token) {
        return token.substring(0, token.lastIndexOf("(")).strip();
    }

    private static String parseMovieYear(String token) {
        String year = token.substring(token.lastIndexOf("(") + 1, token.lastIndexOf(")"));

        if (year.contains(",")) {
            year = year.substring(0, year.indexOf(","));
        }

        return year;
    }

    private void addAllActors(Collection<Actor> actors) {
        this.actors.addAll(actors);
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public Set<Actor> getActors() {
        return Collections.unmodifiableSet(actors);
    }

    @Override
    public String toString() {
        return "Movie [title=" + title + ", year=" + year + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((actors == null) ? 0 : actors.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + year;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Movie other = (Movie) obj;
        if (actors == null) {
            if (other.actors != null)
                return false;
        } else if (!actors.equals(other.actors))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (year != other.year)
            return false;
        return true;
    }
}
