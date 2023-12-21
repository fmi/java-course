# Домашно 2

## Space Scanner :rocket:

`Краен срок: 27.12.2023, 23:59`

### Условие

Ще създадем приложение за извличане на статистически резултати за мисиите в Космоса от 1957г. насам, заедно с ракетите,
използвани за съответните мисии.

Ще използваме data set от [kaggle](https://www.kaggle.com). Данните за мисиите са налични в CSV файлa [all-missions-from-1957.csv](./resources/all-missions-from-1957.csv). Данните за ракетите са налични в CSV файла [all-rockets-from-1957.csv](./resources/all-rockets-from-1957.csv).

Имайте предвид, че е възможно в real-life data set да има непълни записи, т.е. да липсва информация за дадена колона.
Такива ще моделираме с `Optional`. Обърнете внимание, че символът за запетая участва както като разделител между колоните, така и като част от данните в самите тях.

### Задължителни интерфейси и класове

В пакета `bg.sofia.uni.fmi.mjt.space` създайте клас `MJTSpaceScanner`, който има конструктор, приемащ мисиите, под формата на `Reader`,
ракетите, отново под формата на `Reader`, както и частен ключ, който се използва за криптиране и декриптиране на конфиденциална информация,
използвайки **Rijndael** (или **AES**) алгоритъма.

```java
public MJTSpaceScanner(Reader missionsReader, Reader rocketsReader, SecretKey secretKey)
```

Класът `MJTSpaceScanner` имплементира интерфейса `SpaceScannerAPI`:

```java
package bg.sofia.uni.fmi.mjt.space;

import bg.sofia.uni.fmi.mjt.space.mission.Mission;
import bg.sofia.uni.fmi.mjt.space.mission.MissionStatus;
import bg.sofia.uni.fmi.mjt.space.rocket.Rocket;
import bg.sofia.uni.fmi.mjt.space.rocket.RocketStatus;

import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SpaceScannerAPI {
    /**
     * Returns all missions in the dataset.
     * If there are no missions, return an empty collection.
     */
    Collection<Mission> getAllMissions();

    /**
     * Returns all missions in the dataset with a given status.
     * If there are no missions, return an empty collection.
     *
     * @param missionStatus the status of the missions
     * @throws IllegalArgumentException if missionStatus is null
     */
    Collection<Mission> getAllMissions(MissionStatus missionStatus);

    /**
     * Returns the company with the most successful missions in a given time period.
     * If there are no missions, return an empty string.
     *
     * @param from the inclusive beginning of the time frame
     * @param to   the inclusive end of the time frame
     * @throws IllegalArgumentException if from or to is null
     * @throws TimeFrameMismatchException if to is before from
     */
    String getCompanyWithMostSuccessfulMissions(LocalDate from, LocalDate to);

    /**
     * Groups missions by country.
     * If there are no missions, return an empty map.
     */
    Map<String, Collection<Mission>> getMissionsPerCountry();

    /**
     * Returns the top N least expensive missions, ordered from cheapest to more expensive.
     * If there are no missions, return an empty list.
     *
     * @param n             the number of missions to be returned
     * @param missionStatus the status of the missions
     * @param rocketStatus  the status of the rockets
     * @throws IllegalArgumentException if n is less than ot equal to 0, missionStatus or rocketStatus is null
     */
    List<Mission> getTopNLeastExpensiveMissions(int n, MissionStatus missionStatus, RocketStatus rocketStatus);

    /**
     * Returns the most desired location for missions per company.
     * If there are no missions, return an empty map.
     */
    Map<String, String> getMostDesiredLocationForMissionsPerCompany();

    /**
     * Returns the company mapped to its location with most successful missions.
     * If there are no missions, return an empty map.
     *
     * @param from the inclusive beginning of the time frame
     * @param to   the inclusive end of the time frame
     * @throws IllegalArgumentException if from or to is null
     * @throws TimeFrameMismatchException if to is before from
     */
    Map<String, String> getLocationWithMostSuccessfulMissionsPerCompany(LocalDate from, LocalDate to);

    /**
     * Returns all rockets in the dataset.
     * If there are no rockets, return an empty collection.
     */
    Collection<Rocket> getAllRockets();

    /**
     * Returns the top N tallest rockets, in decreasing order.
     * If there are no rockets, return an empty list.
     *
     * @param n the number of rockets to be returned
     * @throws IllegalArgumentException if n is less than ot equal to 0
     */
    List<Rocket> getTopNTallestRockets(int n);

    /**
     * Returns a mapping of rockets (by name) to their respective wiki page (if present).
     * If there are no rockets, return an empty map.
     */
    Map<String, Optional<String>> getWikiPageForRocket();

    /**
     * Returns the wiki pages for the rockets used in the N most expensive missions.
     * If there are no missions, return an empty list.
     *
     * @param n             the number of missions to be returned
     * @param missionStatus the status of the missions
     * @param rocketStatus  the status of the rockets
     * @throws IllegalArgumentException if n is less than ot equal to 0, or missionStatus or rocketStatus is null
     */
    List<String> getWikiPagesForRocketsUsedInMostExpensiveMissions(int n, MissionStatus missionStatus,
                                                                   RocketStatus rocketStatus);

    /**
     * Saves the name of the most reliable rocket in a given time period in an encrypted format.
     *
     * @param outputStream the output stream where the encrypted result is written into
     * @param from         the inclusive beginning of the time frame
     * @param to           the inclusive end of the time frame
     * @throws IllegalArgumentException if outputStream, from or to is null
     * @throws CipherException if the encrypt/decrypt operation cannot be completed successfully
     * @throws TimeFrameMismatchException if to is before from
     */
    void saveMostReliableRocket(OutputStream outputStream, LocalDate from, LocalDate to) throws CipherException;
}
```

#### Record `Mission`

Една мисия се моделира от следния `record`:

```java
Mission(String id, String company, String location, LocalDate date, Detail detail, RocketStatus rocketStatus, Optional<Double> cost, MissionStatus missionStatus)
```

В нея, един **Detail** се моделира от следния `record`:

#### Record `Detail`

```java
public record Detail(String rocketName, String payload)
```

който се състои от двa компонента, разделени в data set-a един от друг с "|". Форматът е: `<rocketName>|<payload>`.

Възможните резултати за всяка мисия са един от `Success, Failure, Partial Failure, Prelaunch Failure` и се моделират от следния `enum`:

#### Enum `MissionStatus`

```java
package bg.sofia.uni.fmi.mjt.space.mission;

public enum MissionStatus {
    SUCCESS("Success"),
    FAILURE("Failure"),
    PARTIAL_FAILURE("Partial Failure"),
    PRELAUNCH_FAILURE("Prelaunch Failure");

    private final String value;

    MissionStatus(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
```

#### Record `Rocket`

Една ракета се моделира от следния record:

```java
public record Rocket(String id, String name, Optional<String> wiki, Optional<Double> height)
```

След дадена масия, изполваната ракета може да бъде все още активна (**StatusActive**), или вече да не е в експлоатация (**StatusRetired**). 
Моделираме го със следния `enum`:

#### Enum `RocketStatus`

```java
package bg.sofia.uni.fmi.mjt.space.rocket;

public enum RocketStatus {
    STATUS_RETIRED("StatusRetired"),
    STATUS_ACTIVE("StatusActive");

    private final String value;

    RocketStatus(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
```

Трите record-a: `Mission`, `Detail` и `Rocket` трябва да имат публичен каноничен конструктор.

#### Rocket reliability

Reliability-то на дадена ракета ще пресмятаме по следната формула:

```
(2 * (броят на успешните мисии на ракетата) + (броят на неуспешните мисии на ракетата)) / (2 * (броят на всички мисии на ракетата))
```

Неуспешна мисия считаме за такава със статус MissionStatus.FAILURE, MissionStatus.PARTIAL_FAILURE или MissionStatus.PRELAUNCH_FAILURE.
Ракетите, които не са участвали в мисии, имат reliability 0.

Алгоритъмът за криптиране (**AES**) има имплементация в JDK-то (в `javax.crypto` пакета) и е разглеждан с code snippet на упражнение. Създайте клас **Rijndael**, който има следния конструктор:

```java
public Rijndael(SecretKey secretKey)
```

и имплементира интерфейса:

```java
package bg.sofia.uni.fmi.mjt.space.algorithm;

import java.io.InputStream;
import java.io.OutputStream;

public interface SymmetricBlockCipher {
    /**
     * Encrypts the data from inputStream and puts it into outputStream
     *
     * @param inputStream the input stream where the data is read from
     * @param outputStream the output stream where the encrypted result is written into
     * @throws CipherException if the encrypt/decrypt operation cannot be completed successfully
     */
    void encrypt(InputStream inputStream, OutputStream outputStream) throws CipherException;

    /**
     * Decrypts the data from inputStream and puts it into outputStream
     *
     * @param inputStream the input stream where the data is read from
     * @param outputStream the output stream where the decrypted result is written into
     * @throws CipherException if the encrypt/decrypt operation cannot be completed successfully
     */
    void decrypt(InputStream inputStream, OutputStream outputStream) throws CipherException;
}

```

### Тестване

Създайте автоматични тестове, с които да тествате решението си.

### Структура на проекта

Спазвайте имената на пакетите на всички по-долу описани класове, тъй като в противен случай решението ви няма да може да бъде тествано от грейдъра.

```bash
src
└── bg.sofia.uni.fmi.mjt.space
    ├── algorithm
    │    ├── Rijndael.java
    │    └── SymmetricBlockCipher.java
    ├── exception
    │    ├── CipherException.java
    │    └── TimeFrameMismatchException.java
    ├── mission
    │    ├── Detail.java
    │    ├── Mission.java
    │    └── MissionStatus.java
    ├── rocket
    │    ├── Rocket.java
    │    └── RocketStatus.java
    ├── MJTSpaceScanner.java
    ├── SpaceScannerAPI.java
    └── (...)
test
└── bg.sofia.uni.fmi.mjt.space
    └── (...)
```

Обърнете внимание, че при качване на решението ви, в грейдъра ще се изпълни само _smoke_ тест, чиято цел е да изчистите
евентуални проблеми с компилацията. Референтите тестове и Checkstyle статичният код анализ ще се изпълнят еднократно
след изтичане на крайния срок за предаване. За функционалната коректност и качеството на кода ще трябва да се погрижите
без тяхната помощ.

### Предаване

Качете `src` и `test` директориите на проекта (или `.zip` архив, който ги съдържа) в съответния assignment в грейдъра. Ако ползвате статични файлове за тестване, те трябва да се намират директно на нивото на `src` и `test`, без да са в отделна директория. Препоръчваме обаче да създадете автоматични тестове, които не ползват файлове.

### Оценяване

Решението може да ви донесе до 100 точки, като ще бъде оценявано за:

* функционална пълнота и коректност, и за автоматични тестове с добър code coverage (60% от оценката)
* добър обектно-ориентиран дизайн, спазване на правилата за чист код и подбиране на оптимални за задачата структури от данни (40% от оценката)

### Желаем ви успех! :four_leaf_clover: 
