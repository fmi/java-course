# Car Store :blue_car:

### Условие

Ще създадем система за управление на магазин за коли, която има следните възможности:
- търсене на кола по регистрационен номер
- търсене на коли по даден модел
- предоставяне на информация за колите в магазина, сортирани по определени критерии
- добаване на нови коли
- премахване на вече добавени коли

За целта създайте клас `CarStore`, който има следните методи:

```java
package bg.sofia.uni.fmi.mjt.carstore;

public class CarStore {
  /**
  * Adds the specified car in the store.
  * @return true if the car was added successfully to the store
  */
  public boolean add(Car car) {
    // implement ...
    return false;
  }

  /**
  * Adds all of the elements of the specified collection in the store.
  * @return true if the store cars are changed after the execution (i.e. at least one new car is added to the store)
  */
  public boolean addAll(Collection<Car> cars) {
    // implement ...
    return false;
  }

  /**
  * Removes the specified car from the store.
  * @return true if the car is successfully removed from the store
  */
  public boolean remove(Car car) {
    // implement ...
    return false;
  }

  /**
  * Returns all cars of a given model.
  * The cars need to be sorted by year of manufacture (in ascending order).
  */
  public Collection<Car> getCarsByModel(Model model) {
    // implement ...
    return null;
  }

  /**
  * Finds a car from the store by its registration number.
  * @throws CarNotFoundException if a car with this registration number is not found in the store
  **/
  public Car getCarByRegistrationNumber(String registrationNumber) {
    // implement ...
    return null;
  }

   /**
   * Returns all cars sorted by their default order*
   **/
  public Collection<Car> getCars() {
    // implement ...
    return null;
  }
  /**
  * Returns all cars sorted according to the order induced by the specified comparator.
  */
  public Collection<Car> getCars(Comparator<Car> comparator) {
    // implement ...
    return null;
  }

  /**
  * Returns all cars sorted according to the given comparator and boolean flag for order.
  * @param isReversed if true the cars should be returned in reversed order
  */
  public Collection<Car> getCars(Comparator<Car> comparator, boolean isReversed) {
    // implement
    return null;
  }

  /**
  * Returns the total number of cars in the store.
  */
  public int getNumberOfCars() {
    // implement ...
    return 0;
  }

  /**
  * Returns the total price of all cars in the store.
  */
  public int getTotalPriceForCars() {
    // implement ...
    return 0;
  }
}

```

***Note***: Default order - подредени първо по модел (по азбучен ред) и след това по година на производство (в нарастващ ред)

Създайте абстрактен клас или интерфейс `Car` със следните методи:

```java
package bg.sofia.uni.fmi.mjt.carstore.car;

public <type> Car {

  /**
  * Returns the model of the car.
  */
  public Model getModel();

  /**
  * Returns the year of manufacture of the car.
  */
  public int getYear();

  /**
  * Returns the price of the car.
  */
  public int getPrice();

  /**
  * Returns the engine type of the car.
  */
  public EngineType getEngineType();

  /**
  * Returns the unique registration number of the car.
  */
  public String getRegistrationNumber();

}
```

Конкретните имплементации на `Car` са `OrdinaryCar` и `SportsCar` и трябва да имат следните конструктори:

```java
public OrdinaryCar(Model model, int year, int price, EngineType engineType, Region region);

public SportsCar(Model model, int year, int price, EngineType engineType, Region region);
```

и да се намират в пакет `bg.sofia.uni.fmi.mjt.carstore.car`

- `Model` може да приема следните стойности: **ALFA_ROMEO**, **AUDI**, **BMW**, **MERCEDES**, **FERRARI**, **OPEL**
- `EngineType` може да приема следните стойности: **DIESEL**, **GASOLINE**, **ELECTRIC**, **HYBRID**
- `Region` може да приема следните стойности: **SOFIA**, **BURGAS**, **VARNA**, **PLOVDIV**, **RUSE**, **GABROVO**, **VIDIN**, **VRATSA**
- Всяка кола се идентифицира уникално чрез регистрационния си номер. Той се образува по следния начин:
``{region}{number}{random alphabet char}{random alphabet char}``, където **{number}** е цяло число, започващо от 1000 (за всяка една област, т.е ако създадем две коли от различни области {number} частта им от регистрационния номер ще бъде 1000 и за двете) и инкрементиращо се при всяка новосъздадена кола, **{random alphabet char}** e произволна буква от латинската азбука, а **{region}** е един от следните областни кодове (буквите на кодовете са от латинската азбука):
  - Бургас -> A
  - София -> CB
  - Варна -> B
  - Видин -> BH
  - Враца -> BP
  - Габрово -> EB
  - Пловдив -> PB
  - Русе -> P
  **Например**: CB1111AA, P1234JA

`Region` трябва да има метод `getPrefix()`, който връща областния код.

---

Проектът ви трябва да има следната структура:
```
src
└─ bg/sofia/uni/fmi/mjt/carstore/
   └─ CarStore.java
   └─(...)
   └─ car/
      └─ Car.java
      └─ OrdinaryCar.java
      └─ SportsCar.java
      └─(...)
   └─ enums/
     └─ (...)
   └─ exception/
      └─ CarNotFoundException.java
      └─ (...)
```
В [sapera.org](http://grader.sapera.org) качвайте .zip архив на `src` директорията.
