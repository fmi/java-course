import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class TempFileTest {

    @Test
    void tempFileExample(@TempDir Path tempDir) throws Exception {

        System.out.println("Auto created temporary directory: " + tempDir);

        // Създаваме файл във временната директория
        // с Files.createFile(...) или Path.resolve(...)
        Path file = tempDir.resolve("sample.txt");

        Files.writeString(file, "Hello from JUnit 6!");

        String text = Files.readString(file);

        assertEquals("Hello from JUnit 6!", text, "Unexpected file content");

        // Временната директория ще се изттрие автоматично след теста,
        // заедно със съдържанието си.
    }

}
