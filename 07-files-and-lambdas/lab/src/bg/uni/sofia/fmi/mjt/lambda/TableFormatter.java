package bg.uni.sofia.fmi.mjt.lambda;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TableFormatter {
    public static final int ROW_WIDTH = 50;
    public static final int NAME_WIDTH = 20;
    public static final int HEIGHT_WIDTH = 10;

    private static final String PIPE = "|";
    private static final String CR = "\n";

    public static Stream<String> tableHeader() {
        throw new UnsupportedOperationException("Please provide implementation");
    }

    public static String dashRow() {
        throw new UnsupportedOperationException("Please provide implementation");
    }

    public static Stream<String> dashGen() {
        throw new UnsupportedOperationException("Please provide implementation");
    }

    public static String titleRow() {
        return Stream.of(String.format("%-20s", "Name"), String.format("%10s", "Height"),
                String.format("%10s", "Prominence")).collect(rowCollector());
    }

    public static String bodyRow(Peak peak) {
        throw new UnsupportedOperationException("Please provide implementation");
    }

    static Collector<CharSequence, ?, String> rowCollector() {
        return Collectors.joining(" " + PIPE + " ", PIPE + " ", " " + PIPE);
    }

}
