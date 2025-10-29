class: center, middle

## Структури от данни

29.10.2025

.center[![Collections](images/04.6-collections.png)]

---

## Структури от данни

- Хранилища за данни
- Основни операции
    - добавяне
    - триене
    - търсене
    - обхождане

---

### Масиви - предимства

- пределно прост "интерфейс"
- най-ефективни от гледна точка на използвана памет*
- бърз достъп на елемент по индекс: O(1)

---

### Масиви - недостатъци

- размерът им е фиксиран при инициализацията
- добавянето или изтриването на елемент (с изключение на последна позиция) е скъпа операция
- търсенето на елемент по стойност е бавно:
      - О(N) за произволен масив
      - O(logN) за сортиран масив

---

### Колекции

- Java предоставя т.нар. *collections framework*, съдържащ интерфейси, имплементации и алгоритми върху най-използваните структури от данни
- За разлика от масивите,
      - размерът им е динамичен
      - съдържат само референтни типове
- Всички* интерфейси и класове се намират в пакета `java.util`

---

### Колекции

Основните структури от данни, използвани в имплементациите на колекциите, са

- Масиви
- Свързани списъци
- Хеш таблици
- Дървета

.center[![Basic Data Structures | 70%](images/04.7-basic-data-structures.png)]

---

### Big-O Complexity Chart

[.center[![Big-O Complexity Chart](images/04.7.1-big-o-chart.jpeg)]](https://www.bigocheatsheet.com/)

---

[.center[![Data Structures Complexity Cheatsheet](images/04.7.2-complexity-cheatsheet.png
)]](https://www.bigocheatsheet.com/)

---

### Итератори

- Итераторите предоставят унифициран начин за обхождане на елементите на дадена колекция
- Колекциите (както и масивите) могат да се обхождат с for-each loop

---

### Интерфейси `Iterator` и `Iterable`

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

### `java.util.Iterator`

- Методът `remove()` премахва от колекцията елемента, последно върнат от `next()`
- Ако колекцията бъде модифицирана докато бъде итерирана, по какъвто и да е начин, различен от извикване на `remove()` на итератора, поведението на итератора е недефинирано
    - В частност, може да се хвърли `ConcurrentModificationException` (дори в еднонишков код)

---

### Колекции: ООП йерархията

.center[![Collection OOP hierarchy](images/04.8-collections-map-hierarchy.jpg)]

---

### Обхождане на колекции

```java
List<Float> nums = Arrays.asList(4.999f, 0.271f, 7.1f, -1f);
```

<br>

```java
// чрез for-each loop
for (float current : nums) {
    System.out.printf("%.2f%n", current);
}
```

<br>

```java
// чрез итератор
Iterator<Float> iterator = nums.iterator();
while (iterator.hasNext()) {
    System.out.printf("%.2f%n", iterator.next());
}
```

---

### Обхождане на `Map`

Според това какво ни е нужно, може да вземем от `Map`-a:

- множеството от ключовете
```java
Set<Integer> keys = map.keySet();
```

<br>

- колекция от стойностите
```java
Collection<String> values = map.values();
```

<br>

- множество от двойките ключ-стойност
```java
Set<Entry<Integer, String>> s = map.entrySet();
```

---

### `java.util.Collection`

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

---

### `java.util.List`

```java
boolean add(E e)
boolean contains(Object o) // warning: O(N), meaning slow!
E get(int index)
int indexOf(Object o) // warning: O(N), meaning slow!
boolean remove(Object o) // warning: O(N), meaning slow!
E remove(int index)
int size()
boolean isEmpty()
Object[] toArray()
List<E> subList(int fromIndex, int toIndex)
```

---

### Имплементации на `List`

- `ArrayList` - resize-ващ се (динамичен) масив
- `LinkedList` - двойно свързан списък
- `Vector` - resize-ващ се (динамичен) масив. Synchronized. Legacy → замества се от `ArrayList`
- `Stack` - наследява Vector. Legacy → замества се от `Deque`

---

### Алгоритмична сложност на основните операции

![ListComplexities](images/04.10-listperformance.png)

---

### `java.util.Queue`


| Операция | хвърля изключение   | връща специална стойност |
|:---------|:--------------------|:-------------------------|
| Insert   | `add(e)`            | `offer(e)`               |
| Remove   | `remove()`          | `poll()`                 |
| Examine  | `element()`         | `peek()`                 |

---

### Имплементации на `Queue`

- `PriorityQueue` - heap (пирамида)
- `LinkedList`
- `ArrayDeque` - resize-ващ се (динамичен) масив

---

### Алгоритмична сложност на основните операции

![QueueComplexities](images/04.11-queueperformance.png)

---

### `java.util.Set`

```java
boolean add(E e)
boolean contains(Object o)
boolean remove(Object o)
int size()
boolean isEmpty()
Object[] toArray()
```

---

### Имплементации на `Set`

- `TreeSet` - `TreeMap`. Червено-черно дърво
- `HashSet` - хеш таблица
- `LinkedHashSet` - хеш таблица + двойно свързан списък
- `EnumSet` - битов масив

---

### Конструиране на `HashSet`

```java
HashSet(); // default initial capacity (16) and load factor (0.75).
HashSet(Collection<? extends E> c);
HashSet(int initialCapacity);
HashSet(int initialCapacity, float loadFactor);

// Since Java 19
// Similar factory methods exist also for LinkedHashSet, HashMap and LinkedHashMap
HashSet.newHashSet(int numElements);
```

---

### Конструктори на `TreeSet`

```java
TreeSet(); // natural ordering
TreeSet(Collection<? extends E> c);
TreeSet(Comparator<? super E> comparator);
TreeSet(SortedSet<E> s);
```

---

### `java.lang.Comparable` vs `java.util.Comparator`


```java
// естествена подредба
public interface Comparable<T> {
    int compareTo(T o);
}

public interface Comparator<T> {
    int compare(T o1, T o2);
}
```

---

### Консистентност на естествената подредба с `equals()`

Естествената подредба на клас С се нарича *консистентна с `equals()`*, ако и само ако
`e1.compareTo(e2) == 0` има същата булева стойност като `e1.equals(e2)`, за всеки два обекта e1 и е2 на С.

<br>
Силно препоръчително е (макар и не задължително) естествената подредба да бъде консистентна с `equals()`.

---

### `LinkedHashSet`

.center[![LinkedHashSet](images/04.12.0-linkedhashset.png)]

```java
Set<Character> sc = new LinkedHashSet<>();
Collections.addAll(sc, 'a', 'b', 'j');
```

---

### Алгоритмична сложност на основните операции

![SetComplexities](images/04.12-setperformance.png)

<small>*h* - капацитет на хеш таблицата</small>

---

### Операции над множества със `Set`

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

### `java.util.Map`

```java
V put(K key, V value) // Добавя двойка key-value. Ако има вече такъв ключ в Map-а,
                      // променя само value-то, и връща старото value като резултат
V get(Object key)
V remove(Object key)
boolean containsKey(Object key)
int size()
boolean isEmpty()
Set<K> keySet()
Collection<V> values()
```

---

### Имплементации на `Map`

- `HashMap` - хеш таблица
- `LinkedHashMap` - хеш таблица + двойно свързан списък
- `TreeMap` - червено-черно дърво
- `EnumMap` - битов масив

---

### Алгоритмична сложност на основните операции

![MapComplexities](images/04.13-mapperformance.png)

<small>*h* - капацитет на хеш таблицата</small>

---

### Колекции с наредба vs. Колекции без наредба

- `TreeMap`/`TreeSet` - червено-черни дървета. Запазват естествената наредба. Елементите трябва да имплементират интерфейса `Comparable` (или да се подава имплементация на `Comparator`). Логаритмична сложност за повечето операции
- `HashMap`/`HashSet` - хеш таблици. Нямат наредба. Елементите трябва да имплементират методите `hashCode()` и `equals()`. Константна сложност за повечето операции

<br/>

> 💡 **Общо правило:**
> Винаги предефинирай методите `equals()` и `hashCode()` за класове, чиито обекти ще се добавят в колекции — дори в наредени като `TreeSet` и `TreeMap`. Това гарантира коректно поведение при сравнение, търсене и използване в различни типове колекции.
---

### Java Map/Collection Cheat Sheet

.center[![Cheat sheet](images/04.14-cheat-sheet.png)]

---

### Sequenced Collections (от Java 21)

```java
// Get the last element of a list: before
var last = list.get(list.size() - 1);

// Get the last element of a list: since Java 21
var last = list.getLast();

// Get the first element of a list: before
var first = linkedHashSet.iterator().next();

// Get the first element of a list: since Java 21
var first = linkedHashSet.getFirst();
```

---

### Интерфейсът `SequencedCollection` (от Java 21)

```java
default E getFirst()
default E getLast()

default void addFirst(E e)
default void addLast(E e)

default E removeFirst()
default E removeLast()

SequencedCollection<E> reversed() // returns a reversed-order view of this collection
```

---

### Интерфейсът `SequencedSet` (от Java 21)

Не добавя нови методи към тези на двата си родителя `Set` and `SequencedCollection`, но

- предефинира `reversed()` да връща `SequencedSet`
- ако се извика `addFirst(E)` или `addLast(E)` за елемент, който вече съществува в множеството, той ще бъде преместен съответно първи или последен

---

### Интерфейсът `SequencedMap` (от Java 21)

```java
default Entry<K, V> firstEntry()
default Entry<K, V> lastEntry() 

default Entry<K, V> pollFirstEntry()
default Entry<K, V> pollLastEntry()

default V putFirst(K, V)
default V putLast(K, V)

SequencedMap<K, V> reversed()

default SequencedSet sequencedKeySet()
default SequencedCollection<V> sequencedValues()
default SequencedSet<Entry<K,V>> sequencedEntrySet()
```

---

### Колекции - операции

Сортиране

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

### Колекции - операции

Търсене: `indexOf()`, `binarySearch()`

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

### Колекции - операции

Разбъркване: `shuffle()`

```java
List<Integer> nums = Arrays.asList(4, 9, 0, 7, -1);

// nums = [4, 9, 0, 7, -1]
Collections.shuffle(nums);
// nums = [?, ?, ?, ?, ?]
```

---

### Колекции - операции

Манипулации: `copy()`, `fill()`, `reverse()`, `swap()`

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

<br>

```java
List<String> list = new ArrayList<>();
list.add("foo");
list.add("bar");
list.add("baz");

Collections.fill(list, "a");
// list = [a, a, a]
```

---

### Колекции - операции

Манипулация `copy()`, `fill()`, `reverse()`, `swap()`

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

### Колекции - операции

Статистики: `min()`, `max()`, `frequency()`

```java
Set<Integer> nums = Set.of(4, 9, 0, 7, -1);

int min = Collections.min(nums); // -1
int max = Collections.max(nums); // 9
int frequency = Collections.frequency(nums, 7); // 1
```

---

### Намиране на всички уникални думи от дадено множество

```java
public class FindDistinctWords {

  public static void main(String[] words) {
      Set<String> distinctWords = new TreeSet<>();
      for (String word : words) {
          distinctWords.add(word);
      }

      System.out.printf("%d distinct words: %s%n", distinctWords.size(), distinctWords);
  }

}
```

<br>

```bash
$ javac FindDistinctWords.java && \
  java FindDistinctWords "foo" "bar" "foo" "baz" "bar" "foo"
3 distinct words: [bar, baz, foo]
```

---

### Премахване на елементи на колекция при итериране

```java
private static void filter(Collection<String> collection) {
    for (Iterator<String> it = collection.iterator(); it.hasNext();) {
        if (it.next().charAt(1) == 'a') {
            it.remove();
        }
    }
}
```

<br>

```java
Set<String> words = new HashSet<>();
words.add("foo");
words.add("bar");
words.add("baz");

filter(words);
// words = [foo]
```

---

### Immutable vs. Unmodifiable колекции

- Колекции, които не поддържат модифициращи операции (като `add`, `remove` и `clear`) се наричат *unmodifiable*. Колекциите, които не са *unmodifiable*, са *modifiable*.

- Колекции, които в допълнение гарантират, че не са възможни никакви промени в съдържанието им, се наричат *immutable*. Колекциите, които не са *immutable*, са *mutable*.

- Една *unmodifiable* колекция не е непременно *immutable*. Ако съдържаните в нея елементи са *mutable*, цялата колекция е очевидно *mutable*, въпреки че е *unmodifiable*
    - Ако обаче една *unmodifiable* колекция съдържа само *immutable* елементи, тя самата може да се счита за *ефективно immutable*.

---

### Unmodifiable колекции

```java
// static factory методи за създаване на unmodifiable колекции (от Java 9)

List<String> list = List.of("Java", "rulez");

Set<Integer> set = Set.of(1, 2, 3, 5, 8);

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

### Unmodifiable колекции

```java
Set<String> namesSet = new HashSet<>();
namesSet.add("Paul");
namesSet.add("Anna");

// static factory методи, създаващи unmodifiable колекции като копие на колекции (от Java 10).
// Последващи промени в оригиналната колекция не се отразяват на копията ѝ.

Set<String> moreNamesYet = Set.copyOf(namesSet);
List<String> copiedNames = List.copyOf(namesSet);
Map<String, Integer> map = Map.copyOf(cities);

moreNamesYet.add("Nicky"); // will throw UnsupportedOperationException at runtime
```

---

### Unmodifiable колекции

```java
Set<String> namesSet = new HashSet<>();
namesSet.add("Paul");
namesSet.add("Anna");

// static factory методи за създаване на unmodifiable view на колекция (от Java 9).
// Последващи промени в оригиналната колекция се отразяват на / "виждат" през view-тата ѝ.

Set<String> namesAgain = Collections.unmodifiableSet(namesSet);

namesSet.add("Martin");

System.out.println(namesAgain.contains("Martin")); // true

namesAgain.add("Nicky"); // will throw UnsupportedOperationException at runtime
```

---

### Unmodifiable колекции

- Заемат по-малко памет от modifiable събратята си
- Не могат да съдържат `null` елементи
- При идентично съдържание, factory методите могат да връщат нови инстанции на дадената колекция или референции към вече съществуващи

---

## Въпроси?

.font-xl[.ri-github-fill.icon-inline[[fmi/java-course](https://github.com/fmi/java-course)]]

.font-xl[.ri-youtube-fill.icon-inline[[MJT2026](https://www.youtube.com/playlist?list=PLew34f6r0Pxx6LmzYcc9-8-_-T3ZPZTXg)]]

---

class: center, middle

# Качествен (Clean) Code

29.10.2025

.center[![Clean Code Zen](images/04.1-clean-code-zen.png)]

---

### Как мерим качеството на кода?

.center[![Code Quality](images/04.2-wtfs-per-min.png)]

---

### Принципи на чистия код - именуване, конвенции и добри практики

- Имената на пакети, класове, интерфейси, член-променливи, методи, локални променливи трябва да са говорещи и да спазват установените конвенции
- Предпочитай цели думи. Избягвай съкращения, освен ако не са общоизвестни

---

### Принципи на чистия код - именуване, конвенции и добри практики

- пакети
    - само малки букви, със смислена йерархия
    - `bg.sofia.uni.fmi.mjt`
- класове, абстрактни класове, интерфейси, enums, records
    - съществителни, започващи с главна буква (upper camel case)
        - `Student`, `GameBoard`
        - имената на интерфейси често са отглаголни съществителни
            - `Cloneable`, `Comparable`, `Runnable`, `Serializable`

---

### Принципи на чистия код - именуване, конвенции и добри практики

- методи
    - глаголи, започващи с малка буква (camel case), без подчертавки
        - `reverseString()`, `calculateSalary()`
- променливи
    - започващи с малка буква (camel case), без подчертавки
        - `itemsCount`
- константи
    - all-caps, с подчертавки между думите
        - `MAX_NAME_LENGTH`

---

### Принципи на чистия код - Спазвай ООП принципите

- Енкапсулация
    - Свеждай публичните части до минимум
- Наследяване
    - Не допускай code duplication

---

### Принципи на чистия код - Спазвай ООП принципите

- Полиморфизъм
    - Ползвай полиморфизъм винаги, когато е възможно
    - Използвай интерфейс за декларация, конкретна имплементация за инициализация
    - Предпочитай интерфейс вместо имплементация в сигнатурите на публичните ти методи

---

### Принципи на чистия код - ползвай интерфейс за декларация и конкретен клас за инициализация

```java
// Bad
ArrayList<String> stringList = new ArrayList<>();
public HashSet<Integer> filterPrimes(HashSet<Integer>);
```

<br>

```java
// Good
List<String> stringList = new ArrayList<>();
public Collection<Integer> filterPrimes(Collection<Integer>);
```

---

### Стреми се към хубав ОО дизайн

- Използвай само по изключение пакета по подразбиране
- Един клас трябва да прави едно нещо
    - Ако имаш "and" в името на класа, вероятно не е така
- Ако имаш "Helper", "Manager", "Utility" в името, *може би* има по-добър дизайн
- Един метод трябва да прави едно нещо
- Методът трябва да е кратък: < 20 реда код

---

### Стреми се към хубав ОО дизайн

- Ако имаш "and" в името на метод, може би той прави повечко неща: раздели го на няколко
- Ако имаш много параметри на метод:
      - може би не му е мястото в този клас (а там, откъдето се взимат стойностите за тези параметри)
      - или трябва да направиш *data object* / `record`, който да групира семантично свързаните от тях
- Не злоупотребявай със `static`

---

### Принципи на чистия код - форматирай

- Форматирай си кода
- Нямаш никакво оправдание да не го правиш: има IDE shortcuts
      - Ctrl+Alt+L (IntelliJ)
      - Ctrl+Shift+F (Eclipse)

---

### Не използвай "magic numbers"

Вместо "магически числа" в кода, ползвай подходящо именувани константи или enums

<br>

```java
// Bad
for (int i = 0; i < 16; i++) { /* what is 16?! */ }

if (message.startsWith("[ERROR] ")) { /* ?! */ }
```

<br>

```java
// Good
public static final int MAX_PASSWORD_LENGTH = 16;
//  [...]
for (int i = 0; i < MAX_PASSWORD_LENGTH; i++) { /* do something */ }

private static final String ERROR_MESSAGE_PREFIX = "ERROR: ";
//  [...]
if (message.startsWith(ERROR_MESSAGE_PREFIX)) { /* do something */ }
```

---

### Принципи на чистия код

```java
// Bad
if (x % 2 == 0)
    return x / 2;
```

<br>

```java
// Good: Винаги ограждай в блок телата на if-else и цикли,
// дори да са с по един statement
if (x % 2 == 0) {
    return x / 2;
}
```

---

### Принципи на чистия код

```java
// Bad
public int calculateSalary()
{

}

// Good: отварящата фигурна скоба на блок не стои на отделен ред
// Изключение: initializer блоковете
public int calculateSalary() {

}

```

---

### Принципи на чистия код

```java
// Bad
private static long calculateSum (int n){
    long sumOfOddSquares = 0;
    for(int i=0; i< n; i ++){
        if(i%2!=0){
            sumOfOddSquares += Math.pow (i,2);
        }
    }
    return sumOfOddSquares;

}
```

---

### Принципи на чистия код

```java
// Good: 1. Не "залепвай" if, for, do, while, switch с последващата (
//       2. Не "залепвай" списъка с параметри на метод с последващата {
//       3. Не "залепвай" операторите към операндите
//       4. Не оставяй интервал между името на метод и (
//       5. Оставяй интервал след ,
//       6. Оставяй празни редове ("въздух")
//       7. Не оставяй никъде два или повече последователни празни редове
//       8. Красиво обикновено е симетричното
private static long calculateSum(int n) {
    long sumOfOddSquares = 0;

    for (int i = 0; i < n; i++) {
        if (i % 2 != 0) {
            sumOfOddSquares += Math.pow(i, 2);
        }
    }

    return sumOfOddSquares;
}

```

---

### Принципи на чистия код

```java
// Bad
if (x % 2 == 0) {
    return true;
} else {
    return false;
}
```
<br>

```java
// Good: Изразявай се кратко. Малко код == малко бъгове
return x % 2 == 0;
```

---

### Принципи на чистия код

- Слагай коментари, без да прекаляваш
- Коментарите следва да обясняват "защо", а не "какво". Хубавият код е self-explanatory за "какво"-то

---

### Принципи на чистия код

- Разделяй нормалната логика от exception логиката
- Ползвай изключения вместо error codes и печатане на съобщения в конзолата
- Подавай точен и разбираем message, когато конструираш и хвърляш изключение
- Когато създаваш твой (*custom*) клас за изключение, override-вай non-default конструкторите

  ```java
  Exception(String message)
  Exception(String message, Throwable cause)
  ```

- Не suppress-вай / swallow-вай exceptions
      - Никога не оставяй празен `catch`, или `catch` само с `e.printStackTrace()`

---

### Принципи на чистия код

```java
// Bad
try {
    // do something with the file system
} catch (FileNotFoundException e) {
    // I hope someone reads the logs
    e.printStackTrace();
} catch (IOException e) {
    // the admin should see this
    System.err.println("I/O exception accessing the file system.");
}
```

---

### Принципи на чистия код

```java
// Good
try {
    // do something with the file system
} catch (FileNotFoundException e) {
    // catalog file is missing -
    // this is OK, first access should create an empty catalog
    createEmptyShoppingCatalog();
} catch (IOException e) {
    // catalog file is there but cannot be opened or read
    // calling method knows how to handle this
    throw new ShoppingException("Cannot load shopping catalog", e);
}
```

---

### Разделяй I/O логиката от *бизнес* логиката

```java
// Bad
public static void factorial() { // swiss knife: all-in-one
    Scanner sc = new Scanner(System.in);
    int n = -1; long result = 1;
    do {
        System.out.println("Въведи число:");
        try {
            n = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            continue;
        }
    } while (n < 0);
    for (int i = 2; i <= n; i++) {
        result *= i;
    }
    System.out.println("n! = " + result);
}
public static void main(String... args) {
    factorial();
}
```

---

### Разделяй I/O логиката от *бизнес* логиката

```java
// Good
private static int readInput(Scanner sc) { // handle input
    int n = -1;
    do {
        System.out.println("Въведи число:");
        try {
            n = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            continue;
        }
    } while (n < 0);
    return n;
}

private static void printOutput(long result) { // handle output
    System.out.println("n! = " + result);
}
```

---

### Разделяй I/O логиката от *бизнес* логиката

```java
// Good
public static long factorial(int n) { // business logic
    long result = 1;
    for (int i = 2; i <= n; i++) {
        result *= i;
    }
    return result;
}

public static void main(String... args) {
    int input = readInput(new Scanner(System.in));
    long result = factorial(input);
    printOutput(result);
}
```

---

### Java код конвенции

Запознай се с цялостна Java код конвенция и се придържай към нея.
Две от най-популярните:

<br>

- [Oracle Code Conventions for the Java Programming Language](https://www.oracle.com/technetwork/java/javase/documentation/codeconvtoc-136057.html)

- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)

<br><br>

.center[![Popular Java Code Conventions](images/04.3-oracle-google.png)]

---

### Инструменти за статичен код анализ

- Има инструменти за статичен код анализ, които
      - автоматизират придържането към код конвенции
      - намират и бъгове

---

### Най-популярните open-source инструменти

- [checkstyle](http://checkstyle.sourceforge.net/index.html)<br>
- [PMD](https://pmd.github.io/)<br>
- [FindBugs](http://findbugs.sourceforge.net/index.html)

<br>

.center[![Static Code Analyzers](images/04.4-static-code-analyzers.png)]

---

### planetgeek.ch | Clean Code Cheat Sheet

.center[[![PlanetGeek Cheat Sheet](images/04.5.1-planetgeek-cheatsheet.png)](https://www.planetgeek.ch/wp-content/uploads/2014/11/Clean-Code-V2.4.pdf)]

---

### Библията

.center[![Clean Code](images/04.5-clean-code.png)]

---

### Clean Code

> "Writing clean code is what you must do in order to call yourself a professional.
> There is no reasonable excuse for doing anything less than your best."
>
> &emsp;&emsp;&emsp;&emsp; ― Robert C. Martin, *Clean Code: A Handbook of Agile Software Craftsmanship*

---

## Въпроси?

.font-xl[.ri-github-fill.icon-inline[[fmi/java-course](https://github.com/fmi/java-course)]]

.font-xl[.ri-youtube-fill.icon-inline[[MJT2026](https://www.youtube.com/playlist?list=PLew34f6r0Pxx6LmzYcc9-8-_-T3ZPZTXg)]]
