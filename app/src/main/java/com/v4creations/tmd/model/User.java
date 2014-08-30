package com.v4creations.tmd.model;

import com.v4creations.tmd.utils.Settings;

public class User {

    private String name, email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public static boolean isLoggedIn() {
        return Settings.getCookie() != null;
    }

}
