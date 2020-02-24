package net.tuyenoc.weather_focast.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;

import net.tuyenoc.weather_focast.R;
import net.tuyenoc.weather_focast.viewmodels.MainViewModel;

public class CurrentDayFragment extends Fragment {
    private static String TAG = "CurrentDayFragment";
    private MainViewModel mainViewModel;
    private TextView tvTemp, tvDescription, tvSunriseSunset, tvHumidity, tvCloud, tvWind, tvPressure, tvUVIndex, tvLastUpdated;
    private ImageView imageIcon;
    private LinearLayout layoutGlobal, layoutDetail;
    private ProgressBar pbGlobal, pbDetail;

    public CurrentDayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_day, container, false);
        tvTemp = view.findViewById(R.id.tvTemp);
        tvDescription = view.findViewById(R.id.tvDescription);
        imageIcon = view.findViewById(R.id.imgIcon);
        tvSunriseSunset = view.findViewById(R.id.tvSunriseSunset);
        tvHumidity = view.findViewById(R.id.tvHumidity);
        tvCloud = view.findViewById(R.id.tvCloud);
        tvWind = view.findViewById(R.id.tvWind);
        tvPressure = view.findViewById(R.id.tvPressure);
        tvUVIndex = view.findViewById(R.id.tvUVIndex);
        tvLastUpdated = view.findViewById(R.id.tvLastUpdated);
        layoutGlobal = view.findViewById(R.id.layoutGlobal);
        layoutDetail = view.findViewById(R.id.layoutDetail);
        pbGlobal = view.findViewById(R.id.pbGlobal);
        pbDetail = view.findViewById(R.id.pbDetail);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mainViewModel.weatherCurrentDay.observe(getViewLifecycleOwner(), weatherCurrentDay -> {
            tvTemp.setText(String.valueOf(weatherCurrentDay.getTemp()));
            Glide.with(this).load(weatherCurrentDay.getIcon()).into(imageIcon);
            tvDescription.setText(weatherCurrentDay.getDescription());
            tvSunriseSunset.setText(getString(R.string.sun_rise_and_sun_set) + weatherCurrentDay.getSunriseAndSunset());
            tvHumidity.setText(weatherCurrentDay.getHumidity());
            tvCloud.setText(weatherCurrentDay.getCloud());
            tvWind.setText(weatherCurrentDay.getWind());
            tvPressure.setText(weatherCurrentDay.getPressure());
            tvLastUpdated.setText(getString(R.string.last_updated) + weatherCurrentDay.getTimeUpdate());
        });

        mainViewModel.uvIndex.observe(getViewLifecycleOwner(), uvIndex -> tvUVIndex.setText(String.valueOf(uvIndex.value)));

        mainViewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                pbGlobal.setVisibility(View.VISIBLE);
                pbDetail.setVisibility(View.VISIBLE);
                layoutGlobal.setVisibility(View.INVISIBLE);
                layoutDetail.setVisibility(View.INVISIBLE);
            } else {
                pbGlobal.setVisibility(View.INVISIBLE);
                pbDetail.setVisibility(View.INVISIBLE);
                layoutGlobal.setVisibility(View.VISIBLE);
                layoutDetail.setVisibility(View.VISIBLE);
            }
        });
    }
}
