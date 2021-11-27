package bg.sofia.uni.fmi.mjt.exchange;

public class CurrencyExchange {

    private CurrencyConverter converter;

    public CurrencyExchange(CurrencyConverter converter) {
        this.converter = converter;
    }

    public double exchangeSum(Currency from, Currency to, double fromAmount) throws UnknownCurrencyException {
        return fromAmount * converter.getExchangeRate(from, to);
    }

}
