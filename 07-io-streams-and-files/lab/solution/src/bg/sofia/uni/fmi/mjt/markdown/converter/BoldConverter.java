package bg.sofia.uni.fmi.mjt.markdown.converter;

public class BoldConverter extends AbstractFormattingConverter {

    public BoldConverter() {
        super("**", "<strong>%s</strong>");
    }

}
