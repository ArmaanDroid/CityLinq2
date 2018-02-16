package adapters;

import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import listners.AdapterItemClickListner;
import models.CityList;
import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 19-01-2018.
 */

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {
    ArrayList<CityList> citiesList;
    AdapterItemClickListner clickListner;

    public CitiesAdapter(ArrayList<CityList> citiesList, AdapterItemClickListner adapterItemClickListner) {
        this.citiesList = citiesList;
        clickListner = adapterItemClickListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cities, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView citiesName;
        private CityList currentItem;

        public ViewHolder(View itemView) {
            super(itemView);
            citiesName = itemView.findViewById(R.id.citiesName);
            citiesName.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(itemView.getContext(), R.drawable.ic_arrow_right), null);
        }

        public void setData(final int position) {
            currentItem = citiesList.get(position);
            citiesName.setText(currentItem.getName());
            citiesName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListner.onClick(position,"");
                }
            });
        }
    }
}
