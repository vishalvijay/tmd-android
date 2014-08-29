package com.v4creations.tmd.model;

import com.v4creations.tmd.utils.C;
import com.v4creations.tmd.utils.Settings;

public class SocialLogin {
    private String token, provider, deviceId, deviceType;

    public SocialLogin(String token) {
        this.token = token;
        provider = "google_oauth2";
        deviceId = Settings.getGCMRegistrationId();
        deviceType = C.DEVICE_TYPE;
    }
}
