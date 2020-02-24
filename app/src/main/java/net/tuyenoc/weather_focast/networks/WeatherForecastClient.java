package net.tuyenoc.weather_focast.networks;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherForecastClient {
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String BASE_URL_SEARCH = "https://api.accuweather.com/locations/v1/cities/";
    //public static final String BASE_URL_SEARCH = "http://dataservice.accuweather.com/locations/v1/cities/";
    public static final String API_KEY = "35816eb5ebf1e6979aba5743806b7ab8";
    public static final String API_KEY_SEARCH = "srRLeAmTroxPinDG8Aus3Ikl6tLGJd94";
    //public static final String API_KEY_SEARCH = "8pHc3erGFfsJ8uQcr28Jfe36X4AYZ7XA";
    //public static final String API_KEY_SEARCH = "Q24IgjqyzrHziA6Ks9AvMUuHGwqM3w0l";
    private static WeatherForecastClient instance;
    private Retrofit retrofit;

    private WeatherForecastClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized WeatherForecastClient getInstance() {
        if (instance == null) {
            instance = new WeatherForecastClient();
        }
        return instance;
    }

    public WeatherForecastApi createRequest() {
        return retrofit.create(WeatherForecastApi.class);
    }
}
