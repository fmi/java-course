package bg.sofia.uni.fmi.mjt.spotify;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class SpotifyExplorerTest {
    private static List<SpotifyTrack> tracks;
    private static SpotifyExplorer explorer;

    @BeforeClass
    public static void setUp() throws IOException {
        Reader tracksStream = initTracksStream();

        try (BufferedReader reader = new BufferedReader(tracksStream)) {
            tracks = reader.lines()
                    .skip(1)
                    .map(SpotifyTrack::of)
                    .collect(Collectors.toList());
        }

        explorer = new SpotifyExplorer(initTracksStream());
    }

    @Test
    public void testIfExistingDatasetIsLoadedCorrectly() {
        String assertMessage =
                "Number of tracks in the SpotifyExplorer does not match the number of tracks in the dataset.";
        int expected = tracks.size();
        int actual = explorer.getAllSpotifyTracks().size();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testGetExplicitSpotifyTracksUnmodifiable() {
        String assertMessage = "Returned collection is not unmodifiable";
        Collection<SpotifyTrack> actual = explorer.getExplicitSpotifyTracks();

        try {
            actual.clear();
        } catch(UnsupportedOperationException e) {
            return;
        }

        fail(assertMessage);
    }

    @Test
    public void testGetExplicitSpotifyTracks() {
        String assertMessage = "The returned explicit tracks do not match the explicit tracks from the dataset";
        List<SpotifyTrack> expected = List.of(
                SpotifyTrack.of("7iOhWWYjhhQiXzF4o4HhXN,['Kanye West'],Last Call,2004,53,760973,83.212,-6.843,0.854,0.577,0.473,0.727,0.316,0.356,1"),
                SpotifyTrack.of("0HtOJj7Kl74s1Ngf3MWeif,['J. Cole'],She's Mine Pt. 1,2016,63,209080,139.652,-15.165,0.0408,0.766,0.439,0.204,0.284,0.0587,1"),
                SpotifyTrack.of("2rlcCS6vJSTrRMjsmVkoFY,['50 Cent'],Piggy Bank,2005,52,255173,79.374,-4.127,0.872,0.012,0.48,0.845,0.331,0.303,1")
        );
        Collection<SpotifyTrack> actual = explorer.getExplicitSpotifyTracks();

        assertTrue(assertMessage, expected.containsAll(actual));
        assertTrue(assertMessage, actual.containsAll(expected));
    }

    @Test
    public void testGroupSpotifyTracksByYear() {
        String assertMessage = "Wrong grouping by year of tracks from the dataset";
        Map<Integer, Set<SpotifyTrack>> expected = Map.of(
                1983, Set.of(
                        SpotifyTrack.of("6ZMQ6kKshuMLWrYk37KBmX,['Stevie Nicks'],Enchanted - 2016 Remaster,1983,40,186173,140.335,-9.841,0.988,0.0648,0.593,0.827,0.0842,0.0288,0"),
                        SpotifyTrack.of("26lCNg8WekUoDbLhEbUyXZ,['Jackson Browne'],Lawyers in Love,1983,40,259747,139.031,-6.496,0.639,0.0286,0.522,0.74,0.145,0.0284,0")
                ),
                1995, Set.of(
                        SpotifyTrack.of("1HtgEaqki4dFclmVJlJDx1,['Grupo Libra'],¿Quieres Ser Mi Novia?,1995,45,195531,136.641,-7.264,0.81,0.606,0.612,0.579,0.353,0.0269,0"),
                        SpotifyTrack.of("1HsbGJnOXPWfocSS6FoR60,['Alanis Morissette'],You Learn,1995,41,239640,168.056,-8.476,0.44,0.00167,0.397,0.668,0.0944,0.0408,0")
                ),
                2003, Set.of(
                        SpotifyTrack.of("400abutrj9yS5YBkd3fE6P,['Phil Collins'],Look Through My Eyes - From 'Brother Bear'/Soundtrack Version,2003,51,240467,158.258,-8.258,0.389,0.0849,0.391,0.704,0.11,0.0401,0"),
                        SpotifyTrack.of("5Mcvgt2GbxxCp6cz3Kfjey,['50 Cent'],In Da Club,2003,46,193467,90.042,-3.955,0.847,0.325,0.906,0.663,0.0883,0.355,0")
                ),
                2004, Set.of(
                        SpotifyTrack.of("7iOhWWYjhhQiXzF4o4HhXN,['Kanye West'],Last Call,2004,53,760973,83.212,-6.843,0.854,0.577,0.473,0.727,0.316,0.356,1"),
                        SpotifyTrack.of("4iN0LgZ4SwAIVJ7VRrsd27,['Banda Cuisillos'],Vanidosa,2004,57,197133,109.887,-7.956,0.966,0.261,0.801,0.621,0.0552,0.0354,0")
                ),
                2005, Set.of(
                        SpotifyTrack.of("2rlcCS6vJSTrRMjsmVkoFY,['50 Cent'],Piggy Bank,2005,52,255173,79.374,-4.127,0.872,0.012,0.48,0.845,0.331,0.303,1")
                ),
                2016, Set.of(
                        SpotifyTrack.of("0HtOJj7Kl74s1Ngf3MWeif,['J. Cole'],She's Mine Pt. 1,2016,63,209080,139.652,-15.165,0.0408,0.766,0.439,0.204,0.284,0.0587,1")
                ),
                2017, Set.of(
                        SpotifyTrack.of("30MM5jWpUmOxtTNd9Ey5LZ,['Thomas Rhett'],Unforgettable,2017,68,157080,130.077,-5.497,0.828,0.0303,0.661,0.75,0.0849,0.0314,0"),
                        SpotifyTrack.of("1P17dC1amhFzptugyAO7Il,['Taylor Swift'],Look What You Made Me Do,2017,76,211853,128.07,-6.471,0.506,0.204,0.766,0.709,0.126,0.123,0"),
                        SpotifyTrack.of("4B1rpPmQXwj78wk6aIGwwU,['Harry Styles'],Two Ghosts,2017,76,229813,69.387,-7.095,0.456,0.234,0.386,0.407,0.0999,0.0268,0")
                )
        );
        Map<Integer, Set<SpotifyTrack>> actual = explorer.groupSpotifyTracksByYear();
        Set<Map.Entry<Integer, Set<SpotifyTrack>>> entries = actual.entrySet();
        for (var entry : entries) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }

        assertEquals(assertMessage, expected.keySet(), actual.keySet());
        assertTrue(assertMessage, expected.entrySet().stream().allMatch(t ->
                assertCollectionsEqual(t.getValue(), actual.get(t.getKey())))
        );
    }

    @Test
    public void testGetArtistActiveYearsArtistNotFound() {
        String assertMessage = "Found active artist but no active artist was expected";
        int expected = 0;
        int actual = explorer.getArtistActiveYears("asd");

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testGetArtistActiveYearsMoreThan1() {
        String assertMessage = "Active years are not calculated correctly";
        int expected = 3;
        int actual = explorer.getArtistActiveYears("50 Cent");

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testGetArtistActiveYears() {
        String assertMessage = "Wrong number of active years for the given artist";
        int expected = 1;
        int actual = explorer.getArtistActiveYears("J. Cole");

        assertEquals(assertMessage, expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTopNHighestValenceTracksFromThe80sNegativeN() {
        explorer.getTopNHighestValenceTracksFromThe80s(-1);
    }

    @Test
    public void testGetTopNHigestValenceTracksFromThe80sUnmodifiable() {
        String assertMessage = "Returned collection is not unmodifiable";
        List<SpotifyTrack> actual = explorer.getTopNHighestValenceTracksFromThe80s(1);

        try {
            actual.clear();
        } catch (UnsupportedOperationException e) {
            return;
        }

        fail(assertMessage);
    }

    @Test
    public void testGetTopNHighestValenceTracksFromThe80s() {
        String assertMessage = "The top n highest valence tracks from the 80s are not determined correctly";
        List<SpotifyTrack> expected = List.of(
                SpotifyTrack.of("6ZMQ6kKshuMLWrYk37KBmX,['Stevie Nicks'],Enchanted - 2016 Remaster,1983,40,186173,140.335,-9.841,0.988,0.0648,0.593,0.827,0.0842,0.0288,0")
        );
        List<SpotifyTrack> actual = explorer.getTopNHighestValenceTracksFromThe80s(1);

        assertTrue(assertMessage, assertCollectionsEqual(expected, actual));
    }

    @Test
    public void testGetMostPopularTrackFromThe90s() {
        String assertMessage = "Wrong track was returned";
        SpotifyTrack expected = SpotifyTrack.of("1HtgEaqki4dFclmVJlJDx1,['Grupo Libra'],¿Quieres Ser Mi Novia?,1995,45,195531,136.641,-7.264,0.81,0.606,0.612,0.579,0.353,0.0269,0");
        SpotifyTrack actual = explorer.getMostPopularTrackFromThe90s();

        assertEquals(assertMessage, expected, actual);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetMostPopularTrackFromThe90sNotFound() {
        SpotifyExplorer no90sExplorer = new SpotifyExplorer(new StringReader(""));
        no90sExplorer.getMostPopularTrackFromThe90s();
    }

    @Test
    public void testGetNumberOfLongerTracksBeforeYear() {
        String assertMessage = "Wrong number of tracks returned for given year";
        long expected = 1;
        long actual = explorer.getNumberOfLongerTracksBeforeYear(4, 2000);

        assertEquals(assertMessage, expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNumberOfLongerTracksBeforeYearNegativeMinutes() {
        explorer.getNumberOfLongerTracksBeforeYear(-1, 2000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNumberOfLongerTracksBeforeYearNegativeYears() {
        explorer.getNumberOfLongerTracksBeforeYear(4, -1);
    }

    @Test
    public void testGetTheLoudestTrackInYear() {
        String assertMessage = "Unexpected track for given year";
        SpotifyTrack expected = SpotifyTrack.of("30MM5jWpUmOxtTNd9Ey5LZ,['Thomas Rhett'],Unforgettable,2017,68,157080,130.077,-5.497,0.828,0.0303,0.661,0.75,0.0849,0.0314,0");
        Optional<SpotifyTrack> actual = explorer.getTheLoudestTrackInYear(2017);

        assertEquals(assertMessage, expected, actual.get());
    }

    @Test
    public void testGetTheLoudestTrackInYearNotFound() {
        String assertMessage = "Found track but no track was expected";
        Optional<SpotifyTrack> actual = explorer.getTheLoudestTrackInYear(2001);

        assertFalse(assertMessage, actual.isPresent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTheLoudestTrackInYearNegativeYear() {
        explorer.getTheLoudestTrackInYear(-1);
    }

    private boolean assertCollectionsEqual(Collection<SpotifyTrack> expected, Collection<SpotifyTrack> actual) {
        return expected.containsAll(actual) && actual.containsAll(expected);
    }

    public static Reader initTracksStream() {
        String[] tracks = {
                "",
                "6ZMQ6kKshuMLWrYk37KBmX,['Stevie Nicks'],Enchanted - 2016 Remaster,1983,40,186173,140.335,-9.841,0.988,0.0648,0.593,0.827,0.0842,0.0288,0",
                "26lCNg8WekUoDbLhEbUyXZ,['Jackson Browne'],Lawyers in Love,1983,40,259747,139.031,-6.496,0.639,0.0286,0.522,0.74,0.145,0.0284,0",
                "1HsbGJnOXPWfocSS6FoR60,['Alanis Morissette'],You Learn,1995,41,239640,168.056,-8.476,0.44,0.00167,0.397,0.668,0.0944,0.0408,0",
                "1HtgEaqki4dFclmVJlJDx1,['Grupo Libra'],¿Quieres Ser Mi Novia?,1995,45,195531,136.641,-7.264,0.81,0.606,0.612,0.579,0.353,0.0269,0",
                "400abutrj9yS5YBkd3fE6P,['Phil Collins'],Look Through My Eyes - From 'Brother Bear'/Soundtrack Version,2003,51,240467,158.258,-8.258,0.389,0.0849,0.391,0.704,0.11,0.0401,0",
                "5Mcvgt2GbxxCp6cz3Kfjey,['50 Cent'],In Da Club,2003,46,193467,90.042,-3.955,0.847,0.325,0.906,0.663,0.0883,0.355,0",
                "7iOhWWYjhhQiXzF4o4HhXN,['Kanye West'],Last Call,2004,53,760973,83.212,-6.843,0.854,0.577,0.473,0.727,0.316,0.356,1",
                "4iN0LgZ4SwAIVJ7VRrsd27,['Banda Cuisillos'],Vanidosa,2004,57,197133,109.887,-7.956,0.966,0.261,0.801,0.621,0.0552,0.0354,0",
                "0HtOJj7Kl74s1Ngf3MWeif,['J. Cole'],She's Mine Pt. 1,2016,63,209080,139.652,-15.165,0.0408,0.766,0.439,0.204,0.284,0.0587,1",
                "30MM5jWpUmOxtTNd9Ey5LZ,['Thomas Rhett'],Unforgettable,2017,68,157080,130.077,-5.497,0.828,0.0303,0.661,0.75,0.0849,0.0314,0",
                "1P17dC1amhFzptugyAO7Il,['Taylor Swift'],Look What You Made Me Do,2017,76,211853,128.07,-6.471,0.506,0.204,0.766,0.709,0.126,0.123,0",
                "4B1rpPmQXwj78wk6aIGwwU,['Harry Styles'],Two Ghosts,2017,76,229813,69.387,-7.095,0.456,0.234,0.386,0.407,0.0999,0.0268,0",
                "2rlcCS6vJSTrRMjsmVkoFY,['50 Cent'],Piggy Bank,2005,52,255173,79.374,-4.127,0.872,0.012,0.48,0.845,0.331,0.303,1"
        };

        return new StringReader(Arrays.stream(tracks).collect(Collectors.joining(System.lineSeparator())));
    }
}
