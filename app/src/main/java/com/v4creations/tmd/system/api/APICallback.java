package com.v4creations.tmd.system.api;


import com.v4creations.tmd.utils.Settings;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

public abstract class APICallback<T> implements Callback<T> {
    @Override
    public void success(T t, Response response) {
        String cookie = null;
        for (Header header : response.getHeaders()) {
            if (null != header.getName() && header.getName().equals("Set-Cookie")) {
                cookie = header.getValue();
                break;
            }
        }
        Settings.setCookie(cookie);
        complete();
    }

    public abstract void complete();

    @Override
    public void failure(RetrofitError retrofitError) {
        complete();
    }
}
