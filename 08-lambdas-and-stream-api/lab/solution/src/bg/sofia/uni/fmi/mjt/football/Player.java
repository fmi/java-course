package bg.sofia.uni.fmi.mjt.football;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public record Player(String name, String fullName, LocalDate birthDate, int age, double heightCm, double weightKg,
        List<Position> positions, String nationality, int overallRating, int potential, long valueEuro, long wageEuro,
        Foot preferredFoot) {

    private static final int NAME = 0;
    private static final int FULL_NAME = 1;
    private static final int BIRTH_DATE = 2;
    private static final int AGE = 3;
    private static final int HEIGHT_CM = 4;
    private static final int WEIGHT_KG = 5;
    private static final int POSITIONS = 6;
    private static final int NATIONALITY = 7;
    private static final int OVERALL_RATING = 8;
    private static final int POTENTIAL = 9;
    private static final int VALUE_EURO = 10;
    private static final int WAGE_EURO = 11;
    private static final int PREFERRED_FOOT = 12;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");

    public static Player of(String line) {
        String[] tokens = line.split(";");

        String name = tokens[NAME];
        String fullName = tokens[FULL_NAME];
        LocalDate birthDate = parseDate(tokens[BIRTH_DATE]);
        int age = Integer.parseInt(tokens[AGE]);
        double heightCm = Double.parseDouble(tokens[HEIGHT_CM]);
        double weightKg = Double.parseDouble(tokens[WEIGHT_KG]);
        List<Position> positions = Arrays.stream(tokens[POSITIONS].split(",")).map(Position::valueOf).toList();
        String nationality = tokens[NATIONALITY];
        int overallRating = Integer.parseInt(tokens[OVERALL_RATING]);
        int potential = Integer.parseInt(tokens[POTENTIAL]);
        long valueEuro = Long.parseLong(tokens[VALUE_EURO]);
        long wageEuro = Long.parseLong(tokens[WAGE_EURO]);
        Foot preferredFoot = Foot.of(tokens[PREFERRED_FOOT]);

        return new Player(name, fullName, birthDate, age, heightCm, weightKg, positions, nationality, overallRating,
                potential, valueEuro, wageEuro, preferredFoot);
    }

    private static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

}
