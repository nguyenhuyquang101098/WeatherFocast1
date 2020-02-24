package net.tuyenoc.weather_focast.views.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import net.tuyenoc.weather_focast.R;
import net.tuyenoc.weather_focast.adapters.WeatherForecastAdapter;
import net.tuyenoc.weather_focast.viewmodels.MainViewModel;

public class ForecastFragment extends Fragment {
    private static String TAG = "ForecastFragment";
    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;
    private ProgressBar pbForecasts;
    private WeatherForecastAdapter adapter;

    public ForecastFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        pbForecasts = view.findViewById(R.id.pbForecasts);
        adapter = new WeatherForecastAdapter(getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mainViewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                pbForecasts.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            } else {
                pbForecasts.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        mainViewModel.weatherForecasts.observe(getViewLifecycleOwner(), weatherForecasts -> {
            adapter.setData(weatherForecasts);
        });
    }
}
