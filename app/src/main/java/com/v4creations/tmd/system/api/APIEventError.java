package com.v4creations.tmd.system.api;

import retrofit.RetrofitError;

public class APIEventError<T> {
    private RetrofitError mRetrofitError;

    public APIEventError(RetrofitError retrofitError) {
        mRetrofitError = retrofitError;
    }

    public void setRetrofitError(RetrofitError retrofitError) {
        this.mRetrofitError = retrofitError;
    }

    public RetrofitError getRetrofitError() {
        return mRetrofitError;
    }
}
