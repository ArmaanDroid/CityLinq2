package adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import fragments.passes.AvailabelFragment;
import fragments.passes.PurchasedFragment;
import models.CommonPojo;
import models.Pass;
import sanguinebits.com.citylinq.R;

public class PassesPagerAdapter extends FragmentPagerAdapter {
    Context context;
    CommonPojo commonPojo;

    public PassesPagerAdapter(FragmentManager fm, Context context, CommonPojo commonPojo) {
        super(fm);
        this.context = context;
        this.commonPojo = commonPojo;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            if (commonPojo == null)
                fragment = PurchasedFragment.newInstance(new ArrayList<Pass>(), "");
            else
                fragment = PurchasedFragment.newInstance(commonPojo.getPurchasePasses(), "");

        } else if (position == 1) {
            if (commonPojo == null)
                fragment = AvailabelFragment.newInstance(new ArrayList<Pass>(), null);
            else
                fragment = AvailabelFragment.newInstance(commonPojo.getPasses(), "");

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
            title = context.getString(R.string.purchased);
        } else if (position == 1) {
            title = context.getString(R.string.available);
        }
        return title;
    }
}