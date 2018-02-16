package sanguinebits.com.citylinq;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dialog.LogoutDialog;
import fragments.explore_routes.CitiesFragment;
import fragments.FavouriteFragment;
import fragments.HelpFragment;
import fragments.HomeFragment;
import fragments.InviteFragment;
import fragments.ProfileFragment;
import fragments.WebViewFragment;
import fragments.MyBaseFragment;
import fragments.wallet.WalletFragment;
import fragments.passes.MyPassesFragment;
import fragments.trips.MyTripsFragment;
import listners.AdapterItemClickListner;
import utils.AppConstants;
import utils.AppPreference;
import utils.FragTransactFucntion;

public class MainActivity extends AppCompatActivity implements MyBaseFragment.OnFragmentInteractionListener {

    private static final int PERMISSION_REQUEST_CODE = 1032;
    private View mainContent;
    private DrawerLayout drawer;
    private static boolean isDrawerItemSelected;
    private static int drawerSelectedItemId = 0;
    private static float previousSlideOffset;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @BindView(R.id.toolbar)
    RelativeLayout toolbar;

    @BindView(R.id.nav_view)
    NavigationView nav_view;

    @BindView(R.id.imageViewMenu)
    ImageView imageViewMenu;

    @BindView(R.id.imageViewNotification)
    ImageView imageViewNotification;

    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;

    @BindView(R.id.textViewTitle)
    TextView textViewTitle;

    @BindView(R.id.userName)
    TextView textViewuserName;

    private Typeface typefaceSemiBold;
    private Typeface typefaceTitle;
    private FusedLocationProviderClient mFusedLocationClient;
    private HomeFragment homeFragment;
    private static String currentFragment;
    private AppPreference preference;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        homeFragment = new HomeFragment();
        preference = new AppPreference(this);
        FragTransactFucntion.addFragFromFadeWithoutHistory(getSupportFragmentManager(), homeFragment, R.id.frame_container_main);
        mainContent = findViewById(R.id.mainContent);
        typefaceSemiBold = Typeface.createFromAsset(getAssets(), "fonts/sans_pro_semi_bold.ttf");
        typefaceTitle = Typeface.createFromAsset(getAssets(), "fonts/big_noodle_titling.ttf");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerElevation(0f);
        drawer.setScrimColor(Color.TRANSPARENT);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mainContent.setTranslationX(drawerView.getWidth() * slideOffset);
                if (slideOffset > 0.7) {
                    mainContent.setScaleY(getScaleRatio(slideOffset));
                    mainContent.setScaleX(getScaleRatio(slideOffset));
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                textViewuserName.setText(preference.getName());
                Picasso.with(getApplicationContext()).load(AppConstants.BASE_URL_image + preference.getProfilePic())
                        .placeholder(R.drawable.user)
                        .into(imageViewProfile);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mainContent.setScaleY(1f);
                mainContent.setScaleX(1f);
                if (!isDrawerItemSelected)
                    return;
                isDrawerItemSelected = false;
                replaceFragments();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }


    private void replaceFragments() {
        switch (drawerSelectedItemId) {
            case R.id.myTrips:
                FragTransactFucntion.replaceFragFromFadeHistory(getSupportFragmentManager(), new MyTripsFragment(), R.id.frame_container_main);
                break;
            case R.id.myFavTrips:
                FragTransactFucntion.replaceFragFromFadeHistory(getSupportFragmentManager(), new FavouriteFragment(), R.id.frame_container_main);
                break;
            case R.id.myWallet:
                FragTransactFucntion.replaceFragFromFadeHistory(getSupportFragmentManager(), new WalletFragment(), R.id.frame_container_main);
                break;
            case R.id.myPasses:
                FragTransactFucntion.replaceFragFromFadeHistory(getSupportFragmentManager(), new MyPassesFragment(), R.id.frame_container_main);
                break;
            case R.id.myReferEarn:
                FragTransactFucntion.replaceFragFromFadeHistory(getSupportFragmentManager(), new InviteFragment(), R.id.frame_container_main);
                break;
            case R.id.myHelp:
                FragTransactFucntion.replaceFragFromFadeHistory(getSupportFragmentManager(), new HelpFragment(), R.id.frame_container_main);
                break;
            case R.id.myExploreRoutes:
                FragTransactFucntion.replaceFragFromFadeHistory(getSupportFragmentManager(), new CitiesFragment(), R.id.frame_container_main);
                break;
            case R.id.imageViewProfile:
                FragTransactFucntion.replaceFragFromFadeHistory(getSupportFragmentManager(), new ProfileFragment(), R.id.frame_container_main);
                break;

            case R.id.contact_us:
                FragTransactFucntion.replaceFragFromFadeHistory(getSupportFragmentManager()
                        , WebViewFragment.newInstance(getString(R.string.contact_us)
                        ,AppConstants.CONTACT_US_URL),R.id.frame_container_main);
                break;

            case R.id.logout:
                LogoutDialog logoutDialog = new LogoutDialog(new AdapterItemClickListner() {
                    @Override
                    public void onClick(int position, String tag) {
                        LoginManager.getInstance().logOut();
                        AppConstants.USER_ID = null;
                        preference.clearAllPref();

                        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        MainActivity.this.startActivity(intent);
                        MainActivity.this.finish();
                    }
                });
                logoutDialog.show(getFragmentManager(), "logoutFragment");
                break;
        }

    }

    private float getScaleRatio(float slideOffset) {
        Log.d("TAG", "getScaleRatio: " + slideOffset);
        if (previousSlideOffset > slideOffset) {
            previousSlideOffset = slideOffset;
            return maximise(slideOffset);
        } else {
            previousSlideOffset = slideOffset;
            return minimise(slideOffset);
        }
    }

    private float maximise(float slideOffset) {
        float asd = slideOffset * 100;
        float result = (float) (100 - (20 - (100 - asd)));
        if (result > 100)
            result = 100;
        return result / 100;
    }

    private float minimise(float slideOffset) {
        float asd = slideOffset * 100;
        float result = (float) (100 - (20 - (100 - asd)));
        if (result > 100)
            result = 100;

        return result / 100;
    }

    @OnClick(R.id.imageViewMenu)
    void toggelDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (drawer.isEnabled()) {
                drawer.openDrawer(GravityCompat.START);

            } else
                onBackPressed();
        }
    }

    /*handle clicks here*/
    //region clicks
    @OnClick(R.id.myTrips)
    void myTrips(View view) {
        isDrawerItemSelected = true;
        drawerSelectedItemId = view.getId();
        drawer.closeDrawer(GravityCompat.START);

    }

    @OnClick(R.id.imageViewProfile)
    void myProfile(View view) {
        isDrawerItemSelected = true;
        drawerSelectedItemId = view.getId();
        drawer.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.contact_us)
    void contactUS(View view) {
        isDrawerItemSelected = true;
        drawerSelectedItemId = view.getId();
        drawer.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.myFavTrips)
    void myFavTrips(View view) {
        isDrawerItemSelected = true;
        drawerSelectedItemId = view.getId();
        drawer.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.myWallet)
    void myWallet(View view) {
        isDrawerItemSelected = true;
        drawerSelectedItemId = view.getId();
        drawer.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.logout)
    void logout(View view) {
        isDrawerItemSelected = true;
        drawerSelectedItemId = view.getId();
        drawer.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.myPasses)
    void myPasses(View view) {
        isDrawerItemSelected = true;
        drawerSelectedItemId = view.getId();
        drawer.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.myReferEarn)
    void myReferEarn(View view) {
        isDrawerItemSelected = true;
        drawerSelectedItemId = view.getId();
        drawer.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.myHelp)
    void myHelp(View view) {
        isDrawerItemSelected = true;
        drawerSelectedItemId = view.getId();
        drawer.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.myExploreRoutes)
    void myExploreRoutes(View view) {
        isDrawerItemSelected = true;
        drawerSelectedItemId = view.getId();
        drawer.closeDrawer(GravityCompat.START);
    }
    //endregion

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            buildAlertMessageNoGps();
//        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.dismiss();
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Toast.makeText(MainActivity.this, "Please turn on your GPS", Toast.LENGTH_LONG).show();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void changeUIAccToFragment(String fragmentTag, String s) {
        currentFragment = fragmentTag;
        switch (fragmentTag) {
            case AppConstants.TAG_MY_TRIPS_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.my_trips);
                break;
            case AppConstants.TAG_HOME_FRAGMENT:
                showNormalToolbar();
                textViewTitle.setTypeface(typefaceTitle);
                textViewTitle.setText(R.string.app_name1);
                break;
            case AppConstants.TAG_MY_PASSES_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.passes);
                break;
            case AppConstants.TAG_FAVOURITE_RIDES_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.favorite_trips);
                break;
            case AppConstants.TAG_WALLET_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.wallet);
                break;
            case AppConstants.TAG_INVITE_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.refer);
                break;
            case AppConstants.TAG_PROFILE_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.profile);
                break;
            case AppConstants.TAG_REVIEW_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.reviewFleet);
                break;
            case AppConstants.TAG_BROWSE_LINQS_FRAGMENT:
                toolbar.setVisibility(View.GONE);
                break;
            case AppConstants.TAG_ROUTE_DETAIL_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.route_details);
                break;
            case AppConstants.TAG_BOOK_MY_TRIP_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.book_my_trip);
                break;
            case AppConstants.TAG_CHOOSE_PAYMENT_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.choose_payment);
                break;
            case AppConstants.TAG_SELECT_CARD_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.select_card);
                break;
            case AppConstants.TAG_ADD_CARD_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.add_card);
                break;
            case AppConstants.TAG_RECIEPT_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.reciept);
                imageViewMenu.setVisibility(View.GONE);
                break;
            case AppConstants.TAG_EXPLORE_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.explore_routes);
                break;
                case AppConstants.TAG_SELECT_CITY_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.select_city);
                break;
            case AppConstants.TAG_VIEW_TRIP:
                showDarkToolbar();
                textViewTitle.setText(R.string.view_trip);
                break;
            case AppConstants.TAG_SUBMIT_CODE_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.add_code);
                break;

            case AppConstants.TAG_HELP_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.help_or_faq);
                break;
            case AppConstants.TAG_SELECT_PASS:
                showDarkToolbar();
                textViewTitle.setText(R.string.select_pass);
                break;
            case AppConstants.TAG_TRANSACTION_HISTORY_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.transactionHistory);
                break;
            case AppConstants.TAG_WEB_VIEW_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(s);
                break;

        }
    }

    private void showNormalToolbar() {
        drawer.setEnabled(true);
        toolbar.setVisibility(View.VISIBLE);
        imageViewMenu.setVisibility(View.VISIBLE);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        imageViewMenu.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_menu));
        textViewTitle.setTextColor(ContextCompat.getColor(this, R.color.textColorDark));
        imageViewNotification.setVisibility(View.GONE);
    }

    private void showDarkToolbar() {
        textViewTitle.setTypeface(typefaceSemiBold);
        drawer.setEnabled(false);
        toolbar.setVisibility(View.VISIBLE);
        imageViewMenu.setVisibility(View.VISIBLE);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        imageViewMenu.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_back_white));
        textViewTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
        imageViewNotification.setVisibility(View.GONE);
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
                    if (homeFragment != null)
                        if (!homeFragment.isDetached())
                            homeFragment.mGetCurrentLocation();
                }
            } else {
                Toast.makeText(this, R.string.provide_permissions, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
