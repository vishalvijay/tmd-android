package com.v4creations.tmd.system.api;

import com.v4creations.tmd.model.RssItem;

import java.util.List;

import retrofit.http.GET;

public interface TMDRssAPIClient {
    @GET("/buzzingstocks.xml")
    void buzzingStocks(APICallback<List<RssItem>> callback);

    @GET("http://www.moneycontrol.com/rss/MCtopnews.xml")
    void topNews(APICallback<List<RssItem>> callback);
}
