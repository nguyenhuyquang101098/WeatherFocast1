package net.tuyenoc.weather_focast.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.tuyenoc.weather_focast.databases.AppDatabase;
import net.tuyenoc.weather_focast.databases.PlaceDao;
import net.tuyenoc.weather_focast.models.Place;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PlaceRepo {
    private static String TAG = "PlaceRepo";
    private PlaceDao placeDao;

    public PlaceRepo(Application application) {
        placeDao = AppDatabase.getInstance(application.getApplicationContext()).placeDao();
    }

    public LiveData<List<Place>> getPlaces() {
        return placeDao.fetchPlaces();
    }

    public void insertPlace(Place place) {
        new InsertPlaceAsyncTask(placeDao).execute(place);
    }

    private static class InsertPlaceAsyncTask extends AsyncTask<Place, Void, Void> {
        private PlaceDao placeDao;

        public InsertPlaceAsyncTask(PlaceDao placeDao) {
            this.placeDao = placeDao;
        }

        @Override
        protected Void doInBackground(Place... places) {
            placeDao.insertPlace(places[0]);
            return null;
        }
    }

    public void deletePlaceById(int id) {
        new DeletePlaceAsyncTask(placeDao).execute(id);
    }

    private static class DeletePlaceAsyncTask extends AsyncTask<Integer, Void, Void> {
        private PlaceDao placeDao;

        public DeletePlaceAsyncTask(PlaceDao placeDao) {
            this.placeDao = placeDao;
        }

        @Override
        protected Void doInBackground(Integer... ids) {
            placeDao.deletePlaceById(ids[0]);
            return null;
        }
    }


    public void setPlaceIsHome(Place place) {
        new SetPlaceIsHomeAsyncTask(placeDao).execute(place);
    }

    private static class SetPlaceIsHomeAsyncTask extends AsyncTask<Place, Void, Void> {
        private PlaceDao placeDao;

        public SetPlaceIsHomeAsyncTask(PlaceDao placeDao) {
            this.placeDao = placeDao;
        }

        @Override
        protected Void doInBackground(Place... places) {
            placeDao.setPlaceIsHomeById(places[0].getId());
            return null;
        }
    }

    public void setAllPlacesIsNotHome() {
        new SetAllPlacesIsNotHomeAsyncTask(placeDao).execute();
    }

    private static class SetAllPlacesIsNotHomeAsyncTask extends AsyncTask<Void, Void, Void> {
        private PlaceDao placeDao;

        public SetAllPlacesIsNotHomeAsyncTask(PlaceDao placeDao) {
            this.placeDao = placeDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            placeDao.setAllPlacesIsNotHome();
            return null;
        }
    }

    public MutableLiveData<List<Place>> searchPlacesByKeyword(String keyword) {
        MutableLiveData<List<Place>> places = new MutableLiveData<>();


        return places;
    }

    public Place getPlaceIsHome() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Place> callable = new Callable<Place>() {
            @Override
            public Place call() {
                return placeDao.fetchPlaceIsHome();
            }
        };
        Future<Place> future = executor.submit(callable);
        try {
            Place placeIsHome = future.get();
            executor.shutdown();
            return placeIsHome;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
