#### Входно-изходни потоци и работа с файловата система

_20.11.2019_

![IO Streams](images/07.1-io-streams.png)

---

#### Предната лекция говорихме за:

@ul

- Как се тества софтуер, и кому е нужно
- Какво е unit testing
- JUnit
- Stubbing и mocking
- Mockito

@ulend

---

#### Днес ще разгледаме:

@ul

- Входно-изходни потоци
- Работа с файловата система

@ulend

---

#### Какво е поточна линия?

![Manufacturing Line](https://learnbookme.files.wordpress.com/2016/12/alienation.gif)

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

![Java App I/O](images/07.3-java-app-io.png)

---

#### Абстрактните класове в корена на I/O йерархията

![Java I/O Root Abstract Classes](images/07.4-java-io-root-abstract-classes.png)

<small>Намират се в пакета `java.io`</small>

---

#### Входно-изходни потоци: според източника/дестинацията на данните

Абстрактните класове `InputStream`, `OutputStream`, `Reader` и `Writer` имат много наследници, създадени за различни цели:

@ul

- Достъп до файлове
- Достъп до мрежи
- Достъп до буфери в паметта
- Междунишкова комуникация (Pipes)

@ulend

---

#### Входно-изходни потоци: според допълнителните си качества

@ul

- Буфериране
- Филтриране
- Парсване
- Четене и писане на текст
- Четене и писане на примитивни типове данни
- Четене и писане на обекти

@ulend

---

#### Видове потоци според нуждата

![Java I/O Root Abstract Classes](images/07.5-java-io-classes.png)

---

![Java Byte I/O Class Hierarchy](images/07.6-java-byte-io-classes-hierarchy.png)

---

![Java Char I/O Class Hierarchy](images/07.7-java-char-io-classes-hierarchy.png)

---

#### Wrapping lower-level I/O streams into higher-level ones

По-специализираните типове входно-изходни потоци...
<small>
- често се създават като wrapper около по-базови типове потоци
- и добавят свойства като буфериране, филтриране или работа на по-високо ниво на абстракция.
- За целта, повечето потоци имат oveloaded конструктори, които приемат поток като аргумент

.close()-ването на wrapper потока автоматично .close()-ва и тези, които той wrap-ва
</small>

---

#### Разликата между Byte Streams и Char Streams

- Byte Streams - извършват операции със символи до 8 бита включително (1 байт)

- Char Streams - извършват операции със символи до 16 бита включително (2 байтa)

---

#### Byte Streams - пример

```java
InputStream in = new ByteArrayInputStream("Ivan / Спасов".getBytes());
int current;
while ((current = in.read()) != -1) {
    // read() reads the next byte of data from the input stream.
    // The value byte is returned as an int in the range 0 to 255.
    // If no byte is available because the end of the stream
    // has been reached, the value -1 is returned
    System.out.print((char)current);
}
in.close();

// output:
// Ivan / Ð¡Ð¿Ð°ÑÐ¾Ð²
```

---

@ul

- символ от латинската азбука има размер до 8 бита
- символ от кирилицата - повече от 8 бита
- 'I' = 73<sub>(10)</sub> = 0b1001001<sub>(2)</sub> - това са 7 бита, които се допълват до 8 с водещи нули
- така се обработва всеки символ от символния низ
- 'С' = 1057<sub>(10)</sub> = 0b100010001<sub>(2)</sub> - 9 бита, което надхвърля 8

@ulend

---

#### Character Streams - пример

```java
Reader in = new InputStreamReader(
                    new ByteArrayInputStream("Ivan / Спасов".getBytes()));
int current;
while ((current = in.read()) != -1) {
    System.out.print((char)current);
}
in.close();

// output:
// Ivan / Спасов
```

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

int data;
while ((data = inputStream.read()) != -1) {
    //doSomethingWithData(data);
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

#### `java.io.OutputStream`

```java
void          close​();
void          flush​();
void          write​(byte[] b);
void          write​(byte[] b, int off, int len);
abstract void write​(int b);
```

---

#### Пример с `OutputStream`

```java
OutputStream os = new FileOutputStream("test.txt"));
os.write("Hello FMI!".getBytes());
os.flush();
os.close();
```

<small>
При конструирането на `FileOutputStream`, ако файлът не съществува, ще се направи опит да се създаде.
</small>

---

#### `FileOutputStream`: append vs. overwrite

```java
// appends to a file
OutputStream output =
        new FileOutputStream("c:\\data\\output-text.txt", true); 

// overwrites a file
OutputStream output =
        new FileOutputStream("c:\\data\\output-text.txt", false);
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

#### Пример с `ObjectOutputStream`

```java
ObjectOutputStream objectOutputStream =
        new ObjectOutputStream(new FileOutputStream("persons.dat"));

Person object = new Person();

objectOutputStream.writeObject(object);

objectOutputStream.close();
```

---

#### `java.io.Serializable`

<small>
`Serializable` е маркерен интерфейс, който указва, че обектите на класа могат да се *сериализират* (т.е. конвертират в последователност от битове) и *десериализират* (т.е. (пре)създават от последователност от битове). Класове, обекти на които ще записваме или четем във файл или ще пращаме или получаваме по мрежата, трябва да са `Serializable`.
</small>

```java
import java.io.Serializable;

public class Person implements Serializable {
    // the particular version of the class definition is denoted by
    // the serial version unique identifier: IDEs can generate these
    private static final long serialVersionUID = 1234L;

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

---

#### Има ли проблем в този код?

```java
InputStream inputStream = new FileInputStream("c:\\data\\input.txt");

int data;
while ((data = inputStream.read()) != -1) {
    //doSomethingWithData(data);
}

inputStream.close();
```

---

#### А тук?

```java
InputStream input = null;
try {
    input = new FileInputStream("c:\\data\\input.txt");
    int data;
    while ((data = input.read()) != -1) {
        //doSomethingWithData(data);
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

- `try` блок, който декларира един или повече ресурси и автоматично затваря всеки ресурс в края на блока
- Ресурс може да е обект от произволен клас, който имплементира интерфейса `java.lang.AutoCloseable` (което включва всички класове, които имплементират `java.io.Closeable`)

---

#### Пример за try-with-resources

```java
static String readFirstLineFromFile(String path) throws IOException {
    try (BufferedReader br =
                new BufferedReader(new FileReader(path))) {
        return br.readLine();
    }
}
```

<small>Каквото е декларирано в кръглите скоби след `try`, се `.close()`-ва автоматично при излизане от `try` блока</small>

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

#### Пример с `BufferedReader`

```java
Reader reader = new FileReader("data/data.bin");
BufferedReader bufferedReader = new BufferedReader(reader);

String line;
while ((line = bufferedReader.readLine()) != null) {
    // process line
}
```

---

#### Трите системни потока

@ul

- `System.in`  (`InputStream`)
- `System.out` (`PrintStream`)
- `System.err` (`PrintStream`)

@ulend

---

#### `java.util.Scanner`

```java
Scanner scanner = new Scanner(System.in);
int i = scanner.nextInt();
String str = scanner.nextLine();
// [...]
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
// locale-specific formatting of numbers. The precision and width
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

// Writes a formatted string to System.out
System.out.format("Local time: %tT", Calendar.getInstance());
// -> "Local time: 13:34:18"
// Writes formatted output to System.err
System.err.printf("Unable to open file '%1$s': %2$s",
                     fileName, exception.getMessage());
// -> "Unable to open file 'food': No such file or directory"
```

---

## Въпроси

---

#### Файловата система

@ul

- Йерархична структура
- Състои се от директории и файлове
- Главна директория (корен)
- Текуща (работна) директория
- Пътища
    - Относителни
    - Абсолютни
- Symbolic links

@ulend

---

#### Java API-то за работа с файловата система

<small>NIO.2 (NIO == Non-blocking I/O)</small>

- Пакетът [`java.nio.file`](https://docs.oracle.com/en/java/javase/13/docs/api/java.base/java/nio/file/package-summary.html)
  - [`java.nio.file.Path`](https://docs.oracle.com/en/java/javase/13/docs/api/java.base/java/nio/file/Path.html)
  - [`java.nio.file.Files`](https://docs.oracle.com/en/java/javase/13/docs/api/java.base/java/nio/file/Files.html)

---

#### `java.nio.file.Path`

```java
// Класът Path служи за представяне на път
// Обозначава файл или директория
// Файлът или директорията може физически да съществуват или не
// Инстанции се създават чрез статични методи на java.nio.file.Paths,
// а от Java 11 - и на java.nio.file.Path

Path pathToUserHomeDir = Path.of("C:\\Users\\joe");
                         // C:\Users\joe
Path pathToAFile       = Path.of("C:\\Users\\joe\\orders.txt");
                         // C:\Users\joe\orders.txt
Path relPathToAFile    = Path.of("Users", "joe", "orders.txt");
                         // \Users\joe\orders.txt

Path linuxPathToAFile  = Paths.get("/home", "joe", "file.txt");
                         // /home/joe/file.txt
Path linuxRelativePath = Paths.get("documents", "FileIO.odp");
                         // documents/FileIO.odp
```

---

#### `java.nio.file.Path` - разделители

- Разделителят по подразбиране на имената в пътищата е OS-dependent
    - В UNIX, Linux и MacOS е forward slash: /
    - В Windows е back slash: \\
- Може да се вземе в Java код чрез `File.separator` или `FileSystem.getSeparator()`


---

<table>
  <tr>
    <th style="font-size:0.9em">Метод</th>
    <th style="font-size:0.9em">Връща под UNIX/Linux</th>
    <th style="font-size:0.9em">Връща под Windows</th>
  </tr>
  <tr style="font-size:0.7em">
    <td>toString()</td>
    <td>/home/joe/foo</td>
    <td>C:\home\joe\foo</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>getFileName()</td>
    <td>foo</td>
    <td>foo</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>getName(0)</td>
    <td>home</td>
    <td>home</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>getNameCount()</td>
    <td>3</td>
    <td>3</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>subpath(0, 2)</td>
    <td>home/joe</td>
    <td>home\joe</sup></td>
  </tr>
  <tr style="font-size:0.7em">
    <td>getParent()</td>
    <td>/home/joe</td>
    <td>\home\joe</sup></td>
  </tr>
  <tr style="font-size:0.7em">
    <td>getRoot()</td>
    <td>/</td>
    <td>C:\</td>
  </tr>
</table>

---

#### `java.nio.file.Path`: Преобразувания

```java
Path toAbsolutePath() // преобразува пътя до абсолютен, т.е.
                      // започващ от главната директория

Path toRealPath()     // преобразува пътя до реален, т.е.
                      //     - абсолютен
                      //     - с премахнати символни връзки
                      //     - с премахнати . и ..
Path resolve(Path anotherPath) // добавя относителен път по следния начин:
Path p1 = Paths.get("/home/joe/foo");
Path p2 = p1.resolve("bar"); // резултатът е /home/joe/foo/bar

```

---

#### `java.nio.file.Path`: Сравняване

```java
boolean equals(Path anotherPath)
boolean startsWith(Path anotherPath)
boolean endsWith(Path anotherPath)
```

---

#### Some legacy: `java.io.File`

<small>Дескриптор за файлове, аналог на `java.nio.file.Path`</small>
<small>във версиите преди Java 7. Присъства все още в много API-та.</small>

```java
// creation
File absoluteFile = new File("c:\\Users\\joe\\file.txt");
File absoluteFile = new File("c:\\Users\\joe", "file.txt");
File parentFile = new File("c:\\Users\\joe");
File absoluteFile = new File(parentFile, "file.txt");
URI fileUri = URI.create("file:///home/joe/file.txt");
File absoluteFile = new File(fileUri);

// conversion to and from java.nio.file.Path
Path absolutePath = absoluteFile.toPath();
File absoluteFile = absolutePath.toFile();
```

---

#### `java.nio.file.Files`: Метаданни

```java
long size(Path path)

boolean isRegularFile(Path path)
boolean isDirectory(Path path)
boolean isSymbolicLink(Path path)
boolean isHidden(Path path)


FileTime getLastModifiedTime(Path path)
FileTime setLastModifiedTime(Path path, FileTime time)

UserPrincipal getOwner(Path path)

Object getAttribute(Path path, String attribute)
Path setAttribute(Path path, String attribute, Object value)
```

---

#### `java.nio.file.Files`: Други проверки

```java
boolean exists(Path path)
boolean notExists(Path path)
boolean isReadable(Path path)
boolean isWritable(Path path)
boolean isExecutable(Path path)
boolean isSameFile(Path path1, Path path2)

```

---

#### `java.nio.file.Files`: Изтриване на файлове и директории

```java
void delete(Path path)
// ако path не съществува, хвърля NoSuchFileException
// ако path е непразна директория, хвърля DirectoryNotEmptyException

boolean deleteIfExists(Path path)
// ако path не съществува, връща false
// ако path е непразна директория, хвърля DirectoryNotEmptyException
```

---

#### `java.nio.file.Files`: Копиране на файлове и директории

```java
Path copy(Path source, Path target, CopyOption... options)

// съдържанието на директория не се копира
// options може да бъде една или повече от:
//    StandardCopyOption.COPY_ATTRIBUTES
//        копират се и атрибутите на файла/директорията,
//        например време на последна промянa
// StandardCopyOption.REPLACE_EXISTING
//        ако target е съществуващ файл, то той се презаписва
//        вместо да се хвърля FileAlreadyExistsException
```

---

#### `java.nio.file.Files`: Преместване/преименуване на файлове и директории

```java
Path move(Path source, Path target, CopyOption... options)

// директория може да се премести само на същата файлова система
// options може да бъде една или повече от:
//    StandardCopyOption.ATOMIC_MOVE
//        преместването е атомарно, а ако това не е възможно,
//        се хвърля `AtomicMoveNotSupportedException
//    StandardCopyOption.REPLACE_EXISTING`: ако target е
//    съществуващ файл, то той се презаписва вместо да се хвърля изключение
```

---

#### `java.nio.file.Files`: Създаване на директория

```java
Path createDirectory(Path dir)
// създава директория с път dir,
// ако родителската директория на този път вече съществува

Path createDirectories(Path dir)
// създава директория с път dir, създавайки
// и родителските директории, ако има нужда
```

---

#### `java.nio.file.Files`: Обхождане на директория

```java
DirectoryStream<Path> newDirectoryStream(Path dir)
// създава поток, от който могат да бъдат прочетени файловете
// и поддиректориите на директорията с път dir като инстанции на Path
// DirectoryStream прилича повече на колекция
// (защото е наследник на Iterable), отколкото на входно-изходен поток.

Path dir = ...;
try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
    for (Path fileOrSubDir: stream) {
        // употреба на fileOrSubDir
    }
} catch (IOException | DirectoryIteratorException e) {
    // обработка на изключенията
}
```

---

#### Обхождане на директория с глоб (wildcards)

```java
DirectoryStream<Path> newDirectoryStream(Path dir, String glob)
// създава поток, който съдържа само файловете и поддиректориите,
// които отговарят на глоба glob
// например, итерираме всички файлове, завършващи със .java


Path dir = ... ;

try (DirectoryStream<Path> stream =
        Files.newDirectoryStream(dir, "*.java")) {
    // [...]
}
```

---

#### Глоб (wildcards)

<table>
  <tr>
    <th style="font-size:0.9em">Глоб</th>
    <th style="font-size:0.9em">Семантика</th>
  </tr>
  <tr style="font-size:0.7em">
    <td>\*</td>
    <td>замества произволен брой символи (вкл. николко) в рамките на едно име от пътя (т.е. не може да замести пътния разделител - / или \\)</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>\*\*</td>
    <td>като \*, но пресича границите на имената в пътя</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>?</td>
    <td>замества точно един символ</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>{subGlobl, …, subGlobN}</td>
    <td>замества някой от подглобовете</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>[c1...cN]</td>
    <td>замества някой от символите</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>[c1-c2]</td>
    <td>замества някой от символите в диапазона от c1 до с2</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>\</td>
    <td>използва се за escape на специалните символи, вкл. \
всеки друг символ - замества себе си</td>
  </tr>
</table>

---

#### Примерни глобове

<table>
  <tr>
    <th style="font-size:0.9em">Глоб</th>
    <th style="font-size:0.9em">Семантика</th>
  </tr>
  <tr style="font-size:0.7em">
    <td>/home/\*/file</td>
    <td>замества /home/joe/file (и други), но не и /home/joe/work/file</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>/home/\*\*/file </td>
    <td>замества /home/joe/work/file (и други)</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>{temp\*, tmp\*}</td>
    <td>замества всички имена, започващи с temp или tmp</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>[fmi]</td>
    <td>замества една от буквите f, m, и i</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>[a-z]</td>
    <td>замества една от малките латински букви</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>[fF]</td>
    <td>замества малко или голямо F</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>\\</td>
    <td>замества обратно наклонена черта</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>alabala</td>
    <td>замества alabala</td>
  </tr>
  <tr style="font-size:0.7em">
    <td>[!a].txt</td>
    <td>замества един символ, различен от ‘a’</td>
  </tr>
</table>

---

#### Временни файлове и директории

```java
Path createTempFile(Path dir, String prefix, String suffix)
// създава нов файл в директорията dir и с име,
// започващо с prefix и завършващо със suffix
Path createTempFile(String prefix, String suffix)
// създава нов файл в системната временна директория и с име,
// започващо с prefix и завършващо със suffix
Path createTempDirectory(Path dir, String prefix)
// създава нова поддиректория в директорията dir и с име,
// започващо с prefix
Path createTempDirectory(String prefix)
// създава нова поддиректория в системната временна директория и с име,
// започващо с prefix
```

---

#### Прочитане или записване на цял файл с `java.nio.file.Files`

```java
// These are suitable for small files.
// For larger ones, use buffered streams/writers
byte[] readAllBytes(Path file);
List<String> readAllLines(Path file, Charset cs);

Path write(Path file, byte[] bytes, OpenOption... options);
Path write(Path path, Iterable<? extends CharSequence> lines,
        Charset cs, OpenOption... options);

// Read/write entire file into a String: since Java 11
String readString(Path file); // UTF-8
String readString(Path file, Charset cs);

Path writeString(Path file, CharSequence lines,
        OpenOption[] options); // UTF-8
Path writeString(Path file, CharSequence lines,
        Charset cs, OpenOption[] options);
```

---

#### Сравнителен performance на различните методи за четене на файл

![File Reading Performance](images/07.8-file-reading-performance.png)


---

#### Как се unit-тества код, работещ с файлове?

@ul

- Пакетираме (статични) тестови файлове в (проекта на) тестовете
- Създаваме динамично тестови файлове преди изпълнението на тестовете и ги изтриваме след края на тестовете
- Заменяме прякото тестване с файлове (файлови потоци) с други I/О потоци (например `ByteArrayInputStream` или `StringReader`)
    - това обикновено изисква refactoring, но е най-сигурният и прост начин

@ulend

---

#### Clean Code принципи за код, работещ с потоци и файлове

<small>
  - винаги и гарантирано затваряй ресурсите (потоци, файлове), които създаваш
  - за целта, ползвай try-with-resources винаги, когато е възможно
  - ползвай `var` (с удоволствие и мярка)

</small>

```java
var input = new ByteArrayInputStream("Shorter is better".getBytes());
```

---

#### Обобщение

<small>В Java има голямо разнообразие от API-та за работа с файловата система.</small>
<small>Те се различават по сценариите, за които са подходящи, имат различни предимства и недостатъци и правят различен компромис между гъвкавост, бързодействие и сложност за употреба.</small>

![IO Streams](images/07.9-io-apis-wrapup.gif)

<small><small>API-тата, подредени от по-прости към по-сложни</small></small>

---

## Въпроси

@snap[south span-100]
@fab[github] [fmi/java-course](https://github.com/fmi/java-course)
@snapend
