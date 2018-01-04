package utils;

import android.content.Context;
import android.content.SharedPreferences;

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

    //Values to save
    private String userID;
    private String name;
    private String email;
    private String mobileNumber;
    private String profilePic;


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


}
