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
        Path filePath = Path.of("students.bin");
        Student firstStudent = new Student("Gosho", 20);
        Student secondStudent = new Student("Stamat", 80);

        writeStudentsToFile(filePath, firstStudent, secondStudent);
        readStudentsFromFile(filePath);
    }

    private static void writeStudentsToFile(Path file, Student... students) {
        try (var objectOutputStream = new ObjectOutputStream(Files.newOutputStream(file))) {
            for (Student student : students) {
                objectOutputStream.writeObject(student);
                objectOutputStream.flush();
            }
        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred while writing to a file", e);
        }
    }

    private static void readStudentsFromFile(Path file) {
        try (var objectInputStream = new ObjectInputStream(Files.newInputStream(file))) {

            Object studentObject;
            while ((studentObject = objectInputStream.readObject()) != null) {
                System.out.println(studentObject);
                
                Student s = (Student)studentObject;
                System.out.println("Name " +  s.name());
            }
        } catch (EOFException e) {
            // EMPTY BODY
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("The files does not exist", e);
        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred while reading from a file", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
