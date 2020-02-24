package net.tuyenoc.weather_focast.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import net.tuyenoc.weather_focast.R;
import net.tuyenoc.weather_focast.adapters.PlaceAdapter;
import net.tuyenoc.weather_focast.models.Place;
import net.tuyenoc.weather_focast.viewmodels.PlaceViewModel;

import java.util.List;

public class PlaceActivity extends AppCompatActivity implements View.OnClickListener, PlaceAdapter.OnItemClickListener {
    private static String TAG = "PlaceActivity";
    public static String PLACE_RETURN = "place_return";
    public static int RESULT_CODE_PLACE_RETURN = 100;
    public static String MY_LOCATION = "my_location";
    public static int RESULT_CODE_MY_LOCATION = 101;
    private PlaceViewModel placeViewModel;
    private RecyclerView recyclerView;
    private PlaceAdapter placeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.layoutAddPlace).setOnClickListener(this);
        findViewById(R.id.layoutMyLocation).setOnClickListener(this);

        placeAdapter = new PlaceAdapter(PlaceActivity.this, placeViewModel.getPlaceRepo(), this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(placeAdapter);

        placeViewModel.places.observe(this, new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                placeAdapter.setData(places);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layoutAddPlace) {
            startActivity(new Intent(PlaceActivity.this, AddPlaceActivity.class));
        } else if (v.getId() == R.id.layoutMyLocation) {
            setResult(RESULT_CODE_MY_LOCATION, new Intent().putExtra(MY_LOCATION, "My location"));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public void onItemClickSelectPlace(Place place) {
        setResult(RESULT_CODE_PLACE_RETURN, new Intent().putExtra(PLACE_RETURN, place));
        finish();
    }
}
