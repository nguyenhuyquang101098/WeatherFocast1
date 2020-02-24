package net.tuyenoc.weather_focast.views.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.tabs.TabLayout;

import net.tuyenoc.weather_focast.AppHelper;
import net.tuyenoc.weather_focast.R;
import net.tuyenoc.weather_focast.adapters.ViewPagerAdapter;
import net.tuyenoc.weather_focast.models.Place;
import net.tuyenoc.weather_focast.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 123;
    private static String TAG = "MainActivity";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2200;
    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private AlertDialog dialogTurnOnLocation;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        buildDialogTurnOnLocation();
        createLocationCallBack();
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = AppHelper.getLocationRequest();

        if (mainViewModel.getPlaceIsHome() != null) {
            mainViewModel.currentPlace = mainViewModel.getPlaceIsHome();
            mainViewModel.getWeatherByPlace(mainViewModel.currentPlace);
        } else {
            if (!AppHelper.isHaveLocationPermission(this)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            } else {
                requestLocationUpdate();
            }
        }

        mainViewModel.titleToolbar.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String titleToolbar) {
                getSupportActionBar().setTitle(titleToolbar);
            }
        });
    }


    private void createLocationCallBack() {

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationRequest.getInterval() == 1L) {
                    locationRequest.setInterval(AppHelper.MY_INTERVAL);
                    locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                }
                if (locationResult.getLastLocation() == null)
                    return;
                LatLng latLng = new LatLng(
                        locationResult.getLastLocation().getLatitude(),
                        locationResult.getLastLocation().getLongitude()
                );
                if (mainViewModel.currentLatLng.getValue() == null || !mainViewModel.currentLatLng.getValue().equals(latLng)) {
                    mainViewModel.currentLatLng.postValue(latLng);
                    mainViewModel.getWeatherByLatLng(latLng);
                }
                return;

            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemMenuPlace:
                startActivityForResult(new Intent(MainActivity.this, PlaceActivity.class), REQUEST_CODE);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == PlaceActivity.RESULT_CODE_MY_LOCATION) {
                mainViewModel.currentLatLng.setValue(null);
                locationRequest.setInterval(1L);
                requestLocationUpdate();
            } else if (resultCode == PlaceActivity.RESULT_CODE_PLACE_RETURN) {
                Place place = (Place) data.getSerializableExtra(PlaceActivity.PLACE_RETURN);
                mainViewModel.getWeatherByPlace(place);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (mainViewModel.currentPlace == null) {
                    mainViewModel.currentPlace = new Place(353412, "Hanoi", "Vietnam", false);
                    mainViewModel.getWeatherByPlace(mainViewModel.currentPlace);
                }
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocationUpdate();
            }
        }

    }

    private void buildDialogTurnOnLocation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setCancelable(false)
                .setTitle(getString(R.string.turn_on_location))
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (mainViewModel.currentPlace == null) {
                            mainViewModel.currentPlace = new Place(353412, "Hanoi", "Vietnam", false);
                            mainViewModel.getWeatherByPlace(mainViewModel.currentPlace);
                        }
                    }
                })
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        if (!AppHelper.isLocationProviderEnabled(MainActivity.this)) {
                            if (mainViewModel.currentPlace == null) {
                                mainViewModel.currentPlace = new Place(353412, "Hanoi", "Vietnam", false);
                                mainViewModel.getWeatherByPlace(mainViewModel.currentPlace);
                            }
                        }
                    }
                });
        dialogTurnOnLocation = builder.create();
    }


    private void requestLocationUpdate() {
        if (!AppHelper.isLocationProviderEnabled(this)) {
            dialogTurnOnLocation.show();
        }

        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }


}
