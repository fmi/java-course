package bg.sofia.uni.fmi.mjt.weather.exceptions;

public class LocationNotFoundException extends WeatherForecastClientException {

    public LocationNotFoundException(String message) {
        super(message);
    }

}
