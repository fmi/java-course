package bg.sofia.uni.fmi.mjt.echo.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoServer {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_HOST = "0.0.0.0";

    // 0.0.0.0 is a special address meaning "listen on all network interfaces"
    // This allows clients to connect via localhost, your local IP address,
    // or any other IP address your machine has

    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
             Selector selector = Selector.open()) {

            serverSocketChannel.bind(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            // Get the local IP address of the server
            InetAddress serverAddress = InetAddress.getLocalHost();
            System.out.println("NIO server started on " + serverAddress.getHostAddress()
                + " and listening on port " + SERVER_PORT);

            while (true) {
                int readyChannels = selector.select();
                if (readyChannels == 0) {
                    // select() is blocking but may still return with 0, check javadoc
                    continue;
                }

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove(); // remove early to avoid double-processing

                    if (!key.isValid()) {
                        continue;
                    }

                    if (key.isAcceptable()) {
                        acceptClient(key, selector);
                    } else if (key.isReadable()) {
                        readAndEcho(key);
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Server error", e);
        }
    }

    private static void acceptClient(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();

        // In non-blocking mode accept() may return null if no connection is ready
        if (clientChannel == null) {
            return;
        }

        clientChannel.configureBlocking(false);

        // Allocate one buffer per connection
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        // Attach per-client state (here: this connection's buffer) to the selection key
        clientChannel.register(selector, SelectionKey.OP_READ, buffer);

        System.out.println("Accepted connection from " + clientChannel.getRemoteAddress());
    }

    private static void readAndEcho(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();

        buffer.clear();
        int bytesRead = clientChannel.read(buffer);

        if (bytesRead == -1) {
            System.out.println("Client disconnected: " + clientChannel.getRemoteAddress());
            clientChannel.close();
            key.cancel();
            return;
        }

        buffer.flip();

        clientChannel.write(buffer);
    }
}
