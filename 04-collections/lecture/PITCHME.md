## Колекции

Структури от данни в Java

_30.10.2019_

---

#### Предната лекция говорихме за:

@ul

- Интерфейсите в повече детайли
- Четвъртия фундаментален ООП принцип - Абстракция
- Статични член-променливи и статични методи
- Enums
- Изключения - въведение

@ulend

---

#### Днес продължаваме с:

- Изключения
- Wrapper Types
- Колекции (collections)

---

#### Видове изключения

@ul

- Изключителните събития могат да се дължат на грешка на потребителя, бъг в кода или физически ресурс, който не е достъпен.
- Делят се на три вида:
  - Checked (Compile-time) exceptions
  - Unchecked (Runtime) exceptions
  - Errors

@ulend

---

#### Видове изключения

![Exceptions](images/03.2-exceptions.jpg)

---

#### Checked vs. unchecked exceptions

![Exceptions](images/03.3-exceptions.jpg)

---

#### Checked exceptions

@ul

- Наричат се още compile-time exceptions, защото компилаторът ни задължава да ги обработим
- Едно добре написано приложение би трябвало да ги очаква и да се възстановява от тях

@ulend

---

#### Checked exceptions - примери

@ul

- `FileNotFoundException` при опит да отворим файл по име, какъвто не съществува
- `IOException` при проблем с четене или писане във файл

@ulend

---

#### Unchecked (Runtime) exceptions

@ul

- Възникват по време на изпълнение на приложението, затова се наричат още runtime exceptions
- Приложението обикновено не може да ги очаква или да се възстанови от тях
- Най-често са резултат от бъгове (логически грешки) в кода, неправилно извикване на API-та и т.н.

@ulend

---

####  Unchecked (Runtime) exceptions - примери

@ul

- `ArithmeticException` при опит за деление на нула
- `ArrayIndexOutOfBoundsException` при опит да достъпим елемент на масив по индекс извън размера на масива
- `NullPointerException` при опит за достъпване на член-данна или метод на обект по референция, която е null
- `ClassCastException` при опит да се cast-не обект към клас, на който обектът не е инстанция

@ulend

---

#### Errors

@ul

- Проблеми, които възникват извън приложението, и приложението обикновено не може да ги очаква или да се възстанови от тях.
- Обикновено се генерират от самата виртуална машина.

@ulend

---

#### Errors - примери

@ul

- `OutOfMemoryError` при опит да заделим памет, когато свободната памет не е достатъчна (и не може да освободи с garbage collection)
- `StackOverflowError` когато метод извиква свое копие твърде много пъти (напр. при безкрайна рекурсия)

@ulend

---

#### Деклариране на хвърляни изключения

Ако метод не прехваща/обработва даден checked exception, който може да се хвърли в тялото му, той трябва да го декларира в сигнатурата си, за да "предупреди" тези, които го викат:

<br/>

```java
public void writeList() throws IOException, FileNotFoundException {
    // [...]
}
```

---

#### Chained exceptions

```java
try {
    // [...]
} catch (IOException e) {
    // прехващаме изключение, обработваме го 
    // и хвърляме ново, към което го "закачаме"
    throw new SampleException("Oopss..", e); 
}
```

---

#### Finally – не само за обработка на изключения

```java
try { 
    // тук може да се хвърлят изключения
    // или да има return/continue/break
} finally {
    // някакъв важен cleanup code -
    // ще се изпълни винаги*, независимо какво се случи в try блока
}
```

---

#### Защо да ползваме изключения?

@ul

- Отделяме кода за обработка на грешки от останалия -> става по-четим
- "Препредаване" на грешки по стека на извикванията
- Групиране и диференциране на различните типове грешки

@ulend

---

## Въпроси

---

### Wrapper types

@ul

- Представляват референтни аналози на примитивните типове
- Използват се
    - Където синтаксисът на езика изисква обект, а не примитивен тип
    - Когато ни трябват константи или помощни функции, които са имплементирани в съответния wrapper клас
- Имплицитно се конвертират към съответния си примитивен тип, и обратно

@ulend

---

#### Примитивни типове и wrapper типове

<table>
  <tr>
    <th style="font-size:0.8em">Тип данни</th>
    <th style="font-size:0.8em">Размер</th>
    <th style="font-size:0.8em">Минимум</th>
    <th style="font-size:0.8em">Максимум</th>
    <th style="font-size:0.8em">Wrapper</th>
  </tr>
  <tr style="font-size:0.6em">
    <td>boolean</td>
    <td>-</td>
    <td>-</td>
    <td>-</td>
    <td>Boolean</td>
  </tr>
  <tr style="font-size:0.6em">
    <td>char</td>
    <td>16 бита</td>
    <td>Unicode 0</td>
    <td>Unicode 2<sup>16</sup> - 1</td>
    <td>Character</td>
  </tr>
  <tr style="font-size:0.6em">
    <td>byte</td>
    <td>8 бита</td>
    <td>-128</td>
    <td>+127</td>
    <td>Byte</td>
  </tr>
  <tr style="font-size:0.6em">
    <td>short</td>
    <td>16 бита</td>
    <td>-2<sup>15</sup></td>
    <td>+2<sup>15</sup> - 1</td>
    <td>Short</td>
  </tr>
  <tr style="font-size:0.6em">
    <td>int</td>
    <td>32 бита</td>
    <td>-2<sup>31</sup></td>
    <td>+2<sup>31</sup> - 1</td>
    <td>Integer</td>
  </tr>
  <tr style="font-size:0.6em">
    <td>long</td>
    <td>64 бита</td>
    <td>-2<sup>63</sup></td>
    <td>+2<sup>63</sup> - 1</td>
    <td>Long</td>
  </tr>
  <tr style="font-size:0.6em">
    <td>float</td>
    <td>32 бита</td>
    <td>IEEE754</td>
    <td>IEEE754</td>
    <td>Float</td>
  </tr>
  <tr style="font-size:0.6em">
    <td>double</td>
    <td>64 бита</td>
    <td>IEEE754</td>
    <td>IEEE754</td>
    <td>Double</td>
  </tr>
  <tr style="font-size:0.6em">
    <td>void</td>
    <td>-</td>
    <td>-</td>
    <td>-</td>
    <td>Void</td>
  </tr>
</table>

---

#### Константи и utility методи във wrapper типовете


```java
Integer.MAX_VALUE
Integer.MIN_VALUE
new Integer(25).intValue()
Integer.parseInt(String)
```

@snap[south span-100]
@[1-1](максималната стойност на типа)
@[2-2](минималната стойност на типа)
@[3-3](връща int стойността, "опакована" в дадената инстанция)
@[4-4](конвертира низ с текстово представяне на цяло число към int)
@snapend

---

#### Autoboxing

```java
// primitive type
char c = 'a';

// wrapper class
Character ch1 = new Character('a');

// autoboxing: char implicitly converted to Character
Character ch2 = 'x';

// character instance implicitly converted to char
char c2 = ch1;
```

@snap[south span-100]
@[1-2](инициализиране на променлива от тип char с литерал)
@[4-5](инициализирне на Character обект с литерал)
@[7-8](autoboxing)
@[10-11](wrapper to primitive conversion)
@snapend

---

## Въпроси

---

## Структури от данни

@ul

- Хранилища за данни
- Основни операции
  - добавяне
  - триене
  - търсене
  - обхождане

@ulend

---

#### Масиви - предимства

@ul

- пределно прост "интерфейс"
- най-ефективни от гледна точка на използвана памет*
- бърз достъп на елемент по индекс: O(1)

@ulend

---

#### Масиви - недостатъци

@ul

- размерът им е фиксиран при инициализацията
- добавянето или изтриването на елемент (с изключение на последна позиция) е скъпа операция
- търсенето на елемент по стойност е бавно:
    - О(N) за произволен масив
    - O(logN) за сортиран масив

@ulend

---

#### Колекции

@ul

- Java предоставя т.нар. collections framework, съдържащ интерфейси, имплементации и алгоритми върху най-използваните структури от данни
- За разлика от масивите,
    - размерът им е динамичен
    - съдържат само референтни типове
- Всички* интерфейси и класове се намират в пакета java.util

@ulend

---

Някои ползи от наличието на collections framework:
- Не се налага да преоткриваме топлата вода
- Увеличават се скоростта и качеството на програмите ни
- Стимулира се преизползването на код

---

#### Итератори

- Итераторите предоставят унифициран начин за обхождане на елементите на дадена колекция.
- Колекциите (както и масивите) могат да се обхождат с foreach loop

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

#### `java.util.Iterator`

@ul

- Методът `remove()` премахва от колекцията елемента, последно върнат от `next()`
- Ако колекцията бъде модифицирана докато бъде итерирана, по какъвто и да е начин, различен от извикване на `remove()` на итератора, поведението на итератора е недефинирано
    - В частност, може да се хвърли `ConcurrentModificationException` (дори в еднонишков код)

@ulend

---

#### Основни структури от данни

Основните структури от данни, използвани в имплементациите на колекциите, са

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

---

#### Имплементации на List

- ArrayList - resize-ващ се (динамичен) масив
- LinkedList - двойно свързан списък
- Vector - resize-ващ се (динамичен) масив. Synchronized. Legacy
- Stack - наследява Vector. Legacy -> замества се от Dequeue

---

#### Алгоритмична сложност на основните операции

![ListComplexities](images/04.9-listperformance.png?raw=true)

---

__Queue__

```java
// adds an element to the head of the queue
boolean offer(E e)

// Retrieves, but does not remove, the head of the queue
E peek()

// Retrieves and removes the head of the queue
// Returns null if the queue is empty
E poll()

// Retrieves and removes the head of the queue
// Throws NoSuchElementException if the queue is empty
E remove()
```

---

#### Имплементации на Queue

- PriorityQueue - heap (пирамида)
- LinkedList
- ArrayDeque - resize-ващ се (динамичен) масив

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

<small>*h* - капацитет на хеш таблицата</small>

---

Операции над множества със __Set__

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

@[1](Добавя двойка key-value. Ако има вече такъв ключ в Map-а, променя само value-то, и връща старото value като резултат)

---

#### Имплементации на Map

- HashMap - хеш таблица
- LinkedHashMap - хеш таблица + свързан списък
- EnumMap - битов масив
- TreeMap - червено-черно дърво

---

#### Алгоритмична сложност на основните операции

![MapComplexities](images/04.10-mapperformance.png?raw=true)

<small>*h* - капацитет на хеш таблицата</small>

---

#### Колекции с наредба vs Колекции без наредба

@ul

- TreeMap/TreeSet - червено-черни дървета. Запазват естествена наредба. Елементите трябва да имплементират интерфейса Comparable (или да се подава имплементация на Comparator). Логаритмична сложност за повечето операции
- HashMap/HashSet - хеш таблици. Нямат естествена наредба. Елементите трябва да имплементират методите hashCode() и equals(). Константна сложност за повечето операции

@ulend

---

![Cheat sheet](images/04.4-cheat-sheet.png?raw=true)

---

## Въпроси

@snap[south span-100]
@fab[github] [fmi/java-course](https://github.com/fmi/java-course)
@snapend
