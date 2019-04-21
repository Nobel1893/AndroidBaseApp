package bluecrunch.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.Locale;

import bluecrunch.utils.Constants;

public class BaseApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        setLocale();
    }

    private void setLocale() {

        final Resources resources = getResources();
        final Configuration configuration = resources.getConfiguration();
        final Locale locale = getLocale(this);
        if (!configuration.locale.equals(locale)) {
            configuration.setLocale(locale);
            resources.updateConfiguration(configuration, null);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLocale();
    }


    public static Locale getLocale(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        String lang = sharedPreferences.getString(Constants.LANGUAGE, "ar");
        switch (lang) {
            case "English":
                lang = "en";
                break;
            case "Arabic":
                lang = "ar";
                break;
        }
        return new Locale(lang);
    }

    public static Context getContext() {
        return mContext;
    }
}

