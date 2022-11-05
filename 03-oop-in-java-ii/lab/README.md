# Escape Rooms :bat:

[Ескейп стаите (Escape Rooms)](https://en.wikipedia.org/wiki/Escape_room) са популярно развлечение, особено сред програмистите. Ще създадем платформа с информация за escape rooms, подобна на [vsichkistai.bg](http://vsichkistai.bg), където почитателите на този тип забавление ще се ориентират за най-интересните стаи и най-яките отбори. Тъкмо навреме, за да помогнем на една група приятели да изберат най-добрата “страшна стая” в града, която все още е свободна за посещение в нощта на Хелоуин.

### Escape Room Platform

В пакета `bg.sofia.uni.fmi.mjt.escaperoom` създайте клас `EscapeRoomPlatform`, който има конструктор

```java
public EscapeRoomPlatform(Team[] teams, int maxCapacity)
```
и имплементира интерфейсите `EscapeRoomAdminAPI` (за администраторите на платформата) и `EscapeRoomPortalAPI` (за потребителите):

```java
package bg.sofia.uni.fmi.mjt.escaperoom;

import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.PlatformCapacityExceededException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.TeamNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.room.EscapeRoom;
import bg.sofia.uni.fmi.mjt.escaperoom.team.Team;

public interface EscapeRoomAdminAPI {

    /**
     * Adds a new escape room to the platform.
     *
     * @param room the escape room to be added.
     * @throws IllegalArgumentException          if room is null.
     * @throws PlatformCapacityExceededException if the maximum number of escape rooms has already been reached.
     * @throws RoomAlreadyExistsException        if the specified room already exists in the platform.
     */
    void addEscapeRoom(EscapeRoom room) throws RoomAlreadyExistsException;

    /**
     * Removes the escape room with the specified name from the platform.
     *
     * @param roomName the name of the escape room to be removed.
     * @throws IllegalArgumentException if the room name is null, empty or blank.
     * @throws RoomNotFoundException    if the platform does not contain an escape room with the specified name.
     */
    void removeEscapeRoom(String roomName) throws RoomNotFoundException;

    /**
     * Returns all escape rooms contained in the platform.
     */
    EscapeRoom[] getAllEscapeRooms();

    /**
     * Registers a team achievement: escaping a room for the specified time.
     *
     * @param roomName   the name of the escape room.
     * @param teamName   the name of the team.
     * @param escapeTime the escape time in minutes.
     * @throws IllegalArgumentException if the room name or the team name is null, empty or blank,
     *                                  or if the escape time is negative, zero or bigger than the maximum time
     *                                  to escape for the specified room.
     * @throws RoomNotFoundException    if the platform does not contain an escape room with the specified name.
     */
    void registerAchievement(String roomName, String teamName, int escapeTime)
        throws RoomNotFoundException, TeamNotFoundException;

}

```

```java
package bg.sofia.uni.fmi.mjt.escaperoom;

import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.room.EscapeRoom;
import bg.sofia.uni.fmi.mjt.escaperoom.room.Review;
import bg.sofia.uni.fmi.mjt.escaperoom.team.Team;

public interface EscapeRoomPortalAPI {

    /**
     * Returns the escape room with the specified name.
     *
     * @param roomName the name of the escape room.
     * @return the escape room with the specified name.
     * @throws IllegalArgumentException if the room name is null, empty or blank
     * @throws RoomNotFoundException    if the platform does not contain an escape room with the specified name.
     */
    EscapeRoom getEscapeRoomByName(String roomName) throws RoomNotFoundException;

    /**
     * Adds a review for the escape room with the specified name.
     *
     * @param roomName the name of the escape room.
     * @throws IllegalArgumentException if the room name is null, empty or blank, or if the review is null
     * @throws RoomNotFoundException    if the platform does not contain an escape room with the specified name.
     */
    void reviewEscapeRoom(String roomName, Review review) throws RoomNotFoundException;

    /**
     * Returns all reviews for the escape room with the specified name,
     * in the order they have been added - from oldest to newest.
     * If there are no reviews, returns an empty array.
     *
     * @param roomName the name of the escape room.
     * @return the reviews for the escape room with the specified name
     * @throws IllegalArgumentException if the room name is null, empty or blank
     * @throws RoomNotFoundException    if the platform does not contain an escape room with the specified name.
     */
    Review[] getReviews(String roomName) throws RoomNotFoundException;

    /**
     * Returns the team with the highest rating. For each room successfully escaped (within the maximum
     * escape time), a team gets points equal to the room difficulty rank (1-4), plus bonus for fast escape:
     * +2 points for escape time less than or equal to 50% of the maximum escape time, or
     * +1 points for escape time less than or equal to 75% (and more than 50%) of the maximum escape time
     * The rating of a team is equal to the sum of all points collected.
     *
     * @return the top team by rating. If there are two or more teams with same highest rating, return any of them.
     * If there are no teams in the platform, returns null.
     */
    Team getTopTeamByRating();

}
```

### Escape Rooms

Стаите се идентифицират еднозначно по името си и се представят чрез класа `EscapeRoom` в пакета `bg.sofia.uni.fmi.mjt.escaperoom.room`, който има конструктор

```java
public EscapeRoom(String name, Theme theme, Difficulty difficulty, int maxTimeToEscape, double priceToPlay,
                      int maxReviewsCount)
```

и имплементира интерфейса `Ratable`:

```java
package bg.sofia.uni.fmi.mjt.escaperoom.rating;

public interface Ratable {

    /**
     * Returns the rating of a ratable object.
     *
     * @return the rating
     */
    double getRating();

}
```

Рейтингът на escape стаите е число с плаваща запетая в интервала [0.0, 10.0] и се смята като средноаритметично на рейтингите на потребителите, добавили мнение (`Review`) за съответната стая. Ако за дадена стая няма добавено нито едно мнение, приемаме, че рейтингът ѝ е 0.0. 

> **:warning: Бележка:** Преди да сте се замислили по въпроса - в конкретния scope на задачата, този общ интерфейс не е особено полезен - просто демонстрираме, че на практика различни неща могат да имат някакви сходства и в определен контекст могат да бъдат разглеждани заедно. Например, ако имахме потребителски интерфейс с leader board, който показва табличка с подадени ratable елементи в realtime и слага :trophy: emoji на първия елемент - тогава компонентът, който прави тази визуализация, не се интересува от типа на елементите, които визуализира, а само от това, че те имат някаква семантика за наредба и за "текущ победител".

`EscapeRoom` съдържа следните методи (при нужда или желание, може да добавяте допълнителни):

```java
    /**
     * Returns the name of the escape room.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the difficulty of the escape room.
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Returns the maximum time to escape the room.
     */
    public int getMaxTimeToEscape() {
        return maxTimeToEscape;
    }

    /**
     * Returns all user reviews stored for this escape room, in the order they have been added.
     */
    public Review[] getReviews() {
        // TODO: add implementation here
    }

    /**
     * Adds a user review for this escape room.
     * The platform keeps just the latest up to {@code maxReviewsCount} reviews and in case the capacity is full,
     * a newly added review would overwrite the oldest added one, so the platform contains
     * {@code maxReviewsCount} at maximum, at any given time. Note that, despite older reviews may have been
     * overwritten, the rating of the room averages all submitted review ratings, regardless of whether all reviews
     * themselves are still stored in the platform.
     *
     * @param review the user review to add.
     */
    public void addReview(Review review) {
        // TODO: add implementation here
    }
```

## Theme и Difficulty

Тематиката и трудността на даден escape room се представят съответно от следните два `enum`-a:

```java
package bg.sofia.uni.fmi.mjt.escaperoom.room;

public enum Theme {
    HORROR,
    FANTASY,
    SCIFI,
    MYSTERY
}
```

и 

```java
package bg.sofia.uni.fmi.mjt.escaperoom.room;

public enum Difficulty {

    EASY(1),
    MEDIUM(2),
    HARD(3),
    EXTREME(4);

    private int rank;

    Difficulty(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

}
```

## Review

Потребителите могат да предоставят мнение за дадена стая, което се моделира от `record`-a `Review(int rating, String reviewText)`, чиито компоненти са съответно рейтинг и текст на мнението. `Review` трябва да има компактен конструктор, който да валидира, че рейтингът е число в интервала [0, 10], а текстът на мнението не е `null` и дължината му не надхвърля 200 символа. При неуспешна валидация, конструкторът трябва да хвърля `IllegalArgumentException`.

## Отбори

Отборите се представят чрез класа `Team` в пакета `bg.sofia.uni.fmi.mjt.escaperoom.team`, инстанции от който не могат да се създават директно, а се получават чрез публичен статичен метод със сигнатура `of(String name, TeamMember[] members)`.
Класът `Team` също имплементира интерфейса `Ratable` и съдържа следните методи (при нужда или желание, може да добавяте допълнителни):

```java
    /**
     * Updates the team rating by adding the specified points to it.
     *
     * @param points the points to be added to the team rating.
     * @throws IllegalArgumentException if the points are negative.
     */
    public void updateRating(int points) {
        // TODO: add implementation here
    }

    /**
     * Returns the team name.
     */
    public String getName() {
        return name;
    }
```

Членовете на отборите се моделират от `record`-a `TeamMember`:

```java
public record TeamMember(String name, LocalDateTime birthday) {
}
```

### Пакети

Спазвайте имената на пакетите на всички по-горе описани типове, тъй като в противен случай, решението ви няма да може да бъде автоматично тествано.

```
src
└── bg.sofia.uni.fmi.mjt.escaperoom
    ├── exception
    │      ├── PlatformCapacityExceededException.java
    │      ├── RoomAlreadyExistsException.java
    │      ├── RoomNotFoundException.java
    │      └── TeamNotFoundException.java
    ├── rating
    │      └── Ratable.java
    ├── room
    │      ├── Difficulty.java
    │      ├── EscapeRoom.java
    │      ├── Review.java
    │      └── Theme.java
    ├── team
    │      ├── Team.java
    │      └── TeamMember.java
    ├── EscapeRoomAdminAPI.java
    ├── EscapeRoomPlatform.java
    └── EscapeRoomPortalAPI.java
```

### :warning: Забележки

- Не променяйте по никакъв начин интерфейсите, дадени в условието.
- Използването на структури от данни, различни от масив, **не е позволено**. Задачата трябва да се реши с помощта на знанията от курса до момента.
