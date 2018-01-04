package adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import listners.AdapterItemClickListner;
import models.Scheduled;
import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 14-11-2017.
 */

public class ScheduledTripsAdapter extends RecyclerView.Adapter<ScheduledTripsAdapter.ViewHolder> {
    private Context context;
    ArrayList<Scheduled> scheduleds;
    AdapterItemClickListner itemClickListner;

    public ScheduledTripsAdapter(Context context, ArrayList<Scheduled> scheduleds, AdapterItemClickListner itemClickListner) {
        this.context = context;
        this.scheduleds = scheduleds;
        this.itemClickListner = itemClickListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_scheduled_trips, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            holder.setData(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return scheduleds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView tv_date_adapter;
            private final TextView textTiming;
            private final TextView textViewFrom;
            private final TextView textViewTO;
            private final TextView textViewRideName;
            private final TextView textViewModel;
            private final ImageView imageView5;
            private Scheduled currentItem;
            private DateFormat monthNameDateFormat = new SimpleDateFormat("MMMM d, yyyy");

        public ViewHolder(View itemView) {
            super(itemView);
            tv_date_adapter = itemView.findViewById(R.id.tv_date_adapter);
            textTiming = itemView.findViewById(R.id.textTiming);
            textViewFrom = itemView.findViewById(R.id.textViewFrom);
            textViewTO = itemView.findViewById(R.id.textViewTO);
            textViewRideName = itemView.findViewById(R.id.textView22);
            textViewModel = itemView.findViewById(R.id.textView25);
            imageView5 = itemView.findViewById(R.id.imageView5);

            tv_date_adapter.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(context, R.drawable.ic_calendar_light), null, null, null);
        }

        public void setData(final int position) throws Exception {
            currentItem = scheduleds.get(position);
            Date date = new Date();
            date.setTime(Long.parseLong(currentItem.getDate()));
            tv_date_adapter.setText(monthNameDateFormat.format(date));

            imageView5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListner.onClick(position, "");
                }
            });
            textTiming.setText(currentItem.getTimings());
            textViewFrom.setText(currentItem.getSource().getName());
            textViewTO.setText(currentItem.getDestination().getName());
            textViewRideName.setText(currentItem.getTransportName());
            textViewModel.setText(currentItem.getVehicleNumber());
        }

    }
}
