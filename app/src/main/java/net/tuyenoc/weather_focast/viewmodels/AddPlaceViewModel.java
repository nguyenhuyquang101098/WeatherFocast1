package net.tuyenoc.weather_focast.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import net.tuyenoc.weather_focast.models.ItemSearch;
import net.tuyenoc.weather_focast.models.Place;
import net.tuyenoc.weather_focast.networks.WeatherForecastApi;
import net.tuyenoc.weather_focast.networks.WeatherForecastClient;
import net.tuyenoc.weather_focast.repositories.PlaceRepo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPlaceViewModel extends AndroidViewModel {
    private static String TAG = "AddPlaceViewModel";
    private PlaceRepo placeRepo;
    public MutableLiveData<List<Place>> places;
    public MutableLiveData<Boolean> isLoading;

    public AddPlaceViewModel(@NonNull Application application) {
        super(application);
        placeRepo = new PlaceRepo(application);
        places = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
    }

    public void getPlacesByKeyword(String keyword) {
        isLoading.setValue(true);
        keyword = keyword.trim().replaceAll("//s+", "%20");
        new Retrofit.Builder().baseUrl(WeatherForecastClient.BASE_URL_SEARCH)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(WeatherForecastApi.class)
                .fetchPlaces(WeatherForecastClient.API_KEY_SEARCH, keyword)
                .enqueue(new Callback<List<ItemSearch>>() {
                    @Override
                    public void onResponse(Call<List<ItemSearch>> call, Response<List<ItemSearch>> response) {
                        if (response.isSuccessful()) {
                            List<Place> listPlaces = new ArrayList<>();
                            for (ItemSearch item : response.body()) {
                                listPlaces.add(item.getPlace());
                            }
                            places.setValue(listPlaces);
                        }
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<List<ItemSearch>> call, Throwable t) {
                        isLoading.setValue(false);
                    }
                });
    }

    public void insertPlace(Place place) {
        placeRepo.insertPlace(place);
    }
}
