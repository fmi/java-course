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

- Всички интерфейси и класове се намират в пакета java.util.

---

Някои ползи от наличието на collections framework:
- Не се налага да преоткриваме топлата вода
- Увеличават се скоростта и качеството на програмите ни
- Стимулира се преизползването на код

---

#### Колекции - интерфейси

От птичи поглед:
![Collection interfaces](images/04.1-collections-interfaces.png?raw=true)

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

![Collection diagrams](images/04.2-collections-diagram.png?raw=true)

---

![Map diagram](images/04.3-map-diagram.png?raw=true)

---

![Cheat sheet](images/04.4-cheat-sheet.png?raw=true)

---

__java.lang.Comparable vs java.util.Comparator__

```
public interface Comparable<T> {
	public int compareTo(T o);
}

public interface Comparator<T> {
	int compare(T o1, T o2);
}
```

@[1-3]
@[5-7]

---

#### Колекции - имплементации

- ArrayList - списък върху динамичен масив. Константна амортизирана сложност за повечето операции.
- LinkedList - свързан списък
- TreeMap/TreeSet - червено-черни дървета. Запазват естествена наредба. Елементите трябва да имплементират интерфейса Comparable и метода #equals. Логаритмична сложност за повечето операции.

---

#### Колекции - имплементации (2)

- HashMap/HashSet - хеш таблици. Нямат естествена наредба. Елементите трябва да имплементират методите #hashCode и #equals. Константна сложност за повечето операции.

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

- Търсене (#indexOf, #binarySearch)

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

- Разбъркване (#shuffle)

```java
List<Integer> nums = Arrays.asList(4, 9, 0, 7, -1);

// nums = [4, 9, 0, 7, -1]
Collections.shuffle(nums);
// nums = [?, ?, ?, ?, ?]
```

---

- Манипулация (__#copy__, __#fill__, #reverse, #swap)

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

- Манипулация (#copy, #fill, __#reverse__, __#swap__)

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

- Статистики (#min, #max, #frequency)

```java
Set<Integer> nums = Set.of(4, 9, 0, 7, -1);

int min = Collections.min(nums); // -1
int max = Collections.max(nums); // -9
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

#### Итератори

- Итераторът е обект, който позволява обхождане на елементите на колекция

```java
public interface Iterator<E> {
    boolean hasNext();
    E next();
    void remove(); //optional
} 
```

---

- Също така чрез итератор можем да премахнем елементи на колекцията

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

#### Обходждане на Map

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

details for previous slide

---

## Шаблонни типове

---

#### Шаблони (Templates)

- Клас или интерфейс, в декларацията на който има един или повече параметри за тип се нарича generic клас/интерфейс

```java
List<E>
// We read "list of E"
```

- Прави се проверка по време на компилация за съвместимост между типовете

- Дават възможност за параметризиране чрез типове на класове и методи

---

- "Сурови" типове (raw types)

- Параметърът за тип може да приема кой да е непримитивен тип – клас, интерфейс, масив и т.н.

- Дефиниция
```java
class name<T1, T2, ..., Tn> {
	/* ... */
}
```

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

#### Сурови типове (Raw types)

- Представлява името на шаблонен клас или интерфейс без аргументите за тип

- Raw type на `Box<T>` е `Box`

- Можем да създадем инстанция по следния начин
```java
Box rawBox = new Box();
```

- Отнемат възможността на компилатора да открива грешки по време на компилация

- Избягвайте ги

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

- Също се генерира предупреждение ако се опитаме да изпълним шаблонен метод през инстанция на суров тип:

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
List <String> strings = new List<>();

raw = strings; // ?
objects = strings; // ?
```

- По възможност, не използвайте сурови типове

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

#### Шаблонни методи

- Могат да използват нови параметри за тип, недекларирани от класа

- Новите параметри за тип са видими единствено за метода, който ги декларира

- Могат да са:
  - статични
  - на инстанцията
  - конструктури

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

#### Шаблонни методи - наследяване

- Integer is-a Object
- Integer is-a Number
- Double is-a Number

Обаче
- Box<Integer> is-not-а Box<Number>, техният общ производен клас е Object

---

diagram

---

#### Шаблони и подтипове

- Подтип на шаблонен клас или интерфейс получаваме чрез разширяване или имплементиране
- Ако аргумента за тип е еднакъв, то is-a връзката е в сила

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

#### Неограничени wildcards

- Списък от елементи от неизвестен тип - List<?>
- List<?> е еквивалентно на List<? extends Object>
- Използва се, когато:
  - Функционалността която пишем може да се имплементира единствено със знанието за методите в java.lang.Object
  - Не се интересуваме от типа на елементите в списъка, а се интересуваме от характеристики на самия списък – например размер на списъка

---

#### Wildcards, ограничени отдолу

- Искаме да създадем метод, които да добавя Integer обекти към списък
- Искаме методът да работи с колекции от Integer, Number, Object

```java
public static void addNumbers(List<? super Integer> list) {
		for (int i = 1; i <= 10; i++) {
			list.add(i);
		}
}
```

---

#### Wildcards и подтипове



---

#### Изтриване на информацията за типовите аргументите

- Java компилаторът изтрива информацията за типовите аргументи и тази информация не е налична по време на изпълнение
- Всички типови параметри в шаблонни класове и интерфейси се заместват

---

- Ако са неограничени – с Object:

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

- Ако са ограничени – с техния ограничителен тип

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

## Въпроси