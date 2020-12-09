# Spell Check Tool :speech_balloon: 
`Краен срок: 23.12.2020, 23:45`

Един от най-полезните инструменти, които използваме при общуването си online е т.нар. spell checker (инструмент за проверка на правописа).
Целта на второто домашно от курса е да упражните знанията, взети до момента, чрез създаване на един такъв tool.

:bulb: *Как работят spell checker-ите?*\
В основата си, те разчитат на алгоритъм, който намира близост между думи - по-конкретно близостта на една сгрешена дума, до всички думи от речника ни и смята за *най-правилна* тази, която е най-близка.

:bulb: *Какво означава две думи да са близки?*\
Най-просто казано - да са съставени от почти едни и същи символи в една и съща последователност.

:bulb: *Какво означава една дума да е "сгрешена"?*\
Една дума е сгрешена, когато я няма в речника ни.*


## Условие

```java
package bg.sofia.uni.fmi.mjt.spellchecker;

public interface SpellChecker {

    /**
     * Analyzes the text contained in {@code textReader} for spelling mistakes and outputs the result in {@code output}
     * The format of the analisis depends on the concrete implemetation.
     * 
     * @param textReader a java.io.Reader input stream containing some text
     * @param output java.io.Writer output stream containing the analysis result
     * @param suggestionsCount The number of suggestions to be generated for each misspelled word in the text
     */
    void analyze(Reader textReader, Writer output, int suggestionsCount);
    
    /**
     * Returns the metadata of the text contained in {@code textReader}
     * The metadata gives information about the number of characters, words, and spelling mistakes in the text
     * @param textReader a java.io.Reader input stream containing some text
     * @return Metadata for the given text
     */
    Metadata metadata(Reader textReader);
    
    /**
     * Returns {@code n} closest words to {@code word}, sorted in descending order. 
     * The algorithm used for computing the similarity between words depends on the concrete implementation.
     * @param word
     * @param n
     * @return A List of {@code n} closest words to {@code word}, sorted in descending order
     */
    List<String> findClosestWords(String word, int n);
    
}
```

Създайте клас `NaiveSpellChecker`, който имплементира `SpellChecker` и има следния публичен конструктор:
```java
package bg.sofia.uni.fmi.mjt.spellchecker;

/**
 * Creates a new instance of NaiveSpellCheckTool, based on a dictionary of words and stop words
 *
 * @param dictionary a java.io.Reader input stream containing list of words which will serve as a dictionary for the tool
 * @param stopwords a java.io.Reader input stream containing list of stopwords
 */
public NaiveSpellChecker(Reader dictionaryReader, Reader stopwordsReader)
```

## Метаданни
Метаданните за файла представляват следния `record`:
``` java
package bg.sofia.uni.fmi.mjt.spellchecker;

public record Metadata(int characters, int words, int mistakes) {

}
```
## Стоп-думи и речник

### Стоп-думи (stopwords)
:link: [stopwords.txt](https://github.com/fmi/java-course/tree/master/homeworks/02-spell-check-tool/resources/stopwords.txt)\
Стоп-думите са множество от често срещани в свободен текст думи, които носят твърде малко семантика: определителни членове, местоимения, предлози, съюзи и т.н. Много алгоритми, свързани с обработка на естествен език (NLP, natural language processing), ги игнорират - т.е. премахват ги от съответните входни текстове, защото внасят "шум", т.е. намаляват качеството на резултата.
 
Няма еднозначна дефиниция (или речник) коя дума е stopword в даден език. В нашия алгоритъм ще ползваме списъка от 174 stopwords в английския език, записани по една на ред в текстовия файл stopwords.txt, който сме заимствали от сайта ranks.nl.

### Речник
:link: [dictionary.txt](https://github.com/fmi/java-course/tree/master/homeworks/02-spell-check-tool/resources/dictionary.txt)\
Речникът представлява файл с думи, разделени с празен ред и ни служи да определеме еднозначно дали една дума е 'правилна' - ако не е стоп-дума и я няма в речника, приемаме че е написана грешно.

### Предварителна обработка на данните
Стоп-думите и думите от речника се нуждаят от предварителна обработка:

✔️ те трябва да са case insensitive\
✔️ трябва да бъдат премахнати leading + trailing whitespaces\
✔️ трябва да се премахнат думите от речника, които съдържат само non-alphanumeric символи\

✒️ *Бележка 1*: В речника няма да има фрази/словосъчетания (състоящи се от няколко думи разледени с whitespace)\
✒️ *Бележка 2*: При проверяването дали дадена дума е правилна, се очаква да бъде приложена същата обработка

## Метаданни
### Броене на думи
При броенето на думи не се включват:
- стоп-думи
- думи, които се състоят само от non-alphanumberic символи

### Броене на символи
При броенето на символи не се включват:
- whitespace символи

## Формат на output файла
### Формат
[the text from the input, without any corrections]\
= = = Metadata = = =\
[charsNumber] characters, [wordsNumber] words, [issuesNumber] writing issue(s) found\
= = = Findings = = =\
Line #[lineNumber], {[misspelled word]} - Possible suggestions are {[suggestion1], [suggestion2], ...}


### Пример
#### Вход
Helllo, my name is Roko the dog.

#### Резултат
Helllo, my name is Roko the dog.\
= = = Metadata = = =\
12 characters, 4 words, 1 writing issue(s) found\
= = = Findings = = =\
Line #1, {helllo} - Possible suggestions are {hello, chello, hellos}\
<br>

✒️ *Бележка 1*: Квадратните скоби са използвани само като placeholder. Те не трябва да са част от output-a.\
✒️ *Бележка 2*: Къдравите скоби трябва да са част от output-a.

## Алгоритъм за намиране на сходство между две думи
За изчисляване на сходство между две думи, `NaiveSpellCheckTool` ще използваме косинусова прилика (`cosine similarity`) с 2-грами.
### N-грами
Най-просто казано, N-грамите разбиват една дума на X последователни части от по N символа.\
Например, всички 3-грами на думата `latop` ще са: [`la`, `at`, `to`, `op`]

### Косинусова прилика
Косинусовата прилика между две думи, представлява косинуса на ъгъла между техните вектори и се изчислява по следната формула: `V1 . V2 / (|V1| * |V2|)`, където:
- V1 и V2 са векторите на дума1 и дума2
- |V1| и |V2| са [дължините на тези вектори](https://bg.khanacademy.org/computing/computer-programming/programming-natural-simulations/programming-vectors/a/vector-magnitude-normalization)
- V1 . V2 е [векторното произведение на V1 и V2](https://www.mathsisfun.com/algebra/vectors-dot-product.html)

![](https://miro.medium.com/max/3656/1*FFxFhSZ_GpuxkpOCNSxYyQ.png)


:bulb: *Как да представим една дума като 'вектор'?*\
Имайки предвид, че искаме алгоритъма ни да работи с 2-грами, един елемент от вектора на тази дума ще представлява двойка (<грамa>, <броя срещания на тaзи грамa в думата>).

### Пример
#### Думи
```
hello
hallo
```

#### Условно 'векторно представяне'
```
(he, 1), (el, 1), (ll, 1), (lo, 1)
(ha, 1), (al, 1), (ll, 1), (lo, 1)
*първите две грами са им различни, а вторите две им съвпадат
```
#### Дължина на векторите
```
sqrt(1^2 + 1^2 + 1^2 + 1^2) = 2
sqrt(1^2 + 1^2 + 1^2 + 1^2) = 2
```
#### Произведение на hello и hallo:
```
(ll, 1) * (ll, 1) + (lo, 1) * (lo, 1) = 2
*умножаваме само припокриващите се грами между двата 'вектора'
```
#### Косинусова прилика между hello и hallo:
```
((ll, 1) * (ll, 1) + (lo, 1) * (lo, 1)) / (2 * 2) = 0.5
```

## Бележки
### Важно
- Все още не е разрешено да променяте сигнатурата на методите, които ви предоставяме. Те се използват за автоматични тестове. В противен случай решенията ви не се компилират
- Проверете внимателно дали сте предали oчакваните от нас класове/интерфейси в правилните пакети
- Проверете внимателно дали не сте добавили някой checked excception към сигнатурата на някой метод

### Пакети

Спазвайте имената на пакетите на всички по-горе описани класове, тъй като в противен случай решението ви няма да може да бъде тествано от грейдъра.

```bash
src
╷
└─ bg/sofia/uni/fmi/mjt/spellchecker/
   └─ SpellChecker.java
   └─ NaiveSpellChecker.java
   └─ Metadata.java
   └─ (...)
test
╷
└─ bg/sofia/uni/fmi/mjt/spellchecker/
   └─ NaiveSpellCheckerTest.java
```

### Предаване

За да предадете решението си, архивирайте в **zip** цялата **src** папка на проекта и я качете в [grader.sapera.org](http://grader.sapera.org/WebObjects/Web-CAT.woa).

✒️ *Бележка*:  Grader-ът има upload size limit на submission-ите от 1mb - т.е. няма да може да качите оригиналнте dictionary и stopwords файлове като част от вашите тестове. Препоръчваме ви да тествате върху малка извадка от думи/стоп-думи.

### Оценяване

Решението може да ви донесе до 100 точки, като ще бъде оценявано:

* за функционална пълнота и коректност, и за автоматични тестове с добър code coverage (50% от оценката)
* от static code check plugin-а в grader.sapera.org (10% от оценката)
* за спазване на принципите за чист код и подбиране на оптимални за задачата структури от данни (40% от оценката)


✒️ *Бележка*: Refernce тестовете се пускат **СЛЕД** крайния срок за предаване на домашното

### Желаем ви успех!