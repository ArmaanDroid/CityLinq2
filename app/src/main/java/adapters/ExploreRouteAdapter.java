package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import listners.AdapterItemClickListner;
import models.RouteList;
import models.Station;
import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 16-11-2017.
 */

public class ExploreRouteAdapter extends RecyclerView.Adapter<ExploreRouteAdapter.ViewHoder> {

    ArrayList<RouteList> routeList;
    AdapterItemClickListner adapterItemClickListner;

    public ExploreRouteAdapter(ArrayList<RouteList> routeList, AdapterItemClickListner adapterItemClickListner) {
        this.routeList = routeList;
        this.adapterItemClickListner = adapterItemClickListner;
    }

    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_explore_route, parent, false);
        ViewHoder viewHoder = new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(ViewHoder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        private final TextView tv_routeName;
        private final TextView tv_routeTiming;
        private final TextView tv_To;
        private final TextView tv_from;
        private RouteList currentItem;
        private Station startLocation;
        private Station endLocation;

        public ViewHoder(View itemView) {
            super(itemView);
            tv_routeName = itemView.findViewById(R.id.tv_routeName);
            tv_routeTiming = itemView.findViewById(R.id.tv_routeTiming);
            tv_To = itemView.findViewById(R.id.tv_To);
            tv_from = itemView.findViewById(R.id.tv_from);
        }

        public void setData(final int position) {
            currentItem = routeList.get(position);

            tv_routeName.setText(currentItem.getName());
            tv_routeTiming.setText(currentItem.getTiming());

            startLocation = currentItem.getStations().get(0);
            endLocation = currentItem.getStations().get(currentItem.getStations().size() - 1);

            tv_from.setText(startLocation.getName());
            tv_To.setText(endLocation.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterItemClickListner.onClick(position, "");
                }
            });
        }
    }
}
