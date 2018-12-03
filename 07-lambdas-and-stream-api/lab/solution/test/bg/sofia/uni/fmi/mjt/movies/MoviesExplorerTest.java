package bg.sofia.uni.fmi.mjt.movies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;

import bg.sofia.uni.fmi.mjt.movies.model.Actor;
import bg.sofia.uni.fmi.mjt.movies.model.Movie;
import bg.sofia.uni.fmi.mjt.movies.util.MoviesStreamInitializer;

public class MoviesExplorerTest {

    private static List<Movie> movies;
    private static MoviesExplorer explorer;

    @BeforeClass
    public static void setUp() throws FileNotFoundException, IOException {
        InputStream moviesStream = MoviesStreamInitializer.initMovieStream();
        
        try (InputStreamReader isr = new InputStreamReader(moviesStream);
                BufferedReader reader = new BufferedReader(isr)) {
            
            movies = reader.lines()
                    .map(Movie::createMovie)
                    .collect(Collectors.toList());
        }

        InputStream explorerStream = MoviesStreamInitializer.initMovieStream();
        explorer = new MoviesExplorer(explorerStream);
    }

    @Test
    public void testIfExistingDatasetIsLoadedCorrectly() {
        String assertMessage = "Number of movies in the MoviesExplorer does not match the movies in the dataset.";
        final int expected = movies.size();
        final int actual = explorer.getMovies().size();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testCountMoviesReleasedInFor2003() {
        String assertMessage = "Number of movies released in 2003 are not counted correctly.";
        final int expected = 2;
        int actual = explorer.countMoviesReleasedIn(2003);

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testCountMoviesReleasedInForUnknownYear() {
        String assertMessage = "Number of movies for an unknown year should be 0.";
        final int unknownYear = 2020;
        final int expected = 0;
        int actual = explorer.countMoviesReleasedIn(unknownYear);

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testFindFirstMovieWithTitle() {
        String assertMessage = "First 'Lord of the Rings' movie is not computed correctly.";
        String title = "Lord of the Rings";
        String expected = "Lord of the Rings, The";
        String actual = explorer.findFirstMovieWithTitle(title).getTitle();

        assertEquals(assertMessage, expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfFindFirstMovieWithTitleForUnknownMovieThrowsException() {
        String title = "Lord of the RingZ";
        explorer.findFirstMovieWithTitle(title);
    }

    // TODO Is toList here a good idea? Can we have more than one movie with the
    // same name?
    @Test
    public void testGetMoviesSortedByReleaseYear() {
        String assertMessage = "Movies are not sorted correctly based on their relase year.";
        Collection<Movie> sortedMovies = movies.stream()
                .sorted((m1, m2) -> m1.getYear() - m2.getYear())
                .collect(Collectors.toList());

        List<Movie> expected = new ArrayList<Movie>(sortedMovies);
        List<Movie> actual = new ArrayList<Movie>(explorer.getMoviesSortedByReleaseYear());

        assertTrue(assertMessage, expected.equals(actual));
    }

    @Test
    public void testGetMinYear() {
        String assertMessage = "First year in the statistics is not computed correctly.";
        final int expected = 1978;
        int actual = explorer.getFirstYear();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testGetAllActors() {
        String assertMessage = "Number of actors does not match the real number from the dataset.";
        final int expected = 43;
        int actual = explorer.getAllActors().size();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testIfGetAllMoviesByReturnsCorrectMoviesForKnownActor() {
        String assertMessage = "Number of movies for a known actor are not computed correctly.";
        Actor actor = new Actor("Orlando", "Bloom");
        final int expected = 3;
        int actual = explorer.getAllMoviesBy(actor).size();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testIfGetAllMoviesByReturnsEmptyCollectionForUnknownActor() {
        String assertMessage = "Number of movies for an unknown actor should be 0.";
        Actor actor = new Actor("Kevin", "Spaceys");
        final int expected = 0;
        int actual = explorer.getAllMoviesBy(actor).size();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testFindYearWithLeastNumberOfReleasedMovies() {
        String assertMessage = "Year with least number of released movies is not computed correctly.";
        List<Integer> expectedYears = Arrays.asList(1996, 2004);
        int actual = explorer.findYearWithLeastNumberOfReleasedMovies();

        assertTrue(assertMessage, expectedYears.contains(actual));
    }

    @Test
    public void testMovieWithGreatestNumberOfActors() {
        String assertMessage = "Movie with greatest number of actors not computet correctly.";
        List<String> expectedMovies = Arrays.asList("Lord of the Rings: The Fellowship of the Ring, The",
                "Lord of the Rings: The Return of the King, The", "Lord of the Rings: The Two Towers, The");

        String actual = explorer.findMovieWithGreatestNumberOfActors().getTitle();
        System.out.println(actual);
        assertTrue(assertMessage, expectedMovies.contains(actual));
    }

}
