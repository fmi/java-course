package bg.sofia.uni.fmi.mjt.weather;

import bg.sofia.uni.fmi.mjt.weather.dto.WeatherForecast;
import bg.sofia.uni.fmi.mjt.weather.exceptions.LocationNotFoundException;
import bg.sofia.uni.fmi.mjt.weather.exceptions.WeatherForecastClientException;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherForecastClient {

    // register at https://home.openweathermap.org/users/sign_up to get your API key
    private static final String API_KEY = "paste-your-API-key-here";

    private static final String API_ENDPOINT_SCHEME = "http";
    private static final String API_ENDPOINT_HOST = "api.openweathermap.org";
    private static final String API_ENDPOINT_PATH = "/data/2.5/weather";
    private static final String API_ENDPOINT_QUERY = "q=%s&units=metric&lang=bg&appid=%s";
    private static final Gson GSON = new Gson();

    private final HttpClient weatherHttpClient;
    private final String apiKey;

    public WeatherForecastClient(HttpClient weatherHttpClient) {
        this(weatherHttpClient, API_KEY);
    }

    public WeatherForecastClient(HttpClient weatherHttpClient, String apiKey) {
        this.weatherHttpClient = weatherHttpClient;
        this.apiKey = apiKey;
    }

    /**
     * Fetches the weather forecast for the specified city.
     *
     * @return the forecast
     */
    public WeatherForecast getForecast(String city) throws WeatherForecastClientException {
        HttpResponse<String> response;

        try {
            URI uri = new URI(API_ENDPOINT_SCHEME, API_ENDPOINT_HOST, API_ENDPOINT_PATH,
                    API_ENDPOINT_QUERY.formatted(city, apiKey), null);
            HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

            response = weatherHttpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new WeatherForecastClientException("Could not retrieve weather forecast", e);
        }

        if (response.statusCode() == HttpURLConnection.HTTP_OK) {
            return GSON.fromJson(response.body(), WeatherForecast.class);
        }

        if (response.statusCode() == HttpURLConnection.HTTP_NOT_FOUND) {
            throw new LocationNotFoundException("Could not find " + city);
        }

        throw new WeatherForecastClientException("Unexpected response code from weather forecast service");
    }

}
