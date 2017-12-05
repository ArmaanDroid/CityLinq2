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
    private String KEY_TOP_FRAGMENT = "KEY_TOP_FRAGMENT";

    //Values to save
    private String userID;

    public AppPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(AppConstants.SHARED_PREFERENCE_NAME, 0);
        editor = sharedPreferences.edit();
    }

    public String getUserID() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public void setUserID(String userID) {
        editor.putString(KEY_USER_ID, userID);
        editor.commit();
    }


}
