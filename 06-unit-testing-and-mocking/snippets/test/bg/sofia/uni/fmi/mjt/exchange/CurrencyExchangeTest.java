package bg.sofia.uni.fmi.mjt.exchange;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrencyExchangeTest {

    @Mock
    private CurrencyConverter currencyConverterMock;
    // the equivalent "manual" mock initialization would be:
    //private CurrencyConverter currencyConverterMock = Mockito.mock(CurrencyConverter.class);

    @InjectMocks
    private CurrencyExchange exchange;
    // the equivalent "manual" construction of a class with a mocked dependency would be:
    //private CurrencyExchange exchange = new CurrencyExchange(currencyConverterMock);

    @Test
    public void testCurrencyExchangeEURtoUSD() throws UnknownCurrencyException {

        when(currencyConverterMock.getExchangeRate(Currency.EUR, Currency.USD))
            .thenReturn(1.11);

        assertEquals(11.1, exchange.exchangeSum(Currency.EUR, Currency.USD, 10), 0.001,
            "Currency exchange of EUR to USD should work properly");

        verify(currencyConverterMock).getExchangeRate(Currency.EUR, Currency.USD);
        verify(currencyConverterMock, times(1)).getExchangeRate(Currency.EUR, Currency.USD);
        // uncomment the next line to play with mock verification failure
        //verify(currencyConverterMock).getExchangeRate(Currency.EUR, Currency.BGN);
    }

    @Test
    public void testCurrencyExchangeUnknownCurrencyThrowsException() throws UnknownCurrencyException {

        when(currencyConverterMock.getExchangeRate(Currency.BGN, Currency.USD))
            .thenThrow(new UnknownCurrencyException(
                String.format("Unknown currency pair (%s --> %s)", Currency.BGN, Currency.USD)));

        assertThrows(UnknownCurrencyException.class, () -> exchange.exchangeSum(Currency.BGN, Currency.USD, 10),
            "UnknownCurrencyException expected to be thrown when converting between currencies one of which is unknown");
    }

}
