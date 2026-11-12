# Laundry Room | Пералнята в блока 🧺

Студентски град, блок 60. Пералнята на партера е една, а опашката от кошове пред нея - безкрайна. Едно пускане струва един жетон, а жетоните, както и всичко останало в края на месеца, не стигат.

Миналата седмица някой пусна бабиния пуловер на Мария на 90 градуса. Сега пуловерът става само за котката, а Мария не говори с никого от етажа.

Домакинът събра етажа и обяви: от днес прането се записва. Системата ще знае кой е на опашката и кой колко жетона е похарчил. А пуловер на 90 градуса просто няма да може да бъде пуснат.

Остава някой да я напише.

## Дрехи

В пакета `bg.sofia.uni.fmi.mjt.laundry.garment` създайте абстрактния клас `Garment`, който е основа на всички дрехи:

```java
package bg.sofia.uni.fmi.mjt.laundry.garment;

public abstract class Garment {

    /**
     * @param brand    the brand of the garment
     * @param weightKg the weight of the garment in kilograms
     * @throws IllegalArgumentException if the brand is null or blank, or the weight is not positive
     */
    public Garment(String brand, double weightKg);

    /**
     * @return the brand of the garment
     */
    public String getBrand();

    /**
     * @return the weight of the garment in kilograms
     */
    public double getWeightKg();

    /**
     * @return true if the garment has been washed, false otherwise. A newly created garment is not clean.
     */
    public boolean isClean();

    /**
     * Marks the garment as clean. Called by the wash programs.
     */
    public void markClean();

}
```

Материята на дрехата определя на кои програми може да бъде прана. В същия пакет създайте следната йерархия:

| Материя (абстрактен клас) | Дрехи             |
|:--------------------------|:------------------|
| `Cotton` (памук)          | `TShirt`, `Towel` |
| `Wool` (вълна)            | `Sweater`         |

Материите наследяват `Garment`, а дрехите - съответната материя. Всички имат публичен конструктор `(String brand, double weightKg)` със същото условие като в `Garment`.

## Програми

Програмите на пералнята имплементират generic интерфейса `WashProgram` в пакета `bg.sofia.uni.fmi.mjt.laundry.program`. Типовият параметър показва какви дрехи програмата може да пере безопасно:

```java
package bg.sofia.uni.fmi.mjt.laundry.program;

import bg.sofia.uni.fmi.mjt.laundry.garment.Garment;

public interface WashProgram<G extends Garment> {

    /**
     * @return the temperature of the program in degrees Celsius
     */
    int getTemperature();

    /**
     * Washes a single garment. After washing, the garment is clean.
     *
     * @param garment the garment to wash
     * @throws IllegalArgumentException if the garment is null
     */
    void wash(G garment);

}
```

В същия пакет създайте следните програми, всяка с публичен конструктор по подразбиране:

| Клас            | Пере               | `getTemperature()` |
|:----------------|:-------------------|:------------------:|
| `GentleProgram` | `Garment` (всичко) | 30                 |
| `WoolProgram`   | `Wool`             | 40                 |
| `CottonProgram` | `Cotton`           | 90                 |

Например `CottonProgram` имплементира `WashProgram<Cotton>`, а `GentleProgram` - `WashProgram<Garment>`, защото деликатната програма не вреди на нищо.

## Кош за пране

Дрехите се носят до пералнята в кош, в който се трупат на стек (LIFO - последно добавената дреха е най-отгоре). Всеки кош има собственик и капацитет в килограми и пази дрехи от определен тип: в `LaundryBasket<Sweater>` има само пуловери, в `LaundryBasket<Cotton>` - всякакви памучни дрехи, а в `LaundryBasket<Garment>` - каквото и да е. Един собственик може да има няколко коша. Всеки от тях се записва за пране поотделно.

Съквартирантите често изсипват прането си в общ кош, за да платят един жетон вместо два. Внимавайте обаче: типът на общия кош определя на какви програми може да бъде пуснат.

В пакета `bg.sofia.uni.fmi.mjt.laundry.basket` създайте класа `LaundryBasket`:

```java
package bg.sofia.uni.fmi.mjt.laundry.basket;

import bg.sofia.uni.fmi.mjt.laundry.exception.BasketOverloadedException;
import bg.sofia.uni.fmi.mjt.laundry.exception.EmptyBasketException;
import bg.sofia.uni.fmi.mjt.laundry.garment.Garment;

import java.util.Collection;
import java.util.Iterator;

public class LaundryBasket<G extends Garment> implements Iterable<G> {

    /**
     * @param owner      the owner of the basket
     * @param capacityKg the maximum total weight of the garments in the basket, in kilograms
     * @throws IllegalArgumentException if the owner is null or blank, or the capacity is not positive
     */
    public LaundryBasket(String owner, double capacityKg);

    /**
     * @return the owner of the basket
     */
    public String getOwner();

    /**
     * @return the capacity of the basket in kilograms
     */
    public double getCapacityKg();

    /**
     * @return the total weight of the garments currently in the basket, in kilograms
     */
    public double getWeightKg();

    /**
     * @return the number of garments currently in the basket
     */
    public int size();

    /**
     * Puts a garment on top of the basket.
     *
     * @param garment the garment to put in
     * @throws IllegalArgumentException  if the garment is null
     * @throws BasketOverloadedException if the total weight would exceed the capacity of the basket
     */
    public void add(G garment);

    /**
     * Puts all given garments on top of the basket in the iteration order of the collection.
     * Either all garments are put in, or none of them.
     *
     * @param garments the garments to put in
     * @throws IllegalArgumentException  if the collection is null or contains null
     * @throws BasketOverloadedException if the total weight would exceed the capacity of the basket
     */
    public void addAll(Collection<? extends G> garments);

    /**
     * Moves all garments from this basket on top of the other one, preserving their stack order.
     * Either all garments are moved, or none of them.
     *
     * @param other the basket to move the garments to
     * @return the number of moved garments
     * @throws IllegalArgumentException  if other is null or is the same object as this basket
     * @throws BasketOverloadedException if the total weight would exceed the capacity of the other basket
     */
    public int moveAllTo(LaundryBasket<? super G> other);

    /**
     * Takes out and returns the garment on top of the basket.
     *
     * @return the garment on top of the basket
     * @throws EmptyBasketException if the basket is empty
     */
    public G takeTop();

    /**
     * @return an iterator over the garments in the basket in LIFO order (from top to bottom)
     */
    @Override
    public Iterator<G> iterator();

}
```

## Записване за пране

Кош, записан за пране с дадена програма, се моделира от следния generic record в пакета `bg.sofia.uni.fmi.mjt.laundry`:

```java
package bg.sofia.uni.fmi.mjt.laundry;

import bg.sofia.uni.fmi.mjt.laundry.basket.LaundryBasket;
import bg.sofia.uni.fmi.mjt.laundry.garment.Garment;
import bg.sofia.uni.fmi.mjt.laundry.program.WashProgram;

/**
 * A basket registered for washing, together with the program it will be washed with.
 */
public record WashJob<G extends Garment>(LaundryBasket<G> basket, WashProgram<? super G> program) {

    /**
     * @throws IllegalArgumentException if the basket or the program is null
     */
    public WashJob {
        // ...
    }

    /**
     * Washes every garment currently in the basket with the program. The garments remain in the basket.
     */
    public void wash() {
        // ...
    }

}
```

## LaundryRoomAPI

Основната функционалност на системата е описана от интерфейса `LaundryRoomAPI`:

```java
package bg.sofia.uni.fmi.mjt.laundry;

import bg.sofia.uni.fmi.mjt.laundry.basket.LaundryBasket;
import bg.sofia.uni.fmi.mjt.laundry.exception.AlreadyQueuedException;
import bg.sofia.uni.fmi.mjt.laundry.exception.EmptyQueueException;
import bg.sofia.uni.fmi.mjt.laundry.garment.Garment;
import bg.sofia.uni.fmi.mjt.laundry.program.WashProgram;

import java.util.List;

public interface LaundryRoomAPI {

    /**
     * Puts the basket at the back of the queue to be washed with the given program.
     *
     * @param basket  the basket to wash
     * @param program the program to wash it with
     * @return the wash job that was put in the queue
     * @throws IllegalArgumentException if the basket or the program is null, or the basket is empty
     * @throws AlreadyQueuedException   if the same basket object is already waiting in the queue
     */
    <G extends Garment> WashJob<G> scheduleWash(LaundryBasket<G> basket, WashProgram<? super G> program);

    /**
     * Cancels the scheduled wash for the given basket and removes it from the queue.
     *
     * @param basket the basket whose wash to cancel
     * @return true if the given basket object was waiting in the queue and was removed, false otherwise
     * @throws IllegalArgumentException if the basket is null
     */
    boolean cancelWash(LaundryBasket<?> basket);

    /**
     * Runs the washing machine with the wash job at the front of the queue. Every run costs one token,
     * paid by the owner of the basket.
     *
     * @return the completed wash job
     * @throws EmptyQueueException if the queue is empty
     */
    WashJob<?> runNext();

    /**
     * @return an unmodifiable copy of the wash jobs waiting in the queue, in the order in which they will be washed
     */
    List<WashJob<?>> getQueue();

    /**
     * @param owner the person whose tokens are counted
     * @return the number of tokens the owner has paid so far; 0 if the owner has not washed anything
     * @throws IllegalArgumentException if the owner is null or blank
     */
    int getTokensSpent(String owner);

}
```

В пакета `bg.sofia.uni.fmi.mjt.laundry` създайте клас `LaundryRoom`, който имплементира интерфейса и има следния публичен конструктор по подразбиране:

```java
public LaundryRoom()
```

## Как работи пералнята

- Пералнята е една. Кошовете чакат на опашка в реда, в който са записани.
- Докато кошът чака, в него може да се слагат и вадят дрехи.
- `runNext()` взима първия кош от опашката и го пуска: всяка дреха, която е в коша в този момент, се пере с програмата, с която кошът е записан. Ако междувременно кошът е останал празен, машината все пак се пуска. Дрехите остават в коша и след изпирането.
- Всяко пускане струва един жетон на собственика на коша (`getOwner()`). За общия кош плаща собственикът му (например "стая 412"), а не всеки съквартирант поотделно.
- Кош, който чака на опашката, не може да бъде записан отново.
- Имената на собствениците се сравняват точно (с главни и малки букви) - "Пешо" и "пешо" са различни хора.

## Изключения

В пакета `bg.sofia.uni.fmi.mjt.laundry.exception` създайте следните изключения. Всички са unchecked, т.е. наследяват `RuntimeException`.

- `BasketOverloadedException` се хвърля, когато дрехите не се побират в коша.
- `EmptyBasketException` се хвърля, когато се вади дреха от празен кош.
- `AlreadyQueuedException` се хвърля, когато се записва кош, който вече чака на опашката.
- `EmptyQueueException` се хвърля, когато машината се пуска при празна опашка.

Невалидните аргументи се проверяват преди състоянието на системата. Например празен кош, който вече чака на опашката, води до `IllegalArgumentException`, а не до `AlreadyQueuedException`.

## Пример

```java
LaundryRoomAPI block60 = new LaundryRoom();

LaundryBasket<Sweater> mariasSweaters = new LaundryBasket<>("Мария", 3.0);
Sweater sweater = new Sweater("Reserved", 0.75);
mariasSweaters.add(sweater);

LaundryBasket<TShirt> ivansShirts = new LaundryBasket<>("Иван", 3.0);
ivansShirts.addAll(List.of(new TShirt("Nike", 0.25), new TShirt("Adidas", 0.25)));

LaundryBasket<Cotton> room412 = new LaundryBasket<>("стая 412", 2.0);
ivansShirts.moveAllTo(room412); // 2
room412.add(new Towel("IKEA", 1.0));

block60.scheduleWash(mariasSweaters, new GentleProgram()); // програмата приема всякакви дрехи
block60.scheduleWash(room412, new CottonProgram());       // в общия кош има само памук
// block60.scheduleWash(mariasSweaters, new CottonProgram()); // не се компилира: пуловерите не са памук

block60.getQueue().size(); // 2
block60.runNext();
sweater.isClean(); // true
block60.runNext();

block60.getTokensSpent("стая 412"); // 1
block60.getTokensSpent("Иван");     // 0: плаща собственикът на общия кош
```

За кош от тип `LaundryBasket<Garment>` програмата трябва да приема всякакви дрехи. Дори сега в него да има само памук, докато чака на опашката, може да бъде добавен пуловер:

```java
LaundryBasket<Garment> mixed = new LaundryBasket<>("стая 413", 5.0);
mixed.add(new TShirt("Nike", 0.25));
// block60.scheduleWash(mixed, new CottonProgram()); // не се компилира, въпреки че в коша има само памук
block60.scheduleWash(mixed, new GentleProgram());
mixed.add(new Sweater("Reserved", 0.75)); // разрешено и докато кошът чака на опашката
block60.runNext();
```

От коша може да се извади най-горната дреха с `takeTop()`:

```java
room412.takeTop();     // вади изпраната хавлия, която е най-отгоре
room412.getWeightKg(); // 0.5
```

---

## 💬 Рефлексия

- Защо `scheduleWash` приема `WashProgram<? super G>`? Ако сигнатурата беше `<G extends Garment> WashJob<G> scheduleWash(LaundryBasket<G> basket, WashProgram<G> program)`, щеше ли да се компилира `block60.scheduleWash(mariasSweaters, new GentleProgram())`? Защо?
- Защо `addAll` приема `Collection<? extends G>`, а `moveAllTo` - `LaundryBasket<? super G>`? Как се прилага **The Get & Put Principle** тук (от коя структура само get-ваме и в коя само put-ваме)?
- Защо `getQueue()` връща `List<WashJob<?>>`, а не `List<WashJob<Garment>>`?
- Защо смесеният кош `LaundryBasket<Garment>` не може да бъде пуснат на `CottonProgram`, а `LaundryBasket<Cotton>` може?
- В `runNext()` взимате `WashJob<?> next` от опашката и викате `next.wash()`. Защо това се компилира, а следният код - не? Как бихте го написали с помощен метод, ако `WashJob` нямаше `wash()`?

  ```java
  for (Garment garment : next.basket()) {
      next.program().wash(garment);
  }
  ```

  Подсказка: [Wildcard Capture and Helper Methods](https://docs.oracle.com/javase/tutorial/java/generics/capture.html).

---

## Структура на пакетите

Спазвайте имената на пакетите на всички по-горе описани типове, тъй като в противен случай решението ви няма да може да бъде автоматично тествано. Можете да добавяте и собствени класове.

```
src
└── bg.sofia.uni.fmi.mjt.laundry
    ├── basket
    │      ├── LaundryBasket.java
    │      └── (...)
    ├── exception
    │      ├── AlreadyQueuedException.java
    │      ├── BasketOverloadedException.java
    │      ├── EmptyBasketException.java
    │      ├── EmptyQueueException.java
    │      └── (...)
    ├── garment
    │      ├── Cotton.java
    │      ├── Garment.java
    │      ├── Sweater.java
    │      ├── Towel.java
    │      ├── TShirt.java
    │      ├── Wool.java
    │      └── (...)
    ├── program
    │      ├── CottonProgram.java
    │      ├── GentleProgram.java
    │      ├── WashProgram.java
    │      ├── WoolProgram.java
    │      └── (...)
    ├── LaundryRoom.java
    ├── LaundryRoomAPI.java
    ├── WashJob.java
    └── (...)
```

## ⚠️ Забележки

- Не променяйте публичните сигнатури на описаните в условието типове (интерфейси, класове, конструктори и record-а `WashJob`). В противен случай решението ви няма да може да бъде автоматично тествано.
- Използването на [Java Stream API](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/stream/package-summary.html) и/или [lambdas](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html) **не е разрешено**. Задачата трябва да се реши с помощта на знанията от курса до момента.
- Дрехи, чието общо тегло е точно равно на капацитета на коша, се побират.
