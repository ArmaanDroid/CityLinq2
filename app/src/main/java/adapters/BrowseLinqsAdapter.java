package adapters;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import listners.AdapterItemClickListner;
import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 13-11-2017.
 */

public class BrowseLinqsAdapter extends RecyclerView.Adapter<BrowseLinqsAdapter.ViewHolder> {
    AdapterItemClickListner listner;

    public BrowseLinqsAdapter(AdapterItemClickListner listner) {
        this.listner = listner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_browse_linqs, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.imageViewRouteDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onClick(position, "");
            }
        });
        holder.textViewStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onClick(position,"book");
            }
        });

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewRouteDetails;
        TextView textViewStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewRouteDetails = itemView.findViewById(R.id.imageView5);
            textViewStatus = itemView.findViewById(R.id.textView24);
        }
    }
}
