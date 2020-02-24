package net.tuyenoc.weather_focast.models;

class CountrySearch {
    public String LocalizedName;
}

public class ItemSearch {
    private int Key;
    private String LocalizedName;
    private CountrySearch Country;

    public Place getPlace() {
        return new Place(
                Key,
                LocalizedName,
                Country.LocalizedName,
                false
        );
    }
}
