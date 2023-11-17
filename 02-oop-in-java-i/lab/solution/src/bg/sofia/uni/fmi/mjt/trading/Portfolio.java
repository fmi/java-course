package bg.sofia.uni.fmi.mjt.trading;

import bg.sofia.uni.fmi.mjt.trading.price.PriceChartAPI;
import bg.sofia.uni.fmi.mjt.trading.stock.AmazonStockPurchase;
import bg.sofia.uni.fmi.mjt.trading.stock.GoogleStockPurchase;
import bg.sofia.uni.fmi.mjt.trading.stock.MicrosoftStockPurchase;
import bg.sofia.uni.fmi.mjt.trading.stock.StockPurchase;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Portfolio implements PortfolioAPI {

    private static final int FIXED_STOCK_PRICE_INCREASE_PERCENTAGE = 5;

    private final String owner;
    private double budget;
    private StockPurchase[] stockPurchases;
    private int size;
    private int maxSize;
    private final PriceChartAPI priceChart;

    public Portfolio(String owner, PriceChartAPI priceChart, double budget, int maxSize) {
        this(owner, priceChart, new StockPurchase[] {}, budget, maxSize);
    }

    public Portfolio(String owner, PriceChartAPI priceChart, StockPurchase[] stockPurchases, double budget,
            int maxSize) {
        this.owner = owner;
        this.budget = budget;
        this.priceChart = priceChart;
        this.size = stockPurchases.length;
        this.maxSize = maxSize;
        this.stockPurchases = Arrays.copyOf(stockPurchases, maxSize);
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public StockPurchase buyStock(String stockTicker, int quantity) {
        if (null == stockTicker || quantity <= 0 || size == maxSize) {
            return null;
        }

        return switch (stockTicker) {
            case MicrosoftStockPurchase.STOCK_TICKER -> attemptPurchase(
                    new MicrosoftStockPurchase(quantity, LocalDateTime.now(), priceChart.getCurrentPrice(stockTicker)));
            case AmazonStockPurchase.STOCK_TICKER -> attemptPurchase(
                    new AmazonStockPurchase(quantity, LocalDateTime.now(), priceChart.getCurrentPrice(stockTicker)));
            case GoogleStockPurchase.STOCK_TICKER -> attemptPurchase(
                    new GoogleStockPurchase(quantity, LocalDateTime.now(), priceChart.getCurrentPrice(stockTicker)));

            default -> null;
        };
    }

    private StockPurchase attemptPurchase(StockPurchase pendingPurchase) {
        double totalPendingPurchasePrice = pendingPurchase.getTotalPurchasePrice();
        if (budget < totalPendingPurchasePrice) {
            return null;
        }

        stockPurchases[size++] = pendingPurchase;
        budget -= totalPendingPurchasePrice;

        priceChart.changeStockPrice(pendingPurchase.getStockTicker(), FIXED_STOCK_PRICE_INCREASE_PERCENTAGE);

        return pendingPurchase;
    }

    @Override
    public StockPurchase[] getAllPurchases() {
        return Arrays.copyOf(stockPurchases, size);
    }

    @Override
    public StockPurchase[] getAllPurchases(LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
        int purchasesInTimeRangeCount = countPurchasesInTimeRange(startTimestamp, endTimestamp);
        StockPurchase[] newStockPurchases = new StockPurchase[purchasesInTimeRangeCount];
        int newStockPurchasesSize = 0;

        for (int i = 0; i < size; i++) {
            StockPurchase stockPurchase = stockPurchases[i];
            LocalDateTime purchaseTimestamp = stockPurchase.getPurchaseTimestamp();
            if (isInTimeRange(purchaseTimestamp, startTimestamp, endTimestamp)) {
                newStockPurchases[newStockPurchasesSize++] = stockPurchase;
            }
        }

        return newStockPurchases;
    }

    private int countPurchasesInTimeRange(LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            StockPurchase stockPurchase = stockPurchases[i];
            LocalDateTime purchaseTimestamp = stockPurchase.getPurchaseTimestamp();
            if (isInTimeRange(purchaseTimestamp, startTimestamp, endTimestamp)) {
                ++count;
            }
        }

        return count;
    }

    private boolean isInTimeRange(LocalDateTime purchaseTimestamp, LocalDateTime startTimestamp,
            LocalDateTime endTimestamp) {
        return (purchaseTimestamp.isEqual(startTimestamp) || purchaseTimestamp.isAfter(startTimestamp)) && //
                (purchaseTimestamp.isBefore(endTimestamp) || purchaseTimestamp.isEqual(endTimestamp));
    }

    @Override
    public double getNetWorth() {
        double netWorth = 0;

        for (int i = 0; i < size; i++) {
            StockPurchase stockPurchase = stockPurchases[i];
            String stockTicker = stockPurchase.getStockTicker();
            int quantity = stockPurchase.getQuantity();
            double currentStockPrice = quantity * priceChart.getCurrentPrice(stockTicker);

            netWorth += currentStockPrice;
        }

        return DoubleRounder.round(netWorth);
    }

    @Override
    public double getRemainingBudget() {
        return DoubleRounder.round(budget);
    }

}
