package bg.sofia.uni.fmi.mjt.echo.net.multithreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientRequestHandler implements Runnable {

    private final Socket socket;

    public ClientRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        Thread.currentThread().setName("Client Request Handler for " + socket.getRemoteSocketAddress());

        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // autoflush on
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             socket) { // resources created elsewhere can also be declared here and will be auto-closed

            String inputLine;
            while ((inputLine = in.readLine()) != null) { // read the message from the client
                System.out.println("Message received from client: " + inputLine);
                out.println("Echo " + inputLine); // send response back to the client
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
