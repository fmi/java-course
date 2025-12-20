package bg.sofia.uni.fmi.mjt.echo.net.multithreaded;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {

    private static final int SERVER_PORT = 4444;
    private static final int MAX_EXECUTOR_THREADS = 10;
    // In production, consider:
    // - Runtime.getRuntime().availableProcessors() for CPU-bound tasks
    // - Larger pool (e.g., availableProcessors() * 2-4) for I/O-bound tasks,
    //   since threads block waiting for I/O and don't consume CPU

    static void main() {
        Thread.currentThread().setName("Echo Server Thread");

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
             ExecutorService executor = Executors.newFixedThreadPool(MAX_EXECUTOR_THREADS)) {

            // Get the local IP address of the server
            InetAddress serverAddress = InetAddress.getLocalHost();
            System.out.println("Server started on " + serverAddress.getHostAddress() +
                " and listening for connection requests on port " + SERVER_PORT);

            Socket clientSocket;

            while (true) {
                // Calling accept() blocks and waits for connection request by a client
                // When a request comes, accept() returns a socket to communicate with this
                // client
                clientSocket = serverSocket.accept();

                System.out.println("Accepted connection request from client " + clientSocket.getInetAddress() + ":" +
                    clientSocket.getPort());

                // We want each client to be processed in a separate thread
                // to keep the current thread free to accept() requests from new clients
                ClientRequestHandler clientHandler = new ClientRequestHandler(clientSocket);

                // uncomment the line below to launch a thread manually
                // new Thread(clientHandler).start();
                executor.execute(clientHandler); // use a thread pool to launch a thread
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the server socket", e);
        }
    }

}
