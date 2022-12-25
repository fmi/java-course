package bg.sofia.uni.fmi.mjt.markdown.converter;

public class ItalicConverter extends AbstractFormattingConverter {

    public ItalicConverter() {
        super("*", "<em>%s</em>");
    }

}
