package bg.sofia.uni.fmi.mjt.markdown.converter;

public interface Converter {

    String apply(String line);

    boolean isApplicable(String line);

}
