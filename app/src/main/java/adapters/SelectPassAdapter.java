package adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import listners.AdapterItemClickListner;
import models.Pass;
import models.PurchasePass;
import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 24-01-2018.
 */

public class SelectPassAdapter extends RecyclerView.Adapter<SelectPassAdapter.ViewHolder> {
    AdapterItemClickListner adapterItemClickListner;
    ArrayList<PurchasePass> purchasePasses;
    int previousSelectedItem;
   public int selectedItem;

    public SelectPassAdapter(ArrayList<PurchasePass> purchasePasses, AdapterItemClickListner adapterItemClickListner) {
        this.adapterItemClickListner = adapterItemClickListner;
        this.purchasePasses = purchasePasses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_select_pass, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return purchasePasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewPassValidity;
        private TextView textViewPassTotalRides;
        private TextView textViewPassPricePErRide;
        private TextView textViewPassPrice;
        private CheckBox checkbox;
        private Pass currentItem;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewPassValidity = itemView.findViewById(R.id.textViewPassValidity);
            textViewPassTotalRides = itemView.findViewById(R.id.textViewPassTotalRides);
            textViewPassPricePErRide = itemView.findViewById(R.id.textViewPassPricePErRide);
            textViewPassPrice = itemView.findViewById(R.id.textViewPassPrice);
            checkbox = itemView.findViewById(R.id.checkbox);

        }

        public void setData(final int position) {
            currentItem = purchasePasses.get(position).getPass();

            textViewPassPrice.setText("$" + currentItem.getAmount().toString());
            textViewPassValidity.setText(currentItem.getValidity() + " days");
            textViewPassTotalRides.setText(currentItem.getRides().toString());

            float perRide = Float.valueOf(currentItem.getAmount()) / Float.valueOf(currentItem.getRides());
            String perRideFare = String.format(Locale.US, "%.2f", perRide);
            textViewPassPricePErRide.setText("Per Ride $" + perRideFare);

            if (position == selectedItem) {
                checkbox.setChecked(true);
            } else {
                checkbox.setChecked(false);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedItem != position) {
                        previousSelectedItem = selectedItem;
                        notifyItemChanged(previousSelectedItem);
                        selectedItem = position;
                        checkbox.setChecked(true);
                    }
                }
            });
        }
    }
}
