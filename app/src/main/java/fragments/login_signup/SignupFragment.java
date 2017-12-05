package fragments.login_signup;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;

import org.json.JSONObject;

import java.util.Arrays;

import api.RequestEndPoints;
import api.WebRequestData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fragments.MyBaseFragment;
import models.CommonPojo;
import sanguinebits.com.citylinq.MainActivity;
import sanguinebits.com.citylinq.R;
import utils.AppConstants;
import utils.FragTransactFucntion;
import views.MyEditTextUnderline;

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
    private static final String TAG = "SignupFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;
    private CallbackManager callbackManager;

    @BindView(R.id.editTextName)
    MyEditTextUnderline editTextName;

    @BindView(R.id.editTextPhoneNumber)
    MyEditTextUnderline editTextPhoneNumber;

    @BindView(R.id.editTextEmailAddress)
    MyEditTextUnderline editTextEmailAddress;

    @BindView(R.id.editTextPassword)
    MyEditTextUnderline editTextPassword;

    @BindView(R.id.textViewPhoneCode)
    TextView textViewPhoneCode;

    @BindView(R.id.imageViewFlag)
    ImageView imageViewFlag;
    @BindView(R.id.fbLogin)
    LoginButton fbLogin;

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
        setFaceBookButton();

    }

    private void setFaceBookButton(){
        callbackManager = CallbackManager.Factory.create();

        final LoginManager loginManager = LoginManager.getInstance();

        fbLogin.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday"));
        fbLogin.setFragment(this);

        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "onSuccess: " + loginResult.getAccessToken());
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.d("TAG", "onSuccess: " + object);

                                try {
                                    WebRequestData webRequestData = new WebRequestData();
//                                    webRequestData.requestEndPoint = WebConstants.LOGIN_URL;
//                                    webRequestData.request = RequestNames.LOGIN;
                                    webRequestData.setEmail(object.getString("email"));
                                    webRequestData.setFacebookId(object.getString("id"));
                                    webRequestData.setName(object.getString("name"));
                                    webRequestData.setDeviceId(FirebaseInstanceId.getInstance().getToken());
                                    webRequestData.setRequestEndPoint(RequestEndPoints.LOGIN);

//                                    webRequestData.profilePicture = object.getJSONObject("picture").getJSONObject("data").getString("url");
//                                    String[] n = object.getString("name").split(" ");
//                                    webRequestData.fName = n[0];
//                                    webRequestData.lName = n[n.length - 1];
//                                    webRequestData.password = "";
                                    performLoginByFaceBook(webRequestData);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }


                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: facebook cancel");
                showToast("Login with facebook failed. Please try again.");
            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
                showToast("Login with facebook failed. Please try again.");
            }
        });

    }

    private void performLoginByFaceBook(WebRequestData webRequestData) {

        makeRequest(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                mPreference.setUserID(commonPojo.getUser().getId());
                AppConstants.USER_ID=commonPojo.getUser().getId();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void failure() throws Exception {

            }
        });
    }

    private void setCountryPicker() {
        Country country = Country.getCountryFromSIM(getActivity());
        if (country != null) {
            imageViewFlag.setImageResource(country.getFlag());
            textViewPhoneCode.setText(country.getDialCode());
        }
    }

    @OnClick(R.id.linearLayout2)
    void facebookLogin() {
        fbLogin.performClick();
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
        String name = editTextName.getText().toString().trim();
        String email = editTextEmailAddress.getText().toString().trim();
        final String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (name.isEmpty()) {
            editTextName.setError(getString(R.string.please_enter_name));
            return;
        }
        if (email.isEmpty()) {
            editTextEmailAddress.setError(getString(R.string.please_enter_email_id));
            return;
        }
        if (!isValidEmail(email)) {
            editTextEmailAddress.setError(getString(R.string.please_enter_valid_email_id));
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.please_enter_password));
            return;
        }
        if (phoneNumber.isEmpty()) {
            editTextPhoneNumber.setError(getString(R.string.please_enter_phone_umber));
            return;
        }

        final String completePhoneNumber = textViewPhoneCode.getText().toString() + phoneNumber;
        WebRequestData webRequestData = new WebRequestData();
        webRequestData.setName(name);
        webRequestData.setEmail(email);
        webRequestData.setMobileNumber(completePhoneNumber);
        webRequestData.setPassword(password);
        webRequestData.setDeviceId(FirebaseInstanceId.getInstance().getToken());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
