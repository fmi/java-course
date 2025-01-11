package bg.sofia.uni.fmi.mjt.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CipherExample {

    private static final int KILOBYTE = 1024;
    private static final String ENCRYPTION_ALGORITHM = "AES"; // //  Advanced Encryption Standard
    private static final String KEY_FILE_PATH = "secret.key";
    private static final int KEY_SIZE_IN_BITS = 128; // Key sizes like 192 or 256 might not be available on all systems

    public static void main(String[] args) {
        try {
            // Generate
            SecretKey secretKey = generateSecretKey();

            // uncomment to store the key in file
            //persistSecretKey(secretKey);

            // uncomment to load the key from file
            //secretKey = loadSecretKey();

            // Encrypt
            encryptData(secretKey);

            // Decrypt
            decryptData(secretKey);

        } catch (Exception e) {
            throw new RuntimeException("Exception caught during the execution", e);
        }
    }

    private static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
        keyGenerator.init(KEY_SIZE_IN_BITS);
        SecretKey secretKey = keyGenerator.generateKey();

        // In order to view the key in some text representation, we'll convert it to Base64 format
        // Comment the next two lines if that's not needed
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Generated Secret Key (Base64-encoded): " + base64Key);

        return secretKey;
    }

    private static void persistSecretKey(SecretKey secretKey) throws IOException {
        byte[] keyBytes = secretKey.getEncoded();
        Path keyFilePath = Path.of(KEY_FILE_PATH);

        // Write key bytes to file
        Files.write(keyFilePath, keyBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        // (Optional) Set file permissions to restrict access
        Files.setPosixFilePermissions(keyFilePath, PosixFilePermissions.fromString("rw-------"));
    }

    private static SecretKey loadSecretKey() throws IOException {
        byte[] keyBytes = Files.readAllBytes(Path.of(KEY_FILE_PATH));
        return new SecretKeySpec(keyBytes, ENCRYPTION_ALGORITHM);
    }

    private static void encryptData(SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        try (var outputStream = new CipherOutputStream(new FileOutputStream("encryptedText.txt"), cipher)) {
            List<String> data = List.of("my-", "secret-", "pass–", "and–", "other–", "stuff");

            for (String str : data) {
                byte[] dataBytes = str.getBytes(StandardCharsets.UTF_8);
                outputStream.write(dataBytes);
            }

            System.out.println("Encryption complete.");
        }
    }

    private static void decryptData(SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        try (InputStream encryptedInputStream = new FileInputStream("encryptedText.txt");
             OutputStream decryptedOutputStream = new CipherOutputStream(new FileOutputStream("decryptedText.txt"),
                 cipher)) {

            byte[] buffer = new byte[KILOBYTE];
            int bytesRead;

            while ((bytesRead = encryptedInputStream.read(buffer)) != -1) {
                decryptedOutputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("Decryption complete.");
        }
    }
}
