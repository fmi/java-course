package bg.sofia.uni.fmi.mjt.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WritingAndReadingFromFile {

    public static void main(String... args) {

        Path file = Path.of("writingAndReadingFromFile.txt");
        String text = "Write this string to my file" + System.lineSeparator();

        writeToFile(file, text);
        readFromFile(file);
    }

    private static void writeToFile(Path file, String text) {

        try (var fw = Files.newBufferedWriter(file)) {
            fw.write(text);
            fw.flush();

        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    private static void readFromFile(Path file) {
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
