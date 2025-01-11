package bg.sofia.uni.fmi.mjt.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class ExecutionTimeComparator {

    private static final int MAX_ITERATIONS = 1_000_000;
    private static final int BUFFER_SIZE = 8_192;
    private static final double MEGABYTE = 1_024.0 * 1_024.0;

    public static void main(String... args) {
        System.err.println("Read time comparison results:");

        Path filePath = Path.of("compareExecutionTime.txt");
        String line = "Hello World";

        fillFileWithText(filePath, line);

        System.out.println("Single byte read took: "
            + readFromFile(filePath) + " milliseconds");
        System.out.println("BufferedReader read took: "
            + readFromFileWithBufferedReader(filePath) + " milliseconds");
        System.out.println("BufferedInputStream read took: "
            + readFromFileWithBufferedInputStream(filePath) + " milliseconds");
    }

    private static void fillFileWithText(Path file, String line) {
        try (Writer fileWriter = Files.newBufferedWriter(file)) {
            for (int i = 0; i <= MAX_ITERATIONS; i++) {
                fileWriter.write(line);
            }

            fileWriter.flush();

            long writtenBytes = line.getBytes().length * MAX_ITERATIONS;
            double writtenMegabytes = writtenBytes / MEGABYTE;
            System.out.println(String.format(
                Locale.US, "Wrote %,d bytes (%.2f MB) to the file", writtenBytes, writtenMegabytes));
        } catch (IOException e) {
            throw new UncheckedIOException("A problem occurred while writing to a file", e);
        }
    }

    private static long readFromFile(Path file) {
        try (InputStream inputStream = Files.newInputStream(file)) {

            long startTime = System.nanoTime();
            int data;
            while ((data = inputStream.read()) != -1) {
                // Do some processing
            }
            long endTime = System.nanoTime();

            return (endTime - startTime) / MAX_ITERATIONS; // milliseconds
        } catch (IOException e) {
            throw new UncheckedIOException("A problem occurred while reading from a file", e);
        }
    }

    private static long readFromFileWithBufferedReader(Path file) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(file)) { // default buffer size is 8192 chars

            long startTime = System.nanoTime();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Do some processing
            }
            long endTime = System.nanoTime();

            return (endTime - startTime) / MAX_ITERATIONS;
        } catch (IOException e) {
            throw new UncheckedIOException("A problem occurred while reading from a file", e);
        }
    }

    private static long readFromFileWithBufferedInputStream(Path file) {
        try (InputStream inputStream = Files.newInputStream(file)) {
            byte[] buff = new byte[BUFFER_SIZE]; // 8 KB buffer
            int r = 0;

            long startTime = System.nanoTime();
            while ((r = inputStream.read(buff)) != -1) {
                // Do some processing
            }
            long endTime = System.nanoTime();

            return (endTime - startTime) / MAX_ITERATIONS;
        } catch (IOException e) {
            throw new UncheckedIOException("A problem occurred while reading from a file", e);
        }
    }

}
