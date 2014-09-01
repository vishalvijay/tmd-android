package com.v4creations.tmd.controller;

import com.v4creations.tmd.model.event.Logout;
import com.v4creations.tmd.system.api.APICallback;
import com.v4creations.tmd.system.api.APIEventError;
import com.v4creations.tmd.system.api.RESTClient;
import com.v4creations.tmd.system.event.EventCompleate;
import com.v4creations.tmd.system.event.TMDEventBus;
import com.v4creations.tmd.model.SocialLogin;
import com.v4creations.tmd.model.User;
import com.v4creations.tmd.utils.Settings;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class API {
    public static void socialLogin(String token) {
        RESTClient.getService().socialLogin(new SocialLogin(token), new APICallback<User>() {
            @Override
            public void success(User user, Response response) {
                super.success(user, response);
                Settings.setUser(user);
                TMDEventBus.getBus().post(user);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                super.failure(retrofitError);
                TMDEventBus.getBus().post(new APIEventError<User>(retrofitError));
            }

            @Override
            public void complete() {
                TMDEventBus.getBus().post(new EventCompleate<User>());
            }
        });
    }

    public static void logout(){
        RESTClient.getService().logout(new APICallback<Logout>() {
            @Override
            public void success(Logout logout, Response response) {
                super.success(logout, response);
                TMDEventBus.getBus().post(new Logout());
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                super.failure(retrofitError);
                TMDEventBus.getBus().post(new APIEventError<Logout>(retrofitError));
            }

            @Override
            public void complete() {
                TMDEventBus.getBus().post(new EventCompleate<Logout>());
            }
        });
    }
}
