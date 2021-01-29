package com.player.coachesapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.player.coachesapp.Model.SignInResponse;

public class PlayerPrefrences {

    public static String saveInfo(String key, String value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.PREFRENCES_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        return key;
    }

    public static String getInfo(String key, Context context) {
        String value = "";
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.PREFRENCES_NAME, context.MODE_PRIVATE);
            value = sharedPreferences.getString(key, "");
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return value;
        }
    }

    public static SignInResponse GetUserProfile(Context context){
        Gson gson = new Gson();
        PlayerPrefrences.getInfo(Constant.PLAYER_PROFILE,context);
        SignInResponse userProfile = gson.fromJson(PlayerPrefrences.getInfo(Constant.PLAYER_PROFILE,context), SignInResponse.class);
        return userProfile;
    }


    public static void deleteSharedPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.PREFRENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static String saveInt(String key, int value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.PREFRENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
        return key;
    }

    public static int getint(String key, Context context) {
        int value = 0;
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.PREFRENCES_NAME, Context.MODE_PRIVATE);
            value = sharedPreferences.getInt(key, 0);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return value;
        }
    }


    public static void saveBoolean(String extraIsIncomingCall, boolean isIncomingCall, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.PREFRENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(extraIsIncomingCall, isIncomingCall);
        editor.commit();
    }

    public static boolean getBoolean(String extraIsIncomingCall, boolean isIncomingCall, Context context) {

        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.PREFRENCES_NAME, context.MODE_PRIVATE);
            isIncomingCall = sharedPreferences.getBoolean(extraIsIncomingCall, false);
            return isIncomingCall;
        } catch (Exception e) {
            e.printStackTrace();
            return isIncomingCall;
        }
    }

    public static void deleteVideocallData(String extraIsIncomingCall, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.PREFRENCES_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(extraIsIncomingCall);
        editor.commit();
    }
}
