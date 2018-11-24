package bg.sofia.uni.fmi.mjt.streams;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class SystemStreams {

    public static void main(String[] args) throws IOException {
        File file = new File("system-out.txt");
        PrintStream fileStream = new PrintStream(file);
        System.setOut(fileStream);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        System.out.println(line);
    }

}
