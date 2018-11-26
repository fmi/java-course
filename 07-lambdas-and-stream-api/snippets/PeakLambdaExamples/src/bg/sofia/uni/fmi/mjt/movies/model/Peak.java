package bg.sofia.uni.fmi.mjt.movies.model;

/**
 * Клас който съхранява информация за един връх от mountains.txt
 */
public class Peak {
    private static final String PEAK_ATTR_DELIMITER = ",";

    private final int pos; // Позиция по височина - мястото по височина на съответния връх
    private final String name; // Име (на върха)
    private final double height; // Височина (в метри)
    private final double prominence; // Изпъкналост (в метри) - показва височината на върха от най-високата седловина, свързваща го с по-висок връх) (виж Topographic prominence)
    private final String range; // Планина – от коя планинска верига е част върхът
    private final int firstAscent; // Година на първо изкачване
    private final int totalAscents; //Брой изкачвания след 2004 г.

    private Peak(
            int pos, String name, double height, double prominence, String range, int firstAscent, int totalAscents) {
        this.pos = pos;
        this.name = name;
        this.height = height;
        this.prominence = prominence;
        this.range = range;
        this.firstAscent = firstAscent;
        this.totalAscents = totalAscents;
    }

    public static Peak createPeak(String line) {
        final String[] tokens = line.split(PEAK_ATTR_DELIMITER);
        return new Peak(
                Integer.parseInt(tokens[0]),
                tokens[1],
                Double.parseDouble(tokens[2]),
                Double.parseDouble(tokens[3]),
                tokens[4],
                Integer.parseInt(tokens[5]),
                Integer.parseInt(tokens[6]));
    }

    public int getPos() {
        return pos;
    }

    public String getName() {
        return name;
    }

    public double getHeight() {
        return height;
    }

    public double getProminence() {
        return prominence;
    }

    public String getRange() {
        return range;
    }

    public int getFirstAscent() {
        return firstAscent;
    }

    public int getTotalAscents() {
        return totalAscents;
    }

}