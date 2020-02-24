package net.tuyenoc.weather_focast.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.tuyenoc.weather_focast.R;
import net.tuyenoc.weather_focast.models.Place;
import net.tuyenoc.weather_focast.repositories.PlaceRepo;
import net.tuyenoc.weather_focast.views.activities.PlaceActivity;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    private Context context;
    private PlaceRepo placeRepo;
    private List<Place> places;
    private OnItemClickListener onItemClickListener;

    public PlaceAdapter(Context context, PlaceRepo placeRepo, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.placeRepo = placeRepo;
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<Place> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_place, parent, false), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = places.get(position);
        holder.imgIcon.setImageResource(place.isHome() ? R.drawable.ic_home : R.drawable.ic_location);
        holder.tvPlaceName.setText(place.getNameAndCountry());
        holder.tvOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (place.isHome()) {
                    buildPopupMenuDelete(holder, place);
                } else {
                    buildPopupMenuSetPlaceIsHomeAndDelete(holder, place);
                }
            }
        });
    }

    private void buildPopupMenuSetPlaceIsHomeAndDelete(ViewHolder holder, Place place) {
        PopupMenu popupMenu = new PopupMenu(context, holder.tvOption);
        popupMenu.inflate(R.menu.menu_option);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.itemMenuSetIsHome) {
                    placeRepo.setAllPlacesIsNotHome();
                    placeRepo.setPlaceIsHome(place);
                    holder.imgIcon.setImageResource(R.drawable.ic_home);
                    Activity placeActivity = (Activity) context;
                    placeActivity.setResult(PlaceActivity.RESULT_CODE_PLACE_RETURN, new Intent().putExtra(PlaceActivity.PLACE_RETURN, place));
                    placeActivity.finish();
                } else {
                    placeRepo.deletePlaceById(place.getId());
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void buildPopupMenuDelete(ViewHolder holder, Place place) {
        PopupMenu popupMenu = new PopupMenu(context, holder.tvOption);
        popupMenu.inflate(R.menu.menu_option_1);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.itemMenuDelete) {
                    placeRepo.deletePlaceById(place.getId());
                }
                return true;
            }
        });
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return places == null ? 0 : places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvPlaceName, tvOption;
        ImageView imgIcon;
        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            tvPlaceName = itemView.findViewById(R.id.tvPlaceName);
            tvOption = itemView.findViewById(R.id.tvOption);
            this.onItemClickListener = onItemClickListener;
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
