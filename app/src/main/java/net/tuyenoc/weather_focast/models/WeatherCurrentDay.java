package net.tuyenoc.weather_focast.models;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherCurrentDay {
    private LatLng latLng;
    private String name, country, description, icon;
    private double temp, pressure;
    private int humidity;
    private double wind;
    private int cloud;
    private long timestamp, sunrise, sunset;

    public WeatherCurrentDay(LatLng latLng, String name, String country, String description, String icon, double temp, double pressure, int humidity, double wind, int cloud, long timestamp, long sunrise, long sunset) {
        this.latLng = latLng;
        this.name = name;
        this.country = country;
        this.description = description;
        this.icon = icon;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.wind = wind;
        this.cloud = cloud;
        this.timestamp = timestamp;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getNameAndCountry() {
        if (country.equals("VN") || country.equals("Vietnam"))
            return name + ", " + "Việt Nam";
        return name + ", " + country;
    }

    public String getDescription() {
        StringBuilder builder = new StringBuilder(description);
        String firstCharacter = String.valueOf(builder.charAt(0)).toUpperCase();
        builder.setCharAt(0, firstCharacter.charAt(0));
        return builder.toString();
    }

    public String getIcon() {
        return "https://openweathermap.org/img/wn/" + icon + "@2x.png";
    }

    public long getTemp() {
        return Math.round(temp);
    }

    public String getPressure() {
        return pressure + "hPa";
    }

    public String getHumidity() {
        return humidity + "%";
    }

    public String getWind() {
        return wind + "m/s";
    }

    public String getCloud() {
        return cloud + "%";
    }

    public String getTimeUpdate() {
        String timeUpdate = new SimpleDateFormat(" HH:mm - dd/MM/yyyy").format(new Date(timestamp * 1000L));
        return timeUpdate;
    }

    public String getSunriseAndSunset() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(" HH:mm");
        String timeSunrise = dateFormat.format(new Date(sunrise * 1000L));
        String timeSunset = dateFormat.format(new Date(sunset * 1000L));
        return timeSunrise + " /" + timeSunset;
    }
}
