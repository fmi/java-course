package bg.sofia.uni.fmi.mjt.csvprocessor.table.printer;

import bg.sofia.uni.fmi.mjt.csvprocessor.table.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MarkdownTablePrinter implements TablePrinter {

    private static final String COLUMN_DELIMITER = "|";
    private static final String COLUMN_NAME_DELIMITER = "-";
    private static final String WHITESPACE = " ";

    private static final int MIN_COLUMN_LENGTH = 3;

    @Override
    public Collection<String> printTable(Table table, ColumnAlignment... alignments) {
        List<StringBuilder> rows = initializeRowBulderList(table.getRowsCount() + 1);
        Map<String, Integer> maxLengths = getColumnsMaxLength(table);
        int counter = 0;
        int alignmentCounter = 0;

        for (String columnName : table.getColumnNames()) {
            int maxLengthForCurrentColumn = maxLengths.get(columnName);
            StringBuilder row = rows.get(counter);

            appendContentToRow(columnName, maxLengthForCurrentColumn, row);

            ColumnAlignment alignment = null;

            if (alignments.length > alignmentCounter) {
                alignment = alignments[alignmentCounter];
                alignmentCounter++;
            }

            counter++;
            row = rows.get(counter);

            var alCount = 0;

            if (alignment != null) {
                alCount = alignment.getAlignmentCharactersCount();
            }

            String columnDelimiter = appendAlignment(
                    COLUMN_NAME_DELIMITER.repeat(maxLengthForCurrentColumn - alCount),
                    alignment);

            appendContentToRow(columnDelimiter, maxLengthForCurrentColumn, row);

            counter++;

            for (String data : table.getColumnData(columnName)) {
                row = rows.get(counter);
                appendContentToRow(data, maxLengthForCurrentColumn, row);

                counter++;
            }

            counter = 0;
        }

        appendEndOfTableToOutput(rows);

        return convertBuilderListToStringList(rows);
    }

    private String appendAlignment(String s, ColumnAlignment columnAlignment) {
        return switch (columnAlignment) {
            case null -> s;
            case LEFT -> ":" + s + " ";
            case CENTER -> ":" + s + ": ";
            case RIGHT -> s + ": ";
            case NOALIGNMENT -> s;
        };
    }

    private List<StringBuilder> initializeRowBulderList(int rowCount) {
        List<StringBuilder> rowBuilderList = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            rowBuilderList.add(new StringBuilder());
        }

        return rowBuilderList;
    }

    private Map<String, Integer> getColumnsMaxLength(Table table) {
        Map<String, Integer> columnsMaxLength = new HashMap<>();

        for (String columnName : table.getColumnNames()) {
            columnsMaxLength.put(columnName, Math.max(getColumnMaxLength(columnName, table), MIN_COLUMN_LENGTH));
        }

        return columnsMaxLength;
    }

    private int getColumnMaxLength(String column, Table table) {
        Collection<String> columnData = table.getColumnData(column);

        int maxLength = column.length();
        for (String data : columnData) {
            maxLength = Math.max(maxLength, data.length());
        }

        return maxLength;
    }

    private void appendContentToRow(String value, int maxLength, StringBuilder row) {
        int fillingWhitespacesCount = Math.max(maxLength - value.length() + 1, 0);
        row.append(COLUMN_DELIMITER).append(WHITESPACE).append(value)
                .append(WHITESPACE.repeat(fillingWhitespacesCount));
    }

    private void appendEndOfTableToOutput(List<StringBuilder> rows) {
        for (StringBuilder row : rows) {
            row.append(COLUMN_DELIMITER);
        }
    }

    private List<String> convertBuilderListToStringList(List<StringBuilder> builderList) {
        List<String> stringList = new LinkedList<>();

        for (StringBuilder row : builderList) {
            stringList.add(row.toString());
        }

        return stringList;
    }
}
