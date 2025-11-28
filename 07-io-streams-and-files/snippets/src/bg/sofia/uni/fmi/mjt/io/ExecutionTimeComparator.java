package bg.sofia.uni.fmi.mjt.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExecutionTimeComparator {

    private static final int BUFFER_SIZE = 8_192;
    private static final int FILE_SIZE_MB = 20;

    static void main() throws Exception {

        Path filePath = Path.of("compareExecutionTime.txt");
        generateTestFile(filePath);

        System.out.println("=== Read Performance Comparison ===\n");

        measureSingleByteRead(filePath);
        measureBufferedReaderRead(filePath);
        measureBufferedChunkRead(filePath);

    }

    private static void generateTestFile(Path path) throws IOException {
        System.out.println("Generating test file...");

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            String line = "Hello World" + System.lineSeparator(); // will removing this newline change anything?
            long targetBytes = FILE_SIZE_MB * 1024L * 1024L;
            long written = 0;

            while (written < targetBytes) {
                writer.write(line);
                written += line.getBytes(StandardCharsets.UTF_8).length;
            }

            System.out.printf("Created %,d MB test file%n%n", FILE_SIZE_MB);
        }
    }

    private static void measureSingleByteRead(Path file) throws Exception {
        // Warm-up
        readSingleBytes(file);

        long start = System.nanoTime();
        long bytesRead = readSingleBytes(file);
        long end = System.nanoTime();

        printResult("Single byte read", start, end, bytesRead);
    }

    private static void measureBufferedChunkRead(Path file) throws Exception {
        // Warm-up
        readBufferedChunks(file);

        long start = System.nanoTime();
        long bytesRead = readBufferedChunks(file);
        long end = System.nanoTime();

        printResult("BufferedInputStream (8 KB chunks)", start, end, bytesRead);
    }

    private static void measureBufferedReaderRead(Path file) throws Exception {
        // Warm-up
        readLines(file);

        long start = System.nanoTime();
        long bytesRead = readLines(file);
        long end = System.nanoTime();

        printResult("BufferedReader.readLine()", start, end, bytesRead);
    }

    private static long readSingleBytes(Path file) throws IOException {
        long count = 0;

        try (InputStream is = Files.newInputStream(file)) {
            int b;
            while ((b = is.read()) != -1) {
                count++;
            }
        }

        return count;
    }

    private static long readLines(Path file) throws IOException {
        long count = 0;

        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;

            while ((line = br.readLine()) != null) {
                count += line.length();
            }
        }

        return count;
    }

    private static long readBufferedChunks(Path file) throws IOException {
        long count = 0;
        byte[] buffer = new byte[BUFFER_SIZE];

        try (BufferedInputStream bis =
                 new BufferedInputStream(Files.newInputStream(file), BUFFER_SIZE)) {

            int r;
            while ((r = bis.read(buffer)) != -1) {
                count += r;
            }
        }

        return count;
    }

    private static void printResult(String label, long start, long end, long bytesRead) {
        double ms = (end - start) / 1_000_000.0;
        double mb = bytesRead / (1024.0 * 1024.0);
        double throughput = mb / (ms / 1000.0);

        System.out.printf("%-35s: %.2f ms  |  %.2f MB/s%n",
            label, ms, throughput);
    }
}
