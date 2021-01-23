package bg.sofia.uni.fmi.mjt.weather;

import bg.sofia.uni.fmi.mjt.weather.dto.WeatherCondition;
import bg.sofia.uni.fmi.mjt.weather.dto.WeatherData;
import bg.sofia.uni.fmi.mjt.weather.dto.WeatherForecast;
import bg.sofia.uni.fmi.mjt.weather.exceptions.LocationNotFoundException;
import bg.sofia.uni.fmi.mjt.weather.exceptions.WeatherForecastClientException;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherForecastClientReferenceTest {

    private static WeatherForecast weatherForecastSofia;
    private static String weatherForecastSofiaJson;

    @Mock
    private HttpClient weatherHttpClientMock;

    @Mock
    private HttpResponse<String> httpWeatherResponseMock;

    private WeatherForecastClient client;

    @BeforeClass
    public static void setUpClass() {
        WeatherCondition weatherCondition = new WeatherCondition("rainy");
        WeatherData weatherData = new WeatherData(3.01, -2.63);
        weatherForecastSofia = new WeatherForecast(new WeatherCondition[] {weatherCondition}, weatherData);
        weatherForecastSofiaJson = new Gson().toJson(weatherForecastSofia);
    }

    @Before
    public void setUp() throws IOException, InterruptedException {
        when(weatherHttpClientMock.send(Mockito.any(HttpRequest.class), ArgumentMatchers.<BodyHandler<String>>any()))
                .thenReturn(httpWeatherResponseMock);

        client = new WeatherForecastClient(weatherHttpClientMock);
    }

    @Test
    public void testGetForecastValidCity() throws WeatherForecastClientException {
        when(httpWeatherResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(httpWeatherResponseMock.body()).thenReturn(weatherForecastSofiaJson);

        var result = client.getForecast("Sofia");

        assertEquals("Incorrect weather forecast for valid location", weatherForecastSofia, result);
    }

    @Test(expected = LocationNotFoundException.class)
    public void testGetForecastNonExistentCity() throws Exception {
        when(httpWeatherResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_NOT_FOUND);
        client.getForecast("Some unknown place");
    }

    @Test
    public void testGetForecastServerError() throws Exception {
        when(httpWeatherResponseMock.statusCode()).thenReturn(HttpURLConnection.HTTP_UNAVAILABLE);

        try {
            client.getForecast("Sofia");
        } catch (Exception e) {
            assertEquals(
                    "WeatherForecastClientException should be thrown if information regarding the weather for this location could not be retrieved",
                    WeatherForecastClientException.class, e.getClass());
            assertNotEquals(
                    "Improper use of LocationNotFoundException - it should be thrown only if the location is not found",
                    LocationNotFoundException.class, e.getClass());
        }
    }

    @Test
    public void testGetForecastHttpClientIOExceptionIsWrapped() throws Exception {
        IOException expectedExc = new IOException();
        when(weatherHttpClientMock.send(Mockito.any(HttpRequest.class), ArgumentMatchers.<BodyHandler<String>>any()))
                .thenThrow(expectedExc);

        try {
            client.getForecast("Sofia");
        } catch (Exception actualExc) {
            assertEquals(
                    "WeatherForecastClientException should properly wrap (i.e. link) the causing IOException or InterruptedException",
                    expectedExc, actualExc.getCause());
        }
    }

}
