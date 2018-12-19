import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

    private static final int SERVER_PORT = 4444;

    public static void main(String[] args) {

        try (Socket s = new Socket("localhost", SERVER_PORT);
                PrintWriter pw = new PrintWriter(s.getOutputStream(), true); // autoflush on
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

                Scanner sc = new Scanner(System.in);) {

            while (true) {
                String message = sc.nextLine(); // read a line from the console
                
                if ("quit".equals(message)) {
                    break;
                }
                
                System.out.println("Sending message <" + message + "> to the server...");

                pw.println(message); // send the message to the server 

                String reply = br.readLine(); // read the response from the server
                System.out.println("The server replied <" + reply + ">");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
