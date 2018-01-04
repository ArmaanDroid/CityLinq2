package sanguinebits.com.citylinq;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fragments.login_signup.LoginFragment;
import fragments.MyBaseFragment;
import fragments.login_signup.SignupFragment;
import services.SingleShotLocationProvider;
import utils.AppConstants;
import utils.FragTransactFucntion;

public class LoginSignupActivity extends AppCompatActivity implements MyBaseFragment.OnFragmentInteractionListener {

    private static final int PERMISSION_REQUEST_CODE = 1003;
            private static final int REQUEST_PERMISSION_SETTING = 6548;
    @BindView(R.id.textViewTitle)
    TextView textViewTitle;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        ButterKnife.bind(this);
        mGetCurrentLocation();

        boolean isLogin = getIntent().getBooleanExtra("isLogin", true);
        if (isLogin)
            FragTransactFucntion.replaceFragFromFadeWithoutHistory(getSupportFragmentManager(), new LoginFragment(), R.id.fragment_container_login);
        else
            FragTransactFucntion.replaceFragFromFadeWithoutHistory(getSupportFragmentManager(), new SignupFragment(), R.id.fragment_container_login);

    }

    @OnClick(R.id.imageButtonBack)
    void imageBack() {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            case AppConstants.TAG_ADD_NUMBER_FRAGMENT:
                textViewTitle.setText(R.string.add_mobile_number);
                break;
        }
    }

    @Override
    public void backPressed() {
        onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mGetCurrentLocation();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        boolean showRational = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
                        if (showRational) {
                            mGetCurrentLocation();
                        } else {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            return;
                        }
                    }
                    mGetCurrentLocation();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            mGetCurrentLocation();
        }
    }

    private void mGetCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
            return;
        }
        SingleShotLocationProvider.requestSingleUpdate(getApplicationContext(), new SingleShotLocationProvider.LocationCallback() {
            @Override
            public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                AppConstants.CURRENT_LOCATION = location;
            }
        });
    }
}
