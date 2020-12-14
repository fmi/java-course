package bg.sofia.uni.fmi.mjt.spotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class SpotifyExplorer {

    private List<SpotifyTrack> spotifyTracks;

    /**
     * Loads the dataset from the given {@code dataInput} stream.
     *
     * @param dataInput java.io.Reader input stream from which the dataset can be read
     */
    public SpotifyExplorer(Reader dataInput) {
        try (var reader = new BufferedReader(dataInput)) {

            spotifyTracks = reader.lines()
                    .skip(1)
                    .map(SpotifyTrack::of)
                    .collect(Collectors.toList());

            System.out.println("Tracks loaded: " + spotifyTracks.size());

        } catch (IOException e) {
            throw new IllegalArgumentException("Could not load dataset", e);
        }
    }

    /**
     * @return all spotify tracks from the dataset as unmodifiable collection
     *         if the dataset is empty, return an empty collection
     */
    public Collection<SpotifyTrack> getAllSpotifyTracks() {
        return Collections.unmodifiableCollection(spotifyTracks);
    }

    /**
     * @return all tracks from the spotify dataset classified as explicit as unmodifiable collection
     *         if the dataset is empty or contains no tracks classified as explicit, return an empty collection
     */
    public Collection<SpotifyTrack> getExplicitSpotifyTracks() {
        return Collections.unmodifiableCollection(spotifyTracks.stream()
                .filter(SpotifyTrack::explicit)
                .collect(Collectors.toList()));
    }

    /**
     * Returns all tracks in the dataset, grouped by release year. If no tracks were released in a given year
     * it should not appear as key in the map.
     *
     * @return map with year as a key and the set of spotify tracks released this year as value.
     *         The list of tracks is in no particular order.
     *         if the dataset is empty, return an empty collection
     */
    public Map<Integer, Set<SpotifyTrack>> groupSpotifyTracksByYear() {
        return spotifyTracks.stream()
                .collect(Collectors.groupingBy(SpotifyTrack::year, Collectors.toSet()));
    }

    /**
     * Return the number of years between the oldest and the newest released tracks of an artist.
     * Note that tracks with multiple authors including the given artist should also be considered in the result.
     *
     * @param artist artist name
     * @throws IllegalArgumentException if artist is null
     * @return number of active years,
     *         0 if the dataset is empty or there are no tracks by the given artist in the dataset
     */
    public int getArtistActiveYears(String artist) {
        if (artist == null) {
            throw new IllegalArgumentException("");
        }
        IntSummaryStatistics statistics = spotifyTracks.stream()
                .filter(track -> track.artists().contains(artist))
                .mapToInt(SpotifyTrack::year)
                .summaryStatistics();

        if (statistics.getMin() == Integer.MAX_VALUE) {
            return 0;
        }

        return statistics.getMax() - statistics.getMin() + 1;
    }

    /**
     * Returns the @n tracks with highest valence from the 80s.
     * Note that the 80s started in 1980 and lasted until 1989, inclusive.
     * Valence describes the musical positiveness conveyed by a track.
     * Tracks with high valence sound more positive (happy, cheerful, euphoric),
     * while tracks with low valence sound more negative (sad, depressed, angry).
     *
     * @param n number of tracks to return
     *        If @n exceeds the total number of tracks from the 80s, return all tracks available from this period.
     * @throws IllegalArgumentException in case @n is a negative number.
     * @return unmodifiable list of tracks sorted by valence in descending order
     */
    public List<SpotifyTrack> getTopNHighestValenceTracksFromThe80s(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Number of tracks should be a non-negative number");
        }

        return Collections.unmodifiableList(spotifyTracks.stream()
                .filter(d -> d.year() >= 1980 && d.year() <= 1989)
                .sorted(Comparator.comparingDouble(SpotifyTrack::valence).reversed())
                .limit(n).collect(Collectors.toList()));
    }

    /**
     * Return the most popular track from the 90s.
     * Note that the 90s started in 1990 and lasted until 1999, inclusive.
     * The value is between 0 and 100, with 100 being the most popular.
     *
     * @throws NoSuchElementException if there are no tracks from the 90s in the dataset
     * @return the most popular track of the 90s,
     *         if there more than one tracks with equal highest popularity, return any of them
     */
    public SpotifyTrack getMostPopularTrackFromThe90s() {
        return spotifyTracks.stream()
                .filter(d -> d.year() >= 1990 && d.year() <= 1999)
                .max(Comparator.comparingInt(SpotifyTrack::popularity))
                .orElseThrow();
    }

    /**
     * Returns the number of tracks longer than @minutes released before @year.
     *
     * @param minutes
     * @param year
     * @throws IllegalArgumentException in case @minutes or @year is a negative number
     * @return
     */
    public long getNumberOfLongerTracksBeforeYear(int minutes, int year) {
        if (minutes < 0 || year < 0) {
            throw new IllegalArgumentException("Minutes and years cannot be negative numbers");
        }

        return spotifyTracks.stream()
                .filter(t -> t.year() < year)
                .filter(t -> (double) t.duration() / 1000.0 / 60.0 > minutes)
                .count();
    }

    /**
     * Returns the loudest track released in a given year
     *
     * @param year
     * @throws IllegalArgumentException in case @year is a negative number
     * @return
     */
    public Optional<SpotifyTrack> getTheLoudestTrackInYear(int year) {
        if (year < 0) {
            throw new IllegalArgumentException("Year cannot be a negative number");
        }

        return spotifyTracks.stream()
                .filter(t -> t.year() == year)
                .sorted(Comparator.comparingDouble(SpotifyTrack::loudness).reversed())
                .findFirst();
    }
}
