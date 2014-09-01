package com.v4creations.tmd.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.Toast;

import com.apptentive.android.sdk.Apptentive;
import com.v4creations.tmd.R;

public class BaseActivity extends FragmentActivity {
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProgress();
    }

    public void showProgress() {
        mProgressDialog.show();
    }

    public void hideProgress() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
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

    public void defaultApiError() {
        Toast.makeText(getApplicationContext(), "Request error", Toast.LENGTH_SHORT).show();
    }
}
