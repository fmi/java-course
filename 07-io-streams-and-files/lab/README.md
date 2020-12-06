# Cities Tagger :globe_with_meridians:

В редица алгоритми от областта на *Natural Language Processing (NLP)*, се налага обработка на големи файлове, съдържащи текст на естествен език, в който се търсят определени patterns, заместват се думи, тагират се думи или фрази, разпознати от някакви правила и т.н.

Ще създадем *tagger*, който ще търси в даден текст имена на градове и ще ги огражда с подходящ XML таг, като ще добавя като property на тага и държавата, в която се намира този град. Резултатът ще се записва в друг текстов файл.

Това е пример за съдържанието на входен и очаквания изходен файл след тагирането:

```
Plovdiv's old town is a major tourist attraction. It is the second largest city
in Bulgaria, after the capital ,Sofia.
```

```
<city country="Bulgaria">Plovdiv</city>'s old town is a major tourist attraction. It is the second largest city
in Bulgaria, after the capital ,<city country="Bulgaria">Sofia</city>.
```

За целта, ще ни е нужен списък с градове по света и съответната държава, в която се намират. Ще използваме такъв, който може да свалите [от тук](./resources/world-cities.csv).

Всеки ред във файла съдържа информация за един град и държавата, в което се намира, като двете са разделени със запетая (стандартен формат, известен като [Comma-Separated Values, или CSV](https://en.wikipedia.org/wiki/Comma-separated_values)). За да опростим задачата ви, сме обработили файла предварително, като сме премахнали от списъка градове, чиито имена се състоят от повече от една дума или съдържат символи, различни от букви, като `-`, `'` и подобни. С други думи, имената на градовете в списъка се състоят от точно една дума, съдържаща единствено малки и главни латински букви. В имената на държавите няма такива ограничения.

## Условие

Създайте клас `Tagger`, като довършите имплементацията по-долу:

```java
package bg.sofia.uni.fmi.mjt.tagger;

import java.io.Reader;
import java.io.Writer;
import java.util.Collection;

public class Tagger {

    /**
     * Creates a new instance of Tagger for a given list of city/country pairs
     *
     * @param citiesReader a java.io.Reader input stream containing list of cities and countries
     *                     in the specified CSV format
     */
    public Tagger(Reader citiesReader) {
        // TBD: add implementation
    }

    /**
     * Processes an input stream of a text file, tags any cities and outputs result
     * to a text output stream.
     *
     * @param text   a java.io.Reader input stream containing text to be processed
     * @param output a java.io.Writer output stream containing the result of tagging
     */
    public void tagCities(Reader text, Writer output) {
        // TBD: add implementation
    }

    /**
     * Returns a collection the top @n most tagged cities' unique names
     * from the last tagCities() invocation. Note that if a particular city has been tagged
     * more than once in the text, just one occurrence of its name should appear in the result.
     * If @n exceeds the total number of cities tagged, return as many as available
     * If tagCities() has not been invoked at all, return an empty collection.
     *
     * @param n the maximum number of top tagged cities to return
     * @return a collection the top @n most tagged cities' unique names
     * from the last tagCities() invocation.
     */
    public Collection<String> getNMostTaggedCities(int n) {
        // TBD: add implementation
        throw new UnsupportedOperationException("Still not implemented");
    }

    /**
     * Returns a collection of all tagged cities' unique names
     * from the last tagCities() invocation. Note that if a particular city has been tagged
     * more than once in the text, just one occurrence of its name should appear in the result.
     * If tagCities() has not been invoked at all, return an empty collection.
     *
     * @return a collection of all tagged cities' unique names
     * from the last tagCities() invocation.
     */
    public Collection<String> getAllTaggedCities() {
        // TBD: add implementation
        throw new UnsupportedOperationException("Still not implemented");
    }

    /**
     * Returns the total number of tagged cities in the input text
     * from the last tagCities() invocation
     * In case a particular city has been taged in several occurences, all must be counted.
     * If tagCities() has not been invoked at all, return 0.
     *
     * @return the total number of tagged cities in the input text
     */
    public long getAllTagsCount() {
        // TBD: add implementation
        throw new UnsupportedOperationException("Still not implemented");
    }

}
```

## Тестове

Напишете и unit тестове за решението ви, като трябва да покриете минимален code coverage 80%.

## :warning: Забележки

 - Приемете, че входният текстов файл е с такъв размер, че не може да бъде изчетен изцяло в паметта и трябва да го обработвате ред по ред
 - Няма ограничения за символите, които могат да се срещат в текста, който ще тагираме
 - В частност, може да има препинателни знаци и други символи, долепени до името на град, отпред или отзад, както например в текста `Plovdiv's old town is a major tourist attraction`, което не следва да пречи на разпознаването и тагирането на `Plovdiv`.
 - Приемете, че в списъка с градове и държави, няма градове с едно и също име в различни държави
 - Имената на градовете в текста трябва да се приемат зa case-insensitive, т.е. `Plovdiv`, `plovdiv`, `PlovDIV` и т.е. трябва да бъдат разпознати, тагирани, и приети за име на един и същи град в методите `getNMostTaggedCities()` и `getAllTaggedCities()`
 - В резултата, връщан от методите `getNMostTaggedCities()` и `getAllTaggedCities()`, имената на градовете трябва да са капитализирани, както се срещат в списъка с градовете - главна първа и малки следващи букви
 - В текста няма сричкопренасяне, т.е. няма думи, които започват на един ред и завършват на друг
 - С изкючение на тагирането на евентуално открити градове в текста, в изходния файл трябва да е изцяло запазено съдържанието и форматирането на входния файл, включително whitespaces и новите редове
 - Списъкът с градове и държави е коректно форматиран както се описано. Не е нужно да правите проверки
 - Входно-изходните потоци - аргументи на конструктора и метода `tagCities()` са коректно създадени, референциите не са `null` и не е нужно да се грижите за затварянето им. Правилото е, че който (метод) отваря даден поток, той е длъжен да се погрижи за затварянето му.

## Структура на проекта

Проектът ви трябва да има следната структура:

```
src
└── bg/sofia/uni/fmi/mjt/tagger
    └── Tagger.java
test
└── bg/sofia/uni/fmi/mjt/tagger
    └── (...)
```

## Добавяне на решение

 - В grader.sapera.org качете `.zip` архив на `src` и `test` директориите на проекта ви.
 - Ако тестовете ви използват статични файлове, трябва също да ги включите в архива, пакетирани там, където ги очакват тестовете.
 - Не е нужно да включвате в архива файла със списъка с градове и държави.
