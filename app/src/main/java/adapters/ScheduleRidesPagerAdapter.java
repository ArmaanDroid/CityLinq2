package adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import listners.AdapterItemClickListner;
import models.Scheduled;
import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 20-11-2017.
 */

public class ScheduleRidesPagerAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    List<Scheduled> scheduleList;
    private TextView textViewDate;
    private TextView textViewTiming;
    private TextView textViewBoardingStation;
    private Scheduled currentItem;
    static final   String PAGER_ITEM_CLICK_TAG="pagerItemClicked";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy", Locale.US);
    private AdapterItemClickListner adapterItemClickListner;

    public ScheduleRidesPagerAdapter(List<Scheduled> scheduleList, AdapterItemClickListner adapterItemClickListner) {
        this.adapterItemClickListner = adapterItemClickListner;
        this.scheduleList = scheduleList;
    }

    @Override
    public int getCount() {
        return scheduleList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    View itemView;

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

            itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.view_schedule_ride_pager_item, container, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterItemClickListner.onClick(position,PAGER_ITEM_CLICK_TAG);
            }
        });
        textViewDate = itemView.findViewById(R.id.textView15);
        textViewTiming = itemView.findViewById(R.id.textView14);
        textViewBoardingStation = itemView.findViewById(R.id.textView18);
        try {
            currentItem = scheduleList.get(position);
            textViewTiming.setText(currentItem.getTimings());
            textViewBoardingStation.setText(currentItem.getSource().getName());
            Date date = new Date();
            date.setTime(Long.parseLong(currentItem.getDate()));
            textViewDate.setText(simpleDateFormat.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
