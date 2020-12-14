package bg.sofia.uni.fmi.mjt.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExecutionTimeComparator {

    public static void main(String... args) throws IOException {
        System.err.println("Read time comparison results:");

        Path filePath = Path.of("compareExecutionTime.txt");
        String line = "Hello World";

        fillFileWithText(filePath, line);

        System.out.println("Single byte read took: " + readFromFile(filePath) + " milliseconds");
        System.out.println("BufferedReader read took: " + readFromFileWithBufferedReader(filePath) + " milliseconds");
        System.out.println("BufferedInputStream read took: " + readFromFileWithBufferedInputStream(filePath) + " milliseconds");
    }

    private static void fillFileWithText(Path file, String line) {
        try (Writer fileWriter = Files.newBufferedWriter(file)) {
            for (int i = 0; i <= 1_000_000; i++) {
                fileWriter.write(line);
            }

            fileWriter.flush();

            long writtenBytesLength = line.getBytes().length * 1_000_000;
            long writtenMegabytesLength = writtenBytesLength / 1_000_000;
            System.out.println("Wrote " + writtenBytesLength + " bytes (" + writtenMegabytesLength + " megabytes) to the file");
        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred while writing to a file", e);
        }
    }

    private static long readFromFile(Path file) throws IOException {
        try (InputStream inputStream = Files.newInputStream(file)) {

            long startTime = System.nanoTime();
            // System.err.println("Start time is: " + startTime);
            while (inputStream.read() != -1) {
                // Do some processing
            }
            long endTime = System.nanoTime();
            // System.err.println("End time is: " + endTime);

            return (endTime - startTime) / 1_000_000; // milliseconds
        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred while reading from a file", e);
        }
    }

    private static long readFromFileWithBufferedReader(Path file) throws IOException {
        try (BufferedReader bufferedReader = Files.newBufferedReader(file)) {

            long startTime = System.nanoTime();
            while (bufferedReader.readLine() != null) {
                // Do some processing
            }
            long endTime = System.nanoTime();

            return (endTime - startTime) / 1_000_000;
        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred while reading from a file", e);
        }
    }

    private static long readFromFileWithBufferedInputStream(Path file) {
        try (InputStream inputStream = Files.newInputStream(file)) {
            byte[] buff = new byte[4]; // 2K bytes buffer
            int r = 0;

            long startTime = System.nanoTime();
            while ((r = inputStream.read(buff)) != -1) {
                // Do some processing
            }
            long endTime = System.nanoTime();

            return (endTime - startTime) / 1_000_000;
        } catch (Exception e) {
            throw new IllegalStateException("A problem occurred while reading from a file", e);
        }
    }

}
