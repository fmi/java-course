import java.util.Arrays;

class StringConstants {

    // 1. This approach can lead to performance problems because it relies on string comparisons
    // 2. If the constants contain typos they will escape detection at compile time and result in bugs at runtime
    // 3. There is no way to iterate over all enumerated types
    // 4. There is no type safety
    public static final String MONDAY = "monday";
    public static final String TUESDAY = "tuesday";
    public static final String WEDNESDAY = "wednesday";
    public static final String THURSDAY = "thursday";
    public static final String FRIDAY = "friday";
    public static final String SATURDAY = "saturday";
    public static final String SUNDAY = "sunday";


    // We can pass whatever we want here no matter if it is an actual day or not
    public static void printDayMessage(String day) {
        // Print some message according to the day
    }

}

// Enums extend the java.lang.Enum class implicitly.
// Therefore, you cannot extend any other class in enum.
enum Day {
    SUNDAY("Sun."),
    MONDAY("Mon."),
    TUESDAY("Tu."),
    WEDNESDAY("Wed."),
    THURSDAY("Th."),
    FRIDAY("Fr."),
    SATURDAY("Sat.");

    private String abbreviation;

    // Constructor is always private or default.
    // You cannot create an instance of enum using the new operator.
    Day(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}

class DaysExample {
    private Day day;

    public DaysExample(Day day) {
        this.day = day;
    }

    public void tellItLikeItIs() {
        String message = switch (day) {
            case MONDAY -> "Mondays are bad.";
            case FRIDAY -> "Fridays are better.";
            case SATURDAY, SUNDAY -> "Weekends are best.";
            default -> "Midweek days are so-so.";
        };

        System.out.println(message);
    }

}

public class EnumExamples {
    public static void main(String[] args) {
        DaysExample example = new DaysExample(Day.TUESDAY);
        example.tellItLikeItIs(); // Midweek days are so-so.

        // The values() method is a special method added by the compiler
        Day[] days = Day.values();
        System.out.println(Arrays.toString(days)); // [SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY]

        // The name must match exactly the identifier used to declare the enum constant in this type.
        System.out.println(Day.valueOf("MONDAY")); // MONDAY
        
        System.out.println(Day.MONDAY.getAbbreviation()); // Mon.
    }
}
