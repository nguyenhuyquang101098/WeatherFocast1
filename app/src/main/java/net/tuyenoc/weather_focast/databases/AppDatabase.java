package net.tuyenoc.weather_focast.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import net.tuyenoc.weather_focast.models.Place;

@Database(entities = {Place.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static String DATABASE_NAME = "weather_forecast_database";

    private static AppDatabase instance;

    public abstract PlaceDao placeDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
        }
        return instance;
    }
}
