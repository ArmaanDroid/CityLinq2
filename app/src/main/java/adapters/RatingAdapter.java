package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 09-01-2018.
 */

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {
    private int currentRating = 0;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rating_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageRate;

        public ViewHolder(View itemView) {
            super(itemView);
            imageRate = itemView.findViewById(R.id.imageRate);
        }

        public void setData(final int position) {
            imageRate.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    currentRating = position;
                    notifyDataSetChanged();
                    return false;
                }
            });
//            imageRate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    currentRating = position;
//                    notifyDataSetChanged();
//                }
//            });
            if (position > currentRating)
                imageRate.setActivated(false);
            else
                imageRate.setActivated(true);
        }
    }
}
