# Java StyleChecker

Ще създадем инструмент за статично анализиране (*static code analysis tool*) на Java код - **StyleChecker**, който да ни помага да се придържаме към определен стандарт за писане на код (например [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)).

Инструментът ни ще проверява дали определен набор от правила са изпълнени.

### Правила:

1.  На всеки ред е позволено да има само един statement:

    ``` java
    // incorrect
    doSomething(); counter++; doSomethingElse(counter);

    // correct
    doSomething();
    counter++;
    doSomethingElse(counter);
    ```

    **Note 1:** Statement-ите се разделят със символа `;`

    **Note 2:** Код от вида `doSomething;;;;` е коректен, тъй като има само един непразен statement

2.  Не е позволено да се ползват wildcards в import statement-и:

    ```java
    // incorrect
    import java.util.*;

    // correct
    import java.util.Date;
    import java.util.List;
    ```

3.  Отварящите скоби не трябва да бъдат на нов ред:

    ```java
    // incorrect
    public static void main(String[] args)
    {

        if (flag)
        {
            return 42;
        }

    }

    // correct
    public static void main(String[] args) {

    }
    ```

4. Всеки ред трябва да има дължина не повече от n на брой символа

    **Note 1:** Проверката за дължина на ред не се прави при import statement-и.

    **Note 2:** В проверката за дължина на ред не се включват leading и trailing whitespace.

### Имплементация:

Създайте клас `StyleChecker`, който има следните конструктори и метод:

```java
/**
 * Used for static code checks of Java files.
 *
 * Depending on a stream from user-defined configuration or default values, it
 * checks if the following rules are applied:
 * <ul>
 * <li>there is only one statement per line;</li>
 * <li>the line lengths do not exceed 100 (or user-defined number of) characters;</li>
 * <li>the import statements do not use wildcards;</li>
 * <li>each opening block bracket is on the same line as the declaration.</li>
 * </ul>
 */
public class StyleChecker {

    /**
     * Creates a StyleChecker with properties having the following default values:
     * <ul>
     * <li>{@code wildcard.import.check.active=true}</li>
     * <li>{@code statements.per.line.check.active=true}</li>
     * <li>{@code opening.bracket.check.active=true }</li>
     * <li>{@code length.of.line.check.active=true}</li>
     * <li>{@code line.length.limit=100}</li>
     * </ul>
     **/
    public StyleChecker() {
        // implementation
    }

    /**
     * Creates a StyleChecker with custom configuration, based on the content from
     * the given {@ inputStream}. If the stream does not contain any of the
     * properties, the missing ones get their default values.
     * 
     * @param inputStream
     */
    public StyleChecker(InputStream inputStream) {
        // implementation
    }

    /**
     * For each line from the given {@code source} InputStream writes FIXME comment
     * for the violated rules (if any) with an explanation of the style error
     * followed by the line itself in the {@code output}.
     * 
     * @param source
     * @param output
     */
    public void checkStyle(InputStream source, OutputStream output) {
        // implementation
    }
}
```

Коментарите могат да бъдат следните:
- `// FIXME Wildcards are not allowed in import statements`
- `// FIXME Only one statement per line is allowed`
- `// FIXME Length of line should not exceed [X] characters`
- `// FIXME Opening brackets should be placed on the same line as the declaration`

**Note 1:** Последователността им няма значение.

**Note 2:** На един ред може да има повече от едно нарушено правило.

Пример:
- **source java file:**
    ```java
    import java.lang.*;

    public class Test {

        public static void sayHello()
        {
            String hello = "Hey, it's Hannah, Hannah Baker. That's right. Don't adjust your... whatever device you're listening to this on. It's me, live and in stereo.";
            System.out.println(hello);
        }

        public static void main(String[] args) {
            sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();
        }
    }
    ```
- **output java file:**

    ```java
    // FIXME Wildcards are not allowed in import statements
    import java.lang.*;

    public class Test {
        public static void sayHello()
        // FIXME Opening brackets should be placed on the same line as the declaration
        {
            // FIXME Length of line should not exceed 100 characters
            String hello = "Hey, it's Hannah, Hannah Baker. That's right. Don't adjust your... whatever device you're listening to this on. It's me, live and in stereo.";
            System.out.println(hello);
        }
        public static void main(String[] args) {
            // FIXME Only one statement per line is allowed
            // FIXME Length of line should not exceed 100 characters
            sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();
        }
    }
    ```

### Properties

`Property`-тата , съдържащи се в `InputStream`-а, който подаваме на нашия `StyleChecker`, дават информация за четирите правила, които той трябва да проверява. Стойностите `true` и `false` показват дали дадено правило ще бъде проверено или не.

`Property`-тата са следните:

```properties
line.length.limit=130
statements.per.line.check.active=true|false
length.of.line.check.active=true|false
wildcard.import.check.active=true|false
opening.bracket.check.active=true|false
```

Стойностите им по подразбиране са следните:

```properties
line.length.limit=100
statements.per.line.check.active=true
length.of.line.check.active=true
wildcard.import.check.active=true
opening.bracket.check.active=true
```

**Note 1**: Ако `length.of.line.check.active=false`, не взимаме под внимание `line.length.limit` (без значение дали съществува и има някаква стойност).

**Note 2**: Ако някое от пропъртитата липсва, то трябва да бъде използвана неговата стойност по подразбиране.

**Note 3**: Няма нужда да валидирате пропъртитата и техните стойности, които прочитате от `InputStream`-a - приемаме че винаги са валидни.

#### Лесен начин да съхраняваме и четем property-та
За тази цел можем да използваме класа `java.util.Properties`.
``` java
Properties properties = new Properties();

// Добаваме property със съответната му стойност към Properties обекта
properties.setProperty("statements.per.line.check.active", "true");

// Взимаме стойността на съответното property от Properties обекта
boolean isPropertySet = Boolean.parseBoolean(properties.getProperty("statements.per.line.check.active")); // true

// Зареждаме всички property-та от дадения InputStream в Properties обекта
// Ако някое от property-тата вече същестува, то стойността му ще бъде override-ната с тази, която е прочетена от `InputStream`-a
ByteArrayInputStream input = new ByteArrayInputStream("statements.per.line.check.active=false".getBytes());
properties.load(input);
isPropertySet = Boolean.parseBoolean(properties.getProperty("statements.per.line.check.active")); // false
```

### Забележки

Java файловете, които ще бъдат проверявани за стил, няма да съдържат:
- коментари
- символни низове с валиден Java код
- static и non-static initializer blocks

### Тестване

Валидирайте решението си чрез unit тестове. Вместо да разчитате на съществуващи файлове, може да използвате подходящи stream-ове:

```java
@Test
public void test() {
    ByteArrayInputStream input = new ByteArrayInputStream("import java.util.*;".getBytes());
    ByteArrayOutputStream output = new ByteArrayOutputStream();

    checker.checkStyle(input, output);
    String actual = new String(output.toByteArray());

    assertEquals("// FIXME Wildcards are not allowed in import statements\nimport java.util.*;", actual.trim());
}
```

Ако все пак вашите тестове разчитат на файлове, уверете се, че ги качвате заедно с вашето решение и ги реферирате с правилния релативен път.

### Структура на проекта

```
src
╷
└─ bg/sofia/uni/fmi/mjt/stylechecker/
╷   ├─ StyleChecker.java
|   └─ (...)
|
test
╷
└─ bg/sofia/uni/fmi/mjt/stylechecker/
    └─ (...)
```

В [grader.sapera.org](http://grader.sapera.org) качете общ `zip` на директориите `src` и `test`.
