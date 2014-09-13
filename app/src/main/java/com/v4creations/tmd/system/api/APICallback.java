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
        boolean isFirstSetCookieFound = false;
        for (Header header : response.getHeaders()) {
            if (null != header.getName() && header.getName().equals("Set-Cookie")) {
                if (isFirstSetCookieFound) {
                    cookie = header.getValue();
                    break;
                } else
                    isFirstSetCookieFound = true;
            }
        }
        Settings.setCookie(cookie);
        complete();
    }

    public void complete(){
    };

    @Override
    public void failure(RetrofitError retrofitError) {
        complete();
    }
}
