package bg.sofia.uni.fmi.mjt.space.algorithm;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import java.io.InputStream;
import java.io.OutputStream;

public class Rijndael implements SymmetricBlockCipher {
    private static final int KILOBYTE = 1024;
    private static final String ENCRYPTION_ALGORITHM = "AES";

    private final SecretKey secretKey;

    public Rijndael(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public void encrypt(InputStream inputStream, OutputStream outputStream) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        try (var encryptedOutputStream = new CipherOutputStream(outputStream, cipher)) {
            int bytesRead;

            while ((bytesRead = inputStream.read()) > 0) {
                encryptedOutputStream.write(bytesRead);
            }
        }
    }

    @Override
    public void decrypt(InputStream inputStream, OutputStream outputStream) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        try (OutputStream decryptedOutputStream = new CipherOutputStream(outputStream, cipher)) {
            byte[] buffer = new byte[KILOBYTE];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                decryptedOutputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}