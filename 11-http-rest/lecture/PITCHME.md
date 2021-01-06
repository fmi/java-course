## HTTP, REST, JSON и `HttpClient`

_06.01.2021_

---

#### Предната лекция говорихме за:

@ul

- Thread pools / Executors API
- Concurrent колекции
- Мрежово програмиране

@ulend

---

#### Днес ще разгледаме:

@ul

- HTTP
- REST
- JSON
- `HttpClient`

@ulend

---

#### Как работи Интернет?

![HTTP request](images/11.1-http-request.png)

---

#### Характеристики на HTTP

@ul

- HTTP (Hypertext Transfer Protocol) e приложен протокол
- Като транспортен протокол, почти винаги се ползва ТCP/IP, в редки случаи и UDP
- Модел заявка-отговор (request-response) - служи за комуникационен канал в клиент-сървър архитектура, като следва строги правила за ред и формат на съобщенията между участниците
- HTTP сървърите по подразбиране "слушат" на порт 80

@ulend

---

#### Характеристики на HTTP

@ul

- Не пази състояние (*stateless*) – всяка клиентска заявка е независима сама по себе си
- Сървърът не обвързва логически серия заявки от определен клиент
- Това води до липса на вграден в протокола механизъм за поддържане на сесии

@ulend

---

#### HTTP транзакция "от птичи поглед"

1. Клиентът отваря комуникационен канал (TCP сокет)
2. Клиентът изпраща заявка към сървъра
3. Сървърът връща отговор на клиента
4. Сървърът затваря сокета

---

#### Видове HTTP съобщения: Заявки

@ul

- *Заявка* – инициатор е клиентът – подава информация на сървъра, достъп до кой ресурс иска да получи и каква операция иска да извърши с него (и евентуални входни параметри). Клиент (условно наречен *User-Agent* в HTTP) може да бъде всяко софтуерно приложение, спазващо правилата на протокола на комуникация

@ulend

---

#### Видове HTTP съобщения: Отговори

@ul

- *Отговор* – изпраща се от уеб сървъра, като резултат от изпълнението на клиентска заявка. Под уеб сървър разбираме софтуерно приложение, служещо като доставчик на дадени услуги върху определени негови ресурси

@ulend

---

#### HTTP Заявка

- Начален ред
- Хедъри (headers)
- Тяло (данни)

---

#### HTTP Заявка: начален ред

@ul

- HTTP Метод (Глагол) – указва типа операция, която клиентът иска да извърши със заявения ресурс
- URL (Uniform Resource Locator) – уникален локатор на заявения ресурс
- Версия на HTTP – версията на протокола, която ще се ползва за комуникация

@ulend

---

#### HTTP Заявка: хедъри и данни

@ul

- *Хедъри* - Възможно е да дефинира множество хедъри, като всеки от тях заема точно един ред и следва формата: “Име на хедър: Стойност на хедър”
- *Данни (Тяло)* – опционални, може да съдържат множество редове, включително и празни

@ulend

---

#### HTTP Заявка - пример

```http
GET en.wikipedia.org/w/index.php HTTP/1.1
```

```http
Connection: Keep-Alive
Host: en.wikipedia.org
```

---

#### HTTP заявка - пример

![HTTP sample request](images/11.2-http-sample-request.png)

---

#### HTTP ресурси

Унифициран локатор на ресурси (URL) - стандартизиран адрес на даден мрежов ресурс (документ или страница).

![URL](images/11.3-url.png)

---

### Основни HTTP методи

@ul

- GET – за зареждане на ресурс от сървъра
- HEAD - идентичен с GET, с разликата, че отговорът няма да върне тяло, а само хедъри

@ulend

---

### Основни HTTP методи

@ul

- POST - изпраща данни (например от HTML форма) за обработка от сървъра. Данните се съдържат в тялото на заявката
- PUT – ъплоудва даден ресурс
- DELETE – трие даден ресурс

@ulend

---

#### HTTP отговор

@ul

- Начален ред – съдържа 3 елемента, разделени с празно пространство помежду си:
  - Версия на HTTP
  - Статус код – обяснява резултата от изпълнението на заявката
  - Причина – кратко обяснение на статус-кода
- Хедъри
- Данни (Тяло) – отговорите обикновено връщат данни, като тук най-често се съдържа HTML документът, получен на базата на клиентската заявка.

@ulend

---

#### HTTP отговор - пример

```http
HTTP/1.1 200 OK
```

```http
Date: Tue, 18 Oct 2018 19:08:15 GMT
Server: Apache
```

---
#### HTTP отговор - пример

![HTTP response](images/11.4-http-sample-response.png)

---

#### HTTP статус кодове

- Трицифрени кодове, идентифициращи какъв е резултът от обработката на клиентската заявка
- Групирани са в 5 категории, на базата на цифрата на стотиците

---

#### HTTP статус кодове: група 100

Не дават индикация дали заявката е била успешна или не. Служат за „временни“ кодове, т.е. заявката е пристигнала, но сървърът все още не е готов с резултата.

```http
100 Continue
101 Switching protocols
```

---

#### HTTP статус кодове: група 200

Сървърът е обработил успешно клиентската заявка

```http
200 OK
206 Partial content
```

---

#### HTTP статус кодове: група 300

Ресурсът е наличен, но е разположен на друго място

```http
301 Moved permanently
304 Not Modified
307 Temporary redirect
```

---

#### HTTP статус кодове: група 400

Клиентска грешка

```http
400 Bad Request
401 Not Authorized
404 Not Found
408 Request Timeout
```

---

#### HTTP статус кодове: група 500

Сървърна грешка

```http
500 Internal Server Error
501 Not Implemented
503 Service Unavailable
```

---

#### HTTP хедъри: Основни (General Headers)

Могат да се ползват едновременно и в заявки, и в отговори. Съдържат информация (мета-данни) за самото съобщение или за метода на комуникация

```http
Connection: keep-alive
Date: Sat, 17 Nov 2018 16:08:15 GMT
```

---

#### HTTP хедъри: Заявка (Request Headers)

Специфични са само за заявките и могат да съдържат данни за самата заявка или за клиента


```http
Accept: text/html
Accept-Charset: utf-8
Accept-Language: en-US
User-Agent: Mozilla/4.0 
```

---

#### HTTP хедъри: Отговор (Response Headers)

Съдържат информация (мета-данни) за сървъра и формата на съобщението


```http
Server: Apache
Allow: GET, HEAD
```

---

#### HTTP хедъри: Същински (Entity Headers)

Информация за самото съдържание на данни (тяло) и/или за ресурса, заявен от клиента


```http
Content-Language: en
Content-Encoding: gzip
Content-Length: 8582
Last-Modified: Tue, 15 Nov 2018 12:45:26 GMT
```

---

#### HTTP хедъри: User Agent

*User Agent* е софтуер, който извършва действие от името на потребителя: E-mail клиенти, Web Browser-и, Месинджъри: Skype, WhatsApp

Примерeн низ за Google Chrome Web Browser:

```http
Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML,
          like Gecko) Chrome/38.0.2125.101 Safari/537.36
```

---

#### HTTP сесии - механизми

<small>
Понеже HTTP протоколът няма вградена поддръжка на сесии, ако има нужда сървърът да може да корелира заявките, идващи от един и същи клиент като логически принадлежащи към една последователност, обикново се ползва един от следните начини:

- *Бисквитки* (*Cookies*): Cookie-тата са малки текстови файлове, генерирани от сървъра и изпратени на клиента в header-ите. Чрез информацията в тях може да се идентифицира сесията
- Hidden fields в HTML forms: HTML страницата съдържа форма с поле, което е скрито и не се визуализира в браузъра, но стойността му се праща като част от заявката и стойността му идентифицира сесията (`<input type="hidden">`)
- URL rewriting: добавяме в края на всяко URL данни, които да идентифицират сесията

</small>

---

#### HTTP vs HTTPS

![HTTPS](images/11.5-https.png)

---

#### HTTP/2

- Появява се през 2015-та
- Какви проблеми на HTTP 1.x решава?

---

#### HTTP/2 vs HTTP 1.1

@ul

- двоичен, вместо текстови (⇨ по-компактен)
- напълно multiplexed, вместо подреден и блокиращ
- постига паралелизъм само с една TCP връзка между клиента и сървъра
- Използва компресия на хедърите
- Разрешава сървърите да “push”-ват HTTP response-и проактивно към клиента

@ulend

---

#### HTTP/2 Multiplexing

![HTTP2 Multiplexing](images/11.6-http2-multiplexing.png)

---

#### HTTP 1.1 vs. HTTP/2 response time

![HTTP2 Response Time](images/11.7-http2-response-time.png)

---

#### HTTP/2: поддръжка към днешна дата

@ul

- практически всички web browsers
- 50% от всички web sites към януари 2021 (43% през януари 2020)
- HttpClient-a в Java 11

@ulend

---

## REST

---

#### REpresentational State Transfer (REST)

@ul

- REST е стил софтуерна архитектура за реализация на уеб услуги.
  - REST не е спецификация или протокол
- Състои се от 6 принципа, които допринасят към това уеб услугата да бъде проста, бързодействаща и скалируема.

@ulend

---

#### REST (2)

@ul

- Има за цел да подобри и улесни комуникацията между две системи.
- Уеб услугите, които покриват REST принципите, се наричат RESTful уеб услуги.
- WWW е RESTful

@ulend

---

#### Принципи на REST архитектурата

@ul

- REST е клиент-сървър архитектура
- REST предоставя единен интерфейс (контракт)
- REST не пази състояние (stateless)
- REST предлага възможности за кеширане
- REST е многослойна система
- REST предоставя code on demand

@ulend

---

#### REST is client-server architecture

@ul

- Клиентът и сървърът са отделни компоненти
- Separation of concerns:
  - Сървърът съдържа бизнес логиката и се грижи за съхранението на данните
  - Клиентът извлича данни от сървъра и се грижи за представянето на данните:
    - уеб клиенти - браузъри
    - конзолни клиенти
    - ...

@ulend

---

#### REST is client-server architecture (2)

@ul

- Единният интерфейс позволява разделянето на клиент и сървър.
- Клиентът и сървърът могат да бъдат променяни независимо един от друг, стига това да не променя единния интерфейс помежду им.

@ulend

---

#### Пример за клиент-сървър

@ul

- Дa предположим, че искаме да извлечем информация за даден потребител от GitHub. REST API документация за въпросната операция - [link](https://docs.github.com/en/free-pro-team@latest/rest/reference/users#get-a-user).
- Можем да го направим през:
  - уеб клиент - @size[1.0em](https://github.com/{username})
  - Java клиент - [hub4j/github-api](https://github.com/hub4j/github-api/)
  - заявка към REST API-то
    - @size[0.75em]($ curl https://api.github.com/users/{username})
  - ...

@ulend

---

#### Ресурси

@ul

- REST въвежда концепцията за ресурс.
- Всяка информация, която може да бъде именувана, може да бъде ресурс.
- Ние, като програмисти, дефинираме кои са ресурсите, с които ще оперира нашата уеб услуга.
- В примера, който разгледахме, ресурси бяха дадените GitHub потребители.
- Ресурси могат да бъдат html страници, js файлове, изображения, потребители и др.

@ulend

---

#### Идентификатори на ресурси

<small>
- Всеки ресурс се идентифицира чрез URI (uniform resource identifier)
- Примери:

</small>

```bash
# Get all repos in fmi organisation
$ curl https://api.github.com/orgs/fmi/repos
# Get fmi/java-course repo
$ curl https://api.github.com/repos/fmi/java-course
```

---

#### Представяне на ресурси

@ul

- Ресурсите са концептуално разделени от представянията, които се връщат към клиентите.
- Например сървър пази ресурсите си в база от данни и на файловата система, а връща на клиентите ресурси под формата на HTML, JSON и XML.
- Не винаги върнатото представяне е вътрешното представяне.

@ulend

---

#### REST provides a uniform interface

<small>
- REST предоставя единен интерфейс (контракт) между клиента и сървъра.
- Контрактът се изгражда въз основа на ресурсите.
- [Github REST API](https://developer.github.com/v3/)

</small>

```bash
# Github API does not support text/html
# instead use application/json
$ curl --header "Accept: text/html" \
  https://api.github.com/repos/fmi/java-course
# repozzz resource does not exist
$ curl https://api.github.com/repozzz/fmi/java-course
```

---

## JSON

---

@ul

- Форматите за обмен на данни са зависими от използваната архитектура:
  - Уеб услуги, базирани на SOAP - само XML
  - Уеб услуги, базирани на REST - JSON, XML и други.

@ulend

---

#### JavaScript Object Notation (JSON)

@ul

- един от най-популярните формати за обмен на данни
- произлиза от JavaScript
- използва се за предаване на структурирани данни по мрежова комуникация
- намира широко приложение в RESTful уеб приложенията за предоставяне на данни

@ulend

---

#### JSON - характеристики

@ul

- web friendly - тривиално парсване (за разлика от XML)
- кратък и интуитивен
- разбираем едновременно от човек и машина
- езиково независима спецификация - [RFC 7159](https://tools.ietf.org/html/rfc7159)
- почти всички езици поддържат JSON

@ulend

---

#### Пример

```bash
$ curl https://api.github.com/users/kelseyhightower
```

```json
{
  "login": "kelseyhightower",
  "name": "Kelsey Hightower",
  "company": "Google, Inc",
  "location": "Portland, OR",
  "email": null,
  "hireable": true,
  "followers": 10041,
  "following": 13
}
```

---

#### Типове данни

@ul

- Boolean - `true` / `false`
- Number - `42`, `3.14156`, `-1`
- String - `"foo"`, `"bar"`
- Array - `["John", "Anna", "Peter"]`
- Object - `{"name": "John", "age": 30}`
- Поддържа `null`, `{}`, но не и функция, дата или `undefined`

@ulend

---

#### Пример (2)

```bash
$ curl https://api.github.com/repos/kubernetes/kubernetes/tags
```

```json
[
  {
    "name": "v1.14.0-alpha.0",
    "commit": {
      "sha": "1f56cd801e795fd063ec3e61fe4f6fa8841f4222"
    }
  },
  {
    "name": "v1.13.2-beta.0",
    "commit": {
      "sha": "efe48f3cd6436737d37fd2fcd6beb9e2328f7cce"
    }
  }
]
```

---

#### Java библиотеки за работа с JSON


@ul

- [google/gson](https://github.com/google/gson)
- [FasterXML/jackson](https://github.com/FasterXML/jackson)
- ...

@ulend

---

#### `google/gson`

@ul

- Предоставя лесен механизъм за преобразуване от Java обект към JSON и обратно
    - .toJson() и .fromJson()
- Не изисква добавяне на анотации или прекомпилиране
- Позволява вмъкването на custom serializer/deserializer
- Комплексен Builder
- [Gson User Guide](https://github.com/google/gson/blob/master/UserGuide.md)

@ulend

---

#### `.toJson(Object src)`

```java
public class Developer {
    private String name;
    private int age;
    public Developer(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

```java
Developer dev = new Developer("Kelsey", 28);
Gson gson = new Gson();
String json = gson.toJson(dev);
// {"name":"Kelsey","age":28}
System.out.println(json);
```

---

#### `.fromJson(String json, Class<T> classOfT)`

```java
String json = "{\"name\": \"Wesley\", \"age\": 20 }";
Gson gson = new Gson();
Developer dev = gson.fromJson(json, Developer.class);
// Wesley, 20 years old
System.out.printf("%s, %d years old%n",
    dev.getName(), dev.getAge());
```

---

#### `.fromJson(Reader json, Class<T> classOfT)`

```java
Gson gson = new Gson();
FileReader reader = new FileReader(new File("devs.json"));
// unchecked conversion
List<Developer> devs = gson.fromJson(reader, List.class);
```

---

#### `.fromJson(String json, Type typeOfT)`

```java
List<Developer> devs = List.of(
        new Developer("Kelsey", 28),
        new Developer("Wesley", 20));
Gson gson = new Gson();
String json = gson.toJson(devs);
Type type = new TypeToken<List<Developer>>(){}.getType();
List<Developer> devsAgain = gson.fromJson(json, type);
System.out.println(devsAgain.size()); // 2
```

---

## Java HTTP Client

@ul

- Част от JDK-то от Java 11 (септември 2018)
- Използва се за request-ване на HTTP ресурси по мрежата и обработка на response-а
- Поддържа HTTP/1.1 и HTTP/2
- Поддържа както синхронни, така и асинхронни заявки
- Състои се от няколко класа и интерфейса в пакета `java.net.http`

@ulend

---

#### Пример: GET заявка, която печата тялото на reponse-a като низ

```java
HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create("http://wikipedia.org/"))
      .build();
client.sendAsync(request, BodyHandlers.ofString())
      .thenApply(HttpResponse::body)
      .thenAccept(System.out::println)
      .join();
```

---

#### `HttpClient`

<small>
- "Входната точка" на API-то. Представлява клиент за пращане на HTTP заявки и получаване на отговори
- Инстанция на `HttpClient` се създава от статичен builder метод на класа
- Builder-ът може да се ползва за конфигуриране на клиента (предпочитана версия на протокола, да следва ли redirects, ползва ли прокси и т.н.)
- веднъж създаден, е immutable, но може да се ползва за пращане на много заявки

</small>

```java
var client = HttpClient.newHttpClient();
var client = HttpClient.newBuilder().build(); // equivalent
```

---

#### Пример

```java
HttpClient client = HttpClient.newBuilder()
      .version(Version.HTTP_2)
      .followRedirects(Redirect.SAME_PROTOCOL)
      .proxy(ProxySelector.of(
              new InetSocketAddress("www-proxy.com", 8080)))
      .authenticator(Authenticator.getDefault())
      .build();
```

---

#### `HttpRequest`

- Клас, представляващ HTTP заявка, включително URI, метод, хедъри и т.н.
-  `HttpRequest` инстанция се създава от статичен builder метод на класа
- Builder-ът може да се ползва за конфигуриране на
  - URI и метод на заявката, хедъри, тяло (ако има)
- съдържа тези "готови" методи: `GET()`, `POST()`, `DELETE()` и `PUT()`. Останалите се конфигурират с `method()`
  - методът по подразбиране е GET() и може да се пропусне
- веднъж създаден, е immutable, но може да се праща многократно

---

#### Пример: Създаване на `HttpRequest`

```java
var request1 = HttpRequest.newBuilder(URI.create("https://www.apple.com/"))
        .build();
var request2 = HttpRequest.newBuilder()
        .uri(URI.create("https://www.apple.com/"))
        .build(); // equivalent

var request3 = HttpRequest.newBuilder()
        .uri(URI.create("http://openjdk.java.net/"))
        .timeout(Duration.ofMinutes(1))
        .header("Content-Type", "application/json")
        .POST(BodyPublishers.ofFile(Paths.get("file.json")))
        .build();
```

---

#### `HttpRequest.BodyPublisher`

<small>
- Ако заявката има тяло (например POST заявка), този клас отговаря за "публикуването" на съдържанието на тялото от даден източник, например низ
- В класа `BodyPublishers` има factory методи за получаване на готови често използвани типове

</small>

```java
BodyPublishers::ofString
BodyPublishers::ofFile
BodyPublishers::ofByteArray
BodyPublishers::ofInputStream
```

---

#### `HttpResponse`

<small>
- Интерфейс, представляващ HTTP response, включително header-а и body-то (ако има)
- Инстанции на имплементации на този интерфейс се получават в резултат на пращане на `HttpRequest` от `HttpClient`

</small>

```java
T body()                     // тялото на отговора
HttpHeaders headers()        // хедърите на отговора
HttpRequest request()        // HttpRequest-а, отговарящ на този отговор
int statusCode()             // HTTP статус кода
URI uri()                    // URI-то на заявката
HttpClient.Version version() // версията на HTTP протокола
```

---

#### `HttpResponse.BodyHandler`

<small>
- Функционален интерфейс, който приема информация за response-a (статус код и хедъри) и връща `BodySubscriber`, който се грижи за "консумирането" (т.е. обработката) на тялото на response-a
- В класа `BodyHandlers` има фактори методи за получаване на готови често използвани типове

</small>

```java
BodyHandlers::ofByteArray
BodyHandlers::ofFile
BodyHandlers::ofString
BodyHandlers::ofInputStream
```

---

#### `HttpResponse.BodySubscriber`

- "Абонира се" за тялото на response-a и трансформира байтовете му в друга форма (низ, файл и т.н.)
- В класа `BodySubscribers` има factory методи за получаване на готови често използвани типове

---

#### Синхронни и асинхронни заявки

- Зявките могат да се пращат синхронно или асинхронно
- Синронното API блокира, докато не се върне `HttpResponse`
- Асинхронното API връща веднага инстанция на `CompletableFuture`, което завършва с `HttpResponse`, когато стане наличен
- `BodyHandler`-ът за всяка заявка определя как да се обработи тялото на response-а, ако има такова

---

#### Синхронни и асинхронни заявки

```java
HttpResponse<String> response =
      client.send(request, BodyHandlers.ofString());
System.out.println(response.statusCode());
System.out.println(response.body());
```

```java
client.sendAsync(request, BodyHandlers.ofString())
      .thenApply(response -> { System.out.println(response.statusCode());
                               return response; } )
      .thenApply(HttpResponse::body)
      .thenAccept(System.out::println);
```

---

#### HTTP/2

- Java HTTP Client-ът поддържа както HTTP/1.1, така и HTTP/2
- По подразбиране, клиентът праща заявките по HTTP/2
- Заявките към сървъри, които не поддържат HTTP/2, автоматично се "downgrade"-ват до HTTP/1.1

---

## Въпроси

@snap[south span-100]
@fab[github] [fmi/java-course](https://github.com/fmi/java-course)
@fab[youtube] [MJT2021](https://www.youtube.com/playlist?list=PLew34f6r0Pxy8PvaJ83pa4r76XCmZR657)
@snapend
