package bg.sofia.uni.fmi.mjt.smartfridge;

import bg.sofia.uni.fmi.mjt.smartfridge.exception.FridgeCapacityExceededException;
import bg.sofia.uni.fmi.mjt.smartfridge.exception.InsufficientQuantityException;
import bg.sofia.uni.fmi.mjt.smartfridge.ingredient.DefaultIngredient;
import bg.sofia.uni.fmi.mjt.smartfridge.ingredient.Ingredient;
import bg.sofia.uni.fmi.mjt.smartfridge.recipe.Recipe;
import bg.sofia.uni.fmi.mjt.smartfridge.storable.Storable;
import bg.sofia.uni.fmi.mjt.smartfridge.storable.StorableByExpirationComparator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class SmartFridge implements SmartFridgeAPI {

    private final Map<String, Queue<Storable>> storage = new HashMap<>();
    private final int totalCapacity;
    private int currentCapacity;

    public SmartFridge(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    @Override
    public <E extends Storable> void store(E item, int quantity) throws FridgeCapacityExceededException {
        if (item == null || quantity <= 0) {
            throw new IllegalArgumentException("Food cannot be null and quantity must be positive");
        }

        if (currentCapacity + quantity > totalCapacity) {
            throw new FridgeCapacityExceededException("Capacity exceeded");
        }

        storage.putIfAbsent(item.getName(), new PriorityQueue<>(new StorableByExpirationComparator()));
        for (int i = 0; i < quantity; i++) {
            storage.get(item.getName()).add(item);
        }
        currentCapacity += quantity;
    }

    @Override
    public List<? extends Storable> retrieve(String itemName) {
        if (itemName == null || itemName.isEmpty() || itemName.isBlank()) {
            throw new IllegalArgumentException("Food cannot be null, empty or blank");
        }

        if (!storage.containsKey(itemName)) {
            return new LinkedList<>();
        }

        List<Storable> retrieved = new LinkedList<>(storage.get(itemName));
        currentCapacity -= retrieved.size();
        storage.remove(itemName);
        return retrieved;
    }

    @Override
    public List<? extends Storable> retrieve(String itemName, int quantity) throws InsufficientQuantityException {
        if (itemName == null || itemName.isEmpty() || itemName.isBlank() || quantity <= 0) {
            throw new IllegalArgumentException("Food cannot be null, empty or blank");
        }

        if (!storage.containsKey(itemName) || storage.get(itemName).size() < quantity) {
            throw new InsufficientQuantityException("Invalid quantity of the specified food");
        }

        List<Storable> retrieved = new LinkedList<>();
        Queue<Storable> foods = storage.get(itemName);
        for (int i = 0; i < quantity; i++) {
            retrieved.add(foods.poll());
        }
        currentCapacity -= quantity;
        return retrieved;
    }

    @Override
    public int getQuantityOfItem(String itemName) {
        if (itemName == null || itemName.isEmpty() || itemName.isBlank()) {
            throw new IllegalArgumentException("Food cannot be null, empty or blank");
        }

        Queue<Storable> foods = storage.get(itemName);
        return foods == null ? 0 : foods.size();
    }

    @Override

    public Iterator<Ingredient<? extends Storable>> getMissingIngredientsFromRecipe(Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("Recipe cannot be null");
        }

        Set<Ingredient<?>> ingredients = recipe.getIngredients();

        List<Ingredient<?>> missingIngredients = new LinkedList<>();

        for (Ingredient<?> ingredient : ingredients) {
            Queue<Storable> foods = storage.get(ingredient.item().getName());

            if (foods == null) {
                missingIngredients.add(ingredient);
            } else {
                int availableQuantity = 0;
                Iterator<Storable> iterator = foods.iterator();

                while (iterator.hasNext()) {
                    if (!iterator.next().isExpired()) {
                        availableQuantity++;
                    }
                }

                if (availableQuantity < ingredient.quantity()) {
                    missingIngredients
                        .add(new DefaultIngredient<>(ingredient.item(), ingredient.quantity() - availableQuantity));
                }
            }
        }

        return missingIngredients.iterator();
    }

    @Override
    public List<? extends Storable> removeExpired() {
        List<Storable> expired = new LinkedList<>();
        for (Queue<Storable> foods : storage.values()) {
            for (Iterator<Storable> it = foods.iterator(); it.hasNext(); ) {
                Storable storable = it.next();
                if (storable.isExpired()) {
                    expired.add(storable);
                    currentCapacity--;
                    it.remove();
                }
            }
        }
        return expired;
    }

}
