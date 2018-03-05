package utils;

import android.os.Environment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Scheduled;
import models.Station;
import services.SingleShotLocationProvider;

/**
 * Created by Armaan on 07-10-2017.
 */

public class AppConstants {

    public static final String BASE_URL = "http://34.215.120.189:8081/";
    public static final String BASE_URL_image = "http://34.215.120.189:8081/static/user/";
    public static final String BASE_URL_image_driver = "http://34.215.120.189:8081/static/driver/";
    public static final String CONTACT_US_URL = "http://34.215.120.189:8081/contactUs";
    public static final String FAQS_LINK = BASE_URL+"faq";
    public static final String POLICY_LINK = BASE_URL+"private-policy";
    public static final String ABOUT_US_LINK = BASE_URL+"aboutUs";
    public static final String TERM_CONDITION_LINK = BASE_URL+"terms-condition";
    public static  boolean RELOAD_HOME = false;


    public static SingleShotLocationProvider.GPSCoordinates CURRENT_LOCATION ;
    public static  String USER_ID ;
    public static  String CITY_ID ;
    public static  String BRAINTREE_TOKEN ;

    public static final String TYPE_USER = "user";

    public static Date JOURNEY_DATE;
    //fragment tags
    public static final String TAG_VIEW_TRIP = "TAG_VIEW_TRIP";
    public static final String TAG_LOGIN_FRAGMENT = "TAG_LOGIN_FRAGMENT";
    public static final String TAG_ADD_NUMBER_FRAGMENT = "TAG_ADD_NUMBER_FRAGMENT";
    public static final String TAG_EXPLORE_FRAGMENT = "TAG_EXPLORE_FRAGMENT";
    public static final String TAG_SELECT_CITY_FRAGMENT = "TAG_SELECT_CITY_FRAGMENT";
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
    public static final String TAG_SUBMIT_CODE_FRAGMENT = "TAG_SUBMIT_CODE_FRAGMENT";
    public static final String TAG_HELP_FRAGMENT = "TAG_HELP_FRAGMENT";
    public static final String TAG_TRANSACTION_HISTORY_FRAGMENT = "TAG_TRANSACTION_HISTORY_FRAGMENT";
    public static final String TAG_WEB_VIEW_FRAGMENT = "TAG_WEB_VIEW_FRAGMENT";
    public static final String TAG_SELECT_PASS = "TAG_SELECT_PASS";
    public static final String TAG_TRACK_LINQ_FRAGMENT = "TAG_TRACK_LINQ_FRAGMENT";

    //Shared Preference stuff
    public static final String SHARED_PREFERENCE_NAME = "sanguinebits.com.citylinq.preference";

    //map
    public static final int MAP_LEVEL_CITY= 13;
    public static Float WALLET_BALANCE;

    //home screen stations
    private static List<Station> stations;
    private static List<Station> destinationStations=new ArrayList<>();

    private static List<Scheduled> scheduleList;
    public static String No_Internet="No_Internet";

    public static List<Scheduled> getScheduleList() {
        return scheduleList;
    }

    public static void setScheduleList(List<Scheduled> scheduleList) {
        AppConstants.scheduleList = scheduleList;
    }

    public static List<Station> getDestinationStations() {
        return destinationStations;
    }

    public static void setDestinationStations(List<Station> destinationStations) {
        AppConstants.destinationStations = destinationStations;
    }


    public static void setStations(List<Station> stations) {
        AppConstants.stations = stations;
    }

    public static List<Station> getStations() {
        return stations;
    }

    public static final String APP_NAME = "CityLinq";

    public static final String LOCAL_STORAGE_BASE_PATH_FOR_MEDIA = Environment
            .getExternalStorageDirectory() + "/" + APP_NAME;

    public static final String LOCAL_STORAGE_BASE_PATH_FOR_USER_PHOTOS =
            LOCAL_STORAGE_BASE_PATH_FOR_MEDIA + "/Photos/";

    public static final String JPEG_FILE_PREFIX = "IMG_";
    public static final String JPEG_FILE_SUFFIX = ".jpg";
    public static final String LOCAL_FILE_PREFIX = "file://";

}
