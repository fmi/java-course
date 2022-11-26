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
