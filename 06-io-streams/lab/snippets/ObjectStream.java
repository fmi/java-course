package bg.sofia.uni.fmi.mjt.streams;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectStream {

    public static void main(String[] args) {
        File file = new File("students.bin");
        Student firstStudent = new Student("Gosho", 20);
        Student secondStudent = new Student("Stamat", 80);

        writeStudentsToFile(file, firstStudent, secondStudent);
        readStudentsFromFile(file);
    }

    private static void writeStudentsToFile(File file, Student... students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            for (Student student : students) {
                oos.writeObject(student);
                oos.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void readStudentsFromFile(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

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
