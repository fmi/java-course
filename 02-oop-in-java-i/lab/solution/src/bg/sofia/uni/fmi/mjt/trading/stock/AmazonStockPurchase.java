package bg.sofia.uni.fmi.mjt.trading.stock;

import java.time.LocalDateTime;

public class AmazonStockPurchase extends BaseStockPurchase {

    public static final String STOCK_TICKER = "AMZ";

    public AmazonStockPurchase(int quantity, LocalDateTime purchaseDate, double purchasePricePerUnit) {
        super(STOCK_TICKER, quantity, purchaseDate, purchasePricePerUnit);
    }

}
