package bg.sofia.uni.fmi.mjt.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WritingToAndReadingFromFile {

    public static void main(String... args) {
        Path filePath = Path.of("writingAndReadingFromFile.txt");
        String text = "Write this string to my file" + System.lineSeparator();

        writeToFile(filePath, text);
        readFromFile(filePath);
    }

    private static void writeToFile(Path filePath, String text) {
        try (var bufferedWriter = Files.newBufferedWriter(filePath)) {
            bufferedWriter.write(text);
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred while writing to a file", e);
        }
    }

    private static void readFromFile(Path filePath) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred while reading from a file", e);
        }
    }

}
