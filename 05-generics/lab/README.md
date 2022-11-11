# Smart Fridge :snowflake:

В наши дни, т.нар. *умни* устройства стават неизменна част от бита ни: днес никой не се изненадва на прахосмукачки, които чистят сами, перални машини, които може да контролираме от телефона си, включително когато не сме вкъщи и дори коли, които нямат нужда от шофьор.
Вероятно скоро ще можем да си купим умен хладилник, който "знае" какво сме сложили в него, ще следи за качеството и количеството на продуктите и ще поръчва сам онлайн любимите ни храни и напитки, хващайки всички промоции, предвиждайки кое кога ще свърши, ще му изтече срокът на годност и дори познавайки ни добре, ще предусеща, какво ще ни се хапва в съботната вечер, така че да се погрижи да поръча необходимите съставки за приготвяне на любимото ни ястие.

На първо време, ще се задоволим със софтуер, с помощта на който ще знаем, какво има в хладилника ни в момента, ще ни стимулира да използваме с приоритет продуктите, чийто срок на годност изтича скоро и по дадена рецепта на ястие, което ни се е дояло, ще ни подсказва, какво трябва да напазаруваме, за да си го сготвим, дори да сме вече на път към магазина - удобно, нали? 

### Smart Fridge

В пакета `bg.sofia.uni.fmi.mjt.smartfridge` създайте клас `SmartFridge`, който има публичен конструктор със сигнатура `SmartFridge(int totalCapacity)` и имплементира интерфейса `SmartFridgeAPI`:

```java
package bg.sofia.uni.fmi.mjt.smartfridge;

import bg.sofia.uni.fmi.mjt.smartfridge.exception.FridgeCapacityExceededException;
import bg.sofia.uni.fmi.mjt.smartfridge.exception.InsufficientQuantityException;
import bg.sofia.uni.fmi.mjt.smartfridge.ingredient.Ingredient;
import bg.sofia.uni.fmi.mjt.smartfridge.recipe.Recipe;
import bg.sofia.uni.fmi.mjt.smartfridge.storable.Storable;

import java.util.Iterator;
import java.util.List;

public interface SmartFridgeAPI {

    /**
     * Stores some item(s) in the fridge.
     *
     * @param item     the item to store.
     * @param quantity the quantity of the items.
     * @throws IllegalArgumentException        if item is null, or if the quantity is not positive.
     * @throws FridgeCapacityExceededException if there is no free space in the fridge to accommodate the item(s).
     */
    <E extends Storable> void store(E item, int quantity) throws FridgeCapacityExceededException;

    /**
     * Retrieves (and removes) some item(s) from the fridge. Note that if an item has been stored with quantity n,
     * the result contains n elements, one for each item stored.
     *
     * @param itemName the name of the item.
     * @return a list of the retrieved items, ordered by expiration date, starting from the ones
     * that will expire first. If there are no items with the specified name, returns an empty list.
     * If there are items already expired, those are also part of the returned list.
     * @throws IllegalArgumentException if itemName is null, empty or blank.
     */
    List<? extends Storable> retrieve(String itemName);

    /**
     * Retrieves (and removes) the specified number of item(s) with the given from the fridge.
     *
     * @param itemName the name of the item(s).
     * @param quantity the quantity of the items to retrieve.
     * @return a list of the retrieved items, containing {@code quantity} elements, ordered by expiration date,
     * starting from the ones that will expire first. If there are items already expired, those are also part of the
     * returned list.
     * @throws IllegalArgumentException      if itemName is null, empty or blank, or of quantity is not positive.
     * @throws InsufficientQuantityException if item with the specified name is not found in the fridge
     *                                       or the stored quantity is insufficient.
     */
    List<? extends Storable> retrieve(String itemName, int quantity) throws InsufficientQuantityException;

    /**
     * Gets the quantity of items with the specified name that are currently stored in the fridge.
     * Note that the quantity includes also potentially expired items.
     *
     * @throws IllegalArgumentException if itemName is null, empty or blank.
     */
    int getQuantityOfItem(String itemName);

    /**
     * Gets the ingredients that are missing or insufficient in the fridge to prepare the recipe.
     * Note that if some items needed for the recipe are stored in the fridge, but are expired,
     * they cannot be used for preparing the recipe and should be considered missing/insufficient and included
     * in the result. The method though does not remove any expired items from the fridge.
     *
     * @param recipe the recipe
     * @return an iterator of the ingredients missing or insufficient to prepare the recipe.
     * @throws IllegalArgumentException if recipe is null.
     */
    public Iterator<Ingredient<? extends Storable>> getMissingIngredientsFromRecipe(Recipe recipe);

    /**
     * Removes all expired items from the fridge and returns them as a list, in an undefined order.
     * If there are no expired items stored in the fridge, returns an empty list.
     */
    List<? extends Storable> removeExpired();

}
```

### Какво държим в хладилника?

Обикновено в хладилника съхраняваме храни и напитки, но всъщност може да държим в него най-разнообразни неща: лед за коктейлите ни, подправки, лекарства, козметика и дори предмети като тенджери, чинии, чаши и прибори.

В нашия умен хладилник, ще може да съхраняваме произволни обекти, имплементиращи интерфейса `Storable`:

```java
package bg.sofia.uni.fmi.mjt.smartfridge.storable;

import bg.sofia.uni.fmi.mjt.smartfridge.storable.type.StorableType;

import java.time.LocalDate;

public interface Storable {

    /**
     * Gets the type of this storable.
     */
    StorableType getType();

    /**
     * Gets the name of this storable.
     */
    String getName();

    /**
     * Gets the expiration date of this storable.
     */
    LocalDate getExpiration();

    /**
     * Returns true, if the storable is expired.
     */
    boolean isExpired();

}
```

Приемаме, че всеки `Storable` уникално се определя от името си, а видовете `Storable` ще моделираме с enum-a `StorableType`:

```java
package bg.sofia.uni.fmi.mjt.smartfridge.storable.type;

public enum StorableType {

    FOOD, BEVERAGE, OTHER

}
```

### Рецепти и съставките им

Рецептите за приготвяне на ястия ще моделираме с клас, имплементиращ интерфейса `Recipe`:

```java
package bg.sofia.uni.fmi.mjt.smartfridge.recipe;

import bg.sofia.uni.fmi.mjt.smartfridge.ingredient.Ingredient;
import bg.sofia.uni.fmi.mjt.smartfridge.storable.Storable;

import java.util.Set;

public interface Recipe {

    /**
     * Gets the ingredients for this recipe.
     */
    Set<Ingredient<? extends Storable>> getIngredients();

    /**
     * Adds an ingredient to the recipe. If an ingredient with the same item exists, merges with the existing one
     * by increasing the quantity of the ingredient.
     *
     * @param ingredient the ingredient
     * @throws IllegalArgumentException if ingredient is null.
     */
    void addIngredient(Ingredient<? extends Storable> ingredient);

    /**
     * Removes the ingredient with the respective item name from the recipe.
     * If an ingredient with such item name does not exist, method does nothing.
     *
     * @param itemName the name of the item
     * @throws IllegalArgumentException if itemName is null, empty or blank.
     */
    void removeIngredient(String itemName);

}
```

Рецептата съдържа съставки, които се представят чрез record-a `DefaultIngredient`, имплементиращ интерфейса `Ingredient`:

```java
package bg.sofia.uni.fmi.mjt.smartfridge.ingredient;

import bg.sofia.uni.fmi.mjt.smartfridge.storable.Storable;

public record DefaultIngredient<E extends Storable>(E item, int quantity) implements Ingredient<E> {
}
```

```java
package bg.sofia.uni.fmi.mjt.smartfridge.ingredient;

import bg.sofia.uni.fmi.mjt.smartfridge.storable.Storable;

public interface Ingredient<E extends Storable> {

    /**
     * Gets the item of the ingredient.
     */
    E item();

    /**
     * Gets the quantity of the ingredient.
     */
    int quantity();

}
```

## Подсказки

:point_right: За смислена реализация и локално тестване, ще трябва да си създадете имплементации на някои от описаните интерфейси, но имате свобода да прецените, с колко и какви класове. 

### Пакети

Спазвайте имената на пакетите на всички по-горе описани типове, тъй като в противен случай, решението ви няма да може да бъде автоматично тествано.

```
src
└── bg.sofia.uni.fmi.mjt.smartfridge
    ├── exception
    │      ├── FridgeCapacityExceededException.java
    │      └── InsufficientQuantityException.java
    ├── ingredient
    │      ├── DefaultIngredient.java
    │      └── Ingredient.java
    ├── recipe
    │      └── Recipe.java
    ├── storable
    │      ├── type
    │      │     └── StorableType.java
    │      └── Storable.java
    ├── SmartFridge.java
    └── SmartFridgeAPI.java
```

### :warning: Забележки

- Не променяйте по никакъв начин интерфейсите, дадени в условието.
- Задачата трябва да се реши с помощта на знанията от курса до момента. Това в частност изключва използването на ламбда изрази и Stream API.
