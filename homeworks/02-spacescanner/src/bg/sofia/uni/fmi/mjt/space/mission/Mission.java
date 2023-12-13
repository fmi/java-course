package bg.sofia.uni.fmi.mjt.space.mission;

import bg.sofia.uni.fmi.mjt.space.rocket.CostParser;
import bg.sofia.uni.fmi.mjt.space.rocket.RocketStatus;
import org.apache.commons.csv.CSVRecord;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Objects;

public record Mission(String id, String company, String location, LocalDate date, Detail detail,
                      RocketStatus rocketStatus, Optional<Double> cost, MissionStatus missionStatus) {

    private static final int ID = 0;
    private static final int COMPANY = 1;
    private static final int LOCATION = 2;
    private static final int DATE = 3;
    private static final int DETAIL = 4;
    private static final int ROCKET_STATUS = 5;
    private static final int COST = 6;
    private static final int MISSION_STATUS = 7;
    private static final String DATE_PATTERN = "EEE MMM dd, yyyy";

    public static Mission of(CSVRecord record) {
        List<String> tokens = record.stream().toList();

        String id = tokens.get(ID);
        String companyName = tokens.get(COMPANY);
        String location = tokens.get(LOCATION);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.ENGLISH);
        LocalDate date = LocalDate.parse(tokens.get(DATE), formatter);
        Detail detail = Detail.of(tokens.get(DETAIL));
        RocketStatus rocketStatus = RocketStatus.fromString(tokens.get(ROCKET_STATUS));
        Optional<Double> cost = CostParser.parseCost(tokens.get(COST));
        MissionStatus missionStatus = MissionStatus.fromString(tokens.get(MISSION_STATUS));

        return new Mission(id, companyName, location, date, detail, rocketStatus, cost, missionStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mission mission = (Mission) o;
        return id.equals(mission.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}