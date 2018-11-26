package bg.sofia.uni.fmi.mjt.streams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WritingAndReadingFromFile {

    public static void main(String[] args) {

        File file = new File("writingAndReadingFromFile.txt");
        String text = "Write this string to my file\n";

        writeToFile(file, text);
        readFromFile(file);
    }

    private static void writeToFile(File file, String text) {

        try (FileWriter fw = new FileWriter(file)) {
            fw.write(text);
            fw.flush();

        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    private static void readFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
