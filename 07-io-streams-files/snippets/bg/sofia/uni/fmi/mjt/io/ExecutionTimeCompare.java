package bg.sofia.uni.fmi.mjt.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExecutionTimeCompare {

    public static void main(String... args) throws IOException {
        Path file = Path.of("compareExecutionTime.txt");
        String line = "Hello World";

        fillFileWithText(file, line);

        System.out.println("Single byte read: " + readFromFile(file));
        System.out.println("BufferedReader read: " + readFromFileWithBufferedReader(file));
        System.out.println("BufferedInputStream read: " + readFromFileWithBufferedInputStream(file));

    }

    private static long readFromFile(Path file) throws IOException {
        try (InputStream fr = Files.newInputStream(file)) {

            long startTime = System.nanoTime();
            while (fr.read() != -1) {
                // Do some processing
            }
            long endTime = System.nanoTime();

            return (endTime - startTime) / 1_000_000; // milliseconds
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static long readFromFileWithBufferedReader(Path file) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(file)) {

            long startTime = System.nanoTime();
            while (br.readLine() != null) {
                // Do some processing
            }
            long endTime = System.nanoTime();

            return (endTime - startTime) / 1_000_000;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static long readFromFileWithBufferedInputStream(Path file) {
        try (InputStream is = Files.newInputStream(file)) {
            byte[] buff = new byte[2048]; // 2K bytes buffer
            int r = 0;

            long startTime = System.nanoTime();
            while ((r = is.read(buff)) != -1) {
                // Do some processing
            }
            long endTime = System.nanoTime();

            return (endTime - startTime) / 1_000_000;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void fillFileWithText(Path file, String line) {
        try (Writer fw = Files.newBufferedWriter(file)) {
            for (int i = 0; i <= 1_000_000; i++) {
                fw.write(line);
            }

            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
