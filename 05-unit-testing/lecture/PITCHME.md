## Unit Testing

_06.11.2018_

---

#### Предната лекция говорихме за:

@ul

- Колекции (collections)
- Шаблонни типове (generics)

@ulend

---

#### Днес ще разгледаме:

@ul

- Как се тества софтуер, и кому е нужно
- Какво е unit testing
- JUnit
- Test-Driven Development (TDD)
- Stubbing и mocking

@ulend

---

#### Защо е нужно да си тестваме кода?

---

#### Кога да тестваме?

![Relative cost of fixing defects](images/05.1-relative-cost.png?raw=true)

---

#### Тестването не е фаза, а е процес

![Testing is a process](images/05.2-testing-process.png?raw=true)

---

#### Основни видове тестове

@ul

- Ръчни:
  - В професионалната софтуерна разработка, липса на автоматични тестове == липса на тестове въобще

- Автоматични:
  - функционални
  - нефункционални

@ulend

---

@ul

- Функционални тестове
  - unit тестове
  - integration тестове

- Нефункционални тестове
  - performance тестове
  - stress тестове
  - crash тестове
  - security тестове
  - usability тестове

@ulend

---

#### Още видове тестове

![More test types](images/05.3-more-tests.png?raw=true)

---

#### Unit Testing

@ul

- Unit test е код, който изпълнява специфична, „атомарна“ (т.е. която не може да се разбие по смислен начин на по-малки) функционалност на кода, която да бъде тествана
- Един unit test цели да тества малък фрагмент код - обикновено един метод или най-много един клас

@ulend

---

@ul

- Unit тестовете гарантират, че кодът работи както очакваме.
- Подсигуряват, че кодът ще продължи да работи, както се очаква, в случай че го модифицираме, за да оправим бъг, рефакторираме или разширяваме функционалността

@ulend

---

#### Малко дефиниции

@ul

- *Продуктивен код* (a.k.a. *code under test*) – това е кодът, който реализира потребителските изисквания и удовлетворява сценариите на клиентите

- Процентът на продуктивния код, който се тества от автоматични тестове, се нарича *test coverage* или *code coverage*. Високият test coverage на кода ни дава увереност да разработваме функционалности, без да се налага да правим много ръчни тестове

@ulend

---

- *Test Driven Development* (TDD) е методология, при която кодът на тестовете се пише преди продуктивния код, така че щом даден тест бъде удовлетворен (т.е. минава успешно, стане „зелен“), съответният use-case е реализиран („done“)

---

#### Още дефиниции

@ul

- *Test fixture* е фиксирано състояние на софтуера, който тестваме, което е началното условие (предусловието) за изпълняване на тестовете
- *Integration test* тества поведението на компонент или интеграцията между множество компоненти

@ulend

---

@ul

- Терминът *функционален тест* понякога се ползва като синоним на integration test
- *Performance test* измерва бързодействието (ефективността) на даден софтуер по repeatable начин

@ulend

---

## JUnit

---

#### JUnit Framework

@ul

- [JUnit](https://github.com/junit-team/junit4) е най-популярният и *de facto* стандартният testing framework в Java
- Актуалната версия е [JUnit 5](https://junit.org/junit5/)
- Засега в курса ще ползваме [JUnit 4](https://junit.org/junit4/) (защо?)

@ulend

---

#### Защо ни трябват testing frameworks?

@ul

- Улесняват ни да пишем и изпълняваме тестове
- Стандартизират разработката и поддръжката на тестове

@ulend

---

#### JUnit

@ul

- JUnit се базира на анотации
- Всеки JUnit тест е метод, анотиран с `@Test`, съдържащ се в клас, който се използва само за тестване
- Такъв клас се нарича Test Case

@ulend

---

#### Пример за JUnit тест

```java
import static org.junit.Assert.*;

@Test
public void multiplicationOfZeroIntegersShouldReturnZero() {
	// MyClass is tested
	MyClass tester = new MyClass();

	// Tests
	assertEquals("10 x 0 must be 0", 0, tester.multiply(10, 0));
	assertEquals("0 x 10 must be 0", 0, tester.multiply(0, 10));
	assertEquals("0 x 0 must be 0", 0, tester.multiply(0, 0));
} 
```

---

#### Конвенции за именуване

@ul

- Прието е да се добавя `Test` към името на класа, който се тества
- Една конвенция за имената на тестовите методи е да се използва думата „should“, например `ordersShouldBeCreated()` или `menuShouldGetActive()`
- Името трябва да описва какво проверява тестът

@ulend

---

- Друга популярна конвенция е методите да се кръщават

`given[Input]When[Condition]Then[Expected]`

```java
givenHomePageWhenUserClicksLoginButtonThenLoginPageShouldBeRendered()
givenNegativeNumbersWhenUserAppliesMultiplicationThenResultShouldBePositive()
givenVirtualMachineWhenUserDeletesItThenVolumeShouldBeDeletedAlso()
```

---

#### Статични методи на Assert класа

JUnit предоставя статични методи в класа `org.junit.Assert` за тестване на определени условия

---

__fail()__

@ul

- `fail(String message)` – фейлва теста
- Може да се използва за проверка, че определена част от кода не се достига или като временна dummy имплементация, която да се замести от реален тест

@ulend

---

@ul

- `assertTrue(String message, boolean condition)` – проверява, че булевото условие е истина
- `assertFalse(String message, boolean condition)` – проверява, че булевото условие е лъжа

@ulend

---

@ul

- `assertNull(String message, Object o)` – проверява, че обектът е null
- `assertNotNull(String message, Object о)` – проверява, че обектът не е null

@ulend

---

@ul

- `assertEquals(String message, expected, actual)` – проверява за равенство на два обекта
- масивите се сравняват по референции, не по съдържание
- assertArrayEquals(String message, expected, actual) - проверява за равенство на два масива по дължина и съдържание

@ulend

---

@ul

- `assertEquals(String message, expected, actual, delta)` – проверява за равенство на числа с плаваща точка
- делтата (delta) определя точността на сравнението

@ulend

---

@ul

- `assertSame(String message, expected, actual)` – проверява, че двете референции съвпадат
- `assertNotSame(String message, expected, actual)` – проверява, че двете референции са различни

@ulend

---

@ul

- Във всички assert методи, String параметърът (message) е опционален
- Добра практика да го подавате винаги и съобщението да е конкретно, подробно и смислено
- Важно е в случаите, в които различни програмисти пишат продуктивния код и тестовете

@ulend

---

#### Ред на изпълнение

@ul

- JUnit предполага, че всички тестови методи могат да се изпълняват в произволен ред
- Добре написаните тестове не трябва да разчитат на конкретен ред на изпълнение
- Т.е. тестовете не трябва да зависят от други тестове

@ulend

---

#### Ред на изпълнение

От JUnit 4.11 натам, може явно с анотация на класа да определите реда на изпълнение да е лексикографският ред на имената на тестовите методи.

```java
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
```

Ако имате няколко тестови класа, може да ги обедините в test suite. Изпълнението на test suite ще изпълни всички тестови класове в него в указания ред.

---

#### Пример за JUnit test suite

```java
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	MyClassTest.class,
	MySecondClassTest.class
})
public class AllTests {

}
```

---

#### JUnit анотации

@ul

- `@Test`
  - обозначава метод като тестов метод
- `@Test(expected = Exception.class)`
  - фейлва, ако методът не хвърли указаното изключение
- `@Test(timeout = 100)`
  - фейлва, ако изпълнението на метода продължи повече от 100 милисекунди

@ulend

---

#### JUnit анотации (2)

@ul

- `@Before public void method()` 
  - този метод се изпълнява преди всеки тест
  - използва се за подготовка на тестовата среда
- `@After public void method()`
  - този метод се изпълнява след всеки тест
  - използва се да зачисти тестовата среда

@ulend

---

#### Къде "живеят" unit тестовете?

@ul

- Обикновено unit тестовете се разполагат в отделен проект или в отделна source директория, за да са отделени от продуктивния код
- Няма единен стандарт - зависи с какви други tools (например за build) искате интеграция

@ulend

---

- Един вариант (maven)
```
fancy-project
  └─ src/main/java
    └─ (...)
  └─ src/test/java
    └─ (...)
```

- Друг вариант
```
fancy-project
  └─ src
    └─ (...)
  └─ test
    └─ (...)
```

---

#### Как се изпълняват?

@ul

- През IDE
- През конзола
- През build системи (maven, gradle)
- През Continuous Integration (CI) системи (Jenkins, Travis)
- Засега ще се ограничим да ги изпълняваме през IDE-то

@ulend

---

#### Code coverage plug-ins

@ul

- [EclEmma](https://www.eclemma.org/) for Eclipse
- [Code coverage runner](https://www.jetbrains.com/help/idea/code-coverage.html) for IntelliJ

@ulend

---

#### Best practices

@ul

- Не тествайте тривиален код като getters/setters
- Тествайте private методи само косвено
- Стремете се към 70-80% code coverage
- Пишете кратки, ясни и бързи unit тестове
- Избягвайте `try-catch`
- Не ползвайте `Thread.sleep`

@ulend

---

## JUnit 4 vs. JUnit 5

---

|                   | JUnit 4     | JUnit 5                 |
| ----------------- | ----------- | ----------------------- |
| Пакет             | `org.junit` | `org.junit.jupiter.api` |
| Анотиране на тест | `@Test`     | `@Test`                 |

---

#### Инициализация

|                    | JUnit 4        | JUnit 5       |
| ------------------ | -------------- | ------------- |
| Обща               | `@BeforeClass` | `@BeforeAll`  |
| Преди всеки тест   | `@Before`      | `@BeforeEach` |

---

#### Заключителни операции

|                 | JUnit 4       | JUnit 5      |
| --------------- | ------------- | ------------ |
| След всеки тест | `@After`      | `@AfterEach` |
| Общи            | `@AfterClass` | `@AfterAll`  |

---

#### Временно изключване на тестов метод или клас

| JUnit 4   | JUnit 5     |
| --------- | ----------- |
| `@Ignore` | `@Disabled` |

---

## Stubbing and mocking

---

#### Unit тестване на класове със зависимости

@ul

- Често нашите класове, за да свършат своята работа, използват други класове
- Познато ви е като композиция
- Това е съвсем очаквано и нормално :)
- Как unit тестваме такива класове?
- Да разгледаме един такъв клас

@ulend

---

```java
public class UserService {

	private UserRepository repository;
	private MailService mailService;

	public User register(String email, String password) {
		if (repository.exists(email)) {
			throw new UserAlreadyExistsException();
		}

		User user = new User(email, password);
		repository.save(user);
		mailService.sendWelcomeMail(email);
		return user;
	}
}
```

@[3-4]
@[7]
@[12]
@[13]

---

@ul

- Нека UserRepository е интерфейс, чиято задача е да съхранява User-и (in-memory, file system, database, etc.)
- Нека MailService също е интерфейс, чиято задача е да праща мейли

@ulend

---

#### Test cases for register()

@ul

- Методът register() има 2 exit point-a - следователно имаме 2 сценария за покриване
- [TC1] register() хвърля подходящо изключение, когато мейл адресът вече съществува в хранилището
- [TC2] register() запазва подходящия user в хранилището и изпраща welcome мейл, когато мейл адресът не съществува в хранилището
- Как unit тестваме UserService класа?

@ulend

---

@ul

- При unit тестване се интересуваме от функционалната коректност само на класа, който се тества
- Трябват ни инструменти, чрез които да "изолираме" композираните класове
- Композираните класове могат да бъдат трудни за инстанциране
- Например UserRepository изисква connectivity към база от данни

@ulend

---

@ul

- Доброто unit тестване се базира на изолация
- Изолацията се постига чрез stub/mock обекти

@ulend

---

#### Stubbing

@ul

- Stub - клас, който отговаря на дадени извиквания на методи с предварително зададени отговори
- В unit тестването ни служат за справяне с проблема с композираните класове

@ulend

---

#### Stubbing

@ul

- Обикновено имплементират по минимален начин даден интерфейс и се подават на класа, който се тества
- Извън unit тестването, могат да бъдат използвани и като заместител на код, който още не е разработен

@ulend

---

__PositiveUserRepositoryStubImpl__

```java
public class PositiveUserRepositoryStubImpl
				implements UserRepository {

	@Override
	public boolean exists(String email) {
		return true;
	}

	@Override
	public void save(User user) {
		// Do nothing
	}
}
```

---

__InMemoryUserRepositoryStubImpl__

```java
public class InMemoryUserRepositoryStubImpl
				implements UserRepository {

	private Map<String, User> users = new HashMap<>();

	@Override
	public boolean exists(String email) {
		return users.containsKey(email);
	}

	@Override
	public void save(User user) {
		users.put(user.getEmail(), user);
	}
}
```

---

#### The stub way

```java
@Test(expected = UserAlreadyExistsException.class)
public void testRegisterThrowsAppropriateException() {
	UserService service =
		new UserService(new PositiveUserRepositoryStubImpl());

	service.register("test@test.com", "weak");
}
```

@[3-4]
@[6]
@[1]

---

#### Test lifecycle with stub

1. Setup data - подготвяме обекта, който ще се тества, както и stub събратята му
2. Exercise - извикваме метода
3. Verify state - използваме assertions, за да проверим състоянието на обекта
4. Teardown - освобождаваме използваните ресурси

---

#### Характеристики на stub-овете

@ul

- Могат да съдържат логика, която не е тривиална (напр. InMemoryUserRepositoryStubImpl) (+)
- Броят на stub-овете расте експоненциално (-)
- Не може да проверим дали даден метод на stub-a е извикан определен брой пъти (-)

@ulend

---

#### Mocking

@ul

- Mock - конфигуриран обект с предварително зададени отговори на дадени извиквания на методи
- Динамични wrapper-и за композираните класове
- По подобие на stub-овете, ни служат за справяне с проблема с композираните класове

@ulend

---

#### The mock way

```java
@Test(expected = UserAlreadyExistsException.class)
public void testRegisterThrowsAppropriateException() {
	UserRepository mock = mock(UserRepository.class);
	when(mock.exists("test@test.com")).thenReturn(true);

	UserService service = new UserService(mock);
	service.register("test@test.com", "weak");
}
```

@[3]
@[6]
@[4]
@[7]
@[1]

---

#### Test lifecycle with mock

1. Setup data - подготвяме обекта, който ще се тества, както и mock събратята му
2. Setup expectations - задаваме желаните отговори
3. Exercise - извикваме метода
4. Verify expectations - уверяваме се, че правилният метод на mock-a се е извикал
5. Verify state - използваме assertions, за да проверим състоянието на обекта
6. Teardown - освобождаваме използваните ресурси

---

#### To mock or not to mock?

```java
public class Cinema {
	private Map<String, Projection> projections;

	public Cinema(Map<String, Projection> projections) {
		this.projections = projections;
	}

	public boolean buyTicket(String projection, int amount) {
		if (!projections.containsKey(projection)) {
			return false;
		}
		// [...]
		return true;
	}
}
```

---

#### Do not mock

```java
@Test
public void testBuyTicket() {
	Map<String, Projection> projections =
			Map.of("foo", new Projection("foo"));
	Cinema cinema = new Cinema(projections);

	boolean actual = cinema.buyTicket("bar", 3);
	assertFalse(actual);
}
```

---

#### To mock or not to mock?

```java
public class Cinema {
	private ProjectionService service;

	public Cinema(ProjectionService service) {
		this.service = service;
	}

	public boolean buyTicket(String projection, int amount) {
		if (!service.contains(projection)) {
			return false;
		}
		// [...]
		return true;
	}
}
```

---

#### Mock

```java
@Test
public void testBuyTicket() {
	ProjectionService mock = mock(ProjectionService.class);
	when(mock.contains("bar")).thenReturn(false);

	Cinema cinema = new Cinema(mock);

	boolean actual = cinema.buyTicket("bar", 3);
	assertFalse(actual);
}
```

---

#### Използвайте mock-ове, когато:

@ul

- Композираният клас се обръща към външен ресурс (REST API, database, file system, etc.)
- Логиката в композирания клас не е тривиална
- Не може да настроите test environment-a по тривиален начин

@ulend

---

#### Не използвайте mock-ове, когато:

@ul

- Композираният клас представлява value object, който може да подадете отвън
- Може тривиално да настроите test environment-a

@ulend

---

#### Mocking libraries

@ul

- [Mockito](https://github.com/mockito/mockito)
- [EasyMock](https://github.com/easymock/easymock)
- [PowerMock](https://github.com/powermock/powermock)\*
- \* Putting it in the hands of junior developers may cause more harm than good.

@ulend

---

#### Mockito

@ul

- Ще разглеждаме mockito (2.23.0) като mocking library
- Възниква като разширение на функционалността на EasyMock
- Една от 10-те най-популярни Java библиотеки като цяло
- Open-source

@ulend

---

#### Setup

@ul

- Mockito е външна библиотека
- Може да я изтеглите от [тук](http://central.maven.org/maven2/org/mockito/mockito-core/2.23.0/)
- Изтеглете mockito-core jar-a и 3-те му compile dependency-та
- Ако ползвате IDE, добавете въпросните jar-ки в class path-a на проекта си
- Алтернативно, ако сте запознати с maven/gradle, ползвайте тях :)

@ulend

---

#### Setup (2)

```
fancy-project
  └─ src/
    └─ (...)
  └─ test/
    └─ (...)
  └─ lib/
    └─ byte-buddy-1.9.0.jar
    └─ byte-buddy-agent-1.9.0.jar
    └─ mockito-core-2.23.0.jar
    └─ objenesis-2.6.jar
```

---

__mock() and verify()__

```java
import static org.mockito.Mockito.*;

List mockedList = mock(List.class);

mockedList.add("one");
mockedList.clear();
mockedList.get(0);

verify(mockedList).add("one");
verify(mockedList, atLeastOnce()).clear();
verify(mockedList, never()).add("two");
```

@[1]
@[3]
@[5-7]
@[9-11]

---

__when()__

```java
LinkedList mockedList = mock(LinkedList.class);

when(mockedList.get(0)).thenReturn("first");
when(mockedList.get(1)).thenThrow(new RuntimeException());

mockedList.get(0);
mockedList.get(1);
```

---

__Argument matchers__

```java
when(mockedList.get(anyInt())).thenReturn("element");

mockedList.get(999);
```

---

__@Mock annotation__

```java
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	@Mock
	private UserRepository repositoryMock;

	@Test(expected = UserAlreadyExistsException.class)
	public void testRegisterThrowsAppropriateException() {
		when(repositoryMock.exists("test@test.com"))
				.thenReturn(true);

		UserService service =
				new UserService(repositoryMock);

		service.register("test@test.com", "weak");
	}
}

```

---

#### Mockito limitations

@ul

- Не може да mock-ва static методи (static is evil)
- Не може да mock-ва конструктори
- Не може да mock-ва final класове и методи
- Не може да mock-ва методите equals() и hashCode()

@ulend

---

#### Best practices

@ul

- Не правете йерархии от тестови класове
- Не mock-вайте типове, които не притежавате
- Mock-вайте само толкова колкото ви трябва за конкретния тест
- Не mock-вайте value обекти
- Keep it short and simple (KISS)
- Redesign when you cannot test it

@ulend

---

#### Полезни четива

@ul

- [JUnit javadoc](https://junit.org/junit4/javadoc/latest/index.html)
- [Unit Testing with JUnit](http://www.vogella.com/tutorials/JUnit/article.html)
- [Mocks aren't stubs](https://martinfowler.com/articles/mocksArentStubs.html) by Martin Fowler
- [Writing good tests](https://github.com/mockito/mockito/wiki/How-to-write-good-tests) by Mockito team

@ulend

---

#### Полезни четива

![Practical unit testing](images/05.4-practical-unit-testing.png?raw=true)

---

## Въпроси
