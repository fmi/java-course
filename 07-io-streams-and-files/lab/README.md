# Markdown Converter :page_facing_up:

Ще създадем инструмент за конвертиране на файлове в [Markdown](https://www.markdownguide.org/getting-started/) формат във файлове в [HTML](https://www.w3schools.com/html/) формат.

Markdown е популярен lightweight markup език, който ни позволява да добавяме форматиране към plaintext текстови документи. Той е широко използван за създаване на уеб страници, документи, бележки, книги, презентации, email съобщения и техническа документация. Популярни уеб приложения като [Reddit](https://www.reddit.com) и [GitHub](https://github.com) поддържат Markdown.

HTML (HyperText Markup Language) е стандартен markup език за създаване на уеб страници.
Markdown документи удобно се визуализират във форматиран вид в уеб браузър, което изисква конвертирането им в HTML формат. Така GitHub визуализира документацията на софтуерните проекти, а в частност ние визуализираме слайдовете към лекциите в нашия курс. 

### Markdown синтаксис

Нашият инструмент ще поддържа ограничено подмножество Markdown елементи, описано тук.

### Заглавия

| Markdown               | HTML                       | Rendered Output          |
| :--------------------- | :------------------------- | :----------------------- |
| # Heading level 1      | `<h1>Heading level 1</h1>` | <h1>Heading level 1</h1> |
| ## Heading level 2     | `<h2>Heading level 1</h2>` | <h2>Heading level 2</h2> |
| ### Heading level 3    | `<h3>Heading level 3</h1>` | <h3>Heading level 3</h3> |
| #### Heading level 4   | `<h4>Heading level 4</h4>` | <h4>Heading level 4</h4> |
| ##### Heading level 5  | `<h5>Heading level 5</h5>` | <h5>Heading level 5</h5> |
| ###### Heading level 6 | `<h6>Heading level 6</h1>` | <h6>Heading level 6</h6> |

### Emphasis: Bold

| Markdown                       | HTML                                      | Rendered Output                         |
| :----------------------------- | :---------------------------------------- | :-------------------------------------- |
| I just love \*\*bold text\*\*. | `I just love <strong>bold text</strong>.` | I just love <strong>bold text</strong>. |
| Love\*\*is\*\*bold             | `Love<strong>is</strong>bold`             | Love<strong>is</strong>bold             |

### Emphasis: Italic

| Markdown                               | HTML                                          | Rendered Output                             |
| :------------------------------------- | :-------------------------------------------- | :------------------------------------------ |
| Italicized text is the \*cat's meow\*. | `Italicized text is the <em>cat's meow</em>.` | Italicized text is the <em>cat's meow</em>. |
| A*cat*meow                             | `A<em>cat</em>meow`                           | A<em>cat</em>meow                           |

### Фрагменти код

| Markdown                         | HTML                                        | Rendered Output                           |
| :------------------------------- | :------------------------------------------ | :---------------------------------------- |
| Always \`.close()\` your streams | `Always <code>.close()</code> your streams` | Always <code>.close()</code> your streams |

Ограденото съдържание винаги е в рамките на един ред. За простота ще приемем, че даден ред може да съдържа най-много едно появяване на всеки от изброените форматиращи елементи.
На един и същи ред обаче може да има комбинация от `bold`, `italic` и `code` фрагменти, но без влагане един в друг: 

| Markdown                           | HTML                                                        | Rendered Output                                           |
| :--------------------------------- | :---------------------------------------------------------- | :-------------------------------------------------------- |
| \`.close()\` \*your\* \*\*eyes\*\* | `<code>.close()</code> <em>your</em> <strong>eyes</strong>` | <code>.close()</code> <em>your</em> <strong>eyes</strong> |

### HTML документи

Нашият инструмент ще генерира съвсем минималистичен HTML документ, в който конвертираното съдържание на Markdown документа ще е оградено с отварящи и затваряши `html` и `body` тагове, и няма да има никакви други тагове, освен тези двата, `h1`, `h2`, `h3`, `h4`, `h5`, `h6`, `strong`, `em` и `code`:

```html
<html>
<body>
Converted Markdown content here
</body>
</html>
```

### Markdown Converter

В пакета `bg.sofia.uni.fmi.mjt.markdown` създайте клас `MarkdownConverter`, който има публичен конструктор по подразбиране и имплементира следния интерфейс:

```java
package bg.sofia.uni.fmi.mjt.markdown;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;

public interface MarkdownConverterAPI {

    /**
     * Converts a text in markdown format readable from {@code source} to a text
     * in corresponding HTML format written to {@code output}.
     *
     * @param source the source character-based input stream
     * @param output the output character-based output stream
     */
    void convertMarkdown(Reader source, Writer output);

    /**
     * Converts a text file in markdown format to a text file in corresponding HTML format.
     *
     * @param from Path of the input file
     * @param to Path of the output file
     */
    void convertMarkdown(Path from, Path to);

    /**
     * Converts all markdown files in a source directory to corresponding HTML files in the target directory.
     * Each markdown file has an extension .md and is converted to an HTML file with the same name
     * and extension .html. The source directory will not contain any subdirectories.
     *
     * @param sourceDir Path of the source directory
     * @param targetDir Path of the target directory
     */
    void convertAllMarkdownFiles(Path sourceDir, Path targetDir);

}
```

### Забележка

Markdown файловете, които ще бъдат конвертирани, ще бъдат well-formed, така че не е нужно да проверявате за това. Например, ако на даден ред има "отваряща" последователност от markdown символи, ще я има и съответната ѝ "затваряща" последователност, двете никога няма да са "слепени" и т.н.

### Тестване

Може като начало да тествате ръчно инструмента за конвертиране, но очакваме да напишете и ваши unit tests с добър code coverage.

### Подсказки

:point_right: Обърнете внимание, че всяко от поддържаните от нашия инструмент конвертирания е приложимо в рамките на единствен ред от Markdown файла, т.е. обработката на файла може да се случва ред по ред и не зависи от съдържанието на предни или следващи редове. Съответно, конвертиранията могат и да се тестват ред по ред.

:point_right: За част от тестовете, вместо да разчитате на съществуващи файлове, може да използвате подходящи потоци: например използващи String за източник и дестинация на данните.

:point_right: За останалите тестове, погрижете се динамично да създавате и триете нужните директории и файлове. Не разчитайте на тестове със статични, предварително създадени директории и файлове: няма как да ги качите на грейдъра.

### Структура на проекта

```bash
src
└── bg.sofia.uni.fmi.mjt.markdown
    ├── MarkdownConverter.java
    └── MarkdownConverterAPI.java

test
└─ bg.sofia.uni.fmi.mjt.markdown
    └─ MarkdownConverterTest
```

В грейдъра качете общ .zip архив на двете директории `src` и `test`.

Успех и не се притеснявайте да задавате въпроси! :star:
