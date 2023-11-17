package bg.sofia.uni.fmi.mjt.trading.price;

import bg.sofia.uni.fmi.mjt.trading.DoubleRounder;
import bg.sofia.uni.fmi.mjt.trading.stock.AmazonStockPurchase;
import bg.sofia.uni.fmi.mjt.trading.stock.GoogleStockPurchase;
import bg.sofia.uni.fmi.mjt.trading.stock.MicrosoftStockPurchase;

public class PriceChart implements PriceChartAPI {

    private double microsoftStockPrice;
    private double googleStockPrice;
    private double amazonStockPrice;

    public PriceChart(double microsoftStockPrice, double googleStockPrice, double amazonStockPrice) {
        this.microsoftStockPrice = microsoftStockPrice;
        this.googleStockPrice = googleStockPrice;
        this.amazonStockPrice = amazonStockPrice;
    }

    @Override
    public double getCurrentPrice(String stockTicker) {
        double price = switch (stockTicker) {
            case MicrosoftStockPurchase.STOCK_TICKER -> microsoftStockPrice;
            case AmazonStockPurchase.STOCK_TICKER -> amazonStockPrice;
            case GoogleStockPurchase.STOCK_TICKER -> googleStockPrice;
            case null, default -> 0.0;
        };

        return DoubleRounder.round(price);
    }

    @Override
    public boolean changeStockPrice(String stockTicker, int percentChange) {
        if (null == stockTicker || percentChange <= 0) {
            return false;
        }

        return switch (stockTicker) {
            case MicrosoftStockPurchase.STOCK_TICKER -> {
                microsoftStockPrice = calculateNewPrice(microsoftStockPrice, percentChange);
                yield true;
            }
            case AmazonStockPurchase.STOCK_TICKER -> {
                amazonStockPrice = calculateNewPrice(amazonStockPrice, percentChange);
                yield true;
            }
            case GoogleStockPurchase.STOCK_TICKER -> {
                googleStockPrice = calculateNewPrice(googleStockPrice, percentChange);
                yield true;
            }

            default -> false;
        };
    }

    private double calculateNewPrice(double currentPrice, int percentChange) {
        final int hundredPercent = 100;

        return currentPrice * (hundredPercent + percentChange) / hundredPercent;
    }

}
