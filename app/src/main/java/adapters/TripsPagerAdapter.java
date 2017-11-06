package adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.trips.CompleteTripFragment;
import fragments.trips.ScheduleTripsFragment;
import sanguinebits.com.citylinq.R;

public class TripsPagerAdapter extends FragmentPagerAdapter {
Context context;
    public TripsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new ScheduleTripsFragment();
        } else if (position == 1) {
            fragment = new CompleteTripFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title =context.getString(R.string.schedule);
        } else if (position == 1) {
            title = context.getString(R.string.complete);
        }
        return title;

    }
}