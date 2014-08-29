package com.v4creations.tmd.api;

import com.v4creations.tmd.utils.Settings;

import retrofit.RequestInterceptor;

public class TMDRequestInterceptor implements RequestInterceptor {
    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Accept", "application/vnd.tmd+json;version=1");
        String cookie = Settings.getCookie();
        if (cookie != null)
            request.addHeader("Set-Cookie", cookie);
    }
}
