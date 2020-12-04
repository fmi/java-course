package bg.sofia.uni.fmi.mjt.shopping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bg.sofia.uni.fmi.mjt.shopping.item.Item;

public class MapShoppingCart extends AbstractShoppingCart {

    private Map<Item, Integer> items;

    public MapShoppingCart(ProductCatalog catalog) {
        super(catalog);
        this.items = new HashMap<>();
    }

    @Override
    public Collection<Item> getUniqueItems() {
        return items.keySet();
    }

    @Override
    public void addItem(Item item) {
        validateItemNotNull(item);

        Integer occurrences = items.get(item);
        items.put(item, occurrences == null ? 1 : ++occurrences);
    }

    @Override
    public void removeItem(Item item) {
        validateItemNotNull(item);

        if (!items.containsKey(item)) {
            throw new ItemNotFoundException(String.format(ITEM_NOT_FOUND_MESSAGE, item.getId()));
        }

        Integer occurrences = items.get(item);
        if (--occurrences == 0) {
            items.remove(item);
        } else {
            items.put(item, occurrences);
        }
    }

    @Override
    public double getTotal() {
        double total = 0;
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            ProductInfo info = catalog.getProductInfo(entry.getKey().getId());
            total += info.price() * entry.getValue();
        }
        return total;
    }

    @Override
    public Collection<Item> getSortedItems() {
        List<Item> sortedItems = new ArrayList<>(items.keySet());
        Collections.sort(sortedItems, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                ProductInfo info1 = catalog.getProductInfo(item1.getId());
                ProductInfo info2 = catalog.getProductInfo(item2.getId());
                return Double.compare(info1.price(), info2.price());
            }
        });
        return sortedItems;
    }
}
