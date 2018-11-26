package bg.sofia.uni.fmi.mjt.streams;

import java.io.*;

public class ExecutionTimeCompare {

    public static void main(String... args) {
        File file = new File("compareExecutionTime.txt");
        String line = new String("Hello World");

        fillFileWithText(file, line);

        System.out.println("Single byte read: " + readFromFile(file));
        System.out.println("BufferedReader read: " + readFromFileWithBufferedReader(file));
        System.out.println("BufferedInputStream read: " + readFromFileWithBufferedInputStream(file));

    }

    private static long readFromFile(File file) {
        try (FileReader fr = new FileReader(file)) {

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

    private static long readFromFileWithBufferedReader(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

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

    private static long readFromFileWithBufferedInputStream(File file) {
        try (InputStream is = new FileInputStream(file)) {
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

    private static void fillFileWithText(File file, String line) {

        try (FileWriter fw = new FileWriter(file)) {
            for (int i = 0; i <= 10_000_000; i++) {
                fw.write(line);
            }

            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}