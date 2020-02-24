package net.tuyenoc.weather_focast.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import net.tuyenoc.weather_focast.models.Place;
import net.tuyenoc.weather_focast.repositories.PlaceRepo;

import java.util.List;

public class PlaceViewModel extends AndroidViewModel {
    private PlaceRepo placeRepo;
    public LiveData<List<Place>> places;

    public PlaceViewModel(@NonNull Application application) {
        super(application);
        placeRepo = new PlaceRepo(application);
        places = placeRepo.getPlaces();
    }

    public PlaceRepo getPlaceRepo() {
        return placeRepo;
    }

    public void insertPlace(Place place) {
        placeRepo.insertPlace(place);
    }

    public void deletePlaceById(int id) {
        placeRepo.deletePlaceById(id);
    }

    public void updatePlaceIsHome(Place place) {
        placeRepo.setPlaceIsHome(place);
    }


}
