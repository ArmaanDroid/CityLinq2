package sanguinebits.com.citylinq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import fragments.MyBaseFragment;
import fragments.WelcomeFragment;
import utils.FragTransactFucntion;

public class SplashActivity extends AppCompatActivity implements MyBaseFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
//        finish();
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
