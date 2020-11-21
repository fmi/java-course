package bg.sofia.uni.fmi.mjt.exchange;

public class Bank {

    private CurrencyExchange currencyExchange;

    public Bank(CurrencyExchange currencyExchange) {
        this.currencyExchange = currencyExchange;
    }

    public double convertSum(Currency from, Currency to, double sum) {
        return sum * currencyExchange.getExchangeRate(from, to);
    }

}
