package adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sanguinebits.com.citylinq.R;

/**
 * Created by Armaan on 16-11-2017.
 */

public class SelectCardAdapter extends RecyclerView.Adapter<SelectCardAdapter.ViewHoder> {


    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_select_card,parent,false);
        ViewHoder viewHoder=new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(ViewHoder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        public ViewHoder(View itemView) {
            super(itemView);
        }
    }
}
