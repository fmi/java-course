package bg.sofia.uni.fmi.mjt.spotify;

import java.io.Reader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

public class SpotifyExplorer {

    /**
     * Loads the dataset from the given {@code dataInput} stream.
     *
     * @param dataInput java.io.Reader input stream from which the dataset can be read
     */
    public SpotifyExplorer(Reader dataInput) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    /**
     * @return all spotify tracks from the dataset as unmodifiable collection
     * If the dataset is empty, return an empty collection
     */
    public Collection<SpotifyTrack> getAllSpotifyTracks() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    /**
     * @return all tracks from the spotify dataset classified as explicit as unmodifiable collection
     * If the dataset is empty or contains no tracks classified as explicit, return an empty collection
     */
    public Collection<SpotifyTrack> getExplicitSpotifyTracks() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    /**
     * Returns all tracks in the dataset, grouped by release year. If no tracks were released in a given year
     * it should not appear as key in the map.
     *
     * @return map with year as a key and the set of spotify tracks released this year as value.
     * If the dataset is empty, return an empty collection
     */
    public Map<Integer, Set<SpotifyTrack>> groupSpotifyTracksByYear() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    /**
     * Returns the number of years between the oldest and the newest released tracks of an artist.
     * Note that tracks with multiple authors including the given artist should also be considered in the result.
     *
     * @param artist artist name
     * @return number of active years
     * If the dataset is empty or there are no tracks by the given artist in the dataset, return 0.
     */
    public int getArtistActiveYears(String artist) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    /**
     * Returns the @n tracks with highest valence from the 80s.
     * Note that the 80s started in 1980 and lasted until 1989, inclusive.
     * Valence describes the musical positiveness conveyed by a track.
     * Tracks with high valence sound more positive (happy, cheerful, euphoric),
     * while tracks with low valence sound more negative (sad, depressed, angry).
     *
     * @param n number of tracks to return
     *          If @n exceeds the total number of tracks from the 80s, return all tracks available from this period.
     * @return unmodifiable list of tracks sorted by valence in descending order
     * @throws IllegalArgumentException in case @n is a negative number.
     */
    public List<SpotifyTrack> getTopNHighestValenceTracksFromThe80s(int n) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    /**
     * Returns the most popular track from the 90s.
     * Note that the 90s started in 1990 and lasted until 1999, inclusive.
     * The value is between 0 and 100, with 100 being the most popular.
     *
     * @return the most popular track of the 90s.
     * If there more than one tracks with equal highest popularity, return any of them
     * @throws NoSuchElementException if there are no tracks from the 90s in the dataset
     */
    public SpotifyTrack getMostPopularTrackFromThe90s() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    /**
     * Returns the number of tracks longer than @minutes released before @year.
     *
     * @param minutes
     * @param year
     * @return the number of tracks longer than @minutes released before @year
     * @throws IllegalArgumentException in case @minutes or @year is a negative number
     */
    public long getNumberOfLongerTracksBeforeYear(int minutes, int year) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    /**
     * Returns the loudest track released in a given year
     *
     * @param year
     * @return the loudest track released in a given year
     * @throws IllegalArgumentException in case @year is a negative number
     */
    public Optional<SpotifyTrack> getTheLoudestTrackInYear(int year) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

}
