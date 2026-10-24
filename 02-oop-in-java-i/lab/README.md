# Обектно-ориентирано програмиране с Java (част I)

> "Гражданин премахна с флекс поставена скоба на автомобила си"

Това гласи едно заглавие от софийските новини.  
Шофьорът твърди, че е платил със SMS. ЦГМ твърди, че не е.  
Кой е прав, кой е крив - не знаем.

Следващия път обаче спорът трябва да се реши без флекс.

Нужна е система, която помни всяко плащане.  
Пари нема, затова ще я напишете вие. 💻

<img src="https://i.imgur.com/ljGQzaI.png" alt="WheelClamp" width="300" />

## UrbanMobilityCenter

Системата има два вида потребители:
1. шофьори
2. инспектори

Всеки от тях трябва да вижда само своята част от нея.   
Затова функционалността ѝ е разделена в два интерфейса:

В пакета `bg.sofia.uni.fmi.mjt.parking` създайте клас `UrbanMobilityCenter` с публичен конструктор

```java
public UrbanMobilityCenter(BlueZone blueZone, GreenZone greenZone)
```

който имплементира и двата интерфейса:

```java
UrbanMobilityCenter umc = new UrbanMobilityCenter(blueZone, greenZone);

ParkingClientAPI client = umc;
ParkingInspectorAPI inspector = umc;
```

### ParkingClientAPI

```java
package bg.sofia.uni.fmi.mjt.parking;

import java.time.LocalDateTime;

public interface ParkingClientAPI {

    /**
     * Pays one hour of parking in the zone with the given short number,
     * starting at the given moment.
     *
     * @param smsNumber the short number, e.g. "1302"
     * @param plate     the license plate, as typed by the driver
     * @param moment    the moment the SMS is sent
     * @return true if the payment is accepted, and false if any argument is null,
     * the short number does not belong to any zone or the plate is not valid
     */
    boolean sendSms(String smsNumber, String plate, LocalDateTime moment);

}
```

### ParkingInspectorAPI

```java
package bg.sofia.uni.fmi.mjt.parking;

import bg.sofia.uni.fmi.mjt.parking.location.Location;
import bg.sofia.uni.fmi.mjt.parking.sanction.Sanction;
import bg.sofia.uni.fmi.mjt.parking.vehicle.Vehicle;

import java.time.LocalDateTime;

public interface ParkingInspectorAPI {

    /**
     * Inspects a parked vehicle at the given moment.
     *
     * @return a sanction if the location is in a zone and the parking there
     * is not paid at the given moment, or null if any argument is null,
     * the location is not in any zone, or the parking is paid
     */
    Sanction inspect(Vehicle vehicle, Location location, LocalDateTime moment);

}
```

## Локация и зони

### Location

В пакета `bg.sofia.uni.fmi.mjt.parking.location` създайте клас `Location`.  
Създавйте публичен конструктор: `Location(double x, double y)` и методи `getX()` и `getY()`.

### ParkingZone

Зоните за паркиране имплементират интерфейса:

```java
package bg.sofia.uni.fmi.mjt.parking.zone;

import bg.sofia.uni.fmi.mjt.parking.location.Location;

/**
 * A paid parking zone.
 */
public interface ParkingZone {

    /**
     * @return the name of the zone, e.g. "Lozenets"
     */
    String getName();

    /**
     * @return the short number for SMS payment of parking in the zone
     */
    String getSmsNumber();

    /**
     * Checks whether the given location is in the zone.
     *
     * @param location the location to check
     * @return true if the location is in the zone and false otherwise.
     * If the location is null, returns false.
     */
    boolean contains(Location location);

}
```

В пакета `bg.sofia.uni.fmi.mjt.parking.zone` създайте класовете `BlueZone` (синя зона) и `GreenZone` (зелена зона),  
които имплементират `ParkingZone` и имат следните публични конструктори:

```java
public BlueZone(String name, Location center, double radius)
public GreenZone(String name, Location center, double innerRadius, double outerRadius)
```

Информация за всяка от зоните:

| Зона        | Кратък номер | Условие за принадлежност              |
|-------------|--------------|---------------------------------------|
| `BlueZone`  | `1302`       | `distance <= radius`                  |
| `GreenZone` | `1303`       | `innerRadius < distance <= outerRadius` |

Където `distance` е разстоянието от точката до `center`.

![Zones](https://i.imgur.com/tN8S2lj.png)

## Регистрационни номера

Българските регистрационни номера използват само 12-те кирилски букви, които имат латински двойници.

Шофьорите пишат номера на колата си както им дойде: на кирилица или на латиница, с главни или с малки букви, с интервали или с тирета.

За системата "са 1234 вх", "CA1234BX" и "С А-1234-ВХ" са един и същи номер.

В пакета `bg.sofia.uni.fmi.mjt.parking.vehicle` създайте клас `LicensePlate`, който представя валиден регистрационен номер.

Инстанции се създават само със статичния метод:

```java
public static LicensePlate parse(String text)
```

Методът работи по следния начин:

- интервалите и тиретата в text се игнорират, а малките букви се третират като главни;
- буквите на кирилица се заменят с латинските им двойници;
- полученият низ е валиден номер, ако се състои от 1 или 2 букви, следвани от 4 цифри (0-9), следвани от 1 или 2 букви;
- ако text е `null` или не е валиден номер, методът връща `null`.

## Превозни средства

Превозните средства имплементират интерфейса:

```java
package bg.sofia.uni.fmi.mjt.parking.vehicle;

/**
 * A vehicle that can be parked in a paid zone.
 */
public interface Vehicle {

    /**
     * @return the license plate of the vehicle
     */
    LicensePlate getLicensePlate();

    /**
     * @return the amount in euro added to every sanction imposed on the vehicle
     */
    int getSurcharge();

}
```

В пакета `bg.sofia.uni.fmi.mjt.parking.vehicle` създайте класовете `Car` (лек автомобил) и `Jeep` (джип) със следните публични конструктори:

```java
public Car(LicensePlate licensePlate)
public Jeep(LicensePlate licensePlate)
```

|                 | `Car` | `Jeep` |
|:----------------|:-----:|:------:|
| Надбавка (евро) | 15    | 20     |

## Санкции

При неплатено паркиране инспекторът налага санкция.  

Санкциите имплементират интерфейса:

```java
package bg.sofia.uni.fmi.mjt.parking.sanction;

import bg.sofia.uni.fmi.mjt.parking.vehicle.Vehicle;
import bg.sofia.uni.fmi.mjt.parking.zone.ParkingZone;

/**
 * A sanction for unpaid parking.
 */
public interface Sanction {

    /**
     * @return the vehicle the sanction is imposed on
     */
    Vehicle getVehicle();

    /**
     * @return the zone in which the sanction is imposed
     */
    ParkingZone getZone();

    /**
     * @return the amount of the sanction in euro:
     * the base amount of the sanction plus the surcharge of the vehicle
     */
    int getAmount();

}
```

В пакета `bg.sofia.uni.fmi.mjt.parking.sanction` създайте класовете `TowAway` и `WheelClamp` със следните публични конструктори:

```java
public TowAway(Vehicle vehicle, ParkingZone zone)
public WheelClamp(Vehicle vehicle, ParkingZone zone)
```

|                    | `TowAway`          | `WheelClamp`  |
|:-------------------|:-------------------|:--------------|
| Какво е            | репатриране с паяк | скоба         |
| Базова сума (евро) | 30                 | 15            |
| Налага се в        | синята зона        | зелената зона |

Например паяк за джип струва 30 + 20 = 50 евро.

## Структура на пакетите

Спазвайте имената на пакетите, иначе решението няма да може да бъде тествано автоматично.  
Можете да добавяте и собствени класове.

```
src
└── bg.sofia.uni.fmi.mjt.parking
    ├── location
    │   └── Location.java
    ├── sanction
    │   ├── Sanction.java
    │   ├── TowAway.java
    │   └── WheelClamp.java
    ├── vehicle
    │   ├── Car.java
    │   ├── Jeep.java
    │   ├── LicensePlate.java
    │   └── Vehicle.java
    ├── zone
    │   ├── BlueZone.java
    │   ├── GreenZone.java
    │   └── ParkingZone.java
    ├── ParkingClientAPI.java
    ├── ParkingInspectorAPI.java
    └── UrbanMobilityCenter.java
    (...)
```

## ⚠️ Забележки

- Използването на структури от данни, различни от масив, **не е позволено**.
- Може да приемете, че аргументите на конструкторите са валидни, а синята и зелената зона имат общ център.
- Не взимайте текущото системно време (`LocalDateTime.now()`).
- Всеки SMS плаща точно един час от момента на изпращането си, независимо от предишните плащания за същия номер и зона. 
  - Например при SMS-и в 10:00 и в 10:01 паркирането е платено до 11:01.

## 💡 Подсказки

- `Car` и `Jeep` си приличат много, както и `TowAway` и `WheelClamp`. Зоните също. Как да не повтаряте един и същи код?