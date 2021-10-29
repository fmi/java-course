package enums;

public class StringConstants {

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


    // We can pass whatever we want here, no matter if it is an actual day or not
    public static void printDayMessage(String day) {
        // Print some message according to the day
    }

}
