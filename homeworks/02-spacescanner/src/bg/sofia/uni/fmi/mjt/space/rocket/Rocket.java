package bg.sofia.uni.fmi.mjt.space.rocket;

import org.apache.commons.csv.CSVRecord;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record Rocket(String id, String name, Optional<String> wiki, Optional<Double> height) {
    private static final int ID = 0;
    private static final int NAME = 1;
    private static final int WIKI = 2;
    private static final int HEIGHT = 3;

    public static Rocket of(CSVRecord record) {
        List<String> tokens = record.stream().toList();

        String id = tokens.get(ID);
        String name = tokens.get(NAME);
        Optional<String> wiki = WikiParser.parseWiki(tokens.get(WIKI));
        Optional<Double> height = HeightParser.parseHeight(tokens.get(HEIGHT));

        return new Rocket(id, name, wiki, height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rocket rocket = (Rocket) o;
        return id.equals(rocket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}