package bg.sofia.uni.fmi.mjt.markdown.converter;

public abstract class AbstractFormattingConverter extends AbstractConverter {
    
    private final String formattingSymbol;

    protected AbstractFormattingConverter(String formattingSymbol, String targetHtmlTag) {
        super(targetHtmlTag);
        this.formattingSymbol = formattingSymbol;
    }

    @Override
    public String apply(String line) {
        String boldedContent = getFormattedContent(line);
        String oldContent = formattingSymbol + boldedContent + formattingSymbol;
        String newContent = String.format(getTargetHtmlTag(), boldedContent);

        return line.replace(oldContent, newContent);
    }

    @Override
    public boolean isApplicable(String line) {
        return line.contains(formattingSymbol);
    }

    private String getFormattedContent(String line) {
        int firstOccurrence = line.indexOf(formattingSymbol);
        int lastOccurrence = line.lastIndexOf(formattingSymbol);
        return line.substring(firstOccurrence + formattingSymbol.length(), lastOccurrence);
    }

}
