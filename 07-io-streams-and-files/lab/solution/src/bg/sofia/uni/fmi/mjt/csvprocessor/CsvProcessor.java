package bg.sofia.uni.fmi.mjt.csvprocessor;

import bg.sofia.uni.fmi.mjt.csvprocessor.exceptions.CsvDataNotCorrectException;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.BaseTable;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.Table;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.printer.ColumnAlignment;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.printer.MarkdownTablePrinter;
import bg.sofia.uni.fmi.mjt.csvprocessor.table.printer.TablePrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

public class CsvProcessor implements CsvProcessorAPI {

    private final Table table;

    public CsvProcessor() {
        this(new BaseTable());
    }

    CsvProcessor(Table table) {
        this.table = table;
    }

    @Override
    public void readCsv(Reader reader, String delimiter) throws CsvDataNotCorrectException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        delimiter = "\\Q" + delimiter + "\\E";
        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(delimiter);
                table.addData(data);
            }
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while processing the given csv file", e);
        }
    }

    @Override
    public void writeTable(Writer writer, ColumnAlignment... alignments) {
        PrintWriter printWriter = new PrintWriter(writer);

        TablePrinter tablePrinter = new MarkdownTablePrinter();
        Collection<String> tableRows = tablePrinter.printTable(table, alignments);

        Iterator<String> iterator = tableRows.iterator();

        while (iterator.hasNext()) {
            String row = iterator.next();

            if (iterator.hasNext()) {
                printWriter.println(row);
            } else {
                printWriter.print(row);
            }
        }
    }
}
