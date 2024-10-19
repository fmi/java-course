# Обектно-ориентирано програмиране с Java (част I)

## GameStore :video_game: :shopping_cart:

Наближава "Черният петък" и вашата задача е да създадете онлайн платформа - магазин за игри в стил **Steam** или **Epic
Games**.

Магазинът трябва да позволява търсене по филтри, прилагане на промоционални кодове (които драстично намаляват цените по
време на "сделката на века") и оценяване на игри и продукти.

## `StoreItem`

Сред предлаганите артикули ще има игри, допълнително съдържание ([DLC](https://en.wikipedia.org/wiki/Downloadable_content)) и различни специални пакети за заклетите геймъри.
Всички артикули имплементират интерфейса `StoreItem`, който изглежда по следния начин:

```java
package bg.sofia.uni.fmi.mjt.gameplatform.store.item;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface StoreItem {

    /**
     * Returns the title of the store item.
     *
     * @return the title of the store item
     */
    String getTitle();

    /**
     * Returns the price of the store item.
     *
     * @return the price of the store item
     */
    BigDecimal getPrice();

    /**
     * Returns the average rating of the store item.
     *
     * @return the average rating of the store item
     */
    double getRating();

    /**
     * Returns the release date of the store item.
     *
     * @return the release date of the store item
     */
    LocalDateTime getReleaseDate();

    /**
     * Sets the title of the store item.
     *
     * @param title the title of the store item
     */
    void setTitle(String title);

    /**
     * Sets the price of the store item.
     *
     * @param price the price of the store item
     */
    void setPrice(BigDecimal price);

    /**
     * Sets the release date of the store item.
     *
     * @param releaseDate the release date of the store item
     */
    void setReleaseDate(LocalDateTime releaseDate);

    /**
     * Rates the store item.
     *
     * @param rating the rating to be given for the store item
     */
    void rate(double rating);

}
```

Очакваме от вас да създадете следните артикули в пакета `bg.sofia.uni.fmi.mjt.gameplatform.store.item.category:

```java
public Game(String title, BigDecimal price, LocalDateTime releaseDate, String genre)
```
```java
public DLC(String title, BigDecimal price, LocalDateTime releaseDate, Game game)
```
```java
public GameBundle(String title, BigDecimal price, LocalDateTime releaseDate, Game[] games) 
```

## `ItemFilter`

Потребителите ще могат да използват различни критерии за филтриране като цена, рейтинг или дата на излизане, за да
открият търсените артикули. Филтрите се реализират чрез интерфейса `ItemFilter`:

```java
package bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

/**
 * A filter used to search for store items.
 */
public interface ItemFilter {

    /**
     * Checks if the given store item matches the filter.
     *
     * @param item the store item to be checked
     * @return true if the store item matches the filter, false otherwise
     */
    boolean matches(StoreItem item);

}
```

Очакваме от вас да създадете следните филтри в пакета `bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter`:

1. Филтърът за определен ценови диапазон. Той приема две граници:
    - `lowerBound`: минималната цена, която артикулът трябва да има.
    - `upperBound`: максималната цена, която артикулът не трябва да надвишава.

```java
public PriceItemFilter(BigDecimal lowerBound, BigDecimal upperBound)
```

2. Филтърът за определен рейтинг.

Приема едно число, което задава минималния рейтинг, който артикулът трябва да има, за да бъде включен в резултатите:

```java
public RatingItemFilter(double rating)
```

3. Филтърът по дата на издаване. Той приема две граници:
    - `lowerBound`: минималната дата на издаване, която артикулът трябва да има.
    - `upperBound`: максималната дата на издаване, която артикулът не трябва да надвишава.

```java
public ReleaseDateItemFilter(LocalDateTime lowerBound, LocalDateTime upperBound)
```

4. Филтърът по определено заглавие. Приема следните параметри:
    - `title`: част или цялото име на заглавието, което се търси.
    - `caseSensitive`: флаг, който указва дали търсенето на заглавието трябва да бъде чувствително към главни и малки
      букви.

```java
public TitleItemFilter(String title, boolean caseSensitive)
```

## `StoreAPI`

В пакета `bg.sofia.uni.fmi.mjt.gameplatform.store` създайте клас `GameStore` с **публичен** конструктор: `public GameStore(StoreItem[] availableItems)`, който имплементира следния интерфейс:

```java
package bg.sofia.uni.fmi.mjt.gameplatform.store;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.ItemFilter;

public interface StoreAPI {

    /**
     * Finds all store items that match the given filters.
     *
     * @param itemFilters the filters to be applied
     * @return an array of store items that match all filters or an empty array if no such items are found
     */
    StoreItem[] findItemByFilters(ItemFilter[] itemFilters);

    /**
     * Applies a promo code discount to all store items.
     * If the promo code is not valid, no discount is applied.
     *
     * @param promoCode the promo code to be applied
     */
    void applyDiscount(String promoCode);

    /**
     * Rates a store item.
     *
     * @param item the item to be rated
     * @param rating the rating to be given in the range [1, 5]
     * @return true if the item is successfully rated, false otherwise
     */
    boolean rateItem(StoreItem item, int rating);

}
```

### Подсказка

👉 За работа с дати и часове, може да използвате [`java.time`](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/time/package-summary.html) API, обръщайки по-специално внимание на [`LocalDateTime`](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/time/LocalDateTime.html) класа и неговите методи.

👉 Цените представяме с референтния тип [`java.math.BigDecimal`](https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/math/BigDecimal.html), разгледайте и неговата документация.

### :warning: Промокод

Приемаме за валидни следните промокодове:
- `VAN40` - 40% отстъпка от всички артикули в магазина
- `100YO` - легендарен промокод със 100% отстъпка на артикулите от магазина (само за приятели)

### :warning: Цена

Цената на артикулите очакваме да бъде във формат (X.XX)
- точно с две цифри след десетичната запетая 

## Структура на пакетите

Спазвайте имената на пакетите на всички по-горе описани класове, интерфейси и конструктори, тъй като в противен случай решението ви
няма да може да бъде автоматично тествано.

```
src
└─ bg.sofia.uni.fmi.mjt.gameplatform
    └──── store
           ├──── item
           │      ├──── category
           │      │      ├─ Game.java
           │      │      ├─ DLC.java
           │      │      └─ GameBundle.java
           │      │
           │      ├──── filter
           │      │      ├─ PriceItemFilter.java
           │      │      ├─ RatingItemFilter.java
           │      │      ├─ ReleaseDateItemFilter.java
           │      │      ├─ TitleItemFilter.java
           │      │      └─ ItemFilter.java
           │      │
           │      └──── StoreItem.java
           │
           ├──── GameStore.java
           └──── StoreAPI.java
```

Допълнително може да добавите собствени класове и интерфейси в съответните пакети, ако смятате, че е необходимо.

## :warning: Забележки

> Използването на структури от данни, различни от масив, **не е позволено**. Задачата трябва да се реши с помощта на
> знанията от курса до момента и допълнителните Java APIs, указани в условието.
