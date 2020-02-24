package net.tuyenoc.weather_focast.views.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import net.tuyenoc.weather_focast.R;
import net.tuyenoc.weather_focast.adapters.AddPlaceAdapter;
import net.tuyenoc.weather_focast.models.Place;
import net.tuyenoc.weather_focast.viewmodels.AddPlaceViewModel;

import java.util.List;

public class AddPlaceActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, AddPlaceAdapter.OnItemClickListener {
    private static String TAG = "AddPlaceActivity";
    private AddPlaceViewModel addPlaceViewModel;
    private ProgressBar pbAddPlace;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private AddPlaceAdapter addPlaceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        addPlaceViewModel = ViewModelProviders.of(this).get(AddPlaceViewModel.class);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addPlaceAdapter = new AddPlaceAdapter(this);

        pbAddPlace = findViewById(R.id.pbAddPlace);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(addPlaceAdapter);

        addPlaceViewModel.isLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    pbAddPlace.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                } else {
                    pbAddPlace.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        addPlaceViewModel.places.observe(this, new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                addPlaceAdapter.setData(places);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        searchView = (SearchView) menu.findItem(R.id.itemMenuSearch).getActionView();
        searchView.onActionViewExpanded();
        searchView.setQueryHint(getString(R.string.hint_search));
        searchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!newText.isEmpty())
            addPlaceViewModel.getPlacesByKeyword(newText);
        else
            addPlaceAdapter.setData(null);
        return true;
    }

    @Override
    public void onItemClickSelectPlace(Place place) {
        addPlaceViewModel.insertPlace(place);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AddPlaceActivity.this.finish();
            }
        }, 300);
    }
}
