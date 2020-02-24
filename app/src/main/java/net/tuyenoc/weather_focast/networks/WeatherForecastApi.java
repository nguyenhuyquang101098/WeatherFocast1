package net.tuyenoc.weather_focast.networks;

import net.tuyenoc.weather_focast.models.ItemSearch;
import net.tuyenoc.weather_focast.models.UVIndex;
import net.tuyenoc.weather_focast.models.WeatherCurrentDayResponse;
import net.tuyenoc.weather_focast.models.WeatherForecastResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherForecastApi {

    @GET("weather?units=metric&lang=vi")
    Call<WeatherCurrentDayResponse> fetchWeatherCurrentDayByLatLng(
            @Query("appid") String API_KEY,
            @Query("lat") double lat,
            @Query("lon") double lng
    );

    @GET("weather?units=metric&lang=vi")
    Call<WeatherCurrentDayResponse> fetchWeatherCurrentDayByPlaceName(
            @Query("appid") String API_KEY,
            @Query("q") String placeName
    );

    @GET("forecast?units=metric&lang=vi")
    Call<WeatherForecastResponse> fetchWeatherForecastByLatLng(
            @Query("appid") String API_KEY,
            @Query("lat") double lat,
            @Query("lon") double lng
    );

    @GET("forecast?units=metric&lang=vi")
    Call<WeatherForecastResponse> fetchWeatherForecastByPlaceName(
            @Query("appid") String API_KEY,
            @Query("q") String placeName
    );

    @GET("uvi")
    Call<UVIndex> fetchUVIndex(
            @Query("appid") String API_KEY,
            @Query("lat") double lat,
            @Query("lon") double lng
    );

    @GET("autocomplete")
    Call<List<ItemSearch>> fetchPlaces(
            @Query("apikey") String apiKey,
            @Query("q") String keyWord
    );
}
