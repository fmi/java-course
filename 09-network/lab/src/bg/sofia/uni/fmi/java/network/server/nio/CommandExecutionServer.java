package bg.sofia.uni.fmi.java.network.server.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * This is a command execution server. It is used to listen for
 * incoming commands to execute them and to return a response.
 * We have implemented only two commands:
 *  - echo:test - echo all the data after the echo: command
 *  - gethostname - returns the hostname of the remote machine
 */
public class CommandExecutionServer implements AutoCloseable {
	
	public static final int SERVER_PORT = 4444;
	
	private Selector selector;
	private ByteBuffer commandBuffer;

	public CommandExecutionServer(int port) throws IOException {
	
	}

	private void start() throws IOException {
	
	}
	
	/**
	 * Accept a new connection
	 * 
	 * @param key The key for which an accept was received
	 * @throws IOException In case of problems with the accept
	 */
	private void accept(SelectionKey key) throws IOException {
	
	}

	/**
	 * Read data from a connection
	 * 
	 * @param key The key for which data was received
	 */
	private void read(SelectionKey key) {
	
	}
	
	/**
	 * Validate and execute the received commands from the clients
	 * 
	 * @param recvMsg
	 * @return The result of the execution of the command
	 */
	private String executeCommand(String recvMsg) {
		String[] cmdParts = recvMsg.split(":");
		
		if (cmdParts.length > 2) {
			return "Incorrect command syntax";
		}
		
		String command = cmdParts[0].trim();
		
		if (command.equalsIgnoreCase("echo")) {
			if (cmdParts.length <= 1) {
				return "Missing Argument";
			}
			return cmdParts[1].strip();
		} else if (command.equalsIgnoreCase("gethostname")) {
			try {
				return InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return "Could not get hostname";
			}
		} else {
			return "Unknown command";
		}
	}
	
	@Override
	public void close() throws Exception {
	}

	public static void main(String args[]) throws Exception {
		try (CommandExecutionServer es = new CommandExecutionServer(SERVER_PORT)) {
			es.start();
		} catch (Exception e) {
			System.out.println("An error has occured");
			e.printStackTrace();
		}
	}
}
