# Домашно 1

## Glovo 🚚

`Краен срок: 30.11.2024 23:59`

### Описание на задачата

Имаме привилегията да живеем в свят, в който храна и всякакви покупки от магазина долитат при вас с един клик. Сега си представете, че вие сте магьосниците зад тази магия! Ще разработите приложение за доставка, което не само следи къде е вашият бургер или пица в момента, но и гарантира, че те ще пристигнат топли, вкусни и навреме 🍕🥡, пренасяйки щастие директно до вратата на хората! 🚀🍟

#### Бърз поглед над задачите

1. Дадени са два интерфейса, както и няколко помощни класа. Имплементирайте интерфейсите. Не променяйте сигнатурите на дадените методи, както и всички конструктури, дадени в условието. Може да добавяте още класове, ако сметнете за необходимо.
2. Добавете **поне два** ***ваши***, т.е. не част от Java JDK, Runtime Exceptions, които да се хвърлят в някои от методите ви. Кое поведение се приема за бъг на програмата този път определяте вие.
3. Добавете необходимите валидации за валидни параметри, подадени на методите, дефинирани в интерфейса.
4. Вие избирате какви алгоритми да използвате. Ако използвате по-ефективен, бихте получили бонус точки, но с тривиален подход, стига да работи правилно, със сигурност няма да ви бъдат взети такива.

### GlovoApi

В пакета `bg.sofia.uni.fmi.mjt.glovo` създайте интерфейса `GlovoApi`. Както вече сте свикнали - какво се очаква да бъде поведението на неговите имплементатори, е описано в javadocs над всеки метод:

```java
package bg.sofia.uni.fmi.mjt.glovo;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.delivery.Delivery;
import bg.sofia.uni.fmi.mjt.glovo.exception.NoAvailableDeliveryGuyException;

public interface GlovoApi {

    /**
     * Returns the cheapest delivery option for a specified food item from a restaurant to a client location.
     *
     * @param client     The delivery destination, represented by a MapEntity.
     * @param restaurant The location of the restaurant from which the food item is sourced,
     *                   represented by a MapEntity.
     * @param foodItem   The name of the food item to be delivered.
     * @return A Delivery object representing the cheapest available delivery option within the
     * specified constraints.
     * @throws InvalidOrderException           If there is no client or restaurant at the specified location on
     *                                         the map, or if the location is outside the map's defined boundaries.
     * @throws NoAvailableDeliveryGuyException If no delivery guys are available to complete the delivery.
     */
    Delivery getCheapestDelivery(MapEntity client, MapEntity restaurant, String foodItem)
        throws NoAvailableDeliveryGuyException;

    /**
     * Returns the fastest delivery option for a specified food item from a restaurant to a
     * client location.
     *
     * @param client     The delivery destination, represented by a MapEntity.
     * @param restaurant The location of the restaurant from which the food item is sourced,
     *                   represented by a MapEntity.
     * @param foodItem   The name of the food item to be delivered.
     * @return A Delivery object representing the fastest available delivery option within the specified
     * constraints.
     * @throws InvalidOrderException           If there is no client or restaurant at the specified location on
     *                                         the map, or if the location is outside the map's defined boundaries.
     * @throws NoAvailableDeliveryGuyException If no delivery guys are available to complete the delivery.
     */
    Delivery getFastestDelivery(MapEntity client, MapEntity restaurant, String foodItem)
        throws NoAvailableDeliveryGuyException;

    /**
     * Returns the fastest delivery option under a specified price for a given food item from a restaurant
     * to a client location.
     *
     * @param client     The delivery destination, represented by a MapEntity.
     * @param restaurant The location of the restaurant from which the food item is sourced,
     *                   represented by a MapEntity.
     * @param foodItem   The name of the food item to be delivered.
     * @param maxPrice   The maximum price the client is willing to pay for the delivery.
     * @return A Delivery object representing the fastest available delivery option under the specified
     * price limit.
     * @throws InvalidOrderException           If there is no client or restaurant at the specified location
     *                                         on the map,or if the location is outside the map's defined boundaries.
     * @throws NoAvailableDeliveryGuyException If no delivery guys are available to complete the delivery.
     */
    Delivery getFastestDeliveryUnderPrice(MapEntity client, MapEntity restaurant, String foodItem, double maxPrice)
        throws NoAvailableDeliveryGuyException;

    /**
     * Returns the cheapest delivery option within a specified time limit for a given food item from a restaurant
     * to a client location.
     *
     * @param client     The delivery destination, represented by a MapEntity.
     * @param restaurant The location of the restaurant from which the food item is sourced,
     *                   represented by a MapEntity.
     * @param foodItem   The name of the food item to be delivered.
     * @param maxTime    The maximum allowable delivery time in minutes.
     * @return A Delivery object representing the cheapest available delivery option within the specified
     * time limit.
     * @throws InvalidOrderException           If there is no client or restaurant at the specified location
     *                                         on the map, or if the location is outside the map's defined boundaries.
     * @throws NoAvailableDeliveryGuyException If no delivery guys are available to complete the delivery.
     */
    Delivery getCheapestDeliveryWithinTimeLimit(MapEntity client, MapEntity restaurant, String foodItem, int maxTime)
        throws NoAvailableDeliveryGuyException;

}
```

### ControlCenterApi

В пакета `bg.sofia.uni.fmi.mjt.glovo.controlcenter` създайте интерфейса `ControlCenterApi`

```java
package bg.sofia.uni.fmi.mjt.glovo.controlcenter;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;

public interface ControlCenterApi {

    /**
     * Finds the optimal delivery person for a given delivery task. The method
     * selects the best delivery option based on the provided cost and time constraints.
     * If no valid delivery path exists, it returns null.
     *
     * @param restaurantLocation The location of the restaurant to start the delivery from.
     * @param clientLocation     The location of the client receiving the delivery.
     * @param maxPrice           The maximum price allowed for the delivery. Use -1 for no cost constraint.
     * @param maxTime            The maximum time allowed for the delivery. Use -1 for no time constraint.
     * @param shippingMethod     The method for shipping the delivery.
     * @return A DeliveryInfo object containing the optimal delivery guy, the total cost,
     * the total time, and the delivery type. Returns null if no valid path is found.
     */
    DeliveryInfo findOptimalDeliveryGuy(Location restaurantLocation, Location clientLocation,
                                        double maxPrice, int maxTime, ShippingMethod shippingMethod);

    /**
     * Returns the map
     *
     * @return A MapEntity[][] containing the map
     */
    MapEntity[][] getLayout();

}
```

### Имплементации на интерфейсите

Имплементацията на `GlovoApi` e `Glovo`. Имплементацията на `ControlCenterApi` е `ControlCenter`.
И двата имплементиращи класа имат конструктори, които приемат един и същи параметър `mapLayout`, който ще обясним по-надолу в тази секция:

```java
public ControlCenter(char[][] mapLayout) {...}
public Glovo(char[][] mapLayout) {...}
```

`mapLayout` е двумерен масив от символи, представляващ картата, с която работим в този момент. На тази карта са координатите на всички ресторанти, на всички клиенти, както и на всички доставчици. Освен това, имаме и пътищата между тях.
Всяко едно поле на картата има специален символ. Значението на символите е както следва:

* '#' = Wall
* '.' = Road
* 'R' = Restaurant
* 'C' = Client
* 'A' = DeliveryGuy in a car
* 'B' = DeliveryGuy on a Bike

Посоките за движение по картата са 4 - нагоре, надолу, наляво и надясно. Доставчикът може да преминава през всичко, различно от стена (включително и други доставчици). Приемаме, че всички доставчици са свободни в момента на поръчката, а след нея те не променят локацията си т.е. остават в стартова позиция. 

Например:

```java
char[][] layout = {
        {'#', '#', '#', '.', '#'},
        {'#', '.', 'B', 'R', '.'},
        {'.', '.', '#', '.', '#'},
        {'#', 'C', '.', 'A', '.'},
        {'#', '.', '#', '#', '#'}
};
```

### Помощни типове

Освен основните класове и интерфейси, в условието има нужда да дефинираме още няколко помощни класа/records. Имплементациите ви, естествено, не се изчерпват с този набор от типове и спокойно може да дефинирате нови. Единственото условие е да не променяте вече дадените по условие такива, техните конструктори и сигнатурите на методите им.

#### Location

`Location` е част от пакета `bg.sofia.uni.fmi.mjt.glovo.controlcenter.map`, репрезентира координатите на дадено entity на нашата карта и има следния конструктор:

```java
public Location(int x, int y) { ... }
```

#### Delivery

В пакета `bg.sofia.uni.fmi.mjt.glovo.delivery` създайте нов Java тип `Delivery`, който има следния конструктор:

```java
public Delivery(Location client, Location restaurant, Location deliveryGuy, String foodItem, double price, int estimatedTime) { ... }
```

#### DeliveryType

Видът на транспорта на доставките се моделира от enum-а `DeliveryType`, дефиниран в `bg.sofia.uni.fmi.mjt.glovo.delivery` и имащ две стойности, които се характеризират с цена на километър и време за изминаване на километър: 

| `DeliveryType` | Price per kilometer | Time per kilometer |
|----------------|---------------------|--------------------|
| CAR            | 5                   | 3                  |
| BIKE           | 3                   | 5                  |

#### Shipping method

Методът на доставка се моделира от следния enum:

```java
package bg.sofia.uni.fmi.mjt.glovo.delivery;

public enum ShippingMethod {
    FASTEST,
    CHEAPEST
}
```

#### DeliveryInfo

Последният помощен тип от пакета `bg.sofia.uni.fmi.mjt.glovo.delivery` е `DeliveryInfo` - той е релевантен за `ControlCenterAPI` интерфейса и има следния конструктор:

```java
public DeliveryInfo(Location deliveryGuyLocation, double price, int estimatedTime, DeliveryType deliveryType)
```

### Пакети

Спазвайте имената на пакетите на всички по-горе описани типове, тъй като в противен случай, решението ви няма да може да бъде автоматично тествано.

```
src
└── bg.sofia.uni.fmi.mjt.glovo
    ├── controlcenter
    │   ├── map
    │   │   ├── Location.java
    │   │   ├── MapEntity.java
    │   │   ├── MapEntityType.java
    |   │   └── (...)
    │   ├── ControlCenterApi.java
    │   ├── ControlCenter.java
    │   └── (...)
    ├── delivery
    │   ├── Delivery.java
    │   ├── DeliveryInfo.java
    │   ├── DeliveryType.java
    │   ├── ShippingMethod.java
    │   └── (...)
    ├── exception
    │   ├── InvalidOrderException.java
    │   ├── NoAvailableDeliveryGuyException.java
    │   └── (...)
    ├── Glovo.java
    ├── GlovoApi.java
    └── (...)
```

### :warning: Забележки

- Не променяйте по никакъв начин интерфейсите, дадени в условието.
- :exclamation: **Решения,
  използващи [Java Stream API](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/util/stream/package-summary.html),
  [lambdas](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html), и всичко останало, което не е
  учено до момента в курса, няма да се приемат за това домашно.**

### Предаване

За да предадете решението си, качете цялата `src` директория на проекта в съответния assignment в грейдъра
(или я архивирайте в **zip** файл и качете него).

### Оценяване

Решението може да ви донесе до 100 точки, като ще бъде оценявано за:

* функционална пълнота и коректност: чрез автоматични тестове (80% от оценката)
* добър обектно-ориентиран дизайн, спазване на правилата за чист код и подбиране на оптимални за задачата структури от
  данни (20% от оценката)

Обърнете внимание, че при качване на решението ви, в грейдъра ще се изпълни само _smoke_ тест, чиято цел е да изчистите
евентуални проблеми с компилацията. Референтите тестове и Checkstyle статичният код анализ ще се изпълнят еднократно
след изтичане на крайния срок за предаване. За функционалната коректност и качеството на кода ще трябва да се погрижите
без тяхната помощ.

### Желаем ви успех! :four_leaf_clover: 
