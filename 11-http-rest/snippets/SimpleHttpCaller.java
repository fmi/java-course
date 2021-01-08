import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleHttpCaller {

    private static final String HOST = "google.com";
    private static final int HTTP_PORT = 80;
    private static final String HTTP_REQUEST = "GET / HTTP/1.1" + System.lineSeparator();

    public static void main(String[] args) {

        try (Socket socket = new Socket(HOST, HTTP_PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true); // autoflush on
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            writer.println(HTTP_REQUEST);

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not access web site", e);
        }

    }
}
