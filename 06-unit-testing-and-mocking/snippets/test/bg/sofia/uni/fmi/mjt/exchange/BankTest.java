package bg.sofia.uni.fmi.mjt.exchange;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class BankTest {

    @Mock
    private CurrencyExchange currencyExchangeMock;

    @InjectMocks
    private Bank bank;

    @Test
    public void testConvertSumEURtoUSD() {
/*        CurrencyExchange currencyExchangeMock = mock(CurrencyExchange.class);
        bank = new Bank(currencyExchangeMock);*/

        when(currencyExchangeMock.getExchangeRate(Currency.EUR, Currency.USD))
                .thenReturn(1.11);

        assertEquals("Conversion from EUR to USD should work properly.", 11.1, bank.convertSum(Currency.EUR, Currency.USD, 10), 0.01);
    }

    @Test
    public void testConvertSumBGNtoEUR() {
/*        CurrencyExchange currencyExchangeMock = mock(CurrencyExchange.class);
        bank = new Bank(currencyExchangeMock);*/

        when(currencyExchangeMock.getExchangeRate(Currency.BGN, Currency.EUR))
                .thenReturn(0.51);

        assertEquals("Conversion from BGN to EUR should work properly.", 5.1, bank.convertSum(Currency.BGN, Currency.EUR, 10), 0.01);

        verify(currencyExchangeMock, times(1)).getExchangeRate(Currency.BGN, Currency.EUR);
    }

}
