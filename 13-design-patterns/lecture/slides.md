class: center, middle

# Design Patterns

11.01.2023

---

### Предната лекция говорихме за:

- HTTP
- REST
- JSON
- Библиотеки за работа с JSON

---

### Днес ще разгледаме:

- библиотеката за работа с HTTP в JDK-то
- Design Patterns

---

### Design Patterns

Шаблоните за дизайн са

- обобщени добри практики
- стандартни решения на общи / често срещани проблеми

---

### Design Patterns

.center[![Design Patterns](images/12.2-design-patterns.jpg)]

---

### Design Patterns - ползи

- Използване на колективния опит за софтуерно проектиране за доказани решения на често срещани проблеми
- Поощряват reusability на кода, което води до по-качествен и лесен за поддръжка код
- Обща терминология, която помага на програмистите да се разбират лесно

---

### Design Patterns - видове

- Creational Patterns
- Structural Patterns
- Behavioral Patterns

---

### Creational Patterns

- Осигуряват начин да се създават обекти на класове, скривайки логиката по създаването им (вместо да се инстанцират директно чрез оператора `new`)
- Factory, Abstract Factory, Builder, Singleton, Prototype

---

### Structural Patterns

- Осигуряват различни начини за създаване на по-сложни класове чрез наследяване и композиция на по-прости класове
- Adapter, Composite, Proxy, Flyweight, Facade, Bridge, Decorator

---

### Behavioral Patterns

- Свързани са с комуникацията между обекти
- Template Method, Mediator, Chain of Responsibility, Observer, Strategy, Command, State, Visitor, Interpreter, Iterator, Memento

---

class: center, middle

# Creational Design Patterns

---

### Factory

.center[![Factory](images/12.3-factory.png)]

---

### Factory

- creational pattern
- създаваме обект без да expose-ваме логиката по създаването му на клиента
- използва се i когато имаме родителски клас с няколко наследници и искаме да създаваме един от наследниците на родителския клас според подаден параметър
- примери от JDK-то: `valueOf()` метода на wrapper класовете като `Boolean`, `Integer` и т.н., `of()` методите на `List`, `Set`, `Map`, `of()` метода на `Path`, `of()` метода на `Stream`

---

### Factory

.center[![Factory](images/12.4-factory-demo-diagram.png)]

---

### Builder

.center[![Builder](images/12.5-builder.png)]

---

### Builder

- creational pattern
- решава някои проблеми на Factory pattern-а за класове с много атрубути, от които много са optional
- примери от JDK-то: `StringBuilder`, `StringBuffer`, `HttpClient`, `HttpRequest`

---

### Builder - имплементация

- Създаваме `static` вложен клас и копираме всички параметри от външния клас в builder класа
- builder класът трябва да има публичен конструктор с всички задължителни атрибути като параметри
- setter методи за всички опционални параметри, които връщат същата builder инстанция
- `build()` метод, който връща обекта (`this`)

---

### Singleton

.center[![Singleton](images/12.6-singleton.png)]

---

### Singleton

- creational pattern
- клас, от който може да съществува най-много една инстанция
- имплементация
    - `private` конструктор
    - `private static` член-променлива от тип същия клас, която реферира единствената инстанция на класа
    - `public static` метод, който връща инстанцията на класа

---

### Singleton

- типични употреби: logging, caching, thread pools
- В други design patterns (Factory, Builder, Facade, Prototype, …)

---

class: center, middle

# Structural Design Patterns

---

### Flyweight

.center[![Flyweight](images/12.7-flyweight.png)]

---

### Flyweight

- structural pattern
- позволява да се съберат повече обекти в наличната памет чрез споделяне на общите части на state-a между множество обекти
- намалява memory footprint-а на програмата
- може също да подобри бързодействието в приложения, където инстанцирането на обектите е скъпа операция

---

### Flyweight

- flyweight обектите са immutable: всяка операция, която променя състоянието им, трябва да се изпълнява от factory-то
- примери от JDK-то: `String` с имплементацията на string pool-a, `Integer.valueOf(int)`, `Byte.valueOf(byte)` и подобните са останалите wrapper типове

---

### Flyweight - имплементация

- интерфейс, който дефинира операциите, които клиентския код може да извършва върху flyweight обектите
- една или повече конкретни имплементации на този интерфейс
- factory, което отговаря за инстанциране и кеширане

---

class: center, middle

# Behavioral Design Patterns

---

### Iterator

.center[![Iterator](images/12.8-iterator.png)]

---

### Iterator

- behavioral pattern
- позволява последователното обхождане на поредица от обекти
- примери от JDK-то: `java.util.Iterator` и обхождането на колекции

---

### Iterator

.center[![Iterator](images/12.9-iterator-demo-diagram.png)]

---

### Command

.center[![Command](images/12.10-command.png)]

---

### Command

- behavioral pattern
- при него, обект (*команда*) енкапсулира информацията, нужна за извършване на действие или trigger-ване на събитие в по-късен момент
- използва се за имплементиране на loose coupling в модел тип заявка-отговор
- пример от JDK-то: `java.lang.Runnable` и thread executors

---

### Command

.center[![Command](images/12.11-command-demo-diagram.png)]

---

### Observer

.center[![Observer](images/12.12-observer.png)]

---

### Observer

- behavioral pattern
- удобен е, когато се интересуваме от състоянието на даден обект и искаме да бъдем нотифицирани, когато има промяна в състоянието
- обектът, който наблюдава за промяна на състоянието на друг обект, се нарича *Observer*, а наблюдаваният обект се нарича *Subject*
- пример от JDK-то: `java.util.Observer`, `java.util.Observable`

---

### Observer

.center[![Observer](images/12.13-observer-demo-diagram.png)]

---

### Strategy

.center[![Strategy](images/12.14-strategy.png)]
---

### Strategy

- behavioral pattern
- прилага се, когато имаме множество алгоритми за дадена задача и клиентът решава по време на изпълнение, коя имплементация на алгоритъм да се ползва
- примери от JDK-to: `Collections.sort()`, който сортира по различен критерий/алгоритъм в зависимост от подадения `Comparator`

---

### Design Patterns - примери с код

- може да разгледате приложените [code snippets](/13-design-patterns/snippets/design-patterns)
- хубави обяснения и примери с псевдокод [Refactoring Guru: Design Patterns](https://refactoring.guru/design-patterns)
- тук може да намерите информация и примери с код на Java за голям брой design patterns (не само 23-те от *Gang-of-Four*): [Java Design Patterns](https://github.com/iluwatar/java-design-patterns)

---

## Въпроси?

.font-xl[.ri-github-fill.icon-inline[[fmi/java-course](https://github.com/fmi/java-course)]]

.font-xl[.ri-youtube-fill.icon-inline[[MJT2023](https://www.youtube.com/playlist?list=PLew34f6r0PxyQmWCgCP5gKeoBuzXJxq1w)]]
