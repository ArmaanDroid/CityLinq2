package adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import fragments.trips.CompleteTripFragment;
import fragments.trips.ScheduleTripsFragment;
import models.CommonPojo;
import models.Completed;
import models.Scheduled;
import sanguinebits.com.citylinq.R;

public class TripsPagerAdapter extends FragmentPagerAdapter {
    Context context;
    CommonPojo commonPojo;

    public TripsPagerAdapter(FragmentManager fm, Context context, CommonPojo commonPojo) {
        super(fm);
        this.context = context;
        this.commonPojo = commonPojo;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            if (commonPojo == null)
                fragment = ScheduleTripsFragment.newInstance(new ArrayList<Scheduled>(), null);
            else
                fragment = ScheduleTripsFragment.newInstance(commonPojo.getScheduled(), null);

        } else if (position == 1) {
            if (commonPojo == null)
                fragment = CompleteTripFragment.newInstance(new ArrayList<Completed>(), null);
            else
                fragment = CompleteTripFragment.newInstance(commonPojo.getCompleted(), null);

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
            title = context.getString(R.string.schedule);
        } else if (position == 1) {
            title = context.getString(R.string.complete);
        }
        return title;

    }
}