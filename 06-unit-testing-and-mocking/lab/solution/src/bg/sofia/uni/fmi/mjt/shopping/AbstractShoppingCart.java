package bg.sofia.uni.fmi.mjt.shopping;

import bg.sofia.uni.fmi.mjt.shopping.item.Item;

public abstract class AbstractShoppingCart implements ShoppingCart {

	private static final String ITEM_NULL_MESSAGE = "Item cannot be null.";
	static final String ITEM_NOT_FOUND_MESSAGE = "Item with id {0} not found.";

	protected ProductCatalog catalog;

	public AbstractShoppingCart(ProductCatalog catalog) {
		this.catalog = catalog;
	}

	static void validateItemNotNull(Item item) {
		if (item == null) {
			throw new IllegalArgumentException(ITEM_NULL_MESSAGE);
		}
	}

}
