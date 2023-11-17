package bg.sofia.uni.fmi.mjt.trading.stock;

import java.time.LocalDateTime;

public class MicrosoftStockPurchase extends BaseStockPurchase {

    public static final String STOCK_TICKER = "MSFT";

    public MicrosoftStockPurchase(int quantity, LocalDateTime purchaseDate, double purchasePricePerUnit) {
        super(STOCK_TICKER, quantity, purchaseDate, purchasePricePerUnit);
    }

}
