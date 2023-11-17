package bg.sofia.uni.fmi.mjt.trading.stock;

import java.time.LocalDateTime;

public interface StockPurchase {

    /**
     * @return the quantity of stocks in this purchase
     */
    int getQuantity();

    /**
     * @return the timestamp when the purchase was made
     */
    LocalDateTime getPurchaseTimestamp();

    /**
     * @return the price per unit of stock at the time of purchase rounded to two decimal places
     */
    double getPurchasePricePerUnit();

    /**
     * Calculates the total price of the purchase given the quantity and the price per unit rounded
     * to two decimal places
     *
     * @return the multiplication result of the quantity by the price-per-unit
     */
    double getTotalPurchasePrice();

    /**
     * @return the ticker of the stock that was purchased
     */
    String getStockTicker();

}
