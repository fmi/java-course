package bg.sofia.uni.fmi.mjt.investment.wallet;

import bg.sofia.uni.fmi.mjt.investment.wallet.acquisition.Acquisition;
import bg.sofia.uni.fmi.mjt.investment.wallet.acquisition.DefaultAcquisition;
import bg.sofia.uni.fmi.mjt.investment.wallet.asset.Asset;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.InsufficientResourcesException;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.OfferPriceException;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.UnknownAssetException;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.WalletException;
import bg.sofia.uni.fmi.mjt.investment.wallet.quote.Quote;
import bg.sofia.uni.fmi.mjt.investment.wallet.quote.QuoteService;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InvestmentWallet implements Wallet {

    private static final String QUANTITY_PARAM_NAME = "Quantity";
    private static final String MAX_PRICE_PARAM_NAME = "Max price";
    private static final String MIN_PRICE_PARAM_NAME = "Min price";
    private static final String N_PARAM_NAME = "N";
    private static final String CASH_PARAM_NAME = "Cash";
    private static final String ASSET_PARAM_NAME = "Asset";

    private final List<Acquisition> acquisitions;
    private final Map<Asset, Integer> assetQuantities;
    private final QuoteService quoteService;

    private double balance;

    InvestmentWallet(QuoteService quoteService) {
        this.acquisitions = new LinkedList<>();
        this.assetQuantities = new HashMap<>();
        this.quoteService = quoteService;
        this.balance = 0;
    }

    @Override
    public double deposit(double cash) {
        assertNonNegative(cash, CASH_PARAM_NAME);

        return balance += cash;
    }

    @Override
    public double withdraw(double cash) throws InsufficientResourcesException {
        assertNonNegative(cash, CASH_PARAM_NAME);

        if (balance < cash) {
            throw new InsufficientResourcesException("Cannot withdraw " + cash + ". Current balance is " + balance);
        }

        return balance -= cash;
    }

    @Override
    public Acquisition buy(Asset asset, int quantity, double maxPrice) throws WalletException {
        assertNonNegative(quantity, QUANTITY_PARAM_NAME);
        assertNonNegative(maxPrice, MAX_PRICE_PARAM_NAME);

        Quote quote = quoteService.getQuote(asset);
        if (quote == null) {
            throw new UnknownAssetException("Quota for asset with id " + asset.getId() + " is unavailable");
        }

        double askPrice = quote.askPrice();
        if (askPrice > maxPrice) {
            throw new OfferPriceException(
                "Cannot buy at price " + maxPrice + ". Minimum price per unit is " + askPrice);
        }

        double bill = askPrice * quantity;
        if (bill > balance) {
            throw new InsufficientResourcesException(
                "Cannot buy asset with id " + asset.getId() + " for " + bill + ". Current balance is " + balance);
        }

        balance -= bill;

        Integer currentQuantity = assetQuantities.get(asset);
        currentQuantity = (currentQuantity != null) ? currentQuantity : 0;
        assetQuantities.put(asset, currentQuantity + quantity);

        Acquisition acquisition = new DefaultAcquisition(asset, askPrice, quantity);
        acquisitions.add(acquisition);

        return acquisition;
    }

    @Override
    public double sell(Asset asset, int quantity, double minPrice) throws WalletException {
        assertNonNull(asset, ASSET_PARAM_NAME);
        assertNonNegative(quantity, QUANTITY_PARAM_NAME);
        assertNonNegative(minPrice, MIN_PRICE_PARAM_NAME);

        Integer currentQuantity = assetQuantities.get(asset);
        currentQuantity = (currentQuantity != null) ? currentQuantity : 0;
        if (currentQuantity < quantity) {
            throw new InsufficientResourcesException(
                "Not enough quantity to sell. Current quantity is " + currentQuantity);
        }

        Quote quote = quoteService.getQuote(asset);
        if (quote == null) {
            throw new UnknownAssetException("Quote for asset with id " + asset.getId() + " is unavailable");
        }

        double bidPrice = quote.bidPrice();
        if (bidPrice < minPrice) {
            throw new OfferPriceException(
                "Cannot sell at price " + minPrice + ". Maximum price per unit is " + bidPrice);
        }

        double bill = bidPrice * quantity;
        balance += bill;
        if (currentQuantity - quantity == 0) {
            assetQuantities.remove(asset);
        } else {
            assetQuantities.put(asset, currentQuantity - quantity);
        }

        return bill;
    }

    @Override
    public double getValuation() {
        double valuation = 0;
        for (Asset asset : assetQuantities.keySet()) {
            double bidPrice = quoteService.getQuote(asset).bidPrice();
            valuation += getValuation(asset, bidPrice);
        }

        return valuation;
    }

    @Override
    public double getValuation(Asset asset) throws UnknownAssetException {
        assertNonNull(asset, ASSET_PARAM_NAME);
        if (!assetQuantities.containsKey(asset)) {
            throw new UnknownAssetException("You don't have asset with id " + asset.getId() + ".");
        }

        Quote quote = quoteService.getQuote(asset);
        if (quote == null) {
            throw new UnknownAssetException("Quota for asset with id " + asset.getId() + " is unavailable");
        }

        return getValuation(asset, quote.bidPrice());
    }

    private double getValuation(Asset asset, double bidPrice) {
        return bidPrice * assetQuantities.get(asset);
    }

    @Override
    public Asset getMostValuableAsset() {
        return Collections.max(assetQuantities.keySet(), new Comparator<Asset>() {
            @Override
            public int compare(Asset o1, Asset o2) {
                double bidPrice1 = quoteService.getQuote(o1).bidPrice();
                double value1 = getValuation(o1, bidPrice1);

                double bidPrice2 = quoteService.getQuote(o2).bidPrice();
                double value2 = getValuation(o2, bidPrice2);

                return Double.compare(value1, value2);
            }
        });
    }

    @Override
    public Collection<Acquisition> getAllAcquisitions() {
        return List.copyOf(acquisitions);
    }

    @Override
    public Set<Acquisition> getLastNAcquisitions(int n) {
        assertNonNegative(n, N_PARAM_NAME);

        int size = acquisitions.size();
        if (n >= size) {
            return Set.copyOf(acquisitions);
        }
        return Set.copyOf(acquisitions.subList(size - n, size));
    }

    private void assertNonNull(Object object, String paramName) {
        if (object == null) {
            throw new IllegalArgumentException(paramName + " should not be null");
        }
    }

    private void assertNonNegative(double param, String paramName) {
        if (param < 0) {
            throw new IllegalArgumentException(paramName + " should not be negative");
        }
    }
}
