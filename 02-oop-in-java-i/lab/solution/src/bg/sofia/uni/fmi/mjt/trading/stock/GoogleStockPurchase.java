package bg.sofia.uni.fmi.mjt.trading.stock;

import java.time.LocalDateTime;

public class GoogleStockPurchase extends BaseStockPurchase {

    public static final String STOCK_TICKER = "GOOG";

    public GoogleStockPurchase(int quantity, LocalDateTime purchaseDate, double purchasePricePerUnit) {
        super(STOCK_TICKER, quantity, purchaseDate, purchasePricePerUnit);
    }

}
