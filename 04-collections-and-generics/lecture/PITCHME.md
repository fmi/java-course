## Колекции и шаблонни типове

Структури от данни в Java

_30.10.2018_

---

#### Предната лекция говорихме за:

@ul

- Wrapper types
- Статични член променливи и статични методи
- Enums
- Изключения

@ulend

---

#### Днес продължаваме с:

- Колекции (collections)
- Шаблонни типове (generics)

---

## Колекции

---

#### Колекции

- Java предоставя т.нар. collections framework, съдържащ интерфейси, имплементации и алгоритми върху най-използваните структури от данни.

- Всички* интерфейси и класове се намират в пакета java.util.

---

Някои ползи от наличието на collections framework:
- Не се налага да преоткриваме топлата вода
- Увеличават се скоростта и качеството на програмите ни
- Стимулира се преизползването на код

---

#### Интерфейси Iterator и Iterable

```java
public interface Iterator<E> {
    boolean hasNext();
    E next();
    void remove();
}

public interface Iterable<T> {
    Iterator<T> iterator();
}
```

---

#### Интерфейси Iterator и Iterable

- Итераторите предоставят унифициран начин за обхождане на елементите на дадена колекция.
- Колекциите (както и масивите) могат да се обхождат с foreach loop
- ConcurrentModificationException в еднонишков код?!

---

#### Основни структури от данни

Основните структури от данни, използвани в имплементациите на колекциите са

- Масиви
- Свързани списъци
- Хеш таблици
- Дървета

---

![Collection diagrams](images/04.2-collections-diagram.png?raw=true)

---

![Map hierarchy](images/04.2.1-java-map-hierarchy.png?raw=true)

---

#### Обхождане на колекции

```java
List<Float> nums = Arrays.asList(4.999f, 0.271f, 7.1f, -1f);
```

- Чрез enhanced for-loop:

```java
for (float current : nums) {
	System.out.printf("%.2f%n", current);
}
```

- Чрез итератор:

```java
Iterator<Float> iterator = nums.iterator();
while (iterator.hasNext()) {
	System.out.printf("%.2f%n", iterator.next());
}
```

---

#### Обхождане на Map

Според това какво ни е нужно, може да вземем от Map-a:

- множеството от ключовете
```java
Set<Integer> keys = map.keySet();
```
- колекция от стойностите
```java
Collection<String> values = map.values();
```

- колекция от двойките ключ-стойност
```java
Set<Entry<Integer, String>> s = map.entrySet();
```

---

__java.util.Collection__

```java
int size()
boolean isEmpty()
boolean contains(Object element)
boolean add(E element)
boolean remove(Object element)
Iterator<E> iterator()

boolean containsAll(Collection<?> c)
boolean addAll(Collection<? extends E> c)
boolean removeAll(Collection<?> c)
boolean retainAll(Collection<?> c)
void clear()

Object[] toArray() 
<T> T[] toArray(T[] a) 
```

@[1-6]
@[8-12]
@[14-15]

---

__List__

```java
boolean add(E e)
boolean contains(Object o)
E get(int index)
int indexOf(Object o)
boolean remove(Object o)
E remove(int index)
int size()
boolean isEmpty()
Object[] toArray()
List<E> subList(int fromIndex, int toIndex)
```

@[1]
@[2]
@[3]
@[4]
@[5]
@[6]
@[7]
@[8]
@[9]
@[10]

---

#### Имплементации на List

- ArrayList - resize-ващ се масив
- LinkedList - двойно свързан списък
- Vector - resize-ващ се масив. Synchronized. Legacy
- Stack - наследява Vector. Legacy

---

#### Алгоритмична сложност на основните операции

![ListComplexities](images/04.9-listperformance.png?raw=true)

---

__Queue__

```java
boolean add(E e)

// Retrieves, but does not remove, the head of the queue
E peek()

// Retrieves and removes the head of the queue
// Returns null if the queue is empty
E poll()

// Retrieves and removes the head of the queue
// Throws NoSuchElementException if the queue is empty
E remove()
```

@[1]
@[3-4]
@[6-8]
@[10-12]

---

#### Имплементации на Queue

- PriorityQueue - heap (пирамида)
- LinkedList
- ArrayDeque - resize-ващ се масив

---

#### Алгоритмична сложност на основните операции

![QueueComplexities](images/04.8-queueperformance.png?raw=true)

---

__Set__

```java
boolean add(E e)
boolean contains(Object o)
boolean remove(Object o)
int size()
boolean isEmpty()
Object[] toArray()
```

@[1]
@[2]
@[3]
@[4]
@[5]
@[6]

---

#### Имплементации на Set

- TreeSet - TreeMap. Червено-черно дърво
- HashSet - хеш таблица
- LinkedHashSet - хеш таблица + свързан списък
- EnumSet - битов масив

---

#### Конструктори на HashSet

```java
HashSet(); // default initial capacity (16) and load factor (0.75).
HashSet(Collection<? extends E> c);
HashSet(int initialCapacity);
HashSet(int initialCapacity, float loadFactor);
```

---

#### Конструктори на TreeSet

```java
TreeSet(); // natural ordering
TreeSet(Collection<? extends E> c);
TreeSet(Comparator<? super E> comparator);
TreeSet(SortedSet<E> s);
```

---

__java.lang.Comparable vs java.util.Comparator__

```
public interface Comparable<T> {
	public int compareTo(T o);
}

public interface Comparator<T> {
	public int compare(T o1, T o2);
}
```

@[1-3]
@[5-7]

---

#### LinkedHashSet

![LinkedHashSet](images/04.6-linkedhashset.png?raw=true)

```java
Set<Character> sc = new LinkedHashSet<>();
Collections.addAll(sc, 'a', 'b', 'j');
```

---

#### Алгоритмична сложност на основните операции

![SetComplexities](images/04.7-setperformance.png?raw=true)

---

Операции над множества с __Set__

```java
Set<String> one = new HashSet<>();
one.add("foo");
one.add("bar");
Set<String> two = new HashSet<>();
two.add("foo");
two.add("baba");

Set<String> union = new HashSet<>(one);
union.addAll(two); // union = [baba, bar, foo]

Set<String> intersection = new HashSet<>(one);
intersection.retainAll(two); // intersection = [foo]

Set<String> difference = new HashSet<>(one);
difference.removeAll(two); // difference = [bar]
```

---

__Map__

```java
V put(K key, V value)
V get(Object key)
V remove(Object key)
boolean containsKey(Object key)
int size()
boolean isEmpty()
Set<K> keySet()
Collection<V> values()
```

@[1]
@[2]
@[3]
@[4]
@[5]
@[6]
@[7]
@[8]

---

#### Имплементации на Map

- HashMap - хеш таблица
- LinkedHashMap - хеш таблица + свързан списък
- EnumMap - битов масив
- TreeMap - червено-черно дърво

---

#### Алгоритмична сложност на основните операции

![MapComplexities](images/04.10-mapperformance.png?raw=true)

---

#### Колекции с наредба vs Колекции без наредба

- TreeMap/TreeSet - червено-черни дървета. Запазват естествена наредба. Елементите трябва да имплементират интерфейса Comparable (или да се подава имплементация на Comparator). Логаритмична сложност за повечето операции.
- HashMap/HashSet - хеш таблици. Нямат естествена наредба. Елементите трябва да имплементират методите hashCode() и equals(). Константна сложност за повечето операции.

---

![Cheat sheet](images/04.4-cheat-sheet.png?raw=true)

---

### Колекции - операции

- Сортиране

```java
List<Integer> nums = new ArrayList<>();
nums.add(4);
nums.add(9);
nums.add(0);
nums.add(7);
nums.add(-1);

// nums = [4, 9, 0, 7, -1]
Collections.sort(nums);
// nums = [-1, 0, 4, 7, 9]
Collections.sort(nums, Collections.reverseOrder());
// nums = [9, 7, 4, 0, -1]
```

---

- Търсене: indexOf(), binarySearch()

```java
List<Integer> nums = Arrays.asList(4, 9, 0, 7, -1);

// nums = [4, 9, 0, 7, -1]
int index = nums.indexOf(7);
// index = 3

Collections.sort(nums);
index = Collections.binarySearch(nums, -1);
// index = 0
```

---

- Разбъркване: shuffle()

```java
List<Integer> nums = Arrays.asList(4, 9, 0, 7, -1);

// nums = [4, 9, 0, 7, -1]
Collections.shuffle(nums);
// nums = [?, ?, ?, ?, ?]
```

---

- Манипулации __copy()__, __fill()__, reverse(), swap()

```java
List<String> from = new ArrayList<>();
from.add("foo");
from.add("bar");
List<String> to = new LinkedList<>();
to.add("a");
to.add("b");

Collections.copy(to, from);
// to = [foo, bar]
```

```java
List<String> list = new ArrayList<>();
list.add("foo");
list.add("bar");
list.add("baz");

Collections.fill(list, "a");
// list = [a, a, a]
```

---

- Манипулация copy(), fill(), __reverse()__, __swap()__)

```java
List<String> list = new ArrayList<>();
list.add("1");
list.add("2");
list.add("3");

Collections.reverse(list);
// list = [3, 2, 1]

Collections.swap(list, 0, 1);
// list = [2, 3, 1]
```

---

- Статистики: min(), max(), frequency()

```java
Set<Integer> nums = Set.of(4, 9, 0, 7, -1);

int min = Collections.min(nums); // -1
int max = Collections.max(nums); // 9
int frequency = Collections.frequency(nums, 7); // 1
```

---

Намиране на всички уникални думи от дадено множество:

```java
import java.util.*;

public class FindDistinctWords {

	public static void main(String[] words) {
		Set<String> distinctWords = new TreeSet<>();
		for (String word : words) {
			distinctWords.add(word);
		}

		System.out.printf("%d distinct words: %s%n",
			distinctWords.size(), distinctWords);
	}
}
```

```bash
$ javac FindDistinctWords.java && \
  java FindDistinctWords "foo" "bar" "foo" "baz" "bar" "foo"
3 distinct words: [bar, baz, foo]
```

---

#### Премахване на елементи на колекция при итериране

```java
private static void filter(Collection<String> collection) {
	for (Iterator<String> it = collection.iterator();
		 it.hasNext();) {
			if (it.next().charAt(1) == 'a') {
				it.remove();
			}
	}
}
```

```java
Set<String> words = new HashSet<>();
words.add("foo");
words.add("bar");
words.add("baz");

filter(words);
// words = [foo]
```
---

#### Collection factory методи

```java
List<String> list = List.of("Java", "9", "rulez");

Set<String> set = Set.of(1, 2, 3, 5, 8);

Map<String, Integer> cities = Map.of(
			"Brussels", 1_139_000,
			"Cardiff", 341_000
);

cities = Map.ofEntries(
			Map.entry("Brussels", 1_139_000),
			Map.entry("Cardiff", 341_000)
);
```

---

#### Collection factory методи

- Колекциите, създавани с factory методите, са immutable
- Заемат по-малко памет от mutable събратята си
- Не могат да съдържат null елементи
- При едно и също съдържание, могат да връщат нови инстанции или референции към съществуващи

---

## Шаблонни типове

---

#### Шаблони (Templates)

- Клас или интерфейс, в декларацията на който има един или повече параметри за тип, се нарича generic клас/интерфейс

```java
List<E>
// We read "list of E"
```

- Дават възможност за параметризиране чрез типове на класове и методи
- Прави се проверка по време на компилация за съвместимост между типовете

---

#### Не-шаблонна кутия

Съдържа какъв да е обект
```java
public class Box {
	private Object value;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
```

---

#### Шаблонна кутия

```java
public class Box<T> {
	private T value;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}

```

---

#### Създаване на инстанции

```java
// The long way - before Java 7
Box<Integer> integerBox = new Box<Integer>();
```

```java
// Diamond operator - from Java 7
Box<Integer> integerBox = new Box<>();
```

---

Конвенция за именуване на параметрите за тип:
- E - Element 
- T - Type
- K - Key
- V - Value
- N - Number
- S, U, V etc. - 2nd, 3rd, 4th types

---

#### Шаблонни методи

- Могат да използват нови параметри за тип, недекларирани от класа

- Новите параметри за тип са видими единствено за метода, който ги декларира

- Могат да са:
  - статични
  - на инстанцията
  - конструктори

---

#### Шаблонни методи – примери

```java
public class Pair<K, V> {
	private K key;
	private V value;

	// Generic constructor
	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	// Generic methods
	public K getKey()   { return key; }
	public void setKey(K key) { this.key = key; }
	public V getValue() { return value; }
	public void setValue(V value) { this.value = value; }
}
```

---

#### Шаблонни методи – примери

```java
public class Util {

	// Generic static method
	public static <K, V> boolean compare(
		Pair<K, V> p1, Pair<K, V> p2) {
		return p1.getKey().equals(p2.getKey()) &&
			p1.getValue().equals(p2.getValue());
	}
}
```

---

#### Шаблонни методи - извикване

```java
Pair<Integer, String> p1 = new Pair<>(1, "apple");
Pair<Integer, String> p2 = new Pair<>(2, "pear");

// Full syntax
boolean areSame = Util.<Integer, String>compare(p1, p2);

// Short syntax
areSame = Util.compare(p1, p2);
```

---

#### Шаблонни типове - наследяване

- Integer is-a Object
- Integer is-a Number
- Double is-a Number
- Обаче `Box<Integer>` is-not-а `Box<Number>`. Техният общ производен клас е Object

---

#### Шаблони и подтипове

- Подтип на шаблонен клас или интерфейс получаваме чрез разширяване или имплементиране
- Ако аргументът за тип е еднакъв, то is-a връзката е в сила

---

- Пример от Collections framework

![Collection subtypes](images/04.5-collections-subtypes.png?raw=true)

---

#### Wildcards

- ? – означава неизвестен тип
- Може да се използва за тип на:
  - параметър
  - поле на инстанцията
  - локална променлива
  - тип на връщане
- Не може да се използва за аргумент за тип при извикване на:
  - шаблонен метод
  - създаването на инстанция на шаблонен клас

---

#### Wildcards, ограничени отгоре

Например, искате да създадете метод, който намира сумата на елементите в списък от Integer, Double, Float, Number.

```java
public static double sumOfList(List<? extends Number> list) {
		double sum = 0.0;
		for (Number current : list) {
			sum += current.doubleValue();
		}
		return sum;
}
```

---

#### Wildcards, ограничени отдолу

- Искаме да създадем метод, който да добавя Integer обекти към списък
- Искаме методът да работи с колекции от Integer, Number, Object

```java
public static void addNumbers(List<? super Integer> list) {
		for (int i = 1; i <= 10; i++) {
			list.add(i);
		}
}
```

---

#### Неограничени wildcards

- Списък от елементи от неизвестен тип - List<?>
- List<?> е еквивалентно на List<? extends Object>
- Използва се, когато:
  - Функционалността, която пишем, може да се имплементира единствено със знанието за методите в java.lang.Object
  - Не се интересуваме от типа на елементите в списъка, а се интересуваме от характеристики на самия списък – например размер на списъка

---

#### Изтриване на информацията за типовите аргументи

- Java компилаторът изтрива информацията за типовите аргументи и тази информация не е налична по време на изпълнение
- Всички типови параметри в шаблонни класове и интерфейси се заместват

---

- Ако са неограничени или ограничени отдолу – заместват се с Object:

```java
public class Box<T> {
	private T value;
	public T getValue() { return value; }
	public void setValue(T value) { this.value = value; }
}
```

- След заместване става:

```java
public class Box {
	private Object value;
	public Object getValue() { return value; }
	public void setValue(Object value) { this.value = value; }
}
```

---

- Ако са ограничени отгоре – заместват се с техния ограничителен тип

```java
class Shape { /* ... */ }
class Circle extends Shape { /* ... */ }
class Rectangle extends Shape { /* ... */ }
```

Нека имаме дефиниран следния метод, който рисува дадена фигура:

```java
public static <T extends Shape> void draw(T shape) { /* ... */ }
```

След заместването на типовия параметър от компилатора се получава:

```java
public static void draw(Shape shape) { /* ... */ }
```

---


#### Сурови типове (Raw types)

- Представляват името на шаблонен клас или интерфейс без аргументите за тип

- Raw type на `Box<T>` е `Box`. Можем да създадем инстанция по следния начин

```java
Box rawBox = new Box();
```

- Отнемат възможността на компилатора да открива грешки

- Оставени са по backward compatibility причини. Избягвайте ги

---

#### Сурови типове (Raw types)

- Може безопасно да се присвои инстанция на параметризиран тип на суровия му тип:

```java
Box<String> stringBox = new Box<>(); 
Box rawBox = stringBox; 
```

- Обратното присвояване се компилира с предупреждение:

```java
// rawBox is a raw type of Box<T>
Box rawBox = new Box();

// warning: unchecked conversion
Box<Integer> intBox = rawBox;
```

---

#### Сурови типове (Raw types)

- Също се генерира предупреждение, ако се опитаме да изпълним шаблонен метод през инстанция на суров тип:

```java
Box<String> stringBox = new Box<>();
Box rawBox = stringBox;

// warning: unchecked invocation to set(T)
rawBox.setValue(8);
```

---

#### Raw types и съвместимост на типовете

- `List` vs. `List<Object>`

```java
List raw;
// warning: List is a raw type.
// References to generic type List<E> should be parameterized

List <Object> objects;
List <String> strings = new ArrayList<>();

raw = strings; // ?
objects = strings; // ?
```

---

#### Предпочитай List вместо масив

```java
// Fails at runtime
Object[] array = new Long[1];
array[0] = "I don't fit in"; // Throws ArrayStoreException

// Won't compile
List<Object> list = new ArrayList<Long>(); // Incompatible types
list.add("I don't fit in");
```

---

#### Ограничения при шаблонните класове и интерфейси

- Не могат да се създават инстанции от типов параметър

```java
public static <E> void append(List<E> list) {
	E elem = new E(); // compile-time error
	list.add(elem); 
}
```

- Не могат да се декларират статични полета на клас от типа на типов параметър

```java
public class MobileDevice<T> {
	private static T os; // compile-time error
}
```

---

#### Ограничения при шаблонните класове и интерфейси

- Не може да се правят конвертирания между типове (casts)
- Не може да се прилага instanceof операторът с шаблонни типове

```java
public static <E> void rtti(List<E> list) {
	if (list instanceof ArrayList<Integer>) { // compile-time error
		// ...
	}
}
```

---

#### Ограничения при шаблонните класове и интерфейси

- Не може да се декларират масиви от параметризиран тип

```java
// compile-time error
List<Integer>[] arrayOfLists = new List<Integer>[2];

// compiler error, but pretend it's allowed
Object[] stringLists = new List<String>[];

// OK
stringLists[0] = new ArrayList<String>();

// An ArrayStoreException should be thrown,
// but the runtime can't detect it
stringLists[1] = new ArrayList<Integer>();
```

---

#### Ограничения при шаблонните класове и интерфейси

- Не може да се дефинират два метода с формални параметри, които след изтриване на типовите параметри имат еднакви сигнатури

```java
public class Example {
	public void print(Set<String> strSet) { }

	// compile-time error
	public void print(Set<Integer> intSet) { }
}
```

---

![Recommended Literature](images/o-reilly-generics-and-collections.png?raw=true)

---

## Въпроси

@snap[south span-100]
@fab[github] [fmi/java-course](https://github.com/fmi/java-course)
@snapend