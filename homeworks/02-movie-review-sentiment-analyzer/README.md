# Домашно 2

## Movie Review Sentiment Analyzer

`Краен срок: 08.01.2023, 23:59`

### Условие

*Sentiment Analysis* е процесът по алгоритмично идентифициране и категоризиране на мнения, изразени в свободен текст, особено за да се определи дали отношението на автора към конкретна тема, продукт и т.н. е позитивно, негативно или неутрално.

*Machine Learning* е особено нашумяло в наши дни направление в компютърните науки, което изучава класове от алгоритми, които "се учат" от данни. Такъв тип алгоритми работят, като изграждат модел от примерни входни данни и използват този модел, за да правят предсказания или взимат решения.

Задачата е, да се имплементира sentiment analyzer за филмови отзиви, който автоматично ще определя степента на позитивност на даден отзив в свободен текст.

Например, нашият алгоритъм би определил отзива 

*"Dire disappointment: dull and unamusing freakshow"*

като твърдо негативен, докато отзивът

*"Immersive ecstasy: energizing artwork!"*

ще се класифицира като еднозначно позитивен.

Данните, от които ще "учи" нашият алгоритъм, са множество от 8,528 филмови отзива (ревюта), за които отношението на автора е било оценено от човек по скала от 0 до 4 със следната семантика:

| рейтинг | семантика         |
| ------- | ----------------- |
| 0       | negative          |
| 1       | somewhat negative |
| 2       | neutral           |
| 3       | somewhat positive |
| 4       | positive          |

Ще използваме data set от сайта [Rotten Tomatoes](https://www.rottentomatoes.com/), използван за престижния [Кaggle machine learning competition](https://www.kaggle.com/c/sentiment-analysis-on-movie-reviews).

Данните са налични в текстовия файл [reviews.txt](https://github.com/fmi/java-course/tree/master/homeworks/02-movie-review-sentiment-analyzer/resources/reviews.txt), като всеки ред от файла започва с рейтинг, следван от интервал и текста на отзива, например:

```
4 The performances are an absolute joy .
```

Имайте предвид, че е напълно очаквано в подобен real-life data set да има typos, жаргонни или направо несъществуващи думи. 

Има обаче едно множество от често срещани в свободен текст думи, които носят твърде малко семантика: определителни членове, местоимения, предлози, съюзи и т.н. Такива думи се наричат *stopwords* и много алгоритми, свързани с обработка на естествен език (NLP, natural language processing), ги игнорират - т.е. премахват ги от съответните входни текстове, защото внасят "шум", т.е. намаляват качеството на резултата. Няма еднозначна дефиниция (или речник), коя дума е stopword в даден език. В нашия алгоритъм, ще ползваме списъка от 174 stopwords в английския език, записани по една на ред в текстовия файл [stopwords.txt](https://github.com/fmi/java-course/tree/master/homeworks/02-movie-review-sentiment-analyzer/resources/stopwords.txt), който сме заимствали от сайта [ranks.nl](https://www.ranks.nl/stopwords).

Алгоритъмът, който трябва да имплементирате, е следният:

Обучение:

1. Изчитат се отзивите в [reviews.txt](https://github.com/fmi/java-course/tree/master/homeworks/02-movie-review-sentiment-analyzer/resources/reviews.txt)
2. Изчислява се sentiment score на всяка дума като средно аритметично (без закръгляване) на всички рейтинги, в които участва дадената дума. Дума е последователност от малки и главни латински букви, цифри и символа за апостроф (') с дължина поне два символа. Думите са case-insensitive, т.е. "Movie", "movie" и "movIE" се третират като една и съща дума. Един отзив се състои от думи, разделени с разделители: интервали, табулации и препинателни знаци - въобще, всеки символ, който не е буква, цифра или апостроф. Stopwords се игнорират, т.е. не се взимат под внимание.

Разпознаване:

1. По даден текст на отзив се изчислява неговият sentiment score като средно аритметично (без закръгляване) на sentiment scores на всяка дума в отзива. Дефиницията на дума е същата като по-горе, и stopwords отново се игнорират. Игнорират се също всички (непознати) думи, за които алгоритъмът не е обучен, т.е. не се срещат нито веднъж в [reviews.txt](https://github.com/fmi/java-course/tree/master/homeworks/02-movie-review-sentiment-analyzer/resources/reviews.txt). Sentiment score на отзив, в който не се съдържа нито една дума с известен sentiment score (състои се само от непознати думи и/или stopwords), се приема за -1.0 (unknown).

В пакет `bg.sofia.uni.fmi.mjt.sentiment` създайте клас `MovieReviewSentimentAnalyzer`, който имплементира интерфейса `SentimentAnalyzer` по-долу и има конструктор

`public MovieReviewSentimentAnalyzer(Reader stopwordsIn, Reader reviewsIn, Writer reviewsOut)`

Kато трите параметъра са съответно поток за четене на [stopwords.txt](https://github.com/fmi/java-course/tree/master/homeworks/02-movie-review-sentiment-analyzer/resources/stopwords.txt), поток за четене на [reviews.txt](https://github.com/fmi/java-course/tree/master/homeworks/02-movie-review-sentiment-analyzer/resources/reviews.txt) и поток за писане в [reviews.txt](https://github.com/fmi/java-course/tree/master/homeworks/02-movie-review-sentiment-analyzer/resources/reviews.txt).

```java
package bg.sofia.uni.fmi.mjt.sentiment;

import java.util.List;

public interface SentimentAnalyzer {

    /**
     * @param review the text of the review
     * @return the review sentiment as a floating-point number in the interval [0.0,
     * 4.0] if known, and -1.0 if unknown.
     */
    double getReviewSentiment(String review);

    /**
     * @param review the text of the review
     * @return the review sentiment as a name: "negative", "somewhat negative",
     * "neutral", "somewhat positive", "positive" or "unknown"
     */
    String getReviewSentimentAsName(String review);

    /**
     * @param word
     * @return the review sentiment of the word as a floating-point number in the
     * interval [0.0, 4.0] if known, and -1.0 if unknown
     */
    double getWordSentiment(String word);

    /**
     * @param word
     * @return the number of occurrences of the word in all reviews
     */
    int getWordFrequency(String word);

    /**
     * Returns a list of the n most frequent words found in the reviews, sorted by frequency in decreasing order
     *
     * @throws {@link IllegalArgumentException}, if n is negative
     */
    List<String> getMostFrequentWords(int n);

    /**
     * Returns a list of the n most positive words in the reviews, sorted by sentiment score in decreasing order
     *
     * @throws {@link IllegalArgumentException}, if n is negative
     */
    List<String> getMostPositiveWords(int n);

    /**
     * Returns a list of the n most negative words in the reviews, sorted by sentiment score in ascending order
     *
     * @throws {@link IllegalArgumentException}, if n is negative
     */
    List<String> getMostNegativeWords(int n);

    /**
     * Appends a review to the end of the data set.
     * Any information from the data set stored in memory should be automatically updated.
     *
     * @param review    The text part of the review
     * @param sentiment the given rating
     * @return true if the operation was successful and false if an issue has occurred and the review is not stored
     * @throws {@link IllegalArgumentException}, if review is null, empty or blank,
     *                or if the sentiment is not in the [0.0, 4.0] range
     */
    boolean appendReview(String review, int sentiment);

    /**
     * Returns the total number of words with known sentiment score
     */
    int getSentimentDictionarySize();

    /**
     * Returns whether a word is a stopword
     */
    boolean isStopWord(String word);

}
```

Нашият data set от ревюта трябва да може да се разширява. Това ще допринесе за допълнителна точност при определяне на sentiment във времето. Точно това е идеята и на `Writer`-a и метода `appendReview(String review, int sentiment)` - чрез тях ще добавяме нови ревюта към `MovieReviews.txt`.

Текстът на ревюто не може да съдържа символ за нов ред. Уверете се обаче, че при `appendReview`-a добавяте и `System.lineSeparator()`, така че всяко ревю да е на отделен ред.
При добавяне на нови ревюта и оценки, преизчисляваме sentiment-a на всички думи в data set-a. 

### **Предаване**

Качете `.zip` архив на `src` и `test` директориите на проекта в съответния assignment в грейдъра. Ако ползвате статични файлове за тестване, те трябва да се намират директно на нивото на `src` и `test`, без да са в отделна директория. Препоръчваме обаче да създадете автоматични тестове, които не ползват файлове.

### **Тестване**

Създайте автоматични тестове, с които да тествате решението си.

### **Структура на проекта**

Спазвайте имената на пакетите на всички по-горе описани класове, тъй като в противен случай решението ви няма да може да бъде тествано от грейдъра.

```bash
src
└─ bg.sofia.uni.fmi.mjt.sentiment
    ├── MovieReviewSentimentAnalyzer.java
    ├── SentimentAnalyzer.java
    └── (...)
test
└─ bg.sofia.uni.fmi.mjt.sentiment
    └── (...)
```

### Оценяване

Решението може да ви донесе до 100 точки, като ще бъде оценявано за:

* функционална пълнота и коректност, и за автоматични тестове с добър code coverage (60% от оценката)
* добър обектно-ориентиран дизайн, спазване на правилата за чист код и подбиране на оптимални за задачата структури от данни (40% от оценката)


### Желаем ви успех! :four_leaf_clover: 
