package bg.sofia.uni.fmi.java.network.client.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import bg.sofia.uni.fmi.java.network.server.nio.CommandExecutionServer;

/**
 * This is a client written with the blocking API of java.net.* The client
 * connects to the server and waits for information from the user. When the user
 * enters data, it sends it to the server and waits for a response. When the
 * response is received, it is printed to the user and the user can enter new
 * data.
 * <p>
 * Hint: The client can be improved by enabling it to simultaneously read from
 * the console/send to the server and receive information from the server, by
 * using a separate thread for reading from the server and printing the
 * information to the user.
 */
public class BasicClient {

    private InetAddress remoteHost = null;
    private int remotePort = 0;

    /**
     * Initialize the client
     *
     * @param host The host of the EchoServer
     * @param port The port of the EchoServer
     */
    public BasicClient(InetAddress host, int port) {
        this.remoteHost = host;
        this.remotePort = port;
    }

    /**
     * Start the client.
     */
    public void start() {
        try (Socket socket = new Socket(remoteHost, remotePort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream());
             Scanner console = new Scanner(System.in)) {
            System.out.println("Client " + socket + " connected to server");

            // Start a separate thread for reading the response from the server
            // EchoReaderThread reader = new EchoReaderThread(socket);
            // reader.setDaemon(true);
            // reader.start();

            String consoleInput = null;
            while ((consoleInput = console.nextLine()) != null) {

                // Stop the client
                if ("quit".equalsIgnoreCase(consoleInput.trim())) {
                    break;
                }
                // Send to the server
                out.println(consoleInput);
                out.flush();

                // Read the response from the server
                String response = in.readLine();

                // Write to the end user
                System.out.println("Server responded with: " + response);
            }
        } catch (IOException e) {
            System.err.println("An error has occured. " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Client stopped");
    }

    public static void main(String[] args) throws UnknownHostException {
        BasicClient ec = new BasicClient(InetAddress.getByName("localhost"), CommandExecutionServer.SERVER_PORT);
        ec.start();
    }

}
