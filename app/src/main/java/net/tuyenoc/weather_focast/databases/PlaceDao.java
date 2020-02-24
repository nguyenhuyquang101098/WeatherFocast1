package net.tuyenoc.weather_focast.databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import net.tuyenoc.weather_focast.models.Place;

import java.util.List;

@Dao
public interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPlace(Place place);

    @Query("DELETE FROM places WHERE id = :id")
    void deletePlaceById(int id);

    @Query("UPDATE places SET isHome = 1 WHERE id = :id")
    void setPlaceIsHomeById(int id);

    @Query("UPDATE places SET isHome = 0 WHERE isHome = 1")
    void setAllPlacesIsNotHome();

    @Query("SELECT * FROM places ORDER BY epochTime DESC")
    LiveData<List<Place>> fetchPlaces();

    @Query("SELECT * FROM places WHERE isHome = 1")
    Place fetchPlaceIsHome();
}
