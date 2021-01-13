# Secret Santa Wish List (remastered) :santa: :gift: :christmas_tree:

`Краен срок: 15.01.2021, 23:45`

Задачата за третото (и последно) домашно този път е с добре положени основи - десетото упражнение за мрежи, в което разработихме Secret Santa Wish List клиент-сървър приложение.

### Wish List User

Ако искаме сценарият ни да бъде с една идея по-реалистичен, би било редно един ученик да може да взима за изпълнение списъка с желания на произволен ученик, без своя собствен.
За да стане това възможно, ще добавим и функционалност за потребителски профили - учениците ще могат да се регистрират и влизат в системата, както и да изпращат желания за подарък.

- Всеки ученик може да се регистрира в платформата с уникален username, който може да съдържа само alphanumeric characters, `-`, `.` и `_`
- За влизане в системата, ученикът използва username и парола

### Wish List Client

- Всеки ученик може да изпраща желание за подарък на който и да е регистриран ученик (вкл. за себе си) към сървъра. С една команда за изпращане, може да се изпрати най-много един подарък. Ученикът, за когото е подаръкът, се идентифицира с username, а името на подаръка може да е съставено от произволен брой думи
- Учениците могат да вземат за изпълнение списъка с желания за подарък на някой случайно избран ученик, без своя собствен
- Учениците могат да излязат от системата, без да прекратяват връзката със сървъра
- Учениците могат да прекратят връзката си със сървъра по всяко време (това автоматично ги logout-ва)
- Клиентът изпраща всяка команда като низ с терминиращ(и) символ(и) за нов ред

### Wish List Server

- Сървърът трябва да може да обслужва множество клиенти едновременно
- Сървърът получава команди от клиентите и връща подходящ резултат
- Когато получи **get-wish** команда, сървърът избира ученик от списъка на случаен принцип (без този, който изпълнява командата) и след като върне резултата, изтрива текущия списък с желания на върнатия ученик
- Спазвайте точния формат на съобщенията, които сървърът връща. Може да ги намерите описани [тук](https://github.com/fmi/java-course/blob/master/homeworks/03-wishlist/README.md#Пример)
- Сървърът изпраща всеки отговор на команда като низ с терминиращ(и) символ(и) за нов ред
- Сървърът трябва да има публичен конструктор **WishListServer(int port)**, който приема единствен аргумент - порт, на който да се стартира сървъра
- Сървърът трябва да има два публични метода **start()** и **stop()**, извикването на които стартира и спира сървъра

### Описание на клиентските команди

```bash
=> register <username> <password> # регистрира се в системата и автоматично влиза в нея
=> login <username> <password> # влиза в системата
=> logout # излиза от системата
=> post-wish <username> <wish> # изпраща желание за подарък
=> get-wish # връща списъка с желанията на случайно избран от сървъра ученик, без неговия
=> disconnect # прекратява връзката си със сървъра
```

:warning: Учениците не могат да изпълняват никоя от следните команди, ако не са влезли в системата:
- `post-wish`
- `get-wish`
- `logout`

### Пример

```bash
# start a wish list client from the command line
$ java bg.sofia.uni.fmi.mjt.wish.list.WishListClient

=> register Zdravko Abcd1234
[ Username Zdravko successfully registered ]

# or
[ Username Zdravko is already taken, select another one ]

=> register Ko$io Abcd1234
[ Username Ko$io is invalid, select a valid one ]

=> login Zdravko Abcd1234
[ User Zdravko successfully logged in ]

# or
[ Invalid username/password combination ]

=> post-wish Zdravko kolelo
[ Gift kolelo for student Zdravko submitted successfully ]

# or
[ The same gift for student Zdravko was already submitted ]

# or
[ Student with username Zdravko is not registered ]

# or
[ You are not logged in ]

=> get-wish
[ Zdravko: [kolelo] ]

# or
[ Zdravko: [kolelo, topka] ]

# or
[ There are no students present in the wish list ]

# or
[ You are not logged in ]

=> logout
[ Successfully logged out ]

# or
[ You are not logged in ]

=> disconnect
[ Disconnected from server ]

=> some random commands
[ Unknown command ]
```

### Тестване

Този път, очакваме да напишете тестове, но само за бизнес логиката, a не и за мрежовата комуникация.
Ако дизайнът ви го позволява, ще се справите лесно с това. В противен случай, ще се наложи да рефакторирате предните си решения и код - да добавите някой нов клас, или абстракция.

#### Code Coverage

Очакваният минимален code coverage е **30%**. Това е така, понеже очакваме, че голяма част от кода ви ще се грижи за мрежовата комуникация.

### Примерна структура на проекта

Добра практика при създаването на приложения тип клиент-сървър е да отделяте клиента и сървъра в отделни проекти. Това предотвратява грешки от типа, класове/интерфейси от клиента да се ползват от сървъра, или обратно. Също така, в реална ситуация, бихме искали да пакетираме и разпространяваме поотделно клиентската и сървърната част на нашето приложение.

В [sapera.org](http://grader.sapera.org/) качете *oбщ* `zip` архив на `src` и `test` директориите от *двата* проекта.

```bash
src
╷
├─ bg/sofia/uni/fmi/mjt/wish/list
|  ├─ WishListClient.java
|  ├─ WishListServer.java
|  └─ (...)
test
╷
└─ bg/sofia/uni/fmi/mjt/wish/list
   ├─ WishListClientTest.java
   ├─ WishListServerTest.java
   └─ (...)
```

### Dependencies

```bash
# (eventually) for testing
wget https://repo1.maven.org/maven2/org/mockito/mockito-all/1.10.19/mockito-all-1.10.19.jar
```
