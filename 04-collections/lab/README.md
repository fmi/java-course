# Структури от данни в Java (Collections)

## Shopping Cart

Ще имплементираме пазарска количка, която държи определени предмети, които искаме да си купим от магазина.

Всички класове и интерфейси поставете в пакет `bg.uni.sofia.fmi.mjt.shopping`.

Всички предмети в магазина имплементират интерфейса `Item`:

```java
public interface Item {
    String getName();
    String getDescription();
    double getPrice();
}
```

Два предмета са еднакви, ако всичките им характеристики са еднакви.

С цел да можем автоматично да тестваме кода ви, създайте две имплементаци на `Item`: `Apple` и `Chocolate`, съответно с публични конструктори `Apple(String name, String description, double price)` и `Chocolate(String name, String description, double price)`.

Създайте класовете `ListShoppingCart` и `MapShoppingCart`, които имплементират интерфейса `ShoppingCart`, използвайки съответно `List` и `Map` за съхранение на предметите в количката.

```java
import java.util.Collection;

public interface ShoppingCart {

    /**
     * Returns the unique items in the shopping cart
     * 
     * @return the unique items in the shopping cart
     */
    Collection<Item> getUniqueItems();

    /**
     * Returns the unique items sorted by quantity in the cart
     * 
     * @return the unique items sorted by quantity in the cart
     */
    Collection<Item> getSortedItems();

    /**
     * Adds an item to the shopping cart. If an item is null, it is not added to the
     * cart. If the same item has already been added, then the number of these items
     * increases by one
     * 
     * @param item
     *            the item to be added
     */
    void addItem(Item item);

    /**
     * Removes the item from the shopping cart. If there is more than one of the
     * same item, then their number decreases by one
     * 
     * @param item
     *            the item to be removed
     */
    void removeItem(Item item) throws ItemNotFoundException;

    /**
     * Returns the total sum to be paid at checkout
     * 
     * @return the total sum to be paid at checkout
     */
    double getTotal();

}
```

Сравнете бързодействието на методите на `ShoppingCart` при двете имплементации, за разглезен консуматор с над един милион предмета в пазарската количка.

### Подсказка

Може да засичате текущото време като ползвате статичния метод `System.currentTimeMillis()`
