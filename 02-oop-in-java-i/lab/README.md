# Обектно-ориентирано програмиране с Java (част I)

## Trading121 :euro:

В последните години, световната икономика претърпява удар след удар. Инфлацията започва да изяжда спестяванията ни, но ние имаме страхотна идея - ще имплементираме платформа, която ще ни позволи да търгуваме с акции, подобна на [Trading212](https://www.trading212.com).

### `Portfolio`

Класът `Portfolio` в пакета `bg.sofia.uni.fmi.mjt.trading121` представлява инвестиционен портфейл - съвкупността от различни активи (в нашата платформа - засега само акции), в които собственикът на портфейла е инвестирал към даден момент.

В този клас имплементирайте 2 публични конструктора:
* `Portfolio(String owner, PriceChartAPI priceChart, double budget, int maxSize)` - когато до момента няма покупки на акции
* `Portfolio(String owner, PriceChartAPI priceChart, StockPurchase[] stockPurchases, double budget, int maxSize)` - когато вече има реализирани `stockPurchases` покупки на акции
  
Класът `Portfolio` трябва да имплементира следния интерфейс:

```java
package bg.sofia.uni.fmi.mjt.trading121;

import bg.sofia.uni.fmi.mjt.trading121.stock.StockPurchase;

import java.time.LocalDateTime;

public interface PortfolioAPI {

    /**
     * Purchases the provided quantity of stocks with the provided ticker. The budget in the portfolio should
     * decrease by the corresponding amount. If a stock is on-demand then naturally its price increases.
     * Every stock purchase should result in a 5% price increase of the purchased stock
     *
     * @param stockTicker the stock ticker
     * @param quantity    the quantity of stock that should be purchased
     * @return the stock purchase if it was successfully purchased. If the stock with the provided ticker is
     * not traded on the platform or the ticker is null, return null. If the budget is not enough to make the
     * purchase, return null. If quantity is not a positive number, return null. If the portfolio is already
     * at max size, return null.
     */
    StockPurchase buyStock(String stockTicker, int quantity);

    /**
     * @return all stock purchases made so far.
     */
    StockPurchase[] getAllPurchases();

    /**
     * Retrieves purchases made in the provided inclusive time interval
     *
     * @param startTimestamp the start timestamp of the interval
     * @param endTimestamp   the end timestamp of the interval
     * @return all stock purchases made so far in the provided time interval
     */
    StockPurchase[] getAllPurchases(LocalDateTime startTimestamp, LocalDateTime endTimestamp);

    /**
     * @return the current total net worth of the portfolio: the sum of each purchases' quantity multiplied by
     * the current price of the stock identified by that purchase rounded to two decimal places
     */
    double getNetWorth();

    /**
     * @return the remaining budget in the portfolio rounded to two decimal places
     */
    double getRemainingBudget();

    /**
     * @return the owner of the portfolio
     */
    String getOwner();
    
}
```

#### Забележка: Навсякъде в задачата, където се връща double, обозначаващ сума пари, закръгляме с точност до 2 знака след десетичната запетая, като при >=0.005 закръгляме нагоре

### Покупка на акции

Като млада платформа за търговия с активи, все още не можем да поддържаме обширен асортимент от акции. За момент допускаме търговия с акции само на Microsoft(ticker=MSFT), Amazon(ticker=AMZ) и Google(ticker=GOOG). Класовете, което символизират покупка на даден тип акции са съответно `MicrosoftStockPurchase`, `AmazonStockPurchase` и `GoogleStockPurchase` в пакета `bg.sofia.uni.fmi.mjt.trading121.stock`, като те имплеметират следния интерфейс:

```java
package bg.sofia.uni.fmi.mjt.trading121.stock;

import java.time.LocalDateTime;

public interface StockPurchase {

    /**
     * @return the quantity of stocks in this purchase
     */
    int getQuantity();

    /**
     * @return the timestamp when the purchase was made
     */
    LocalDateTime getPurchaseTimestamp();

    /**
     * @return the price per unit of stock at the time of purchase rounded to two decimal places
     */
    double getPurchasePricePerUnit();

    /**
     * Calculates the total price of the purchase given the quantity and the price per unit rounded
     * to two decimal places
     *
     * @return the multiplication result of the quantity by the price-per-unit
     */
    double getTotalPurchasePrice();

    /**
     * @return the ticker of the stock that was purchased
     */
    String getStockTicker();

}
```

И трите имплеметации на `StockPurchase` трябва да имат публичен конструктор с параметри `(String ticker, int quantity, LocalDateTime purchaseTimestamp, double purchasePricePerUnit)`.

### Цена на акции

Цената на акциите е динамична и се определя от търсенето. За тази цел обаче е необходимо да имаме механизъм за следене на текущата цена и нейната промяна, когато това се налага. В пакета `bg.sofia.uni.fmi.mjt.trading121.price` създайте клас `PriceChart` с публичен конструктор `PriceChart(double microsoftStockPrice, double googleStockPrice, double amazonStockPrice)`, който имплементира следния интерфейс:

```java
package bg.sofia.uni.fmi.mjt.trading121.price;

public interface PriceChartAPI {

    /**
     * Gets the current price of the stock identified by the provided stock ticker rounded to two decimal places
     *
     * @param stockTicker the stock ticker
     * @return current price of stock. If the stock with the provided ticker is not traded on the platform
     * or the ticker is null, return 0.0
     */
    double getCurrentPrice(String stockTicker);

    /**
     * Changes the current price of the stock identified by the provided stock ticker by the provided percentage.
     * As we are creating a thriving trading platform, the percentage can only be a positive number
     *
     * @param stockTicker   the ticker of the stock for which the price is changing
     * @param percentChange positive number denoting the percentage increase of stock price
     * @return true, if the price was increased successfully. If the stock with the provided ticker is not traded
     * on the platform or the ticker is null, return false. If the provided percentChange is not a positive
     * number, return false.
     */
    boolean changeStockPrice(String stockTicker, int percentChange);

}
```

### Подсказка

:point_right: За работа с дати и часове, може да използвате [`java.time`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/time/package-summary.html) API, обръщайки по-специално внимание на [`LocalDateTime`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/time/LocalDateTime.html) класа и неговите методи.

### Пакети

Спазвайте имената на пакетите на всички по-горе описани класове и интерфейси, тъй като в противен случай, решението ви няма да може да бъде автоматично тествано.

```bash
src
└─ bg.sofia.uni.fmi.mjt.trading121
    ├─── price
    │       ├─ PriceChart.java
    │       ├─ PriceChartAPI.java
    │       └─ (...)
    ├─── stock
    │       ├─ AmazonStockPurchase.java
    │       ├─ GoogleStockPurchase.java
    │       ├─ MicrosoftStockPurchase.java
    │       ├─ StockPurchase.java
    │       └─ (...)
    ├─ Portfolio.java
    ├─ PortfolioAPI.java
    └─ (...)
```

### :warning: Забележки

- Използването на структури от данни, различни от масив, **не е позволено**. Задачата трябва да се реши с помощта на знанията от курса до момента и допълнителните Java APIs, указани в условието.
