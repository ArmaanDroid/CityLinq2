package adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import fragments.route_detail.ListViewFragment;
import fragments.route_detail.MapViewFragment;
import fragments.trips.CompleteTripFragment;
import fragments.trips.ScheduleTripsFragment;
import models.Station;
import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 13-11-2017.
 */

public class RouteDetailPagerAdapter extends FragmentPagerAdapter {
    Context context;
    ArrayList<Station> stationList;
    public RouteDetailPagerAdapter(ArrayList<Station> stationList, FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
        this.stationList=stationList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = MapViewFragment.newInstance(stationList,null);
        } else if (position == 1) {
            fragment = ListViewFragment.newInstance(stationList,null);
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
            title =context.getString(R.string.map_view);
        } else if (position == 1) {
            title = context.getString(R.string.list_view);
        }
        return title;
    }
}