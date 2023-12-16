package bg.sofia.uni.fmi.mjt.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TextFileDuplicator {

    // Copy a text file, exactly replicating the source file's line breaks, including a potential trailing newline
    // at the end of the last line in the file.
    //     Option 1: use Files.copy()
    //     Option 2: use BufferedReader and BufferedWriter and copy the file line by line.
    //               Note that The readLine() method from BufferedReader does not retain newline characters,
    //               it strips them away.
    //               Therefore, there isn't a direct way to determine if a line ended with a newline or not
    //               by just using readLine().
    //     Option 3: use Scanner and its exotic findWithinHorizon() method

    public static void copyTextFileSimple(String fromFileName, String toFileName) throws IOException {
        Files.copy(Path.of(fromFileName), Path.of(toFileName), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void copyTextFileNaive(String fromFileName, String toFileName) throws IOException {
        try (var reader = new BufferedReader(new FileReader(fromFileName));
             var writer = new BufferedWriter(new FileWriter(toFileName))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        }
    }

    public static void copyTextFileExact(String fromFileName, String toFileName) throws IOException {
        Pattern pat = Pattern.compile(".*\\R|.+\\z");

        try (var scanner = new Scanner(new BufferedReader(new FileReader(fromFileName)));
             var writer = new BufferedWriter((new FileWriter(toFileName)))) {

            String line;
            while ((line = scanner.findWithinHorizon(pat, 0)) != null) {
                writer.write(line);
                writer.flush();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        copyTextFileSimple("source.txt", "destination.txt");
        //copyTextFileNaive("source.txt", "destination.txt");
        //copyTextFileExact("source.txt", "destination.txt");
    }

}
