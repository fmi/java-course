package bg.sofia.uni.fmi.mjt.exchange;

public interface CurrencyConverter {

    double getExchangeRate(Currency from, Currency to) throws UnknownCurrencyException;

}
