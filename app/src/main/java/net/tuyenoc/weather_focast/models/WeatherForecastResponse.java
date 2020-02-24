package net.tuyenoc.weather_focast.models;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

class WeatherFor {
    public String description, icon;
}

class MainFor {
    public double temp_min, temp_max;
}

class ItemWeatherFor {
    public long dt;
    public MainFor main;
    public List<WeatherFor> weather;
}

public class WeatherForecastResponse {
    private JsonElement list;

    public List<WeatherForecast> getWeatherForecasts() {
        List<WeatherForecast> weatherForecasts = new ArrayList<>();
        ItemWeatherFor[] itemWeatherFors = new Gson().fromJson(list, ItemWeatherFor[].class);
        for (ItemWeatherFor item : itemWeatherFors) {
            weatherForecasts.add(new WeatherForecast(
                    item.dt,
                    item.main.temp_min,
                    item.main.temp_max,
                    item.weather.get(0).description,
                    item.weather.get(0).icon
            ));
        }
        return weatherForecasts;
    }
}
