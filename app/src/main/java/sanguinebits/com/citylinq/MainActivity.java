package sanguinebits.com.citylinq;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fragments.FavouriteFragment;
import fragments.HomeFragment;
import fragments.InviteFragment;
import fragments.LoginFragment;
import fragments.MyBaseFragment;
import fragments.wallet.WalletFragment;
import fragments.passes.MyPassesFragment;
import fragments.trips.MyTripsFragment;
import utils.AppConstants;
import utils.FragTransactFucntion;

public class MainActivity extends AppCompatActivity implements MyBaseFragment.OnFragmentInteractionListener {

    private View mainContent;
    private DrawerLayout drawer;
    private static boolean isDrawerItemSelected;
    private static int drawerSelectedItemId = 0;
    private static float previousSlideOffset;

    @BindView(R.id.toolbar)
    RelativeLayout toolbar;

    @BindView(R.id.imageViewMenu)
    ImageView imageViewMenu;

    @BindView(R.id.imageViewNotification)
    ImageView imageViewNotification;

    @BindView(R.id.textViewTitle)
    TextView textViewTitle;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FragTransactFucntion.replaceFragFromFadeWithoutHistory(getSupportFragmentManager(), new HomeFragment(), R.id.frame_container_main);

        mainContent = findViewById(R.id.mainContent);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerElevation(0f);
        drawer.setScrimColor(Color.TRANSPARENT);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mainContent.setTranslationX(drawerView.getWidth() * slideOffset);
                Log.d("TAG", "onDrawerSlide: " + slideOffset);
                if (slideOffset > 0.76) {
                    mainContent.setScaleY(getScaleRatio(slideOffset));
                    mainContent.setScaleX(getScaleRatio(slideOffset));
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

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
                FragTransactFucntion.replaceFragFromFadeWithoutHistory(getSupportFragmentManager(), new LoginFragment(), R.id.frame_container_main);
                break;
            case R.id.myExploreRoutes:
                FragTransactFucntion.replaceFragFromFadeWithoutHistory(getSupportFragmentManager(), new LoginFragment(), R.id.frame_container_main);
                break;
        }
    }

    private float getScaleRatio(float slideOffset) {

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
        Log.d("TAG", "maximise: " + result);
        if (result > 100)
            result = 100;
        return result / 100;
    }

    private float minimise(float slideOffset) {
        float asd = slideOffset * 100;
        float result = (float) (100 - (20 - (100 - asd)));
//        Log.d("TAG", "minimise: asd" + asd+" result"+result);
        if (result > 100)
            result = 100;

        return result / 100;
    }

    @OnClick(R.id.imageViewMenu)
    void toggelDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (drawer.isEnabled())
                drawer.openDrawer(GravityCompat.START);
            else
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void changeUIAccToFragment(String fragmentTag, String s) {
        switch (fragmentTag) {
            case AppConstants.TAG_MY_TRIPS_FRAGMENT:
                showDarkToolbar();
                textViewTitle.setText(R.string.my_trips);
                break;
            case AppConstants.TAG_HOME_FRAGMENT:
                showNormalToolbar();
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
                textViewTitle.setText(R.string.invite);
                break;
        }
    }

    private void showNormalToolbar() {
        drawer.setEnabled(true);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        imageViewMenu.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_menu));
        textViewTitle.setTextColor(ContextCompat.getColor(this, R.color.textColorDark));
        imageViewNotification.setVisibility(View.VISIBLE);
    }

    private void showDarkToolbar() {
        drawer.setEnabled(false);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        imageViewMenu.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_back_white));
        textViewTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
        imageViewNotification.setVisibility(View.GONE);
    }

    @Override
    public void backPressed() {

    }

}
