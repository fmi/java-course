# Football Player Analyzer :soccer:

Съвсем скоро, Манчестър Юнайтед ще се сдобие с нов миноритарен собственик, а с него започват и промените в клуба. Очаква се позицията на Sporting director да се освободи и това е нашият златен шанс.

За да бъдем максимално подготвени, ще използваме натрупаните знания и ще имплементираме приложение за анализ на данни за футболисти. Анализът ни ще се базира на реален *dataset*, съдържащ данни за над 17,000 футболисти, който е взет от [kaggle](https://www.kaggle.com/datasets/maso0dahmed/football-players-data), онлайн платформата за machine learning и data science на Google. За да опростим парсването на данните, сме обработили предварително файла, като може да го свалите от [тук](./resources/fifa_players_clean.csv).

Всеки ред от файла съдържа информация за един футболист, като полетата са разделени с точка и запетая (CSV формат с разделител ';'):

`name;full_name;birth_date;age;height_cm;weight_kgs;positions;nationality;overall_rating;potential;value_euro;wage_euro;preferred_foot`

**Забележки:**

- Първият ред на файла съдържа имената на полетата
- Разделител между полетата на всеки ред е точка и запетая (`;`)
- birth_date не е в default-ния за [LocalDate](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/time/LocalDate.html) формат за десериализиране

### 1. Зареждане и обработка на данните

Преди да започнем със същинската част на анализа на *dataset*-a, ще заредим данните в паметта.

Една от първите стъпки в задачите за анализ на данни и machine learning, винаги е "изчистване/подготовка" на данните. В общия случай, допускаме, че може да има зле форматирани данни, може да липсват елементи, на които разчитаме, или да има такива, които не очакваме.

Имплементирайтe `record`-a `Player` с компоненти
    `(String name, String fullName, LocalDate birthDate, int age, double heightCm, double weightKg,
        List<Position> positions, String nationality, int overallRating, int potential, long valueEuro, long wageEuro,
        Foot preferredFoot)`,

който представлява футболист от *dataset*-a. Инстанции на `Player` се създават чрез публичен статичен factory метод

```java
public static Player of(String line)
```

където `line` е низ, представляващ правилно форматиран ред с данни за футболист от CSV файла.

Възможните позиции на футболист се представляват от `enum`-a

```java
public enum Position {
    // Diagram: https://fifauteam.com/fifa-21-positions/

    ST, // Striker
    LM, // Left Midfielder
    CF, // Centre Forward
    GK, // Goalkeeper
    RW, // Right Winger
    CM, // Centre Midfielder
    LW, // Left Winger
    CDM, // Defensive Midfielder
    CAM, // Attacking midfielder
    RB, // Right back
    LB, // Left back
    LWB, // Left Wing-back
    RM, // Right Midfielder
    RWB, // Right Wing-back
    CB; // Centre Back
}
```

Предпочитаният крак на футболист се представлява от `enum`-a 

```java
public enum Foot {
    LEFT, RIGHT;
}
```

### 2. Football Player Analyzer

След като разполагаме с имплементация на този `record`, можем да заредим данните в подходяща колекция, от която да вземем поток и (декларативно) да определим разнообразни статистики, както и да имплементираме търсене на футболисти по различни критерии.

Класът, който ще предоставя API за достъп до *dataset*-a, е `FootballPlayerAnalyzer`, като частична негова имплементация може да намерите [тук](./resources/FootballPlayerAnalyzer.java), а задачата ви ще бъде да я довършите.

### 3. Тестване

Валидирайте решението си чрез unit тестове.

## Структура на проекта

Спазвайте имената на пакетите на всички по-горе описани типове, тъй като в противен случай, решението ви няма да може да бъде автоматично тествано.

```
src
└─ bg.sofia.uni.fmi.mjt.football
    ├── Foot.java
    ├── Position.java
    ├── Player.java
    ├── FootballPlayerAnalyzer.java
    └── (...)
test
└─ bg.sofia.uni.fmi.mjt.football
    └── (...)
```

В грейдъра качете общ `zip` архив на папки `src` и `test`.
