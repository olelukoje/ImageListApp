package ru.sibsoft.imagelistapp.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by minaevaolga on 25/05/17.
 */

public class SharedPreferencesManager {

    private static final String APP_SETTINGS = "APP_SETTINGS";
    public static final String IMAGES_DIRECTORY = "IMAGES_DIRECTORY";

    private SharedPreferencesManager() {}

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

    public static String getString(Context context, String key) {
        return getSharedPreferences(context).getString(key , null);
    }

    public static void setString(Context context, String key, String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, newValue);
        editor.apply();
    }
}