package dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import listners.AdapterItemClickListner;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;

/**
 * Created by Armaan on 04-12-2017.
 */

@SuppressLint("ValidFragment")
public class PayByWalletDialog extends DialogFragment {
    AdapterItemClickListner adapterItemClickListner;
    private String fare;

    private TextView textViewAmountPayable;
    private TextView textViewRemainingBalance;
    private TextView textViewWalletBalance;

    @SuppressLint("ValidFragment")
    public PayByWalletDialog(String fare, AdapterItemClickListner adapterItemClickListner) {
        this.adapterItemClickListner = adapterItemClickListner;
        this.fare = fare;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_wallet_payment, null);
        textViewAmountPayable = view.findViewById(R.id.textViewAmountPayable);
        textViewRemainingBalance = view.findViewById(R.id.textViewRemainingBalance);
        textViewWalletBalance = view.findViewById(R.id.textViewWalletBalance);

        if (AppConstants.WALLET_BALANCE != null) {
            int remainingBalance = (int) (AppConstants.WALLET_BALANCE - Float.valueOf(fare));
            textViewRemainingBalance.setText(setPriceAsText(String.valueOf(remainingBalance)));
            textViewWalletBalance.setText(setPriceAsText(String.valueOf(AppConstants.WALLET_BALANCE)));
        } else {
            textViewRemainingBalance.setText(setPriceAsText("0"));
            textViewWalletBalance.setText("Not Available");

        }

        textViewAmountPayable.setText(setPriceAsText(fare));
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.payment));
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.pay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (AppConstants.WALLET_BALANCE == null)
                            dialog.cancel();

                        adapterItemClickListner.onClick(0, "pay");
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    public String setPriceAsText(String price) {
        return "$" + price;
    }

}
