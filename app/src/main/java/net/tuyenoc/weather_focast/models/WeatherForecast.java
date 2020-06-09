package net.tuyenoc.weather_focast.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherForecast {
    private long timestamp;
    private double tempMin, tempMax;
    private String description, icon;

    public WeatherForecast(long timestamp, double tempMin, double tempMax, String description, String icon) {
        this.timestamp = timestamp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.description = description;
        this.icon = icon;
    }

    public String getTimestamp() {
        String time = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(timestamp * 1000L));
        int dayOfWeek = Integer.parseInt(new SimpleDateFormat("u").format(new Date(timestamp * 1000L)));
        switch (dayOfWeek) {
            case 1:
                return "Th2, " + time;
            case 2:
                return "Th3, " + time;
            case 3:
                return "Th4, " + time;
            case 4:
                return "Th5, " + time;
            case 5:
                return "Th6, " + time;
            case 6:
                return "Th7, " + time;
            default:
                return "CN, " + time;
        }
    }

    public long getTempMin() {
        return Math.round(tempMin);
    }

    public long getTempMax() {
        return Math.round(tempMax);
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
}
