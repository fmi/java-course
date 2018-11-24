package bg.sofia.uni.fmi.mjt.streams;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DataStream {

    public static void main(String... args) {
        writeWithDataStream();
        readDataStream();
    }

    /**
     * Reads structured data using DataInputStream
     * https://docs.oracle.com/javase/8/docs/api/java/io/DataInputStream.html
     */
    private static void readDataStream() {
        try (DataInputStream is = new DataInputStream(new FileInputStream("test.dat"))) {
            System.out.println("int: " + is.readInt());
            System.out.println("float: " + is.readFloat());
            System.out.println("string: " + is.readUTF());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes structured data using DataOutputStream
     * https://docs.oracle.com/javase/8/docs/api/java/io/DataOutputStream.html
     */
    private static void writeWithDataStream() {
        try (DataOutputStream os = new DataOutputStream(
                new FileOutputStream("test.dat"))) {
            os.writeInt(10);
            os.writeFloat(5.2f);
            os.writeUTF("potato");
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
