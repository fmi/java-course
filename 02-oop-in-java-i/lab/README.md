# Обектно-ориентирано програмиране с Java (част I)

## AirBnB :hotel:

В тази част на годината, всеки се замисля, къде ще празнува Студентския празник и Нова година. Да намериш яко място за настаняване на прилична цена си е предизвикателство - как да намерим нещо в Банско или близката околност?
Ще имплементираме платформа, в която ще можем да търсим и резервираме места за настаняване, подобна на [AirBnB](https://www.airbnb.com/).

### `Airbnb`

В пакета `bg.sofia.uni.fmi.mjt.airbnb` създайте клас `Airbnb` с публичен конструктор `Airbnb(Bookable[] accommodations)`, който имплементира следния интерфейс:

```java
package bg.sofia.uni.fmi.mjt.airbnb;

public interface AirbnbAPI {

    /**
     * Finds an accommodation by ID.
     *
     * @param id the unique ID of the bookable
     *
     * @return the bookable with the specified id (the IDs are case-insensitive).
     * If the id is null or blank, or no accommodation with the specified id is found, return null.
     */
    Bookable findAccommodationById(String id);

    /**
     * Estimates the total revenue of all booked accommodations.
     *
     * @return the sum of total prices of stay for all booked accommodations.
     */
    double estimateTotalRevenue();

    /**
     * @return the number of booked accommodations.
     */
    long countBookings();

    /**
     * Filters the accommodations by given criteria
     *
     * @param criteria the criteria to apply
     *
     * @return an array of accommodations matching the specified criteria
     */
    Bookable[] filterAccommodations(Criterion... criteria);

}
```

### Места за настаняване

Платформата поддържа три типа места за настаняване - хотели, апартаменти и вили.
Те са реализирани съответно от конкретните класове `Hotel`, `Apartment` и `Villa`, имплементиращи интерфейса `Bookable`:

```java
package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

public interface Bookable {

    /**
     * @return the unique ID of the Bookable.
     * It is String with prefix the first three letters (all-caps) of the accommodation type,
     * a dash and a sequential number counting the number of created accommodation instances of the respective type:
     * e.g. "HOT-45", "APA-12", "VIL-7". Note that counting starts from 0
     * and is done separately for the different types.
     */
    String getId();

    /**
     * @return the location of the Bookable.
     */
    Location getLocation();

    /**
     * Checks if the accommodation is booked.
     *
     * @return true, if the accommodation is booked.
     */
    boolean isBooked();

    /**
     * Books the accommodation for the specified date interval.
     *
     * @param checkIn  check-in date
     * @param checkOut check-out date
     * @return true, if the accommodation is booked successfully.
     * If the accommodation has been previously booked, does nothing and returns false.
     * If checkIn or checkOut is null, or checkIn is in the past,
     * or checkIn is not strictly before checkOut, returns false
     */
    boolean book(LocalDateTime checkIn, LocalDateTime checkOut);

    /**
     * @return If the accommodation is booked, returns the total price of the stay: the number of nights
     * of the booking, multiplied by the price per night. If the accommodation is not booked, returns 0.0.
     */
    double getTotalPriceOfStay();

    /**
     * @return The price per night for this accommodation.
     */
    double getPricePerNight();

}
```

И трите имплементации на `Bookable` трябва да имат публичен конструктор с параметри `(Location location, double pricePerNight)`.

### Локация

Ще разглеждаме населените места като равнина, а местоположенията ще моделираме с класа `Location` с публичен конструктор с параметри `(double x, double y)`, представляващ двойка декартови координати.

### Критерии за филтриране

Можем да филтрираме местата за настаняване по два критерия: локация и цена, които са реализирани съответно от конкретните класове `LocationCriterion` и `PriceCriterion`, имплементиращи интерфейса `Criterion`:

```java
package bg.sofia.uni.fmi.mjt.airbnb.filter;

public interface Criterion {

    /**
     * @return true, if the bookable matches the criterion. If bookable is null, returns false.
     */
    boolean check(Bookable bookable);

}
```

`LocationCriterion` трябва да има публичен конструктор `(Location currentLocation, double maxDistance)`, a `PriceCriterion` трябва да има публичен конструктор `(double minPrice, double maxPrice)`.

### Подсказка

:point_right: За работа с дати и часове, може да използвате [`java.time`](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/time/package-summary.html) API, обръщайки по-специално внимание на [`LocalDateTime`](https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/time/LocalDateTime.html) класа и неговите методи.

### Пакети

Спазвайте имената на пакетите на всички по-горе описани класове и интерфейси, тъй като в противен случай, решението ви няма да може да бъде автоматично тествано.

```bash
src
└─ bg.sofia.uni.fmi.mjt.airbnb
    ├─── accommodation
    │       ├─── location
    │       │       └─ Location.java
    │       ├─ Apartment.java
    │       ├─ Bookable.java
    │       ├─ Hotel.java
    │       ├─ Villa.java
    │       └─ (...)
    ├─── filter
    │       ├─ Criterion.java
    │       ├─ LocationCriterion.java
    │       ├─ PriceCriterion.java
    │       └─ (...)
    ├─ Airbnb.java
    ├─ AirbnbAPI.java
    └─ (...)
```

### :warning: Забележки

- Използването на структури от данни, различни от масив, **не е позволено**. Задачата трябва да се реши с помощта на знанията от курса до момента и допълнителните Java APIs, указани в условието.
