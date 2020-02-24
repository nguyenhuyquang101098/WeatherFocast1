package net.tuyenoc.weather_focast.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import net.tuyenoc.weather_focast.R;
import net.tuyenoc.weather_focast.views.fragments.CurrentDayFragment;
import net.tuyenoc.weather_focast.views.fragments.ForecastFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    public ViewPagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return position == 0 ? new CurrentDayFragment() : new ForecastFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? context.getString(R.string.today) : context.getString(R.string.forecast);
    }
}
