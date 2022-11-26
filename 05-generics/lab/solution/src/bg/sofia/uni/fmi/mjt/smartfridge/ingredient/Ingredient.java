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
