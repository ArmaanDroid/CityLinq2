package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import listners.AdapterItemClickListner;
import models.Station;
import sanguinebits.com.citylinq.R;
import views.TextViewWithSideLine;

/**
 * Created by Armaan on 13-11-2017.
 */

public class RouteDetailListAdapter extends RecyclerView.Adapter<RouteDetailListAdapter.ViewHolder> {
    private final Calendar calendar;
    AdapterItemClickListner mListner;
    ArrayList<Station> stationList;
    SimpleDateFormat dateFormat = new SimpleDateFormat(" hh:mm a");
    private Station currentItem;

    public RouteDetailListAdapter(ArrayList<Station> stationList, AdapterItemClickListner mListner) {
        this.mListner = mListner;
        this.stationList = stationList;
        calendar = Calendar.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_route_detail_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        currentItem = stationList.get(position);
        if (position == 0)
            holder.textViewWithSideLine.setIsFirst(true);
        if (position == getItemCount() - 1)
            holder.textViewWithSideLine.setIsLast(true);
        if (currentItem.getReach_time() != 0) {
            calendar.setTimeInMillis(currentItem.getReach_time());
            holder.textViewWithSideLine.customSetText(currentItem.getName(), "Estimate reach time " + dateFormat.format(calendar.getTime()));
        } else {
            holder.textViewWithSideLine.customSetText(currentItem.getName(), "");

        }
    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextViewWithSideLine textViewWithSideLine;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewWithSideLine = itemView.findViewById(R.id.textViewRouteDetail);
        }
    }
}
