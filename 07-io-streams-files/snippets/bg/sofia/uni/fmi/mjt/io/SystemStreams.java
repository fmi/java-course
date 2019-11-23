package bg.sofia.uni.fmi.mjt.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class SystemStreams {

    public static void main(String... args) throws IOException {
        Path file = Path.of("system-out.txt");
        PrintStream fileStream = new PrintStream(Files.newOutputStream(file));
        System.setOut(fileStream);

        var br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        System.out.println(line);
    }

}
