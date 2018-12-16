# Мрежово Програмиране с Java

## Условие на задачата

Да се имплементира Command Execution Server, който да се използва за изпълнение на отдалечени команди. Сървърът да поддържа изпълнението на следните команди:

| Команда | Входни Параметри | Описание | Пример |
| ------- | ---------------- | -------- | ------ |
| echo    | Да               | Командата връща на потребителя подадените входни параметри | echo:somestringtoreturn |
| gethostname | Не           | Командата прочита какво е името на сървъра и го връща на потребителя | gethostname |

За имплементирането на сървъра, да не се използват нишки, a неблокиращото `java.nio` API.

## Изпълнение

1. Създайте клас `CommandExecutionServer` с следната структура:

```java
package bg.sofia.uni.fmi.echo.server.nio;

/**
 * This is a command execution server. It is used to listen for
 * incoming commands, to execute them and to return a response.
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
```
2. Имплементирайте конструктора на класа `CommandExecutionServer`. В конструктора трябва да инициализирате `Selector`, `ByteBuffer` и да отворите `ServerSocketChannel` на подадения порт. След като отворите сокета, го регистрирайте в селектора за OP_ACCEPT и изпишете, на кой IP адрес се намира нашият сървър с извикването `InetAddress.getLocalHost().getHostAddress()`. За повече информация, вижте [тук](https://gitpitch.com/fmi/java-course/master?p=09-network/lecture/#/36) и [тук](https://gitpitch.com/fmi/java-course/master?p=09-network/lecture/#/37)

3. Имплементирайте метода `close`, като затворите всички ресурси заделени в конструктора.


4. Пуснете вашия сървър и вижте дали на операционната система порт 4444 е зает, с командата:
```
netstat -an
```
или ако сте на Linux:
```
ss -an
```
След това спрете сървъра.

5. Имплементирайте метода `start`. В този метод трябва да извиквате `selector.select()` и да итерирате по селектираните канали, като обработвате OP_ACCEPT и OP_READ операции. За повече информация, вижте [тук](https://gitpitch.com/fmi/java-course/master?p=09-network/lecture/#/39). Не забравяйте да извикате метода `start` в `main` метода.

6. Имплементирайте метода `accept`, като в него трябва да обработвате нови канали, когато клиент се върже към сървъра, и да ги регистрирате в `selector`-а за операции OP_READ.

7. Стартирайте сървъра и се опитайте да се вържете към него. Можете да използвате няколко инструмента:
- Команда `nc <IP of Server> <Port of Server>` за Linux и MacOS
- Команда `telnet <IP of Server> <Port of Server>` за Windows
- Използвайте имплементирания java клиент `bg.uni.sofia.fmi.java.network.client.io.BasicClient`

8. Имплементирайте метода `read`. В този метод трябва да вземете `Channel` от ключа и да прочетете съдържанието в буфера, който сме заделили в конструктора. След като данните се прочетат, конвертирайте `ByteBuffer` обекта в `String` с `Charset.forName("UTF-8").decode(commandBuffer).toString();` и го подайте на метода `executeCommand`. Резултата от `executeCommand` го конвертирайте в `ByteArray` с `commandBuffer.put(result.getBytes());` и го запишете в канала. За повече информация, вижте [тук](https://gitpitch.com/fmi/java-course/master?p=09-network/lecture/#/40).

9. Стартирайте сървъра и го тествайте с помощта на командите, описани в точка 7:

| Команда | Очакван резултат |
| ------- | ---------------- |
| test    | Unknown command  |
| echo:what | what |
| gethostname | \<hostname of your machine\> |
| echo: who | who |
| echo      | Missing argument |

## Структура на проекта
```
src
╷
└─ bg/sofia/uni/fmi/java/network/
   ├─ server/nio/
   |   └─ CommandExecutionServer.java
   └─ client/io/ 
       └─ BasicClient.java
```
