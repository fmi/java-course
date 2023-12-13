package bg.sofia.uni.fmi.mjt.space.rocket;

import java.util.Optional;

public class WikiParser {
    public static Optional<String> parseWiki(String str) {
        if (str.isBlank() || str.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(str);
    }
}
