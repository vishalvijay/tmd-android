package com.v4creations.tmd.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.v4creations.tmd.R;
import com.v4creations.tmd.utils.C;
import com.v4creations.tmd.utils.Settings;
import com.v4creations.tmd.utils.SystemFeatureChecker;

import java.io.IOException;


public class SplashScreenActivity extends Activity {
    private boolean isClosed = false;
    private GoogleCloudMessaging gcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (checkPlayServices()) {
            if (Settings.getGCMRegistrationId().isEmpty()) {
                registerInBackground();
            } else
                startLoading();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        1, new DialogInterface.OnCancelListener() {

                            @Override
                            public void onCancel(DialogInterface dialog) {
                                finish();
                            }
                        }).show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    private void startLoading() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isClosed) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class
                    ));
                }
            }
        }, 3000);
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String regid = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regid = gcm.register(C.GCM_PROJECT_ID);
                } catch (IOException ex) {
                }
                return regid;
            }

            @Override
            protected void onPostExecute(String token) {
                if (token.isEmpty()) {
                    if (!SystemFeatureChecker.isInternetConnection(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), getString(R.string.failed_to_register_gcm) + ".\n" + getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), R.string.failed_to_register_gcm, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Settings.setGCMRegistrationId(token);
                    startLoading();
                }
            }
        }.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isClosed = true;
    }
}
