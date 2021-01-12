package bg.sofia.uni.fmi.mjt.todo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class client {

	private static final int SERVER_PORT = 3333;
	private static final String SERVER_HOST = "localhost";
	private static final ByteBuffer buffer = ByteBuffer.allocate(512);

	public static void main(String[] args) {

		try (SocketChannel socketChannel = SocketChannel.open();
			 Scanner scanner = new Scanner(System.in)) {

			socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
			System.out.println("Connected to the server.");

			while (true) {
				System.out.print("Enter message: ");
				String message = scanner.nextLine();

				System.out.println("Sending message <" + message + "> to the server...");

				buffer.clear();
				buffer.put(message.getBytes());
				buffer.flip();
				socketChannel.write(buffer);

				buffer.clear();
				socketChannel.read(buffer);
				buffer.flip();

				byte[] byteArray = new byte[buffer.remaining()];
				buffer.get(byteArray);
				String reply = new String(byteArray, StandardCharsets.UTF_8);

				System.out.println(reply);

				if ("disconnect".equals(message)) {
					break;
				}
			}

		} catch (IOException e) {
			System.out.println("There is a problem with the network communication");
			e.printStackTrace();
		}
	}
}
