package com.player.coachesapp.Utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;


/**
 * Created by Ritu Patidar on 3/18/2020.
 */

public class Constant {

    public static final  DateFormat appDateFormat = new SimpleDateFormat("mm/dd/yyyy", Locale.ENGLISH);
    public static  final DateFormat serverDateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);

    public static final String IS_USER_LOGIN = "isUserLogin";
    public static final String USER = "user";
    public static final String PREFRENCES_NAME = "Player_app";
    public static final String LOGIN_STATUS = "login_status";
    public static final String PLAYER_PROFILE = "player_profile";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String DEVICE_TYPE = "2";

    public static final String SOCIAL_AUTH = "social_auth";
    public static final String GOOGLE_ID = "googleId";
    public static final String FACEBOOK_ID = "facebookId";
    public static final String GOOGLE_EMAIL = "google_email";
    public static final String FACEBOOK_EMAIL = "facebook_email";

    public static final String FIRST_NAME = "fisr_name";
    public static final String LAST_NAME = "last_name";
    public static final String DOB = "dob";
    public static final String SELECTION = "choice";

    public static String FILTTERTYPE = "";



    public static boolean isValidMail(String email) {
        String EMAIL_STRING = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(EMAIL_STRING).matcher(email).matches();
    }

    public static boolean isValidMobile(String phone) {
        boolean check;
        check = phone.length() >= 6 && phone.length() <= 13;
        return check;
    }

    public static String convertDateFormat(String original){
        Date date = null;
        try {
            date = appDateFormat.parse(original);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return serverDateFormat.format(date);
    }

    //notification keys
    public static final String REFERENCE_ID = "reference_id";
    public static final String NOTIFY_TYPE = "notify_type";
}
