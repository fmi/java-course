import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleHttpCaller {

    public static void main(String[] args) {

        try (Socket s = new Socket("www.google.com", 80);
             PrintWriter pw = new PrintWriter(s.getOutputStream(), true); // autoflush on
             BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()))) {

            pw.println("GET / HTTP/1.1\r\n");

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
