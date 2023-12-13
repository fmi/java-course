package bg.sofia.uni.fmi.mjt.space.rocket;

import java.util.Optional;

public class HeightParser {
    private static final String SPACE_REGEX = " ";

    public static Optional<Double> parseHeight(String str) {
        if (str.isBlank() || str.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(Double.parseDouble(str.split(SPACE_REGEX)[0]));
    }
}
