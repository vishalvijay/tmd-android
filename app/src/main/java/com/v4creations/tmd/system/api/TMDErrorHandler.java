package com.v4creations.tmd.system.api;

import android.content.Intent;
import android.support.v4.content.IntentCompat;

import com.v4creations.tmd.utils.Settings;
import com.v4creations.tmd.view.activity.LoginActivity;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TMDErrorHandler implements ErrorHandler {

    @Override
    public Throwable handleError(RetrofitError cause) {
        Response r = cause.getResponse();
        if (r != null && r.getStatus() == 401) {
            Settings.setCookie(null);
            openLoginAcitivity();
        }
        return cause;
    }

    private void openLoginAcitivity() {
        Intent i = new Intent(RESTClient.getContext(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        RESTClient.getContext().startActivity(i);
    }
}
