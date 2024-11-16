# Unit Testing and Mocking

Освен писане на code from scratch, в практиката много често се налага и да поддържаме, fix-ваме или пишем тестове за вече съществуващ код.

Целта на упражнението днес е, да създадете и изпълните JUnit тестове спрямо налична имплементация.

---

## MJT Olympics  🏃‍🏊‍🚴‍🏅

Добре дошли на MJT Олимпийските игри: Злато, Слава и Код!

**Имплементацията може да бъде намерена в директорията [resources](./resources).**

За съжаление, в трескавата подготовка преди Игрите, време за написване на тестове за системата така и не се намери, и в резултат, в имплементацията ѝ се крият някои бъгове. Ще трябва да ги откриете и отстраните в процеса на тестване. За да бъде той ефективен, първо напишете тест за някой сценарий, след това оправете бъга, който сте намерили с него.

## Основни класове и тяхната функционалност

### Competitor

Всички състезатели в олимпийските игри имплементират интерфейса `Competitor`. Има една основна имплементация:

 - `Athlete`
  
Интерфейсът `Competitor` изглежда така:

```java
package bg.sofia.uni.fmi.mjt.olympics.competitor;

import java.util.Collection;

public interface Competitor {

    /**
     * Returns the unique identifier of the competitor.
     */
    String getIdentifier();

    /**
     * Returns the name of the competitor.
     */
    String getName();

    /**
     * Returns the nationality of the competitor.
     */
    String getNationality();

    /**
     * Returns an unmodifiable collection of medals won by the competitor.
     */
    Collection<Medal> getMedals();

    /**
     * Adds a medal to the competitor's collection of medals.
     *
     * @throws IllegalArgumentException if the medal is null.
     */
    void addMedal(Medal medal);

}
```

### Medal

При участието си в състезания, спортистите могат да спечелят няколко вида медали със стойности

- 🏅 GOLD
- 🥈 SILVER 
- 🥉 BRONZE 

```java
package bg.sofia.uni.fmi.mjt.olympics.competitor;

public enum Medal {
    GOLD, SILVER, BRONZE;
}
```

### Competition

Състезание в Олимпийските игри ще бъде описвано чрез следния record: 

```java
package bg.sofia.uni.fmi.mjt.olympics.competition;

import bg.sofia.uni.fmi.mjt.olympics.competitor.Competitor;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * @throws IllegalArgumentException when the competition name is null or blank
 * @throws IllegalArgumentException when the competition discipline is null or blank
 * @throws IllegalArgumentException when the competition's competitors is null or empty
 */
public record Competition(String name, String discipline, Set<Competitor> competitors) { // ... }
```

### Olympics

Основната логика на системата се съдържа в класа `MJTOlympics`, който имплементира следния интерфейс:

```java
package bg.sofia.uni.fmi.mjt.olympics;

import bg.sofia.uni.fmi.mjt.olympics.competition.Competition;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Competitor;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Medal;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public interface Olympics {

    /**
     * The method updates the competitors' medal statistics based on the competition result.
     *
     * @param competition the competition to update the statistics with
     * @throws IllegalArgumentException if the competition is null.
     * @throws IllegalArgumentException if a competitor is not registered in the Olympics.
     */
    void updateMedalStatistics(Competition competition);

    /**
     * Returns the nations, sorted in descending order based on the total medal count.
     * If two nations have an equal number of medals, they are sorted alphabetically.
     */
    TreeSet<String> getNationsRankList();

    /**
     * Returns the total number of medals, won by competitors from the specified nationality.
     *
     * @param nationality the nationality of the competitors
     * @return the total number of medals
     * @throws IllegalArgumentException when nationality is null
     * @throws IllegalArgumentException when nationality is not registered in the olympics
     */
    int getTotalMedals(String nationality);

    /**
     * Returns a map of nations and their respective medal amount, won from each competition.
     *
     * @return the nations' medal table
     */
    Map<String, EnumMap<Medal, Integer>> getNationsMedalTable();

    /**
     * Returns the set of competitors registered for the Olympics.
     *
     * @return the set of registered competitors
     */
    Set<Competitor> getRegisteredCompetitors();

}
```

## Пакети

Спазвайте имената на пакетите на всички по-горе описани класове, тъй като в противен случай решението ви няма да може да бъде тествано от грейдъра.

```
src
└── bg.sofia.uni.fmi.mjt.olympics
     ├── comparator
     │     ├── NationMedalComparator.java
     │     └── (...)     
     ├── competition
     │     ├── Competition.java
     │     ├── CompetitionResultFetcher.java
     │     └── (...)     
     ├── competitor
     │     ├── Competitor.java
     │     ├── Medal.java
     │     ├── Athlete.java
     │     └── (...)
     ├── MJTOlympics.java
     ├── Olympics.java
     └── (...)     
test
└── bg.sofia.uni.fmi.mjt.olympics
     └── (...)
```

## Забележки

- В грейдъра качете общ .zip архив на двете директории src и test.
- Не включвайте в архива jar-ките на JUnit и Mockito библиотеките. На грейдъра ги има, няма смисъл архивът с решението ви да набъбва излишно.

Успех и не се притеснявайте да задавате въпроси! ⭐
