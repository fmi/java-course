package bg.sofia.uni.fmi.mjt.csvprocessor.table.printer;

public enum ColumnAlignment {
    LEFT(1),

    CENTER(2),

    RIGHT(1),

    NOALIGNMENT(0);

    private final int alignmentCharactersCount;

    ColumnAlignment(int count) {
        this.alignmentCharactersCount = count;
    }

    public int getAlignmentCharactersCount() {
        return alignmentCharactersCount;
    }
}