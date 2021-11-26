package bg.sofia.uni.fmi.mjt.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TextFileDuplicator {

    // copy a text file, exactly replicating the source file's line breaks
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
        copyTextFileExact("source.txt", "destination.txt");
    }
}
