package bg.sofia.uni.fmi.mjt.investment.wallet.acquisition;

import bg.sofia.uni.fmi.mjt.investment.wallet.asset.Asset;

import java.time.LocalDateTime;

public class DefaultAcquisition implements Acquisition {

    private final Asset asset;
    private final double price;
    private final LocalDateTime timestamp;
    private final int quantity;

    public DefaultAcquisition(Asset asset, double price, int quantity) {
        this.asset = asset;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public Asset getAsset() {
        return asset;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }
}
