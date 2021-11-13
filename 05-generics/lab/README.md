# Gifts :package: :gift:

Наближава Черният петък, Коледа и Нова Година, и се замисляме за подаръци. Ще трябва да избираме и опаковаме подаръци за нашите близки, надяваме се и ние да получим, или за да сме сигурни, взимаме нещата в свои ръце и си правим сами подарък. Ще създадем приложение, което ще моделира опаковането и доставката на подаръци.

### Подарък

Подарък може да бъде всяко нещо. Някои подаръци се състоят от няколко неща. В живота някои подаръци не струват нищо или пък са безценни, но в нашата задача, всеки има определена цена.

Всички подаръци имплементират generic интерфейса `Gift<T extends Priceable>`:

```java
package bg.sofia.uni.fmi.mjt.gifts.gift;

import bg.sofia.uni.fmi.mjt.gifts.person.Person;

import java.util.Collection;

public interface Gift<T extends Priceable> {

    /**
     * @return the sender of this gift.
     */
    Person<?> getSender();

    /**
     * @return the receiver of this gift.
     */
    Person<?> getReceiver();

    /**
     * @return the total price of the items in the gift.
     */
    double getPrice();

    /**
     * @param t the item to add to this gift
     * @throws IllegalArgumentException if @t is null
     */
    void addItem(T t);

    /**
     * @param t the item to remove from this gift
     * @return true if the @t was contained in this gift, false otherwise.
     *         In particular, if @t is null, return false.
     */
    boolean removeItem(T t);

    /**
     * @return an unmodifiable copy of the items in this gift.
     */
    Collection<T> getItems();

    /**
     * @return the most expensive item in this gift. If there is a tie, return any of them.
     *         If the gift contains no items, return null.
     */
    T getMostExpensiveItem();

}
```

Цената се моделира от интерфейса `Priceable`:

```java
package bg.sofia.uni.fmi.mjt.gifts.gift;

/**
 * Defines objects having determinable price.
 */
public interface Priceable {

    /**
     * @return the price of the instance.
     */
    double getPrice();
}
```

В пакета `bg.sofia.uni.fmi.mjt.gifts.gift` създайте generic клас `BirthdayGift<T extends Priceable> implements Gift<T>`, който има конструктор

```java
public BirthdayGift(Person<?> sender, Person<?> receiver, Collection<T> items)
```

### Person

Хората, които си изпращат подаръци, се моделират от generic класа `DefaultPerson<I>`, който има конструктор

```java
public DefaultPerson(I id)
```

и имплементира generic интерфейса `Person<I>`:

```java
package bg.sofia.uni.fmi.mjt.gifts.person;

import bg.sofia.uni.fmi.mjt.gifts.exception.WrongReceiverException;
import bg.sofia.uni.fmi.mjt.gifts.gift.Gift;

import java.util.Collection;

public interface Person<I> {

    /**
     * @param n the max number of most expensive gifts to return.
     *          If there is a tie of two or more gifts with the same lowest price in the top @n,
     *          which of those to include in the result is undefined.
     *          If @n exceeds the total number of gifts received,
     *          return all of them. If @n = 0, return an empty collection.
     * @return an unmodifiable copy of the @n most expensive gifts received by this person. The order of the gifts
     * in the returned collection is undefined.
     * @throws IllegalArgumentException if @n < 0
     */
    Collection<Gift<?>> getNMostExpensiveReceivedGifts(int n);

    /**
     * @param person the sender of the gifts.
     *               If there are no gifts sent by @person to this person, return an empty collection.
     * @return an unmodifiable copy of the gifts sent by @person, in undefined order
     * @throws IllegalArgumentException if @person is null
     */
    Collection<Gift<?>> getGiftsBy(Person<?> person);

    /**
     * @return the unique id of this person.
     */
    I getId();

    /**
     * @param gift the @gift to be received
     * @throws IllegalArgumentException if @gift is null
     * @throws WrongReceiverException   if recipient of the @gift is not this person
     */
    void receiveGift(Gift<?> gift);

}
```

Той е параметризиран с типа `I`, елементите на който се използват за идентификация на човека. Идентификаторът може да бъде число, низ, инстанция на някакъв клас, създаден за целта.

### Опаковане и доставяне на подаръци

В пакета `bg.sofia.uni.fmi.mjt.gifts.service` създайте generic клас `DefaultPackingService<T extends Priceable>`, който имлементира generic интерфейса `PackingService<T>`:

```java
package bg.sofia.uni.fmi.mjt.gifts.service;

import bg.sofia.uni.fmi.mjt.gifts.gift.Gift;
import bg.sofia.uni.fmi.mjt.gifts.gift.Priceable;
import bg.sofia.uni.fmi.mjt.gifts.person.Person;

import java.util.Collection;

public interface PackingService<T extends Priceable> {

    /**
     * @param sender   the sender of the item @t
     * @param receiver the receiver of the item @t
     * @param item     the item to wrap
     * @throws IllegalArgumentException if any of @sender, @receiver, @item is null
     * @return the item @t wrapped in gift from @sender to @receiver
     */
    Gift<T> pack(Person<?> sender, Person<?> receiver, T item);

    /**
     * @param sender   the sender of the items @t
     * @param receiver the receiver of the items @t
     * @param items    the items to wrap
     * @throws IllegalArgumentException if any of @sender, @receiver, any item in @items is null
     * @return the items @items wrapped in gift from @sender to @receiver
     */
    Gift<T> pack(Person<?> sender, Person<?> receiver, T... items);

    /**
     * @param gift the gift to unwrap
     * @throws IllegalArgumentException if @gift is null
     * @return an unmodifiable copy of the items in the gift @gift.
     */
    Collection<T> unpack(Gift<T> gift);

}
```

В същия пакет, създайте клас `DefaultDeliveryService`, който имлементира интерфейса `DeliveryService`:

```java
package bg.sofia.uni.fmi.mjt.gifts.service;

import bg.sofia.uni.fmi.mjt.gifts.gift.Gift;
import bg.sofia.uni.fmi.mjt.gifts.person.Person;
import bg.sofia.uni.fmi.mjt.gifts.exception.WrongReceiverException;

public interface DeliveryService {

    /**
     * @param receiver the receiver of the @gift
     * @param gift the gift to be sent
     * @throws IllegalArgumentException if @receiver or @gift is null
     * @throws WrongReceiverException if @receiver is different from the receiver of the gift
     */
    void send(Person<?> receiver, Gift<?> gift);
}
```

### Пакети

Спазвайте имената на пакетите на всички по-горе описани типове, тъй като в противен случай, решението ви няма да може да бъде автоматично тествано.

```
src
└── bg.sofia.uni.fmi.mjt.gifts
    ├── exception
    │      └── WrongReceiverException.java
    ├── gift
    │      ├── BirthdayGift.java
    │      ├── Gift.java
    │      ├── Priceable.java
    │      └── (...)
    ├── person
    │      ├── DefaultPerson.java
    │      └── Person.java
    └── service
           ├── DefaultDeliveryService.java
           ├── DefaultPackingService.java
           ├── DeliveryService.java
           ├── PackingService.java
           └── (...)
```

### :warning: Забележки

- Не променяйте по никакъв начин интерфейсите, дадени в условието.
