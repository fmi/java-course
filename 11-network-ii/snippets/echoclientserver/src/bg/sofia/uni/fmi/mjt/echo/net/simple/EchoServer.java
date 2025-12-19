package bg.sofia.uni.fmi.mjt.echo.net.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private static final int SERVER_PORT = 6666;

    static void main() {

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            // Get the local IP address of the server
            InetAddress serverAddress = InetAddress.getLocalHost();
            System.out.println("Server started on " + serverAddress.getHostAddress() +
                " and listening for connection requests on port " + SERVER_PORT);

            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) { // autoflush on

                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    System.out.println("Message received from client: " + inputLine);
                    out.println("Echo " + inputLine);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the server socket", e);
        }
    }

}
