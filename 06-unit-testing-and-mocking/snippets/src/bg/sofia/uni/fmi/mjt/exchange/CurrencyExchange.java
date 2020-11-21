package bg.sofia.uni.fmi.mjt.exchange;

public interface CurrencyExchange {

    double getExchangeRate(Currency from, Currency to);

}
