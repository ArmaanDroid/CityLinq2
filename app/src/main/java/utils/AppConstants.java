package utils;

import android.location.Location;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import models.Station;
import services.SingleShotLocationProvider;

/**
 * Created by Armaan on 07-10-2017.
 */

public class AppConstants {

    public static final String BASE_URL = "http://ec2-35-165-220-224.us-west-2.compute.amazonaws.com:8081/";
    public static SingleShotLocationProvider.GPSCoordinates CURRENT_LOCATION ;
    public static  String USER_ID   ;


    public static final String TYPE_USER = "user";

    //fragment tags
    public static final String TAG_LOGIN_FRAGMENT = "TAG_LOGIN_FRAGMENT";
    public static final String TAG_EXPLORE_FRAGMENT = "TAG_EXPLORE_FRAGMENT";
    public static final String TAG_SIGNUP_FRAGMENT = "TAG_SIGNUP_FRAGMENT";
    public static final String TAG_VERIFY_PHONE_FRAGMENT = "TAG_VERIFY_PHONE_FRAGMENT";
    public static final String TAG_MY_TRIPS_FRAGMENT = "TAG_MY_TRIPS_FRAGMENT";
    public static final String TAG_HOME_FRAGMENT = "TAG_HOME_FRAGMENT";
    public static final String TAG_MY_PASSES_FRAGMENT = "TAG_MY_PASSES_FRAGMENT";
    public static final String TAG_FAVOURITE_RIDES_FRAGMENT = "TAG_FAVOURITE_RIDES_FRAGMENT";
    public static final String TAG_WALLET_FRAGMENT = "TAG_WALLET_FRAGMENT";
    public static final String TAG_INVITE_FRAGMENT = "TAG_INVITE_FRAGMENT";
    public static final String TAG_PROFILE_FRAGMENT = "TAG_PROFILE_FRAGMENT";
    public static final String TAG_REVIEW_FRAGMENT = "TAG_REVIEW_FRAGMENT";
    public static final String TAG_BROWSE_LINQS_FRAGMENT = "TAG_BROWSE_LINQS_FRAGMENT";
    public static final String TAG_ROUTE_DETAIL_FRAGMENT = "TAG_ROUTE_DETAIL_FRAGMENT";
    public static final String TAG_BOOK_MY_TRIP_FRAGMENT = "TAG_BOOK_MY_TRIP_FRAGMENT";
    public static final String TAG_CHOOSE_PAYMENT_FRAGMENT = "TAG_CHOOSE_PAYMENT_FRAGMENT";
    public static final String TAG_SELECT_CARD_FRAGMENT = "TAG_SELECT_CARD_FRAGMENT";
    public static final String TAG_ADD_CARD_FRAGMENT = "TAG_ADD_CARD_FRAGMENT";
    public static final String TAG_RECIEPT_FRAGMENT = "TAG_RECIEPT_FRAGMENT";


    //Shared Preference stuff
    public static final String SHARED_PREFERENCE_NAME = "sanguinebits.com.citylinq.preference";


    //map
    public static final int MAP_LEVEL_CITY= 13;
    public static Integer WALLET_BALANCE;


    private static List<Station> stations;

    public static void setStations(List<Station> stations) {
        AppConstants.stations = stations;
    }

    public static List<Station> getStations() {
        return stations;
    }
}
