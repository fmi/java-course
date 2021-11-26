package bg.sofia.uni.fmi.mjt.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class SystemStreams {

    public static void main(String... args) throws IOException {
        Path fileOut = Path.of("system-out.txt");
        Path fileIn = Path.of("system-in.txt");

        redirectSystemOut(fileOut);
        String stringFromSystemIn = readFromSystemIn();
        System.out.println(stringFromSystemIn);

        redirectSystemInFrom(fileIn);
        stringFromSystemIn = readFromSystemIn();
        System.out.println(stringFromSystemIn);
    }

    public static void redirectSystemOut(Path file) throws IOException {
        PrintStream fileStream = new PrintStream(Files.newOutputStream(file));
        System.setOut(fileStream);
    }

    public static void redirectSystemInFrom(Path file) throws IOException {
        System.setIn(Files.newInputStream(file));
    }

    public static String readFromSystemIn() throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }
}
