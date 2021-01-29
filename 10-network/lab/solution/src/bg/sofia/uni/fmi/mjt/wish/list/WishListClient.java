package bg.sofia.uni.fmi.mjt.wish.list;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class WishListClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 7777;

    public static void main(String[] args) {
        new WishListClient().start();
    }

    public void start() {
        try (SocketChannel socketChannel = SocketChannel.open();
                BufferedReader reader = new BufferedReader(Channels.newReader(socketChannel, StandardCharsets.UTF_8));
                PrintWriter writer = new PrintWriter(Channels.newWriter(socketChannel, StandardCharsets.UTF_8), true);
                Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));

            System.out.println("Connected to the server.");

            new Thread(new ClientRunnable(reader)).start();

            while (true) {
                System.out.print("=> ");
                String message = scanner.nextLine();
                writer.println(message);
                if ("disconnect".equals(message)) {
                    break;
                }
            }

            System.out.println("[ Disconnected ]");
        } catch (IOException e) {
            System.err.println("An error occurred in the client I/O: " + e.getMessage());
            System.err.println(e);
        }
    }

}
