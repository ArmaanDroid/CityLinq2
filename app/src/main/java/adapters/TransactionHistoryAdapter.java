package adapters;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import models.WalletDetailPerMonth;
import sanguinebits.com.citylinq.R;
import utils.SimpleDividerItemDecoration;

/**
 * Created by Armaan on 15-01-2018.
 */

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder> {

    List<WalletDetailPerMonth> mOnthAccordingList;
    public TransactionHistoryAdapter(List<WalletDetailPerMonth> mOnthAccordingList) {
        this.mOnthAccordingList=mOnthAccordingList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_transaction_history,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);

    }

    @Override
    public int getItemCount() {
        return mOnthAccordingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final RecyclerView recyclerViewInner;
        private final TextView textViewMonth;
        private WalletDetailPerMonth currentItem;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerViewInner=itemView.findViewById(R.id.recyclerViewInner);
            textViewMonth=itemView.findViewById(R.id.textView32);
        }

        public void setData(int position) {
            currentItem=mOnthAccordingList.get(position);
            textViewMonth.setText(currentItem.getMonthYear());

            recyclerViewInner.addItemDecoration(new SimpleDividerItemDecoration(itemView.getContext()));
            recyclerViewInner.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            recyclerViewInner.setAdapter(new TransactionInnerAdapter(currentItem.getTransactions()));
        }
    }
}
