package fragments.login_signup;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fragments.MyBaseFragment;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import utils.FragTransactFucntion;
import views.MyEditTextUnderline;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNumberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNumberFragment extends MyBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String userID;
    private String mParam2;
    private Unbinder unbinder;


    @BindView(R.id.textViewPhoneCode)
    TextView textViewPhoneCode;

    @BindView(R.id.imageViewFlag)
    ImageView imageViewFlag;

    @BindView(R.id.editTextPhoneNumber)
    MyEditTextUnderline editTextPhoneNumber;


    public AddNumberFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param2 Parameter 2.
     * @return A new instance of fragment WelcomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNumberFragment newInstance(String userID, String param2) {
        AddNumberFragment fragment = new AddNumberFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, userID);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userID = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_number, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.changeUIAccToFragment(AppConstants.TAG_ADD_NUMBER_FRAGMENT,"");
        setCountryPicker();
    }


    @OnClick(R.id.linearLayoutCountryPicker)
    void openCountryPicker() {
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

    @OnClick(R.id.continueButton)
    void continuePhoneVerify() {
        final String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        if (phoneNumber.isEmpty()) {
            editTextPhoneNumber.setError(getString(R.string.please_enter_phone_umber));
            return;
        }

        final String completePhoneNumber = textViewPhoneCode.getText().toString() + phoneNumber;
        FragTransactFucntion.replaceFragFromFadeHistory(getFragmentManager()
                , VerifyPhoneFragment.newInstance(completePhoneNumber, userID), R.id.fragment_container_login);
    }
    private void setCountryPicker() {
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
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
