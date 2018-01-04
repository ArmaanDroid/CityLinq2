package dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
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

import butterknife.OnClick;
import listners.AdapterItemClickListner;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import views.MyEditTextUnderline;

/**
 * Created by Armaan on 14-12-2017.
 */

@SuppressLint("ValidFragment")
public class ChangeNumberFragment extends DialogFragment {


    private MyEditTextUnderline editTextPhoneNumber;
    private TextView textViewPhoneCode;
    private ImageView imageViewFlag;
    private LinearLayout linearLayoutCountryPicker;
    private Button continueButton;
    private AdapterItemClickListner adapterItemClickListner;

    public ChangeNumberFragment(AdapterItemClickListner adapterItemClickListner) {
        this.adapterItemClickListner = adapterItemClickListner;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view=  inflater.inflate(R.layout.dialog_change_number, null);
        editTextPhoneNumber=view.findViewById(R.id.editTextPhoneNumber);
        textViewPhoneCode=view.findViewById(R.id.textViewPhoneCode);
        imageViewFlag=view.findViewById(R.id.imageViewFlag);
        linearLayoutCountryPicker=view.findViewById(R.id.linearLayoutCountryPicker);
        continueButton=view.findViewById(R.id.continueButton);


        linearLayoutCountryPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        // Implement your code here
                        picker.dismiss();
                        imageViewFlag.setImageResource(flagDrawableResID);
                        textViewPhoneCode.setText(dialCode);
                    }
                });
                picker.show(getFragmentManager(), "COUNTRY_PICKER");
            }
        });


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNumber = editTextPhoneNumber.getText().toString().trim();
                if(mobileNumber.isEmpty())
                {
                    Toast.makeText(getContext(), getString(R.string.please_enter_phone_umber), Toast.LENGTH_SHORT).show();
                    return;
                }
                final String completePhoneNumber = textViewPhoneCode.getText().toString() + mobileNumber;
                adapterItemClickListner.onClick(0,completePhoneNumber);
                ChangeNumberFragment.this.dismiss();
            }
        });
        Country country = Country.getCountryFromSIM(getActivity());
        if (country != null) {
            imageViewFlag.setImageResource(country.getFlag());
            textViewPhoneCode.setText(country.getDialCode());
        } else {
            country = Country.getCountryByName("United States");
            if (country != null) {
                imageViewFlag.setImageResource(country.getFlag());
                textViewPhoneCode.setText(country.getDialCode());
            }
        }

        builder.setTitle(getString(R.string.change_number));
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);


        return builder.create();
    }

}
