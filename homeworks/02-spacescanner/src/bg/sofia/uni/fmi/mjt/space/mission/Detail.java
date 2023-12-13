package bg.sofia.uni.fmi.mjt.space.mission;

public record Detail(String rocketName, String payload) {
    private static final String DELIMITER = "\\|";

    public static Detail of(String line) {
        String[] parts = line.split(DELIMITER);
        return new Detail(parts[0].trim(), parts[1]);
    }
}