package com.v4creations.tmd.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.apptentive.android.sdk.Apptentive;

public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    }

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
