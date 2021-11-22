package bg.sofia.uni.fmi.mjt.investment.wallet.quote;

import bg.sofia.uni.fmi.mjt.investment.wallet.asset.Asset;

public interface QuoteService {

    /**
     * @param asset
     * @return the quote for the given asset. If there is no quote, return null.
     * @throws IllegalArgumentException if the asset is null
     */
    Quote getQuote(Asset asset);
}
