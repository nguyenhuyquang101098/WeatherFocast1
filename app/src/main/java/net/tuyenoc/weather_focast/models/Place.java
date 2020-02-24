package net.tuyenoc.weather_focast.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "places")
public class Place implements Serializable {
    @NonNull
    @PrimaryKey
    private int id;
    private String name, country;
    private boolean isHome;
    private long epochTime;

    public Place(int id, String name, String country, boolean isHome) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.isHome = isHome;
        this.epochTime = System.currentTimeMillis() / 1000L;
    }

    public void setEpochTime(long epochTime) {
        this.epochTime = epochTime;
    }

    public long getEpochTime() {
        return epochTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public boolean isHome() {
        return isHome;
    }

    public String getNameAndCountry() {
        return name + ", " + country;
    }
}
