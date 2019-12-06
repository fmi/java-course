## Design Patterns

_04.12.2019_

---

#### Предната лекция говорихме за:

@ul

- Lambda изрази
- Stream API

@ulend

---

#### Stream vs Collection

@ul

- Потоците не са алтернатива на колекциите
- Колекциите са отговорни за съхранение и достъп до данни

@ulend

---

#### Особености на Stream

@ul

- нямат собствено хранилище, а използват източник на данни (колекция, файл и т.н.)
- функционална същност – не модифицират вътрешните данни
- колекциите винаги имат краен брой елементи, докато Stream може да бъде безкраен
- елементите могат да се "консумират" само веднъж, подобно на итератор
- позволяват lazy операции
- позволяват паралелно изпълнение на операциите

@ulend

---

#### Stateless и stateful операции върху потоци

@ul

- Stream операциите могат да се разделят на *stateless* и *stateful*.
- Stateless операциите не съхраняват данни между обработката на отделните елементи на потока
    - `filter()`, `map()`, `flatMap()`
- Stateful операциите могат да предават състояние от вече обработените елементи към обработката на следващия
    - `distinct()`, `limit()`, `sorted()`, `reduce()`, `collect()`

@ulend

---

#### Паралелно изпълнение на stream операциите

@ul

- Stateless операциите не представляват проблем при паралелно изпълнение
- Stateful операциите
    - (без `limit()`), приложени към безкраен поток, не завършват никога
    - могат да дадат различен резултат според това дали се изпълняват последователно или паралелно
    - имат performance impact, защото често изискват обработка на всички елементи на няколко паса

@ulend

---

#### Паралелно изпълнение на stream операциите

@ul

- Малките потоци обикновено се обработват по-бързо последователно
- Ако stateful операциите не могат да се заменят със stateless, кодът трябва внимателно да се проектира за паралелно изпълнение, или да се избегне паралелизма

@ulend

---

#### Функции от по-висок ред

@ul

- Функции, които приемат като аргумент функция или връщат функция като резултат, се наричат *функции от по-висок ред*
- Функциите от по-висок ред ни предоставят средство, с което да композираме логика, комбинирайки функции

@ulend

---

#### Въпроси от предната лекция

@ul

- Как да извикаме чрез method reference overloaded методи и конструктори? [Отговор](https://github.com/fmi/java-course/tree/master/09-design-patterns/snippets/MethRefExample.java)
- `flatmap()` на произволна дълбочина (т.е. рекурсивно) ли работи?
- Как се обработват изключения по време на изпълнение на stream pipeline?
- Може ли да се направи stream от (инстанции на) анонимен клас?


@ulend

---

#### Днес ще разгледаме:

@ul

- S.O.L.I.D. дизайн принципите
- Design Patterns

@ulend

---

#### S.O.L.I.D дизайн принципи

![S.O.L.I.D.](images/09.1-solid.png)

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

- A client should not be forced to implement an interface that it doesn’t use
- Many client-specific interfaces are better than one general-purpose interface

@ulend

---

#### S.O.L.I.D: Dependency inversion principle

@ul

- high level modules must not depend on low level modules, but they should depend on abstractions
- one should “depend upon abstractions, not concretions”

@ulend

---

#### Design Patterns

Шаблоните за дизайн са

@ul

- обобщени добри практики
- стандартни решения на общи / често срещани проблеми

@ulend

---

![Design Patterns](images/09.2-design-patterns.jpg)

---

#### Design Patterns - ползи

@ul

- Използване на колективния опит за софтуерно проектиране за доказани решения на често срещани проблеми
- Поощряват reusability на кода, което води до по-качествен и лесен за поддръжка код
- Обща терминология, която помага на програмистите да се разбират лесно

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

- Осигуряват различни начини за създаване на по-сложни класове чрез наследяване и композиция на по-просто класове.
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
- примери от JDK-то: `valueOf()` метода на wrapper класовете като `Boolean`, `Integer` и т.н., `of()` методите на `List`, `Set`, `Map`, `of()` метода на `Path`, `of()` метода на `Stream`


@ulend

---

#### Factory

![Factory](images/09.3-factory-demo-diagram.png)

---

#### Builder

@ul

- creational pattern
- решава някои проблеми на Factory pattern-а за класове с много атрубути, от които много са optional
- примери от JDK-то: `StringBuilder`, `StringBuffer`, `HttpClient`, `HttpRequest`

@ulend

---

#### Builder - имплементация

@ul

- Създаваме `static` вложен клас и копираме всички параметри от външния клас в builder класа
- builder класът трябва да има публичен конструктор с всички задължителни атрибути като параметри
- setter методи за всички опционални параметри, които връщат същата builder инстанция
- `build()` метод, който връща обекта (`this`)

@ulend

---

#### Singleton

@ul

- creational pattern
- клас, от който може да съществува най-много една инстанция
- имплементация
  - `private` конструктор
  - `private static` член-променлива от тип същия клас, която реферира единствената инстанция на класа
  - `public static` метод, който връща инстанцията на класа

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
- намалява memory footprint-а на програмата
- може също да подобри бързодействието в приложения, където инстанцирането на обектите е скъпа операция

@ulend

---

#### Flyweight

@ul

- flyweight обектите са immutable: всяка операция, която променя състоянието им трябва да се изпълнява от factory-то
- примери от JDK-то: `String` с имплементацията на string pool-a, `Integer.valueOf(int)`, `Byte.valueOf(byte)` и подобните са останалите wrapper типове

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
- позволява последователното обхождане на поредица от обекти
- примери от JDK-то: `java.util.Iterator` и обхождането на колекции

@ulend

---

#### Iterator

![Iterator](images/09.4-iterator-demo-diagram.png)

---

#### Command

@ul

- behavioral pattern
- използва се за имплементиране на loose coupling в модел тип заявка-отговор
- пример от JDK-то: `java.lang.Runnable`

@ulend

---

#### Command

![Command](images/09.5-command-demo-diagram.png)

---

#### Observer

@ul

- behavioral pattern
- удобен е, когато се интересуваме от състоянието на даден обект и искаме да бъдем нотифицирани, когато има промяна в състоянието
- обектът, който наблюдава за промяна на състоянието на друг обект, се нарича *Observer*, а наблюдаваният обект се нарича *Subject*
- пример от JDK-то: `java.util.Observer`, `java.util.Observable`

@ulend

---

#### Observer

![Observer](images/09.6-observer-demo-diagram.png)

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

- може да разгледате приложените [code snippets](https://github.com/fmi/java-course/tree/master/09-design-patterns/snippets/design-patterns)
- тук може да намерите информация и примери с код на Java за голям брой design patterns (не само 23-те от *Gang-of-Four*): https://github.com/iluwatar/java-design-patterns

@ulend

---

## Въпроси

@snap[south span-100]
@fab[github] [fmi/java-course](https://github.com/fmi/java-course)
@snapend
