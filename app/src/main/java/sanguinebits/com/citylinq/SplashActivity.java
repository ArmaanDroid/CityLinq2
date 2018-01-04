package sanguinebits.com.citylinq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import butterknife.ButterKnife;
import fragments.MyBaseFragment;
import fragments.login_signup.WelcomeFragment;
import services.SingleShotLocationProvider;
import utils.AppConstants;
import utils.FragTransactFucntion;

public class SplashActivity extends AppCompatActivity implements MyBaseFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        FirebaseInstanceId.getInstance().getToken();
        getWindow().setBackgroundDrawableResource(R.drawable.bg_splash);
        FragTransactFucntion.replaceFragFromFadeWithoutHistory(getSupportFragmentManager(), new WelcomeFragment(), R.id.fragment_container_home);
    }

    @Override
    public void changeUIAccToFragment(String tag, String s) {
        switch (tag) {
            case "":
                break;

        }
    }


    @Override
    public void backPressed() {
        onBackPressed();
    }
}
