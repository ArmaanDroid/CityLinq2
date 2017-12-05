package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import models.Pass;
import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 14-11-2017.
 */

public class AvailablePassesAdapter extends RecyclerView.Adapter<AvailablePassesAdapter.ViewHolder> {
    List<Pass> passList;

    public AvailablePassesAdapter(List<Pass> passList) {
        this.passList = passList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_available_pass, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return passList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewPassSaving;
        private TextView textViewPassValidity;
        private TextView textViewPassTotalRides;
        private TextView textViewPassPricePErRide;
        private TextView textViewPassPrice;
        private Pass currentItem;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewPassSaving= itemView.findViewById(R.id.textViewPassSaving);
            textViewPassValidity= itemView.findViewById(R.id.textViewPassValidity);
            textViewPassTotalRides= itemView.findViewById(R.id.textViewPassTotalRides);
            textViewPassPricePErRide= itemView.findViewById(R.id.textViewPassPricePErRide);
            textViewPassPrice= itemView.findViewById(R.id.textViewPassPrice);
        }

        public void setData(int position) {
            currentItem=passList.get(position);

            textViewPassPrice.setText(currentItem.getAmount().toString());
            textViewPassValidity.setText(currentItem.getValidity());
            textViewPassSaving.setText(currentItem.getDiscount().toString());
        }
    }
}
