## Ламбда изрази и Stream API

_20.11.2018_

---

#### Предната лекция говорихме за:

@ul

- качествен код
- IO streams

@ulend

---

#### Днес ще разгледаме:

@ul

- Функционално програмиране
- Lambda изрази
- Stream API

@ulend

---

## Функционално програмиране

---

#### Дефиниция (Wikipedia)

Functional programming is a __programming paradigm__, a style of building the structure and elements of computer programs, that treats computation as the evaluation of **mathematical functions** and **avoids changing-state and mutable data**. It is a **declarative programming** paradigm, which means programming is done with **expressions**.

--- 

#### Основни програмни парадигми

@ul

- Императивно програмиране (ООП, структурно)
- Функционално програмиране
- Логическо програмиране

@ulend

---

#### ФП е парадигма

@ul

- Стил на програмиране, следващ определен дизайн, правила и принципи
- Може да се пише "функционално" на императивен език, но на функционален е много по-лесно и интуитивно

@ulend

---

#### Промяна на данни

- При императивните езици, дадена променлива може да променя стойността си
```java
// No math sense
x = x + 42
```

- При функционалните езици, изчислителният модел е подобен на математиката
  - Щом веднъж е зададена стойност на дадена променлива, тя не може да се изменя

---

#### In immutability we trust

@ul

- Обект, чието състояние не може да се измени никога, се нарича immutable
- Immutable обектите имат няколко предимства като thread safety и caching

@ulend

---

#### Statement vs Expression

@ul

- В езиците за програмиране има условно два типа контролни инструкции:
  - Statement - не връща резултат
  - Expression - връща резултат
- В императивните езици се ползват основно statements, докато във ФП - expressions
- Statements имат смисъл да бъдат изпълнени единствено ако извършват страничен ефект

@ulend

---

#### Странични ефекти

@ul

- Страничен ефект е всяко действие във функция, което променя външно за нея състояние:
  - промяна на данни (външни за функцията)
  - IO операция
  - хвърляне на изключение
  - извикване на друга функция, която предизвиква страничен ефект
- Всеки метод в Java, който е от тип void, извършва страничен ефект.

@ulend

---

#### Странични ефекти - пример

```java
public class BankAccount {

	private int balance = 100;

	public int transfer(int amount) {
		balance += amount;
		return balance;
	}
}
```

```java
BankAccount account = new BankAccount();
account.transfer(10); // 110
account.transfer(10); // 120
```

---

#### Чисти функции

@ul

- Чистите функции не извършват странични ефекти
- Винаги когато извикаме функцията с дадени параметри, е гарантиран един и същ резултат => лесни за анализ на проблеми

@ulend

---

#### Чисти функции (2)

@ul

- Резултатът им може да се запази и преизползва
- Извикването на функцията може да бъде заменено с резултата (referential transparency)
- Чистите функции са лесно композируеми

@ulend

---

#### Чисти функции - пример

```java
public static int add(int a, int b) {
	return a + b;
}
```

```java
add(3, 5) // 8
add(3, 5) // 8
```

---

#### Декларативен стил

@ul

- Java използва основно императивен стил на програмиране
- При декларативния стил имаме акцент върху това **какво** трябва да се направи
- При императивния - **как** трябва да се направи
- Концепцията на декларативния стил е, че задаваме само логиката за изчисление, а не контролните инструкции (if/else, for и т.н.)

@ulend

---

#### Императивен стил - пример

```java
List<Integer> result = new ArrayList<>();
for (int i = 0; i < data.size(); i++) {
	if (data[i] % 3 == 0) {
		result.add(data[i]);
	}
}
```

@ul

- Програмистът е отговорен **как** трябва да се обходи структурата и построи резултатът

@ulend

---

#### Декларативен стил - пример

```java
data.stream().filter(new Predicate<Integer>() {
	public boolean test(Integer value) {
		return (value % 3 == 0);
	}
});
```

@ul

- Библиотеката, която предоставя структурата, е отговорна за итерацията. Ние само задаваме какво да се направи на всяка стъпка.

@ulend

---

#### Декларативен стил - пример (2)

```java
data.stream().filter(i -> i % 3 == 0);
```

@ul

- Отново декларативен стил – този път с много по-удобен синтаксис.

@ulend

---

#### Декларативен стил - характеристики

@ul

- (+) Ясно разграничение между изчислителната логика и control-flow командите
- (+) Възможност за генерални оптимизации от библиотеката/компилатора/езика

- (-) Губи се контрол над изпълнението => възможност за конкретна оптимизация
- (-) В общия случай по-ниска ефективност

@ulend

---

#### Функции от по-висок ред

@ul

- Функционалните езици третират функциите като първостепенни конструкции, т.е. може да правим всичко, което може да правим с обекти в ООП:
  - декларираме/инициализираме функция
  - подаваме функция като параметър
  - връщаме функция като резултат от друга функция
- Функциите от по-висок ред ни предоставят средство, с което да композираме логика, комбинирайки функции

@ulend

---

#### Функции от по-висок ред (2)

@ul

- Функциите от по-висок ред ни предоставят средство, с което да композираме логика, комбинирайки функции

@ulend

---

#### Предимства на ФП

@ul

- По-удобен и смислен синтаксис в дадени ситуации
- Чистите функции са композируеми
- (По-)лесен за анализиране на проблеми в кода
- Декларативният стил позволява част от инфраструктурния код да се премести от програмиста към библиотеката

@ulend

---

#### Предимства на ФП (2)

@ul

- Възможност за редица оптимизации като:
  - Memoization
  - Lazy-evaluation
  - Паралелизация

@ulend

---

## Ламбда изрази

---

#### Функцията като тип

- В Java  (< 8) няма възможност за използването на функция като отделен тип. Функциите са винаги асоциирани или с клас (статични методи) или с инстанция на клас (методи).
- Предаването на функция като параметър на друга функция е много удобно, в случай че само поведението е значимо

---

```java
button.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		System.out.println("Button was pressed!");
	}
});
```

@ul

-  Имплементираме клас само за да подадем поведение (при натискане) като параметър, анонимният вътрешен клас не е асоцииран със състояние (state)

@ulend

---

#### Функцията като тип (2)

@ul

- Най-често използваният похват за подаване на поведение на метод е чрез т.нар. "SAМ (Single Abstract Method) интерфейси", чиято имплементация се представя чрез анонимен вътрешен клас.

@ulend

---

#### Функцията като тип (3)

@ul

- SAM интерфейсите съдържат един-единствен метод (напр. Runnable, Comparable, ActionListener). В Java 8 се наричат Functional Interface.
- Недостатък на този подход е прекалено разтегленият синтаксис, който води до т.нар. "вертикален проблем".

@ulend

---

#### Вертикален проблем

```java
list.stream() 
	.filter(
		new Predicate<String>() {
			public boolean test(String current) {
				return (current.length() > 5);
			}
		})
	.forEach(
		new Consumer<String>() {
			public void accept(String current) {
				System.out.println(current);
			};
		});
```

---

#### Ламбда изрази

@ul

- Добавен в Java 8 с т. нар. "проект Ламбда"
- Счита се за най-голямата промяна, правена някога в езика

@ulend

---

#### Ламбда изрази (2)

@ul

- Позволяват използване на функционален стил на програмиране в Java като:
  - добавя нов логически функционален тип
  - въвежда лесен и удобен начин за представяне на анонимна функция чрез нововъведения литерал на функция, който решава "вертикалния проблем"
  - улеснява писането и поддръжката на код чрез възможности за декларативен стил на програмиране.

@ulend

---

#### Какво е ламбда израз?

- Анонимна функция
- Литерал, който представлява много удобен и лесен начин за предоставяне имплементация на функционален интерфейс
- По-удобен и интуитивен начин да подадем "поведение"

```java
button.addActionListener(
	(ActionEvent e) -> System.out.println("Button pressed"));
```

---

#### Ламбда литерал - синтаксис

Литерал – нотация за създаване и инициализация на обект без използване на оператора new, а чрез дефиниран в езика синтаксис

```java
int i = 5;
String s = "foo";
float f = 1.1f;
int[] a = { 1, 2, 3 };
```

---

#### Ламбда литерал - синтаксис (2)

- Функционалният литерал предоставя възможност за удобна имплементация на lambda израз
```java
(int a, int b) -> (a + b)
```

- Състои се условно от две части, разделени с оператора стрелка (->):
  - дефиниция на функция (списък на входящи параметри)
  - тяло на функция (логиката, извършвана в метода)

---

#### Ламбда – синтаксис дефиниция

@ul

- Съдържа списък с входните параметри
- Ламбда израз може да има нула, един или повече параметри
- Параметрите се поставят в скоби () и се изреждат със запетая
- Празни скоби () означават, че функцията не приема входни параметри

@ulend

---

#### Ламбда – синтаксис дефиниция (2)

@ul

- Типът на параметрите може да бъде експлицитно зададен или изпуснат (type inference)
- Когато има един-единствен параметър и неговият тип може да бъде изпуснат, може да се изпуснат скобите

@ulend

---

#### Примери - синтаксис тяло

```java
// 1 parameter
(String name) -> System.out.println("Hello " + name);

// type inference
(name) -> System.out.println("Hello " + name);

// we omit ()
name -> System.out.println("Hello " + name);

// 3 parameters
(int a, int b, double c) -> a + b + c;

// type inference
(a, b, c) -> a + b + c;
```

---

#### Ламбда - синтаксис тяло

@ul

- Тялото на ламбда израз може да има нула, един или повече изрази
- Когато има единствен израз в тялото, къдравите скоби {} могат да се пропуснат. В противен случай, тялото се поставя в къдрави скоби и отделните изрази се разделят чрез ";" или вложени къдрави скоби

@ulend

---

#### Ламбда - синтаксис тяло (2)

@ul

- Когато има единствен израз в тялото, return клаузата може да се изпусне
- Когато имаме повече от един израз, трябва експлицитно да предоставим return клауза
- [NB] Препоръчва се lambda изразите да са one-liners

@ulend

---

#### Пример - синтаксис тяло

```java
// empty body
() -> {};

// we omit return as we have 1 expression
(a, b) -> a + b;

// 3 expressions in the body
(a, b) -> {a = 5; b = 2; return a + b;}; 

// body on multiple lines
(a, b) -> {
	a = 5;
	b = 2;
	return a + b;
};
```

---

#### Тип на ламбда израз

- Java e статично типизиран език => Lambda изразите си имат тип
- По време на компилация се знае типът на ламбда израза (даден функционален интерфейс), както и типът на параметрите на функцията и на връщания резултат

```java
ActionListener listener = 
	(ActionEvent e) -> System.out.println(e.getSource());
```

---

#### Type inference

- С цел по-удобен дизайн на ламбда израза, може да се изпусне типът на променливите
- Възможно е, когато няма двусмислие и Java компилаторът може да е сигурен какъв е типът на дадена променлива (type inference)

```java
ActionListener listener = 
	(e) -> System.out.println(e.getSource());
```

---

#### Функционални интерфейси

@ul

- В Java 8 SAM интерфейсите се наричат "функционални интерфейси". Всеки SAМ интерфейс е ФИ.
- Опционално може да се анотира с @FunctionalInterface
- Сама по себе си, тази анотация не прави нищо, освен че ще даде компилационна грешка, ако някой добави нов метод към този интерфейс (подобно на @Override)

@ulend

---

#### Функционални интерфейси (2)

@ul

- Java 8 добавя много нови функционални интерфейси
- Представляват "форма" за основните типове ламбда изрази, но не за всички
- Намират се в пакета java.util.function и са предназначени за ползване както от Java SDK, така и от програмистите

@ulend

---

#### Функционални интерфейси (3)

| java.util.function  | Lambda notation |
| ------------------- |---------------- |
| Consumer<T>         | (T) -> void     |
| Predicate<T>        | (T) -> boolean  |
| Supplier<R>         | () -> R         |
| Function<T, R>      | (T) -> R        |
| BiFunction<T, U, R> | (T, U) -> R     |

---

#### Контекст на Lambda израз

Един и същ ламбда литерал може да има различен тип в зависимост от контекста:

```java
Supplier s = () -> "Hello World"; // Supplier
Callable c = () -> "Hello World"; // Callable
```

---

#### Контекст на Lambda израз (2)

@ul

- Възможни контексти за ламбда израз:
  - декларация на променлива
  - присвояване
  - връщане от метод
  - инициализация на масив
  - параметър на метод или конструктор
  - в тялото на ламбда израз
  - тернарен оператор
  - сast операции

@ulend

---

#### Видове ламбда изрази

@ul

- Captured (closure) – използва в тялото си променливи, които са дефинирани извън ламбда израза
- Non-captured – не използва външни променливи

@ulend

---

#### Captured Lambda (2)

@ul

- За да се използва променлива извън контекста на ламбда израза, тя трябва да бъде final или ефективно final (т.е. никога да не ѝ се променя стойността след инициализация)
- this в тяло на lambda израз "сочи" към обграждащия клас

@ulend

---

#### Метод референции

- Много често в ламбда изразите просто делегираме параметрите на някой съществуващ метод:

```java
list.forEach(s -> System.out.println(s));
list.filter(s -> s.equalsIgnoreCase("ivan"));
```

- Метод референциите са удобен синтаксис, с който може да се създаде ламбда израз от вече съществуващ метод:

```java
list.forEach(System.out::println);
list.filter("ivan"::equalsIgnoreCase);
```

---

#### Метод референции (2)

- Дефиницията на метод референцията трябва да е същата като метода на функционалния интерфейс, който методът приема

---

#### Метод референции - видове

- Статичен метод (ClassName::methName)
```java
String::valueOf
```

- Метод на конкретна инстанция от даден клас (instanceRef::methName)
```java
"FPRocks!"::equals
```

- Метод на произволна инстанция на даден клас (ClassName::methName)
```java
String::toLowerCase
```

---

#### Метод референции - видове (2)

- Конструктор (ClassName::new)
```java
String::new, String[]::new
```

- Метод на суперкласа (super::methName)
```java
super::equals
```

---

#### Композиция на функции

java.util.function.Function предоставя помощни методи, с които може да "композираме" функция от вече съществуващи.

```java
// z1(x) = f(g(x))
Function<Integer, Integer> z1 = f.compose(g);

// z2(x) = g(f(x))
Function<Integer, Integer> z2 = f.andThen(g);
```

---

## Stream API

---

#### Еволюция на Collection API

@ul

- Съществуващият Collections API би изглеждал много различно, ако ламбда изразите съществуваха от началото
- Въвеждането на нов API (Collections II, New Collections API и т.н.) би решило проблема, но ще отнеме години да измести от употреба старата версия

@ulend

---

#### Еволюция на Collection API (2)

Решението е:
- добявяне на нови default методи към съществуващите интерфейси (Iterable.forEach, Collections.removeIf и т.н.), позволяващи работа с ламбда изрази
- добавяне на нова "stream" абстракция, позволяващата верижни операции
- лесна взаимозаменяемост на линейна и паралелна обработка (stream() или parallelStream()).

---

#### Stream API

- java.util.stream – въведен в Java 8
- Stream представлява абстракция на последователност от стойности и възможност за "функционални" операции върху тях
Collections API предоставят удобен начин за създаване на Stream от съществуваща колекция. (java.util.Collection.stream())

---

#### Stream API - мотивация

@ul

- декларативен стил на работа с колекции (и потоци от данни като цяло)
- възможност за верижно изпълнение
- лесна миграция от последователно към паралелно изпълнение

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

- няма собствено хранилище, а използва източник на данни (колекция, IO и т.н.)
- функционална същност – не модифицират вътрешните данни
- колекциите винаги имат краен брой елементи, докато Stream може да бъде безкраен
- позволяват lazy операции
- елементите могат да се "консумират" само веднъж подобно на итератор

@ulend

---

#### Създаване на потоци

```java
// from collection ('names' is ArrayList<String>)
Stream<String> namesStream = names.stream(); 

// from array
IntStream primesStream = Arrays.stream(primes); 

// from given objects
Stream<Object> objects = Stream.of("A string", 123, 3.14f);

// from given range
IntStream zeroToNine = IntStream.range(0, 10);

// empty stream
Stream<Stream> emptyStream = Stream.empty();
```

---

#### Създаване на потоци (2)

```java
// Concatenation of two streams
// (left и right са Stream<Object>)
Stream<Object> union = Stream.concat(left, right);

// from file lines
Stream<String> lines =
	new BufferedReader(new FileReader(file)).lines();

// stream containing all files in directory
// it is not recursive
Stream<Path> files = Files.list(Paths.get("."));
```

---

#### Създаване на потоци (2)

От генератор-функция – полезно за създаване на безкраен поток.

```java
// Infinite stream from random numbers
Stream<Integer> randNums =
	Stream.generate(() -> new Random().nextInt(100));

// Infinite stream from even numbers
Stream<Integer> evens = Stream.iterate(0, (x) -> x + 2);
```

---

#### Операции върху Stream

- Stream API предоставя набор от функции от по-висок ред, чрез които декларативно можем да обработим данните
- Препоръчва се ламбда изразите да не модифицират:
  - данните на източника (non-interference) 
  - външни променливи (stateless)

---

#### Операции върхи Stream (2)

- Операциите с потоци биват два вида:
  - Междинни – връщат Stream обект като резултат
  - Терминални – връщат обект, различен от Stream, или не връщат резултат
- Междинните операции се изпълняват lazy.

---

#### Stream API pipeline

```
List<Person> result = people.stream()
          .filter(p -> p.getFirstName().equals("Nikolay"))
          .filter(p -> p.getAge() > 25)
          .sorted(Comparator.comparing(Person::getLastName))
          .collect(Collectors.toList());
```

---

#### `filter`

Междинна операция, приема функция (T -> Boolean) и връща поток само с елементите, за които резултатът е true.

```java
// Returns Stream<Employee>
// with all employees matching the criteria
// Stream<T> -> filter -> Stream<T>
employees.filter(e -> e.getAge() > 25);
```

---

#### `map`

Междинна операция, приема функция (T -> V) и връща поток със същия брой елементи, но oт новия тип (V)

```java
// Returns Stream<String> 
// containing only the employee names
// Stream<T> -> map -> Stream<V>
employees.map(e -> e.getName());
```

Специализирани map операции - mapToInt, mapToLong, mapToDouble

---

#### `flatMap`

Междинна операция, приема функция (T -> Stream[V]) и връща поток със същия брой елементи с линейна структура (flat)

```java
// Returns 1-dimensional stream with all employee bonuses
// Stream<T> -> flatMap -> Stream<V>
employees.flatMap(e -> Stream.of(e.getSalary(), e.getBonus()));
```

---

#### `forEach`

Терминална операция, приема функция (T -> void) и не връща резултат (т.е. няма функционална природа)

```java
// Prints the name and age of each employee
// Stream<T> -> forЕach -> void
employees.forEach(
	e -> System.out.println(e.getName() + " " + e.getAge()));
```

---

#### `reduce`

Терминална операция, приема функция ((T, T) -> T) и връща единичен резултат

```java
// Returns the sum of employee salaries
// Stream<T> -> reduce -> T
employees.mapToDouble(Employee::getSalary)
		 .reduce((res, el) -> res + el);
```

Специализирани reduce операции (само за Int/Long/DoubleStream) - sum(), min(), max(), average()

---

#### `reduce` - генерална форма

Reduce има и по-обща форма, която дава възможност да се върне резултат, различен от типа на потока ((V, T) -> V)

```java
// Stream<T> -> reduce -> V
double result = employees.reduce(
    0.0,                               // initial value
    (res, el) -> res + el.getSalary(), // accumulator
    (left, right) -> left + right);    // combiner
```

---

#### `collect`


- Възможно е reduce операция да върне резултат, който не е единичен обект, а колекция
- collect предоставя възможност за акумулиране резултата във външен контейнер (колекция)

---

#### `collect (2)`

- Класът Collectors предоставя помощни методи за най-често използваните колекции
  - toCollection(),  toList(), toSet(), toMap()

```java
// Returns List<Employees>
employees.filter(e -> e.getAge() > 25)
		 .collect(Collectors.toList());
```

---

#### Колектори

Помощният клас Collectors също така предоставя полезни помощни функции за reduce oперации.
- groupBy – групира елементите

```java
// The result is from type Map<Department, List<Employee>>
employees.collect(
	Collectors.groupingBy(Employee::getDepartment));
```

- joining – конкатенира в цял низ

```java
employees.map(Employee::getName)
		 .collect(Collectors.joining(", "));
```

---

#### Short Circuiting операции

Операции, които прекратяват обхождането на потока преждевременно. Полезни са при безкрайни потоци.

---

#### Short Circuiting операции (2)

- findFirst() – връща първия елемент на потока
- findAny() – връща произволен елемент от потока
- allMatch(T -> boolean) – дали всички елементи отговарят на дадено условие
- anyMatch(T -> boolean) – дали някой елемент отговаря на дадено условие
- limit(int n) – връща n елементи (междинна операция)

---

#### Други операции

- count() – връща броя на елементите (терминална)
- distinct() – връща поток с уникални елементи (на базата на equals)
- sorted() – подрежда елементите
- sorted(Comparator) – сортира по зададен признак

---

#### `Optional API`

- Контейнер обект, който може да съдържа или не дадена стойност.
- Някои от методите на Stream API връщат като резултат Optional<Т>.
- Начин за избягване на null проверки или NullPointerException, предоставя възможности за:

```java
// Check whether value is present
boolean isPresent = optionalEmployee.isPresent();

// Executes the action only if the value is present
optionalEmployee.ifPresent(System.out::println);
```

---

#### `Optional API (2)`

```java
// Returns the container value
// or throws NoSuchElementException
Employee e = optionalEmployee.get();

// Returns default value if the value is not present
optionalEmployee.orElse(Employee.UNKNOWN);

// Throws given exception if the value is not present
optionalEmployee.orElseThrow(NoSuchElementException::new);
```

---

#### Паралелно изпълнение

- Както вече знаем, потоците "итерират" вътрешно източника на данни, което дава възможност за редица оптимизации – като например да изпълним pipeline от задачи паралелно.
- Целта на паралелното изпълнение е да подобрим бързодействието на дадено изчисление, възползвайки се от мултипроцесорната архитектура.
- Можем да превърнем всеки поток в паралелен, използвайки метода parallel()

---

#### Паралелно изпълнение (2)

- Паралелният поток има същия API като серийния
- [NB] Паралелното програмиране крие опасности:
  - не всички задачи са подходящи за паралелизация
  - не всички структури от данни са подходящи за паралелна обработка
  - не винаги постигаме оптимизацията, която целим

---

#### Заключение

- ФП има редица предимства пред императивното програмиране, но и редица недостатъци – избирайте разумно кой подход ще ви даде по-добро (елегантно/бързо) решение на конкретната задача.
- Достатъчно кратки и ясни ламбда изрази могат да подобрят значително четимостта на кода.

---

#### Заключение (2)

- Когато използвате Lambda изрази и Stream API, стремете се към функционален стил, избягвайте да мутирате данни или извършвате странични ефекти. 
- Ако изпълнявате Stream API pipeline паралелно, винаги изпълнявайте релевантни performance тестове.

---

#### Полезни четива

- [State of the Lambda](http://cr.openjdk.java.net/~briangoetz/lambda/lambda-state-4.html)
- [Package java.util.stream](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)
- [Package java.util.function](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html)

---

## Въпроси
