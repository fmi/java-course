package bg.sofia.uni.fmi.mjt.markdown.converter;

public class CodeConverter extends AbstractFormattingConverter {

    public CodeConverter() {
        super("`", "<code>%s</code>");
    }

}
