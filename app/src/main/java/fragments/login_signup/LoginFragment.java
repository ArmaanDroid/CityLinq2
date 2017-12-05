package fragments.login_signup;


import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;

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
import views.MyEditTextUnderline;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends MyBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "LoginFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;
    private CallbackManager callbackManager;

    @BindView(R.id.editTextEmailAddress)
    MyEditTextUnderline editTextEmailAddress;

    @BindView(R.id.editTextPassword)
    MyEditTextUnderline editTextPassword;
    @BindView(R.id.fbLogin)
    LoginButton fbLogin;
    private static boolean FACEBOOK_LOGINED;
    private static WebRequestData facebookRequest;

    public LoginFragment() {
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
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.changeUIAccToFragment(AppConstants.TAG_LOGIN_FRAGMENT, "");
        unbinder = ButterKnife.bind(this, view);
        setFaceBookButton();
    }



    private void setFaceBookButton() {
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
                                    facebookRequest = new WebRequestData();
//                                    webRequestData.requestEndPoint = WebConstants.LOGIN_URL;
//                                    webRequestData.request = RequestNames.LOGIN;
                                    facebookRequest.setEmail(object.getString("email"));
                                    facebookRequest.setFacebookId(object.getString("id"));
                                    facebookRequest.setName(object.getString("name"));
                                    facebookRequest.setDeviceId(FirebaseInstanceId.getInstance().getToken());
                                    facebookRequest.setRequestEndPoint(RequestEndPoints.LOGIN);

//                                    webRequestData.profilePicture = object.getJSONObject("picture").getJSONObject("data").getString("url");
//                                    String[] n = object.getString("name").split(" ");
//                                    webRequestData.fName = n[0];
//                                    webRequestData.lName = n[n.length - 1];
//                                    webRequestData.password = "";
                                    FACEBOOK_LOGINED = true;

                                    performLoginByFaceBook(facebookRequest);

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
                AppConstants.USER_ID = commonPojo.getUser().getId();
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

    @OnClick(R.id.linearLayout)
    void facebookLogin() {
        fbLogin.performClick();
    }

    @OnClick(R.id.buttonLogin)
    void login() {
        String emailAddress = editTextEmailAddress.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (emailAddress.isEmpty()) {
            editTextEmailAddress.setError(getString(R.string.please_enter_email_id));
            return;
        }
        if (!isValidEmail(emailAddress)) {
            editTextEmailAddress.setError(getString(R.string.please_enter_valid_email_id));
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.please_enter_password));
            return;
        }
        WebRequestData webRequestData = new WebRequestData();
        webRequestData.setRequestEndPoint(RequestEndPoints.LOGIN);
        webRequestData.setEmail(emailAddress);
        webRequestData.setPassword(password);
        webRequestData.setDeviceId(FirebaseInstanceId.getInstance().getToken());
        if (AppConstants.CURRENT_LOCATION != null) {
            webRequestData.setLongitude(String.valueOf(AppConstants.CURRENT_LOCATION.longitude));
            webRequestData.setLatitude(String.valueOf(AppConstants.CURRENT_LOCATION.latitude));
        }
        makeRequest(webRequestData, new WeResponseCallback() {
            @Override
            public void onResponse(CommonPojo commonPojo) throws Exception {
                mPreference.setUserID(commonPojo.getUser().getId());
                AppConstants.USER_ID = commonPojo.getUser().getId();
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
