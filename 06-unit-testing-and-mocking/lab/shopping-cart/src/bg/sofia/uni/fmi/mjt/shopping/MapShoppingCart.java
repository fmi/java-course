package bg.sofia.uni.fmi.mjt.shopping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bg.sofia.uni.fmi.mjt.shopping.item.Item;

public class MapShoppingCart implements ShoppingCart {

    private Map<Item, Integer> items = new HashMap<>();

    @Override
    public Collection<Item> getUniqueItems() {
        return items.keySet();
    }

    @Override
    public void addItem(Item item) {
        if (item != null) {
            Integer occurrences = items.get(item);
            if (occurrences == null) {
                occurrences = new Integer(0);
            }
            items.put(item, ++occurrences);
        }
    }

    @Override
    public void removeItem(Item item) throws ItemNotFoundException {
        if (!items.containsKey(item)) {
            throw new ItemNotFoundException();
        }
        Integer occurrences = items.get(item);
        items.put(item, occurrences--);
    }

    @Override
    public double getTotal() {
        int total = 0;
        for (Map.Entry<Item, Integer> e : items.entrySet()) {
            total += e.getKey().getPrice();
        }
        return total;
    }

    @Override
    public Collection<Item> getSortedItems() {
        List<Item> itemsList = new ArrayList<>(items.keySet());
        Collections.sort(itemsList, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return Double.compare(o1.getPrice(), o2.getPrice());
            }
        });
        return itemsList;
    }
}
