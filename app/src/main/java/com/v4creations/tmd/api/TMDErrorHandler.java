package com.v4creations.tmd.api;

import android.content.Intent;

import com.v4creations.tmd.activity.LoginActivity;
import com.v4creations.tmd.utils.Settings;

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
        RESTClient.getContext().startActivity(i);
    }
}
