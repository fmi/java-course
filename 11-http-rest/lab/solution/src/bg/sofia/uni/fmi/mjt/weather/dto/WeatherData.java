package bg.sofia.uni.fmi.mjt.weather.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class WeatherData {

    @SerializedName("temp")
    private double temperature;
    @SerializedName("feels_like")
    private double feelsLike;

    public WeatherData(double temperature, double feelsLike) {
        this.temperature = temperature;
        this.feelsLike = feelsLike;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WeatherData)) {
            return false;
        }
        WeatherData that = (WeatherData) o;
        return Double.compare(that.temperature, temperature) == 0
                && Double.compare(that.feelsLike, feelsLike) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, feelsLike);
    }

}
