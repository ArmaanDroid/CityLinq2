package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import listners.AdapterItemClickListner;
import sanguinebits.com.citylinq.R;
import views.TextViewWithSideLine;

/**
 * Created by Armaan on 13-11-2017.
 */

public class RouteDetailListAdapter extends RecyclerView.Adapter<RouteDetailListAdapter.ViewHolder> {
    AdapterItemClickListner mListner;

    public RouteDetailListAdapter(AdapterItemClickListner mListner) {
        this.mListner = mListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_route_detail_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0)
            holder.textViewWithSideLine.setIsFirst(true);
        if (position == getItemCount() - 1)
            holder.textViewWithSideLine.setIsLast(true);

        holder.textViewWithSideLine.customSetText("548 Nikolas Bridge", "Estd. Reach Time 10:00 AM");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextViewWithSideLine textViewWithSideLine;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewWithSideLine = itemView.findViewById(R.id.textViewRouteDetail);
        }
    }
}
