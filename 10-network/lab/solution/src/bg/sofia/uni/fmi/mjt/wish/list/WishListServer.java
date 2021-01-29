package bg.sofia.uni.fmi.mjt.wish.list;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class WishListServer {

    private static final int SERVER_PORT = 7777;
    private static final int BUFFER_SIZE = 1024;
    private static final String SERVER_HOST = "localhost";
    private static final Random RANDOM = new Random();

    private final int port;
    private final Map<String, Set<String>> studentsWishes;
    private final ByteBuffer messageBuffer;

    private boolean isStarted = true;

    public WishListServer(int port) {
        this.port = port;
        this.studentsWishes = new HashMap<>();
        this.messageBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    }

    public static void main(String[] args) {
        new WishListServer(SERVER_PORT).start();
    }

    public void start() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(SERVER_HOST, port));
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (isStarted) {
                int readyChannels = selector.select();
                if (readyChannels == 0) {
                    // select() is blocking but may still return with 0, check javadoc
                    continue;
                }

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        messageBuffer.clear();
                        int r = socketChannel.read(messageBuffer);
                        if (r <= 0) {
                            System.out.println("Nothing to read, closing channel");
                            socketChannel.close();
                            continue;
                        }

                        handleKeyIsReadable(key, messageBuffer);
                    } else if (key.isAcceptable()) {
                        handleKeyIsAcceptable(selector, key);
                    }

                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            System.err.println("There is a problem with the server socket: " + e.getMessage());
            System.err.println(e);
        }

        System.out.println("Server stopped");
    }

    public void stop() {
        isStarted = false;
    }

    private void handleKeyIsReadable(SelectionKey key, ByteBuffer buffer) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        buffer.flip();
        String message = new String(buffer.array(), 0, buffer.limit()).trim();

        System.out.println("Message [" + message + "] received from client " + socketChannel.getRemoteAddress());

        String command = message.split(" ")[0];
        String arguments = message.substring(message.indexOf(" ") + 1);

        String response = null;
        switch (command) {
        case "post-wish":
            response = postWish(arguments);
            break;
        case "get-wish":
            response = getWish();
            break;
        case "disconnect":
            disconnect(key);
            break;
        default:
            response = "[ Unknown command ]";
        }

        if (response != null) {
            System.out.println("Sending response to client: " + response);
            response += System.lineSeparator();
            buffer.clear();
            buffer.put(response.getBytes());
            buffer.flip();
            socketChannel.write(buffer);
        }
    }

    private void handleKeyIsAcceptable(Selector selector, SelectionKey key) throws IOException {
        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
        SocketChannel accept = sockChannel.accept();
        accept.configureBlocking(false);
        accept.register(selector, SelectionKey.OP_READ);

        System.out.println("Connection accepted from client " + accept.getRemoteAddress());
    }

    private String postWish(String arguments) {
        String[] argumentsArray = arguments.split("\\s+", 2);
        String username = argumentsArray[0];
        String gift = argumentsArray[1];

        if (!studentsWishes.containsKey(username)) {
            studentsWishes.put(username, new HashSet<>());
        } else if (studentsWishes.get(username).contains(gift)) {
            return "[ The same gift for student " + username + " was already submitted ]";
        }

        studentsWishes.get(username).add(gift);

        return "[ Gift " + gift + " for student " + username + " submitted successfully ]";
    }

    private String getWish() {
        if (studentsWishes.isEmpty()) {
            return "[ There are no students present in the wish list ]";
        }

        List<String> students = new ArrayList<>(studentsWishes.keySet());
        String randomStudent = students.get(RANDOM.nextInt(students.size()));
        String randomStudentWishes = studentsWishes.get(randomStudent).toString();
        studentsWishes.remove(randomStudent);

        return "[ " + randomStudent + ": " + randomStudentWishes + " ]";
    }

    private void disconnect(SelectionKey key) throws IOException {
        key.channel().close();
        key.cancel();
    }

}
