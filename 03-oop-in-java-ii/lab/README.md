# Netflix :popcorn:

*Netflix* e една от най-известните видео стрийминг услуги в света.

Целта на упражнението ни тази седмица е, да създадем набор от класове и интерфейси, с които да имплементираме част от основните ѝ функционалности.

## Условие

Имплементацията на задачата не бива да се ограничава само до дадените класове и интерфейси. Проявете креативност и помислете за начини, по които може да улесните работата си и да намалите изписаното количество код.

:warning: Добавянето на допълнителни методи в дадените интерфейси **не е разрешено**.

### Netflix

В пакета `bg.sofia.uni.fmi.mjt.netflix.platform` създайте клас `Netflix`, който има следния конструктор:

```java
public Netflix(Account[] accounts, Streamable[] streamableContent)
```
и имплементира интерфейса `StreamingService`:

```java
package bg.sofia.uni.fmi.mjt.netflix.platform;

import bg.sofia.uni.fmi.mjt.netflix.account.Account;
import bg.sofia.uni.fmi.mjt.netflix.content.Streamable;
import bg.sofia.uni.fmi.mjt.netflix.exceptions.ContentUnavailableException;

public interface StreamingService {

    /**
    * Simulates watching activity for the given user.
    * @param user the user that will watch the video. The user must be registered in the platform in order to access its contents.
    * @param videoContentName the exact name of the video content: movie or series
    *                         If the content is of type Series, we assume that the user will watch all episodes in it.
    * @throws ContentUnavailableException if the content is age restricted and the user is not yet permitted to access it.
    * @throws UserNotFoundException if the user is not registered in the platform.
    * @throws ContentNotFoundException if the content is not present in the platform.
    */
    void watch(Account user, String videoContentName) throws ContentUnavailableException;

   /**
    * @param videoContentName the exact name of the video content: movie or series
    * @return the Streamable resource with name that matches the provided name or null if no such content exists in the platform.
    */
    Streamable findByName(String videoContentName);

    /**
    * @return the most watched Streamable resource available in the platform or null if no streams were done yet.
    */
    Streamable mostViewed();

    /**
    * @return the minutes spent by all users registered in the platform while watching streamable content.
    */
    int totalWatchedTimeByUsers();

}
```

### Видео съдържание

Създайте два класа - филм и сериал - `Movie` и `Series`, които имат следните конструктори:

```java
public Movie(String name, Genre genre, PgRating rating, int duration)
public Series(String name, Genre genre, PgRating rating, Episode[] episodes)
```

където `Episode` има контруктор:

```java
Episode(String name, int duration)
```

`Movie` и `Series` имплементират интерфейса `Streamable`:

```java
package bg.sofia.uni.fmi.mjt.netflix.content;

import bg.sofia.uni.fmi.mjt.netflix.content.enums.PgRating;

public interface Streamable {
    /**
    * @return the title of the streamable content.
    */
    String getTitle();

    /**
    * @return the content duration in minutes.
    */
    int getDuration();

    /**
    * @return the PG rating of the streamable content.
    */
    PgRating getRating();
}
```

Всяко видео съдържание има `Genre` и `PgRating`(Parental Guidance Rating), които са от тип `enum`:

```java
package bg.sofia.uni.fmi.mjt.netflix.content.enums;

public enum Genre { ACTION, HORROR, COMEDY }
```

```java
package bg.sofia.uni.fmi.mjt.netflix.content.enums;

public enum PgRating {
    G("General audience"), // it is available for everyone
    PG13("May be inappropriate for children under 13"), // it is available only for people aged 14 and over
    NC17("Adults Only"); // it is available only for people aged 18 and over

    private final String details;

    PgRating(String details) {
        this.details = details;
    }
}
```

### Account

Потребителите на платформата се представят чрез класа `Account`.
Той е част от пакета `bg.sofia.uni.fmi.mjt.netflix.account` и има конструктор:

```java
Account(String username, LocalDateTime birthdayDate)
```

## Структура на проекта

Преди предаване на проекта в grader.sapera.org, се уверете, че следната структура е спазена:

```
src
└── bg.sofia.uni.fmi.mjt.netflix
    ├── account
    │      └── Account.java
    ├── content
    │      ├── Streamable.java
    │      ├── Movie.java
    │      ├── Series.java
    │      ├── Episode.java
    │      └── enums
    │            ├── Genre.java
    │            └── PgRating.java
    ├── exceptions
    │      ├── UserNotFoundException.java
    │      ├── ContentNotFoundException.java
    │      └── ContentUnavailableException.java
    └── platform
           ├── StreamingService.java
           └── Netflix.java
```

## Добавяне на решение

В grader.sapera.org качете `.zip` архив на `src` директорията на проекта ви.
