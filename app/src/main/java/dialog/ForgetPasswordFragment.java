package dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;

import listners.AdapterItemClickListner;
import sanguinebits.com.citylinq.R;
import views.MyEditTextUnderline;

/**
 * Created by Armaan on 14-12-2017.
 */

@SuppressLint("ValidFragment")
public class ForgetPasswordFragment extends DialogFragment {

    private MyEditTextUnderline editTextPhoneNumber;
    private TextView textViewPhoneCode;
    private ImageView imageViewFlag;
    private LinearLayout linearLayoutCountryPicker;
    private Button continueButton;
    private AdapterItemClickListner adapterItemClickListner;

    public ForgetPasswordFragment(AdapterItemClickListner adapterItemClickListner) {
        this.adapterItemClickListner = adapterItemClickListner;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view=  inflater.inflate(R.layout.dialog_forget_password, null);
        editTextPhoneNumber=view.findViewById(R.id.editTextPhoneNumber);
        continueButton=view.findViewById(R.id.continueButton);


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNumber = editTextPhoneNumber.getText().toString().trim();
                if(mobileNumber.isEmpty())
                {
                    Toast.makeText(getContext(), getString(R.string.please_enter_email_id), Toast.LENGTH_SHORT).show();
                    return;
                }
                adapterItemClickListner.onClick(0,mobileNumber);
                ForgetPasswordFragment.this.dismiss();
            }
        });


        builder.setTitle(getString(R.string.forgot_password));
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);

        return builder.create();
    }

}
