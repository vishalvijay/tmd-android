package com.v4creations.tmd.view.activity;

import android.app.Activity;

import com.apptentive.android.sdk.Apptentive;

public class BaseActivity extends Activity {
    @Override
    protected void onStart() {
        super.onStart();
        Apptentive.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Apptentive.onStop(this);
    }
}
