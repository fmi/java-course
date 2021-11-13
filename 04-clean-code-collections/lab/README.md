# Investment Wallet :moneybag: :euro: ₿

Всеки иска да умножи парите си. Днес с инвестиции се занимават далеч не само професионалните инвестиционни брокери. Наред с класическите активи като ценни книжа и метали, нараства популярността и на криптовалутите. Ще създадем приложение, с което ще управляваме свой *инвестиционен портфейл*.

### Инвестиционен портфейл

В пакета `bg.sofia.uni.fmi.mjt.investment.wallet` създайте клас `InvestmentWallet`, който има конструктор

```java
public InvestmentWallet(QuoteService quoteService)
```
и имплементира интерфейса `Wallet`:

```java
package bg.sofia.uni.fmi.mjt.investment.wallet;

import java.util.Collection;
import java.util.Set;

import bg.sofia.uni.fmi.mjt.investment.wallet.acquisition.Acquisition;
import bg.sofia.uni.fmi.mjt.investment.wallet.asset.Asset;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.InsufficientResourcesException;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.OfferPriceException;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.UnknownAssetException;
import bg.sofia.uni.fmi.mjt.investment.wallet.exception.WalletException;

public interface Wallet {

    /**
     *
     * @param cash the cash to deposit in the investment wallet
     * @return the cash balance in the wallet after the transaction
     * @throws IllegalArgumentException if the @cash is negative
     */
    double deposit(double cash);

    /**
     *
     * @param cash the cash to withdraw from the investment wallet
     * @return the cash balance in the wallet after the transaction
     * @throws IllegalArgumentException       if the @cash is negative
     * @throws InsufficientResourcesException if the cash balance is insufficient to
     *                                        proceed with the withdrawal
     */
    double withdraw(double cash) throws InsufficientResourcesException;

    /**
     *
     * @param asset    the asset to buy
     * @param quantity the quantity to buy
     * @param maxPrice the maxPrice to pay for a unit of @asset
     * @return the acquisition of the @asset
     *
     * @throws IllegalArgumentException when:
     * @quantity is negative
     * @maxPrice is negative
     * @asset is null
     *
     * @throws UnknownAssetException          if there is no defined quote for
     *                                        the @asset
     * @throws OfferPriceException            if the ask price of the @asset is
     *                                        higher than the @maxPrice
     * @throws InsufficientResourcesException if there is not enough balance for the
     *                                        transaction
     */
    Acquisition buy(Asset asset, int quantity, double maxPrice) throws WalletException;

    /**
     *
     * @param asset    the asset to sell
     * @param quantity the quantity to sell
     * @param minPrice the minPrice to sell a unit of @asset for
     * @return the cash added to the balance after the sell
     * @throws IllegalArgumentException when:
     * @asset is null
     * @quantity is negative
     * @minPrice is negative
     * @throws InsufficientResourcesException if there is not enough quantity of
     *                                        the @asset
     * @throws UnknownAssetException          if there is no defined quote for
     *                                        the @asset
     * @OfferPriceException if the bid price of the @asset is lower than
     *                      the @minPrice
     */
    double sell(Asset asset, int quantity, double minPrice) throws WalletException;

    /**
     *
     * @return the valuations of all of the assets in the wallet combined
     */
    double getValuation();

    /**
     * Valuation is the current market price of an asset.
     *
     * Valuation of a specific @asset in the investment wallet is the current bid
     * price of the @asset multiplied by the quantity of the @asset in the wallet
     *
     * @param asset
     * @return the valuation of the @asset
     * @throws IllegalArgumentException if @asset is null
     * @throws UnknownAssetException    if @asset is not currently in the wallet
     * @throws UnknownAssetException    if there is no defined quote for the @asset
     */
    double getValuation(Asset asset) throws UnknownAssetException;

    /**
     * @return the asset with the highest valuation
     */
    Asset getMostValuableAsset();

    /**
     * @return all acquisitions in the wallet as an unmodifiable copy
     */
    Collection<Acquisition> getAllAcquisitions();

    /**
     *
     * @param n the maximum number of acquisitions to return
     * @return an unmodifiable copy of the last @n acquisitions in the wallet. If
     *         there are less than @n acquisitions in the wallet, return all of
     *         them. The order of the acquisitions in the returned set is undefined
     * @throws IllegalArgumentException when @n is negative
     */
    Set<Acquisition> getLastNAcquisitions(int n);
}

```

> Забележкa: `WalletException` е базов клас за `InsufficientResourcesException`, `OfferPriceException` и `UnknownAssetException`.

### Активи

Активите биват четири вида: криптовалути (`Crypto`), обикновени валути (`Fiat`), злато (`Gold`) и акции (`Stock`), типът им се описва чрез `AssetType`:

```java
public enum AssetType {
    CRYPTO, FIAT, GOLD, STOCK
}
```

имат публичен конструктор с параметри `(String id, String name)` и имплементират интерфейса `Asset`:

```java
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
```

### Придобивания на активи

Придобиването на даден актив се моделира от клас, имплементиращ интерфейса `Acquisition`:

```java
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
```

### Котировки

Текущите "цена продава" (*ask price*) и "цена купува" (*bid price*) на всеки актив се моделират от `record`-a `Quote`:

```java
public record Quote(double askPrice, double bidPrice) { }
```

 и се достъпват чрез интерфейса `QuoteService`:

```java
public interface QuoteService {

    /**
     * @param asset
     * @return the quote for the given asset. If there is no quote, return null.
     * @throws IllegalArgumentException if the asset is null
     */
    Quote getQuote(Asset asset);
}

```

### Пакети

Спазвайте имената на пакетите на всички по-горе описани типове, тъй като в противен случай, решението ви няма да може да бъде автоматично тествано.

```
src
└── bg.sofia.uni.fmi.mjt.investment.wallet
    ├── acquisition
    │      ├── Acquisition.java
    │      └── (...)
    ├── asset
    │      ├── Asset.java
    │      ├── AssetType.java
    │      ├── Crypto.java
    │      ├── Fiat.java
    │      ├── Gold.java
    │      ├── Stock.java
    │      └── (...)
    ├── exception
    │      ├── InsufficientResourcesException.java
    │      ├── OfferPriceException.java
    │      ├── UnknownAssetException.java
    │      └── WalletException.java
    ├── quote
    │      ├── Quote.java
    │      ├── QuoteService.java
    │      └── (...)
    ├── InvestmentWallet.java
    └── Wallet.java
```

### :warning: Забележки

- Не променяйте по никакъв начин интерфейсите, дадени в условието.
