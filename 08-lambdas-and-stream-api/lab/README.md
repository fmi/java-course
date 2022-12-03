# Netflix Recommender :popcorn:

## Условие

Ще създадем програма, която предоставя статистики, търсачка и прост *recommendation engine*, базирани на реален *dataset*, 
съдържащ данни за сериали и филми в streaming платформата [Netflix](https://www.netflix.com), която понастоящем има над 222 милиона потребители в цял свят. *Dataset*-ът съдържа данни за над 5,400 сериала и филми и е взет от [kaggle](https://www.kaggle.com/datasets/victorsoeiro/netflix-tv-shows-and-movies?select=titles.csv), онлайн платформата за machine learning и data science на Google.
За да опростим парсването на данните, сме обработили предварително файла, като може да го свалите [от тук](./resources/dataset.csv).

Всеки ред от файла съдържа информация за един сериал или филм, като полетата са разделени със запетая (стандартен формат, известен като [Comma-Separated Values, или CSV](https://en.wikipedia.org/wiki/Comma-separated_values)):

`id,title,type,description,release_year,runtime,genres,seasons,imdb_id,imdb_score,imdb_votes`

**Забележки:**

- Първият ред на файла съдържа имената на полетата
- Семантиката и възможните стойности на всички полета, може да разгледате в [kaggle](https://www.kaggle.com/datasets/victorsoeiro/netflix-tv-shows-and-movies?select=titles.csv)
- Разделител между полетата на всеки ред е запетая (`,`)

### 1. Зареждане и обработка на данните

Преди да започнем със същинската част на анализа на *dataset*-a, ще заредим данните в паметта.

Една от първите стъпки в задачите за анализ на данни и machine learning, винаги е "изчистване/подготовка" на данните. В общия случай, допускаме, че може да има зле форматирани данни, може да липсват елементи, на които разчитаме, или да има такива, които не очакваме.

Имплементирайтe `record`-a `Content` с компоненти
    `(String id, String title, ContentType type, String description, int releaseYear,
        int runtime, List<String> genres, int seasons, String imdbId, double imdbScore,
        double imdbVotes)`,

който представлява сериал или филм от *dataset*-a.

Типът съдържание (сериал или филм), се представлява от `enum`-a 

```java
package bg.sofia.uni.fmi.mjt.netflix;

public enum ContentType {
    MOVIE, SHOW
}
```

### 2. Netflix Recommender

След като разполагаме с имплементация на този клас, можем да заредим данните в подходяща колекция, от която да вземем поток и (декларативно) да определим разнообразни статистики, както и да имплементираме търсене на сериали и филми по различни критерии.

Класът, който ще предоставя API за достъп до *dataset*-a, е `NetflixRecommender`, като частична негова имплементация може да намерите [тук](./resources/NetflixRecommender.java), а задачата ви ще бъде да я довършите.

#### Търсене по ключови думи

API-то дава възможност и за търсене на сериали и филми по ключови думи от описанието им. За разделители между думите в описанието, приемаме whitespaces и пунктуационните символи (може да използвате следния regex: `"[\\p{IsPunctuation}\\s]+"`). Всяка дума се състои от поне един символ. При търсенето, пренебрегваме разликата в капитализацията на буквите.

### 3. Тестване

Валидирайте решението си чрез unit тестове.

## Структура на проекта

Спазвайте имената на пакетите на всички по-горе описани типове, тъй като в противен случай, решението ви няма да може да бъде автоматично тествано.

```
src
└─ bg.sofia.uni.fmi.mjt.netflix
    ├── Content.java
    ├── ContentType.java
    ├── NetflixRecommender.java
    └── (...)
test
└─ bg.sofia.uni.fmi.mjt.netflix
    └── (...)
```

В грейдъра качете общ `zip` архив на папки `src` и `test`.
