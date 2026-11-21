# Unit Testing and Mocking

Освен писане на code from scratch, в практиката много често се налага и да поддържаме, fix-ваме или пишем тестове за вече съществуващ код.

Целта на упражнението днес е да създадете и изпълните JUnit тестове спрямо налична имплементация.

---

# Car Wash Station 🚗🫧

С днешното упражнение ще се запознаете с едно приложение за автоматизирано управление на автомивка. Кодът все още не е ревюиран, за да се пусне. Вашият мениджър иска от вас да помогнете с ревюирането и тестването на приложението, защото неговият Go-Live наближава. 

**Имплементацията може да бъде намерена в директорията [resources](./resources).**

В имплементацията се крият бъгове. Ще трябва да ги откриете и отстраните в процеса на тестване. Първо напишете тест за даден сценарий, след това поправете проблема, който сте открили с него.

Също така, в кода има известни отстъпления от Clean Code принципите, които също ще трябва да коригирате.

## Основни класове и тяхната функционалност

### CarWashStation

Централният клас за управление на автомивката е `CarWashStation`. Той има кредитен баланс и история на приетите заявки. Всички парични суми са изразени в центове.
```java
/**
 * A prepaid car wash station with a credit balance and a history of its accepted requests.
 * All monetary amounts are expressed in cents.
 */
public class CarWashStation {

    /**
     * Creates a station with the given credit, controller, and an empty history.
     * Creates its state without contacting the controller.
     *
     * @throws IllegalArgumentException if initialCreditCents is negative or controller is null
     */
    public CarWashStation(int initialCreditCents, WashController controller);

    /**
     * Adds the amount to the balance without changing history or contacting the controller.
     * Invalid additions leave the balance unchanged.
     *
     * @throws IllegalArgumentException if amountCents is not positive
     */
    public void addCredit(int amountCents);

    /**
     * Requests a wash for the vehicle using the plan supplied by the selected package.
     * A supported request with sufficient credit is submitted to the controller exactly once.
     * Unsupported vehicles and insufficient credit do not result in a controller submission.
     * Only an accepted request is charged at its plan's price and added to this station's history.
     * Unsuccessful requests leave the balance and history unchanged.
     *
     * @return true if accepted; false if the vehicle is unsupported, credit is insufficient,
     *         or the controller refuses the request
     * @throws IllegalArgumentException if vehicle or washPackage is null;
     * @throws WashUnavailableException the original exception if submission fails
     */
    public boolean requestWash(Vehicle vehicle, WashPackage washPackage);

    /**
     * @return the current credit in cents
     */
    public int getCreditCents();

    /**
     * Returns an unmodifiable snapshot of the requests submitted by this station and accepted,
     * in submission order. Each accepted submission is a separate history entry.
     */
    public List<WashRequest> getAcceptedWashes();

}
```

### WashPackage

Този клас репрезентира план за измиване на превозно средство. Всяка имплементация на интерфейса `WashPackage` трябва да предоставя уникален план за измиване на превозното средство, като се вземат предвид типа на превозното средство и стъпките, които трябва да се извършат по време на измиването.

```java
/**
 * A wash package that supplies a plan for a vehicle.
 */
public interface WashPackage {

    /**
     * Creates a unique wash plan for the given vehicle.
     *
     * @return a non-null, immutable wash plan
     * @throws IllegalArgumentException if vehicle is null
     * @throws UnsupportedVehicleException if this package does not support the vehicle
     */
    WashPlan createPlan(Vehicle vehicle);

}
```

Предоставени са три имплементации, всяка с публичен конструктор без параметри:

| Клас | Поддържа | Име на пакета | Цена в центове | Стъпки, в този ред |
|---|---|---|---:|---|
| `BasicWashPackage` | `CAR` | `"Basic"` | 600 | `SHAMPOO`, `RINSE` |
| `PremiumWashPackage` | `CAR` | `"Premium"` | 1000 | `PREWASH`, `SHAMPOO`, `RINSE`, `WAX`, `DRY` |
| `VanWashPackage` | `VAN` | `"Van"` | 1500 | `PREWASH`, `SHAMPOO`, `RINSE`, `DRY` |

### WashController

С този клас се осъществява комуникацията с автомивката. Той приема или отказва заявки за измиване на превозни средства. Контролерът не управлява кредитите или историята на станцията.

```java
/**
 * A controller that accepts or refuses wash requests.
 */
public interface WashController {

    /**
     * Submits one request. Refused and failed submissions leave business state unchanged.
     * The controller does not manage the station's credit or history.
     *
     * @return true if accepted, false if refused
     * @throws IllegalArgumentException if request is null
     * @throws WashUnavailableException if the request could not be accepted
     */
    boolean submit(WashRequest request);

}
```

### InMemoryWashController

С тази имплементация на `WashController` ще симулираме работата на автомивката. Тя управлява наличностите от вода, пяна и вакса в паметта. Всяка стъпка на измиване консумира определено количество от тези ресурси. Приети заявки консумират общите изисквания на плана и се записват отделно. Отказаните и неуспешните заявки не променят наличностите и историята.

```java
/**
 * An in-memory controller that manages water, shampoo, and wax supplies.
 * Each occurrence of a wash step consumes its prescribed amounts.
 * Accepted submissions consume the plan's total requirements and are recorded separately.
 * Refused and failed submissions leave supplies and history unchanged.
 */
public class InMemoryWashController implements WashController {

    /**
     * Creates an operational controller with the given supplies and an empty history.
     *
     * @throws IllegalArgumentException if initialSupplies is null
     */
    public InMemoryWashController(WashSupplies initialSupplies);

    /**
     * @return true if the controller is operational and supplies cover the entire plan, false if supplies are insufficient
     * @throws IllegalArgumentException if request is null; argument validation precedes state checks
     * @throws WashUnavailableException if the controller is not operational
     */
    public boolean submit(WashRequest request);

    /**
     * Adds the supplied amounts without changing operational status or history.
     *
     * @throws IllegalArgumentException if additionalSupplies is null
     */
    public void refill(WashSupplies additionalSupplies);

    /**
     * Changes only the operational status.
     */
    public void setOperational(boolean operational);

    /**
     * @return an immutable snapshot of the current supplies
     */
    public WashSupplies getSupplies();

    /**
     * @return an unmodifiable snapshot of all requests accepted by this controller, in submission order
     */
    public List<WashRequest> getAcceptedWashes();

}
```

### WashStep

```java
public enum WashStep {
    PREWASH, SHAMPOO, RINSE, WAX, DRY
}
```

Всяка стъпка има фиксиран разход, достъпен чрез `getWaterLitres()`, `getShampooMl()` и `getWaxMl()`:

| Стъпка | Вода, литри | Пяна, милилитри | Вакса, милилитри |
|---|---:|----------------:|-----------------:|
| `PREWASH` | 10 |               0 |                0 |
| `SHAMPOO` | 5 |              20 |                0 |
| `RINSE` | 15 |               0 |                0 |
| `WAX` | 0 |               0 |               10 |
| `DRY` | 0 |               0 |                0 |

### Данни

```java
public enum VehicleType {
    CAR, VAN
}
```

```java
/**
 * A vehicle whose registration number is stored unchanged.
 * No registration-format validation is required.
 *
 * @throws IllegalArgumentException if registrationNumber is null or blank, or type is null
 */
public record Vehicle(String registrationNumber, VehicleType type) { // ... }
```

```java
/**
 * An immutable wash plan with an ordered, unmodifiable list of steps. Steps may repeat.
 * Changes to the constructor's input list must not change the plan.
 *
 * @throws IllegalArgumentException if packageName is null or blank, priceCents is not positive,
 *         or steps is null, empty, or contains null
 */
public record WashPlan(String packageName, int priceCents, List<WashStep> steps) { // ... }
```

```java
/**
 * A request retaining the supplied immutable vehicle and plan.
 *
 * @throws IllegalArgumentException if vehicle or plan is null
 */
public record WashRequest(Vehicle vehicle, WashPlan plan) { // ... }
```

```java
/**
 * Immutable supply quantities. Zero amounts are permitted.
 *
 * @throws IllegalArgumentException if any amount is negative
 */
public record WashSupplies(int waterLitres, int shampooMl, int waxMl) { // ... }
```

### Изключения

- `UnsupportedVehicleException` — избраният пакет не поддържа превозното средство;
- `WashUnavailableException` — контролерът не може да приеме заявката.

## Пакети

Спазвайте имената на пакетите, класовете и публичните сигнатури, иначе решението ви няма да може да бъде тествано от грейдъра.

```text
src
└── bg/sofia/uni/fmi/mjt/carwash
    ├── CarWashStation.java
    ├── WashRequest.java
    ├── vehicle
    │   ├── Vehicle.java
    │   └── VehicleType.java
    ├── plan
    │   ├── WashPlan.java
    │   └── WashStep.java
    ├── washpackage
    │   ├── WashPackage.java
    │   ├── BasicWashPackage.java
    │   ├── PremiumWashPackage.java
    │   └── VanWashPackage.java
    ├── controller
    │   ├── WashController.java
    │   ├── InMemoryWashController.java
    │   └── WashSupplies.java
    └── exception
        ├── UnsupportedVehicleException.java
        └── WashUnavailableException.java

test
└── bg/sofia/uni/fmi/mjt/carwash
    └── (...)
```

## Забележки

- В грейдъра качете общ `.zip` архив на директориите `src` и `test`. Тестовите класове трябва да завършват на `Test`.
- Не включвайте JAR файловете на JUnit и Mockito. Библиотеките са налични в грейдъра.
- Не променяйте интерфейсите и публичните сигнатури. Можете да добавяте помощни класове и методи.

Успех и не се притеснявайте да задавате въпроси! ⭐
