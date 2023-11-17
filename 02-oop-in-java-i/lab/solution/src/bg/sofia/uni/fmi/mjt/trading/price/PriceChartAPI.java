package bg.sofia.uni.fmi.mjt.trading.price;

public interface PriceChartAPI {

    /**
     * Gets the current price of the stock identified by the provided stock ticker rounded to two decimal places
     *
     * @param stockTicker the stock ticker
     * @return current price of stock. If the stock with the provided ticker is not traded on the platform
     * or the ticker is null, return 0.0
     */
    double getCurrentPrice(String stockTicker);

    /**
     * Changes the current price of the stock identified by the provided stock ticker by the provided percentage.
     * As we are creating a thriving trading platform, the percentage can only be a positive number
     *
     * @param stockTicker   the ticker of the stock for which the price is changing
     * @param percentChange positive number denoting the percentage increase of stock price
     * @return true, if the price was increased successfully. If the stock with the provided ticker is not traded
     * on the platform or the ticker is null, return false. If the provided percentChange is not a positive
     * number, return false.
     */
    boolean changeStockPrice(String stockTicker, int percentChange);

}
