package bg.sofia.uni.fmi.mjt.csvprocessor;

import bg.sofia.uni.fmi.mjt.csvprocessor.exceptions.CsvDataNotCorrectException;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.printer.ColumnAlignment;

import java.io.Reader;
import java.io.Writer;

public interface CsvProcessorAPI {

    /**
     * Reads a CSV data from Reader
     * @param reader the Reader from which the CSV will be read
     * @param delimiter the delimeter used to split the CSV (such as ,.- and so on)
     * @throws CsvDataNotCorrectException if the CSV data is in wrong format
     */
    void readCsv(Reader reader, String delimiter) throws CsvDataNotCorrectException;

    /**
     * Writes the content of the table to the provided writer
     * @param writer - the writer to which the table will be written
     */
    void writeTable(Writer writer, ColumnAlignment... alignments);

}
