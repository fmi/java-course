package bg.sofia.uni.fmi.mjt.investment.wallet.asset;

public interface Asset {

    /**
     * @return the id of the asset
     */
    String getId();

    /**
     * @return the name of the asset
     */
    String getName();

    /**
     * @return the type of the asset
     */
    AssetType getType();
}
