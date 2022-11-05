# Flight Scanner :airplane:

Ще създадем проста търсачка за самолетни полети, подобна на [Skyscanner](https://www.skyscanner.net), с помощта на която ще разбираме, как да се придвижим между две локации с минимален брой прекачвания.

### Flight Scanner

В пакета `bg.sofia.uni.fmi.mjt.flightscanner` създайте клас `FlightScanner`, който има публичен конструктор по подразбиране и имплементира интерфейса `FlightScannerAPI`:

```java
package bg.sofia.uni.fmi.mjt.flightscanner;

import bg.sofia.uni.fmi.mjt.flightscanner.airport.Airport;
import bg.sofia.uni.fmi.mjt.flightscanner.flight.Flight;

import java.util.Collection;
import java.util.List;

public interface FlightScannerAPI {

    /**
     * Adds a flight. If the same flight has already been added, call does nothing.
     *
     * @param flight the flight to add.
     * @throws IllegalArgumentException if flight is null.
     */
    void add(Flight flight);

    /**
     * Adds all flights specified. If any of the flights have already been added, those are ignored.
     *
     * @param flights the flights to be added.
     * @throws IllegalArgumentException if flights is null.
     */
    void addAll(Collection<Flight> flights);

    /**
     * Returns a list of flights from start to destination airports with minimum number of transfers.
     * If there are several such flights with equal minimum number of transfers, returns any of them.
     * If the destination is not reachable with any sequence of flights from the start airport,
     * returns an empty list.
     *
     * @param from the start airport.
     * @param to   the destination airport.
     * @throws IllegalArgumentException if from or to is null, or if from and to are one and the same airport.
     */
    List<Flight> searchFlights(Airport from, Airport to);

    /**
     * Gets an unmodifiable copy of all outbound flights from the specified airport,
     * sorted by number of free seats, in descending order.
     *
     * @param from the airport.
     * @throws IllegalArgumentException if from is null.
     */
    List<Flight> getFlightsSortedByFreeSeats(Airport from);

    /**
     * Gets an unmodifiable copy of all outbound flights from the specified airport,
     * sorted by destination airport ID, in lexicographic order.
     *
     * @param from the airport.
     * @throws IllegalArgumentException if from is null.
     */
    List<Flight> getFlightsSortedByDestination(Airport from);

}
```

### Летища

Всяко летище има уникално ID (тип `String`) и се моделира чрез `record`-a `Airport` с единствен компонент това ID, в пакета `bg.sofia.uni.fmi.mjt.flightscanner.airport`.  `Airport` трябва да има компактен конструктор, който да валидира, че  ID-то не е `null`, *empty* или *blank*. При неуспешна валидация, конструкторът трябва да хвърля `IllegalArgumentException`.

### Полети

Полетът се моделира от класа `RegularFlight` в пакета `bg.sofia.uni.fmi.mjt.flightscanner.flight` и имплементира интерфейса `Flight`:

```java
package bg.sofia.uni.fmi.mjt.flightscanner.flight;

import bg.sofia.uni.fmi.mjt.flightscanner.airport.Airport;
import bg.sofia.uni.fmi.mjt.flightscanner.exception.FlightCapacityExceededException;
import bg.sofia.uni.fmi.mjt.flightscanner.passenger.Passenger;

import java.util.Collection;

public interface Flight {

    /**
     * Gets the start airport of this flight.
     */
    Airport getFrom();

    /**
     * Gets the destination airport of this flight.
     */
    Airport getTo();

    /**
     * Adds a passenger to this flight.
     *
     * @param passenger the passenger to add.
     * @throws FlightCapacityExceededException, if the flight is already fully booked (there are no free seats).
     */
    void addPassenger(Passenger passenger) throws FlightCapacityExceededException;

    /**
     * Adds a collection of passengers to this flight.
     *
     * @param passengers the passengers to add.
     * @throws FlightCapacityExceededException, in case the flight cannot accommodate that many passengers
     * (the available free seats are not enough).
     */
    void addPassengers(Collection<Passenger> passengers) throws FlightCapacityExceededException;

    /**
     * Gets all passengers on this flight. If there are no passengers, returns an empty list.
     *
     * @return a collection of all passengers on this flight, as an unmodifiable copy.
     */
    Collection<Passenger> getAllPassengers();

    /**
     * Gets the number of free seats on this flight.
     */
    int getFreeSeatsCount();

}
```

Инстанции на `RegularFlight` не могат да се създават директно, а се получават чрез публичен статичен метод със сигнатура `of(String flightId, Airport from, Airport to, int totalCapacity)`. Методът валидира аргументите си: ако `flightId` е `null`, *empty* или *blank*, или `from` или `to` летищата са `null`, или капацитетът на полета е отрицателно число, се хвърля `IllegalArgumentException`. Ако `from` и `to` са едно и също летище, се хвърля `runtime` изключението `InvalidFlightException`.

### Пътници

Пътник се моделира чрез `record`-a `Passenger` в пакета `bg.sofia.uni.fmi.mjt.flightscanner.passenger` с компоненти `(String id, String name, Gender gender)` (евентуалната им валидация оставяме на вас), а `Gender` е следният `enum`:

```java
package bg.sofia.uni.fmi.mjt.flightscanner.passenger;

public enum Gender {
    MALE, FEMALE, OTHER
}
```

## Подсказки

:point_right: Ако си представим летищата като върхове на граф, а полетите - като дъгите в графа, задачата ни се свежда до класическата задача за търсене на най-кратък път в безтегловен граф. За щастие, тази задача има просто решение чрез т.нар. [Breadth-First Search (BFS)](https://en.wikipedia.org/wiki/Breadth-first_search) алгоритъм. Какво представлява той и как се прилага към задачата за намиране на най-кратък път, може да видите например в това [видео](https://www.youtube.com/watch?v=oDqjPvD54Ss).

:point_right: В програмирането, граф може да се представи по няколко класически начина, използвайки по-прости структури от данни. За ефективна имплементация на BFS, е подходящо представяне, позволяващо лесно да намираме всички съседи на даден връх. Може да използваме представяне тип *adjacency list*: за всеки връх (летище) може да съхраняваме списък (или множество) от изходящите от този връх дъги (които представляват изходящите полети от това летище).

### Пакети

Спазвайте имената на пакетите на всички по-горе описани типове, тъй като в противен случай, решението ви няма да може да бъде автоматично тествано.

```
src
└── bg.sofia.uni.fmi.mjt.flightscanner
    ├── airport
    │      └── Airport.java
    ├── exception
    │      ├── FlightCapacityExceededException.java
    │      └── InvalidFlightException.java
    ├── flight
    │      ├── Flight.java
    │      └── RegularFlight.java
    ├── passenger
    │      ├── Gender.java
    │      └── Passenger.java
    ├── FlightScanner.java
    └── FlightScannerAPI.java
```

### :warning: Забележки

- Не променяйте по никакъв начин интерфейсите, дадени в условието.
