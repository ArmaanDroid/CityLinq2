package dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import listners.AdapterItemClickListner;


public class ErrorDialogFragment extends DialogFragment {
    public static ErrorDialogFragment newInstance(int titleId, String message) {
        ErrorDialogFragment fragment = new ErrorDialogFragment();

        Bundle args = new Bundle();
        args.putInt("titleId", titleId);
        args.putString("message", message);

        fragment.setArguments(args);

        return fragment;
    }

  public   static AdapterItemClickListner clickListner;

    public static ErrorDialogFragment newInstanceWithCallBack(int titleId, String message, AdapterItemClickListner clickListner1) {
        ErrorDialogFragment fragment = new ErrorDialogFragment();

        Bundle args = new Bundle();
        args.putInt("titleId", titleId);
        args.putString("message", message);
        clickListner = clickListner1;
        fragment.setArguments(args);

        return fragment;
    }

    public ErrorDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int titleId = getArguments().getInt("titleId");
        String message = getArguments().getString("message");

        return new AlertDialog.Builder(getActivity())
                .setTitle(titleId)
                .setMessage(message)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (clickListner != null)
                            clickListner.onClick(1, "click");
                        dialogInterface.dismiss();
                    }
                })
                .create();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clickListner=null;
    }
}