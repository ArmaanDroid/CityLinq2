package utils;

import android.content.Context;
import android.content.SharedPreferences;

import fragments.HomeFragment;

/**
 * Created by Armaan on 16-11-2017.
 */

public class AppPreference {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    // keys
    private String KEY_USER_ID = "KEY_USER_ID";
    private String KEY_USER_NAME = "KEY_USER_NAME";
    private String KEY_USER_EMAIL = "KEY_USER_EMAIL";
    private String KEY_USER_MOBILE_NUMBER = "KEY_USER_MOBILE_NUMBER";
    private String KEY_USER_PROFILE_PIC = "KEY_USER_PROFILE_PIC";
    private String KEY_USER_PROMO_CODE = "KEY_USER_PROOMO_CODE";
    private String KEY_USER_DEFAULT_CITY = "KEY_USER_DEFAULT_CITY";
    private String KEY_USER_DEFAULT_CITY_ID = "KEY_USER_DEFAULT_CITY_ID";

    //Values to save
    private String userID;
    private String name;
    private String email;
    private String mobileNumber;
    private String profilePic;
    private String promoCode;
    private String defaultCity;
    private String defaultCityId;

    public String getDefaultCityId() {
        return sharedPreferences.getString(KEY_USER_DEFAULT_CITY_ID, null);
    }

    public void setDefaultCityId(String defaultCityId) {
        editor.putString(KEY_USER_DEFAULT_CITY_ID, defaultCityId);
        editor.commit();
    }

    public String getDefaultCity() {
        return sharedPreferences.getString(KEY_USER_DEFAULT_CITY, null);
    }

    public void setDefaultCity(String defaultCity) {
        editor.putString(KEY_USER_DEFAULT_CITY, defaultCity);
        editor.commit();
    }

    public String getPromoCode() {
        return sharedPreferences.getString(KEY_USER_PROMO_CODE, "");
    }

    public void setPromoCode(String promoCode) {
        editor.putString(KEY_USER_PROMO_CODE, promoCode);
        editor.commit();
    }

    public AppPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(AppConstants.SHARED_PREFERENCE_NAME, 0);
        editor = sharedPreferences.edit();
    }

    public String getProfilePic() {
        return sharedPreferences.getString(KEY_USER_PROFILE_PIC, "");
    }

    public void setProfilePic(String profilePic) {
        editor.putString(KEY_USER_PROFILE_PIC, profilePic);
        editor.commit();
    }

    public String getMobileNumber() {
        return sharedPreferences.getString(KEY_USER_MOBILE_NUMBER, "");
    }

    public void setMobileNumber(String mobileNumber) {
        editor.putString(KEY_USER_MOBILE_NUMBER, mobileNumber);
        editor.commit();
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, "");
    }

    public void setEmail(String email) {
        editor.putString(KEY_USER_EMAIL, email);
        editor.commit();
    }

    public String getName() {
        return sharedPreferences.getString(KEY_USER_NAME, "User Name");
    }

    public void setName(String name) {
        editor.putString(KEY_USER_NAME, name);
        editor.commit();
    }

    public String getUserID() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public void setUserID(String userID) {
        editor.putString(KEY_USER_ID, userID);
        editor.commit();
    }

    public void clearAllPref() {
        editor.putString(KEY_USER_ID, null);
        editor.putString(KEY_USER_DEFAULT_CITY_ID, null);
        editor.putString(KEY_USER_DEFAULT_CITY, null);
        editor.putString(KEY_USER_EMAIL, null);
        editor.putString(KEY_USER_NAME, null);
        HomeFragment.stationDestination=null;
        HomeFragment.stationSource=null;
        editor.commit();
    }

}
