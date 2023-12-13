package bg.sofia.uni.fmi.mjt.space.rocket;

import java.util.Optional;

public class CostParser {
    public static Optional<Double> parseCost(String str) {
        if (str.isBlank() || str.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(Double.parseDouble(str));
    }
}
