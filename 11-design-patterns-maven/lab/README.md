# Chat Server

### Условие

Ще разработим чат клиент-сървър със следната функционалност:
- Да обслужва много потребители едновременно
- Всеки потребител има уникално име, с което се индентифицира при свърване към сървъра
- Потребителите могат да напуснат чата по всяко време
- Потребителите могат да изпращат лични съобщение до друг активен (т.е. в момента свързан към сървъра) потребител, както и съобщения до всички активни потребители
- Извеждане на всички активни потребители

### Chat Server

- Сървърът трябва да може да обслужва множество клиенти едновременно
- Сървърът получава команди от клиентите и връща подходящ резултат

### Описание на клиентските команди

```bash
- connect <host> <port> <username> - свързва потребител с дадено потребителско име към съвръра
- disconnect – напуска чата
- list-users – извежда списък с всички активни в момента потребители
- send <username> <message> - изпраща лично съобщение до даден активен потребител
- send-all <message> - изпраща съобщение до всички активни потребители
```

**Подсказка:** Разгледайте имплементацията на многонишковия [Echo Server](https://github.com/fmi/java-course/tree/master/09-network/lab/snippets) от упражнението за мрежово програмиране. Можем ли да го превърнем в Chat Server?

**Подсказка:** Решението на тази задача ще ви улесни изключително много при разработката на курсовите ви проекти, защото всички те представляват приложения тип клиент-сървър, като сървърът обслужва много потребители едновременно.

### Пример

```bash
# start a client from command line
$ java bg.sofia.uni.fmi.mjt.chat.ChatClient
connect localhost 8080 java-duke
=> connected to server running on localhost:8080 as java-duke
# or
=> cannot connect to server on localhost:8080, make sure that the server is started

send java-duke hi there
=> [12.01.2018 20:05] java-duke: hi there
=> [12.01.2018 20:06] adam: hallo # sent to java-duke from adam
# or
=> java-duke seems to be offline

list-users
=> john, connected at 12.01.2019 20:05
=> adam, connected at 12.01.2019 21:12
# or
=> nobody is online

disconnect
=> disconnected from server on localhost:8080
# or
=> cannot disconnect, try to connect first
```

Имате свобода да използвате съобщения и формати по ваш избор.

### Тестване

Голяма част от имплементацията може да се тества със стандартни JUnit средства, но всяка програма тип клиент-сървър ни навежда на мисълта да ползваме mocking, за да тестваме изолирано логиката на клиента и логиката на сървъра.

### Примерна структура на проекта

```
src
╷
├─ bg/sofia/uni/fmi/mjt/chat/
|  ├─ ChatClient.java
|  └─ ChatServer.java 
test
├─ bg/sofia/uni/fmi/mjt/chat/
|  └─ ChatClientTest.java
|  └─ ChatServerTest.java
lib
├─ mockito-core-x.xx.x.jar
└─ ...
```

### Dependencies

```bash
# for testing
wget http://central.maven.org/maven2/org/mockito/mockito-core/2.23.0/mockito-core-2.23.0.jar
wget http://central.maven.org/maven2/net/bytebuddy/byte-buddy/1.9.0/byte-buddy-1.9.0.jar
wget http://central.maven.org/maven2/net/bytebuddy/byte-buddy-agent/1.9.0/byte-buddy-agent-1.9.0.jar
wget http://central.maven.org/maven2/org/objenesis/objenesis/2.6/objenesis-2.6.jar
```

[Код от упражнение](https://gist.github.com/ialidzhikov/04df81ae7da7fbefc561bf4608028152).
