package echoclientserver.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

// NIO, but still blocking
public class EchoClientNio {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_HOST = "localhost";
    private static final int SLEEP_MILLIS = 500;
    private static ByteBuffer buffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) {

        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));

            System.out.println("Connected to the server.");

            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine(); // read a line from the console

                if ("quit".equals(message)) {
                    break;
                }

                System.out.println("Sending message <" + message + "> to the server...");

                buffer.clear();
                buffer.put(message.getBytes()); // buffer fill
                buffer.flip();
                socketChannel.write(buffer); // buffer drain

                buffer.clear();
                socketChannel.read(buffer); // buffer fill
                buffer.flip();
                String reply = new String(buffer.array(), 0, buffer.limit()); // buffer drain

                System.out.println("The server replied <" + reply + ">");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
