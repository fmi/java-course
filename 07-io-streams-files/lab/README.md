# Java StyleChecker

Ще създадем инструмент за статично анализиране (*static code analysis tool*) на Java код - **StyleChecker**, който да ни помага да се придържаме към определен стандарт за писане на код (например [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)).

Инструментът ни ще проверява, дали определен набор от правила са изпълнени за Java сорс файл.

### Правила:

1. На всеки ред е позволено да има само един непразен statement:

``` java
// incorrect
doSomething(); counter++; doSomethingElse(counter);

// correct
doSomething();
counter++;
doSomethingElse(counter);
```

**Забележка 1:** Statement-ите се разделят със символа `;`

**Забележка 2:** Код от вида `doSomething;;;;` е коректен, тъй като има само един непразен statement

2. Не е позволено да се ползват wildcards в import statement-и:

```java
// incorrect
import java.util.*;

// correct
import java.util.Date;
import java.util.List;
```

3.  Отварящите фигурни скоби не трябва да бъдат на нов ред:

```java
// incorrect
public static void main(String... args)
{

}

// correct
public static void main(String... args) {

}
```

4. Имената на пакетите не трябва да съдържат главни букви или подчертавки:

```java
// incorrect
package gov.nasa.deepSpace;
package gov.nasa.deep_space;
package gov.NASA.deepSpace;

// correct
package gov.nasa.deepspace;
```

5. Всеки ред трябва да има дължина не повече от 100 символа

**Забележка 1:** Ограничението за дължина на ред не в сила за `import` statement-и.

**Забележка 2:** В определянето на дължината на даден ред не се включват евентуални leading и trailing whitespaces.

### Имплементация:

Създайте клас `StyleChecker`:

```java

import java.io.Reader;
import java.io.Writer;

/**
 * Checks adherence to Java style guidelines.
 */
public class StyleChecker {

    /**
     * For each line from the given {@code source} performs code style checks
     * and writes to the {@code output}
     * 1. a FIXME comment line for each style violation in the input line, if any
     * 2. the input line itself.
     * 
     * @param source
     * @param output
     */
    public void checkStyle(Reader source, Writer output) {
        // implementation
    }

}
```

Коментарите могат да бъдат следните:
- `// FIXME Wildcards are not allowed in import statements`
- `// FIXME Only one statement per line is allowed`
- `// FIXME Length of line should not exceed 100 characters`
- `// FIXME Opening brackets should be placed on the same line as the declaration`
- `// FIXME Package name should not contain upper-case letters or underscores`

**Забележка 1:** Последователността им не е от значение.

**Забележка 2:** В един ред може да е нарушено повече от едно правило.

**Забележка 3:** Подравняването на коментарите не е от значение. Въпреки това, ако искате output-ът да стане една идея по-красив, може да си поиграете.

Пример:

- **source java file:**

```java
package bg.uni_sofia.fmi.mjt;

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
// FIXME Package name should not contain upper-case letters or underscores
package bg.uni_sofia.fmi.mjt;

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

### Забележки

Java файловете, които ще бъдат проверявани за стил, няма да съдържат:
- коментари
- символни низове с валиден Java код
- static и non-static initializer blocks

### Тестване

Валидирайте решението си чрез unit тестове. Вместо да разчитате на съществуващи файлове, може да използвате подходящи потоци:

```java
@Test
public void test() {
    Reader input = new StringReader("import java.util.*;");
    Writer output = new StringWriter();

    checker.checkStyle(input, output);
    String actual = output.toString();

    assertEquals("// FIXME Wildcards are not allowed in import statements"
            + System.lineSeparator() + "import java.util.*;", actual.strip());
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
