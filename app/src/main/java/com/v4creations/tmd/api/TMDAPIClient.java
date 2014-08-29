package com.v4creations.tmd.api;

import com.v4creations.tmd.model.SocialLogin;
import com.v4creations.tmd.model.User;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;

public interface TMDAPIClient {

    @POST("/users/social_login.json")
    void socialLogin(@Body SocialLogin login, APICallback<User> callback);

    @DELETE("/users/sign_out.json")
    void logout(APICallback callback);

    @GET("/users/profile.json")
    void profile(APICallback<User> callback);
}
