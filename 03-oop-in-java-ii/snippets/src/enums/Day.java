package enums;

// Enums extend the java.lang.Enum class implicitly.
// Therefore, you cannot extend any other class in enum.
public enum Day {

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
