package bg.sofia.uni.fmi.mjt.peaks.model;

/**
 * Клас, който съхранява информация за един връх от mountains.txt
 *
 * @param pos          - Позиция по височина - мястото по височина на съответния връх
 * @param name         - Име (на върха)
 * @param height       - Височина (в метри)
 * @param prominence   - Изпъкналост (в метри) - показва височината на върха от най-високата седловина,
 *                     свързваща го с по-висок връх) (виж Topographic prominence)
 * @param range        - Планина – от коя планинска верига е част върхът
 * @param firstAscent  - Година на първо изкачване
 * @param totalAscents - Брой изкачвания след 2004 г.
 */
public record Peak(int pos, String name, double height, double prominence, String range, int firstAscent,
                   int totalAscents) {

    private static final String PEAK_ATTRIBUTE_DELIMITER = ",";

    public static Peak of(String line) {
        final String[] tokens = line.split(PEAK_ATTRIBUTE_DELIMITER);
        return new Peak(Integer.parseInt(tokens[0]), tokens[1], Double.parseDouble(tokens[2]),
            Double.parseDouble(tokens[3]), tokens[4], Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]));
    }

}
