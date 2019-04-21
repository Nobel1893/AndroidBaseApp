package bluecrunch.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by karim on 14/06/17.
 */

public class DataUtil {

    public static void saveData(Context context, String variable, String data) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        prefs.edit().putString(variable, data).apply();
    }

    public static String getData(Context context, String variable,
                                 String defaultValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String data = prefs.getString(variable, defaultValue);

        return data;
    }

    public static String getLanguage(Context context) {
        return getData(context, "language",
                "ar");
    }

    public static void setLanguage(String language, Context context) {
        saveData(context, "language", language);
    }

    public static void saveData(Context context, String variable, boolean data) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        prefs.edit().putBoolean(variable, data).apply();
    }

    public static boolean getData(Context context, String variable,
                                  boolean defaultValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        boolean data = prefs.getBoolean(variable, defaultValue);

        return data;
    }


    public static void saveData(Context context, String variable, int data) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        prefs.edit().putInt(variable, data).apply();
    }

    public static int getData(Context context, String variable, int defaultValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        int data = prefs.getInt(variable, defaultValue);

        return data;
    }

    public static void saveData(Context context, String variable, long data) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        prefs.edit().putLong(variable, data).apply();
    }

    public static long getData(Context context, String variable, long defaultValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        long data = prefs.getLong(variable, defaultValue);

        return data;
    }


}
