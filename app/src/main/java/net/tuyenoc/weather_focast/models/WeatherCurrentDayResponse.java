package net.tuyenoc.weather_focast.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

class CoordinatesCur {
    public double lat, lon;
}

class WeatherCur {
    public String description, icon;
}

class MainCur {
    public double temp, pressure;
    public int humidity;
}

class WindCur {
    public double speed;
}

class CloudCur {
    public int all;
}

class SysCur {
    public String country;
    public long sunrise, sunset;
}

public class WeatherCurrentDayResponse {
    private CoordinatesCur coord;
    private List<WeatherCur> weather;
    private MainCur main;
    private WindCur wind;
    private CloudCur clouds;
    private long dt;
    private SysCur sys;
    private String name;

    public WeatherCurrentDay getWeatherCurrentDay() {
        return new WeatherCurrentDay(
                new LatLng(coord.lat, coord.lon),
                name,
                sys.country,
                weather.get(0).description,
                weather.get(0).icon,
                main.temp,
                main.pressure,
                main.humidity,
                wind.speed,
                clouds.all,
                dt,
                sys.sunrise,
                sys.sunset
        );
    }
}
