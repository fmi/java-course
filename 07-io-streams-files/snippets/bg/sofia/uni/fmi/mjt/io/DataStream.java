package bg.sofia.uni.fmi.mjt.io;

import java.io.*;

public class DataStream {

    public static void main(String... args) throws IOException {
        writeWithDataStream();
        readDataStream();
    }

    /**
     * Reads structured data using DataInputStream
     * https://docs.oracle.com/en/java/javase/13/docs/api/java.base/java/io/DataInputStream.html
     */
    private static void readDataStream() throws IOException {
        try (var is = new DataInputStream(new FileInputStream("test.dat"))) {
            System.out.println("int: " + is.readInt());
            System.out.println("float: " + is.readFloat());
            System.out.println("string: " + is.readUTF());
        }
    }

    /**
     * Writes structured data using DataOutputStream
     * https://docs.oracle.com/javase/8/docs/api/java/io/DataOutputStream.html
     */
    private static void writeWithDataStream() throws IOException {
        try (var os = new DataOutputStream(
                new FileOutputStream("test.dat"))) {
            os.writeInt(10);
            os.writeFloat(5.2f);
            os.writeUTF("potato");
            os.flush();
        }
    }

}
