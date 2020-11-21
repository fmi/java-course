package bg.sofia.uni.fmi.mjt.shopping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import bg.sofia.uni.fmi.mjt.shopping.item.Item;

public class MapShoppingCart implements ShoppingCart {

    public Map<Item, Integer> items; public ProductCatalog catalog;

    public MapShoppingCart(ProductCatalog catalog) {
    	this.catalog = catalog;
    }

    public Collection<Item> getUniqueItems() {
        Collection<Item> i = new ArrayList<>();
        for(Map.Entry<Item, Integer> entry:items.entrySet()) {
            i.add(entry.getKey());
        }
        return i;
    }

    @Override
    public void addItem(Item item) {
        if (item != null) {
            if (items.containsKey(item)) {
                Integer i = items.get(item);
                if (i == null)
                    items.put(item, 0);
                else
                    items.put(item, i++);
            }
        }
    }

    @Override
    public void removeItem(Item item) {
        if (item == null) {
        	throw new ItemNotFoundException();
        }
        
        Integer occurrences = items.get(item);
        if (--occurrences==0) {
        	items.remove(item);
        } else {
        	items.put(item, occurrences - 1);
        }
    }

    @Override
    public double getTotal() {
        int total = 0;
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
            	if (info1.price() > info2.price()) {
            	    return 1;
                } else if (info1.price() < info2.price()) {
            	    return -1;
                } else {
            	    return 0;
                }
            }
        });
        return sortedItems;
    }

}
