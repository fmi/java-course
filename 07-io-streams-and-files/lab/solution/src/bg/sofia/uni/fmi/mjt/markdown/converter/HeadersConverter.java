package bg.sofia.uni.fmi.mjt.markdown.converter;

import java.util.regex.Pattern;

public class HeadersConverter extends AbstractConverter {

    private static final String TARGET_HEADER_TAG = "<h%s>%s</h%s>";
    private static final char HEADERS_SYMBOL = '#';

    public HeadersConverter() {
        super(TARGET_HEADER_TAG);
    }

    @Override
    public String apply(String line) {
        int counter = 0;
        while (line.charAt(counter) == HEADERS_SYMBOL) {
            counter++;
        }

        String headerContent = line.substring(counter + 1);

        return String.format(getTargetHtmlTag(), counter, headerContent, counter);
    }

    @Override
    public boolean isApplicable(String line) {
        return Pattern.compile("^#{1,6}\s.+$").matcher(line).find();
    }

}
