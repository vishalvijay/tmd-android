package com.v4creations.tmd.system;

import com.orm.SugarApp;
import com.v4creations.tmd.system.api.RESTClient;
import com.v4creations.tmd.utils.Settings;

public class TMDApplication extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();
        Settings.initialize(getApplicationContext());
        RESTClient.initialize(getApplicationContext());
    }
}
