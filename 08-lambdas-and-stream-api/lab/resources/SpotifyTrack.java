package bg.sofia.uni.fmi.mjt.spotify;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record SpotifyTrack(String id, Set<String> artists, String name,
                           int year, int popularity, long duration, double tempo,
                           double loudness, double valence, double acousticness,
                           double danceability, double energy, double liveness,
                           double speechiness, boolean explicit) {

    private static final int ID = 0;
    private static final int ARTISTS = 1;
    private static final int NAME = 2;
    private static final int YEAR = 3;
    private static final int POPULARITY = 4;
    private static final int DURATION_MS = 5;
    private static final int TEMPO = 6;
    private static final int LOUDNESS = 7;
    private static final int VALENCE = 8;
    private static final int ACOUSTICNESS = 9;
    private static final int DANCEABILITY = 10;
    private static final int ENERGY = 11;
    private static final int LIVENESS = 12;
    private static final int SPEECHINESS = 13;
    private static final int EXPLICIT = 14;
    private static final String IS_EXPLICIT = "1";

    /**
     * Returns a SpotifyTrack instance, based on the given @{line} from the dataset.
     *
     * @param line line from the dataset text file
     * @return SpotifyTrack instance
     **/
    public static SpotifyTrack of(String line) {
        String[] tokens = line.split(",");

        String id = tokens[ID];
        Set<String> artists = Stream.of(tokens[ARTISTS].split(";"))
                .map(s -> s.strip().replace("[", "").replace("]", "").replaceAll("'", ""))
                .collect(Collectors.toSet());

        String name = tokens[NAME];
        int year = Integer.parseInt(tokens[YEAR]);
        int popularity = Integer.parseInt(tokens[POPULARITY]);
        long duration = Long.parseLong(tokens[DURATION_MS]);
        double tempo = Double.parseDouble(tokens[TEMPO]);
        double loudness = Double.parseDouble(tokens[LOUDNESS]);
        double valence = Double.parseDouble(tokens[VALENCE]);
        double acousticness = Double.parseDouble(tokens[ACOUSTICNESS]);
        double danceability = Double.parseDouble(tokens[DANCEABILITY]);
        double energy = Double.parseDouble(tokens[ENERGY]);
        double liveness = Double.parseDouble(tokens[LIVENESS]);
        double speechiness = Double.parseDouble(tokens[SPEECHINESS]);
        boolean explicit = tokens[EXPLICIT].equals(IS_EXPLICIT);

        return new SpotifyTrack(id, artists, name, year, popularity, duration, tempo, loudness, valence, acousticness,
                danceability, energy, liveness, speechiness, explicit);
    }

}
