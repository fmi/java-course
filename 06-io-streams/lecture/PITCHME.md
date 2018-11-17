## Качествен (Clean) Code

_13.11.2018_

![Clean Code Zen](images/06.1-clean-code-zen.png?raw=true)

---

#### Как мерим качеството на кода?

![Code Quality](images/06.2-wtfs-per-min.png?raw=true)

---

#### Принципи на чистия код - Спазвай ООП принципите

@ul

- Енкапсулация
  - Свеждай публичните части до минимум

- Наследяване
  - Не допускай code duplication

---

#### Принципи на чистия код - Спазвай ООП принципите

@ul

- Полиморфизъм
  - Ползвай полиморфизъм винаги, когато е възможно
  - Използвай интерфейс за декларация, конкретна имплементация за инициализация

- Абстракция

@ulend

---

#### Принципи на чистия код - Стреми се към хубав ОО дизайн

@ul

- Използвай само по изключение пакета по подразбиране
- Един клас трябва да прави едно нещо
- Ако имаш "and" в името, вероятно не е така
- Ако имаш "Helper", "Manager", "Utility" в името, *може би* има по-добър дизайн
- Един метод трябва да прави едно нещо
- Методът трябва да е кратък: < 20 реда код

@ulend

---

#### Принципи на чистия код - Стреми се към хубав ОО дизайн

@ul

- Ако имаш "and" в името на метод, може би той прави повечко неща: раздели го на няколко
- Ако имаш много параметри на метод:
  - може би не му е мястото в този клас (а там, откъдето се взимат стойностите за тези параметри)
  - или трябва да направиш data object, който да групира семантично свързаните от тях
- Не злоупотребявай със static

@ulend

---

#### Принципи на чистия код

@ul

- Форматирай си кода
- Нямаш никакво оправдание да не го правиш (IDE shortcut?)
- Вместо "магически числа" в кода, ползвай подходящо именувани константи

@ulend

---

#### Принципи на чистия код - Смислени имена

- Имената на пакети, класове, интерфейси, член-променливи, методи, локални променливи трябва да са говорящи и да спазват установените конвенции

---

#### Принципи на чистия код - Смислени имена

@ul

- пакети
  - само малки букви, със смислена йерархия
  - `bg.sofia.uni.fmi.mjt`
- класове, абстрактни класове, интерфейси, enums
  - съществителни, започващи с главна буква (upper camel case)
  - `Student`, `GameBoard`

@ulend

---

#### Принципи на чистия код - Смислени имена

@ul

- методи
  - глаголи, започващи с малка буква (camel case)
  - `reverseString()`, `calculateSalary()`
- константи
  - all-caps, с подчертавки между думите
  - `MAX_NAME_LENGTH`

@ulend

---

#### Принципи на чистия код

```java
// Bad
if (x % 2 == 0)
    return x / 2;
```

```java
// Good
if (x % 2 == 0) {
    return x / 2;
}
```

- Винаги ограждай в блок телата на if-else и цикли, дори да са с по един statement

---

#### Принципи на чистия код

```java
// Bad
if (x % 2 == 0) {
    return true;
} else {
    return false;
}
```

```java
// Good
return x % 2 == 0;
```

- Изразявай се кратко. Малко код == малко бъгове

---

#### Принципи на чистия код

@ul

- Слагай коментари, без да прекаляваш
- Хубавият код е self-explanatory, и все пак, на места си трябва коментар
- Разделяй нормалната логика от exception логиката
- Ползвай изключения вместо error codes и печатане на съобщения в конзолата
- Не suppress-вай / swallow-вай exceptions
- Никога не оставяй празен catch, или catch само с `e.printStackTrace()`

---

#### Принципи на чистия код

@ul

- Разделяй I/O логиката от бизнес логиката

@ulend

---

#### Java код конвенции

@ul

- Запознай се с цялостна Java код конвенция и се придържай към нея.
- Две от най-популярните:
- [Oracle Code Conventions for the Java Programming Language](https://www.oracle.com/technetwork/java/javase/documentation/codeconvtoc-136057.html)
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)

@ulend

---

#### Инструменти за статичен код анализ

![Static Code Analyzers](images/06.3-static-code-analyzers.png?raw=true)

@ul

- Има инструменти за статичен код анализ, които
  - автоматизират придържането към код конвенции
  - намират и бъгове

@ulend

---

#### Инструменти за статичен код анализ

Някои от най-популярните open-source инструменти:

@ul

- [checkstyle](http://checkstyle.sourceforge.net/index.html)
- [PMD](https://pmd.github.io/)
- [FindBugs](http://findbugs.sourceforge.net/index.html)

@ulend

---

#### Библията

![Clean Code](images/06.4-clean-code.png?raw=true)

---

#### PlanetGeek

[Clean Code Cheat Sheet](https://www.planetgeek.ch/wp-content/uploads/2014/11/Clean-Code-V2.4.pdf)

---

## Въпроси

---

## Управление на вход и изход

#### Работа с потоци

![Streams](images/06.5-streams.png?raw=true)

---

#### Ще разгледаме

@ul

- Концепцията за поток
- Входно-изходните потоци в Java

@ulend

---

#### Какво е поточна линия?

![Manufacturing Line](images/06.6-manufacturing-line.gif?raw=true)

---

#### Входно-изходни потоци

@ul

- Концепцията за вход-изход в Java се основава на *потоци* (*streams*)
- *Потокът* е абстракция за безкраен поток от данни  
- Може да се четат данни от поток или да се пишат данни в поток
- В Java потоците може да се основават на байтове или на символи

@ulend

---

#### Източник на данни и дестинация за данни

![Java App I/O](images/06.7-java-app-io.png?raw=true)

---

#### `java.io.*`

![Java I/O Root Abstract Classes](images/06.8-java-io-root-abstract-classes.png?raw=true)

---

#### Входно-изходни потоци

`InputStream`, `OutputStream`, `Reader` и `Writer` имат много наследници, създадени за различни цели:

@ul

- Достъп до файлове
- Достъп до мрежи
- Достъп до буфери в паметта
- Междунишкова комуникация (Pipes)

@ulend

---

#### Входно-изходни потоци

@ul

- Буфериране
- Филтриране
- Парсване
- Четене и писане на текст
- Четене и писане на примитивни данни
- Четене и писане на обекти

@ulend

---

#### Видове потоци според нуждата

![Java I/O Root Abstract Classes](images/06.9-java-io-classes.png?raw=true)

---

![Java I/O Class Hierarchy](images/06.10-java-io-class-hierarchy.png?raw=true)

---

#### Входно-изходни потоци: жизнен цикъл

Потоците се

@ul

- Създават
- Използват
- Затварят

@ulend

---

#### Пример с `InputStream`

```java
InputStream inputStream =
	new FileInputStream("c:\\data\\input-text.txt");

int data = inputStream.read();

while (data != -1) {
	doSomethingWithData(data);
	data = inputStream.read();
}

inputStream.close();
```

---

#### `java.io.InputStream`

```java
int          available​();
void         close​();
void         mark​(int readlimit);
boolean      markSupported​();
abstract int read​();
int          read​(byte[] b);
int          read​(byte[] b, int off, int len);
byte[]       readAllBytes​();
int          readNBytes​(byte[] b, int off, int len);
void         reset​();
long         skip​(long n);
long         transferTo​(OutputStream out);
```

---

#### Пример с `OutputStream`

```java
OutputStream os = new FileOutputStream("test.txt"));
os.write("Hello FMI!".getBytes());
os.flush();
os.close();
```

---

#### `FileOutputStream`: append or overwrite? 

```java
// appends to file
OutputStream output =
	new FileOutputStream("c:\\data\\output-text.txt", true); 

// overwrites file
OutputStream output =
	new FileOutputStream("c:\\data\\output-text.txt", false);
```

---

#### `java.io.OutputStream`

```java
void          close​();
void          flush​();
void          write​(byte[] b);
void          write​(byte[] b, int off, int len);
abstract void write​(int b);
```

---

#### Пример с `DataInputStream`

```java
DataInputStream input =
	new DataInputStream(new FileInputStream("binary.data"));

int    aByte   = input.read();
int    anInt   = input.readInt();
float  aFloat  = input.readFloat();
double aDouble = input.readDouble();
//etc.

input.close();
```

---

#### Пример с `BufferedReader`

```java
Reader reader = new FileReader("data/data.bin");

BufferedReader bufferedReader = new BufferedReader(reader)) {

String line = bufferedReader.readLine();

while (line != null) {
    line = bufferedReader.readLine();
}
```

---

#### Пример с `ObjectOutputStream`

```java
ObjectOutputStream objectOutputStream =
	new ObjectOutputStream(new FileOutputStream("object.data"));

MyClass object = new MyClass();

objectOutputStream.writeObject(object);

objectOutputStream.close();
```

---

#### `java.io.Serializable`

```java
import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 1234L;

	private String name = null;
	private int age = 0;
}
```

---

#### Има ли проблем в този код?

```java
InputStream input =
	new FileInputStream("c:\\data\\input-text.txt");
int data = input.read();

while (data != -1) {
	doSomethingWithData(data);
	data = input.read();
}

input.close();
```

---

#### А тук?

```java
InputStream input = null;
try {
	input = new FileInputStream("c:\\data\\input-text.txt");
	int data = input.read();
	while (data != -1) {
		doSomethingWithData(data);
		data = input.read();
	}
} catch (IOException e) { //do something with e...
} finally {
	if (input != null) {
		input.close();
	}
}
```

---

#### Try-with-resources

- `try` блок, който декларира един или повече ресурси и автоматично затваря всеки ресурс в края на блока
- Ресурс може да е обект от произволен клас, който имплементира интерфейса `java.lang.AutoCloseable` (което включва всички класове, които имплементират `java.io.Closeable`)

---

#### Пример за try-with-resources

```java
static String readFirstLineFromFile(String path)
								throws IOException { 
	try (BufferedReader br =
			new BufferedReader(new FileReader(path))) { 
		return br.readLine(); 
	}
}
```

- Каквото е декларирано в кръглите скоби след try, се `.close()`-ва автоматично при излизане от `try` блока

---

#### `java.io.Reader`

```java
abstract void close();
void          mark(int readAheadLimit);
boolean       markSupported();
int           read();
int           read(char[] cbuf);
abstract int  read(char[] cbuf, int off, int len);
int           read(CharBuffer target);
boolean       ready();
void          reset();
long          skip(long n);
```

---

#### `java.io.Writer`

```java
Writer        append(char c);
Writer        append(CharSequence csq);
Writer        append(CharSequence csq, int start, int end);
abstract void close();
abstract void flush();
void          write(char[] cbuf);
abstract void write(char[] cbuf, int off, int len);
void          write(int c);
void          write(String str);
void          write(String str, int off, int len);
```

---

#### Трите системни потока

@ul

- `System.in` (`InputStream`)
- `System.out` (`PrintStream`)
- `System.err` (`PrintStream`)

@ulend

---

#### `java.util.Scanner`

```java
Scanner scanner = new Scanner(System.in);
int i = scanner.nextInt();
String str = scanner.nextLine();
// ...
```

---

#### `java.util.Formatter`

```java
StringBuilder sb = new StringBuilder();
// Send all output to the Appendable object sb
Formatter formatter = new Formatter(sb, Locale.US);

// Explicit argument indices may be used to re-order output.
formatter.format("%4$2s %3$2s %2$2s %1$2s", "a", "b", "c", "d")
// -> " d  c  b  a"

// Optional locale as the first argument can be used to get
// locale-specific formatting of numbers.  The precision and width
// can be given to round and align the value.
formatter.format(Locale.FRANCE, "e = %+10.4f", Math.E);
// -> "e =    +2,7183"
```

---

#### `java.util.Formatter`

```java
// The '(' numeric flag may be used to format negative numbers
// with parentheses rather than a minus sign. Group separators
// are automatically inserted.
formatter.format("Amount diff since last statement: $ %(,.2f",
                    balanceDelta);
// -> "Amount diff since last statement: $ (6,217.58)"

// Writes a formatted string to System.out.
System.out.format("Local time: %tT", Calendar.getInstance());
// -> "Local time: 13:34:18"
// Writes formatted output to System.err.
System.err.printf("Unable to open file '%1$s': %2$s",
                     fileName, exception.getMessage());
// -> "Unable to open file 'food': No such file or directory"
```

---

## Въпроси
