package adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import models.WalletDetail;
import sanguinebits.com.citylinq.R;

import static fragments.MyBaseFragment.setPriceAsText;

/**
 * Created by Armaan on 17-01-2018.
 */

class TransactionInnerAdapter extends RecyclerView.Adapter<TransactionInnerAdapter.ViewHolder> {
    List<WalletDetail> transactions;
    protected DateFormat monthNameDateFormat = new SimpleDateFormat("MMMM d, yyyy");

    public TransactionInnerAdapter(List<WalletDetail> transactions) {
        this.transactions = transactions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_transaction_inner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_transactionAmount;
        private final TextView tv_transactionType;
        private final TextView tv_transactionDate;
        private WalletDetail currentItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_transactionType = itemView.findViewById(R.id.tv_transactionType);
            tv_transactionDate = itemView.findViewById(R.id.tv_transactionDate);
            tv_transactionAmount = itemView.findViewById(R.id.tv_transactionAmount);
        }

        public void setData(int position) {
            currentItem = transactions.get(position);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(currentItem.getDate());
            tv_transactionDate.setText(monthNameDateFormat.format(calendar.getTime()));
            tv_transactionAmount.setText(setPriceAsText(currentItem.getAmount().toString()));

            if (currentItem.getTransactionType().equalsIgnoreCase("debit")) {
                tv_transactionAmount.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.pale_red));
                tv_transactionType.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.pale_red));
                tv_transactionType.setText("Debited");
            } else if(currentItem.getTransactionType().equalsIgnoreCase("refund")){
                tv_transactionAmount.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.btnColorDark));
                tv_transactionType.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.btnColorDark));
                tv_transactionType.setText("Refund");
            }
            else{
                tv_transactionAmount.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.windows_blue));
                tv_transactionType.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.windows_blue));
                tv_transactionType.setText("Credited");

            }
        }
    }
}
