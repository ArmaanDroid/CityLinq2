package adapters;

import android.content.Context;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import models.Scheduled;
import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 14-11-2017.
 */

public class ScheduledTripsAdapter extends RecyclerView.Adapter<ScheduledTripsAdapter.ViewHolder> {
    private  Context context;
    ArrayList<Scheduled> scheduleds;
    public ScheduledTripsAdapter(Context context, ArrayList<Scheduled> scheduleds) {
        this.context = context;
        this.scheduleds=scheduleds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_scheduled_trips,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return scheduleds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_date_adapter;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_date_adapter= itemView.findViewById(R.id.tv_date_adapter);

            tv_date_adapter.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(context,R.drawable.ic_calendar_light), null,null, null);
        }
    }
}
