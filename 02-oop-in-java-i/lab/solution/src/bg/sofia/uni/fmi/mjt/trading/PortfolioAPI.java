package bg.sofia.uni.fmi.mjt.trading;

import bg.sofia.uni.fmi.mjt.trading.stock.StockPurchase;
import java.time.LocalDateTime;

public interface PortfolioAPI {

    /**
     * Purchases the provided quantity of stocks with the provided ticker. The budget in the portfolio should
     * decrease by the corresponding amount. If a stock is on-demand then naturally its price increases.
     * Every stock purchase should result in a 5% price increase of the purchased stock
     *
     * @param stockTicker the stock ticker
     * @param quantity    the quantity of stock that should be purchased
     * @return the stock purchase if it was successfully purchased. If the stock with the provided ticker is
     * not traded on the platform or the ticker is null, return null. If the budget is not enough to make the
     * purchase, return null. If quantity is not a positive number, return null. If the portfolio is already
     * at max size, return null.
     */
    StockPurchase buyStock(String stockTicker, int quantity);

    /**
     * @return all stock purchases made so far.
     */
    StockPurchase[] getAllPurchases();

    /**
     * Retrieves purchases made in the provided inclusive time interval
     *
     * @param startTimestamp the start timestamp of the interval
     * @param endTimestamp   the end timestamp of the interval
     * @return all stock purchases made so far in the provided time interval
     */
    StockPurchase[] getAllPurchases(LocalDateTime startTimestamp, LocalDateTime endTimestamp);

    /**
     * @return the current total net worth of the portfolio: the sum of each purchases' quantity multiplied by
     * the current price of the stock identified by that purchase rounded to two decimal places
     */
    double getNetWorth();

    /**
     * @return the remaining budget in the portfolio rounded to two decimal places
     */
    double getRemainingBudget();

    /**
     * @return the owner of the portfolio
     */
    String getOwner();
    
}
