package com.v4creations.tmd.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.v4creations.tmd.model.User;

public class Settings {
    private static final String PREFS_COOKIE = "cookie";
    private static final String PREFS_GCM_REGISTRATION_ID = "gcm_registration_id";
    private static final String PREFS_APP_LAST_VERSION = "app_last_version";
    private static final String PREFS_USER = "user";

    private static SharedPreferences prefs;
    private static Context mContext;

    private Settings() {
    }

    public static void initialize(Context context) {
        mContext = context;
        prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
    }

    private static SharedPreferences getInstance() {
        if (prefs == null)
            new RuntimeException("Settings not initialized");
        return prefs;
    }

    private static Context getContext() {
        getInstance();
        return mContext;
    }

    public static void setCookie(String cookie) {
        SharedPreferences.Editor edit = getInstance().edit();
        edit.putString(PREFS_COOKIE, cookie);
        edit.commit();
        if (cookie == null)
            setUser(null);
    }

    public static String getGCMRegistrationId() {
        String registrationId = getInstance().getString(PREFS_GCM_REGISTRATION_ID, "");
        if (registrationId.isEmpty()) {
            return "";
        }
        int registeredVersion = getInstance().getInt(PREFS_APP_LAST_VERSION, Integer.MIN_VALUE);
        int currentVersion = SystemFeatureChecker.getAppVersionCode(getContext());
        if (registeredVersion != currentVersion) {
            return "";
        }
        return registrationId;
    }

    public static void setGCMRegistrationId(String token) {
        SharedPreferences.Editor edit = getInstance().edit();
        edit.putString(PREFS_GCM_REGISTRATION_ID, token);
        edit.putInt(PREFS_APP_LAST_VERSION, SystemFeatureChecker.getAppVersionCode(getContext()));
        edit.commit();
    }

    public static String getCookie() {
        return getInstance().getString(PREFS_COOKIE, null);
    }

    public static void setUser(User user) {
        String userJson = null;
        if (user != null)
            userJson = new Gson().toJson(user);
        SharedPreferences.Editor edit = getInstance().edit();
        edit.putString(PREFS_USER, userJson);
        edit.commit();
    }

    public static User getUser() {
        String user = getInstance().getString(PREFS_USER, null);
        if (user == null)
            return null;
        return new Gson().fromJson(user, User.class);
    }

}
