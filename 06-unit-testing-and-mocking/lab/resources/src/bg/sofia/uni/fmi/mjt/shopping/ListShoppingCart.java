package bg.sofia.uni.fmi.mjt.shopping;

import java.util.*;

import bg.sofia.uni.fmi.mjt.shopping.item.Item;

public class ListShoppingCart implements ShoppingCart {

    ArrayList<Item> items;
    ProductCatalog catalog;

    public ListShoppingCart(ProductCatalog catalog) {
    	this.catalog = catalog;
        this.items = new ArrayList<>();
    }

    @Override
    public Collection<Item> getUniqueItems() {
        return new TreeSet<>(items);
    }

    @Override
    public void addItem(Item item) {
        items.add(item);
    }

    @Override
    public void removeItem(Item item) {
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
        Map<Item, Integer> itemToQuantity = create_map();
        Map<Item, Integer> sortedItems = new TreeMap<>(new Comparator<Item>() {
            public int compare(Item item1, Item item2) {
                return itemToQuantity.get(item1).compareTo(itemToQuantity.get(item2));
            }
        });
        sortedItems.putAll(itemToQuantity);
        return sortedItems.keySet();
    }

    private Map<Item, Integer> create_map() {
        HashMap<Item, Integer> itemToQuantity = new HashMap<Item, Integer>();
        for (Item item : items) {
        	boolean condition = !itemToQuantity.containsKey(item);
            itemToQuantity.put(item, condition ? itemToQuantity.get(item) + 1: 1);
        }
        return itemToQuantity;
    }
}

