package bg.sofia.uni.fmi.mjt.game.recommender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record Game(String name, String platform, LocalDate releaseDate, String summary, int metaScore,
                   double userReview) {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    private static final int NAME = 0;
    private static final int PLATFORM = 1;
    private static final int RELEASE_DATE = 2;
    private static final int SUMMARY = 3;
    private static final int META_SCORE = 4;
    private static final int USER_REVIEW = 5;

    /**
     * Returns a Game instance, based on the given @{line} from the dataset.
     *
     * @param line line from the dataset text file
     * @return Game instance
     **/
    public static Game of(String line) {
        String[] tokens = line.split(",");

        String name = tokens[NAME];
        String platform = tokens[PLATFORM];
        LocalDate releaseDate = LocalDate.parse(tokens[RELEASE_DATE], DATE_FORMATTER);
        String summary = tokens[SUMMARY];
        int metaScore = Integer.parseInt(tokens[META_SCORE]);
        double userReview = Double.parseDouble(tokens[USER_REVIEW]);

        return new Game(name, platform, releaseDate, summary, metaScore, userReview);
    }

}
