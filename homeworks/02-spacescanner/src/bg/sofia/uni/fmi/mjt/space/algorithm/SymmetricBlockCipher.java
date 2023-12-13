package bg.sofia.uni.fmi.mjt.space.algorithm;

import java.io.InputStream;
import java.io.OutputStream;

public interface SymmetricBlockCipher {
    void encrypt(InputStream inputStream, OutputStream outputStream) throws Exception;

    void decrypt(InputStream inputStream, OutputStream outputStream) throws Exception;
}
