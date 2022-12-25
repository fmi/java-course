package bg.sofia.uni.fmi.mjt.markdown.converter;

public abstract class AbstractConverter implements Converter {

    private final String targetHtmlTag;

    protected AbstractConverter(String targetHtmlTag) {
        this.targetHtmlTag = targetHtmlTag;
    }

    public String getTargetHtmlTag() {
        return targetHtmlTag;
    }

    @Override
    public abstract String apply(String line);

    @Override
    public abstract boolean isApplicable(String line);

}
