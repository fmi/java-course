package bg.sofia.uni.fmi.mjt.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipExplorer {

    static void showZipContents(String zipFilePath) {
        try (var zf = new ZipFile(zipFilePath)) {

            System.out.printf("Inspecting contents of: %s\n%n", zf.getName());

            for (var it = zf.entries().asIterator(); it.hasNext(); ) {
                ZipEntry entry = it.next();
                System.out.printf(
                        "Item: %s \nType: %s \nSize: %d\n%n",
                        entry.getName(),
                        entry.isDirectory() ? "directory" : "file",
                        entry.getSize()
                );
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void initialize(Path inputsDir) throws IOException {
        deleteDirectoryRecursively(inputsDir);
        Files.createDirectory(inputsDir);
    }

    static void deleteDirectoryRecursively(Path pathToDelete) throws IOException {
        if (Files.exists(pathToDelete)) {
            File[] allContents = pathToDelete.toFile().listFiles();
            if (allContents != null) {
                for (File file : allContents) {
                    deleteDirectoryRecursively(file.toPath());
                }
            }
            Files.delete(pathToDelete);
        }
    }

    static void unzipAZip(String zipFilePath, String toPath) {
        var outputPath = Path.of(toPath);

        try (var zf = new ZipFile(zipFilePath)) {

            // Delete if exists, then create a fresh empty directory to put the zip archive contents
            initialize(outputPath);

            for (var it = zf.entries().asIterator(); it.hasNext(); ) {
                ZipEntry entry = it.next();
                try {
                    if (entry.isDirectory()) {
                        var dirToCreate = outputPath.resolve(entry.getName());
                        Files.createDirectories(dirToCreate);
                    } else {
                        var fileToCreate = outputPath.resolve(entry.getName());
                        Files.copy(zf.getInputStream(entry), fileToCreate);
                    }
                } catch (IOException ei) {
                    ei.printStackTrace();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void zipSomeStrings(String zipFilePath) {
        Map<String, String> stringsToZip = Map.ofEntries(
                Map.entry("file1", "This is the first file"),
                Map.entry("file2", "This is the second file"),
                Map.entry("file3", "This is the third file")
        );
        var zipPath = Path.of(zipFilePath);
        try (var zos = new ZipOutputStream(
                new BufferedOutputStream(Files.newOutputStream(Path.of(zipFilePath))))) {
            for (var entry : stringsToZip.entrySet()) {
                zos.putNextEntry(new ZipEntry(entry.getKey()));
                zos.write(entry.getValue().getBytes());
                zos.closeEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void zipAFile(String filePath, String zipFilePath) {
        var fileToBeZipped = Path.of(filePath);

        try (ZipOutputStream zippedOutput = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            zippedOutput.putNextEntry(new ZipEntry(fileToBeZipped.getFileName().toString()));

            byte[] bytes = Files.readAllBytes(fileToBeZipped);
            zippedOutput.write(bytes, 0, bytes.length);
            zippedOutput.closeEntry();
        } catch (FileNotFoundException e) {
            System.err.format("The file %s does not exist", filePath);
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("I/O error: " + e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        zipSomeStrings("zippedStrings.zip");
        zipAFile("students.bin", "zippedStudents.zip");
        showZipContents("src.zip");
        unzipAZip("src.zip", "unzipped");
        deleteDirectoryRecursively(Path.of("unzipped"));
    }

}
