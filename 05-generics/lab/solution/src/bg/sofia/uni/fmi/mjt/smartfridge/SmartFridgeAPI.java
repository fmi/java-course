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
