package bg.sofia.uni.fmi.mjt.shopping;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import bg.sofia.uni.fmi.mjt.shopping.item.Apple;
import bg.sofia.uni.fmi.mjt.shopping.item.Chocolate;
import bg.sofia.uni.fmi.mjt.shopping.item.Item;

@RunWith(MockitoJUnitRunner.class)
public class MapShoppingCartTest {

    private static final String APPLE_ID = "apple-1";
    private static final String CHOCOLATE_ID = "chocolate-1";

    private static final Apple APPLE = new Apple(APPLE_ID);
    private static final Chocolate CHOCOLATE = new Chocolate(CHOCOLATE_ID);

    private ShoppingCart cart;

    @Mock
    private ProductCatalog catalog;

    @Before
    public void setUp() {
        cart = new MapShoppingCart(catalog);
    }

    @Test
    public void testGetUniqueItemsWithDuplicatesSuccess() {
        cart.addItem(APPLE);
        cart.addItem(CHOCOLATE);
        cart.addItem(APPLE);

        int expected = 2;
        int actual = cart.getUniqueItems().size();
        assertEquals("getUniqueItems does not return correct number of items when there are duplicate items", expected, actual);
    }

    @Test
    public void testRemoveItemWithExistingItemSuccess() {
        cart.addItem(APPLE);
        cart.addItem(CHOCOLATE);
        cart.removeItem(APPLE);

        int expected = 1;
        int actual = cart.getUniqueItems().size();
        assertEquals("removeItem does not remove correctly", expected, actual);
    }

    @Test(expected = ItemNotFoundException.class)
    public void testRemoveItemWithNonExistingItemFail() {
        cart.removeItem(APPLE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveItemWithNullItemFail() {
        cart.removeItem(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddItemWithNullItemFail() {
        cart.addItem(null);
    }

    @Test
    public void testGetTotalSuccess() {
        cart.addItem(APPLE);
        cart.addItem(CHOCOLATE);
        cart.addItem(CHOCOLATE);

        Mockito.when(catalog.getProductInfo(APPLE_ID)).thenReturn(new ProductInfo("apple", "green apple", 1));
        Mockito.when(catalog.getProductInfo(CHOCOLATE_ID)).thenReturn(new ProductInfo("chocolate", "black chocolate", 2));

        double expected = 5;
        double actual = cart.getTotal();
        assertEquals("getTotal does not calculate total price of items correctly", expected, actual, 0);

        Mockito.verify(catalog, Mockito.times(1)).getProductInfo(APPLE_ID);
        Mockito.verify(catalog, Mockito.times(1)).getProductInfo(CHOCOLATE_ID);
    }

    @Test
    public void testGetSortedItems() {
        cart.addItem(CHOCOLATE);
        cart.addItem(CHOCOLATE);
        cart.addItem(APPLE);
        cart.addItem(APPLE);
        cart.addItem(APPLE);

        Mockito.when(catalog.getProductInfo(APPLE_ID)).thenReturn(new ProductInfo("apple", "green apple", 1));
        Mockito.when(catalog.getProductInfo(CHOCOLATE_ID)).thenReturn(new ProductInfo("chocolate", "black chocolate", 2));

        Collection<Item> expectedItems = List.of(APPLE, CHOCOLATE);
        Collection<Item> actualItems = new ArrayList<>(cart.getSortedItems());
        assertEquals("getSortedItems does not sort items correctly", expectedItems, actualItems);
    }

}
