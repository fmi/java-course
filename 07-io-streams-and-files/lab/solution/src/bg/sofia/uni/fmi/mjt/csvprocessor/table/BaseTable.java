package bg.sofia.uni.fmi.mjt.csvprocessor.table;

import bg.sofia.uni.fmi.mjt.csvprocessor.exceptions.CsvDataNotCorrectException;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.column.BaseColumn;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.column.Column;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class BaseTable implements Table {

    private final Map<String, Column> columns;
    private int rowSize;

    public BaseTable() {
        this(new LinkedHashMap<>());
    }

    BaseTable(Map<String, Column> columns) {
        this.columns = columns;
    }

    public void addData(String[] data) throws CsvDataNotCorrectException {
        if (columns.isEmpty()) {
            addColumnNames(data);
        } else {
            addColumnData(data);
        }

        this.rowSize++;
    }

    private void addColumnNames(String[] columnNames) {
        for (String columnName : columnNames) {
            columns.put(columnName, null);
        }
    }

    private void addColumnData(String[] dataParts) throws CsvDataNotCorrectException {
        Iterator<String> columnNamesIterator = columns.keySet().iterator();

        for (String columnData : dataParts) {
            if (!columnNamesIterator.hasNext()) {
                throw new CsvDataNotCorrectException("A row from the given csv file has more data than columns");
            }

            String columnName = columnNamesIterator.next();
            Column column = columns.get(columnName);

            if (column == null) {
                column = new BaseColumn();
                columns.put(columnName, column);
            }

            column.addData(columnData);
        }

        if (columnNamesIterator.hasNext()) {
            throw new CsvDataNotCorrectException("A row from the given csv file has less data than columns");
        }
    }

    public Collection<String> getColumnNames() {
        return Collections.unmodifiableSet(columns.keySet());
    }

    public Collection<String> getColumnData(String column) {
        if (!columns.containsKey(column)) {
            throw new IllegalArgumentException("There is no column under the name %s in the table".formatted(column));
        }

        var columnObj = columns.get(column);

        if (columnObj == null) {
            return Collections.emptyList();
        }

        return columns.get(column).getData();
    }

    public int getRowsCount() {
        return rowSize;
    }
}
