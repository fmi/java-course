# Обектно-ориентирано програмиране с Java (част I)

## Rental Service :car: :bike:

Една набираща популярност опция за придвижване в съвременните градове са споделените превозни средства. Ще създадем приложение за резервиране на автомобили, тротинетки и велосипеди.

### `RentalService`

В пакета `bg.sofia.uni.fmi.mjt.rentalservice.service` създайте клас `RentalService` с публичен конструктор `RentalService(Vehicle[] vehicles)`, който имплементира следния интерфейс:

```java
package bg.sofia.uni.fmi.mjt.rentalservice.service;

public interface RentalServiceAPI {

    /**
     * Rents an available vehicle until a certain point in time
     * 
     * @param vehicle a valid vehicle registered with the RentalService
     * @param until point in time until which the vehicle will be rented.
     *              The vehicle will be available for next booking after @until
     * @return the price of the trip (starting now).
     *         If the vehicle does not exist or is already booked, return -1.0
     */
    double rentUntil(Vehicle vehicle, LocalDateTime until);

    /**
     * Returns the nearest available vehicle of the specified type within a certain radius
     * of the given location
     * 
     * @param type the type of the vehicle
     * @param location current location
     * @param maxDistance non-negative maximum distance to the vehicle
     * @return the nearest available vehicle of the specified @type within @maxDistance, null otherwise.
     *         If there are two or more equidistant nearest vehicles, return any of them
     */
    public Vehicle findNearestAvailableVehicleInRadius(String type, Location location, double maxDistance);

}

```

### Превозни средства

Приложението поддържа три типа превозни средства - автомобили, тротинетки и велосипеди.
Те са реализирани съответно от конкретните класове `Car`, `Scooter` и `Bicycle`, имплементиращи интерфейса `Vehicle`:

```java
package bg.sofia.uni.fmi.mjt.rentalservice.vehicle;

import java.time.LocalDate;

public interface Vehicle {

    /**
     * @return the price per minute for booking this vehicle.
     *         Prices for the different types are:
     *             "CAR" -> 0.50
     *             "SCOOTER" -> 0.30
     *             "BICYCLE" -> 0.20
     */
    double getPricePerMinute();

    /**
     * @return the type of this vehicle. Valid types are: "CAR", "SCOOTER" or
     *         "BICYCLE"
     */
    String getType();

    /**
     * @return the ID of this vehicle. The ID should not be blank or null
     */
    String getId();

    /**
     * @return the location of this vehicle. The location should not be null
     */
    Location getLocation();

    /**
     * @return the end of the reservation period, in case the vehicle is currently
     *         booked, otherwise return current time
     */
    LocalDateTime getEndOfReservationPeriod();

    /**
     * 
     * @param until time until the vehicle is booked
     */
    void setEndOfReservationPeriod(LocalDateTime until);

}

```

И трите имплементации на `Vehicle` трябва да имат публичен конструктор с параметри `(String id, Location location)`.

### Локация

Ще разглеждаме града като равнина, а местоположенията ще моделираме с класа `Location`, представляващ двойка декартови координати.

```java
package bg.sofia.uni.fmi.mjt.rentalservice.location;

public class Location {

    private final double x;
    private final double y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
```

### Подсказка

:point_right: За работа с дати и часове, може да използвате [`java.time`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/package-summary.html) API, обръщайки по-специално внимание на [`LocalDateTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDateTime.html) класа и неговите методи.

### Пакети

Спазвайте имената на пакетите на всички по-горе описани класове и интерфейси, тъй като в противен случай, решението ви няма да може да бъде автоматично тествано.

```bash
src
│
└─ bg/sofia/uni/fmi/mjt/rentalservice
    ├─── location/
    │       └─ Location.java
    ├─── service/
    │       ├─ RentalService.java
    │       ├─ RentalServiceAPI.java
    │       └─ (...)
    ├─── vehicle/
    │       ├─ Bicycle.java
    │       ├─ Car.java
    │       ├─ Scooter.java
    │       ├─ Vehicle.java
    │       └─ (...)
    └─ (...)
```

### :warning: Забележки

- Използването на структури от данни, различни от масив, **не е позволено**. Задачата трябва да се реши с помощта на знанията от курса до момента и допълнителните Java APIs, указани в условието.
