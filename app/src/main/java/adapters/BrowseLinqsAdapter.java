package adapters;

import android.media.Image;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import java.util.List;

import listners.AdapterItemClickListner;
import models.TransportList;
import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 13-11-2017.
 */

public class BrowseLinqsAdapter extends RecyclerView.Adapter<BrowseLinqsAdapter.ViewHolder> {
    AdapterItemClickListner listner;
    List<TransportList> transportList;

    public BrowseLinqsAdapter(List<TransportList> transportList, AdapterItemClickListner listner) {
        this.listner = listner;
        this.transportList = transportList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_browse_linqs, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.bindData(position);
        holder.imageViewRouteDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onClick(position, "");
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onClick(position, "book");
            }
        });
    }

    @Override
    public int getItemCount() {
        return transportList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewRouteDetails;
        TextView textViewStatus;
        TextView textViewLinqName;
        TextView textViewSeatAndTime;
        TextView textViewTime;
        TextView textViewPrice;
        private TransportList currentItem;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewRouteDetails = itemView.findViewById(R.id.imageView5);
            textViewStatus = itemView.findViewById(R.id.textView24);
            textViewLinqName = itemView.findViewById(R.id.textView20);
            textViewSeatAndTime = itemView.findViewById(R.id.textView23);
            textViewTime = itemView.findViewById(R.id.textView21);
            textViewPrice = itemView.findViewById(R.id.textView19);

            textViewStatus.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(itemView.getContext(), R.drawable.ic_available), null, null, null);
        }

        public void bindData(int position) {
            currentItem = transportList.get(position);
            String duration = currentItem.getApproxTimeDuration();
            duration.split("");
            String finalDuration;
            if (Integer.valueOf(duration) < 60) {
                finalDuration = duration + " minutes";
            } else {
                int hours = Integer.valueOf(duration) / 60;
                int minutes = Integer.valueOf(duration) % 60;
                finalDuration = hours + " hrs " + minutes + " min";
            }

            textViewLinqName.setText(currentItem.getTransportName());
            textViewSeatAndTime.setText(currentItem.getAvailableSeats() + " seats • " + finalDuration);
            textViewTime.setText(currentItem.getTimings()[0]);
            textViewPrice.setText("$"+currentItem.getFare());
            int availablepercentage = (int) ((Float.valueOf(currentItem.getAvailableSeats()) / Float.valueOf(currentItem.getSeats())) * 100);
            if (availablepercentage < 20) {
                textViewStatus.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(itemView.getContext(), R.drawable.ic_almost_filled), null, null, null);
                textViewStatus.setTextColor(itemView.getContext().getResources().getColor(R.color.yellow_squash));
                textViewStatus.setText("Almost Filled");
            } else if (availablepercentage == 0) {
                textViewStatus.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(itemView.getContext(), R.drawable.ic_not_available), null, null, null);
                textViewStatus.setText("Not Available");
                textViewStatus.setTextColor(itemView.getContext().getResources().getColor(R.color.pale_red));
            } else {
                textViewStatus.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(itemView.getContext(), R.drawable.ic_available), null, null, null);
                textViewStatus.setText("Available");
                textViewStatus.setTextColor(itemView.getContext().getResources().getColor(R.color.windows_blue));
            }
        }
    }

    public String setPriceAsText(String price) {
        return  "$" + String.format("%.2f",Float.valueOf( price));
//        return "$" + price;
    }

}
