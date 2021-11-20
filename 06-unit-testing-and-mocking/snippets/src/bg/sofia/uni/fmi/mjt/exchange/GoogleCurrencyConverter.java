package bg.sofia.uni.fmi.mjt.exchange;

import java.net.URL;

public class GoogleCurrencyConverter implements CurrencyConverter {

    public GoogleCurrencyConverter(URL webServiceEndpoint, String user, String password) {
        // some complex logic here
    }

    @Override
    public double getExchangeRate(Currency from, Currency to) {
        return 0.0;
    }

}
