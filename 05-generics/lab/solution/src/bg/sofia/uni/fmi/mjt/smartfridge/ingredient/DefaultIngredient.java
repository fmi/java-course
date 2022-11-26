package bg.sofia.uni.fmi.mjt.smartfridge.ingredient;

import bg.sofia.uni.fmi.mjt.smartfridge.storable.Storable;

public record DefaultIngredient<E extends Storable>(E item, int quantity) implements Ingredient<E> {
}
