package bluecrunch.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import bluecrunch.base.BaseApplication;

/**
 * Created by root on 13/05/18.
 */

public class Utils {

    public static boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                BaseApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    public static void getHashKey(Activity context) {

        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo("com.bluecrunch.toyota",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("hash key", something);
            }
        } catch (
                PackageManager.NameNotFoundException e1) {
             Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }

    private static boolean isArabic() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = BaseApplication.getContext().getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = BaseApplication.getContext().getResources().getConfiguration().locale;
        }
        return locale.toString().contains("ar");
    }

    public static String getLocaleLanguage() {
        String systemLang;

        if (isArabic())
            systemLang = "ar";
        else
            systemLang = "en";

        return systemLang;
    }


    public static void setDefaultLocale(String localeString) {
        SharedPreferences.Editor editor = PreferenceManager.
                getDefaultSharedPreferences(BaseApplication.getContext()).edit();
        editor.putString(Constants.LANGUAGE, localeString);
        editor.apply();

        Locale locale = new Locale(localeString);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        BaseApplication.getContext().getResources().updateConfiguration(config,
                BaseApplication.getContext().getResources().getDisplayMetrics());

    }

    public static float convertPixelsToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi /
                DisplayMetrics.DENSITY_DEFAULT);
    }

    public static void share(Context context, String whatToShare) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, whatToShare);
        shareIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(shareIntent, "Share with"));
    }


    public static void navigateToLocation(Context context, String lat, String lng) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?q=loc:" + lat + "," + lng));
        context.startActivity(intent);
    }

    public static void logout(Context context) {
//        User.saveUser(null);
//        Intent intent = new Intent(context, SplashActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(intent);
    }


    public static void openLink(Activity context, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static void callNumber(Activity context, String number) {
        Intent callIntent = new Intent(Intent.ACTION_VIEW);
        callIntent.setData(Uri.parse("tel:" + number));
        context.startActivity(callIntent);
    }

    public static String formatIntToCommaSperatedString(int number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
        DecimalFormat formatter = new DecimalFormat("#,###,###", symbols);
        return formatter.format(number);
    }


    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static String getGoogleMapURL(Double lat, Double lng) {
        return "http://maps.google.com/maps/api/staticmap?center="
                + lat + "," + lng + "&zoom=14&size=750x450&sensor=true&key=" + Constants.GOOGLE_KEY;
    }
}
