package bluecrunch.utils;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.CalendarContract;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import bluecrunch.base.BaseApplication;

/**
 * Created by georgenaiem on 8/27/17.
 */

public class DateUtils {

    private static String format = "yyyy-MM-dd";
    private static DateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());

    public static Date toDate(String str) {
        Date date;
        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        return date;
    }

    public static String toString(Date date) {
        return formatter.format(date);
    }

    public static String toString(String date) {
        return formatter.format(toDate(date));
    }

    public static int getYear(String str) {
        Date date = toDate(str);
        return getYear(date);
    }

    private static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static String formateTimeToAMorPM(String time) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm a", Locale.ENGLISH);
        String reformatTime = "";
        try {
            reformatTime = df.format(df.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reformatTime;
    }

    public static String formateStringToDate(String date) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String reformattedStr = "";
        try {
            reformattedStr = myFormat.format(myFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reformattedStr;

    }


    public static String getDateString(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return sdf.format(calendar.getTime());
    }

    public static Date formatCalendar(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static String formatTime(String time) {
        String res = "";
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            final Date dateObj = sdf.parse(time);
            res = new SimpleDateFormat("hh:mm a").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String getDayName(String dateStr) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-M-d").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
    }

    public static void DeleteCalendarEntry(int eventID) {
        Uri deleteUri = null;
        deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, Long.parseLong(String.valueOf(eventID)));
        int rows = BaseApplication.getContext().getContentResolver().delete(deleteUri, null, null);
    }

    public static String addDurationToTime(String time, int duration) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = null;
        try {
            d = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, duration);
        return df.format(cal.getTime());
    }

    public static long getDifferenceBetweenTwoDatesInHours(String start, String end) {
        Date endDate = null;
        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(start);
            endDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;
        long elapsedHours = (different / hoursInMilli) + (elapsedDays * 24);
        /*different = different % hoursInMilli;
        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;
        long elapsedSeconds = different / secondsInMilli;*/

        return elapsedHours;
    }


    public static String getFullMonthName(int monthNumber) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, monthNumber - 1);
        return month_date.format(cal.getTime()).toUpperCase();
    }

    public static String getMonthName(int monthNumber) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMM", Locale.getDefault());
        cal.set(Calendar.MONTH, monthNumber);
        return month_date.format(cal.getTime()).toUpperCase();
    }

    public static String addMonthsToDate(String strDate, int numberOfMonths) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = new Date(sdf.parse(strDate).getTime());
            date.setMonth(date.getMonth() + numberOfMonths);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String x = sdf.format(date);
        return sdf.format(date);
    }

    public static String getCurrentDate() {
        return Calendar.getInstance().get(Calendar.YEAR) + "-" + Calendar.getInstance().get(Calendar.MONTH)
                + "-" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }


    public static String getCurrentDateName() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return year + "-" + (month + 1) + "-" + day;
    }


    public static String getDateWithMonthName(String date) {
        /* Date is dd/MM/yyyy format */
        String[] dateArr = date.split("/");
        return dateArr[0] + " " + getMonthName(Integer.parseInt(dateArr[1]) - 1) +
                " " + dateArr[2];
    }

}
