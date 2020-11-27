package bg.sofia.uni.fmi.mjt.io;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ObjectStream {

    public static void main(String... args) {
        Path file = Path.of("students.bin");
        Student firstStudent = new Student("Gosho", 20);
        Student secondStudent = new Student("Stamat", 80);

        writeStudentsToFile(file, firstStudent, secondStudent);
        readStudentsFromFile(file);
    }

    private static void writeStudentsToFile(Path file, Student... students) {
        try (var oos = new ObjectOutputStream(Files.newOutputStream(file))) {
            for (Student student : students) {
                oos.writeObject(student);
                oos.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void readStudentsFromFile(Path file) {
        try (var ois = new ObjectInputStream(Files.newInputStream(file))) {
            while (true) {
                Object studentObject = ois.readObject();
                System.out.println(studentObject);
            }
        } catch (EOFException e) {
            // EMPTY BODY
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
