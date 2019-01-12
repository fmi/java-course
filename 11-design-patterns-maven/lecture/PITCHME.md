## Design Patterns и maven

_08.01.2019_

---

#### S.O.L.I.D дизайн принципи

![S.O.L.I.D.](images/11.1-solid.png?raw=true)

---

#### S.O.L.I.D: Single responsibility principle

@ul

- A class should have only a single responsibility (i.e. changes to only one part of the software's specification should be able to affect the specification of the class)
- "A class should have only one reason to change."

@ulend

---

#### S.O.L.I.D: Open/closed principle

@ul

- software entities (classes, modules, functions, etc.) should be open for extension, but closed for modification

@ulend

---

#### S.O.L.I.D: Liskov substitution principle

@ul

- objects in a program should be replaceable with instances of their subtypes without altering the correctness of that program

@ulend

---

#### S.O.L.I.D: Interface segregation principle

@ul

- A client should not be forced to implement an interface that it doesn’t use.
- Many client-specific interfaces are better than one general-purpose interface

@ulend

---

#### S.O.L.I.D: Dependency inversion principle

@ul

- high level modules must not depend on low level modules, but they should depend on abstractions.
- one should “depend upon abstractions, not concretions”.

@ulend

---

#### Design Patterns

Шаблоните за дизан са

@ul

- обобщени добри практики
- стандартни решения на общи / често срещани проблеми

@ulend

---

![Design Patterns](images/11.2-design-patterns.png?raw=true)

---

#### Design Patterns - ползи

@ul

- Използване на колективния опит за софтуерно проектиране за доказани решения на често срещани проблеми
- Поощряват reusability на кода, което води до по-качествен и лесен за поддръжка код
- Обща терминология, което помага на програмистите да се разбират лесно

@ulend

---

#### Design Patterns - видове

@ul

- Creational Patterns
- Structural Patterns
- Behavioral Patterns

@ulend

---

#### Creational Patterns

@ul

- Осигуряват начин да се създават обекти на класове, скривайки логиката по създаването им (вместо да се инстанцират директно чрез оператора `new`)
- Factory, Abstract Factory, Builder, Singleton, Prototype

@ulend

---

#### Structural Patterns

@ul

- Свързани са с композицията на класове и обекти. Концепцията за наследяване се използва за да се композират интерфейси и да се дефинират начини за композиране на обекти за получаване на нови функционалности
- Adapter, Composite, Proxy, Flyweight, Facade, Bridge, Decorator

@ulend

---

#### Behavioral Patterns

@ul

- Свързани са с комуникацията между обекти
- Template Method, Mediator, Chain of Responsibility, Observer, Strategy, Command, State, Visitor, Interpreter, Iterator, Memento

@ulend

---

#### Factory

@ul

- creational pattern
- създаваме обект без да expose-ваме логиката по създаването му на клиента
- използва се, когато имаме родителски клас с няколко наследници и искаме да създаваме един от наследниците на родителския клас според подаден параметър
- примери от JDK-то: `java.util.Calendar` методите, `valueOf()` метода на wrapper класовете като `Boolean`, `Integer` и т.н., `of()` методите на `List`, `Set`, `Map`


@ulend

---

#### Factory

![Factory](images/11.3-factory-demo-diagram.png?raw=true)

---

#### Builder

@ul

- creational pattern
- решава някой проблеми на Factory pattern-а за класове с много атрубути, от които много са optional
- примери от JDK-то: `HttpClient`, `HttpRequest`

@ulend

---

#### Builder - имплементация

@ul

- Създаваме static вложен клас и копираме всички параметри от външния клас в builder класа
- builder класът трябва да има публичен конструктор с всички задължителни атрибути като параметри
- setter методи за всички опционални параметри, които връщат същата builder инстанция
- build() method, който връща обекта

@ulend

---

#### Singleton

@ul

- creational pattern
- клас, от който може да съществува най-много една инстанция
- имплементация
  - private конструктор
  - private static член-променлива от тип същия клас, която реферира единствената инстанция на класа
  - public static метод, който връща инстанцията на класа

@ulend

---

#### Singleton

@ul

- типични употреби: logging, caching, thread pools
- В други design patterns (Factory, Builder, Facade, Prototype, …)

@ulend

---

#### Flyweight

@ul

- structural pattern
- позволява да се съберат повече обекти в наличната памет чрез споделяне на общите части на state-a между множество обекти
- намялва memory footprint-а на програмата
- може също да подобри бързодействието в приложения, където инстанцирането на обектите е скъпа операция

@ulend

---

#### Flyweight

@ul

- flyweight обектите са immutable: всяка операция, която променя състоянието им трябва да се изпълнява от factory-то
- примери от JDK-то: `Integer.valueOf(int)`, `Byte.valueOf(byte)` и подобните са останалите wrapper типове

@ulend

---

#### Flyweight - имплементация

@ul

- интерфейс, който дефинира операциите, които клиентския код може да извършва върху flyweight обектите
- една или повече конкретни имплементации на този интерфейс
- factory, което отговаря за инстанциране и кеширане

@ulend

---

#### Iterator

@ul

- behavioral pattern
- позволява последователното обхождането на поредица от обекти
- примери от JDK-то: `java.util.Iterator` и обхождането на колекции

@ulend

---

#### Iterator

![Iterator](images/11.4-iterator-demo-diagram.png?raw=true)

---

#### Command

@ul

- behavioral pattern
- използва се за имплементиране на loose coupling в модел тип заявка-отговор
- пример от JDK-то: `java.lang.Runnable`

@ulend

---

#### Command

![Command](images/11.5-command-demo-diagram.png?raw=true)

---

#### Observer

@ul

- behavioral pattern
- удобен е, когато се интересуваме от състоянието на даден обект и искаме да бъдем нотифицирани, когато има промяна в състоянието
- обектът, който наблюдава за промяна на състоянието на друг обект, се нарича *Observer*, а наблюдаваният обект се нарича *Subject*

@ulend

---

#### Observer

![Observer](images/11.6-observer-demo-diagram.png?raw=true)

---

#### Strategy

@ul

- behavioral pattern
- прилага се, когато имаме множество алгоритми за дадена задача и клиентът решава по време на изпълнение, коя имплементация на алгоритъм да се ползва
- примери от JDK-to: `Collections.sort()`, който сортира по различен критерий/алгоритъм в зависимост от подадения `Comparator`

@ulend

---

#### Design Patterns - примери с код

@ul

- може да разгледате приложените [code snippets](https://github.com/fmi/java-course/tree/master/11-design-patterns-maven/lab/snippets/design-patterns)
- тук може да намерите информация и примери с код на Java за голям брой design patterns (не само 23-те от *Gang-of-Four*): https://github.com/iluwatar/java-design-patterns

@ulend

---

#### `Java ARchive - JAR`

@ul

- файлов формат за пакетиране, който най-често събира множество Java class файлове и ресурси в един файл, който е готов за използване и разпространение
- просто архив, който съдържа манифест
- дефакто стандарт за пакетиране на Java библиотеки/приложения

@ulend

---

#### `Executable JAR`

```
project
  └─ bin/
    └─ com/project/
      └─ Main.class
  └─ src/
    └─ com/project/
      └─ Main.java
```

```bash
javac -d bin/ src/com/project/Main.java
cd bin/
jar cvfe project.jar com.project.Main *
java -jar project.jar
# or java -cp bin/project.jar com.project.Main

jar tf project.jar
unzip project.jar
cat META-INF/MANIFEST.MF
```

---

#### `Executable JAR with dependency`

```
project
  └─ bin/
    └─ com/project/
      └─ Main.class
  └─ src/
    └─ com/project/
      └─ Main.java
  └─ lib/
    └─ gson.jar
```

```bash
javac -cp lib/*.jar -d bin/ src/com/project/Main.java
cd bin
jar cvfe project.jar com.project.Main *

cd ..
java -cp bin/project.jar:lib/* com.project.Main
```

---

#### Classpath

- параметър в JVM или Java compiler-a, който специфицира местоположението на потребителските класове или пакети
- може да бъде специфициран чрез параметър на команда или environment variable

---

#### The hard way

@ul

- Нужни са доста стъпки за създането на jar
- Потребителите най-често искат една бутонка
- А програмистите - една команда

@ulend

---

#### `maven`

@ul

- Инструмент, който:
  - Улеснява build процеса
  - Предоставя dependency management (transitive dependencies, dependency scope)
  - Предоставя release management
  - Налага конвенции, които са се утвърдили като best practices (Convention over Configuration)

@ulend

---

#### The maven way

```bash
mvn -B archetype:generate \
  -DarchetypeGroupId=org.apache.maven.archetypes \
  -DgroupId=com.project \
  -DartifactId=my-app

cd my-app
ls -la

mvn compile
mvn test
mvn package

java -cp target/my-app-1.0-SNAPSHOT.jar com.project.App
# or
vi target/my-app-1.0-SNAPSHOT.jar 
java -jar target/my-app-1.0-SNAPSHOT.jar
```

---

#### Standard Directory Layout

- maven налага конвенция за структурата на проекта
  - ${baseDir} е директорията, която съдържа pom.xml
  - ${baseDir}/src/main/java - сорс кода на приложението
  - ${basedir}/src/main/resources - ресурси
  - ${baseDir}/src/test/java - тестовете на приложението
  - ${basedir}/src/test/resources - тестови ресурси

---

#### Add dependency

1. Find you dependency from https://mvnrepository.com/repos/central.
2. Add the dependency tag to your pom.xml.
3. mvn clean install

---

#### Полезни четива

- [JAR files](https://docs.oracle.com/javase/tutorial/deployment/jar/index.html)
- [Maven User Centre](https://maven.apache.org/users/index.html)
