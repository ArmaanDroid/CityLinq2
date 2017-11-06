package fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;

import api.RequestEndPoints;
import api.WebRequestData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import models.CommonPojo;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import utils.FragTransactFucntion;
import views.MyEditTextUnderlineGreen;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends MyBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;

    @BindView(R.id.editTextName)
    MyEditTextUnderlineGreen editTextName;

    @BindView(R.id.editTextPhoneNumber)
    MyEditTextUnderlineGreen editTextPhoneNumber;

    @BindView(R.id.editTextEmailAddress)
    MyEditTextUnderlineGreen editTextEmailAddress;

    @BindView(R.id.editTextPassword)
    MyEditTextUnderlineGreen editTextPassword;

    @BindView(R.id.textViewPhoneCode)
    TextView textViewPhoneCode;

    @BindView(R.id.imageViewFlag)
    ImageView imageViewFlag;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.changeUIAccToFragment(AppConstants.TAG_SIGNUP_FRAGMENT, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
    }

    private void initView() {
        setCountryPicker();
    }

    private void setCountryPicker() {
        Country country = Country.getCountryFromSIM(getActivity());
        if (country != null) {
            imageViewFlag.setImageResource(country.getFlag());
            textViewPhoneCode.setText(country.getDialCode());
        }
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
        String name = editTextName.getText().toString();
        String email = editTextEmailAddress.getText().toString();
        final String phoneNumber = editTextPhoneNumber.getText().toString();
        String password = editTextPassword.getText().toString();


        if (name.isEmpty()) {
            editTextName.setError("Please enter name");
            return;
        }
        if (email.isEmpty()) {
            editTextEmailAddress.setError("Please enter email id");
            return;
        }
        if (!isValidEmail(email)) {
            editTextEmailAddress.setError("Please enter valid email id");
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Please enter password");
            return;
        }
        if (phoneNumber.isEmpty()) {
            editTextPhoneNumber.setError("Please enter phone number");
            return;
        }

        final String completePhoneNumber = textViewPhoneCode.getText().toString() + phoneNumber;
        WebRequestData webRequestData = new WebRequestData();
        webRequestData.setName(name);
        webRequestData.setEmail(email);
        webRequestData.setMobileNumber(completePhoneNumber);
        webRequestData.setPassword(password);
        webRequestData.setDeviceId(password);
        webRequestData.setRequestEndPoint(RequestEndPoints.SIGNUP_URL);
        makeRequest(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                FragTransactFucntion.replaceFragFromFadeHistory(getFragmentManager(), VerifyPhoneFragment.newInstance(completePhoneNumber, commonPojo.getUser().getId()), R.id.fragment_container_login);


            }

            @Override
            public void failure() throws Exception {

            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
