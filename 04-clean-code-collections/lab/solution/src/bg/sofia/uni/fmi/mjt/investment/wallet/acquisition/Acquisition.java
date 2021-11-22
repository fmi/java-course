package bg.sofia.uni.fmi.mjt.investment.wallet.acquisition;

import bg.sofia.uni.fmi.mjt.investment.wallet.asset.Asset;

import java.time.LocalDateTime;

public interface Acquisition {

    /**
     * @return the price per unit paid for the asset
     */
    double getPrice();

    /**
     * Timestamp is the time the acquisition has been created
     *
     * @return the timestamp of acquisition
     */
    LocalDateTime getTimestamp();

    /**
     * @return the number of asset units acquired
     */
    int getQuantity();

    /**
     * @return the asset which was acquired
     */
    Asset getAsset();
}
