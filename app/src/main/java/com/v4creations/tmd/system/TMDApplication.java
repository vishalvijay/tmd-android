package com.v4creations.tmd.system;

import android.app.Application;

import com.v4creations.tmd.api.RESTClient;
import com.v4creations.tmd.utils.Settings;

public class TMDApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Settings.initialize(getApplicationContext());
        RESTClient.initialize(getApplicationContext());
    }
}
