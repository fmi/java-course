package bg.sofia.uni.fmi.mjt.space;

public enum Country {
    RUSSIA("Russia"),
    NORTH_KOREA("North Korea"),
    CHINA("China"),
    KAZAKHSTAN("Kazakhstan"),
    FRANCE("France"),
    IRAN("Iran"),
    USA("USA");

    private final String value;

    private Country(String value) {
        this.value = value;
    }

    public static String toString(Country country) {
        for (Country c : Country.values()) {
            if (c.equals(country)) {
                return c.value;
            }
        }

        return "";
    }
}