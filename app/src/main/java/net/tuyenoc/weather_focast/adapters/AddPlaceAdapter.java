package net.tuyenoc.weather_focast.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.tuyenoc.weather_focast.R;
import net.tuyenoc.weather_focast.models.Place;

import java.util.List;

public class AddPlaceAdapter extends RecyclerView.Adapter<AddPlaceAdapter.ViewHolder> {
    private List<Place> places;
    private OnItemClickListener onItemClickListener;

    public AddPlaceAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<Place> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_view_add_place, parent, false);
        return new ViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = places.get(position);
        holder.tvName.setText(place.getNameAndCountry());
    }

    @Override
    public int getItemCount() {
        return places == null ? 0 : places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            tvName = itemView.findViewById(R.id.tvPlaceName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.onItemClickListener.onItemClickSelectPlace(places.get(getAdapterPosition()));
        }
    }

    public interface OnItemClickListener {
        void onItemClickSelectPlace(Place place);
    }
}
