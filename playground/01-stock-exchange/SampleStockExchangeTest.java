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
    public void testFunnelChecker_StockOne() {
        assertEquals(7, stockExchange.maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
    }

    @Test
    public void testFunnelChecker_StockTwo() {
        assertEquals(4, stockExchange.maxProfit(new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    public void testFunnelChecker_StockThree() {
        assertEquals(0, stockExchange.maxProfit(new int[]{7, 6, 4, 3, 1}));
    }

}
