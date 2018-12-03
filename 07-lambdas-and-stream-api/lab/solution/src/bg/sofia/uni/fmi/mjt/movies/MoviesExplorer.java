package bg.sofia.uni.fmi.mjt.movies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import bg.sofia.uni.fmi.mjt.movies.model.Actor;
import bg.sofia.uni.fmi.mjt.movies.model.Movie;

public class MoviesExplorer {

    private List<Movie> movies;

    /**
     * Loads the dataset located in the given {@datasetPath}.
     * 
     * @throws DataSetCannotBeLoaded
     *             if the dataset location does not exist or an error occurs during
     *             reading.
     */
    public MoviesExplorer(InputStream dataInput) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(dataInput))) {

            movies = reader.lines()
                    .map(Movie::createMovie)
                    .collect(Collectors.toList());

            System.out.println("Movies loaded: " + movies.size());

        } catch (IOException e) {
            throw new IllegalArgumentException("Could not load dataset", e);
        }
    }

    public Collection<Movie> getMovies() {
        return Collections.unmodifiableList(movies);
    }

    public int countMoviesReleasedIn(int year) {
        return (int) movies.stream()
                .filter(m -> m.getYear() == year)
                .count();
    }

    public Movie findFirstMovieWithTitle(String title) {
        return movies.stream()
                .filter(m -> m.getTitle().contains(title))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Collection<Actor> getAllActors() {
        return movies.stream()
                .flatMap(movie -> movie.getActors().stream())
                .collect(Collectors.toSet());
    }

    public int getFirstYear() {
        return movies.stream()
                .mapToInt(Movie::getYear)
                .min()
                .getAsInt();
    }

    public Collection<Movie> getAllMoviesBy(Actor actor) {
        return movies.stream()
                .filter(movie -> movie.getActors().stream()
                .anyMatch(a -> a.equals(actor)))
                .collect(Collectors.toList());
    }

    public Collection<Movie> getMoviesSortedByReleaseYear() {
        return movies.stream()
                .sorted(Comparator.comparing(Movie::getYear))
                .collect(Collectors.toList());
    }

    public int findYearWithLeastNumberOfReleasedMovies() {
        Map<Integer, Long> result = movies.stream()
                .collect(Collectors.groupingBy(Movie::getYear, Collectors.counting()));
        
        return result.entrySet()
                .stream()
                .min(Comparator.comparing(x -> x.getValue()))
                .get()
                .getKey();
    }

    public Movie findMovieWithGreatestNumberOfActors() {
        return movies.stream()
                .max(Comparator.comparing(x -> x.getActors().size()))
                .get();
    }
}
