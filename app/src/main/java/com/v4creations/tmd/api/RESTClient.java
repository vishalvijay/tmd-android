package com.v4creations.tmd.api;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RESTClient {
    private static final String API_URL = "http://trade-my-day.herokuapp.com/api";
    private static final RestAdapter REST_ADAPTER = new RestAdapter.Builder()
            .setEndpoint(API_URL)
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setRequestInterceptor(new TMDRequestInterceptor()).setErrorHandler(new TMDErrorHandler())
            .setConverter(new GsonConverter(new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()))
            .build();
    private static final TMDAPIClient TMD_API_CLIENT = REST_ADAPTER.create(TMDAPIClient.class);
    private static RESTClient mRESTClient;
    private Context mContext;

    private RESTClient(Context context) {
        this.mContext = context;
    }

    public Context getAppContext() {
        return mContext;
    }

    public static void initialize(Context context) {
        mRESTClient = new RESTClient(context);
    }

    public static Context getContext() {
        return mRESTClient.getAppContext().getApplicationContext();
    }

    public static synchronized TMDAPIClient getService() {
        if (mRESTClient == null)
            new RuntimeException("RESTClient not initialized");
        return TMD_API_CLIENT;
    }

}