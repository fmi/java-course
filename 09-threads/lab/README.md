# Многонишково програмиране

## Race Track :racing_car: :vertical_traffic_light: :fuelpump:

Ще симулираме писта за състезание с коли. Пистата се състои от `Pit` (Бокс), в който има определен брой `PitTeam`-s - екипи от механици. За разлика от традиционните състезания, на нашата писта всеки екип може да обслужи всяка от колите. Колите на пистата карат определено време, докато финишират състезанието или имат нужда от обслужване и влизат в бокса. Ако има свободен екип, той обслужва колата, ако ли не, колата изчаква такъв да се освободи.

## Условие

Задачата е разделена на 7 стъпки, като препоръката ни е да започнете с проста частична имплементация, която да допълните и подобрите впоследствие.

**1. Създайте клас `RaceTrack`**

Класът трябва да има публичен конструктор, който приема цяло число (броят на екипите в бокса) и имплементира интерфейса `Track`:

```java
package bg.sofia.uni.fmi.mjt.race.track;

import java.util.List;

import bg.sofia.uni.fmi.mjt.race.track.pit.Pit;

public interface Track {

    /**
     * A car enters the pit when it needs maintenance or when it finishes the race.
     * A car can finish the race, if it has no more pitStops to be done.
     * 
     * @param car - the car which enters the pit
     */
    void enterPit(Car car);

    /**
     * Returns the number of cars which already finished the race.
     *
     * @return the number of cars which already finished the race
     */
    int getNumberOfFinishedCars();

    /**
     * Returns the ids of the cars which already finished the race.
     *
     * @return the ids of the cars which already finished the race
     */
    List<Integer> getFinishedCarsIds();

    /**
     * Returns the pit.
     *
     * @return the pit
     */
    Pit getPit();

}
```

**2. Създайте клас `Car`, който е нишка, и има следния вид:**

```java
package bg.sofia.uni.fmi.mjt.race.track;

public class Car implements Runnable {

    public Car(int id, int nPitStops, Track track) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }
    
    @Override
    public void run() {
         throw new UnsupportedOperationException("Method not yet implemented");
    }

    public int getCarId() {
         throw new UnsupportedOperationException("Method not yet implemented");
    }

    public int getNPitStops() {
         throw new UnsupportedOperationException("Method not yet implemented");
    }

    public Track getTrack() {
         throw new UnsupportedOperationException("Method not yet implemented");
    }

}
```

При стартирането си, колата започва да прави обиколки по пистата, докато не остане без гориво или гумите ѝ не се износят. Това отнема известно време, което можем да моделираме с паузиране на нишката - кола за случаен отрязък от 0 до 1 секунди, след което колата влиза в бокса. При създаването си, колата получава като параметър нужния брой спирания в бокса, които са ѝ необходими, за да завърши успешно състезанието.

**3. Създайте клас `Pit`, който има следния вид:**

```java
package bg.sofia.uni.fmi.mjt.race.track.pit;

import bg.sofia.uni.fmi.mjt.race.track.Car;

public class Pit {
    
    public Pit(int nPitTeams) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    public void submitCar(Car car) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    public Car getCar() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    public int getPitStopsCount() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    public List<PitTeam> getPitTeams() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    public void finishRace() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

}
```
⭐ Забележкa:
 - Използвайте подходяща структура от данни, в която да съхранявате колите, които са в бокса и чакат свободен екип, който да ги обслужи.

**4. Създайте клас `PitTeam`, който е нишка и има следния вид:**

```java
package bg.sofia.uni.fmi.mjt.race.track.pit;

public class PitTeam extends Thread {

    public PitTeam(int id, Pit pitStop) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    public int getPitStoppedCars() {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

}
```

Задачата на `PitTeam`-овете е да обслужват колите, които са в бокса:

  1. Избиране на кола от чакащите.
  2. Обслужване на колата - ще го симулираме отново с паузиране на нишката - екип за от 0 до 200 милисекунди (времето, което отнема на един екип да обслужи една кола, е индивидуално за екипите).
  3. След като колата е обслужена, трябва да се стартира нова нишка - кола, която да направи отново определен брой обиколки, преди следващата спирка в бокса или финиширане. 

:star: Забележка:

- В даден момент (докато състезанието не е приключило), може да няма коли, които чакат да бъдат обслужени. В такъв случай, отборите без работа изчакват да се появи нова кола.

**5. Създайте инстанция на `RaceTrack` и пуснете коли да се състезават**

- Отборите от бокса се създават и започват работа при неговото създаване.
- Създайте и пуснете едновременно N коли.
- Изчакайте всички коли да приключат състезанието.

:star: Локално тестване:

- Уверете се, че броят на всички финиширали коли, е равен на броя коли, започнали състезанието.

**6. Тествайте локално решението ви**

- В основната нишка, изчакайте даден брой секунди за настъпване на края на състезанието.
- След това, извикайте метода `finishRace()` на бокса.
- Всички коли, влезли в бокса преди извикването на `finishRace()`, трябва да бъдат обслужени.
- След като всеки отбор приключи работа (т.е дошъл е краят на състезанието и всички коли са обслужени и приключили състезанието), изведете на конзолата ID-то на отбора и броя на обслужените от него коли (за ваши тестови цели).
- Уверете се, че броят на спиранията в бокса на всички коли на пистата е равен на сумата от спиранията при всеки от екипите в бокса.

**7. Изполвайте насоките от горната точка за създаване на unit тестове**

## Структура на проекта

```
src
└─ bg.sofia.uni.fmi.mjt.race.track
    ├── pit
    │    ├─ Pit.java
    │    ├─ PitTeam.java
    │    └─ (...)
    ├── Car.java
    ├── RaceTrack.java
    ├── Track.java
    └── (...)
test
└─ bg.sofia.uni.fmi.mjt.race.track
    ├── RaceTrackTest.java
    └── (...)
```

В грейдъра качете общ `zip` архив на папки `src` и `test`.
