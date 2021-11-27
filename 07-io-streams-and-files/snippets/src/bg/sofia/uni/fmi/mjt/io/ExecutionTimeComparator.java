package bg.sofia.uni.fmi.mjt.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExecutionTimeComparator {

    private static final int MAX_ITERATIONS = 1_000_000;
    private static final int BUFFER_SIZE = 8192;

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

            long writtenBytesLength = line.getBytes().length * MAX_ITERATIONS;
            long writtenMegabytesLength = writtenBytesLength / MAX_ITERATIONS;
            System.out.println("Wrote " + writtenBytesLength + " bytes (" + writtenMegabytesLength + " megabytes)" +
                    " to the file");
        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred while writing to a file", e);
        }
    }

    private static long readFromFile(Path file) {
        try (InputStream inputStream = Files.newInputStream(file)) {

            long startTime = System.nanoTime();
            // System.err.println("Start time is: " + startTime);
            while (inputStream.read() != -1) {
                // Do some processing
            }
            long endTime = System.nanoTime();
            // System.err.println("End time is: " + endTime);

            return (endTime - startTime) / MAX_ITERATIONS; // milliseconds
        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred while reading from a file", e);
        }
    }

    private static long readFromFileWithBufferedReader(Path file) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(file)) {

            long startTime = System.nanoTime();
            while (bufferedReader.readLine() != null) {
                // Do some processing
            }
            long endTime = System.nanoTime();

            return (endTime - startTime) / MAX_ITERATIONS;
        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred while reading from a file", e);
        }
    }

    private static long readFromFileWithBufferedInputStream(Path file) {
        try (InputStream inputStream = Files.newInputStream(file)) {
            byte[] buff = new byte[BUFFER_SIZE]; // 8K bytes buffer
            int r = 0;

            long startTime = System.nanoTime();
            while ((r = inputStream.read(buff)) != -1) {
                // Do some processing
            }
            long endTime = System.nanoTime();

            return (endTime - startTime) / MAX_ITERATIONS;
        } catch (Exception e) {
            throw new IllegalStateException("A problem occurred while reading from a file", e);
        }
    }

}
