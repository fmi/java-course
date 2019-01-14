---
# Мрежово програмиране с Java
_11.12.2018_

---

#### Съдържание

- Мрежови основи
- Моделът клиент-сървър
- Мрежова комуникация в Java

---

#### Мрежата

@img[resize-image](09-network/lecture/assets/img/the_network.png)

---

#### Предаване на данни

@img[resize-image](09-network/lecture/assets/img/protocols.png)

---

#### Open Systems Interconnection (OSI) модел

<table style="font-size:0.5em; line-height=0.7em;">
  <tr>
    <th>#</th>
    <th>Слой</th>
    <th>Описание</th>
    <th>Протоколи</th>
  </tr>
  <tr>
    <td>7</td>
    <td>Application</td>
    <td>Позволява на потребителските приложения да заявяват услуги или информация, а на сървър приложенията — да се регистрират и предоставят услуги в мрежата.</td>
    <td>DNS, FTP, HTTP, NFS, NTP, DHCP, SMTP, Telnet</td>
  </tr>
  <tr>
    <td>6</td>
    <td>Presentation</td>
    <td>Конвертиране, компресиране и криптиране на данни.</td>
    <td>TLS/SSL</td>
  </tr>
  <tr>
    <td>5</td>
    <td>Session</td>
    <td>Създаването, поддържането и терминирането на сесии. Сигурност. Логически портове.</td>
    <td>Sockets</td>
  </tr>
  <tr>
    <td>4</td>
    <td>Transport</td>
    <td>Грижи се за целостта на съобщенията, за пристигането им в точна последователност, потвърждаване за пристигане, проверка за загуби и дублиращи се съобщения.</td>
    <td>TCP, UDP</td>
  </tr>
  <tr>
    <td>3</td>
    <td>Network</td>
    <td>Управлява пакетите в мрежата. Рутиране. Фрагментация на данните. Логически адреси.</td>
    <td>IPv4, IPv6, IPX, ICMP</td>
  </tr>
  <tr>
    <td>2</td>
    <td>Data Link</td>
    <td>Предаване на фреймове от един възел на друг. Управление на последователността на фреймовете. Потвърждения. Проверка за грешки. MAC.</td>
    <td>ATM, X.25, DSL, IEEE 802.11</td>
  </tr>
  <tr>
    <td>1</td>
    <td>Physical</td>
    <td>Отговаря за предаването и приемането на неструктурирани потоци от данни по физическия носител. Кодиране/декодиране на данните. Свързване на физическия носител.</td>
    <td>IEEE 802.11, IEEE 1394, Bluetooth</td>
  </tr>
</table>

---
#### Как работи TCP протоколът

@img[resize-image](09-network/lecture/assets/img/tcp.png)

---

@snap[north]
#### Как идентифицираме един компютър в мрежата?
@snapend

@snap[west]
@img[resize-image-width-small](09-network/lecture/assets/img/cable.png)
@snapend

@snap[midpoint]
@img[resize-image-width-small](09-network/lecture/assets/img/pc.png)
@snapend

@snap[east]
@img[resize-image-width-small](09-network/lecture/assets/img/apps.png)
@snapend

@snap[south]
<table>
  <tr>
    <th>IP Адрес</th>
    <th>Порт</th>
  </tr>
  <tr>
    <td>10.199.199.200</td>
    <td>50430</td>
  </tr>
</table>
IP Адрес + Порт = Socket
@snapend

---
@snap[north]
#### IP протокол
@snapend

@snap[middle]
<ul style="font-size:0.7em; line-height=0.8em;">
  <li>Всеки компютър, свързан към която и да е мрежа, се идентифицира с логически адрес.</li>
  <li>Най-разпространените протоколи за логически адреси в мрежата са IP (Internet Protocol) версия 4 и IP версия 6.</li>
  <li>Адресите в IPv4 представляват 32-битови числа, а в IPv6 - 128-битови.</li>
</ul>
@snapend

@snap[south-west]
@img[resize-image-width-small](09-network/lecture/assets/img/ipv4.png)
@snapend

@snap[south-east]
@img[resize-image-width-small](09-network/lecture/assets/img/ipv6.png)
@snapend

---

#### Портове

<ul style="font-size:0.7em; line-height=0.8em;">
  <li>В общия случай, компютърът има една физическа връзка към мрежата. По тази връзка се изпращат и получават данни от/за всички приложения. Портовете се използват, за да се знае кои данни за кое приложение са.</li>
  <li>Предадените данни по мрежата винаги съдържат в себе си информация за компютъра и порта, към които са насочени.</li>
  <li>Портовете се идентифицират с 16-битово число, което се използва от UDP и TCP протоколите, за да идентифицират за кое приложение са предназначени данните.</li>
  <li>Портовете могат да бъдат от номер 0 до номер 65 535.</li>
  <li>Портове с номера от 0 до 1023 са известни като “well-known ports”. За да се използват тези портове от вашето приложение, то трябва да се изпълнява с администраторски права.</li>
</ul>

---

@snap[north]
#### Сокети (Socket)
@snapend

@snap[west]
<p>Сокетите се използват при клиент-сървър комуникация и представляват една крайна точка от двупосочна връзка./p>
<p>Състояния на сокетите:</p>
<ol style="font-size:0.5em; line-height=0.7em;">
  <li>socket() методът създава крайна точка за комуникация и връща дескриптор на сокета.</li>
  <li>bind() методът създава уникално име за този сокет. По този начин, сървърът е достъпен по мрежата.</li>
  <li>listen() методът показва готовност за приемане на клиентски връзки.</li>
  <li>Клиентското приложение трябва да извика метода connect(), за да осъществи връзка със сървъра.</li>
  <li>Сървърно приложение използва accept() метода, за да приеме връзка от клиента.</li>
  <li>След като е осъществена връзката, може да се използват методите за трансфер на потоци (streams): send(), recv(), read(), write() и други.</li>
  <li>Сървърът или клиентът може да прекратят връзката с метода close().</li>
<ol>
@snapend

---

#### Сокети (Socket)

@img[resize-image](09-network/lecture/assets/img/socket_calls.png)

---

#### Модел Клиент-Сървър

<p style="font-size:0.8em;line-height:1em;">Клиент-сървър е разпределен изчислителен модел, при който част от задачите се разпределят между доставчиците на ресурси или услуги, наречени сървъри и консуматорите на услуги, наречени клиенти.</p>

@img[resize-image-width-small](09-network/lecture/assets/img/client-server.png)

---

#### Модел Peer-to-peer

<p style="font-size:0.8em;line-height:1em;">Peer-to-peer е разпределен архитектурен модел на приложение, при който задачите се разпределят по еднакъв начин между всички участници (peer, node). Всеки участник е едновременно и клиент, и сървър.</p>

@img[resize-image-width-small](09-network/lecture/assets/img/peer-to-peer.png)

---

#### Видове клиенти

Според наличната функционалност в клиента:
- Rich клиенти.
- Thin клиенти.

Според семантиката (протокола):
- Web клиенти – Браузъри (Chrome, Firefox, IE).
- Mail клиенти – POP/SMTP клиенти (MS Outlook, Lotus notes).
- FTP клиенти – Total Commander, Filezilla, WinSCP.
- …

---

#### Видове сървъри

<ul style="font-size:0.8em; line-height=1em;">
  <li>Файл сървър (Windows, Samba, UNIX NFS, OpenAFS).</li>
  <li>DB сървър (MySQL, PostgreSQL, Oracle, MS SQL Server, Mongo DB).</li>
  <li>Mail сървър (MS Exchange, GMail, Lotus Notes).</li>
  <li>Name сървър (DNS).</li>
  <li>FTP сървър (ftpd, IIS).</li>
  <li>Print сървър.</li>
  <li>Game сървър.</li>
  <li>Web сървър (Apache, GWS, MS IIS, nginx).</li>
  <li>Application сървър (Tomcat, GlassFish, JBoss, BEA, Oracle).</li>
  <li>…</li>
<ul>

---

#### Предимства и недостатъци на Клиент-Сървър

- Single Point Of Failure (SPOF).
- Увеличаването на броя на клиентите води до намаляване на производителността.
- 70-95% от времето, през което работи, сървърът е idle.


---

#### Предимства и недостатъци на Peer-to-Peer

- Няма SPOF.
- Няма намаляване на производителността при увеличаване на клиентите.
- Проблеми със сигурността.
- Риск от умишлена промяна на съдържание.
- Липса на контрол върху съдържанието и възможност за загуба на съдържание.
- Труден процес на поддръжка.

---

#### java.net.* | Въведение

<p style="font-size:0.6em; line-height=0.8em;">Пакетът java.net предоставя класове, които използват и работят на различни нива от OSI модела.</p>
<ul style="font-size:0.6em; line-height=0.8em;">
  <li>Мрежов и data link слой – класът NetworkInterface предоставя достъп до мрежовите адаптери на компютъра.</li>
  <li>Транспортен слой – в зависимост от използваните класове, може да се използват следните транспортни протоколи:</li>
  <li>TCP – класове Socket и ServerSocket.</li>
  <li>UDP – класове DatagramPacket, DatagramSocket, MulticastSocket.</li>
  <li>Приложен слой – класовете URL и URLConnection се използват за достъпването на HTTP и FTP ресурси.</li>
</ul>
<p style="font-size:0.6em; line-height=0.8em;">Класовете в пакета java.io се използват за обработка на потоци от данни (data streams). InputStream, BufferedInputStream, Reader, InputStreamReader, BufferedReader, OutputStream, BufferedOutputStream, Writer, PrintWriter са класове, които често се използват при мрежовото програмиране в Java.</p>

---

#### java.net.* | Мрежови адаптери | 1

<ul style="font-size:0.6em; line-height=0.8em;">
  <li>Мрежовият адаптер осъществява връзката между компютърната система и публична или частна мрежа.</li>
  <li>Мрежовите адаптери могат да бъдат физически или виртуални (софтуерни). Примери за виртуални са loopback интерфейсът и интерфейсите, създадени от виртуалните машини.</li>
  <li>Една система може да има повече от един физически и/или виртуален мрежови адаптер.</li>
  <li>Java предоставя достъп до всички мрежови адаптери чрез класа java.net.NetworkInterface.</li>
</ul>

---

#### java.net.* | Мрежови адаптери | 2

<p style="font-size:0.6em; line-height=0.8em;">С помощта на класа NetworkInterface, може да вземете списък с всички мрежови адаптери (getNetworkInterfaces()) или да вземете точно определен (getByInetAddress() и getByName()).</p>

```java
Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
for (NetworkInterface netIf : Collections.list(nets)) {
   System.out.printf("Display name: %s\n", netIf.getDisplayName());
   System.out.printf("Name: %s\n", netIf.getName());
   System.out.printf("Addresses: %s\n", printEnum(netIf.getInetAddresses()));
   System.out.printf("\n");
}
```

```
Display name: Software Loopback Interface 1
Name: lo
Addresses: /0:0:0:0:0:0:0:1, /127.0.0.1,

Display name: Intel(R) 82578DM Gigabit Network Connection
Name: eth6
Addresses: /10.xxx.xxx.xxx,
```

---

#### java.net.* | TCP комуникация

@img[resize-image](09-network/lecture/assets/img/java_io_calls.png)

---

#### java.net.* | Клиент-сървър комуникация | 1

```java
// Отваряне на сокет от клиентската страна и изпращане на заявка
try {
    Socket s = new Socket("www.cia.gov", 80);
    PrintWriter pw = new PrintWriter(s.getOutputStream());
    pw.println("GET /index.html");
    pw.println();
    pw.flush();
} catch (UnknownHostException e) {
} catch (IOException e) {}

```

---

#### java.net.* | Клиент-сървър комуникация | 2

```java
try {
    ServerSocket ss = new ServerSocket(80);
    Socket s = ss.accept(); // The thread is blocked.
    // New connection is established. Read the request
    BufferedInputStream is = new BufferedInputStream(s.getInputStream());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    byte[] b = new byte[2048];
    int r = 0;
    while ((r = is.read(b)) != -1) {
        bytes.write(b, 0, r);
    }
} catch (IOException e) {}
```

---

#### java.net.* | Архитектура

За класовете от пакета java.net е необходимо да има една нишка за всяка връзка (connection).
Синхронни (блокиращи) операции.

@img[](09-network/lecture/assets/img/java_net_arch.png)

---

#### java.net.* | Клиент-сървър комуникация | 3

```java
// Обработка на заявката и връщане на отговор:
try {
    // Process request
    // Send response
    PrintWriter pw = new PrintWriter(s.getOutputStream());
    pw.println("Hello World");
    pw.flush();
    pw.close();
    is.close();
    s.close();
} catch (IOException e) {}
```

---

#### java.net.* | Клиент-сървър комуникация | 4

```java
// Прочитане на отговора (response) от сървъра:
try {
    ...
    BufferedReader br = new BufferedReader(
        new InputStreamReader(s.getInputStream()));
    String line;
    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }
    pw.close();
    br.close();
    s.close();
} catch (IOException e) {}
```

---

#### java.nio.* | Въведение

Въведен от JDK 1.4.
Позволява асинхронни входно-изходни (I/О) операции.
Намалява генерирането на “боклук” чрез използването на буфери с директна памет (DMA), при които при писането и четенето в сокетите не се извършва копиране на данни.

---

#### java.nio.* | Основни обекти

<ul style="font-size:0.8em; line-height=1em;">
  <li>Буфери (Buffers) – Представляват блок от паметта, в който може да се записват данни. Използват се за четене и запис в NIO канали (channels).</li>
  <li>Канали (Channels) – Подобни на потоците (Stream). Представляват една връзка, като от тях може да се чете и да се записва. Основните класове: FileChannel, DatagramChannel, SocketChannel, ServerSocketChannel.</li>
  <li>Селектор (Selector) – Компонент, в който се регистрират канали и може да обработва повече от един канал в една нишка.</li>
</ul>

---

#### java.io.* | Четене и запис от файлове | 1

@img[resize-image](09-network/lecture/assets/img/file_read_write_old_io.png)

---

#### java.nio.* | Четене и запис във/от файл

@img[resize-image](09-network/lecture/assets/img/file_read_write_new_io.png)

---

#### java.nio.* | Буфери

<ul style="font-size:0.8em; line-height=1em;">
  <li>Буферът е обект, който съдържа данни, които ще бъдат записани или са били прочетени.</li>
  <li>В NIO, за обработката на данни се използват буфери и това е една от най-големите разлики в сравнение със стандартния вход/изход, където се използват потоци (Stream).</li>
  <li>NIO предоставя имплементации на буфери за всички не-булеви примитивни типове: ByteBuffer, CharBuffer, ShortBuffer, IntBuffer, LongBuffer, FloatBuffer, DoubleBuffer</li>
  <li>Буферът като цяло е масив от данни, който пази в себе си състоянието си:  докъде е запълнен, колко свободно място има, докъде са прочетени или записани данните.</li>
</ul>

---

#### java.nio.* | Вътрешно състояние на буфера

<p style="font-size:0.7em; line-height=0.9em;">В себе си буферът пази четири състояния:</p>
<ul style="font-size:0.7em; line-height=0.9em;">
  <li>Позиция (position) – съдържа информация до кой елемент на масива сме стигнали.</li>
  <li>Лимит (limit) – съдържа информация колко данни още можем да прочетем от масива или още колко може да запишем.</li>
  <li>Обем (capacity) – показва какво е максималното количество данни, което може да съдържа буферът.</li>
  <li>Маркер (mark) – програмистът може да запомни някоя позиция от масива.</li>
</ul>
<p style="font-size:0.7em; line-height=0.9em;">Зависимостта между четирите състояния е: 0 <= mark <= position <= limit <= capacity</p>

---

#### java.nio.* | Как работи буферът

@img[resize-image](09-network/lecture/assets/img/buffer_calls.png)

---

#### java.nio.* | Достъп до паметта | 1

Indirect Buffer (индиректен буфер).
```java
ByteBuffer buffer = ByteBuffer.allocate(1024);
```
Direct Buffer (директен буфер) – ще се опита да използва native операции за четене и запис директно чрез операционната система.
```java
ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
```

---

#### java.nio.* | Достъп до паметта | 2

Memory-mapped Buffer (директен буфер) – използва функционалност на операционната система,  за да обвърже даден файл с част от оперативната памет.
```java
MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, 1024);
```
Read-only Buffer (буфер за четене).
```java
ByteBuffer readBuffer = buffer.asReadOnlyBuffer();
```

---

#### java.nio.* | Достъп до паметта | 3

@img[resize-image](09-network/lecture/assets/img/buffer_graph.png)

---

#### java.nio.* | ServerSocketChannel

```java
// ServerSocketChannel е канал (java.nio.Channel), който може
// да слуша за входящи TCP повиквания, точно както ServerSocket.

ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
serverSocketChannel.socket().bind(new InetSocketAddress(9999));
serverSocketChannel.configureBlocking(false);

while (true) {
    SocketChannel socketChannel = serverSocketChannel.accept();
    ...
}
```

---

#### java.nio.* | SocketChannel | Регистрация | 1

SocketChannel  представлява една TCP връзка. За да се използва асинхронно, трябва да се регистрира в селектор.
Каналът трябва да се постави в nonblocking режим (чрез извикване на `configureBlocking(false)`) преди да може да бъде регистриран със селектор.

```java
Selector selector = Selector.open();
socketChannel.configureBlocking(false);
socketChannel.register(selector, SelectionKey.OP_READ);
```

---

#### java.nio.* | SocketChannel | Регистрация | 2

Когато регистрираме SocketChannel, трябва да укажем за какви операции да бъдем известени:
<ul style="font-size:0.7em; line-height=0.9em;">
  <li>OP_READ – ще се получи известие (notification), когато се получи нещо за четене от канала.</li>
  <li>OP_WRITE – ще се получи известие (notification), когато каналът е готов за запис.</li>
  <li>OP_CONNECT – ще се получи известие (notification), когато каналът е готов да завърши последователността си за свързване към отдалечената система.</li>
  <li>OP_ACCEPT – ще се получи известие (notification), когато пристигне нова конекция.</li>
</ul>

---

#### java.nio.* | SocketChannel | Notification

```java
// Получаване на известие за няколко канала.
while (true) {
    int readyChannels = selector.select();
    if (readyChannels == 0) { continue; }

    Set<SelectionKey> selectedKeys = selector.selectedKeys();
    Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
    while (keyIterator.hasNext()) {
        SelectionKey key = keyIterator.next();
        if (key.isReadable()) {
            // A channel is ready for reading
        }
        keyIterator.remove();
    }
}
```

---

#### java.nio.* | SocketChannel | Notification | Четене

```java
// Четене на данните от канал.
if (key.isReadable()) {
    // Read the data
    SocketChannel sc = (SocketChannel) key.channel();
    while (true) {
        buffer.clear();
        int r = sc.read( buffer );
        if (r<=0) { break; }
        buffer.flip();
        sc.write( buffer );
    }
} else if (key.isWritable()) {
...
}
```

---

#### java.nio.* | SocketChannel | Запис

```java
// Използваме SocketChannel канал (channel) и за изпращане на данни
// по TCP връзката (connection).

SocketChannel socketChannel = SocketChannel.open();
socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));

String newData = "Current time: " + System.currentTimeMillis();
ByteBuffer buf = ByteBuffer.allocate(48);

buf.put(newData.getBytes());
buf.flip();
while (buf.hasRemaining()) {
    socketChannel.write(buf);
}
```

---

#### java.nio.* | Архитектура

<p style="font-size:0.6em; line-height=0.8em;">Нишка селектор (Selector), която позволява обработването на няколко канала от една нишка.</p>
<p style="font-size:0.6em; line-height=0.8em;">Намалява броя на нишките, като премахва нуждата от отделна нишка за всяка връзка (connection).</p>
<p style="font-size:0.6em; line-height=0.8em;">Асинхронни (неблокиращи) операции.</p>

@img[resize-image-width-small](09-network/lecture/assets/img/java_nio_arch.png)
