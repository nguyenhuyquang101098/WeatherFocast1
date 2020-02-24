package net.tuyenoc.weather_focast.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;

import net.tuyenoc.weather_focast.models.Place;
import net.tuyenoc.weather_focast.models.UVIndex;
import net.tuyenoc.weather_focast.models.WeatherCurrentDay;
import net.tuyenoc.weather_focast.models.WeatherCurrentDayResponse;
import net.tuyenoc.weather_focast.models.WeatherForecast;
import net.tuyenoc.weather_focast.models.WeatherForecastResponse;
import net.tuyenoc.weather_focast.networks.WeatherForecastApi;
import net.tuyenoc.weather_focast.networks.WeatherForecastClient;
import net.tuyenoc.weather_focast.repositories.PlaceRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainViewModel extends AndroidViewModel {
    private static String TAG = "MainViewModel";
    public MutableLiveData<WeatherCurrentDay> weatherCurrentDay;
    public MutableLiveData<UVIndex> uvIndex;
    public MutableLiveData<List<WeatherForecast>> weatherForecasts;
    public MutableLiveData<Boolean> isLoading;
    public MutableLiveData<LatLng> currentLatLng;
    public Place currentPlace;
    public MutableLiveData<String> titleToolbar;
    private PlaceRepo placeRepo;
    private WeatherForecastApi weatherApi;

    public MainViewModel(@NonNull Application application) {
        super(application);
        weatherApi = WeatherForecastClient.getInstance().createRequest();
        weatherCurrentDay = new MutableLiveData<>();
        uvIndex = new MutableLiveData<>();
        weatherForecasts = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        currentLatLng = new MutableLiveData<>();
        titleToolbar = new MutableLiveData<>();
        placeRepo = new PlaceRepo(application);
    }

    public Place getPlaceIsHome() {
        return placeRepo.getPlaceIsHome();
    }

    public void getWeatherByLatLng(LatLng latLng) {
        getWeatherCurrentDayByLatLng(latLng);
        getWeatherForecastsByLatLng(latLng);
    }

    private void getWeatherCurrentDayByLatLng(LatLng latLng) {
        isLoading.setValue(true);
        weatherApi
                .fetchWeatherCurrentDayByLatLng(
                        WeatherForecastClient.API_KEY,
                        latLng.latitude,
                        latLng.longitude
                )
                .enqueue(new Callback<WeatherCurrentDayResponse>() {
                    @Override
                    public void onResponse(Call<WeatherCurrentDayResponse> call, Response<WeatherCurrentDayResponse> response) {
                        if (response.isSuccessful()) {
                            WeatherCurrentDay weatherCurrent = response.body().getWeatherCurrentDay();
                            titleToolbar.setValue(weatherCurrent.getNameAndCountry());
                            weatherCurrentDay.setValue(weatherCurrent);
                            getUVIndex();
                        } else {
                            Log.d(TAG, "Not success getWeatherCurrentDayByLatLng: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherCurrentDayResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure getWeatherCurrentDayByLatLng: " + t.getMessage());
                    }
                });
    }

    private void getWeatherForecastsByLatLng(LatLng latLng) {
        weatherApi
                .fetchWeatherForecastByLatLng(
                        WeatherForecastClient.API_KEY,
                        latLng.latitude,
                        latLng.longitude
                )
                .enqueue(new Callback<WeatherForecastResponse>() {
                    @Override
                    public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                        if (response.isSuccessful()) {
                            List<WeatherForecast> listWeatherForecasts = response.body().getWeatherForecasts();
                            weatherForecasts.setValue(listWeatherForecasts);
                        } else {
                            Log.d(TAG, "Not success getWeatherForecastsByLatLng: " + response.message());
                        }
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure getWeatherForecastsByLatLng: " + t.getMessage());
                        isLoading.setValue(false);
                    }
                });

    }

    private void getUVIndex() {
        weatherApi
                .fetchUVIndex(
                        WeatherForecastClient.API_KEY,
                        weatherCurrentDay.getValue().getLatLng().latitude,
                        weatherCurrentDay.getValue().getLatLng().longitude
                )
                .enqueue(new Callback<UVIndex>() {
                    @Override
                    public void onResponse(Call<UVIndex> call, Response<UVIndex> response) {
                        if (response.isSuccessful()) {
                            uvIndex.setValue(response.body());
                        } else {
                            Log.d(TAG, "Not success getUVIndex: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<UVIndex> call, Throwable t) {
                        Log.d(TAG, "onFailure getUVIndex: " + t.getMessage());
                    }
                });
    }

    public void getWeatherByPlace(Place place) {
        getWeatherCurrentDayByPlace(place);
        getWeatherForecastsByPlace(place);
    }

    private void getWeatherCurrentDayByPlace(Place place) {
        isLoading.setValue(true);
        weatherApi
                .fetchWeatherCurrentDayByPlaceName(
                        WeatherForecastClient.API_KEY,
                        place.getName()
                )
                .enqueue(new Callback<WeatherCurrentDayResponse>() {
                    @Override
                    public void onResponse(Call<WeatherCurrentDayResponse> call, Response<WeatherCurrentDayResponse> response) {
                        if (response.isSuccessful()) {
                            titleToolbar.setValue(place.getNameAndCountry());
                            WeatherCurrentDay weatherCurrent = response.body().getWeatherCurrentDay();
                            weatherCurrentDay.setValue(weatherCurrent);
                            getUVIndex();
                        } else {
                            Log.d(TAG, "Not success getWeatherCurrentDayByPlace: " + response.message());
                        }

                    }

                    @Override
                    public void onFailure(Call<WeatherCurrentDayResponse> call, Throwable t) {
                        Log.d(TAG, "Error getWeatherCurrentDayByPlace: " + t.getMessage());
                    }
                });
    }

    private void getWeatherForecastsByPlace(Place place) {
        weatherApi
                .fetchWeatherForecastByPlaceName(WeatherForecastClient.API_KEY, place.getName())
                .enqueue(new Callback<WeatherForecastResponse>() {
                    @Override
                    public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                        if (response.isSuccessful()) {
                            List<WeatherForecast> listWeatherForecasts = response.body().getWeatherForecasts();
                            weatherForecasts.setValue(listWeatherForecasts);
                        } else {
                            Log.d(TAG, "Not success getWeatherForecastsByPlace: " + response.message());
                        }
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure getWeatherForecastsByPlace: " + t.getMessage());
                        isLoading.setValue(false);
                    }
                });
    }


}
