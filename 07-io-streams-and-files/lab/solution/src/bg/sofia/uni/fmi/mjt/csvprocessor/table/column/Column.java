package bg.sofia.uni.fmi.mjt.csvprocessor.table.column;

import java.util.Collection;

public interface Column {

    /**
     * Adds new data string to the column
     * @param data - the string to be added
     */
    void addData(String data);

    /**
     * Returns an unmodifiable collection of all data stored in the column
     */
    Collection<String> getData();
}
