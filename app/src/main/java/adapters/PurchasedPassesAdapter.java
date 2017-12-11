package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import models.Pass;
import models.PurchasePass;
import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 14-11-2017.
 */

public class PurchasedPassesAdapter extends RecyclerView.Adapter<PurchasedPassesAdapter.ViewHolder> {
    List<PurchasePass> passList;
    public PurchasedPassesAdapter(List<PurchasePass> passList) {
        this.passList=passList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_purchased_pass,parent,false);
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
        private TextView textViewPassValidity;
        private TextView textViewPassTotalRides;
        private TextView textViewPassPricePErRide;
        private TextView textViewPassPrice;
        private Pass currentItem;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewPassValidity= itemView.findViewById(R.id.textViewPassValidity);
            textViewPassTotalRides= itemView.findViewById(R.id.textViewPassTotalRides);
            textViewPassPricePErRide= itemView.findViewById(R.id.textViewPassPricePErRide);
            textViewPassPrice= itemView.findViewById(R.id.textViewPassPrice);

        }

        public void setData(final int position) {
            currentItem=passList.get(position).getPass();

            textViewPassPrice.setText("$"+currentItem.getAmount().toString());
            textViewPassValidity.setText(currentItem.getValidity()+" days");
            textViewPassTotalRides.setText(currentItem.getRides().toString());

            float perRide=Float.valueOf(currentItem.getAmount())/Float.valueOf(currentItem.getRides());
            String perRideFare= String.format(Locale.US, "%.2f", perRide);
            textViewPassPricePErRide.setText("Per Ride $"+perRideFare);
        }
    }

}
