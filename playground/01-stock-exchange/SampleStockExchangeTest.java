import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SampleStockExchangeTest {

    private static StockExchange stockExchange;

    @BeforeClass
    public static void setUp() {
        stockExchange = new StockExchange();
    }

    @Test
    public void testStockExchange_StockOne() {
        assertEquals(stockExchange.maxProfit(new int[]{7, 1, 5, 3, 6, 4}), 7);
    }

    @Test
    public void testStockExchange_StockTwo() {
        assertEquals(stockExchange.maxProfit(new int[]{1, 2, 3, 4, 5}), 4);
    }

    @Test
    public void testStockExchange_StockThree() {
        assertEquals(stockExchange.maxProfit(new int[]{7, 6, 4, 3, 1}), 0);
    }

}
