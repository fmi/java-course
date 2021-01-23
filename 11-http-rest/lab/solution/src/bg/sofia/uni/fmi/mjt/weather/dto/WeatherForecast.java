package bg.sofia.uni.fmi.mjt.weather.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Objects;

public class WeatherForecast {

    @SerializedName("weather")
    private WeatherCondition[] weatherCondition;
    @SerializedName("main")
    private WeatherData weatherData;

    public WeatherForecast(WeatherCondition[] weatherCondition, WeatherData weatherData) {
        this.weatherCondition = weatherCondition;
        this.weatherData = weatherData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WeatherForecast)) {
            return false;
        }
        WeatherForecast that = (WeatherForecast) o;
        return Arrays.equals(weatherCondition, that.weatherCondition)
                && Objects.equals(weatherData, that.weatherData);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(weatherData);
        result = 31 * result + Arrays.hashCode(weatherCondition);
        return result;
    }

}
