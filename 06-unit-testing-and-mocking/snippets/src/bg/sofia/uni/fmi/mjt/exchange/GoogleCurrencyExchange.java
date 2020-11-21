package bg.sofia.uni.fmi.mjt.exchange;

import java.net.URL;

public class GoogleCurrencyExchange implements CurrencyExchange {

    public GoogleCurrencyExchange(URL webServiceEndPoint, String user, String password) {
        // some complex init logic
    }

    @Override
    public double getExchangeRate(Currency from, Currency to) {
        return 0;
    }

}
