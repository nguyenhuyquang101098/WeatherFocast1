package net.tuyenoc.weather_focast.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import net.tuyenoc.weather_focast.R;
import net.tuyenoc.weather_focast.models.WeatherForecast;

import java.util.List;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder> {
    private List<WeatherForecast> weatherForecasts;
    private Context context;

    public WeatherForecastAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<WeatherForecast> weatherForecasts) {
        this.weatherForecasts = weatherForecasts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_recycler_view_forecast, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherForecast weatherForecast = weatherForecasts.get(position);
        holder.tvTime.setText(weatherForecast.getTimestamp());
        Glide.with(context).load(weatherForecast.getIcon()).into(holder.imgIcon);
        holder.tvDescription.setText(weatherForecast.getDescription());
        holder.tvTempMax.setText(weatherForecast.getTempMax() + context.getString(R.string.doC));
        holder.tvTempMin.setText(weatherForecast.getTempMin() + context.getString(R.string.doC));
    }

    @Override
    public int getItemCount() {
        return weatherForecasts == null ? 0 : weatherForecasts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvDescription, tvTempMax, tvTempMin;
        ImageView imgIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvTempMax = itemView.findViewById(R.id.tvTempMax);
            tvTempMin = itemView.findViewById(R.id.tvTempMin);
            imgIcon = itemView.findViewById(R.id.imgIcon);
        }
    }
}
