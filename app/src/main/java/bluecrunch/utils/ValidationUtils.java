package bluecrunch.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by karim on 8/11/18.
 */

public class ValidationUtils {

    public static boolean isValidImageURL(String url) {
        if (url != null) {
            if (url.toLowerCase().contains("jpg") || url.toLowerCase().contains("jpeg") ||
                    url.toLowerCase().contains("png"))
                return true;
            else return false;
        }
        return false;
    }

    public static boolean isValidEmail(String email) {
        if (email.trim().length() == 0) {
            return false;
        }
        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        boolean matches = matcher.matches();
        return matches;
    }

    public static boolean isValidPassword(String password) {
        return password.trim().length() > 5;
    }

    public static boolean isValidMobile(String mobile) {
        if (mobile != null) {
            if (mobile.trim().length() == 11) {
                // TODO: 12/17/18 uncomment below line 
                if (mobile.trim().charAt(0) == '0' /*&& mobile.trim().charAt(1) == '1'*/)
                    return true;
            }
        }
        return false;
    }

    public static boolean isValidIDNumber(String idNumber) {
        String dateOfBirth = "";
        if (idNumber != null) {
            if (idNumber.trim().length() == 14) {
                if ((idNumber.trim().charAt(0) == '2' || idNumber.trim().charAt(0) == '3')) {
                    dateOfBirth = "" + idNumber.trim().charAt(5) + "" +
                            idNumber.trim().charAt(6) + "/" + "" + idNumber.trim().charAt(3) +
                            "" + idNumber.trim().charAt(4) + "/";
                    if (idNumber.trim().charAt(0) == '2') {
                        dateOfBirth += "19" + idNumber.trim().charAt(1)
                                + "" + idNumber.trim().charAt(2);
                    } else if (idNumber.trim().charAt(1) == '3') {
                        dateOfBirth += "20" + idNumber.trim().charAt(1)
                                + "" + idNumber.trim().charAt(2);
                    }
                }
                if (validateDateOfBirth(dateOfBirth))
                    return true;
            }
        }
        return false;
    }


    public static boolean isValidText(String text) {
        if (text != null) {
            if (text.trim().length() > 0) return true;
        }
        return false;
    }

    public static boolean isValidHomePhone(String homePhone) {
        if (homePhone != null) {
            if (homePhone.trim().length() >= 8 && homePhone.trim().length() <= 11) return true;
        }
        return false;
    }


    //    private static final String DATE_PATTERN = "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";
    private static final String DATE_PATTERN =
            "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";


    /**
     * Validate date format with regular expression
     *
     * @param date date address for validation
     * @return true valid date format, false invalid date format
     */
    public static boolean validateDateOfBirth(final String date) {

        /** dd/MM/yyyy **/
        Matcher matcher = Pattern.compile(DATE_PATTERN, Pattern.CASE_INSENSITIVE).matcher(date.trim());

        if (matcher.matches()) {
            matcher.reset();

            if (matcher.find()) {
                String day = date.substring(0, 2);
                String month = date.substring(3, 5);
                int year = Integer.parseInt(date.substring(6));

                if (day.equals("31") &&
                        (month.equals("4") || month.equals("6") || month.equals("9") ||
                                month.equals("11") || month.equals("04") || month.equals("06") ||
                                month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if (year % 4 == 0) {
                        if (day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (day.equals("29") || day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public static int findMaxChar(String str) {

        int[] count = new int[256];

        for (int i = 0; i < str.length(); i++) {
            count[str.charAt(i)]++;
        }

        int max = -1;
        char result = ' ';

        for (int j = 0; j < str.length(); j++) {
            if (max < count[str.charAt(j)] && count[str.charAt(j)] > 1) {
                max = count[str.charAt(j)];
                result = str.charAt(j);
            }
        }

        return max;

    }

}
