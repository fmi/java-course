package bg.sofia.uni.fmi.mjt.shopping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import bg.sofia.uni.fmi.mjt.shopping.item.Item;

public class ListShoppingCart extends AbstractShoppingCart {

    private List<Item> items;
  
    public ListShoppingCart(ProductCatalog catalog) {
    	super(catalog);
        this.items = new ArrayList<>();
    }

    @Override
    public Collection<Item> getUniqueItems() {
        return new HashSet<>(items);
    }

    @Override
    public void addItem(Item item) {
    	validateItemNotNull(item);
        items.add(item);
    }

    @Override
    public void removeItem(Item item) throws ItemNotFoundException {
    	validateItemNotNull(item);
        
        if (!items.contains(item)) {
            throw new ItemNotFoundException(String.format(ITEM_NOT_FOUND_MESSAGE, item.getId()));
        }
        
        items.remove(item);
    }

    @Override
    public double getTotal() {
        double total = 0;
        for (Item item : items) {
        	ProductInfo info = catalog.getProductInfo(item.getId());
            total += info.price();
        }
        return total;
    }

    @Override
    public Collection<Item> getSortedItems() {
        Map<Item, Integer> itemToQuantity = createQuantityMap();
        Map<Item, Integer> sortedItems = new TreeMap<>(new Comparator<Item>() {
            public int compare(Item item1, Item item2) {
                return itemToQuantity.get(item2).compareTo(itemToQuantity.get(item1));
            }
        });
        sortedItems.putAll(itemToQuantity);
        return sortedItems.keySet();
    }

    private Map<Item, Integer> createQuantityMap() {
        HashMap<Item, Integer> itemToQuantity = new HashMap<>();
        for (Item item : items) {
        	boolean condition = itemToQuantity.containsKey(item);
            itemToQuantity.put(item, condition ? itemToQuantity.get(item) + 1: 1);
        }
        return itemToQuantity;
    }
}

