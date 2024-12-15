# TF-IDF - Term Frequency - Inverse Document Frequency

## Какво е?

В случая, мярка за "важността" на една дума от описание на книга на фона на думите от описанията на всички книги.

### Изчисляваме TF

TF за дадена дума е броят нейни срещания в описанието, разделен на общия брой думи в описанието на книгата.

Пример:
За книга `Book X` с описание : `academy superhero club superhero` бихме получили:
```
TF(academy, Book X) = 0.25
TF(club, Book X) = 0.25
TF(superhero, Book X) = 0.5
```

### IDF

IDF се изчислява като логаритъм от частното на общия брой книги и броя книги, които съдържат съответната дума в описанието си.

Пример:

Нека разгледаме книгите X, Y, Z:
```
Book X with description "academy superhero club superhero"
Book Y with description "superhero mission save club"
Book Z with description "crime murder mystery club"
```
И да изчислим IDF оценката на superhero:
Брой книги: 3
Брой книги, в описанието на които се среща думата superhero: 2
Отговор:

```
IDF('superhero',All books) = log(3/2) = log(1.5) = 0.17
```

### TF-IDF

След като вече имаме изчислени TF и IDF, ги умножаваме, за да получим TF-IDF:

```
TFIDF('superhero', Book X, All Books) = TF('superhero', Book X) * IDF('superhero', All Books) = 0.5 * 0.17 =  0.085
```

Методът `computeTFIDF(Book book)` в класа `TFIDFSimilarityCalculator` се очаква да върне `Map<String, Double>`, където ключът е дума от описанието на книгата, а стойността - нейната `TF-IDF` стойност. 

## Book similarity

След като сме изчислили нужните TF-IDF метрики, можем да пристъпим към пресмятането на "подобността" на две книги.
Съществуват различни алгоритми за нейното изчисление:

  - Cosine Similarity (най-популярният) - повече информация [тук](https://en.wikipedia.org/wiki/Cosine_similarity#:~:text=In%20data%20analysis%2C%20cosine%20similarity,the%20product%20of%20their%20lengths)
  - Euclidean Distance - повече информация [тук](https://en.wikipedia.org/wiki/Euclidean_distance) в секцията Distance formulas / higher dimensions
  - Manhattan Distance - повече информация [тук](https://simple.wikipedia.org/wiki/Manhattan_distance)

В класа `TFIDFSimilarityCalculator` се изисква само да изчислите TF-IDF оценките на думите от описанието на всяка една от двете книги. В него ще намерите и `private` методи, имплементиращи `Cosine Similarity`.
След като сме получили `Map<String, Double>` оценките на думите на двете книги, използваме `double cosineSimilarity(Map<String, Double> first, Map<String, Double> second)`, за да получим крайната оценка.
