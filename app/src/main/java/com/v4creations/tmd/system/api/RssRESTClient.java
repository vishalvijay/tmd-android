package com.v4creations.tmd.system.api;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;

public class RssRESTClient {
    private static final String API_URL = "https://ajax.googleapis.com/ajax/services/feed/load?v=2.0&num=5&q=http://www.moneycontrol.com/rss";
    private static final RestAdapter REST_ADAPTER = new RestAdapter.Builder()
            .setEndpoint(API_URL)
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setConverter(new RssJSONConverter(new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()))
            .build();
    private static final TMDRssAPIClient TMD_RSS_API_CLIENT = REST_ADAPTER.create(TMDRssAPIClient.class);
    private static RssRESTClient mRESTClient;
    private Context mContext;

    private RssRESTClient(Context context) {
        this.mContext = context;
    }

    public Context getAppContext() {
        return mContext;
    }

    public static void initialize(Context context) {
        mRESTClient = new RssRESTClient(context);
    }

    public static Context getContext() {
        return mRESTClient.getAppContext().getApplicationContext();
    }

    public static synchronized TMDRssAPIClient getService() {
        if (mRESTClient == null)
            new RuntimeException("RssRESTClient not initialized");
        return TMD_RSS_API_CLIENT;
    }

}