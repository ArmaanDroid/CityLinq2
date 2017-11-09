package sanguinebits.com.citylinq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fragments.login_signup.LoginFragment;
import fragments.MyBaseFragment;
import fragments.login_signup.SignupFragment;
import utils.AppConstants;
import utils.FragTransactFucntion;

public class LoginSignupActivity extends AppCompatActivity implements MyBaseFragment.OnFragmentInteractionListener {

    @BindView(R.id.textViewTitle)
    TextView textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.imageButtonBack)
    void imageBack() {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isLogin = getIntent().getBooleanExtra("isLogin", true);
        if (isLogin)
            FragTransactFucntion.replaceFragFromFadeWithoutHistory(getSupportFragmentManager(), new LoginFragment(), R.id.fragment_container_login);
        else
            FragTransactFucntion.replaceFragFromFadeWithoutHistory(getSupportFragmentManager(), new SignupFragment(), R.id.fragment_container_login);
    }

    @Override
    public void changeUIAccToFragment(String tag, String s) {
        switch (tag) {
            case AppConstants.TAG_LOGIN_FRAGMENT:
                textViewTitle.setText(R.string.login_small);
                break;
            case AppConstants.TAG_SIGNUP_FRAGMENT:
                textViewTitle.setText(R.string.create_account);
                break;
            case AppConstants.TAG_VERIFY_PHONE_FRAGMENT:
                textViewTitle.setText(R.string.verify_phone_number);
                break;
        }
    }

    @Override
    public void backPressed() {
        onBackPressed();
    }
}
