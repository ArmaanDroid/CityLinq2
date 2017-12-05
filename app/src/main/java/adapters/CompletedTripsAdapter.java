package adapters;

import android.content.Context;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import models.Completed;
import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 14-11-2017.
 */

public class CompletedTripsAdapter extends RecyclerView.Adapter<CompletedTripsAdapter.ViewHolder> {
    private  Context context;
    ArrayList<Completed> tripList;
    public CompletedTripsAdapter(Context context, ArrayList<Completed> tripList) {
        this.context = context;
        this.tripList=tripList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_completed_trips,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_date_adapter;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_date_adapter= itemView.findViewById(R.id.tv_date_adapter);

            tv_date_adapter.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(context,R.drawable.ic_calendar_light), null, null, null);
        }
    }
}
